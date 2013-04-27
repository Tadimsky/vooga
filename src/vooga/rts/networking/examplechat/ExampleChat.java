package vooga.rts.networking.examplechat;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vooga.rts.networking.client.ClientModel;
import vooga.rts.networking.client.IClient;
import vooga.rts.networking.client.IMessageReceiver;
import vooga.rts.networking.client.NetworkedGame;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.PlayerInfo;
import vooga.rts.networking.communications.UserTimeStamp;


/**
 * An example client for games that wish to use networking.
 * 
 * @author David Winegar
 * 
 */
public class ExampleChat implements NetworkedGame, IChatModel, IMessageReceiver {

    private static final int ONE_MILLISECOND = 1000000;
    private static final String SEPARATOR = " : ";
    private static final int MAX_PLAYERS = 8;
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 600;
    private ClientModel myModel;
    private JFrame myFrame;
    private ChatPanel myChatPanel;
    private IClient myClient;
    private PlayerInfo myPlayer;
    private ExpandedLobbyInfo myInfo;
    private long myStartTime;

    /**
     * Starts by creating the dialog box.
     */
    public ExampleChat () {
        new UsernameDialogBox(this);
    }

    /**
     * Creates the frame for the given panel
     */
    protected void createFrame (JPanel panel) {
        myFrame = new JFrame();
        myFrame.add(panel);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        myFrame.setVisible(true);
        myFrame.pack();
    }

    @Override
    public void switchToServerBrowser (String name) {
        createServerBrowserModel(name);
        createFrame(myModel.getView());
    }

    protected void createServerBrowserModel (String name) {
        List<String> factions = new ArrayList<String>();
        factions.add("chat person");
        List<String> maps = new ArrayList<String>();
        maps.add("chat room");
        List<Integer> maxPlayers = new ArrayList<Integer>();
        maxPlayers.add(MAX_PLAYERS);
        myModel = new ClientModel(this, "Chat Room", name, factions, maps, maxPlayers);
    }

    @Override
    public void loadGame (ExpandedLobbyInfo info, PlayerInfo thisPlayer) {
        myFrame.dispose();
        myChatPanel = new ChatPanel(this);
        myPlayer = thisPlayer;
        myInfo = info;

        createFrame(myChatPanel);
    }

    @Override
    public void startGame (IClient client) {
        myClient = client;
        client.setMessageReceiver(this);
        myStartTime = System.nanoTime();
    }

    @Override
    public void getMessage (Message message) {
        if (message instanceof ChatMessage) {
            ChatMessage chat = (ChatMessage) message;
            myChatPanel.appendMessage(
                    chat.getInitialTime() + SEPARATOR +
                            myInfo.getPlayer(chat.getSender()).getName() + SEPARATOR +
                            chat.getMessage() + "\n");

        }
    }

    @Override
    public void messageEntered (String message) {
        myClient.sendData(new ChatMessage(
                                          new UserTimeStamp(
                                                            (System.nanoTime() - myStartTime) /
                                                                    ONE_MILLISECOND),
                                          message, myPlayer.getId()));
    }

    @Override
    public void connectionClosed () {
        JOptionPane.showMessageDialog(new JFrame(), "Connection Closed.");
        System.exit(0);
    }

    @Override
    public void serverBrowserClosed () {
        System.exit(0);
    }

}
