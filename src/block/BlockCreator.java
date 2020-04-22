package block;

/**
 * BlockCreator interface.
 * @author Shai acoca
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the x coordinate.
     * @param ypos the y coordinate.
     * @return a block.
     */
    Block create(int xpos, int ypos);
}
