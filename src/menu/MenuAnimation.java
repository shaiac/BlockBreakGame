package menu;
import backgrounds.MenuBG;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu Animation class.
 * @author Shai acoca
 * @param <T> the task parameter.
 */
public class MenuAnimation<T> implements Menu<T> {

    private String menuName;
    private MenuBG background;
    private List<String> keys;
    private List<String> messages;
    private List<T> returnValues;
    private T status;
    private KeyboardSensor keyboard;
    private boolean stop;
    private List<Menu<T>> subMenuList;

    /**
     * Constructor to the class.
     * @param keyboard the KeyboardSensor.
     * @param menuName the game name.
     */
    public MenuAnimation(KeyboardSensor keyboard, String menuName) {
        this.menuName = menuName;
        this.background = new MenuBG();
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.returnValues = new ArrayList<>();
        this.keyboard = keyboard;
        this.stop = false;
        this.subMenuList = new ArrayList<>();
    }


    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnValues.add(returnVal);

    }

    @Override
    public T getStatus() {
        this.stop = false;
        return this.status;

    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int i = 150;
        this.background.drawOn(d);
        d.setColor(Color.green);
        d.drawText(100, 80, this.menuName, 50);
        for (String message : this.messages) {
            d.drawText(150, i, message, 30);
            i += 60;
        }
        // check if to change status:
        for (String key : this.keys) {
            if (this.keyboard.isPressed(key) || this.keyboard.isPressed(key.toUpperCase())) {
                this.status = this.returnValues.get(this.keys.indexOf(key));
                this.stop = true;
                break;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenuList.add(subMenu);
    }
}
