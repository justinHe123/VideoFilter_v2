import java.util.*;

public class DateExplore {
    static DataSet dataObj = new DataSet(new Point(308, 234),205, 25);
    static int fps = 25;
    public static void main(String[] args) {
        dataObj.loadDataFromFile("filePath.csv");
        double maxSpeed = dataObj.getMaximumSpeed();
        System.out.println("1: " + dataObj.getDistanceTravelled() +
                "\n2: " + dataObj.getTimeCloseToWall() +
                "\n3: " + dataObj.getTimeCloseToWall() * fps / dataObj.getPoints().size() + "%" +
                "\n4: " + dataObj.getTimeCloseToCenter() +
                "\n5: " + dataObj.getTimeInSpeedBounds(0, 3) +
                "\n6: " + maxSpeed +
                "\n7: " + dataObj.getTimeInSpeedBounds(maxSpeed*0.8, maxSpeed) +
                "\n8: " + test()
        );
    }

    public static double test(){
        ArrayList<Interval> intervals = dataObj.getTimesNearWall();
        double maxTime = 0;
        for (int i = 1; i < intervals.size(); i++){
            int end = intervals.get(i - 1).getEndFrame();
            int start = intervals.get(i).getStartFrame();
            if ((start - end) > maxTime) maxTime = start - end;
        }
        return maxTime / fps;
    }
}
