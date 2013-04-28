package vooga.rts.networking.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        getLogger().addHandler(new HandlerTxt(getClass().getSimpleName()).getHandler());
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
            container = createGameContainer(gameName);
            getLogger().log(Level.INFO, NetworkBundle.getString("NewGameContainer") +
                                        gameName);
        }
        container.addConnection(thread);
        removeConnection(thread);
    }

    /**
     * If there is a GameContainer subclass associated with the game name, it looks for it in the
     * configuration file and tries to instantiate that instead. If not, uses a normal
     * GameContainer.
     */
    private GameContainer createGameContainer (String gameName) {
        GameContainer container;

        if (NetworkBundle.containsConfigurationItem(gameName)) {
            String className = NetworkBundle.getConfigurationItem(gameName);

            try {
                Constructor<?> constructor = Class.forName(className).getConstructor(String.class);
                container = (GameContainer) constructor.newInstance(gameName);

            }
            catch (InstantiationException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (IllegalAccessException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (ClassNotFoundException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (IllegalArgumentException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (InvocationTargetException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (NoSuchMethodException e) {
                container = logAndCreateContainer(gameName);
            }
            catch (SecurityException e) {
                container = logAndCreateContainer(gameName);
            }
        }
        else {
            container = new GameContainer(gameName);
        }

        myGameContainers.put(gameName, container);
        return container;
    }

    private GameContainer logAndCreateContainer (String gameName) {
        getLogger().log(Level.SEVERE, NetworkBundle.getString("GameContainerCreationFailed"));
        return new GameContainer(gameName);
    }

}
