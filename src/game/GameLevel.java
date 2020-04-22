package game;


import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.PauseScreen;
import animation.KeyPressStoppableAnimation;
import levels.LevelInformation;
import sprite.LevelName;
import sprite.Sprite;
import block.Block;
import sprite.Paddle;
import sprite.ScoreIndicator;
import sprite.LivesIndicator;
import sprite.Ball;
import sprite.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.GUI;
import collision.BallRemover;
import collision.BlockRemover;
import collision.Collidable;
import counter.Counter;
import counter.ScoreTrackingListener;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;
import java.util.List;

/**
 * The game class that will be in charge of the animation .
 * @author Shai acoca
 */
public class GameLevel implements Animation {
    private SpriteCollection spriteCollection;
    private GameEnvironment gameEnvironment;
    private GUI gui;
    private Counter numOfBlocks;
    private Counter numOfBalls;
    private Counter score;
    private Counter numOfLife;
    private AnimationRunner runner;
    private boolean running;
    private Paddle paddle;
    private biuoop.KeyboardSensor keyboard;
    private LevelInformation levelInfo;

    /**
     * Construct an new empty game.
     */
    public GameLevel() {
        this.spriteCollection = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.numOfBlocks = new Counter(0);
        this.score = new Counter(0);
    }

    /**
     * Construct an new empty game.
     * @param levelInfo the level information (number of balls, blocks, background...) .
     */
    public GameLevel(LevelInformation levelInfo) {
        this.spriteCollection = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.numOfBlocks = new Counter(0);
        this.score = new Counter(0);
        this.levelInfo = levelInfo;
    }

    /**
     * Constructor to the class.
     * @param levelInfo the level information.
     * @param gui the game GUI.
     * @param score the score.
     * @param numOfLife the number of life.
     * @param runner runner for the game.
     */
    public GameLevel(LevelInformation levelInfo, GUI gui, Counter score, Counter numOfLife, AnimationRunner runner) {
        this.spriteCollection = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.gui = gui;
        this.levelInfo = levelInfo;
        this.runner = runner;
        this.numOfLife = numOfLife;
        this.keyboard = gui.getKeyboardSensor();
        this.numOfBlocks = new Counter(0);
        this.score = score;
    }

    /**
     * @return the number of Blocks in the game.
     */
    public Counter getNumOfBlocks() {
        return this.numOfBlocks;
    }

    /**
     * Setting the number of balls in the game.
     * @param numberOfBalls the number of balls that is going to be in the game.
     */
    public void setNumOfBalls(Counter numberOfBalls) {
        this.numOfBalls = numberOfBalls;
    }

    /**
     * Setting a paddle to the game.
     * @param gamePaddle the paddle.
     */
    public void setPaddle(Paddle gamePaddle) {
        this.paddle = gamePaddle;
    }

    /**
     * Setting the number of life that the player will have.
     * @param numberOfLife the number of life.
     */
    public void setnumOfLife(Counter numberOfLife) {
        this.numOfLife = numberOfLife;
    }

    /**
     * @return the number of life that remain.
     */
    public Counter getnumOfLife() {
        return this.numOfLife;
    }

    /**
     * Setting the GUI to the game.
     * @param gameGUI the GUI.
     */
    public void setGameGUI(GUI gameGUI) {
        this.gui = gameGUI;
    }

    /**
     * @return the GUI.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Updating the score of the player in the game.
     * @param newScore the new score.
     */
    public void updateScore(Counter newScore) {
        this.score = newScore;
    }

    /**
     * Adding one collidable to the collection (list).
     * @param c one collidable.
     */
    public void addCollidable(Collidable c) {
        this.gameEnvironment.addCollidable(c);
    }

    /**
     * Adding one sprite to the collection (list).
     * @param s one sprite.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.addSprite(s);
    }

    /**
     * Initialize a game: creating the Blocks, game frame, life, score.
     */
    public void initialize() {
        this.spriteCollection.addSprite(this.levelInfo.getBackground());
        initializeBlocks();
        addRectangleFrame();
        initializeScoreFrame();
        initializeLifeFrame();
        LevelName levelName = new LevelName(this.levelInfo.levelName());
        this.spriteCollection.addSprite(levelName);
    }

    /**
     * Initializing the score frame of the game.
     */
    public void initializeScoreFrame() {
        int surfaceWidth = this.getGui().getDrawSurface().getWidth();
        Point upperLeft = new Point(0, 0);
        Rectangle frame = new Rectangle(upperLeft, surfaceWidth, 20);
        ScoreIndicator scoreFrame = new ScoreIndicator(frame, Color.lightGray, this.score);
        this.spriteCollection.addSprite(scoreFrame);
    }

    /**
     * Initializing the life frame of the game.
     */
    public void initializeLifeFrame() {
        Point upperLeft = new Point(0, 0);
        int surfaceWidth = this.getGui().getDrawSurface().getWidth();
        Rectangle frame = new Rectangle(upperLeft, surfaceWidth, 20);
        LivesIndicator lives = new LivesIndicator(frame, Color.black, this.getnumOfLife());
        this.spriteCollection.addSprite(lives);
    }

    /**
     * initializing the balls in the game.
     */
    public void initializeBalls() {
        int numberOfBalls = this.levelInfo.numberOfBalls();
        Point center;
        BallRemover removerBall = new BallRemover(this, new Counter(numberOfBalls));
        setNumOfBalls(new Counter(numberOfBalls));
        List<Point> centersList = this.levelInfo.ballsCenters();
        for (int i = 0; i < numberOfBalls; i++) {
            center = centersList.get(i);
            sprite.Ball ball = new Ball(center.getX(), center.getY(),
                    5, Color.BLACK, this.gameEnvironment);
            ball.setVelocity(this.levelInfo.initialBallVelocities().get(i));
            ball.setGui(this.getGui());
            ball.addHitListener(removerBall);
            ball.addToGame(this);
        }
    }

    /**
     * initializing the Blocks in the game.
     */
    public void initializeBlocks() {
        int surfaceWidth = this.getGui().getDrawSurface().getWidth();
        this.numOfBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());
        BlockRemover removerB = new BlockRemover(this, numOfBlocks);
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this, this.score);
        for (Block block : this.levelInfo.blocks()) {
            block.addHitListener(removerB);
            block.setScoreTracking(scoreTracking);
            block.addToGame(this);
        }
    }

    /**
     * initializing the sprite.Paddle in the game.
     * @return the paddle.
     */
    public Paddle initializePaddle() {
        int surfaceHeight = this.getGui().getDrawSurface().getHeight();
        int surfaceWidth = this.getGui().getDrawSurface().getWidth();
        int paddleWidth = this.levelInfo.paddleWidth();
        Point upperLeftPaddle = new Point((double) (surfaceWidth / 2 - paddleWidth / 2), (double) (surfaceHeight - 25));
        Paddle gPaddle = new Paddle(upperLeftPaddle, paddleWidth, 20, gui, this.levelInfo.paddleSpeed());
        this.setPaddle(gPaddle);
        paddle.addToGame(this);
        return gPaddle;
    }


    /**
     * Adding 4 blocks to be the game frames, one of them in the bottom of the screen will be the "Death block.Block".
     */
    public void addRectangleFrame() {
        int surfaceHeight = this.getGui().getDrawSurface().getHeight();
        int surfaceWidth = this.getGui().getDrawSurface().getWidth();
        Point leftFramePt = new Point(0, 20);
        Block leftFrame = new Block(leftFramePt, 20, surfaceHeight - 20, Color.gray);
        leftFrame.setBlockText("X");
        leftFrame.addToGame(this);
        Point topFramePt = new Point(0, 20);
        Block topFrame = new Block(topFramePt, surfaceWidth, 20, Color.gray);
        topFrame.setBlockText("X");
        topFrame.addToGame(this);
        Point rightFramePt = new Point(surfaceWidth - 20, 20);
        Block rightFrame = new Block(rightFramePt, 20, surfaceHeight - 20,
                Color.gray);
        rightFrame.setBlockText("X");
        rightFrame.addToGame(this);
        Point bottomFramePt = new Point(20, surfaceHeight);
        Block deathBlock = new Block(bottomFramePt, surfaceWidth - 40,
                20, Color.gray);
        deathBlock.setBlockText("DeathBlock");
        deathBlock.addToGame(this);
    }

    /**
     * Run one turn - start the animation loop, initializing the ball and paddle.
     * in charge of drawing all the sprites, and to notify them that time has passed.
     * The turn ends when there are no more balls in the game or no more blocks (in that case ending the game).
     */
    public void playOneTurn() {
        this.running = true;
        this.initializeBalls();
        this.initializePaddle();
        this.runner.run(new CountdownAnimation(3, 3, this.spriteCollection));
        this.runner.run(this);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.numOfBlocks.getValue() == 0) {
            this.running = false;
        } else if (this.numOfBalls.getValue() == 0) {
            this.getnumOfLife().decrease(1);
            paddle.removeFromGame(this);
            this.running = false;
        }
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            PauseScreen pauseS = new PauseScreen(this.keyboard);
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", pauseS));
        }
        this.spriteCollection.notifyAllTimePassed();
        this.spriteCollection.drawAllOn(d);
    }

    @Override
    public boolean shouldStop() {
        return !(this.running);
    }


    /**
     * Removing one collidable from the list.
     * @param c the collidable that we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.gameEnvironment.getGameEnv().remove(c);
    }

    /**
     * Removing one sprite from the list.
     * @param s the sprite that we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.spriteCollection.getSpriteCollection().remove(s);
    }
}
