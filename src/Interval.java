public class Interval {
    int startFrame, endFrame;

    public Interval (int startFrame){
        this.startFrame = startFrame;
    }

    public int getStartFrame(){
        return startFrame;
    }

    public int getEndFrame(){
        return endFrame;
    }

    public void setStartFrame(int startFrame){
        this.startFrame = startFrame;
    }

    public void setEndFrame(int endFrame){
        this.endFrame = endFrame;
    }

    public String toString(){
        return startFrame + "-" + endFrame;
    }
}
