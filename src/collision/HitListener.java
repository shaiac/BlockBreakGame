package collision;

import sprite.Ball;
import block.Block;

/**
 * A collision.HitListener interface.
 * @author Shai acoca
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the block that being hit.
     * @param hitter the Sprites.Ball that's doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}
