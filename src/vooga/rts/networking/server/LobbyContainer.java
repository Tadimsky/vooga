package vooga.rts.networking.server;

import java.util.HashMap;
import java.util.Map;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.SystemMessage;
import vooga.rts.networking.factory.Command;
import vooga.rts.networking.factory.CommandFactory;

public class LobbyContainer implements ICommandable, IMessageReceiver {
    
    private Map<Integer, ConnectionThread> myConnectionThreads = new HashMap<Integer, ConnectionThread>();
    private CommandFactory myFactory;
    
    public LobbyContainer (CommandFactory factory) {
        myFactory = factory;
    }
    
    protected void addConnection (ConnectionThread thread) {
        myConnectionThreads.put(thread.getID(), thread);
    }
    
    @Override
    public void sendMessage (Message message, ConnectionThread thread) {
        if(message instanceof SystemMessage) {
            SystemMessage systemMessage = (SystemMessage) message;
            Command command = myFactory.getCommand(systemMessage.getMessage());
            command.execute(thread, this, systemMessage.getParameters());
        } else {
            
        }
    }
    
    @Override
    public void removeConnection (ConnectionThread thread) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void joinGame (ConnectionThread thread, String gameName) {        
    }
    
    @Override
    public void joinLobby (ConnectionThread thread, String lobbyName) {        
    }
    
    @Override
    public void leaveLobby (ConnectionThread thread) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void startGameServer (ConnectionThread thread) {
        // TODO Auto-generated method stub
        
    }
    
}