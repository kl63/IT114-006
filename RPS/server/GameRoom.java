package RPS.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import RPS.common.Constants;
import RPS.common.Phase;
import RPS.common.Player;
import RPS.common.TimedEvent;

public class GameRoom extends Room {
    Phase currentPhase = Phase.READY;
    private static Logger logger = Logger.getLogger(GameRoom.class.getName());
    private TimedEvent readyTimer = null;
    private String choice; //EDITED 3/28
    private int rounds = 0; //EDITED 3/29
    private ConcurrentHashMap<Long, ServerPlayer> players = new ConcurrentHashMap<Long, ServerPlayer>();
    public GameRoom(String name) {
        super(name);
    }
    protected void setChoice(String pick) {
        System.out.println("Testing");
        String[] validChoices = {"R", "P", "S"};
        for (String choice : validChoices) {
            if (pick.equals(choice)) {
                this.choice = pick;
                return;
            }
        }
        sendMessage(null,"Invalid choice: " + pick);
    }

    public String getChoice(){ //EDITED 3/28
        return choice;
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

    protected void setReady(ServerThread client) {
        logger.info("Ready check triggered");
        if (currentPhase != Phase.READY) {
            logger.warning(String.format("readyCheck() incorrect phase: %s", Phase.READY.name()));
            return;
        }
        if (readyTimer == null) {
            sendMessage(null, "Ready Check Initiated, 30 seconds to join");
            readyTimer = new TimedEvent(30, () ->  {//EDITED 3/28
                readyTimer = null;
                readyCheck(true);
            });
        }
        players.values().stream().filter(p -> p.getClient().getClientId() == client.getClientId()).findFirst()
                .ifPresent(p -> {
                    p.setReady(true);
                    logger.info(String.format("Marked player %s[%s] as ready", p.getClient().getClientName(), p
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
            updatePhase(Phase.PICKING); //EDITED 3/28
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

    private void start() { //EDITED 3/28
        updatePhase(Phase.PICKING);
        // TODO example
        sendMessage(null, "Choosing started please type R, P, or S");
        new TimedEvent(15, () -> outcome());  
                //.setTickCallback(() -> {  //TO FIX MILESTONE 3
                    sendMessage(null, String.format("Picking session, time remaining: %s" ));
                    players.values().stream().forEach(p -> { //EDITED 3/29
                        p.setChoice(choice);
                    });
                //});
    }
    private void outcome() { //EDITED 3/28-3/29 //TO FIX
        // TODO example
        updatePhase(Phase.OUTCOME);
        sendMessage(null, "Outcome Begin");
        for(Player p: players.values()){ //EDITED 3/29
            if (p.getChoice() == p.getChoice()) {
                sendMessage(null, "It's a tie!");
                updatePhase(Phase.READY);
            }
        };
    //});
        

        
        //players.values().stream().forEach(p -> { //EDITED 3/29
                    //p.setChoice(choice);  

         
       // });
        


            


    }
        
        
                    
                    
                 
    
    private synchronized void resetSession() {
        players.values().stream().forEach(p -> p.setReady(false));
        updatePhase(Phase.READY);
        sendMessage(null, "Session ended, please intiate ready check to begin a new one");
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