package vooga.rts.player;

import vooga.rts.networking.client.Player;

public class RTSPlayer extends Player {
    //TODO: get this up to sync with new player type
    private String myFaction;
    
    public RTSPlayer (String name, String faction) {
        super(name);
        myFaction = faction;
        
    }
    
    public RTSPlayer(String name) {
        super(name);
    }
    
    
    public String getFaction() {
        return myFaction;
    }

    public void setFaction(String faction) {
        myFaction = faction;
    }
    
    public String toString() {
        return myName;
    }
}
