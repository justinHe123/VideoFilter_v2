import java.util.ArrayList;

public class DataSet {
    private int fps = 30; //base
    private double cmPerPixel = 79/207; //79 cm / 207 pixels (radius)
    private double centerRegionDistance, wallRegionDistance;
    private ArrayList<Point> points;
    private Point enclosureCenter;

    //all times in seconds
    public DataSet(Point center, int fps){
        enclosureCenter = center;
        points = new ArrayList<>();
        this.fps = fps;
        centerRegionDistance = 100; //temp value, user prompted
        wallRegionDistance = 205; //temp value, user prompted
    }

    public void setFPS(int fps){
        this.fps = fps;
    }

    public void setCmPerPixel(double cmpp){
        cmPerPixel = cmpp;
    }

    public void add(Point point){
        points.add(point); //adds point every frame
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public Point getLocation(double time){ //time in seconds
        return points.get((int)(time*fps));
    }

    public double getSpeed(double time){
        int frame = (int)(time * fps);
        if (frame > points.size() - 1) return 0;
        Point p1, p2;
        if (time == getCurrentTime()){
            p1 = points.get(frame);
            p2 = points.get(frame - 1);

        } else {
            p1 = points.get(frame);
            p2 = points.get(frame + 1);
        }
        return p1.distanceFrom(p2) * fps;
    }

    public double getCurrentTime(){
        return points.size()/(double)fps;
    }

    public double getCurrentSpeed(){
        return getSpeed(getCurrentTime());
    }

    public double getAverageSpeed(int t1, int t2){ //t1 < t2, in seconds
        int startFrame = fps * t1;
        int endFrame = fps * t2;
        if (points.size() < endFrame) return -1; //invalid time range
        double rateSum = 0; //in pixels/frame
        for (int i = startFrame; i < endFrame - 1; i++){
            double rate = getSpeed(i);
            rateSum += rate;
        }
        double rateAverage = rateSum / (endFrame - startFrame - 1);
        return rateSum * fps * cmPerPixel; //converts to frames
    }

    public double getDistanceFromWall(int time){
        return 0;
    }

    public double getTimeCloseToCenter(){
        return 0;
    }

    public double getTimeCloseToWall(){
        return 0;
    }

    public double getDistanceTravelled(){
        return 0;
    }

    public double getTimeInSpeedBounds(double minSpeed, double maxSpeed){
        return 0;
    }

    public ArrayList<Integer> getTimeCodeNearWall(){
        return null;
    }

    public ArrayList<Integer> getTimeCodeNearCenter(){
        return null;
    }

    public ArrayList<Integer> getTimeCodeAtSpeed(double speed){
        return null;
    }

    public String toString(){
        return "TIME: " + getCurrentTime() + ", SPEED: " + getCurrentSpeed();
    }
}
