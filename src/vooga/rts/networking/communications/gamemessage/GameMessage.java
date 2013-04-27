package vooga.rts.networking.communications.gamemessage;

import vooga.rts.action.InteractiveAction;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.TimeStamp;

/**
 * Message for game genres to extend.
 * 
 * @author David Winegar
 * 
 */
public class GameMessage extends Message {
    
    private int myPlayerID;
    
    /**
     * Default constructor that calls superclass.
     */
    public GameMessage (int id) {
        myPlayerID = id;
    }

    /**
     * Constructor that stores a TimeStamp object.
     * @param stamp to store
     */
    public GameMessage (TimeStamp stamp) {
        super(stamp);
    }
    
    public int getPlayerID () {
        return myPlayerID;
    }

    private static final long serialVersionUID = -5881860775991367901L;

}
