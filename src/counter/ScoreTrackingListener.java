package counter;

import game.GameLevel;
import sprite.Ball;
import block.Block;
import collision.HitListener;

/**
 * The counter.ScoreTrackingListener class.
 * @author Shai acoca
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    private GameLevel game;

    /**
     * Constructor to the counter.ScoreTrackingListener.
     * @param scoreCounter the beginning score.
     * @param game the game.GameLevel.
     */
    public ScoreTrackingListener(GameLevel game, Counter scoreCounter) {
        this.game = game;
        this.currentScore = scoreCounter;
    }

    /**
     * @return the game.
     */
    public GameLevel getGame() {
        return this.game;
    }


    /**
     * @return current player score.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (this.getGame().getNumOfBlocks().getValue() == 0) {
            this.getCurrentScore().increase(100);
        } else if (beingHit.getBlockText().equals("0")) {
            this.getCurrentScore().increase(10);
        } else if (!beingHit.getBlockText().equals("DeathBlock")) {
            this.getCurrentScore().increase(5);
        }
        this.getGame().updateScore(this.getCurrentScore());
    }
}