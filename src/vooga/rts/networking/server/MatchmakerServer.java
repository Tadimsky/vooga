package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import vooga.rts.networking.logger.HandlerTxt;
import vooga.rts.networking.logger.NetworkLogger;


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
        NetworkLogger.getInstance();
        NetworkLogger.setLevel(Level.ALL);
        NetworkLogger.addHandler(new HandlerTxt());
        NetworkLogger.logMessage(Level.CONFIG, "Server started");
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
            NetworkLogger.logMessage(Level.FINER, "GameContainer joined");
        } else {
            container = new GameContainer();
            myGameContainers.put(gameName, container);
            NetworkLogger.logMessage(Level.FINER, "new GameContainer");
        }
        container.addConnection(thread);
        removeConnection(thread);
    }

}
