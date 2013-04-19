package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Object responsible for creating an instance of a game, and establishing
 * connections between clients and the game.
 * 
 * @author srwareham
 * @author David Winegar
 * 
 */
public class MatchmakerServer extends AbstractThreadContainer {
    private static final String DEFAULT_RESOURCE_PACKAGE = "vooga.rts.networking.resources.";
    private Map<String, GameContainer> myGameContainers = new HashMap<String, GameContainer>();
    private ConnectionServer myConnectionServer = new ConnectionServer(this);
    private ResourceBundle myGamesBundle;

    /**
     * Initializes overall server hierarchy, reads in game names and creates containers.
     */
    public MatchmakerServer () {
        initializeGameContainers();
    }

    /**
     * Starts the ConnectionServer so that this server can start accepting connections.
     */
    public void startAcceptingConnections () {
        myConnectionServer.start();
    }

    private void initializeGameContainers () {
        myGamesBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "games");
        for (String game : myGamesBundle.keySet()) {
            myGameContainers.put(game, new GameContainer());
        }
    }

    @Override
    public void joinGameContainer (ConnectionThread thread, String gameName) {
        if (myGameContainers.containsKey(gameName)) {
            myGameContainers.get(gameName).addConnection(thread);
            removeConnection(thread);
        }
    }

}
