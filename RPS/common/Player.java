package RPS.common;

public class Player {

    private boolean isReady = false;
    private String choice; //EDITED 3/28
    private boolean isSkip = false; //EDITED 3/31
    private boolean isAway = false; //EDITED 4/21
    private boolean isSpectator; //EDITED 4/24

    public void setAway(boolean isAway){ //EDITED 4/21
         this.isAway = isAway;
    }
    public boolean isAway() { //EDITED 4/24
        return isAway;
    }
    public void setSpectator(boolean isSpectator) { //EDITED 4/24
        this.isSpectator = isSpectator;
    }

    public boolean isSpectator() { //EDITED 4/24
        return isSpectator;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
    public void setSkip(boolean isSkip){ //EDITED 3/31
        this.isSkip = isSkip;
    }
    public boolean isSkip(){//EDITED 3/31
        return this.isSkip;
    }

    public boolean isReady() {
        return this.isReady;
    }
    public void setChoice(String choice) { //EDITED 3/28
        this.choice = choice;
    }
    public  String getChoice(){ //EDITED 3/28
        return choice;
    }
    private boolean isOut = false; //EDITED 3/30

    

    public void setIsOut(boolean isOut) {
        this.isOut = isOut; //EDITED 4/3
    }

    public boolean isOut() {
        return isOut;
    }
    private int points = 0; //EDITED 3/31

    public void setPoints(int points) {
        this.points += points;
    }

    public void changePoints(int points) {
        this.points += points;
        if (this.points < 0) {
            this.points = 0;
        }
    }

    public int getPoints() {
        return points;
    }
    



}