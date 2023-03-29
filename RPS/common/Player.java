package RPS.common;

public class Player {

    private boolean isReady = false;
    private String choice; //EDITED 3/28

    public void setReady(boolean isReady) {
        this.isReady = isReady;
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

}