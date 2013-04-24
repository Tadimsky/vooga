package vooga.rts.networking.communications;

import java.io.Serializable;


/**
 * A Message is the object sent between the server and the client.
 * A Designer may subclass Message to send whatever form of data they wish.
 * There is also a SystemLevel Message subclass that is used to communicate
 * between the server for managerial tasks; the developer likely will not need
 * to worry about these.
 * 
 * @author Henrique Moraes
 * @author David Winegar
 * 
 */
public abstract class Message implements Serializable, Comparable<Message>,IMessage {

    private static final long serialVersionUID = 3906028159511905867L;
    private TimeStamp myTimeStamp;

    /**
     * Constructor for this class
     * Creates a timestamp for this message with the given time
     * as the initial time
     * 
     * @param timeStamp timestamp passed in
     */
    public Message (TimeStamp timeStamp) {
        myTimeStamp = timeStamp;
    }

    /**
     * Constructor for this class
     * Creates a timestamp for this message with the current system time
     * as the initial time
     */
    public Message () {
        myTimeStamp = new SystemTimeStamp();
    }

    /**
     * 
     * @return this message's timestamp
     */
    public TimeStamp getTimeStamp () {
        return myTimeStamp;
    }

    /**
     * Call this method to reset the initial time to the current one and
     * the final time to the default
     */
    public void resetTime () {
        myTimeStamp.resetStamp();
    }

    /**
     * Call this method to mark the time received (final time)
     */
    public void stampTime () {
        myTimeStamp.stamp();
    }

    /**
     * Call this method to mark the time received (final time)
     * @param time to stamp
     */
    public void stampTime (long time) {
        myTimeStamp.stamp(time);
    }

    /**
     * 
     * @return time message was created or reset in milliseconds
     */
    public long getInitialTime () {
        return myTimeStamp.getInitialTime();
    }

    /**
     * 
     * @return time this message was stamped in milliseconds
     */
    public long getFinalTime () {
        return myTimeStamp.getFinalTime();
    }

    @Override
    public boolean equals (Object object) {
        if (object == null) { return false; }
        if (!(object instanceof Message)) { return false; }
        Message m = (Message) object;
        return myTimeStamp.equals(m.getTimeStamp());
    }

    @Override
    public int hashCode () {
        return myTimeStamp.hashCode();
    }

    /**
     * Compares based on timestamps
     * @param message to compare
     */
    @Override
    public int compareTo (Message message) {
        return myTimeStamp.compareTo(message.getTimeStamp());
    }

}
