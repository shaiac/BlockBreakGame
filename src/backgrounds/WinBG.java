package backgrounds;
import biuoop.DrawSurface;
import counter.Counter;
import sprite.Sprite;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * WinBG calss.
 * @author Shai acoca
 */
public class WinBG implements Sprite {
    private Counter score;
    private Image image;
    private ImageBG backGround;

    /**
     * Constructor to the class.
     * @param score the score in the end of the game.
     */
    public WinBG(Counter score) {
        this.score = score;
        this.image = getImage();
        this.backGround = new ImageBG(image);
    }

    /**
     * @return an Image for the background.
     */
    public Image getImage() {
        String location = "image(background_images/Win.jpg)";
        Image imageBG = null;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(getImageLocation(location));
            imageBG = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBG;
    }

    /**
     * @param str string of the image location.
     * @return The image location extracted from the string.
     */
    public String getImageLocation(String str) {
        String location = str.substring(6, str.length() - 1);
        return location;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle((int) 0, (int) 0, (int) d.getWidth(), (int) d.getHeight());
        backGround.drawOn(d);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 4, d.getHeight() / 2, "Winner! Your score is "
                + Integer.toString(this.score.getValue()), 40);
    }

    @Override
    public void timePassed() {
        return;
    }
}
