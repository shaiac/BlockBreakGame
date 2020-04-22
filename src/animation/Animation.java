package animation;
import biuoop.DrawSurface;

/**
 * A animation.Animation interface.
 * @author Shai acoca
 */
public interface Animation {
    /**
     * is in charge of the logic.
     * @param d the game draw surface.
     */
    void doOneFrame(DrawSurface d);

    /**
     *
     * @return true if the game should stop and false otherwise.
     */
    boolean shouldStop();
}
