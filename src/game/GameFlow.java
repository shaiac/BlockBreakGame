package game;
import animation.Animation;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.EndScreen;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import counter.Counter;
import levels.LevelInformation;
import levels.LevelSetsReader;
import levels.LevelSpecificationReader;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;
import scores.HighScoresTable;
import scores.ScoreInfo;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameFlow class that will be creating the different levels, and moving from one level to the next.
 * @author Shai acoca
 */
public class GameFlow {
    private int width;
    private int height;
    private GUI gui;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private Counter score;
    private Counter lives;
    private HighScoresTable scoresTable;
    private File fileName;
    private String levelsSet;
    private boolean gameStatus;


    /**
     * Constructor for the class.
     * @param width the draw surface width.
     * @param height the draw surface height.
     * @param levelsSet the name of the levels set file.
     */
    public GameFlow(int width, int height, String levelsSet) {
        this.width = width;
        this.height = height;
        this.gui = new GUI("Arkanoid", width, height);
        this.keyboard = gui.getKeyboardSensor();
        this.runner = new AnimationRunner(gui, 60);
        this.score = new Counter(0);
        this.lives = new Counter(7);
        this.fileName = new File("C:/Users/shaia/IdeaProjects/ass7/highscores");
        this.levelsSet = levelsSet;
        this.gameStatus = true;
    }

    /**
     * Creating a menu and runs the tasks.
     * @throws IOException - as an exception.
     */
    public void startGame() throws IOException {
        this.loadScoresTable();
        while (true) {
            Menu<Task<Void>> menu = makeMenu();
            runner.run(menu);
            Task<Void> task = menu.getStatus();
            // run the task of the user selection:
            task.run();
            this.scoresTable.save(this.fileName);
            this.gameStatus = true;
        }

    }

    /**
     * The method building a sub menu according to the level sets file.
     * @return the sub menu the created.
     */
    private Menu<Task<Void>> makeSubMenu() {
        Menu<Task<Void>> subMenu = new MenuAnimation<>(this.keyboard, "Choose Mode");
        LevelSetsReader levelSetsReader = new LevelSetsReader();
        Reader reader = null;
        List<List<String>> allSets;
        try {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.levelsSet);
        reader = new InputStreamReader(is);
        allSets = levelSetsReader.fromReader(reader);
        } finally {
            try { // try to close the reader
                reader.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
        List<List<LevelInformation>> setsLevels = setsLevels(allSets);
        for (int i = 0; i < allSets.size(); i++) {
            List<LevelInformation> levels = setsLevels.get(i);
            Task<Void> playSetTask = new Task<Void>() {
                @Override
                public Void run() {
                    runLevels(levels);
                    initializeLiveScore();
                    return null;
                }
            };
            String key = allSets.get(i).get(0);
            subMenu.addSelection(key, "(" + key + ") " + allSets.get(i).get(1), playSetTask);
        }
        return subMenu;
    }

    /**
     * @param allSets List of lists of strings that include all the sets.
     * @return List of List of the levels information.
     */
    private List<List<LevelInformation>> setsLevels(List<List<String>> allSets) {
        List<List<LevelInformation>> setsLevels = new ArrayList<>();
        for (List<String> set : allSets) {
            String levelDefinitions = set.get(2);
            Reader reader = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelDefinitions);
                reader = new InputStreamReader(is);
                LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
                setsLevels.add(levelSpecificationReader.fromReader(reader));
            } finally {
                try { // try to close the reader
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Couldn't close reader");
                    e.printStackTrace();
                }
            }
        }
        return setsLevels;
    }


    /**
     * Creating the menu by Creating tasks and adding them to the menu.
     * @return the menu.
     */
    private Menu<Task<Void>> makeMenu() {
        Menu<Task<Void>> menu = new MenuAnimation<>(this.keyboard, "Arknoid");
        Task<Void> playGameTask = new Task<Void>() {
            @Override
            public Void run() {
                while (gameStatus) {
                        Menu<Task<Void>> subMenu = makeSubMenu();
                        menu.addSubMenu("s", "(s) Start New Game", subMenu);
                        runner.run(subMenu);
                        Task<Void> task = subMenu.getStatus();
                        // run the task of the user selection:
                        task.run();
                        gameStatus = false;
                        return null;
                }
                return null;
            }
        };
        menu.addSelection("s", "(s) Start New Game", playGameTask);

        Task<Void> showHighScoresTask = new Task<Void>() {
            @Override
            public Void run() {
                Animation scoresAnimation = new HighScoresAnimation(keyboard, scoresTable);
                runner.run(new KeyPressStoppableAnimation(keyboard, "space", scoresAnimation));
                return null;
            }
        };
        menu.addSelection("h", "(h) High Scores", showHighScoresTask);

        Task<Void> exitTask = new Task<Void>() {
            @Override
            public Void run() {
                Runtime.getRuntime().exit(0);
                return null;
            }
        };
        menu.addSelection("q", "(q) Quit", exitTask);
        return menu;
    }

    /**
     * Getting a list of levels and runs them.
     * @param levels a list of levels.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo: levels) {
            GameLevel game = new GameLevel(levelInfo, this.gui, this.score, this.lives, this.runner);
            game.initialize();
            while (this.lives.getValue() != 0 && game.getNumOfBlocks().getValue() != 0) {
                game.playOneTurn();
                this.lives = game.getnumOfLife();
            }
        }
        DialogManager dialog = gui.getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        ScoreInfo newScore = new ScoreInfo(name, this.score.getValue());
        this.scoresTable.add(newScore);
        if (this.lives.getValue() == 0) {
            EndScreen endScreen = new EndScreen(this.keyboard, "lose", this.score);
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", endScreen));
        } else {
            EndScreen endScreen = new EndScreen(this.keyboard, "win", this.score);
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", endScreen));
        }
        return;
    }

    /**
     * Loading the high scores table from the file.
     * @throws IOException if there was a problem.
     */
    public void loadScoresTable() throws IOException {
        this.scoresTable = HighScoresTable.loadFromFile(this.fileName);
    }

    /**
     * every new Game that starts initializing the scores and lives for a new game.
     */
    public void initializeLiveScore() {
        this.score = new Counter(0);
        this.lives = new Counter(7);
    }
}
