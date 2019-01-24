import java.util.ArrayList;

public class DataSet {
    private int fps = 25; //base
    private double cmPerPixel = 39.5/207; //79 cm / 207 pixels (radius)
    private double centerRegionDistance, wallRegionDistance;
    private ArrayList<Point> points;
    private Point enclosureCenter;
    private int enclosureRadius;
    private ArrayList<Point> centerRegion;
    private ArrayList<Point> wallRegion;

    //all times in seconds
    public DataSet(Point center, int radius, int fps){
        enclosureCenter = center;
        enclosureRadius = radius; //in pixels
        points = new ArrayList<>();
        centerRegion = new ArrayList<>();
        wallRegion = new ArrayList<>();
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
        if (getDistanceFromCenter() < centerRegionDistance){
            centerRegion.add(point);
        } else if (getDistanceFromCenter() > wallRegionDistance){
            wallRegion.add(point);
        }

    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public Point getLocation(double time){ //time in seconds
        return points.get((int)(time*fps));
    }

    public Point getCurrentLocation(){
        if (points.size() == 0){
            return new Point (0, 0);
        }
        return points.get(points.size() - 1);
    }

    public double getSpeed(int frame){
        if (frame == getCurrentFrame()){
            frame = frame - 1 ;
        }
        if (points.size() < 2 || frame < 1) return 0;

        Point p1, p2;
        p1 = points.get(frame);
        p2 = points.get(frame - 1);

        System.out.println(p1.distanceFrom(p2) * cmPerPixel * fps);
        return p1.distanceFrom(p2) * cmPerPixel * fps;
    }

    public double getCurrentTime(){
        return points.size()/(double)fps;
    }

    public int getCurrentFrame() {
        return points.size();
    }

    public double getCurrentSpeed(){
        return getSpeed(getCurrentFrame());
    }

    public double getAverageSpeed(int startFrame, int endFrame){ //t1 < t2, in seconds
        if (points.size() < endFrame) return -1; //invalid time range
        double rateSum = 0; //in pixels/frame
        for (int i = startFrame; i < endFrame - 1; i++){
            double rate = getSpeed(i);
            rateSum += rate;
        }
        double rateAverage = rateSum / (endFrame - startFrame - 1);
        return rateSum * cmPerPixel; //converts to frames
    }

    public double getDistanceFromWall(){
        return enclosureRadius - getDistanceFromCenter();
    }

    public double getDistanceFromCenter(){
        return getCurrentLocation().distanceFrom(enclosureCenter);
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

    public ArrayList<Integer> getTimeNearWall(){
        return null;
    }

    public ArrayList<Integer> getTimeNearCenter(){
        return null;
    }

    public ArrayList<Integer> getTimeAtSpeed(double speed){
        return null;
    }

//    public String toString(){
//        return "TIME: " + getCurrentTime() +
//                "\nLOCATION: " + getCurrentLocation().toString() +
//                "\nSPEED: " + Math.round(getCurrentSpeed()) +
//                "\nDISTANCE FROM WALL: " + Math.round(getDistanceFromWall());
//    }

}
