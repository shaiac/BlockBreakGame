package collision;

import game.GameLevel;
import sprite.Ball;
import block.Block;
import counter.Counter;

/**
 * The collision.BallRemover class.
 * @author Shai acoca
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructor for collision.BallRemover class.
     * @param game the game.
     * @param numOfBalls how many balls are in the game.
     */
    public BallRemover(GameLevel game, Counter numOfBalls) {
        this.game = game;
        this.remainingBalls = numOfBalls;
    }

    /**
     * @return the game.
     */
    public GameLevel getGame() {
        return this.game;
    }

    /**
     * @return The number of the remaining balls (counter.Counter).
     */
    public Counter getCounter() {
        return this.remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        hitter.removeHitListener(this);
        this.getCounter().decrease(1);
        this.getGame().setNumOfBalls(this.getCounter());
    }

}
