// 323274480 Michael Ifraimov
package listeners.listenerclass;

/**
 * Class Counter, a simple class that is used for counting.
 * @author Michael Ifraimov
 */
public class Counter {
    private int currentValue;

    /**
     * Constructor.
     * @param value int type, counter value
     */
    public Counter(int value) {
        this.currentValue = value;
    }
    /**
     * Add a given number to current counter value.
     * @param number int type, number to add to current counter value
     */
    public void increase(int number) {
        this.currentValue += number;
    }

    /**
     * Remove a given number from current counter value.
     * @param number int type, number to remove from current counter value
     */
    public void decrease(int number) {
        this.currentValue -= number;
    }

    /**
     * Get the current value of the counter.
     * @return int type, current counter value
     */
    public int getValue() {
        return this.currentValue;
    }
}
