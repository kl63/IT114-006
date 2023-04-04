package RPS.common;

public class PointsPayload extends Payload {// EDITED 3/31
    
    private int points; 

    public void setPoints(int points){
        this.points = points;
    }
    public int getPoints(){
        return points;
    }
}
