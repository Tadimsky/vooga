package vooga.rts.networking.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import javax.swing.JPanel;
import util.logger.LoggerManager;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.client.clientgui.ClientViewAdapter;
import vooga.rts.networking.client.clientgui.IModel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.IMessage;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.PlayerInfo;
import vooga.rts.networking.communications.TimeStamp;
import vooga.rts.networking.communications.UserTimeStamp;
import vooga.rts.networking.communications.clientmessages.ClientTimingMessage;
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
 * @author Sean Wareham
 * @author Henrique Morales
 * @author David Winegar
 * 
 */
public class ClientModel extends Observable implements IClientModel, IModel {

    private static final int ONE_SECOND = 1000;
    private static final int NANOSECONDS_IN_MILLISECOND = 1000000000;
    private IClient myClient;
    private String myUserName;
    private ExpandedLobbyInfo myLobbyInfo;
    private List<PlayerInfo> myUserControlledPlayers = new ArrayList<PlayerInfo>();
    private PlayerInfo myPlayer;
    private NetworkedGame myGame;
    private ClientViewAdapter myViewAdapter;
    private List<String> myFactions;
    private long myTimeDelay;

    /**
     * This is the handler of information needed by all of the views in the process of connecting to
     * / creating a server, creating a game, waiting in a lobby.
     * 
     * @param game game that called us
     * @param gameName name of the game
     * @param userName name of the user
     * @param factionNameList a list of all faction choices, in string form
     * @param mapNameList a list of all map names
     * @param maxPlayerList a list of all max players. Should be the same length as the mapNameList
     *        and contain an Integer value for the max players corresponding to the position that
     *        the mapNameList.
     */
    public ClientModel (NetworkedGame game,
                        String gameName,
                        String userName,
                        List<String> factionNameList,
                        List<String> mapNameList,
                        List<Integer> maxPlayerList) {
        myGame = game;
        myFactions = factionNameList;
        myUserName = userName;
        myClient = new Client(this);
        IMessage initialConnection = new InitialConnectionMessage(gameName, userName);
        myClient.sendMessage(initialConnection);
        myViewAdapter = new ClientViewAdapter(this, gameName, mapNameList, maxPlayerList);
        //testPing();
    }

    private void testPing () {
        myTimeDelay = System.nanoTime();
        myClient.sendMessage(new ClientTimingMessage(new UserTimeStamp(myTimeDelay)));
    }
    
    @Override
    public void setTimeDelay (TimeStamp timeStamp) {
        timeStamp.stamp(System.nanoTime());
        // divide in 2 for round trip
        myTimeDelay = timeStamp.getDifference() / NANOSECONDS_IN_MILLISECOND / 2;
    }

    @Override
    public void getMessage (IMessage message) {
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
        myClient.sendMessage(new RequestServerListMessage());
    }

    /**
     * Request to join a lobby on the server
     * 
     * @param id ID of the lobby to join
     */
    public void requestJoinLobby (int id) {
        myClient.sendMessage(new JoinLobbyMessage(id));
    }

    /**
     * Request to leave a lobby on the server
     */
    public void leaveLobby () {
        myLobbyInfo.removePlayer(myPlayer);
        myClient.sendMessage(new LeaveLobbyMessage(myLobbyInfo));
    }

    /**
     * Starts a new lobby for purposes of hosting a game
     * 
     * @param lobbyInfo Lobby containing information to host a game
     */
    public void startLobby (LobbyInfo lobbyInfo) {
        myClient.sendMessage(new StartLobbyMessage(lobbyInfo));
    }

    /**
     * Request to initiate the game in this lobby
     */
    public void requestStartGame () {
        myClient.sendMessage(new RequestStartGameMessage());
    }

    /**
     * Sends an infoLobby so other users can view that the state of
     * the lobby has changed
     */
    public void sendUpdatedLobbyInfo () {
        myClient.sendMessage(new UpdateLobbyInfoMessage(myLobbyInfo));
    }

    /**
     * Gets the panel that this model's view uses.
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
        myClient.sendMessage(new ReadyToStartGameMessage());
    }

    @Override
    public void startGame () {
        try {
            Thread.sleep(ONE_SECOND - myTimeDelay);
        }
        catch (InterruptedException e) {
            LoggerManager.DEFAULT_LOGGER.log(Level.SEVERE, NetworkBundle.getString("WaitFailed"));
        }
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
        getView().removeAll();
        myGame.serverBrowserClosed();
    }

}
