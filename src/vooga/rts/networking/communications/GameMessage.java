package vooga.rts.networking.communications;

/**
 * Message for game genres to extend.
 * 
 * @author David Winegar
 * 
 */
public class GameMessage extends Message {
    
    /**
     * Default constructor that calls superclass.
     */
    public GameMessage () {
        super();
    }

    /**
     * Constructor that stores a TimeStamp object.
     * @param stamp to store
     */
    public GameMessage (TimeStamp stamp) {
        super(stamp);
    }

    private static final long serialVersionUID = -5881860775991367901L;

}
