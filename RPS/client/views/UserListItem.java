package RPS.client.views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import RPS.client.ClientUtils;
import RPS.common.Constants;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserListItem extends JPanel {
    private static Logger logger = Logger.getLogger(UserListPanel.class.getName());
    private long clientId;
    private String clientName;
    private long points;
    private boolean isAway = false; //EDITED 4/24
    private boolean isSpectator = false; //EDITED 4/24
    JEditorPane text = new JEditorPane("text/plain", "");
    JButton awayButton = new JButton(); //EDITED 4/24
    JButton awayIndicator = new JButton("A"); //EDITED 4/25
    JButton spectatorButton = new JButton(); //EDITED 4/24

    public UserListItem(String clientName, long clientId) {
        this.clientId = clientId;
        this.clientName = clientName;
        awayIndicator.setEnabled(false); //EDITED 4/25
        awayIndicator.setVisible(false); //EDITED 4/25
        awayIndicator.setBackground(Color.YELLOW); //EDITED 4/25
        Dimension d = new Dimension(24, 24); //EDITED 4/25
        awayIndicator.setPreferredSize(d); //EDITED 4/25
        awayIndicator.setMinimumSize(d); //EDITED 4/25
        awayIndicator.setMaximumSize(d); //EDITED 4/25


        text.setEditable(false);
        text.setText(getBaseText());
        this.add(awayIndicator); //EDITED 4/25
        this.add(text);
        ClientUtils.clearBackground(text);
    }

    private String getBaseText() {
        return String.format("%s[%s] Pts.(%s)", clientName, clientId, points);
    }

    public long getClientId() {
        return clientId;
    }
    
    public void setPoints(long points) {
        this.points = points;
        text.setText(getBaseText());
        revalidate(); //EDITED 4/25
        repaint();
    }
    
    public void setAway(long clientId) {
        logger.log(Level.INFO, "FIRST checking clientId " + clientId + ", isAway = " + isAway);
        if (this.clientId == clientId && clientId != Constants.DEFAULT_CLIENT_ID) {
            if (!isAway) { // player is not away, set away and update UI
                isAway = true;
                logger.log(Level.INFO, "SECOND (IF) checking clientId " + clientId + ", isAway = " + isAway);
                awayIndicator.setBackground(Color.YELLOW);
                awayIndicator.setVisible(true);
            } else { // player is already away, set back and update UI
                isAway = false;
                logger.log(Level.INFO, "THIRD (ELSE) checking clientId " + clientId + ", isAway = " + isAway);
                awayIndicator.setBackground(Color.WHITE);
                awayIndicator.setVisible(false);
            }
        }
        revalidate();
        repaint();
    }

    
    public void setSpectator(boolean isSpectator) {  //EDITED 4/24
        this.isSpectator = isSpectator;
        spectatorButton.setText("(Spectator)");
        spectatorButton.setVisible(isSpectator);
    }

    
    
}