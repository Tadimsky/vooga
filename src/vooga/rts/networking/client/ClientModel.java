package vooga.rts.networking.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.client.GUI.CreateLobbyView;
import vooga.rts.networking.client.GUI.IModel;
import vooga.rts.networking.client.GUI.LobbyView;
import vooga.rts.networking.client.GUI.ServerBrowserTableAdapter;
import vooga.rts.networking.client.GUI.ServerBrowserView;
import vooga.rts.networking.client.GUI.ViewContainerPanel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.Player;
import vooga.rts.networking.communications.clientmessages.InitialConnectionMessage;
import vooga.rts.networking.communications.clientmessages.JoinLobbyMessage;
import vooga.rts.networking.communications.clientmessages.LeaveLobbyMessage;
import vooga.rts.networking.communications.clientmessages.RequestServerListMessage;
import vooga.rts.networking.communications.clientmessages.StartGameMessage;
import vooga.rts.networking.communications.clientmessages.StartLobbyMessage;
import vooga.rts.networking.communications.clientmessages.UpdateLobbyInfoMessage;
import vooga.rts.networking.communications.servermessages.ServerInfoMessage;


/**
 * Model for the overall server browser on the client.
 * 
 * @author David Winegar
 * 
 */
public class ClientModel implements IMessageReceiver, IClientModel, IModel {

    private IClient myClient;
    private String myUserName;
    private ViewContainerPanel myContainerPanel;
    private ServerBrowserView myServerBrowserView;
    private CreateLobbyView myCreateLobbyView;
    private ExpandedLobbyInfo myLobbyInfo;
    private LobbyView myLobbyView;
    private String[] myFactions;
    private List<Player> myUserControlledPlayers = new ArrayList<Player>();

    /**
     * This is the handler of information needed by all of the views in the process of connecting to
     * / creating a server, creating a game, waiting in a lobby.
     * 
     * @param gameName
     * @param userName
     * @param maps
     * @param maxPlayerArray
     */
    public ClientModel (String gameName,
                        String userName,
                        String[] factions,
                        String[] maps,
                        Integer[] maxPlayerArray) {
        myUserName = userName;
        myFactions = factions;
        myContainerPanel = new ViewContainerPanel(gameName);
        myServerBrowserView = new ServerBrowserView(new ServerBrowserTableAdapter());
        myCreateLobbyView = new CreateLobbyView(maps, maxPlayerArray);
        myClient = new Client(this);
        myClient.beginAcceptingConnections();
        Message initialConnection = new InitialConnectionMessage(gameName, userName);
        myClient.sendData(initialConnection);
        switchToServerBrowserView();
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
     * Switches the current View to the ServerBrowser.
     */
    private void switchToServerBrowserView () {
        requestLobbies();
        myContainerPanel.changeView(myServerBrowserView,
                                    NetworkBundle.getString("ServerBrowser"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("HostGame"),
                                          new ActionListener() {
                                              @Override
                                              public void actionPerformed (ActionEvent arg0) {
                                                  switchToCreateLobbyView();
                                              }
                                          });
        myContainerPanel.changeRightButton(NetworkBundle.getString("JoinGame"),
                                           new ActionListener() {
                                               @Override
                                               public void actionPerformed (ActionEvent arg0) {
                                                   if (myServerBrowserView.hasSelected()) {
                                                       requestJoinLobby(myServerBrowserView
                                                               .getSelectedID());
                                                   }
                                               }
                                           });
    }

    /**
     * Switches the current View to the LobbyCreatorScreen.
     */
    private void switchToCreateLobbyView () {
        myContainerPanel.changeView(myCreateLobbyView,
                                    NetworkBundle.getString("LobbyCreation"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("BackToBrowser"),
                                          new ActionListener() {
                                              @Override
                                              public void actionPerformed (ActionEvent arg0) {
                                                  switchToServerBrowserView();
                                              }
                                          });
        myContainerPanel.changeRightButton(NetworkBundle.getString("StartLobby"),
                                           new ActionListener() {
                                               @Override
                                               public void actionPerformed (ActionEvent arg0) {
                                                   if (myCreateLobbyView.allItemsChosen()) {
                                                       startLobby(myCreateLobbyView.getLobbyInfo());
                                                   }
                                               }
                                           });
    }

    /**
     * Switches the current view to the Lobby.
     */
    private void switchToLobbyView (ExpandedLobbyInfo lobbyInfo) {
        myLobbyView = new LobbyView(this, myFactions, lobbyInfo.getMaxPlayers());
        updateLobby(lobbyInfo);
        sendUpdatedLobbyInfo();

        myContainerPanel.changeView(myLobbyView, NetworkBundle.getString("LobbyCreation"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("LeaveLobby"),
                                          new ActionListener() {
                                              @Override
                                              public void actionPerformed (ActionEvent arg0) {
                                                  // TODO this code sucks
                                                  myLobbyInfo.removePlayer(myUserControlledPlayers
                                                          .get(0));
                                                  myClient.sendData(new LeaveLobbyMessage(
                                                                                          myLobbyInfo));
                                                  switchToServerBrowserView();
                                              }
                                          });
        myContainerPanel.changeRightButton(NetworkBundle.getString("StartLobby"),
                                           new ActionListener() {
                                               @Override
                                               public void actionPerformed (ActionEvent arg0) {
                                                   if (myLobbyInfo.canStartGame()) {
                                                       startGame();
                                                   }
                                               }
                                           });
    }

    private void requestLobbies () {
        myClient.sendData(new RequestServerListMessage());
    }

    private void requestJoinLobby (int id) {
        myClient.sendData(new JoinLobbyMessage(id));
    }

    private void startLobby (LobbyInfo lobbyInfo) {
        myClient.sendData(new StartLobbyMessage(lobbyInfo));
    }

    private void startGame () {
        myClient.sendData(new StartGameMessage());
    }

    private void sendUpdatedLobbyInfo () {
        myClient.sendData(new UpdateLobbyInfoMessage(myLobbyInfo));
    }
    
    public JPanel getView () {
        return myContainerPanel;
    }

    @Override
    public void addLobbies (LobbyInfo[] lobbies) {
        myServerBrowserView.addLobbies(lobbies);
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
        Player userPlayer = new Player(myUserName, 1, myFactions[0], playerID);
        myUserControlledPlayers.clear();
        myUserControlledPlayers.add(userPlayer);
        lobbyInfo.addPlayer(userPlayer);
        switchToLobbyView(lobbyInfo);
    }

    @Override
    public void updateLobby (ExpandedLobbyInfo lobbyInfo) {
        myLobbyInfo = lobbyInfo;
        myLobbyView.update(myUserControlledPlayers, myLobbyInfo.getPlayers());
    }
}
