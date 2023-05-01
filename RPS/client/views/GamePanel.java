package RPS.client.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import RPS.client.Client;
import RPS.client.IClientEvents;
import RPS.common.Constants;
import RPS.common.Phase;
import RPS.common.TimedEvent;

public class GamePanel extends JPanel implements IClientEvents {

    int numReady = 0;

    private static Logger logger = Logger.getLogger(GamePanel.class.getName());
    GamePanel self;
    JPanel gridLayout;
    JPanel readyCheck;
    JPanel buttonsPanel = new JPanel(new FlowLayout());
    JButton rockButton = new JButton("ROCK (R)");
    JButton paperButton = new JButton("PAPER (P)");
    JButton scissorsButton = new JButton("SCISSORS (S)");
    JButton awayButton = new JButton("AWAY"); // EDITED 4/21
    JButton skipButton = new JButton("SKIP");
    TimedEvent currentTimer; // EDITED 4/18
    Phase currentPhase; // EDITED 4/18
    JLabel timeLabel = new JLabel(""); // EDITED 4/19
    UserListPanel ulp; // EDITED 4/19
    private boolean isSpectator = false; // EDITED 4/27

    public GamePanel() {
        gridLayout = new JPanel();
        buildReadyCheck();
        this.setLayout(new BorderLayout());
        this.add(gridLayout, BorderLayout.CENTER);
        this.add(readyCheck, BorderLayout.SOUTH);
        self = this;
        Client.INSTANCE.addListener(this);
        this.setFocusable(true);
        this.setRequestFocusEnabled(true);
        Dimension td = new Dimension(this.getWidth(), 30); // EDITED 4/19
        timeLabel.setName("time"); // EDITED 4/19
        timeLabel.setMaximumSize(td); // EDITED 4/19
        timeLabel.setPreferredSize(td); // EDITED 4/19
        this.add(timeLabel); // EDITED 4/19

        // JPanel buttonsPanel = new JPanel(new FlowLayout()); //MOVED 4/27

        JButton rockButton = new JButton("ROCK (R)");
        JButton paperButton = new JButton("PAPER (P)");
        JButton scissorsButton = new JButton("SCISSORS (S)");
        JButton awayButton = new JButton("AWAY"); // EDITED 4/21
        JButton skipButton = new JButton("SKIP");
        buttonsPanel.add(rockButton);
        buttonsPanel.add(paperButton);
        buttonsPanel.add(scissorsButton);
        buttonsPanel.add(skipButton);
        buttonsPanel.add(awayButton); // EDITED 4/24
        rockButton.addActionListener((event) -> {
            try {
                Client.INSTANCE.sendChoice("R");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        paperButton.addActionListener((event) -> {
            try {
                Client.INSTANCE.sendChoice("P");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        scissorsButton.addActionListener((event) -> {
            try {
                Client.INSTANCE.sendChoice("S");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        skipButton.addActionListener((event) -> {
            try {
                Client.INSTANCE.sendSkip();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        awayButton.addActionListener((event) -> { // EDITED 4/21
            try {
                Client.INSTANCE.sendAwayStatus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonsPanel.setPreferredSize(new Dimension(200, 70));
        this.add(buttonsPanel, BorderLayout.WEST);

    }

    public void setUserListPanel(UserListPanel ulp) {
        this.ulp = ulp;
    }

    private void buildReadyCheck() {
        if (readyCheck == null) {
            readyCheck = new JPanel();
            readyCheck.setLayout(new BorderLayout());
            JTextField tf = new JTextField(String.format("%s/%s", 0, Constants.MINIMUM_PLAYERS));
            tf.setName("readyText");
            readyCheck.add(tf, BorderLayout.CENTER);
            JButton jb = new JButton("Ready");

            jb.addActionListener((event) -> {
                if (!Client.INSTANCE.isCurrentPhase(Phase.READY)) {
                    return;
                }
                try {
                    Client.INSTANCE.sendReadyStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readyCheck.add(jb, BorderLayout.SOUTH);

            JButton spectatorButton = new JButton(" Spectator Mode");
            spectatorButton.addActionListener((event) -> {
                if (!Client.INSTANCE.isCurrentPhase(Phase.READY)) {
                    return;
                }
                try {
                    Client.INSTANCE.sendSpectatorStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readyCheck.add(spectatorButton, BorderLayout.NORTH);

        }
    }

    // Although we must implement all of these methods, not all of them may be
    // applicable to this panel
    @Override
    public void onClientConnect(long id, String clientName, String message) {

    }

    @Override
    public void onClientDisconnect(long id, String clientName, String message) {

    }

    @Override
    public void onMessageReceive(long id, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReceiveClientId(long id) {

    }

    @Override
    public void onSyncClient(long id, String clientName) {
    }

    @Override
    public void onResetUserList() {
        // players.clear();
    }

    @Override
    public void onReceiveRoomList(String[] rooms, String message) {
        // TODO Auto-generated method stub

    }

    public void onRoomJoin(String roomName) {
        logger.info(
                Constants.ANSI_BRIGHT_BLUE + String.format("Received room name %s", roomName) + Constants.ANSI_RESET);

        if (roomName.equalsIgnoreCase("lobby")) {
            setVisible(false);
        } else {
            setVisible(true);
        }

    }

    @Override
    public void onReceiveReady(long clientId) {
        if (currentTimer == null) {
            currentTimer = new TimedEvent(30, () -> {
                currentTimer = null;
            });
            currentTimer.setTickCallback((time) -> {
                timeLabel.setText("Remaining: " + time);
            });
        }
    }

    @Override
    public void onReceiveReadyCount(long count) { // EDITED 4/18
        logger.info(
                Constants.ANSI_BRIGHT_BLUE + String.format("Received ready count %s", count) + Constants.ANSI_RESET);
        if (currentTimer != null && count == 0) {
            currentTimer.cancel();
            currentTimer = null;
        }
        if (readyCheck != null) {
            for (Component c : readyCheck.getComponents()) {
                if (c.getName().equalsIgnoreCase("readyText")) {
                    ((JTextField) c).setText(String.format("%s/%s", count, Constants.MINIMUM_PLAYERS));
                    break;
                }
            }
        }
        this.validate();
        this.repaint();
    }

    @Override
    public void onReceivePhase(Phase phase) {
        this.setVisible(true);
        logger.info(Constants.ANSI_BRIGHT_BLUE + String.format("Received phase %s", phase) + Constants.ANSI_RESET);
        if (phase == Phase.READY) {
            readyCheck.setVisible(true);
            buttonsPanel.setVisible(false);
        } else if (phase == Phase.PICKING) { // EDITED 4/10
            readyCheck.setVisible(false);
            //buttonsPanel.setVisible(true);

            if (Client.INSTANCE.getIsSpectator()) {
                buttonsPanel.setVisible(false);
                /*rockButton.setVisible(false);
                paperButton.setVisible(false);
                scissorsButton.setVisible(false);
                skipButton.setVisible(false);
                awayButton.setVisible(false);*/
            }else{
                buttonsPanel.setVisible(true);
            }

            /*
             * JPanel buttonsPanel = new JPanel(new FlowLayout());
             * 
             * JButton rockButton = new JButton("ROCK (R)");
             * JButton paperButton = new JButton("PAPER (P)");
             * JButton scissorsButton = new JButton("SCISSORS (S)");
             * JButton awayButton = new JButton("AWAY"); //EDITED 4/21
             * JButton skipButton = new JButton("SKIP");
             * buttonsPanel.add(rockButton);
             * buttonsPanel.add(paperButton);
             * buttonsPanel.add(scissorsButton);
             * buttonsPanel.add(skipButton);
             * buttonsPanel.add(awayButton); // EDITED 4/24
             * rockButton.addActionListener((event) -> {
             * try {
             * Client.INSTANCE.sendChoice("R");
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * });
             * paperButton.addActionListener((event) -> {
             * try {
             * Client.INSTANCE.sendChoice("P");
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * });
             * scissorsButton.addActionListener((event) -> {
             * try {
             * Client.INSTANCE.sendChoice("S");
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * });
             * skipButton.addActionListener((event) -> {
             * try {
             * Client.INSTANCE.sendSkip();
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * });
             * awayButton.addActionListener((event) -> { //EDITED 4/21
             * try {
             * Client.INSTANCE.sendAwayStatus();
             * } catch (IOException e) {
             * e.printStackTrace();
             * }
             * });
             *
             *
             * buttonsPanel.setPreferredSize(new Dimension(200, 70));
             * this.add(buttonsPanel, BorderLayout.WEST);
             */

        }

        this.validate();
        this.repaint();
        logger.info(
                Constants.ANSI_BRIGHT_MAGENTA + String.format("Dimension %s", this.getSize()) + Constants.ANSI_RESET);
    }

    @Override
    public void onReceivePoints(long clientId, int points) {
        if (ulp != null) {
            ulp.setPointsForPlayer(clientId, points);
        }
    }

    @Override
    public void onReceiveAway(long clientId, boolean isAway) { // EDITED 4/24
        logger.info("onReceiveAway triggered for clientId: " + clientId + ", away status: " + isAway);
        ulp.setAwayPlayer(clientId, isAway);
        if (isAway) {
            ulp.setSpectatorPlayer(clientId, true);
            ulp.setSpectatorPlayer(clientId, false);
        }
    }

    @Override
    public void onReceiveOut(long clientId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'onReceiveOut'");
    }

    public void onReceiveSpectator(long clientId, boolean isSpectator) { // EDITED 4/24
        
        logger.info("onReceiveSpectator triggered for clientId: " + clientId + ", isSpectator: " + isSpectator);
        if (ulp != null) {
            ulp.setSpectatorPlayer(clientId, isSpectator);
        }
        
    }
}
