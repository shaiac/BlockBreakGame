package menu;

/**
 * Task interface.
 * @author Shai acoca
 * @param <T> the task parameter.
 */
public interface Task<T> {
    /**
     * this method run the task.
     * @return T - as a task
     */
    T run();
}
