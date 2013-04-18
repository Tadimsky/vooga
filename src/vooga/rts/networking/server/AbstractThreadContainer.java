package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.clientmessages.ClientInfoMessage;


/**
 * Class that provides default behavior for IThreadContainer and provides other behaviors
 * for thread containers. This is the superclass for MatchmakerServer, GameContainer, Room, Lobby,
 * and GameServer. This provides default (empty) behavior for IThreadContainer and default
 * (non-empty) behavior for IMessageReceiver.
 * 
 * @author David Winegar
 * 
 */
public abstract class AbstractThreadContainer implements IThreadContainer, IMessageReceiver {

    private Map<Integer, ConnectionThread> myConnectionThreads =
            new HashMap<Integer, ConnectionThread>();

    /**
     * Default empty constructor.
     */
    public AbstractThreadContainer () {
    }

    /**
     * Constructor that copies all current threads from the AbstractThreadContainer passed in.
     * 
     * @param container AbstractThreadContainer
     */
    public AbstractThreadContainer (AbstractThreadContainer container) {
        myConnectionThreads = new HashMap<Integer, ConnectionThread>(container.myConnectionThreads);
    }

    @Override
    public void joinGameContainer (ConnectionThread thread, String gameName) {
    }

    @Override
    public void joinLobby (ConnectionThread thread, int lobbyNumber) {
    }

    @Override
    public void leaveLobby (ConnectionThread thread) {
    }

    @Override
    public void startGameServer (ConnectionThread thread) {
    }

    @Override
    public void requestLobbies (ConnectionThread thread, int startNumber, int endNumber) {
    }

    @Override
    public void removeConnection (ConnectionThread thread) {
        myConnectionThreads.remove(thread.getID());
    }

    /**
     * Receives a message and then executes default behavior, stamping it and if it is a
     * systemMessage, executing it.
     * 
     * @param message message received
     * @param thread thread received from
     */
    @Override
    public void receiveMessageFromClient (Message message, ConnectionThread thread) {
        stampMessage(message);
        if (message instanceof ClientInfoMessage) {
            ClientInfoMessage systemMessage = (ClientInfoMessage) message;
            systemMessage.execute(thread, this);
        }
    }

    /**
     * Overridable method for stamping this message called by receiveMessageFromClient.
     * ]
     */
    protected void stampMessage (Message message) {
        message.stampTime();
    }

    /**
     * Adds a connection.
     */
    protected void addConnection (ConnectionThread thread) {
        myConnectionThreads.put(thread.getID(), thread);
        thread.switchMessageServer(this);
    }

    /**
     * Send a message to all connection threads.
     */
    protected void sendMessageToAllConnections (Message message) {
        for (ConnectionThread thread : myConnectionThreads.values()) {
            thread.sendMessage(message);
        }
    }

    /**
     * Send a message to a specific connection thread.
     */
    protected void sendMessageToClient (ConnectionThread thread, Message message) {
        thread.sendMessage(message);
    }

    /**
     * Returns whether the AbstractThreadContainer has any connection threads or not.
     */
    protected boolean haveNoConnections () {
        return myConnectionThreads.isEmpty();
    }

    /**
     * Removes all connection threads.
     */
    protected void removeAllConnections () {
        myConnectionThreads.clear();
    }

}
