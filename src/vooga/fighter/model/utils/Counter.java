package vooga.fighter.model.utils;

/**
 * Holds a counter which can be used as a timer. The counter can be set to an initial
 * value and decremented as needed.
 * 
 * @author James Wei
 * 
 */
public class Counter {

    /**
     * The count remaining on the Counter object.
     */
    private int myCount;

    /**
     * True if the counter is active, false otherwise. Active refers to a counter
     * that has set its value.
     */
    private boolean myStatus;
    

    /**
     * Construct counter with initial count set to my count
     */
    public Counter(int count) {
        myCount = count;
        myStatus = true;
    }

    /**
     * Construct counter with no initial count. The counter will be inactive.
     */
    public Counter() {
        myStatus = false;
    }
    
    /**
     * Construct counter with identical count to another counter.
     */
    public Counter(Counter other) {
        this(other.getCount());
    }

    /**
     * Sets the value of the current object counter. Count must not be negative.
     */
    public void setCounter (int count) {
        if (count < 0) { return; }
        myCount = count;
        myStatus = true;
    }


    /**
     * Reduces the counter by one, if it is active. If the counter is already
     * at zero, the counter remains at zero.
     */
    public void decrementCounter () {
        if (!myStatus) { return; }
        if (myCount > 0) {
            myCount--;
        }
    }

    /**
     * Returns the value of the counter. Returns -1 if counter is inactive.
     */
    public int getCount () {
        if (!myStatus) { return -1; }
        return myCount;
    }

    /**
     * Returns true if the counter is positive, or false otherwise.
     */
    public boolean hasCountRemaining () {
        return (myCount > 0);
    }

}
