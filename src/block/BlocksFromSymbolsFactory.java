package block;

import java.util.Map;

/**
 * BlocksFromSymbolsFactory class.
 * @author Shai acoca
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor to the class.
     * @param spacerWidths spacerWidths map.
     * @param blockCreators blockCreators map.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * @param s a space symbol.
     * @return true if 's' is a valid space symbol
     */
    public boolean isSpaceSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * @param s a block symbol.
     * @return true true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * This method return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     * @param s the symbol.
     * @param xpos the x coordinate.
     * @param ypos the y coordinate.
     * @return a block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * @param s a space symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

}
