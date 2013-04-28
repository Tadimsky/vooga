package vooga.rts.networking.client;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.TimeStamp;


/**
 * Represents the client to ServerInfoMessages
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public interface IClientModel extends IMessageReceiver {
    /**
     * Closes the client-side connection to the server.
     */
    public void closeConnection ();

    /**
     * Adds a lobby to the server's collection of lobbies.
     * 
     * @param lobbies list of lobbies to display
     */
    public void addLobbies (LobbyInfo[] lobbies);

    /**
     * Switches the clients visual elements to that of the Lobby.
     * 
     * @param lobbyInfo info
     * @param id id of client
     */
    public void switchToLobby (ExpandedLobbyInfo lobbyInfo, int id);

    /**
     * Updates the client held Lobby information with that passed down from the server.
     * 
     * @param lobbyInfo info of lobby
     */
    public void updateLobby (ExpandedLobbyInfo lobbyInfo);

    /**
     * Immediately alerts the client to an important message.
     * Such as an error in client behavior (clicking prematurely on a start button, for example)
     * 
     * @param title of error
     * @param message message of error
     */
    public void alertClient (String title, String message);

    /**
     * Loads the game and waits for the start.
     * 
     * @param lobbyInfo info of lobby
     */
    public void loadGame (ExpandedLobbyInfo lobbyInfo);

    /**
     * Starts the game.
     */
    public void startGame ();

    /**
     * Gets the timestamp of a message to test the ping from the server
     * @param timeStamp of message sent from client
     */
    public void setTimeDelay (TimeStamp timeStamp);

}
