package RPS.server;

import java.lang.System.Logger.Level;
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
import RPS.common.PointsPayload;

public class GameRoom extends Room {
    Phase currentPhase = Phase.READY;
    private static Logger logger = Logger.getLogger(GameRoom.class.getName());
    private TimedEvent readyTimer = null;
    private String choice;
    private int rounds = 0;
    private ConcurrentHashMap<Long, ServerPlayer> players = new ConcurrentHashMap<Long, ServerPlayer>();

    public GameRoom(String name) {
        super(name);
    }

    protected void setChoice(String pick, long clientId) {
        boolean checker = false;
        String[] validChoices = { "R", "P", "S" };
        if (currentPhase != Phase.PICKING) {
            return;
        }
        /*
         * Checks player has picked one of the valid choices
         * If false it will print line 43
         */
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
            client.sendPhaseSync(currentPhase);
            return player;
        });
    }

    protected void setSkip(ServerThread client) {

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

    public void setAway(ServerThread client) { // EDITED 4/21
        logger.info("Away check triggered");
        if (currentPhase != Phase.PICKING) {
            logger.warning(String.format("setAway() incorrect phase: %s", Phase.PICKING.name()));
            return;
        }

        ServerPlayer player = players.get(client.getClientId());
        if (player != null) {
            player.setAway(true);
            logger.info(String.format("Marked player [%s] as ready", player.getClient().getClientName(), player
                    .getClient().getClientId()));
            syncAwayStatus(player.getClient().getClientId());
        }
    }

    public void setSpectator(ServerThread client) { // EDITED 4/24 FIX HERE
        logger.info("Spectator check triggered");
        if (currentPhase != Phase.READY) {
            logger.warning(String.format("setSpectator() incorrect phase: %s", Phase.READY.name()));
            readyTimer.cancel();
            readyTimer = null;
            return;
        }

        ServerPlayer player = players.get(client.getClientId());
        if (player != null) {
            player.setSpectator(true);
            logger.info(String.format("Marked player [%s] as spectator", player.getClient().getClientName(), player
                    .getClient().getClientId()));
            syncSpectatorStatus(player.getClient().getClientId());

                
            
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
            readyTimer = new TimedEvent(30, () -> {
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
            updatePhase(Phase.PICKING);
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
    /*
     * UCID#: 31555276
     * DATE: 4/14/23
     * COMMENT: Round Start
     */
    private void nextRound(){
        updatePhase(Phase.PICKING);
        players.values().stream().forEach(p -> {
            p.setChoice(null);
            p.setSkip(false);
        });
        new TimedEvent(30, () -> outcome());
        sendMessage(null, "Next Round, Please pick again.");
    }

    private void start() {
        players.values().stream().forEach(p -> {
            p.setPoints(0);
            p.setIsOut(false);
            p.setChoice(null);
            p.setSkip(false);
        });
        updatePhase(Phase.PICKING);
        /*
         * UCID#: 31555276
         * DATE: 4/14/23
         * COMMENT: Players have X seconds to pick
         */
        sendMessage(null, "Choosing started please type R, P, or S");
        new TimedEvent(30, () -> outcome()) {
            // .setTickCallback((time) -> { // TO FIX MILESTONE 3
            // sendMessage(null, String.format("Picking session, time remaining: %s",
            // time));
        };
    }

    /*
     * UCID#: 31555276
     * DATE: 4/14/23
     * COMMENT: Logic for not picking on time and skipping
     */
    private void outcome() {
        updatePhase(Phase.OUTCOME);
        players.values().stream()
                .filter(p -> !p.isOut() && (p.getChoice() == null || p.isSkip() == true)).forEach(p -> {
                    p.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID,
                            "You did not make a choice or skipped. You lose." + p.getChoice() + " " + p.isSkip());
                    p.setIsOut(true);
                    syncOut(p.getClient().getClientId());

                });

        players.values().stream().filter(p -> p.isAway() == true).forEach(p -> { // EDITED 4/25 AWAY PLAYER
            p.setSkip(true);
        });
        /*UCID: kl63
         * DATE: 5/1/23
         * COMMENT: numReadyCount is a count for who are ready and made a choice. 
         * This way the others aren't counted for the battle (game logic)
         */

        long numReadyCount = players.values().stream().filter(p -> p.isReady() && p.getChoice() != null && !p.isAway()
                && !p.isOut() && !p.isSkip() && !p.isSpectator()).count(); 


        
        List<ServerPlayer> numReady = (List<ServerPlayer>) players.values().stream().filter(p -> p.isReady()
                && p.getChoice() != null && !p.isAway() && !p.isOut() && !p.isSkip() && !p.isSpectator()).toList(); //SPEC HERE
        logger.info(String.format("TESTING COUNT:  %s", numReady.size()));
        if (numReadyCount > 1) {
            for (int i = 0; i < numReadyCount; i++) {
                ServerPlayer playerA = (ServerPlayer) numReady.get(i);
                ServerPlayer playerB = null;
                if (i + 1 < numReadyCount) {
                    playerB = (ServerPlayer) numReady.get(i + 1);
                } else {
                    playerB = (ServerPlayer) numReady.get(0);
                }

                String choiceA = playerA.getChoice();
                String choiceB = playerB.getChoice();
                /*
                 * UCID#: 31555276
                 * DATE: 4/14/23
                 * COMMENT: Logic for calculating winners (TIED GAME)
                 */
                if (choiceA.equals(choiceB)) {
                    sendMessage(null, String.format("%s has tied with %s.  %s chose %s and %s chose %s.",
                            playerA.getClient().getClientName(), playerB.getClient().getClientName(),
                            playerA.getClient().getClientName(), playerA.getChoice(),
                            playerB.getClient().getClientName(), playerB.getChoice()));

                    // resetSession();

                    /*
                     * UCID#: 31555276
                     * DATE: 4/14/23
                     * COMMENT: Logic for calculating winners (PLAYER A & PLAYER B)
                     */
                } else if ((choiceA.equalsIgnoreCase("R")) && choiceB.equalsIgnoreCase("S") ||
                        (choiceA.equalsIgnoreCase("P")) && choiceB.equalsIgnoreCase("R") ||
                        (choiceA.equalsIgnoreCase("S")) && choiceB.equalsIgnoreCase("P")) {
                    playerA.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("%s has beaten %s. " +
                            "%s chose %s and %s chose %s.",
                            playerA.getClient().getClientName(), playerB.getClient().getClientName(),
                            playerA.getClient().getClientName(), playerA.getChoice(),
                            playerB.getClient().getClientName(), playerB.getChoice()));
                    // playerA.setPoints(5);
                    syncPoints(playerA.getClient().getClientId(), 10);
                    // playerB.setIsOut(true);
                    // resetSession();

                } else if (((choiceA.equalsIgnoreCase("S")) && choiceB.equalsIgnoreCase("R") ||
                        (choiceA.equalsIgnoreCase("R")) && choiceB.equalsIgnoreCase("P") ||
                        (choiceA.equalsIgnoreCase("P")) && choiceB.equalsIgnoreCase("S"))) {
                    playerB.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("%s has lost to  %s. " +
                            "%s chose %s and %s chose %s.",
                            playerA.getClient().getClientName(), playerB.getClient().getClientName(),
                            playerB.getClient().getClientName(), playerB.getChoice(),
                            playerA.getClient().getClientName(), playerA.getChoice()));
                    // playerB.setPoints(5);
                    syncPoints(playerB.getClient().getClientId(), 10);
                    playerA.setIsOut(true);
                    // resetSession()

                }
            }

            List<ServerPlayer> numReadyS = (List<ServerPlayer>) players.values().stream().filter(p -> p.isReady()
                    && p.getChoice() != null && !p.isAway() && !p.isOut() && !p.isSkip()).toList();
            logger.info(String.format("TESTING COUNT AFTER:  %s", numReadyS.size()));
            // resetSession();
            if (numReadyS.size() <= 1) {

                resetSession();

            } else if (numReadyS.size() > 1) {
                nextRound();

            }
            /*
             * UCID#: 31555276
             * DATE: 4/4/23
             * COMMENT: Logic for only 1 player pick.The rest skips.
             */
        } else if (numReadyCount == 1) {
            players.values().stream().filter(players -> players.getChoice() != null && !players.isOut() == true)
                    .forEach(p -> {
                        p.getClient().sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("%s has won the game",
                                p.getClient().getClientName()));
                        // resetSession();
                    });
            resetSession();

        } else if (numReadyCount == 0) {
            sendMessage(null, String.format("Tied Game!"));
            resetSession();
        } else {
            //resetSession();
        }

        /*boolean gameOver = false;
        if (numReadyCount == 0 || numReadyCount == 1) {
            gameOver = true;
            // Show Score
            for (ServerPlayer players : players.values()) {
                // syncPoints(players.getClient().getClientId(), players.getPoints());
                syncOut(players.getClient().getClientId());
                String message = players.getClient().getClientName() + ": " + players.getPoints();
                sendMessage(null, message + " points");
            }
            resetSession();
        } else {
            players.values().stream().forEach(p -> {
                p.setChoice(null);
                p.setSkip(false);
            });
            updatePhase(Phase.PICKING);
            new TimedEvent(30, () -> outcome());
        }*/
    }

    /*
     * for (ServerPlayer players : players.values()) {
     * syncPoints(players.getClient().getClientId(), players.getPoints());
     * syncOut(players.getClient().getClientId());
     * String message = players.getClient().getClientName() + ": " +
     * players.getPoints();
     * sendMessage(null, message + " points");
     */

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
            p.setPoints(0);
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

    public void syncAwayStatus(long clientId) { // EDITED 4/21
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendAwayStatus(clientId);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

    public void syncSpectatorStatus(long clientId) { // EDITED 4/24
        Iterator<ServerPlayer> iter = players.values().stream().iterator();
        while (iter.hasNext()) {
            ServerPlayer client = iter.next();
            boolean success = client.getClient().sendSpectatorStatus(clientId);
            if (!success) {
                handleDisconnect(client);
            }
        }
    }

}