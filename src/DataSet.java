import java.util.ArrayList;

public class DataSet {
    final private int FPS = 30; //variable constant
    final private double CMPP = 79/207; //29.5 cm / 207 pixels (radius)
    private double centerRegionDistance, wallRegionDistance;
    private ArrayList<Point> points;
    private Point enclosureCenter;

    //all times in seconds
    public DataSet(Point center){
        enclosureCenter = center;
        points = new ArrayList<>();
        centerRegionDistance = 100; //temp value, user prompted
        wallRegionDistance = 205; //temp value, user prompted
    }

    public void add(Point point){
        points.add(point); //adds point every frame
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public Point getLocation(int time){ //time in seconds
        return points.get(time*FPS);
    }

    public double getSpeed(int time){
        int frame = time*FPS;
        Point p1 = points.get(frame);
        Point p2 = points.get(frame + 1);
        return (p2.getY() - p1.getY()) - (p2.getX() - p1.getX());
    }

    public double getAverageSpeed(int t1, int t2){ //t1 < t2, in seconds
        int startFrame = FPS * t1;
        int endFrame = FPS * t2;
        if (points.size() < endFrame) return -1; //invalid time range
        double rateSum = 0; //in pixels/frame
        for (int i = startFrame; i < endFrame - 1; i++){
            double rate = getSpeed(i);
            rateSum += rate;
        }
        double rateAverage = rateSum / (endFrame - startFrame - 1);
        return rateSum * FPS * CMPP; //converts to frames
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

}
