package levels;
import block.BlocksDefinitionReader;
import block.BlocksFromSymbolsFactory;
import collision.Velocity;
import block.Block;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
/**
 * Build level class, building a new level with the given info.
 * @author Shai acoca
 */
public class BuildLevel {
    private List<String> level;

    /**
     * Constructor to the class.
     * @param level A list of the level information.
     */
    public BuildLevel(List<String> level) {
        this.level = level;
    }

    /**
     * The method taking a the list of the strings level info and creating a level information.
     * @return the level information (Level Data).
     */
    public LevelInformation seperateLevelData() {
        List<Block> blockList = new ArrayList<>();
        String levelName = null, backGround = null, blockDefinitions = null;
        List<Velocity> ballsVelocity = null;
        List<String> layout;
        int paddleSpeed = 0, paddleWidth = 0, blockStartX = 0, blockStartY = 0, rowHeight = 0, numBlocks = 0;
        int layoutStartIndex = -1, layoutEndIndex = -1;
        for (int i = 0; i < level.size(); i++) {
            String[] oneData = level.get(i).trim().split(":");
            if (oneData[0].equals("level_name")) {
                levelName = oneData[1].trim();
                continue;
            }
            if (oneData[0].equals("ball_velocities")) {
                ballsVelocity = getBallsVelocity(level.get(i));
                continue;
            }
            if (oneData[0].equals("background")) {
                backGround = oneData[1].trim();
                continue;
            }
            if (oneData[0].equals("paddle_speed")) {
                paddleSpeed = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (oneData[0].equals("paddle_width")) {
                paddleWidth = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (oneData[0].equals("block_definitions")) {
                blockDefinitions = oneData[1].trim();
                continue;
            }
            if (oneData[0].equals("blocks_start_x")) {
                blockStartX = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (oneData[0].equals("blocks_start_y")) {
                blockStartY = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (oneData[0].equals("row_height")) {
                rowHeight = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (oneData[0].equals("num_blocks")) {
                numBlocks = Integer.parseInt(oneData[1].trim());
                continue;
            }
            if (level.get(i).trim().equals("START_BLOCKS")) {
                layoutStartIndex = i + 1;
                continue;
            }
            if (level.get(i).trim().equals("END_BLOCKS")) {
                layoutEndIndex = i - 1;
                continue;
            }
        }
        layout = layout(layoutStartIndex, layoutEndIndex);
        blockList = createBlocks(layout, blockDefinitions, blockStartX, blockStartY, rowHeight);
        return new LevelData(levelName, ballsVelocity, backGround, paddleSpeed, paddleWidth, blockList, numBlocks);
    }

    /**
     * The method gets the start and end index of the layout and returns the layOut.
     * @param layoutStartIndex the layout start index in the list.
     * @param layoutEndIndex the layout end index in the list.
     * @return the layOut.
     */
    private List<String> layout(int layoutStartIndex, int layoutEndIndex) {
        List<String> layout = new ArrayList<>();
        for (int i = layoutStartIndex; i <= layoutEndIndex; i++) {
            layout.add(this.level.get(i));
        }
        return layout;
    }

    /**
     * The method getting a string with all the balls velocities and returning a list of velocities.
     * @param line the String.
     * @return a list of velocities.
     */
    private List<Velocity> getBallsVelocity(String line) {
        List<Velocity> ballsVelocity = new ArrayList<>();
        String[] splitLine = line.split(":")[1].split(" ");
        for (int i = 0; i < splitLine.length; i++) {
            ballsVelocity.add(Velocity.fromAngleAndSpeed(Double.parseDouble(splitLine[i].split(",")[0].trim()),
                    Double.parseDouble(splitLine[i].split(",")[1].trim())));
        }
        return ballsVelocity;
    }

    /**
     * The method create each block in the layout according to the block definitions.
     * @param layout the layout of the level.
     * @param blockDefinitions a string the include the block definition location.
     * @param blockStartX the blocks layout horizontal starting point.
     * @param blockStartY the block layout vertical starting point.
     * @param rowHeight the blocks are arranged in equally-spaced rows.
     * @return A list of blocks of this level.
     */
    public List<Block> createBlocks(List<String> layout, String blockDefinitions, int blockStartX,
                                    int blockStartY, int rowHeight) {
        List<Block> blocksList = new ArrayList<>();
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        BlocksFromSymbolsFactory factory = null;
        Reader reader = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions);
            reader = new InputStreamReader(is);
            factory = blocksDefinitionReader.fromReader(reader);
        } finally {
            try { // try to close the buffer
                reader.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
        String symbol;
        int x = blockStartX;
        int y = blockStartY;
        for (String row : layout) {
            char[] rowChars = row.toCharArray();
            for (int i = 0; i < row.length(); i++) {
                symbol = String.valueOf(rowChars[i]);
                if (factory.isSpaceSymbol(symbol)) {
                    x += factory.getSpaceWidth(symbol);
                } else if (factory.isBlockSymbol(symbol)) {
                    blocksList.add(factory.getBlock(symbol, x, y));
                    x += factory.getBlock(symbol, x, y).getWidth() + 1;
                }
            }
            x = blockStartX;
            y += rowHeight;
        }
        return blocksList;
    }

}
