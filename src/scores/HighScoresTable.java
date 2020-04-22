package scores;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * High Scores table class.
 * @author Shai acoca
 */
public class HighScoresTable implements java.io.Serializable {
    private static final long serialVersionUID = -3854765650600616652L;
    private List<ScoreInfo> scoreList;
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     * @param size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.scoreList = new ArrayList<>();
        this.size = size;
    }
    /**
     * @return the score list.
     */
    public List<ScoreInfo> getScoreList() {
        return this.scoreList;
    }

    /**
     * Add a high-score.
     * @param score the score.
     */
    public void add(ScoreInfo score) {
        if (this.getScoreList().isEmpty() || this.getScoreList().size() < this.size()) {
            this.scoreList.add(score);
            //Checking if the score is higher then the lower score in the list.
        } else if (getHighScores().get(this.size() - 1).getScore() < score.getScore()) {
            this.getScoreList().remove(this.size() - 1);
            this.getScoreList().add(score);
        }
        this.sortList(this.getScoreList());
    }

    /**
     * @return table size.
     */
     public int size() {
        return this.size;
     }

    /**
     * @return the scores list sorted such that the highest scores come first.
     */
     public List<ScoreInfo> getHighScores() {
         this.sortList(this.scoreList);
         return this.scoreList;
     }

    /**
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * @param score a score
     * @return the rank of the current score: where will it be on the list if added?
     */
     public int getRank(int score) {
         int rank = 1;
         for (ScoreInfo scores : this.getHighScores()) {
             if (score <= scores.getScore()) {
                 rank++;
             }
         }
         return rank;
     }

    /**
     * Clears the table.
     */
     public void clear() {
         this.getScoreList().clear();
     }

    /**
     * this methood load table data from file. the current table data is
     * cleared.
     * @param filename - as the File name
     * @throws IOException - an exception
     * @throws ClassNotFoundException - an exception
     */
    public void load(File filename) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = null;
        // trying to reas an ObjectInputStream.
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            objectInputStream.readObject();
            // Can't find file to open
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename);
            return;
            // Some other problem
        } catch (IOException e) {
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return;
        } finally {
            try {
                if (objectInputStream != null) {
                    // close it
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * This method saves table data to the specified file.
     * @param filename - as the File name
     * @throws IOException - as an exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        // trying to write to ObjectOutputStream
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } finally {
            try {
                if (objectOutputStream != null) {
                    // close it
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * this method reads a table from file and returns it. if the file doesn't
     * exist, or there is a problem with reading it , an empty table is
     * returned.
     * @param filename - as the File name
     * @return HighScoresTable
     * @throws IOException - as an exception.
     */
    public static HighScoresTable loadFromFile(File filename) throws IOException {
        ObjectInputStream objectIS = null;
        // tring to read the object
        try {
            objectIS = new ObjectInputStream(new FileInputStream(filename));
            return (HighScoresTable) objectIS.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("something went wrong", e);
        } catch (FileNotFoundException e) {
            return new HighScoresTable(5);
        } finally {
            if (objectIS != null) {
                // closing it
                objectIS.close();
            }
        }
    }

     /**
     * @param list the current score list.
     */
    public void sortList(List<ScoreInfo> list) {
        int listSize = list.size();
        ScoreInfo temp = null;
        // bubble sort
        for (int i = 0; i < listSize; i++) {
            for (int j = 1; j < (listSize - i); j++) {
                if (this.scoreList.get(j - 1).getScore() < this.scoreList.get(j).getScore()) {
                    temp = this.scoreList.get(j - 1);
                    this.scoreList.set(j - 1, this.scoreList.get(j));
                    this.scoreList.set(j, temp);
                }
            }
        }
    }
}
