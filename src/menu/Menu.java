package menu;

import animation.Animation;

/**
 * Menu interface.
 * @author Shai acoca
 * @param <T> the task parameter.
 */
public interface Menu<T> extends Animation {
    /**
     * this method adds a selection.
     * @param key - as a key
     * @param message - as a message
     * @param returnVal - as the return value
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * this method returns the object status.
     * @return T - as the status
     */
    T getStatus();

    /**
     * this method adds a sub menu.
     * @param key - as a key
     * @param message - as a message
     * @param subMenu - as a sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}
