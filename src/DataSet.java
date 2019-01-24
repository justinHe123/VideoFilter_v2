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
    private double pixelsTravelled;

    //all times in seconds
    public DataSet(Point center, int radius, int fps){
        enclosureCenter = center;
        enclosureRadius = radius; //in pixels
        points = new ArrayList<>();
        centerRegion = new ArrayList<>();
        wallRegion = new ArrayList<>();
        this.fps = fps;
        pixelsTravelled = 0;
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
        if (points.size() > 1){
            pixelsTravelled += point.distanceFrom(points.get(points.size() - 2));
        }
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public Point getLocation(double time){ //need to update
        return points.get((int)(time*fps));
    }

    public Point getCurrentLocation(){
        if (points.size() == 0){
            return new Point (0, 0);
        }
        return points.get(points.size() - 1);
    }

    public double getSpeed(int frame){ //in frames, currently code is a disaster DO NOT TOUCH (yet)
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

    public double getAverageSpeed(int startFrame, int endFrame){ //UNKNOWN TERRITORIES (might completely break)
        if (points.size() < endFrame) return -1; //invalid time range
        double rateSum = 0; //in pixels/frame
        for (int i = startFrame; i < endFrame - 1; i++){
            double rate = getSpeed(i);
            rateSum += rate;
        }
        double rateAverage = rateSum / (endFrame - startFrame - 1);
        return rateSum * cmPerPixel; //converts to frames
    }

    public double getDistanceFromWall(){ //currently in pixels
        return enclosureRadius - getDistanceFromCenter();
    }

    public double getDistanceFromCenter(){ //current in pixels
        return getCurrentLocation().distanceFrom(enclosureCenter);
    }

    public double getTimeCloseToCenter(){
        return centerRegion.size() / fps;
    }

    public double getTimeCloseToWall(){
        return wallRegion.size() / fps;
    }

    public double getDistanceTravelled(){
        return pixelsTravelled * cmPerPixel;
    }

    public double getTimeInSpeedBounds(double minSpeed, double maxSpeed){ //fill in later
        return 0;
    }

    public ArrayList<Integer> getTimeNearWall(){ //fill in later
        return null;
    }

    public ArrayList<Integer> getTimeNearCenter(){ //fill in later
        return null;
    }

    public ArrayList<Integer> getTimeAtSpeed(double speed){ //fill in later
        return null;
    }

//    public String toString(){
//        return "\nTIME: " + getCurrentTime() +
//            "\nLOCATION: " + getCurrentLocation().toString() +
//            "\nSPEED: " + Math.round(getCurrentSpeed()) +
//            "\nDISTANCE FROM WALL: " + Math.round(getDistanceFromWall()) +
//            "\nDISTANCE FROM CENTER: " + Math.round(getDistanceFromCenter()) +
//            "\nDISTANCE TRAVELLED: " + Math.round(getDistanceTravelled()) + " CM";
//    }

}
