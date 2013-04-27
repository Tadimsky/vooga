package vooga.rts.networking.server;

/**
 * Main for the Server.
 * 
 * 
 * @author David Winegar
 * 
 */
public class ServerMain {

    private ServerMain () {
        // does not make sense to construct this class
    }

    /**
     * Starts the server.
     * 
     * @param args command line arguments, not used in this main
     */
    public static void main (String[] args) {
        MatchmakerServer server = new MatchmakerServer();
        server.startAcceptingConnections();
    }
}
