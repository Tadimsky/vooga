package vooga.rts.networking.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JPanel;
import vooga.rts.networking.client.clientgui.ClientViewAdapter;
import vooga.rts.networking.client.clientgui.IModel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.PlayerInfo;
import vooga.rts.networking.communications.clientmessages.InitialConnectionMessage;
import vooga.rts.networking.communications.clientmessages.JoinLobbyMessage;
import vooga.rts.networking.communications.clientmessages.LeaveLobbyMessage;
import vooga.rts.networking.communications.clientmessages.ReadyToStartGameMessage;
import vooga.rts.networking.communications.clientmessages.RequestServerListMessage;
import vooga.rts.networking.communications.clientmessages.RequestStartGameMessage;
import vooga.rts.networking.communications.clientmessages.StartLobbyMessage;
import vooga.rts.networking.communications.clientmessages.UpdateLobbyInfoMessage;
import vooga.rts.networking.communications.servermessages.ServerInfoMessage;


/**
 * Model for the overall server browser on the client.
 * 
 * @author David Winegar
 * @author Sean Wareham
 * @author Henrique Morales
 * 
 */
public class ClientModel extends Observable implements IClientModel, IModel {

    private IClient myClient;
    private String myUserName;
    private ExpandedLobbyInfo myLobbyInfo;
    private List<PlayerInfo> myUserControlledPlayers = new ArrayList<PlayerInfo>();
    private PlayerInfo myPlayer;
    private NetworkedGame myGame;
    private ClientViewAdapter myViewAdapter;
    private List<String> myFactions;

    /**
     * This is the handler of information needed by all of the views in the process of connecting to
     * / creating a server, creating a game, waiting in a lobby.
     * 
     * @param gameName
     * @param userName
     * @param factions
     * @param maps
     * @param maxPlayerArray
     */
    public ClientModel (NetworkedGame game,
                        String gameName,
                        String userName,
                        List<String> factions,
                        List<String> maps,
                        List<Integer> maxPlayerArray) {
        myGame = game;
        myFactions = factions;
        myUserName = userName;
        myClient = new Client(this);
        Message initialConnection = new InitialConnectionMessage(gameName, userName);
        myClient.sendData(initialConnection);
        myViewAdapter = new ClientViewAdapter(this, gameName, maps, maxPlayerArray);
    }

    @Override
    public void getMessage (Message message) {
        if (message instanceof ServerInfoMessage) {
            ((ServerInfoMessage) message).affectClient(this);
        }
    }

    @Override
    public void closeConnection () {
        myClient.closeConnection();
    }

    /**
     * Request currently available lobbies from the server
     */
    public void requestLobbies () {
        myClient.sendData(new RequestServerListMessage());
    }

    /**
     * Request to join a lobby on the server
     * 
     * @param id ID of the lobby to join
     */
    public void requestJoinLobby (int id) {
        myClient.sendData(new JoinLobbyMessage(id));
    }

    /**
     * Request to leave a lobby on the server
     */
    public void leaveLobby () {
        myClient.sendData(new LeaveLobbyMessage(myLobbyInfo));
    }

    /**
     * Starts a new lobby for purposes of hosting a game
     * 
     * @param lobbyInfo Lobby containing information to host a game
     */
    public void startLobby (LobbyInfo lobbyInfo) {
        myClient.sendData(new StartLobbyMessage(lobbyInfo));
    }

    /**
     * Request to initiate the game in this lobby
     */
    public void requestStartGame () {
        myClient.sendData(new RequestStartGameMessage());
    }

    /**
     * Sends an infoLobby so other users can view that the state of
     * the lobby has changed
     */
    public void sendUpdatedLobbyInfo () {
        myClient.sendData(new UpdateLobbyInfoMessage(myLobbyInfo));
    }

    /**
     * 
     * @return the view used by all networking functions
     */
    public JPanel getView () {
        return myViewAdapter.getView();
    }

    @Override
    public void addLobbies (LobbyInfo[] lobbies) {
        myViewAdapter.changeLobbies(lobbies);
    }

    @Override
    public void updateFaction (String faction, int position) {
        myLobbyInfo.getPlayerAtPosition(position).setFaction(faction);
        sendUpdatedLobbyInfo();
    }

    @Override
    public void updateTeam (int team, int position) {
        myLobbyInfo.getPlayerAtPosition(position).setTeam(team);
        sendUpdatedLobbyInfo();
    }

    @Override
    public void switchToLobby (ExpandedLobbyInfo lobbyInfo, int playerID) {
        myPlayer = new PlayerInfo(myUserName, 1, myFactions.get(0), playerID);
        myUserControlledPlayers.clear();
        myUserControlledPlayers.add(myPlayer);
        lobbyInfo.addPlayer(myPlayer);
        myViewAdapter.switchToLobbyView(lobbyInfo);
    }

    @Override
    public void updateLobby (ExpandedLobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
        myViewAdapter.updateLobby();
    }

    @Override
    public void alertClient (String title, String message) {
        myViewAdapter.alertClient(title, message);
    }

    @Override
    public void loadGame (ExpandedLobbyInfo lobbyInfo) {
        myGame.loadGame(lobbyInfo, myPlayer);
        myClient.sendData(new ReadyToStartGameMessage());
    }

    @Override
    public void startGame () {
        myGame.startGame(myClient);
    }

    /**
     * 
     * @return LobbyInfo of this model
     */
    public ExpandedLobbyInfo getLobbyInfo () {
        return myLobbyInfo;
    }

    /**
     * 
     * @return List with player information for this lobby
     */
    public List<PlayerInfo> getPlayersInfo () {
        return myUserControlledPlayers;
    }

    /**
     * 
     * @return A list with name of factions of this client view
     */
    public List<String> getFactions () {
        return myFactions;
    }

    @Override
    public void connectionClosed () {
        myViewAdapter.destroyPanel();
        myGame.serverBrowserClosed();
    }
}
