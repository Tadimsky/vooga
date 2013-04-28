package vooga.rts.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.logger.LoggerManager;
import vooga.rts.networking.NetworkBundle;


/**
 * A pure connection server that listens to connections, creates them, and sends them to the
 * MatchmakerServer.
 * 
 * @author David Winegar
 * 
 */
public class ConnectionServer extends Thread {

    private static final int PORT = Integer.valueOf(NetworkBundle.getConfigurationItem("port"));
    private int myConnectionID = 0;
    private MatchmakerServer myMatchServer;
    private boolean myIsServerAcceptingConnections = false;
    private Logger myLogger;

    /**
     * Creates a connection server that sends connections to the MatchmakerServer specified.
     * 
     * @param matchServer server to send connections to
     */
    public ConnectionServer (MatchmakerServer matchServer) {
        myMatchServer = matchServer;
        LoggerManager log = new LoggerManager();
        log.addHandler(AbstractThreadContainer.EMAIL_HANDLER);
        log.addTxtHandler(getClass().getSimpleName() + PORT);
        myLogger = log.getLogger();
    }

    /**
     * Starts accepting connections.
     */
    @Override
    public void run () {
        myIsServerAcceptingConnections = true;
        ServerSocket serverSocket = null;

        myLogger.log(Level.INFO, NetworkBundle.getString("ConnectionServerStarted") + PORT);
        while (myIsServerAcceptingConnections) {
            try {
                serverSocket = new ServerSocket(PORT);

                Socket socket = serverSocket.accept();
                ConnectionThread thread =
                        new ConnectionThread(socket, myMatchServer, myConnectionID, myLogger);
                myConnectionID++;
                thread.start();
                myMatchServer.addConnection(thread);
                myLogger.log(Level.INFO, NetworkBundle.getString("NewConnection") +
                                         ": " + thread.getID());
                serverSocket.close();
            }
            catch (IOException e) {
                myLogger.log(Level.SEVERE,
                             NetworkBundle.getString("ConnectionFailed"));
                myIsServerAcceptingConnections = false;
            }
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                myLogger.log(Level.SEVERE,
                             NetworkBundle.getString("ConnectionSocketFailed"));
            }
        }
    }

    /**
     * Stops accepting connections from this server and closes the socket.
     */
    protected void stopAcceptingConnections () {
        myIsServerAcceptingConnections = false;
    }

}
