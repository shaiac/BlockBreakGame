package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import scores.HighScoresTable;
import java.awt.Color;


/**
 * HighScoresAnimation class.
 * @author Shai acoca
 */
public class HighScoresAnimation implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private HighScoresTable scores;

    /**
     * Constructor the class given a key sensor and stop to false.
     * @param k the KeyboardSensor.
     * @param scores the scores table.
     */
    public HighScoresAnimation(KeyboardSensor k, HighScoresTable scores) {
        this.keyboard = k;
        this.stop = false;
        this.scores = scores;
    }

    /**
     * Drawing the pause screen on the draw surface, if the space key is pressed changing the stop status.
     * @param d the game draw surface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();
        // filling the backround
        d.setColor(Color.gray);
        d.fillRectangle((int) 0, (int) 0, (int) width, (int) height);
        d.setColor(Color.YELLOW);
        d.drawText(40, 60, "High Scores", 30);
        d.setColor(Color.CYAN);
        d.drawText(80, 120, "Player Name", 20);
        d.drawText(300, 120, "Score", 20);
        d.drawLine(75, 125, 500, 125);
        d.drawText(width / 3, height - 100, "Press space to continue", 20);
            for (int i = 0; i < scores.getScoreList().size(); i++) {
                d.drawText(80, 150 + (i * 30), scores.getScoreList().get(i).getPlayerName(), 20);
                d.drawText(300, 150 + (i * 30), String.valueOf(scores.getScoreList().get(i).getScore()), 20);
            }

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
