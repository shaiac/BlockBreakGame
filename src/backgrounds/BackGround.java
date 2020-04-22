package backgrounds;
import sprite.Sprite;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * BackGround class.
 * @author Shai acoca.
 */
public class BackGround {
    private String backGround;

    /**
     * Constructor to the class.
     * @param backGround a String that indicated the Background.
     */
    public BackGround(String backGround) {
        this.backGround = backGround;
    }

    /**
     * Creating the one of the options of the backgrounds and return it.
     * @return A Background (Color or image).
     */
    public Sprite createBackGround() {
        if (this.backGround.startsWith("color")) {
            ColorsParser colorsParser = new ColorsParser();
            return new ColorBG(colorsParser.colorFromString(this.backGround));
        } else {
            return new ImageBG(getImage());
        }
    }

    /**
     * @return an Image for the background.
     */
    public Image getImage() {
        Image imageBG = null;
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(getImageLocation(this.backGround));
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
}
