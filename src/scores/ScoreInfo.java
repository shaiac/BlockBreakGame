package scores;


/**
 * Score information class.
 * @author Shai acoca
 */
public class ScoreInfo implements java.io.Serializable {
    private static final long serialVersionUID = 6219314936840928897L;
    private String playerName;
    private int score;

    /**
     * Constructor to the class.
     * @param name the player name.
     * @param score his score in the game.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.score = score;
    }

    /**
     * @return the player name.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * @return his score.
     */
    public int getScore() {
        return this.score;
    }
}
