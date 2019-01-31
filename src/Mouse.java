import processing.core.PApplet;
import java.util.ArrayList;

public class Mouse implements PixelFilter {
    final private short[][] kernel = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };
    final private int kernelWeight = 16;
    final private Point CENTER = new Point(308, 234);
    final private double ENCLOSURE_RADIUS = 205;
    private DataSet dataObj;
    private Point currentCenter;
    private int numFrames;

    public Mouse(){
        dataObj = new DataSet(CENTER, 205, 25);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        threshold1(pixels, 100);
        pixels = blur(pixels);
        cheapHack(pixels);
        currentCenter = calculateMouseCenter(pixels);
        dataObj.add(currentCenter);

        numFrames++;
        if (numFrames == 9994) dataObj.saveDataToFile("filePath.csv") ;

        img.setPixels(pixels);
        return img;
    }

    public Point calculateMouseCenter(short[][] pixels) {
        int count = 0;
        int sumR = 0;
        int sumC = 0;
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                if (pixels[r][c] == 255){
                    sumR += r;
                    sumC += c;
                    count++;
                }
            }
        }
        int avgR = sumR / count;
        int avgC = sumC / count;
        return new Point(avgC, avgR);
    }

    public short[][] blur(short[][] pixels) {
        short[][] img = new short[pixels.length][pixels[0].length];
        for (int r = 1; r < pixels.length - 1; r++){
            for (int c = 1; c < pixels[r].length - 1; c++){

                short output = 0;
                for (int dr = -1; dr <= 1; dr++){
                    for (int dc = -1; dc <= 1; dc++){
                        short kernelVal = kernel[dr + 1][dc + 1];
                        short pixelVal = pixels[r + dr][c + dc];
                        output += kernelVal*pixelVal;
                    }
                }
                output = (short)(output / kernelWeight);
                if (output < 0) output = 0;
                if (output > 255) output = 255;
                img[r][c] = output;
            }
        }
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.fill(254);
        window.ellipse(CENTER.getX(), CENTER.getY(), 5, 5);
        ArrayList<Point> points = dataObj.getPoints();

        for (int i = 1; i < points.size(); i++){
            window.stroke(255, 0, 0);
            window.line(points.get(i).getX(), points.get(i).getY(), points.get(i-1).getX(), points.get(i-1).getY());
            window.stroke(0, 0, 0);
        }

        if (currentCenter != null){
            window.fill(255, 0, 0);
            window.ellipse(currentCenter.getX(), currentCenter.getY(), 5, 5);
        }

        window.textSize(20);
        window.fill(255, 255, 255);
        window.text(toString(dataObj), 0, 0);
    }

    public void threshold1(short[][] pixels, int threshold){
        for (int r = 0; r < pixels.length; r++){
            for (int c = 0; c < pixels[r].length; c++){
                if (pixels[r][c] > threshold){
                    pixels[r][c] = 0;
                } else {
                    pixels[r][c] = 255;
                }
            }
        }
    }

    public void threshold2(short[][] pixels, int threshold){
        for (int r = 0; r < pixels.length; r++){
            for (int c = 0; c < pixels[r].length; c++){
                if (pixels[r][c] < threshold){
                    pixels[r][c] = 0;
                } else {
                    pixels[r][c] = 255;
                }
            }
        }
    }

    //(308, 234) center, (517, 234) radius 205
    public void cheapHack(short[][] pixels){
        for (int r = 0; r < pixels.length; r++){
            for (int c = 0; c < pixels[r].length; c++){
                if (distance(c, r, CENTER.getX(), CENTER.getY()) > ENCLOSURE_RADIUS){
                    pixels[r][c] = 0;
                }
            }
        }
    }

    public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public String toString(DataSet dataObj){
        return "\nTIME: " + dataObj.getCurrentTime() +
                "\nFRAMES: " + dataObj.getPoints().size() +
                "\nLOCATION: " + dataObj.getCurrentLocation().toString() +
                "\nSPEED: " + Math.round(dataObj.getCurrentSpeed()) + "CM/S" +
                "\nDISTANCE FROM WALL: " + Math.round(dataObj.getDistanceFromWall()) + "CM" +
                "\nDISTANCE FROM CENTER: " + Math.round(dataObj.getDistanceFromCenter()) + "CM" +
                "\nDISTANCE TRAVELLED: " + Math.round(dataObj.getDistanceTravelled()) + " CM" +
                "\nTIMECODE NEAR WALL: " + dataObj.getTimeCodeNearWall().toString() +
                "\nTIMECODE NEAR CENTER: " + dataObj.getTimeCodeNearCenter().toString();
    }
}