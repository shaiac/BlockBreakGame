package animation;
import backgrounds.PauseBG;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * PauseScreen class, one of the Animations.
 * @author Shai acoca
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor the pause screen given a key sensor and stop to false.
     * @param k the KeyboardSensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * Drawing the pause screen on the draw surface, if the space key is pressed changing the stop status.
     * @param d the game draw surface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        PauseBG pauseBG = new PauseBG();
        pauseBG.drawOn(d);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
