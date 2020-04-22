package backgrounds;

import biuoop.DrawSurface;
import sprite.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;


/**
 * menu background class.
 *
 * @author Shai acoca
 */
public class MenuBG implements Sprite {
    private Image image;
    private ImageBG backGround;

    /**
     * Constructor to the class.
     */
    public MenuBG() {
        this.image = getImage();
        this.backGround = new ImageBG(image);
    }

    /**
     * @return an Image for the background.
     */
    public Image getImage() {
        String location = "image(background_images/MenuBG.jpg)";
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
        this.backGround.drawOn(d);
    }

    @Override
    public void timePassed() {
        return;
    }
}
