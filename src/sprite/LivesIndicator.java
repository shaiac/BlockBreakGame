package sprite;

import java.awt.Color;
import biuoop.DrawSurface;
import counter.Counter;
import geometry.Rectangle;

/**
 * The sprite.LivesIndicator class.
 * @author Shai acoca
 */
public class LivesIndicator implements Sprite {

    private Rectangle rect;
    private java.awt.Color color;
    private Counter lives;

    /**
     * this is the constructor for sprite.LivesIndicator.
     * @param rect - as a rectangle
     * @param color - as a Color
     * @param lives - as the lives counter
     */
    public LivesIndicator(Rectangle rect, Color color, Counter lives) {
        this.rect = rect;
        this.color = color;
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        double x1 = this.rect.getUpperLeft().getX();
        double y1 = this.rect.getUpperLeft().getY();
        double width = this.rect.getWidth();
        double height = this.rect.getHeight();
        int intScore = this.lives.getValue();
        String stringScore = "Lives: " + Integer.toString(intScore);
        surface.drawText((int) (x1 + width / 6 - 20), (int) (y1 + height / 1.5 + 3), stringScore, 13);
    }

    @Override
    public void timePassed() {
        return;
    }
}