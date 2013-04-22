package vooga.rts.networking.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vooga.rts.networking.client.GUI.CreateLobbyView;
import vooga.rts.networking.client.GUI.ServerBrowserTableAdapter;
import vooga.rts.networking.client.GUI.ServerBrowserView;
import vooga.rts.networking.client.GUI.ViewContainerPanel;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.clientmessages.InitialConnectionMessage;
import vooga.rts.networking.communications.clientmessages.RequestServerListMessage;
import vooga.rts.networking.communications.servermessages.ServerInfoMessage;

/**
 * Model for the overall server browser on the client.
 * @author David Winegar
 *
 */
public class ClientModel implements IMessageReceiver, IClientModel {

    //private IClient myClient = new Client(this);
    private ViewContainerPanel myContainerPanel;
    private ServerBrowserView myServerBrowserView;
    private CreateLobbyView myCreateLobbyView;
    private ServerBrowserTableAdapter myAdapter;
    
    public ClientModel (String gameName, String userName) {
        //Message initialConnection = new InitialConnectionMessage(gameName, userName);
        //myClient.sendData(initialConnection);
        myContainerPanel = new ViewContainerPanel(gameName);
        myAdapter = new ServerBrowserTableAdapter();
        myServerBrowserView = new ServerBrowserView(myAdapter);
        myCreateLobbyView = new CreateLobbyView();
        switchToServerBrowserView();
    }
    
    private JPanel getPanel () {
        return myContainerPanel;
    }
    
    private void requestLobbies () {
        //myClient.sendData(new RequestServerListMessage());
    }

    @Override
    public void getMessage (Message message) {
        if (message instanceof ServerInfoMessage) {
            ((ServerInfoMessage) message).affectClient(this);
        }
    }

    @Override
    public void closeConnection () {
        
    }
    
    public void switchToServerBrowserView () {
        // TODO resources
        myContainerPanel.changeView(myServerBrowserView, " Server Browser");
        myContainerPanel.changeLeftButton("Host Game", new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                switchToCreateLobbyView();
            }    
        });
        myContainerPanel.changeRightButton("Join Game", null);
    }
    
    public void switchToCreateLobbyView () {
        // TODO resources
        myContainerPanel.changeView(myCreateLobbyView, " Lobby Creation");
        myContainerPanel.changeLeftButton("Back to Server Browser", new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                switchToServerBrowserView();
            }    
        });
        myContainerPanel.changeRightButton("Start Lobby", null);
    }
    
    /**
     * testing
     */
    public static void main(String[] args) {
        ClientModel model = new ClientModel("Test Game", "User 1");
        
        JFrame frame = new JFrame();
        frame.add(model.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    public void addLobbies (LobbyInfo[] lobbies) {
        myAdapter.addLobbies(lobbies);
    }
}
