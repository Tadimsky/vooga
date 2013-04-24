package vooga.rts.networking.client;

import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Represents the client to ServerInfoMessages
 * 
 * @author David Winegar
 * @author Sean Wareham
 * 
 */
public interface IClientModel {
    /**
     * Closes the client-side connection to the server.
     */
    public void closeConnection ();

    /**
     * Adds a lobby to the server's collection of lobbies.
     * 
     * @param lobbies 
     */
    public void addLobbies (LobbyInfo[] lobbies);

    /**
     * Switches the clients visual elements to that of the Lobby.
     * 
     * @param lobbyInfo 
     * @param id 
     */
    public void switchToLobby (ExpandedLobbyInfo lobbyInfo, int id);

    /**
     * Updates the client held Lobby information with that passed down from the server.
     * 
     * @param lobbyInfo 
     */
    public void updateLobby (ExpandedLobbyInfo lobbyInfo);

    /**
     * Immediately alerts the client to an important message.
     * Such as an error in client behavior (clicking prematurely on a start button, for example)
     * 
     * @param title  
     * @param message 
     */
    public void alertClient (String title, String message);

    /**
     * Loads the game and waits for the start.
     * @param lobbyInfo 
     */
    public void loadGame (ExpandedLobbyInfo lobbyInfo);

    /**
     * Starts the game.
     */
    public void startGame ();

}
