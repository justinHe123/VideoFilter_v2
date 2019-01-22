import processing.core.PApplet;

public class ColorNoise implements PixelFilter {

    final private double PROB_TO_ADD = 0.01;

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for (int r = 0; r < red.length; r++){
            for (int c = 0; c < red[r].length; c++){
                if (Math.random() < PROB_TO_ADD){
                    red[r][c] = getRandom(256);
                    green[r][c] = getRandom(256);
                    blue[r][c] = getRandom(256);
                }
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }

    private short getRandom(int max) {
        return (short)(Math.random() * max);
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}