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
    private ActionListener myHostGameListener;
    private ActionListener myJoinGameListener;
    private ActionListener myBackToBrowserListener;
    private ActionListener myStartLobbyListener;
    private ActionListener myLeaveLobbyListener;
    private ActionListener myStartGameListener;

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
        initializeActionListeners();
        myServerBrowserView = new TableContainerView(myServerBrowserAdapter);
        myCreateLobbyView = new CreateLobbyView(maps, maxPlayerArray);
        switchToServerBrowserView();
    }

    /**
     * Instantiates the listeners. This is a utility method so that there is not too much code in
     * the "switch" methods.
     */
    private void initializeActionListeners () {

        myHostGameListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                switchToCreateLobbyView();
            }
        };

        myJoinGameListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                if (myServerBrowserView.hasSelectedRow()) {
                    myModel.requestJoinLobby(myServerBrowserAdapter
                            .getIdOfRow(myServerBrowserView
                                    .getSelectedRow()));
                }
            }
        };

        myBackToBrowserListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                switchToServerBrowserView();
            }
        };

        myStartLobbyListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                if (myCreateLobbyView.allItemsChosen()) {
                    myModel.startLobby(myCreateLobbyView
                            .getLobbyInfo());
                }
            }
        };

        myLeaveLobbyListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myModel.getLobbyInfo()
                        .removePlayer(myModel.getPlayersInfo()
                                .get(0));
                myModel.leaveLobby();
                switchToServerBrowserView();
            }
        };

        myStartGameListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                if (myModel.getLobbyInfo().canStartGame()) {
                    myModel.requestStartGame();
                }
            }
        };
    }

    /**
     * Switches the current View to the ServerBrowser.
     */
    private void switchToServerBrowserView () {
        myModel.requestLobbies();
        myContainerPanel.changeView(myServerBrowserView, NetworkBundle.getString("ServerBrowser"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("HostGame"),
                                          myHostGameListener);
        myContainerPanel.changeRightButton(NetworkBundle.getString("JoinGame"),
                                           myJoinGameListener);
    }

    /**
     * Switches the current View to the LobbyCreatorScreen.
     */
    private void switchToCreateLobbyView () {
        myContainerPanel.changeView(myCreateLobbyView, NetworkBundle.getString("LobbyCreation"));
        myContainerPanel.changeLeftButton(NetworkBundle.getString("BackToBrowser"),
                                          myBackToBrowserListener);
        myContainerPanel.changeRightButton(NetworkBundle.getString("StartLobby"),
                                           myStartLobbyListener);
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
                                          myLeaveLobbyListener);
        myContainerPanel.changeRightButton(NetworkBundle.getString("StartLobby"),
                                           myStartGameListener);
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
