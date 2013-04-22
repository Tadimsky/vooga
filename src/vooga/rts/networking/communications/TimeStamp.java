package vooga.rts.networking.communications;

import java.io.Serializable;


/**
 * Represents a timeStamp that will be sent across the network. This is an abstract class
 * 
 * @author Henrique Moraes
 * @author David Winegar
 * 
 */
public abstract class TimeStamp implements Serializable, Comparable<TimeStamp> {
    protected static final int DEFAULT_VALUE = -1;
    private static final long serialVersionUID = -2759543981020260696L;
    private long myInitialTime = DEFAULT_VALUE;
    private long myFinalTime = DEFAULT_VALUE;

    /**
     * 
     * @return time message was created or reset in milliseconds
     */
    public long getInitialTime () {
        return myInitialTime;
    }

    /**
     * 
     * @return time this message was stamped in milliseconds
     */
    public long getFinalTime () {
        return myFinalTime;
    }

    /**
     * Resets this stamp setting the initial time as the current one
     */
    public abstract void resetStamp ();

    /**
     * Returns the difference between initial and final times.
     * 
     * @return difference in elapsed time since this stamp was created
     *         in milliseconds
     */
    public abstract long getDifference ();

    /**
     * sets the received message time for this stamp
     * 
     * @return difference in elapsed time since this stamp was created
     */
    public abstract long stamp ();

    /**
     * sets the received message time for this stamp
     * 
     * @param time time to stamp
     * @return difference in elapsed time since this stamp was created
     */
    public abstract long stamp (long time);

    /**
     * Used by subclasses to set initial time.
     */
    protected void setInitialTime (long time) {
        myInitialTime = time;
    }

    /**
     * Used by subclasses to set final time.
     */
    protected void setFinalTime (long time) {
        myFinalTime = time;
    }

    @Override
    public int hashCode () {
        return (int) (myInitialTime * 200 - 13 + myFinalTime * 30 + 23);
    }

    @Override
    public boolean equals (Object object) {
        if (object == null) { return false; }
        if (!(object instanceof SystemTimeStamp)) { return false; }
        SystemTimeStamp message = (SystemTimeStamp) object;
        return getInitialTime() != message.getInitialTime();
    }

    @Override
    public int compareTo (TimeStamp message) {
        if (message != null && message.getFinalTime() != DEFAULT_VALUE &&
            getFinalTime() != DEFAULT_VALUE) { return (int) (getInitialTime() - message
                .getInitialTime()); }
        return (int) (getInitialTime() - message.getInitialTime());
    }
}
