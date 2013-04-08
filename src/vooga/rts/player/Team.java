package vooga.rts.player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private int myID;
    private List<Player> myPlayers;
    
    public Team (int id) {
        myID = id;
        myPlayers = new ArrayList<Player>();
    }
    
    public void addPlayer(Player p) {
        myPlayers.add(p);
        p.setTeamID(myID);
    }
    
    public int getID() {
        return myID;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + myID;
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Team other = (Team) obj;
        if (myID != other.myID)
            return false;
        return true;
    }

}
