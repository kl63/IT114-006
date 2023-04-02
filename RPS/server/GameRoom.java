package RPS.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;

import RPS.common.Constants;
import RPS.common.Phase;
import RPS.common.Player;
import RPS.common.TimedEvent;
import RPS.common.PointsPayload; //EDITED 3/31

public class GameRoom extends Room {
    Phase currentPhase = Phase.READY;
    private static Logger logger = Logger.getLogger(GameRoom.class.getName());
    private TimedEvent readyTimer = null;
    private String choice; // EDITED 3/28
    private int rounds = 0; // EDITED 3/29
    private ConcurrentHashMap<Long, ServerPlayer> players = new ConcurrentHashMap<Long, ServerPlayer>();

    public GameRoom(String name) {
        super(name);
    }

    protected void setChoice(String pick, long clientId) { // EDITED 3/29
        boolean checker = false;
        String[] validChoices = { "R", "P", "S" };
        if (currentPhase != Phase.PICKING) {
            return;
        }
        for (String choice : validChoices) {
            if (choice.equals(pick)) {
                checker = true;
                break;
            }
        }
        if (checker == false) {
            sendMessage(null, "Enter a valid response");
        } else {
            Player player = players.get(clientId);
            if (player != null) {
                player.setChoice(pick);
                sendMessage(null, clientId + " has chosen");
            }
        }
    }

    @Override
    protected void addClient(ServerThread client) {
        logger.info("Adding client as player");
        players.computeIfAbsent(client.getClientId(), id -> {
            ServerPlayer player = new ServerPlayer(client);
            super.addClient(client);
            logger.info(String.format("Total clients %s", clients.size()));
            return player;
        });
    }

    protected void setSkip(ServerThread client) { // EDITED 3/31

        logger.info("Player skipped");
        if (currentPhase != Phase.PICKING) {
            logger.warning(String.format("setSkip() incorrect phase: %s", Phase.PICKING.name()));
            return;
        }
        Player player = players.get(client.getClientId());
        if (player != null) {
            player.setSkip(true);
            sendMessage(null, client.getClientId() + " has skipped");
        }

    }

    protected void setReady(ServerThread client) {
        logger.info("Ready check triggered");
        if (currentPhase != Phase.READY) {
            logger.warning(String.format("readyCheck() incorrect phase: %s", Phase.READY.name()));
            return;

        }
        if (readyTimer == null) {
            sendMessage(null, "Ready Check Initiated, 30 seconds to join");
            readyTimer = new TimedEvent(30, () -> {// EDITED 3/28
                readyTimer = null;
                readyCheck(true);
            });
        }
        players.values().stream().filter(p -> p.getClient().getClientId() == client.getClientId()).findFirst()
                .ifPresent(p -> {
                    p.setReady(true);
                    logger.info(String.format("Marked player [%s] as ready", p.getClient().getClientName(), p
                            .getClient().getClientId()));
                    syncReadyStatus(p.getClient().getClientId());
                });
        readyCheck(false);
    }

    private void readyCheck(boolean timerExpired) {
        if (currentPhase != Phase.READY) {
            return;
        }
        // two examples for the same result
        // int numReady = players.values().stream().mapToInt((p) -> p.isReady() ? 1 :
        // 0).sum();
        long numReady = players.values().stream().filter(ServerPlayer::isReady).count();
        if (numReady >= Constants.MINIMUM_PLAYERS) {
            updatePhase(Phase.PICKING); // EDITED 3/28
            if (timerExpired) {
                sendMessage(null, "Ready Timer expired, starting session");
            } else if (numReady >= players.size()) {
                sendMessage(null, "Everyone in the room marked themselves ready, starting session");
                if (readyTimer != null) {
                    readyTimer.cancel();
                    readyTimer = null;
                }
            }
            start();
        } else {
            if (timerExpired) {
                resetSession();
                sendMessage(null, "Ready Timer expired, not enough players. Resetting ready check");
            }
        }
    }

    private void start() { // EDITED 3/28
        players.values().stream().forEach(p -> { // EDITED 3/31
            p.setPoints(0);
            p.setIsOut(false);
            p.setChoice(null);
            p.setSkip(false); // EDITED 4/1
        });
        updatePhase(Phase.PICKING);
        sendMessage(null, "Choosing started please type R, P, or S");
        new TimedEvent(19, () -> outcome());
        // .setTickCallback((time) -> { //TO FIX MILESTONE 3
        // sendMessage(null, String.format("Picking session, time remaining: %s",
        // time));
        // });
        /*
         * players.values().stream().forEach(p -> { //EDITED 4/1
         * if(p.isSkip()== true || p.getChoice() == null || p.isOut() == true){
         * outcome();
         * }
         * });
         * 
         * players.values().stream().forEach(p -> { //EDITED 3/29
         * p.setChoice(choice);
         * });
         */
        // });
    }

    private void outcome() { // EDITED 3/28-3/29
        updatePhase(Phase.OUTCOME);
        // List<ServerPlayer> winners = new ArrayList<>(); //EDITED 3/30
        System.out.println("TESTING 1");
        players.values().stream().filter(players -> players.getChoice() == null).forEach(p -> {
            p.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, "You did not make a choice. You lose.");
            p.setIsOut(true);
            syncIsOut(p.getClient().getClientId());
        });
        /*
         * for (ServerPlayer p : players.values()) {
         * if (p.getChoice() == null) {
         * p.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID,
         * "You did not make a choice. You lose.");
         * p.setIsOut(true);
         * syncIsOut(p.getClient().getClientId());
         * continue;
         * }
         */
        System.out.println("TESTING 2");
        Stream<ServerPlayer> numReady = players.values().stream()
                .filter(p -> p.isReady() && p.getChoice() != null); // EDITED 3/31
                System.out.println("TESTING 2a: "+ numReady.count());
        if (numReady.count() > 1) {
            System.out.println("TESTING 2b: "+ numReady.count());
            for (int i = 0; i < numReady.count(); i++) {
                ServerPlayer playerA = (ServerPlayer) numReady.toArray()[i];
                ServerPlayer playerB = null;
                if (i + 1 < numReady.count()) {
                    playerB = (ServerPlayer) numReady.toArray()[i + 1];
                } else {
                    playerB = (ServerPlayer) numReady.toArray()[0];
                }
                players.values().stream().filter(p -> p.numReady.count()); //TO DO 4/2
                System.out.println("TESTING 2ba");
                String choiceA = playerA.getChoice();
                String choiceB = playerB.getChoice();
                if (choiceA.equals(choiceB)) {
                    sendMessage(null, String.format("%s has tied with %s. %s chose %s and %s chose %s.",
                            playerA.getClient().getClientName(), playerB.getClient().getClientName(),
                            playerA.getClient().getClientName(), playerA.getChoice(),
                            playerB.getClient().getClientName(), playerB.getChoice()));


                    // GOAL: Make B lose
                    System.out.println("TESTING 2b");
                } else if ((choiceA.equalsIgnoreCase("R")) && choiceB.equalsIgnoreCase("S") ||
                        (choiceA.equalsIgnoreCase("P")) && choiceB.equalsIgnoreCase("R") ||
                        (choiceA.equalsIgnoreCase("S")) && choiceB.equalsIgnoreCase("P")) {
                    playerA.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("%s has beaten %s." +
                            "%s chose %s and %s chose %s.",
                            playerA.getClient().getClientName(), playerB.getClient().getClientName(),
                            playerA.getClient().getClientName(), playerA.getChoice(),
                            playerB.getClient().getClientName(), playerB.getChoice()));
                    playerA.setPoints(5);
                    playerB.setIsOut(true);

                } else {
                    System.out.println("TESTING 2c");
                    playerB.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("%s has beaten %s." +
                            "%s chose %s and %s chose %s.",
                            playerB.getClient().getClientName(), playerA.getClient().getClientName(),
                            playerB.getClient().getClientName(), playerB.getChoice(),
                            playerA.getClient().getClientName(), playerA.getChoice()));
                    playerB.setPoints(5);
                    playerA.setIsOut(true);
                }
            }
            System.out.println("TESTING 3");
            for (ServerPlayer players : players.values()) {
                syncPoints(players.getClient().getClientId(), players.getPoints());
                syncOut(players.getClient().getClientId());

            }
        }

        long numRemain = players.values().stream().filter(player -> !player.isOut()).count();
        if (numRemain > 1) {
            for (ServerPlayer player : players.values()) {
                player.setChoice(null);
                player.setSkip(false);
            }
            updatePhase(Phase.PICKING);
        } else {
            resetSession();
        }
    }

    private void syncPoints(long clientId, int points) {
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            client = iter.next();
            boolean success = client.getClient().sendPoints(points, clientId);
            if (!success) {
                handleDisconnect(client);
            }

        }
    }

    private void syncOut(long clientId) {
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            client = iter.next();
            boolean success = client.getClient().sendOut(clientId);
            if (!success) {
                handleDisconnect(client);
            }

        }
    }

    /**
     * Syncs to everyone that a specific client is out for a round
     * 
     * @param clientId
     */
    private void syncIsOut(long client) {

        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer sp = iter.next();
            handleDisconnect(sp);

        }
    }

    private synchronized void resetSession() {
        players.values().stream().forEach(p -> p.setReady(false));
        updatePhase(Phase.READY);
        sendMessage(null, "Session ended, please intiate ready check to begin a new one");
        players.values().stream().forEach(p -> {
            p.setPoints(0); // EDITED 3/31
        });
    }

    private void updatePhase(Phase phase) {
        if (currentPhase == phase) {
            return;
        }
        currentPhase = phase;
        // NOTE: since the collection can yield a removal during iteration, an iterator
        // is better than relying on forEach
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendPhaseSync(currentPhase);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

    protected void handleDisconnect(ServerPlayer player) {
        if (players.containsKey(player.getClient().getClientId())) {
            players.remove(player.getClient().getClientId());
            super.handleDisconnect(null, player.getClient());
            logger.info(String.format("Total clients %s", clients.size()));
            sendMessage(null, player.getClient().getClientName() + " disconnected");
            if (players.isEmpty()) {
                close();
            }
        }
    }

    private void syncReadyStatus(long clientId) {
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendReadyStatus(clientId);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

}