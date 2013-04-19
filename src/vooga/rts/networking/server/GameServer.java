package vooga.rts.networking.server;

import vooga.rts.networking.communications.GameMessage;
import vooga.rts.networking.communications.Message;


/**
 * Server that represents one instance of a in-play game.. It receives information from game clients
 * and AI and
 * sends pushes changes to all other clients
 * 
 * @author Henrique Moraes
 * @author Sean Wareham
 * @author David Winegar
 * 
 */
public class GameServer extends Room {

    /**
     * Instantiates the gameServer with all the connections passed in by a lobby.
     * @param id id of the gameserver
     * @param container gamecontainer of the gameserver
     * @param lobby lobby to get connections from
     */
    public GameServer (int id, GameContainer container, Lobby lobby) {
        super(id, container, lobby);
    }

    @Override
    public void receiveMessageFromClient (Message message, ConnectionThread thread) {
        super.receiveMessageFromClient(message, thread);
        if (message instanceof GameMessage) {
            sendMessageToAllConnections(message);
        }
    }
    
    @Override
    public void stampMessage (Message message) {
        // TODO timer
    }

}
