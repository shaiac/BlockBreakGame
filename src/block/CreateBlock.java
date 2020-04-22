package block;
import geometry.Point;
import geometry.Rectangle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.List;

/**
 * Create block class.
 * @author Shai acoca
 */

public class CreateBlock implements BlockCreator {
    private int width;
    private int height;
    private int hitPoints;
    private String stroke;
    private List<String> fill;

    /**
     * Constructor to the class.
     * @param width the block width.
     * @param height the block height.
     * @param hitPoints number of hit-points of the block.
     * @param fill the background.
     * @param stroke The blocks border.
     */
    public CreateBlock(int width, int height, int hitPoints, List<String> fill, String stroke) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.fill = fill;
        this.stroke = stroke;
    }

    /**
     * @return the width.
     */
    public int getWidth() {
        return this.width;
    }

    @Override
    public Block create(int xpos, int ypos) {
        //one or more of the parameters are missing.
        if (this.width == 0 || this.height == 0 || this.hitPoints == 0 || this.fill == null) {
            messageToUser();
        }
        Rectangle rect = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        Block block = new Block(rect, this.hitPoints, this.fill, this.stroke);
        //Setting the images map for each block.
        block.setImages();
        return block;
    }

    /**
     * this message opens an eror message to the user and close the project.
     */
    public void messageToUser() {
        // show a message dialog - the message is an error message.
        JOptionPane.showMessageDialog(new JFrame(),
                "You are missing some parameters in the block definitions, Please check it", "Error!",
                JOptionPane.ERROR_MESSAGE);
        // exit the program
        System.exit(0);
    }
}
