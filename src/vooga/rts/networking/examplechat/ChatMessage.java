package vooga.rts.networking.examplechat;

import vooga.rts.networking.communications.GameMessage;
import vooga.rts.networking.communications.UserTimeStamp;


/**
 * Creates a message for the chat program.
 * 
 * @author David Winegar
 * 
 */
public class ChatMessage extends GameMessage {

    private static final long serialVersionUID = -3728664209235141886L;
    private String myMessage;
    private int myID;

    /**
     * Receives a user time stamp and a message to send.
     * 
     * @param stamp timestamp
     * @param message to send
     * @param id id of sender
     */
    public ChatMessage (UserTimeStamp stamp, String message, int id) {
        super(stamp);
        myMessage = message;
        myID = id;
    }

    /**
     * gets the String message
     * 
     * @return message
     */
    public String getMessage () {
        return myMessage;
    }

    /**
     * Gets the ID of the sender.
     * 
     * @return id
     */
    public int getSender () {
        return myID;
    }

}
