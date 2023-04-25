package RPS.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap; //EDITED 4/4
import java.util.logging.Logger;
import java.util.List; //EDITED 4/4
import java.util.ArrayList; //EDITED 4/4

import RPS.common.Constants;
import RPS.common.Payload;
import RPS.common.PointsPayload;
import RPS.common.PayloadType;
import RPS.common.RoomResultPayload;
import RPS.common.Phase; //EDITED 4/4
import RPS.common.Player; //EDITED 4/4

public enum Client {
    INSTANCE;

    Socket server = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    final String ipressPattern = "/connect\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{3,5})";
    final String localhostPattern = "/connect\\s+(localhost:\\d{3,5})";
    boolean isRunning = false;
    private Thread inputThread;
    private Thread fromServerThread;
    private String clientName = "";
    private long myClientId = Constants.DEFAULT_CLIENT_ID;
    private static Logger logger = Logger.getLogger(Client.class.getName());
    private boolean isAway = false; // EDITED 4/19

    // private Hashtable<Long, String> userList = new Hashtable<Long, String>();
    // //EDITED 4/4
    private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
    private Phase currentPhase = Phase.READY; // EDITED 4/4
    List<IClientEvents> listeners = new ArrayList<IClientEvents>(); // EDITED 4/4

    public boolean isConnected() {
        if (server == null) {
            return false;
        }
        // https://stackoverflow.com/a/10241044
        // Note: these check the client's end of the socket connect; therefore they
        // don't really help determine
        // if the server had a problem
        return server.isConnected() && !server.isClosed() && !server.isInputShutdown() && !server.isOutputShutdown();

    }

    public boolean isAway() { // EDITED 4/19
        return isAway ;
    }

    public void setAway(boolean isAway) { // EDITED 4/19
        this.isAway = isAway;
    }

    public boolean isCurrentPhase(Phase phase) { // EDITED 4/4
        return currentPhase == phase;
    }

    public Phase getCurrentPhase() { // EDITED 4/4
        return currentPhase;
    }

    public void addListener(IClientEvents listener) { // EDITED 4/4
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    /**
     * Takes an ip address and a port to attempt a socket connection to a server.
     * 
     * @param address
     * @param port
     * @return true if connection was successful
     */
    boolean connect(String address, int port, String clientName, IClientEvents listener) { // EDITED 4/4
        try {
            addListener(listener);
            this.clientName = clientName;
            server = new Socket(address, port);
            // channel to send to server
            out = new ObjectOutputStream(server.getOutputStream());
            // channel to listen to server
            in = new ObjectInputStream(server.getInputStream());
            logger.info("Client connected");
            listenForServerPayload();
            sendConnect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isConnected();
    }

    public void removeListener(IClientEvents listener) { // EDITED 4/4
        listeners.remove(listener);
    }

    // Send methods
    public void sendChoice(String choice) throws IOException { // EDITED 3/27
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CHOICE);
        p.setChoice(choice);
        out.writeObject(p);
    }

    public void sendSkip() throws IOException { // EDITED 3/27
        Payload p = new Payload();
        p.setPayloadType(PayloadType.SKIP);
        out.writeObject(p);
    }

    public void sendReadyStatus() throws IOException { // EDITED 4/4
        Payload p = new Payload();
        p.setPayloadType(PayloadType.READY);
        out.writeObject(p);
    }

    public void sendListRooms(String query) throws IOException { // EDITED 4/4
        Payload p = new Payload();
        p.setPayloadType(PayloadType.GET_ROOMS);
        p.setMessage(query);
        out.writeObject(p);
    }

    public void sendJoinRoom(String roomName) throws IOException { // EDITED 4/4
        Payload p = new Payload();
        p.setPayloadType(PayloadType.JOIN_ROOM);
        p.setMessage(roomName);
        out.writeObject(p);
    }

    public void sendCreateRoom(String roomName) throws IOException { // EDITED 4/4
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CREATE_ROOM);
        p.setMessage(roomName);
        out.writeObject(p);
    }

    protected void sendDisconnect() throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.DISCONNECT);
        out.writeObject(p);
    }

    protected void sendConnect() throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CONNECT);
        p.setClientName(clientName);
        out.writeObject(p);
    }

    public void sendMessage(String message) throws IOException { // EDITED 4/4
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MESSAGE);
        p.setMessage(message);
        p.setClientName(clientName);
        out.writeObject(p);
    }

    public void sendAwayStatus() throws IOException { // EDITED 4/21
        Payload p = new Payload();
        p.setPayloadType(PayloadType.AWAY);
        out.writeObject(p);

    }
    public void sendSpectatorStatus() throws IOException { // EDITED 4/24
        Payload p = new Payload();
        p.setPayloadType(PayloadType.SPECTATOR);
        out.writeObject(p);
    }

    private void listenForServerPayload() {
        fromServerThread = new Thread() {
            @Override
            public void run() {
                try {
                    Payload fromServer;
                    isRunning = true; // EDITED 4/10

                    // while we're connected, listen for objects from server
                    while (isRunning && !server.isClosed() && !server.isInputShutdown()
                            && (fromServer = (Payload) in.readObject()) != null) {

                        logger.info("Debug Info: " + fromServer);
                        processPayload(fromServer);

                    }
                    logger.info("listenForServerPayload() loop exited");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    logger.info("Stopped listening to server input");
                    close();
                }
            }
        };
        fromServerThread.start();// start the thread
    }

    protected String getClientNameById(long clientId) {
        if (players.containsKey(clientId)) {
            return ((ClientPlayer) players.get(clientId)).getClientName();
        }
        if (clientId == Constants.DEFAULT_CLIENT_ID) {
            return "[Server]";
        }
        return "unkown user";
    }

    private void addPlayer(long clientId, String clientName) { // EDITED 4/4
        if (!players.containsKey(clientId)) {
            ClientPlayer cp = new ClientPlayer(clientId, clientName);
            players.put(clientId, cp);
        }
    }

    private void removePlayer(long clientId) { // EDITED 4/4
        if (players.containsKey(clientId)) {
            players.remove(clientId);
        }
    }

    /**
     * Processes incoming payloads from ServerThread
     * 
     * @param p
     */

    /*
     * UCID#: 31555276
     * DATE: 4/4/23
     */
    private void processPayload(Payload p) {
        switch (p.getPayloadType()) {
            /*
             * Payload for clients to connect to localhost
             */
            case CONNECT: // EDITED 4/4
                addPlayer(p.getClientId(), p.getClientName());
                logger.info(String.format("*%s %s*",
                        p.getClientName(),
                        p.getMessage()));
                listeners.forEach(l -> l.onClientConnect(
                        p.getClientId(), p.getClientName(),
                        String.format("*%s %s*", p.getClientName(), p.getMessage())));
                break;
            /*
             * Payload for disonnecting from the game/server
             */
            case DISCONNECT: // EDITED 4/4
                removePlayer(p.getClientId());
                if (p.getClientId() == myClientId) {
                    myClientId = Constants.DEFAULT_CLIENT_ID;
                    // isSeeker = false;
                }
                logger.info(String.format("*%s %s*",
                        p.getClientName(),
                        p.getMessage()));
                listeners.forEach(l -> l.onClientDisconnect(
                        p.getClientId(), p.getClientName(), (String.format("*%s %s*",
                                p.getClientName(),
                                p.getMessage()))));
                break;
            /*
             * UCID#: 31555276
             * DATE: 4/4/23
             */

            /*
             * Paylaod for geeting all the clioents in a room
             */
            case SYNC_CLIENT: // EDITED 4/4
                addPlayer(p.getClientId(), p.getClientName());
                listeners.forEach(l -> l.onSyncClient(
                        p.getClientId(), p.getClientName()));
                break;

            /*
             * Payload for sending message, ex chat
             */
            case MESSAGE: // EDITED 4/4
                System.out.println(Constants.ANSI_CYAN + String.format("%s: %s",
                        getClientNameById(p.getClientId()),
                        p.getMessage()) + Constants.ANSI_RESET);
                listeners.forEach(l -> l.onMessageReceive(
                        p.getClientId(), p.getMessage()));
                break;

            /*
             * Payload for geting clientID
             */
            case CLIENT_ID: // EDITED 4/4
                if (myClientId == Constants.DEFAULT_CLIENT_ID) {
                    myClientId = p.getClientId();
                } else {
                    logger.warning("Receiving client id despite already being set");
                }
                listeners.forEach(l -> l.onReceiveClientId(
                        p.getClientId()));
                break;
            /*
             * UCID#: 31555276
             * DATE: 4/4/23
             */

            /*
             * Payload for getting the creted rooms
             */
            case GET_ROOMS: // EDITED 4/4
                RoomResultPayload rp = (RoomResultPayload) p;
                logger.info("Received Room List:");
                if (rp.getMessage() != null) {
                    logger.info(rp.getMessage());
                } else {
                    for (int i = 0, l = rp.getRooms().length; i < l; i++) {
                        logger.info(String.format("%s) %s", (i + 1), rp.getRooms()[i]));
                    }
                }
                listeners.forEach(l -> l.onReceiveRoomList(
                        rp.getRooms(), p.getMessage()));
                break;

            /*
             * Payload for getting the list of rooms created
             */
            case RESET_USER_LIST: // EDITED 4/4
                players.clear();
                listeners.forEach(l -> l.onResetUserList());
                break;
            /*
             * Payload to set the players satus to start the game
             */
            case READY: // EDITED 4/4
                logger.info(String.format("Player %s is ready", getClientNameById(p.getClientId()))
                        + Constants.ANSI_RESET);
                if (players.containsKey(p.getClientId())) {
                    players.get(p.getClientId()).setReady(true);
                }
                listeners.forEach(l -> l.onReceiveReady(p.getClientId()));
                long count = players.values().stream().filter(Player::isReady).count();
                listeners.forEach(l -> l.onReceiveReadyCount(count));
                break;
            /*
             * UCID#: 31555276
             * DATE: 4/4/23
             */

            /*
             * Payload for the different phase of the game.
             */
            case PHASE: // EDITED 4/4
                logger.info(Constants.ANSI_YELLOW + String.format("The current phase is %s", p.getMessage())
                        + Constants.ANSI_RESET);
                currentPhase = Phase.valueOf(p.getMessage());
                listeners.forEach(l -> l.onReceivePhase(Phase.valueOf(p.getMessage())));
                break;

            /*
             * Payload for getting the players picked choice
             */
            case CHOICE: // EDITED 4/4
                try {
                    logger.info(
                            String.format(Constants.ANSI_GREEN + "Player %s chosen %s", p.getClientId(), p.getChoice())
                                    + Constants.ANSI_RESET);

                } catch (Exception e) {
                    logger.severe(Constants.ANSI_RED + String.format("Error handling position payload: %s", e)
                            + Constants.ANSI_RESET);
                }
                break;

            /*
             * UCID#: 31555276
             * DATE: 4/4/23
             */

            /*
             * Payload for points that the player earn.
             */
            case POINTS: // EDITED 4/4
                try {
                    PointsPayload pp = (PointsPayload) p;
                    if (players.containsKey(p.getClientId())) {
                        players.get(p.getClientId()).setPoints(pp.getPoints());
                    }
                    listeners.forEach(l -> l.onReceivePoints(pp.getClientId(), pp.getPoints()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            /*
             * Paylod for syncing if a player is out.
             */
            case OUT: // EDITED 4/4
                if (p.getClientId() == Constants.DEFAULT_CLIENT_ID) {
                    players.values().stream().forEach(player -> player.setIsOut(false));
                    logger.info("Resetting out players");
                } else {
                    logger.info(
                            Constants.ANSI_BLUE + String.format("Player %s is out!", getClientNameById(p.getClientId()))
                                    + Constants.ANSI_RESET);
                    if (players.containsKey(p.getClientId())) {
                        players.get(p.getClientId()).setIsOut(true);
                    }
                }
                listeners.forEach(l -> l.onReceiveOut(p.getClientId()));
                break;

                case AWAY: // EDITED 4/24
                if (players.containsKey(p.getClientId())) {
                    players.get(p.getClientId()).setAway(isAway);
                    if (isAway) {
                        logger.info(String.format("Player %s is away", getClientNameById(p.getClientId()))
                                + Constants.ANSI_RESET);
                    }
                }
                listeners.forEach(l -> l.onReceiveAway(p.getClientId(), isAway));
                break;

            default:
                logger.warning(Constants.ANSI_RED + String.format("Unhandled Payload type: %s", p.getPayloadType())
                        + Constants.ANSI_RESET); // EDITEd 3/29
                break;

        }
    }

    private void close() { // EDITED 4/4
        myClientId = Constants.DEFAULT_CLIENT_ID;
        players.clear();
        try {
            inputThread.interrupt();
        } catch (Exception e) {
            System.out.println("Error interrupting input");
            e.printStackTrace();
        }
        try {
            fromServerThread.interrupt();
        } catch (Exception e) {
            System.out.println("Error interrupting listener");
            e.printStackTrace();
        }
        try {
            System.out.println("Closing output stream");
            out.close();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Closing input stream");
            in.close();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Closing connection");
            server.close();
            System.out.println("Closed socket");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        }
    }

}
