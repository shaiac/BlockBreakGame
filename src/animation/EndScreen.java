package animation;
import backgrounds.LoseBG;
import backgrounds.WinBG;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import counter.Counter;

/**
 * EndScreen class, saying if yhe player lose or win.
 * @author Shai acoca
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private String status;
    private Counter score;

    /**
     * Constructor the EndGame screen.
     * @param k the KeyboardSensor
     * @param status Win/Lose.
     * @param score the score in the end of the game.
     */
    public EndScreen(KeyboardSensor k, String status, Counter score) {
        this.keyboard = k;
        this.stop = false;
        this.status = status;
        this.score = score;
    }

    /**
     * Drawing the end screen on the draw surface, if the space key is pressed changing the stop status.
     * @param d the game draw surface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        LoseBG loseBG = new LoseBG(this.score);
        WinBG winBG = new WinBG(this.score);
        if (this.status.equals("win")) {
            winBG.drawOn(d);
        } else {
            loseBG.drawOn(d);
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
