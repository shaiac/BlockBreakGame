package levels;
import backgrounds.BackGround;
import collision.Velocity;
import geometry.Point;
import block.Block;
import sprite.Sprite;
import java.util.ArrayList;
import java.util.List;

/**
 * level data class, create a level with the given info.
 * @author Shai acoca
 */
public class LevelData implements LevelInformation {
    private String levelName;
    private List<Velocity> ballVelocity;
    private String background;
    private int paddleSpeed;
    private int paddleWidth;
    private List<Block> blockList;
    private int numBlocks;

    /**
     * Constructor to the class.
     * @param levelName the level name.
     * @param ballVelocity the balls velocities.
     * @param background the background.
     * @param paddleSpeed the paddle speed.
     * @param paddleWidth the paddle width.
     * @param blockList a blocks list.
     * @param numBlocks the number of blocks that need to be destroyed in order to pass this level.
     */
    public LevelData(String levelName, List<Velocity> ballVelocity, String background, int paddleSpeed,
                     int paddleWidth, List<Block> blockList, int numBlocks) {
        this.levelName = levelName;
        this.ballVelocity = ballVelocity;
        this.background = background;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blockList = blockList;
        this.numBlocks = numBlocks;
    }


    @Override
    public int numberOfBalls() {
        return this.ballVelocity.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return ballVelocity;
    }

    @Override
    public List<Point> ballsCenters() {
        List<Point> centersList = new ArrayList<>();
        for (int i = 0; i < numberOfBalls(); i++) {
            Point center = new Point((double) (400 + i * 10), (double) (400));
            centersList.add(center);
        }
        return centersList;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        BackGround backGround = new BackGround(this.background);
        return backGround.createBackGround();
    }

    @Override
    public List<Block> blocks() {
        return this.blockList;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }
}
