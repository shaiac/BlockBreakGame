import game.GameFlow;
import java.io.IOException;

/**
 * The main class that runs the game.
 * @author Shai acoca
 */
public class Ass7Game {

    /**
     * the main class, starting a new game and run it.
     * @param args no arguments from the user.
     * @throws IOException an IO exception.
     */
    public static void main(String[] args) throws IOException {
        String levelsSet;
        if (args.length == 0) {
            levelsSet = "definitions/level_sets.txt";
        } else {
            levelsSet = args[0];
        }
        GameFlow game = new GameFlow(800, 600, levelsSet);
        game.startGame();
    }

}
