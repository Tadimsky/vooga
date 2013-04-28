package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.logger.HandlerMail;
import util.logger.HandlerMemory;
import util.logger.IVoogaHandler;
import util.logger.LoggerManager;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.ExpandedLobbyInfo;
import vooga.rts.networking.communications.IMessage;
import vooga.rts.networking.communications.LobbyInfo;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.clientmessages.ClientInfoMessage;
import vooga.rts.networking.communications.servermessages.AlertClientMessage;


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

    /**
     * The email handler that sends this to the email.
     */
    public static final IVoogaHandler EMAIL_HANDLER =
            new HandlerMemory(new HandlerMail("vooga-networking-logger@duke.edu",
                                              new String[] { "david.s.winegar@gmail.com" },
                                              "mail.smtp.host", "Log update",
                                              "New log item received: \n"), 1, Level.SEVERE);
    private Map<Integer, ConnectionThread> myConnectionThreads =
            new HashMap<Integer, ConnectionThread>();
    private Logger myLogger;

    /**
     * Default empty constructor, initializes state and logger.
     */
    public AbstractThreadContainer () {
        LoggerManager log = new LoggerManager();
        // log.addHandler(EMAIL_HANDLER);
        myLogger = log.getLogger();
    }

    /**
     * Initializes state and uses the passed in logger.
     * 
     * @param logger to use
     */
    public AbstractThreadContainer (Logger logger) {
        myLogger = logger;
    }

    /**
     * Constructor that copies all current threads from the AbstractThreadContainer passed in and
     * uses the logger passed in.
     * 
     * @param container AbstractThreadContainer
     * @param logger to use
     */
    public AbstractThreadContainer (AbstractThreadContainer container, Logger logger) {
        this(logger);
        myConnectionThreads = new HashMap<Integer, ConnectionThread>(container.myConnectionThreads);
        for (ConnectionThread thread : myConnectionThreads.values()) {
            thread.switchMessageServer(this);
        }
    }

    /**
     * This method returns the logger for the class.
     * 
     * @return logger
     */
    protected Logger getLogger () {
        return myLogger;
    }

    @Override
    public void joinGameContainer (ConnectionThread thread, String gameName) {
        sendErrorMessage(thread);
    }

    @Override
    public void joinLobby (ConnectionThread thread, int lobbyNumber) {
        sendErrorMessage(thread);
    }

    @Override
    public void leaveLobby (ConnectionThread thread, ExpandedLobbyInfo lobbyInfo) {
        sendErrorMessage(thread);
    }

    @Override
    public void requestGameStart (ConnectionThread thread) {
        sendErrorMessage(thread);
    }

    @Override
    public void requestLobbies (ConnectionThread thread) {
        sendErrorMessage(thread);
    }

    @Override
    public void startLobby (ConnectionThread thread, LobbyInfo lobbyInfo) {
        sendErrorMessage(thread);
    }

    @Override
    public void updateLobbyInfo (ConnectionThread thread, ExpandedLobbyInfo myLobbyInfo) {
        sendErrorMessage(thread);
    }

    @Override
    public void clientIsReadyToStart (ConnectionThread thread) {
        sendErrorMessage(thread);
    }

    /**
     * Sends an error message to the client with the method that calls this one.
     * 
     * @param thread to send to
     */
    protected void sendErrorMessage (ConnectionThread thread) {
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        thread.sendMessage(new AlertClientMessage(NetworkBundle.getString("InvalidOperation"),
                                                  element[1].getMethodName()));
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
        LoggerManager.DEFAULT_LOGGER.log(Level.FINEST, NetworkBundle.getString("MessageReceived") +
                                                       thread.getID());
        stampMessage(message);
        if (message instanceof ClientInfoMessage) {
            ClientInfoMessage systemMessage = (ClientInfoMessage) message;
            systemMessage.affectServer(thread, this);
        }
    }

    /**
     * Overridable method for stamping this message called by receiveMessageFromClient.
     * 
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
    protected void sendMessageToAllConnections (IMessage message) {
        for (ConnectionThread thread : myConnectionThreads.values()) {
            thread.sendMessage(message);
        }
    }

    /**
     * Send a message to a specific connection thread.
     */
    protected void sendMessageToClient (ConnectionThread thread, IMessage message) {
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

    protected int getNumberOfConnections () {
        return myConnectionThreads.size();
    }
}
