package collision;

import game.GameLevel;
import sprite.Ball;
import block.Block;
import counter.Counter;

/**
 * The collision.BlockRemover class.
 * @author Shai acoca
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor for collision.BlockRemover class.
     * @param game the game.
     * @param removedBlocks how many blocks are in the game.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * @return the game.
     */
    public GameLevel getGame() {
        return this.game;
    }

    /**
     * @return The number of the remaining blocks (counter.Counter).
     */
    public Counter getCounter() {
        return this.remainingBlocks;
    }

    // Blocks that are hit and reach 0 hit-points are removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
            if (beingHit.getHitPoints() == 1) {
                beingHit.removeHitListener(this);
                this.getGame().removeCollidable(beingHit);
                this.getGame().removeSprite(beingHit);
                this.getCounter().decrease(1);
            } else {
                beingHit.decreaseHitPoint();
            }
    }
}
