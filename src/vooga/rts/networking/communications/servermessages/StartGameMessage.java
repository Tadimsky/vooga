package vooga.rts.networking.communications.servermessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.Message;


/**
 * Starts the game on each client.
 * 
 * @author David Winegar
 * 
 */
public class StartGameMessage extends Message implements ServerInfoMessage {

    private static final long serialVersionUID = -140612656634180594L;

    @Override
    public void affectClient (IClientModel model) {
        model.startGame();
    }

}
