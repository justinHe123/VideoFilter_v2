public class Point {

    private int x, y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public double distanceFrom(Point point){
        return Math.sqrt((point.getX() - x)*(point.getX() - x) + (point.getY() - y)*(point.getY() - y));
    }

    public boolean equals(Point point){
        return x == point.getX() && y == point.getY();
    }

    public String toString(){
        return x + ", " + y;
    }

//    public void takeRandomStep() {
//        int move = (int)(Math.random() * 4);
//        if (move == 0){
//            x += 3;
//        } else if (move == 1){
//            x -= 3;
//        } else if (move == 2){
//            y += 3;
//        } else {
//            y -= 3;
//        }
//    }
}
