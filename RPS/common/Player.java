package RPS.common;

public class Player {

    private boolean isReady = false;
    private String choice; //EDITED 3/28
    private boolean isSkip = false; //EDITED 3/31

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
        this.isOut = isOut; //EDITEd 4/3
    }

    public boolean isOut() {
        return isOut;
    }
    private int points = 0; //EDITED 3/31

    public void setPoints(int points) {
        this.points = points;
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