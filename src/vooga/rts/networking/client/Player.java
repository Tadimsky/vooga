package vooga.rts.networking.client;
/**
 * Class represents a player that can play a game
 * (can be either AI or Human)
 * Note: hopefully this will be replaced by a Player class from the arcade, if not this
 * will at a minimum serve as a handover between arcade settings and game settigns
 * @author srwareham
 * @author Henrique Moraes
 *
 */
public class Player {
    protected String myName;
    protected int myID;
    
    public Player (String name, int ID) {
        myName = name;
    }
    
    public String getName() {
        return myName;
    }
    
    public int getID() {
        return myID;
    }
    
    @Override
    public boolean equals(Object obj){
    	if (!(obj instanceof Player)) return false;
    	Player other = (Player) obj;
    	if (other.getID() == myID) return true;
    	
    	return false;
    }

}
