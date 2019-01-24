import processing.core.PApplet;

import javax.swing.*;
import java.util.ArrayList;

public class RandomWalk implements PixelFilter {

    private ArrayList<Point> objects;
    private final int NUM_POINTS = 10;

    public RandomWalk(){
        objects = new ArrayList<>();
        for (int i = 0; i < NUM_POINTS; i++) {
            objects.add(new Point(200, 200));
        }
    }

    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        for (Point p : objects) {
            window.ellipse(p.getX(), p.getY(), 10, 10);
//            p.takeRandomStep();
        }
    }
}