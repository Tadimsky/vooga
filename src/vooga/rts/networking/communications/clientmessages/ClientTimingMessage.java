package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.client.IClientModel;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.TimeStamp;
import vooga.rts.networking.communications.servermessages.ServerInfoMessage;
import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Sends a message back to itself to measure latency.
 * 
 * @author David Winegar
 * 
 */
public class ClientTimingMessage extends Message implements ClientInfoMessage, ServerInfoMessage {

    private static final long serialVersionUID = 8049396477825603070L;

    /**
     * Only allow passed in timestamps for this constructor.
     * 
     * @param timeStamp stamp
     */
    public ClientTimingMessage (TimeStamp timeStamp) {
        super(timeStamp);
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        thread.sendMessage(this);
    }

    @Override
    public void affectClient (IClientModel model) {
        model.setTimeDelay(getTimeStamp());
    }

}
