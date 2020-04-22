package backgrounds;

import biuoop.DrawSurface;
import sprite.Sprite;
import java.awt.Color;

/**
 * ColorBG class.
 * @author Shai acoca.
 */
public class ColorBG implements Sprite {
    private Color color;

    /**
     * Constructor to the class.
     * @param color the color of the background.
     */
    public ColorBG(Color color) {
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();
        d.setColor(this.color);
        d.fillRectangle((int) 0, (int) 0, (int) width, (int) height);
    }

    @Override
    public void timePassed() {
        return;
    }
}
