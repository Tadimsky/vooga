package vooga.rts.networking.communications.gamemessage;

import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.TimeStamp;


/**
 * Message for game genres to extend.
 * 
 */
public class GameMessage extends Message {

    private static final long serialVersionUID = -5881860775991367901L;
    private int myPlayerID;

    /**
     * Default constructor that calls superclass.
     */
    public GameMessage (int id) {
        myPlayerID = id;
    }

    /**
     * Constructor that stores a TimeStamp object.
     * 
     * @param stamp to store
     */
    public GameMessage (TimeStamp stamp) {
        super(stamp);
    }

    /**
     * gets the player ID
     * 
     * @return player id
     */
    public int getPlayerID () {
        return myPlayerID;
    }

}
