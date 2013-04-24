package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.Message;


/**
 * Message class used to inform the user of important information. Such as Error messages they need
 * to be immediately privy to.
 * 
 * @author srwareham
 * 
 */
public class AlertClientMessage extends Message implements ServerInfoMessage {

    /**
     * 
     */
    private static final long serialVersionUID = -4813386387789409462L;
    private String myMessage;
    private String myTitle;

    public AlertClientMessage (String title, String message) {
        myTitle = title;
        myMessage = message;
    }

    @Override
    public void affectClient (IClientModel model) {
        model.alertClient(myTitle, myMessage);
    }

}
