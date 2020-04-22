package levels;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The LevelSpecificationReader class, in charge of reading from the level specification.
 * @author Shai acoca
 */
public class LevelSpecificationReader {
    private List<List<String>> levelsInfoStrings;
    private List<LevelInformation> levels;

    /**
     * Contructor to the class.
     */
    public LevelSpecificationReader() {
        levelsInfoStrings = new ArrayList<>();
        levels = new ArrayList<>();
    }


    /**
     * The method creates a list of lists of Strings each string represent a level information.
     * @param reader the IO reader.
     */
    public void readData(java.io.Reader reader) {
        String data;
        List<String> level = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(reader);
        try {
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                //reading an empty line
                if (data.equals("")) {
                    continue;
                }
                if (!(data.matches("END_LEVEL")) && !data.equals("START_LEVEL")) {
                    level.add(data);
                } else if (data.matches("END_LEVEL")) {

                    this.levelsInfoStrings.add(level);
                    level = new ArrayList<>();
                }
            }
        } catch (IOException e) {
            System.err.println("Couldn't read file");
            e.printStackTrace();
        } finally {
            try {
                // try to close the buffer
                buffer.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
    }

    /**
     * The method gets a reader and returns a list of LevelInformation objects.
     * @param reader the IO reader.
     * @return A list of levels information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        this.readData(reader);
        for (int i = 0; i < this.levelsInfoStrings.size(); i++) {
            BuildLevel level = new BuildLevel(levelsInfoStrings.get(i));
            this.levels.add(level.seperateLevelData());
        }
        return this.levels;
    }
}
