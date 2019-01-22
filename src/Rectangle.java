import processing.core.PApplet;

import java.awt.*;

public class Rectangle implements PixelFilter {

    private DImage previousImg;
    private final short THRESHOLD = 45;
    private short[][] differenceGrid;
//    private short threshold;

    public Rectangle() {
//        this.threshold = Short.parseShort(JOptionPane.showInputDialog("threshold"));
        differenceGrid = new short[0][0];
    }

    @Override
    public DImage processImage(DImage img) {
        if (previousImg == null) {
            previousImg = new DImage(img);
            return img;
        }
        short[][] current = img.getBWPixelGrid();
        short[][] previous = previousImg.getBWPixelGrid();

        for (int r = 0; r < img.getHeight(); r++) {
            for (int c = 0; c < img.getWidth(); c++) {
                short difference = (short) Math.abs(current[r][c] - previous[r][c]);
                if (difference > THRESHOLD) {
                    current[r][c] = 255;
                } else {
                    current[r][c] = 0;
                }
            }
        }


        previousImg = new DImage(img); // save current frame as previous
//        img.setPixels(current); // set pixels and return them
        differenceGrid = current;
        return img;
    }


    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        int x1 = 0, y1 = 0, x2 = 200, y2 = 200, dx = 100;
        double slope = slope(x1, y1, x2, y2);
        double perpSlope = -1 / slope;
        window.line(x1, y1, x2, y2);
        window.line(x2, y2, x2 + dx, (float)(y2 + perpSlope*dx));
        window.line(x1, y1, x1 + dx, (float)(y1 + perpSlope*dx));
        window.line(x1 + dx, (float)(y1 + perpSlope*dx), x2 + dx, (float)(y2 + perpSlope*dx));
    }

    public double distance(int xCoord, int yCoord, int x, int y) {
        return Math.sqrt((xCoord - x) * (xCoord - x) + (yCoord - y) * (yCoord - y));
    }

    public double slope(int x1, int y1, int x2, int y2){
        return (double)(y2 - y1) / (x2 - x1);
    }
}