package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import util.logger.HandlerTxt;
import vooga.rts.networking.NetworkBundle;


/**
 * Object responsible for creating an instance of a game, and establishing
 * connections between clients and the game.
 * 
 * @author srwareham
 * @author David Winegar
 * 
 */
public class MatchmakerServer extends AbstractThreadContainer {
    private Map<String, GameContainer> myGameContainers = new HashMap<String, GameContainer>();
    private ConnectionServer myConnectionServer = new ConnectionServer(this);

    /**
     * Initializes overall server hierarchy.
     */
    public MatchmakerServer () {
        //getLogger().addHandler(new HandlerTxt(getClass().getSimpleName()).getHandler());
        getLogger().log(Level.INFO, NetworkBundle.getString("ServerStarted"));
    }

    /**
     * Starts the ConnectionServer so that this server can start accepting connections.
     */
    public void startAcceptingConnections () {
        myConnectionServer.start();
    }

    @Override
    public void joinGameContainer (ConnectionThread thread, String gameName) {
        GameContainer container;
        if (myGameContainers.containsKey(gameName)) {
            container = myGameContainers.get(gameName);
            getLogger().log(Level.INFO,
                            NetworkBundle.getString("GameContainerJoined") +
                                    gameName);
        }
        else {
            container = new GameContainer(gameName);
            myGameContainers.put(gameName, container);
            getLogger().log(Level.INFO, NetworkBundle.getString("NewGameContainer") +
                                        gameName);
        }
        container.addConnection(thread);
        removeConnection(thread);
    }

}
