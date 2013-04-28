package vooga.rts.networking.client.clientgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.client.ClientModel;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Class used as a meddler between the client view and model
 * It manages the necessary operations on the model given the
 * user input and keeps the display information up to date
 * in case data comes from the network
 * 
 * @author Henrique Moraes
 * @author David Winegar
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
     * @param model model to communicate with
     * @param gameName name of game
     * @param mapNameList list of maps
     * @param maxPlayerList list of max players
     */
    public ClientViewAdapter (ClientModel model,
                              String gameName, List<String> mapNameList,
                              List<Integer> maxPlayerList) {
        super(model, gameName);
        initializeActionListeners();
        myServerBrowserView = new TableContainerView(myServerBrowserAdapter);
        myCreateLobbyView = new CreateLobbyView(mapNameList, maxPlayerList);
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
                    getMyModel().requestJoinLobby(myServerBrowserAdapter
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
                    getMyModel().startLobby(myCreateLobbyView
                            .getLobbyInfo());
                }
            }
        };

        myLeaveLobbyListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getMyModel().leaveLobby();
                switchToServerBrowserView();
            }
        };

        myStartGameListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                if (getMyModel().getLobbyInfo().canStartGame()) {
                    getMyModel().requestStartGame();
                }
            }
        };
    }

    /**
     * Switches the current View to the ServerBrowser.
     */
    private void switchToServerBrowserView () {
        getMyModel().requestLobbies();
        getView().changeView(myServerBrowserView, NetworkBundle.getString("ServerBrowser"));
        getView().changeLeftButton(NetworkBundle.getString("HostGame"),
                                          myHostGameListener);
        getView().changeRightButton(NetworkBundle.getString("JoinGame"),
                                           myJoinGameListener);
    }

    /**
     * Switches the current View to the LobbyCreatorScreen.
     */
    private void switchToCreateLobbyView () {
        getView().changeView(myCreateLobbyView, NetworkBundle.getString("LobbyCreation"));
        getView().changeLeftButton(NetworkBundle.getString("BackToBrowser"),
                                          myBackToBrowserListener);
        getView().changeRightButton(NetworkBundle.getString("StartLobby"),
                                           myStartLobbyListener);
    }

    /**
     * Switches the current view to the Lobby.
     * 
     * @param lobbyInfo info of lobby
     */
    public void switchToLobbyView (ExpandedLobbyInfo lobbyInfo) {
        myLobbyView = new LobbyView(getMyModel(), getMyModel().getFactions(), lobbyInfo.getMaxPlayers());
        getMyModel().updateLobby(lobbyInfo);
        getMyModel().sendUpdatedLobbyInfo();

        getView().changeView(myLobbyView, NetworkBundle.getString("LobbyCreation"));
        getView().changeLeftButton(NetworkBundle.getString("LeaveLobby"),
                                          myLeaveLobbyListener);
        getView().changeRightButton(NetworkBundle.getString("StartLobby"),
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
        myLobbyView.update(getMyModel().getPlayersInfo(),
                           getMyModel().getLobbyInfo().getPlayers());
    }
}
