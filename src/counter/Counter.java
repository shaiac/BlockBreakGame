package counter;

/**
 * The counter.Counter class.
 * @author Shai acoca
 */
public class Counter {
    private int counterNum;

    /**
     * Constructor to the counter.Counter class.
     * @param counterNum the number of the blocks in the game.
     */
    public Counter(int counterNum) {
        this.counterNum = counterNum;
    }

    /**
     * Add number to current count.
     * @param number the number to add.
     */
    public void increase(int number) {
        this.counterNum += number;
    }

    /**
     * Subtract number from current count.
     * @param number the number to decrease.
     */
    public void decrease(int number) {
        this.counterNum -= number;
    }

    /**
     * Get the current count.
     * @return the current count.
     */
    public int getValue() {
        return this.counterNum;
    }
}
