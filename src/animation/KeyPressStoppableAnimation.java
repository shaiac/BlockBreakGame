package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation class.
 *  * @author Shai acoca
 */
public class KeyPressStoppableAnimation implements Animation {
    private Animation animation;
    private String key;
    private boolean stop;
    private KeyboardSensor keyboard;
    private boolean isAlreadyPressed;

    /**
     * Constructor to the class.
     * @param keyboard the key board.
     * @param key the key that indicate that should stop.
     * @param animation the animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.animation = animation;
        this.keyboard = keyboard;
        this.key = key;
        this.stop = animation.shouldStop();
        isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.key.equals("space")) {
            if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                if (!isAlreadyPressed) {
                    this.stop = true;
                } else {
                    isAlreadyPressed = false;
                }
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
