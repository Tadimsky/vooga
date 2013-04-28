package vooga.rts.networking.communications;

import java.io.Serializable;


/**
 * A Message is the object sent between the server and the client.
 * A Designer may subclass Message to send whatever form of data they wish.
 * There is also a SystemLevel Message subclass that is used to communicate
 * between the server for managerial tasks; the developer likely will not need
 * to worry about these.
 * 
 * @author Henrique Morales
 * @author David Winegar
 * 
 */
public interface IMessage extends Serializable, Comparable<Message> {

    /**
     * gets the current time stamp.
     * 
     * @return this message's timestamp
     */
    public TimeStamp getTimeStamp ();

    /**
     * Call this method to reset the initial time to the current one and
     * the final time to the default
     */
    public void resetTime ();

    /**
     * Call this method to mark the time received (final time)
     */
    public void stampTime ();

    /**
     * Call this method to mark the time received (final time)
     * 
     * @param time to stamp
     */
    public void stampTime (long time);

    /**
     * gets the initial time.
     * 
     * @return time message was created or reset in milliseconds
     */
    public long getInitialTime ();

    /**
     * Gets the final time.
     * 
     * @return time this message was stamped in milliseconds
     */
    public long getFinalTime ();

    @Override
    public boolean equals (Object object);

    @Override
    public int hashCode ();

}
