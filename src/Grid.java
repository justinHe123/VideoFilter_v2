import processing.core.PApplet;
import javax.swing.*;

public class Grid implements PixelFilter {

    private int n;

    public Grid(){
        n = Integer.parseInt(JOptionPane.showInputDialog("hOw mnay grid?"));
    }

    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

        int widthIncrement = original.getWidth()/n;
        int heightIncrement = original.getHeight()/n;
        for (int i = 0; i < n; i++){
            window.strokeWeight(3);
            int x = widthIncrement * i;
            window.line(x, 0, x, original.getHeight());
            int y = heightIncrement * i;
            window.line(0, y, original.getWidth(), y);
        }
    }
}