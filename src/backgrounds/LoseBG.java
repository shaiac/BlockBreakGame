package backgrounds;
import biuoop.DrawSurface;
import counter.Counter;
import sprite.Sprite;
import java.awt.Color;
import java.util.Random;

/**
 * LoseBG calss.
 * @author Shai acoca
 */
public class LoseBG implements Sprite {
    private Counter score;

    /**
     * Constructor to the class.
     * @param score the score in the end of the game.
     */
    public LoseBG(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();
        d.setColor(Color.black);
        d.fillRectangle((int) 0, (int) 0, (int) width, (int) height);
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 4, 50, "Game Over! Your score is "
                + Integer.toString(this.score.getValue()), 32);
        d.drawText(d.getWidth() / 4 + 80, 100, "LOSER!!!!!!" , 40);
        d.setColor(Color.blue);
        // drawing the circles (faces, eyes).
        d.drawCircle(width - width / 3, (int) (height * 0.3 + height / 30), 40);
        d.drawCircle(width - width / 3 - 17, (int) (height * 0.3 + height / 30 - 3), 3);
        d.drawCircle(width - width / 3 + 17, (int) (height * 0.3 + height / 30 - 3), 3);
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            d.drawLine(width - width / 3 - 17 + i / 10, (int) (height * 0.3 + height / 30 - 3),
                    545 + i, 300 + rand.nextInt(270));
            d.drawLine(width - width / 3 + 17 + i / 10, (int) (height * 0.3 + height / 30 - 3),
                    500 + i, 300 + rand.nextInt(270));
        }
        d.drawCircle(width / 3, (int) (height * 0.3 + height / 30), 40);
        d.drawCircle(width / 3 - 17, (int) (height * 0.3 + height / 30 - 3), 3);
        d.drawCircle(width / 3 + 17, (int) (height * 0.3 + height / 30 - 3), 3);
        Random rand1 = new Random();
        for (int i = 0; i < 10; i++) {
            d.drawLine(width / 3 - 17 + i / 10, (int) (height * 0.3 + height / 30 - 3), 545 + i,
                    470 + rand1.nextInt(270));
            d.drawLine(width / 3 + 17 + i / 10, (int) (height * 0.3 + height / 30 - 3),
                    90 + i, 395 + rand1.nextInt(270));
        }
    }

    @Override
    public void timePassed() {
        return;
    }
}
