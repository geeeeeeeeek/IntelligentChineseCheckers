package graphics;

/**
 * Class Timer.
 *
 * @author Tong
 * @version 3.10 2014.1.10
 */
public class Timer {

    private long startingTime;
    private long interruptedTime;
    private boolean paused;

    public Timer() {
        this.startingTime = System.currentTimeMillis();
    }

    public long listen() {
        if (paused)
            return (30000 - interruptedTime + startingTime) / 1000;
        long cur = System.currentTimeMillis();
        return (30000 - cur + startingTime) / 1000;
    }

    public void pause() {
        this.interruptedTime = System.currentTimeMillis();
        this.paused = true;
    }

    public void resume() {
        long currentTime = System.currentTimeMillis();
        this.startingTime += currentTime - interruptedTime;
        this.paused = false;
    }
}
