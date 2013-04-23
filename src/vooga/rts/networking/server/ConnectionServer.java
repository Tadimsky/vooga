package vooga.rts.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import vooga.rts.networking.logger.NetworkLogger;


/**
 * A pure connection server that listens to connections, creates them, and sends them to the
 * MatchmakerServer.
 * 
 * @author David Winegar
 * 
 */
public class ConnectionServer extends Thread {

    private static final int PORT = 55308;
    private int myConnectionID = 0;
    private MatchmakerServer myMatchServer;
    private boolean myServerAcceptingConnections = false;

    /**
     * Creates a connection server that sends connections to the MatchmakerServer specified.
     * @param matchServer server to send connections to
     */
    public ConnectionServer (MatchmakerServer matchServer) {
        myMatchServer = matchServer;
    }

    /**
     * Starts accepting connections.
     */
    @Override
    public void run () {
        myServerAcceptingConnections = true;
        ServerSocket serverSocket = null;

        while (myServerAcceptingConnections) {
            try {
                serverSocket = new ServerSocket(PORT + myConnectionID);
                
                Socket socket = serverSocket.accept();
                ConnectionThread thread =
                        new ConnectionThread(socket, myMatchServer, myConnectionID);
                myConnectionID++;
                thread.start();
                myMatchServer.addConnection(thread);
                NetworkLogger.logMessage(Level.FINEST, "New connection joined");
                serverSocket.close();
            }
            catch (IOException e) {
                NetworkLogger.logMessage(Level.SEVERE, "Connection Server failed");
                myServerAcceptingConnections = false;
            }
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                NetworkLogger.logMessage(Level.SEVERE, "Connection serversSocket close failed");
            }
        }
    }
    
    /**
     * Stops accepting connections from this server and closes the socket.
     */
    protected void stopAcceptingConnections () {
        myServerAcceptingConnections = false;
    }

}
