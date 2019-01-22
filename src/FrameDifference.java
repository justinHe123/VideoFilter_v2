import processing.core.PApplet;
import javax.swing.*;

public class FrameDifference implements PixelFilter {

    private DImage previousImg;
    private final short THRESHOLD = 45;
//    private short threshold;

    public FrameDifference(){
//        this.threshold = Short.parseShort(JOptionPane.showInputDialog("threshold"));
    }

    @Override
    public DImage processImage(DImage img) {
        if (previousImg == null){
            previousImg = new DImage(img);
            return img;
        }
        short[][] current = img.getBWPixelGrid();
        short[][] previous = previousImg.getBWPixelGrid();

        for (int r = 0; r < img.getHeight(); r++){
            for (int c = 0; c < img.getWidth(); c++){
                short difference = (short)Math.abs(current[r][c] - previous[r][c]);
                if (difference > THRESHOLD){
                    current[r][c] = 255;
                } else {
                    current[r][c] = 0;
                }
            }
        }
        previousImg = new DImage(img); // save current frame as previous
        img.setPixels(current); // set pixels and return them
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
    }
}