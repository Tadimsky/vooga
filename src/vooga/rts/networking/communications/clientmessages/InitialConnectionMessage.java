package vooga.rts.networking.communications.clientmessages;

import vooga.rts.networking.server.ConnectionThread;
import vooga.rts.networking.server.IThreadContainer;

public class InitialConnectionMessage extends ClientInfoMessage {

    private static final long serialVersionUID = -3741469544179563398L;
    private String myGameName;
    private String myUserName;

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
