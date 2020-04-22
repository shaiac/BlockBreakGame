package levels;
import collision.Velocity;
import geometry.Point;
import block.Block;
import sprite.Sprite;
import java.util.List;

/**
 * A LevelInformation interface.
 * interface specifies the information required to fully describe a level.
 * @author Shai acoca
 */
public interface LevelInformation {
    /**
     * @return the number of balls.
     */
    int numberOfBalls();

    /**
     * @return list of the initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return the paddle speed.
     */
    int paddleSpeed();

    /**
     * @return the paddle width.
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     * @return the level name.
     */
    String levelName();

    /**
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * @return The Blocks that make up this level, each block contains its size, color and location.
     */
    List<Block> blocks();

    /**
     * @return Number of blocks that should be removed before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();

    /**
     * @return List of the centers of the balls in the game.
     */
    List<Point> ballsCenters();
}
