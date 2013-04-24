package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.communications.Message;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Tells the server the client is ready to start the game.
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public class ReadyToStartGameMessage extends Message implements ClientInfoMessage {

    private static final long serialVersionUID = 4747519878766646563L;

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        server.clientIsReadyToStart(thread);
    }

}
