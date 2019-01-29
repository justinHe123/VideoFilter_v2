public class Interval {
    int startFrame, endFrame;

    public Interval (int startFrame, int endFrame){
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public String toString(){
        return startFrame + "-" + endFrame;
    }
}
