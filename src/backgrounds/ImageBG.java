package backgrounds;
import biuoop.DrawSurface;
import sprite.Sprite;
import java.awt.Image;

/**
 * ImageBG class.
 * @author Shai acoca.
 */
public class ImageBG implements Sprite {
    private Image image;

    /**
     * Constructor to the class.
     * @param image the image of the background.
     */
    public ImageBG(Image image) {
        this.image = image;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, image);
    }

    @Override
    public void timePassed() {
        return;
    }

}
