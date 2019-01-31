import java.io.*;
import java.util.*;

public class DataSet {
    private int fps = 25; //base
    private double cmPerPixel = 39.5/207;
    private double centerRegionDistance, wallRegionDistance;
    private Point enclosureCenter;
    private int enclosureRadius;
    private ArrayList<Point> points;
    private ArrayList<Integer> framesNearWall, framesNearCenter;
    private double pixelsTravelled;

    public DataSet(Point center, int radius, int fps){
        enclosureCenter = center;
        enclosureRadius = radius; //in pixels
        points = new ArrayList<>();
        framesNearCenter = new ArrayList<>();
        framesNearWall = new ArrayList<>();
        this.fps = fps;
        pixelsTravelled = 0;
        centerRegionDistance = 10.0; //in cm
        wallRegionDistance = 10.0; //in cm
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
            framesNearCenter.add(points.size());
        }
        else if (getDistanceFromWall() < wallRegionDistance){
            framesNearWall.add(points.size());
        }

        if (points.size() > 1){
            pixelsTravelled += point.distanceFrom(points.get(points.size() - 2));
        }
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public Point getLocation(int frame){
        if (frame >= points.size() || frame < 0) return new Point (0, 0);
        return points.get(frame);
    }

    public Point getCurrentLocation(){
        return getLocation(points.size() - 1);
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

//    public double getCurrentSpeed(){
//        return getAverageSpeed(getCurrentFrame() - 5, getCurrentFrame());
//    }

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

    public double getAverageSpeed(int startFrame, int endFrame){ //UNKNOWN TERRITORIES (might completely break)
        if (endFrame > points.size() || startFrame < 0) return -1; //invalid time range
        double rateSum = 0; //in pixels/frame
        for (int i = startFrame; i < endFrame - 1; i++){
            double rate = getSpeed(i);
            rateSum += rate;
        }
        int numFrames = (endFrame - startFrame - 1);
        double rateAverage = rateSum / numFrames;
        return rateAverage; // converts to cm
    }

    public double getDistanceFromWall(){
        return (enclosureRadius - getCurrentLocation().distanceFrom(enclosureCenter)) * cmPerPixel;
    }

    public double getDistanceFromCenter(){ //current in pixels
        return (getCurrentLocation().distanceFrom(enclosureCenter)) * cmPerPixel;
    }

    public double getTimeCloseToCenter(){
        return framesNearCenter.size() / fps;
    }

    public double getTimeCloseToWall(){
        return framesNearWall.size() / fps;
    }

    public double getDistanceTravelled(){
        return pixelsTravelled * cmPerPixel;
    }

    public double getTimeInSpeedBounds(double minSpeed, double maxSpeed){ //fill in later
        return 0;
    }

    public TimeCode getTimeCodeNearWall(){
        return new TimeCode(framesNearWall.size(), fps);
    }

    public TimeCode getTimeCodeNearCenter(){
        return new TimeCode(framesNearCenter.size(), fps);
    }

    public ArrayList<Integer> getTimeAtSpeed(double speed){ //fill in later
        return null;
    }

    private void writeDataToFile(String filePath, String data){
        File outFile = new File(filePath);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            writer.write(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private String readFileAsString (String filepath){
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + System.getProperty("line.separator"));

            }
        } catch (IOException e){
                e.printStackTrace();
        }
        return output.toString();
    }

    private ArrayList<Interval> getTimesInRoI(ArrayList<Integer> frames){
        ArrayList<Interval> timesNearRoI = new ArrayList<>();
        Interval interval = new Interval(frames.get(0));
        int prevValue = interval.getStartFrame() - 1;
        for (int frame : frames){
            if (prevValue + 1 != frame){
                interval.setEndFrame(prevValue);
                timesNearRoI.add(interval);
                interval = new Interval(frame);
            }
            prevValue = frame;
        }
        interval.setEndFrame(prevValue);
        timesNearRoI.add(interval);
        return timesNearRoI;
    }

    public ArrayList<Interval> getTimesNearWall(){ //assumes there are values in framesNearWall
        return getTimesInRoI(framesNearWall);
    }

    public ArrayList<Interval> getTimesNearCenter(){
        return getTimesInRoI(framesNearCenter);
    }

}
