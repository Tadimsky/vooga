package vooga.rts.networking.client.clientgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Class used as a meddler between the client view and model
 * It manages the necessary operations on the model given the
 * user input and keeps the display information up to date
 * in case data comes from the network
 * 
 * @author Henrique Moraes
 * 
 */
public class ClientViewAdapter extends ViewAdapter {
    private TableContainerView myServerBrowserView;
    private CreateLobbyView myCreateLobbyView;
    private LobbyView myLobbyView;
    private ServerBrowserTableAdapter myServerBrowserAdapter = new ServerBrowserTableAdapter();

    /**
     * Constructor for this class.
     * 
     * @param model
     * @param gameName
     * @param factions
     * @param maps
     * @param maxPlayerArray
     */
    public ClientViewAdapter (IModel model,
                              String gameName, List<String> maps,
                              List<Integer> maxPlayerArray) {
        super(model, gameName);
        myServerBrowserView = new TableContainerView(myServerBrowserAdapter);
        myCreateLobbyView = new CreateLobbyView(maps, maxPlayerArray);
        switchToServerBrowserView();
    }

    /**
     * Switches the current View to the ServerBrowser.
     */
    private void switchToServerBrowserView () {
        myModel.requestLobbies();
        myContainerPanel.changeView(myServerBrowserView, NetworkBundle.getString("ServerBrowser"));
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
                                                   if (myServerBrowserView.hasSelectedRow()) {
                                                       myModel.requestJoinLobby(myServerBrowserAdapter
                                                               .getIdOfRow(myServerBrowserView
                                                                       .getSelectedRow()));
                                                   }
                                               }
                                           });
    }

    /**
     * Switches the current View to the LobbyCreatorScreen.
     */
    private void switchToCreateLobbyView () {
        myContainerPanel.changeView(myCreateLobbyView, NetworkBundle.getString("LobbyCreation"));
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
                                                       myModel.startLobby(myCreateLobbyView
                                                               .getLobbyInfo());
                                                   }
                                               }
                                           });
    }

    /**
     * Switches the current view to the Lobby.
     */
    public void switchToLobbyView (ExpandedLobbyInfo lobbyInfo) {
        myLobbyView = new LobbyView(myModel, myModel.getFactions(), lobbyInfo.getMaxPlayers());
        myModel.updateLobby(lobbyInfo);
        myModel.sendUpdatedLobbyInfo();

        myContainerPanel.changeView(myLobbyView, NetworkBundle.getString("LobbyCreation"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("LeaveLobby"),
                                          new ActionListener() {
                                              @Override
                                              public void actionPerformed (ActionEvent arg0) {
                                                  myModel.getLobbyInfo()
                                                          .removePlayer(myModel.getPlayersInfo()
                                                                  .get(0));
                                                  myModel.leaveLobby();
                                                  switchToServerBrowserView();
                                              }
                                          });
        myContainerPanel.changeRightButton(NetworkBundle.getString("StartLobby"),
                                           new ActionListener() {
                                               @Override
                                               public void actionPerformed (ActionEvent arg0) {
                                                   if (myModel.getLobbyInfo().canStartGame()) {
                                                       myModel.requestStartGame();
                                                   }
                                               }
                                           });
    }

    /**
     * 
     * @param lobbies array with LobbyInfo that should update
     *        the ServerBrowserAdapter
     */
    public void changeLobbies (LobbyInfo[] lobbies) {
        myServerBrowserAdapter.changeLobbies(lobbies);
    }

    /**
     * Updates the information on the LobbyView according to the
     * model's current information
     */
    public void updateLobby () {
        myLobbyView.update(myModel.getPlayersInfo(),
                           myModel.getLobbyInfo().getPlayers());
    }
}
