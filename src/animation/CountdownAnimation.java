package animation;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprite.SpriteCollection;
import java.awt.Color;

/**
 * CountdownAnimation class, one of the Animations.
 * @author Shai acoca
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private long milliSecondPerNumber;

    /**
     * Constructor to the class.
     * @param numOfSeconds is the seconds to count down.
     * @param countFrom countdown from countFrom back to 1.
     * @param gameScreen the game sprite collection.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.milliSecondPerNumber = (long) ((this.numOfSeconds / this.countFrom) * 1000);
    }

    /**
     * Setting the countFrom.
     * @param newCounter the second that left to the count.
     */
    public void setCountFrom(int newCounter) {
        this.countFrom = newCounter;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.RED);
        if (this.countFrom == 0 || this.countFrom == -1) {
            d.drawText((int) (d.getWidth() / 2 - 75), d.getHeight() / 2, "GO!", 100);
        } else {
            d.setColor(Color.black);
            d.drawText((int) (d.getWidth() / 2 - 15), d.getHeight() / 2,
                    Integer.toString((int) this.countFrom), 50);
        }
        long startTime = System.currentTimeMillis();
        Sleeper sleeper = new Sleeper();
        long usedTime = System.currentTimeMillis() - startTime;
        long leftTime = this.milliSecondPerNumber - usedTime;
        if (leftTime > 0) {
            sleeper.sleepFor(leftTime);
        }
        this.setCountFrom(this.countFrom - 1);
    }

    @Override
    public boolean shouldStop() {
        if (this.countFrom >= -1) {
            return false;
        }
        return true;
    }
}
