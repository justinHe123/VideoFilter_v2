import processing.core.PApplet;

public class RemoveRed implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] blackPixels = new short[img.getHeight()][img.getWidth()];
        for (int r = 0; r < blackPixels.length; r++){
            for (int c = 0; c < blackPixels[r].length; c++) {
                blackPixels[r][c] = 100;
            }
        }
        img.setRedChannel(blackPixels);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}