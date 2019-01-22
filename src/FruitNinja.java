import processing.core.PApplet;

public class FruitNinja implements PixelFilter {

    private DImage previousImg;
    private final short THRESHOLD = 45;
    private int score = 0;
    private final int DIAMETER = 50;
    private int xCoord = DIAMETER / 2;
    private short[][] differenceGrid;
//    private short threshold;

    public FruitNinja() {
//        this.threshold = Short.parseShort(JOptionPane.showInputDialog("threshold"));
        score = 0;
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

    public void test(DImage previousImg, int xCoord) {

    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

        window.textSize(50);
        window.text("SCORE: " + score, 0, 0);

        window.fill(155, 251, 255, 100);
        int speed = 5;

        int yCoord = DIAMETER / 2;
        xCoord += speed;
        if (xCoord + DIAMETER / 2 > original.getWidth()) {
            xCoord = DIAMETER / 2;
        }

        int numCollisions = 0;
        short[][] coords = differenceGrid;
        for (int y = 0; y < coords.length; y++) {
            for (int x = 0; x < coords[y].length; x++) {
                if (coords[y][x] == 255 && distance(xCoord, yCoord, x, y) < DIAMETER) {
                    numCollisions++;
                }
            }
        }

        double circleArea = DIAMETER * DIAMETER * Math.PI;
        if (numCollisions > circleArea / 8){
            xCoord = DIAMETER / 2;
            score++;
        }

        window.ellipse(xCoord, yCoord, DIAMETER, DIAMETER);
    }

    public double distance(int xCoord, int yCoord, int x, int y) {
        return Math.sqrt((xCoord - x) * (xCoord - x) + (yCoord - y) * (yCoord - y));
    }
}