public class TimeCode {
    private int hours, minutes, seconds, frames;
    private int fps;
    public TimeCode(int numFrames, int fps){
        this.fps = fps;
        frames = numFrames % 25;
        seconds = (numFrames / fps) % 60;
        minutes = (numFrames / fps / 60) % 60;
        hours = (numFrames / fps / 3600) % 60;

    }

    public String toString() {
        return format(hours) + ":" + format(minutes) + ":" + format(seconds) + ":" + format(frames);
    }

    private String format(int n){
        String value = Integer.toString(n);
        if (value.length() == 1){
            return "0" + value;
        } else {
            return value;
        }
    }

}
