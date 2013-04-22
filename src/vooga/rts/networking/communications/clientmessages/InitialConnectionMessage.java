package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;


/**
 * Gives the server the username and name of the game being played. Must be the first message
 * received by the server.
 * 
 * @author David Winegar
 * 
 */
public class InitialConnectionMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -3741469544179563398L;
    private String myGameName;
    private String myUserName;

    /**
     * Creates the message.
     * @param gameName name of the game
     * @param userName name of the user
     */
    public InitialConnectionMessage (String gameName, String userName) {
        myGameName = gameName;
        myUserName = userName;
    }

    @Override
    public void affectServer (ConnectionThread thread, IThreadContainer server) {
        thread.setUserName(myUserName);
        thread.setGameName(myGameName);
        server.joinGameContainer(thread, myGameName);
    }

}
