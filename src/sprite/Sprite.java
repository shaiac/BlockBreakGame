package sprite;

import biuoop.DrawSurface;

/**
 * A sprite.Sprite interface.
 * @author Shai acoca
 */
public interface Sprite {
    /**
     * @param d Drawsurface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}
