package sprite;

import java.awt.Color;
import biuoop.DrawSurface;

/**
 * The LevelName class.
 * @author Shai acoca
 */
public class LevelName implements Sprite {
    private String levelName;

    /**
     * Constructor to the class.
     * @param levelName the level name;
     */
    public LevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        //d.drawText((int) (d.getWidth() / 2 - 100), 80, "Level Name:" + this.levelName, 20);
        d.drawText((int) (d.getWidth() - 270), (int) (20 / 1.5 + 3), "Level Name:" + this.levelName, 13);
    }

    @Override
    public void timePassed() {
        return;
    }



}