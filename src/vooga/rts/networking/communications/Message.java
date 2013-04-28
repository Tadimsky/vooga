package vooga.rts.networking.communications;

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
public abstract class Message implements IMessage {

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

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#getTimeStamp()
     */
    @Override
    public TimeStamp getTimeStamp () {
        return myTimeStamp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#resetTime()
     */
    @Override
    public void resetTime () {
        myTimeStamp.resetStamp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#stampTime()
     */
    @Override
    public void stampTime () {
        myTimeStamp.stamp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#stampTime(long)
     */
    @Override
    public void stampTime (long time) {
        myTimeStamp.stamp(time);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#getInitialTime()
     */
    @Override
    public long getInitialTime () {
        return myTimeStamp.getInitialTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#getFinalTime()
     */
    @Override
    public long getFinalTime () {
        return myTimeStamp.getFinalTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object object) {
        if (object == null) { return false; }
        if (!(object instanceof Message)) { return false; }
        Message m = (Message) object;
        return myTimeStamp.equals(m.getTimeStamp());
    }

    /*
     * (non-Javadoc)
     * 
     * @see vooga.rts.networking.communications.IMessage#hashCode()
     */
    @Override
    public int hashCode () {
        return myTimeStamp.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vooga.rts.networking.communications.IMessage#compareTo(vooga.rts.networking.communications
     * .Message)
     */
    @Override
    public int compareTo (Message message) {
        return myTimeStamp.compareTo(message.getTimeStamp());
    }

}
