package block;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * BlocksDefinitionReader class.
 * @author Shai acoca
 */
public class BlocksDefinitionReader {
    /**
     * This method reads all the block and spaces definitions and return a BlocksFromSymbolsFactory.
     * that has all the information.
     * @param reader as java.io.Reader
     * @return BlocksFromSymbolsFactory
     */
    public BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        TreeMap<String, BlockCreator> blocksMap = new TreeMap<>();
        TreeMap<String, Integer> spacersMap = new TreeMap<>();
        List<String> dataForBlocks = new ArrayList<>();
        List<String> dataForSpacers = new ArrayList<>();
        String data;
        BufferedReader buffer = new BufferedReader(reader);
        try {
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                if (!(data.contains("#") || data.isEmpty())) {
                    if (data.startsWith("default") || data.startsWith("bdef")) {
                        dataForBlocks.add(data);
                    } else {
                        dataForSpacers.add(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { // try to close the buffer
                buffer.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
        dataForBlocks = connectingStrings(dataForBlocks);
        for (int i = 0; i < dataForBlocks.size(); i++) {
            String symbol = null, strokeColor = null;
            int width = 0, height = 0, hitPoints = 0;
            List<String> fill = new ArrayList<>();
            String[] blockDataLine = dataForBlocks.get(i).split(" ");
            for (int j = 1; j < blockDataLine.length; j++) {
                String[] oneData = blockDataLine[j].trim().split(":");
                if (oneData[0].trim().equals("symbol")) {
                    symbol = oneData[1].trim();
                    continue;
                }
                if (oneData[0].trim().equals("width")) {
                    width = Integer.parseInt(oneData[1].trim());
                    continue;
                }
                if (oneData[0].trim().equals("height")) {
                    height = Integer.parseInt(oneData[1].trim());
                    continue;
                }
                if (oneData[0].trim().equals("hit_points")) {
                    hitPoints = Integer.parseInt(oneData[1].trim());
                    continue;
                }
                if (oneData[0].trim().startsWith("fill")) {
                    fill.add(oneData[1].trim());
                    continue;
                }
                if (oneData[0].trim().equals("stroke")) {
                    strokeColor = oneData[1].trim();
                    continue;
                }
            }
            BlockCreator block = new CreateBlock(width, height, hitPoints, fill, strokeColor);
            blocksMap.put(symbol, block);
        }
        String spaceSymbol = null;
        int spaceWidth = 0;
        for (int i = 0; i < dataForSpacers.size(); i++) {
            String[] spaceDataLine = dataForSpacers.get(i).split(" ");
            for (int j = 1; j <= 2; j++) {
                String[] oneData = spaceDataLine[j].trim().split(":");
                if (oneData[0].trim().equals("symbol")) {
                    spaceSymbol = oneData[1].trim();
                }
                if (oneData[0].trim().equals("width")) {
                    spaceWidth = Integer.parseInt(oneData[1].trim());
                }
            }
            spacersMap.put(spaceSymbol, spaceWidth);
        }
        return new BlocksFromSymbolsFactory(spacersMap, blocksMap);
    }

    /**
     * the method adds all the default values of the block to each line of the block definition.
     * @param dataList the data list.
     * @return a new ordered list.
     */
    public static List<String> connectingStrings(List<String> dataList) {
        String completeData;
        if (!dataList.get(0).trim().startsWith("default")) {
            return dataList;
        } else {
            String[] defaultData = dataList.get(0).split(" ");
            for (int i = 1; i < defaultData.length; i++) {
                for (int j = 1; j < dataList.size(); j++) {
                    completeData = dataList.get(j) + " " + defaultData[i].trim();
                    dataList.set(j, completeData);
                }
            }
        }
        return listWithOutDefault(dataList);
    }

    /**
     * @param dataList the data list.
     * @return the data list without the default line.
     */
    public static List<String> listWithOutDefault(List<String> dataList) {
        List<String> newDataList = new ArrayList<>();
        for (int i = 1; i < dataList.size(); i++) {
            newDataList.add(dataList.get(i));
        }
        return newDataList;
    }
}
