package vooga.rts.networking.client;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.PlayerInfo;

/**
 * Represents the game to the networking architecture.
 * 
 * @author David Winegar
 * 
 */
public interface NetworkedGame {

    /**
     * Loads the game. Once this terminates, the client tells the server that it may start the game
     * at any time.
     * 
     * @param info info about server
     * @param thisPlayer player on this client
     */
    public void loadGame (ExpandedLobbyInfo info, PlayerInfo userPlayer);

    /**
     * Starts the game.
     */
    public void startGame (IClient client);
}
