package levels;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A LevelSetsReader class.
 * @author Shai acoca
 */
public class LevelSetsReader {

    /**
     * The method read from the level sets file and separate all the data.
     * @param reader the reader for the sets file.
     * @return list of list of strings that has the level sets info.
     */
    public List<List<String>> fromReader(java.io.Reader reader) {
        List<List<String>> allSets = new ArrayList<>();
        String data;
        BufferedReader buffer = new BufferedReader(reader);
        int lineNum = 0;
        List<String> oneSet = new ArrayList<>();
        try {
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                String[] line = null;
                if (lineNum % 2 == 0) {
                    line = data.split(":");
                    oneSet.add(line[0]);
                    oneSet.add(line[1]);
                } else {
                    oneSet.add(data);
                }
                if (oneSet.size() == 3) {
                    allSets.add(oneSet);
                    oneSet = new ArrayList<>();
                }
                lineNum++;
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
        return allSets;
    }
}
