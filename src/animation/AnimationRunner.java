package animation;
import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * AnimationRunner class.
 * @author Shai acoca
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    /**
     * Constructor to the class.
     * @param gui the GUI of the game.
     * @param framesPerSecond the number of frames per second.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Running the given animation until we should stop each animation has its own stop cause.
     * the run method showing the drawsurface on the gui.
     * @param animation the animation the we want to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = this.gui.getDrawSurface();
            animation.doOneFrame(d);
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
