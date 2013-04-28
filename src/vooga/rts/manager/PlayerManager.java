package vooga.rts.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.rts.networking.communications.PlayerInfo;
import vooga.rts.player.HumanPlayer;
import vooga.rts.player.Player;
import vooga.rts.player.Team;


public class PlayerManager {
    private Map<Integer, Player> myPlayers;
    private Map<Integer, Team> myTeams;
    private HumanPlayer myHuman;

    public PlayerManager () {
        myTeams = new HashMap<Integer, Team>();
        myPlayers = new HashMap<Integer, Player>();
    }

    /**
     * Adds a player to the game
     * 
     * @param player to add
     * @param teamID of the player.
     */
    public void addPlayer (Player player, int teamID) {
        myPlayers.put(player.getPlayerID(), player);
        if (myTeams.get(teamID) == null) {
            addTeam(teamID);
        }
        myTeams.get(teamID).addPlayer(player);
    }

    private void addTeam (int teamID) {
        myTeams.put(teamID, new Team(teamID));
    }

    public void addHuman (PlayerInfo info) {
        myHuman = new HumanPlayer(info.getId(), info.getTeam());
        addPlayer(myHuman, info.getTeam());
    }
    /**
     * Creates a new player with the specified team ID
     * 
     * @param teamID the team ID of the player.
     */
    public void addPlayer (PlayerInfo info) {
        addPlayer(new Player(info.getId(), info.getTeam()), info.getTeam());
    }
    
   
    /**
     * Returns a team corresponding to the team ID
     * 
     * @param teamid The Team ID
     * @return The Team
     */
    public Team getTeam (int teamid) {
        return myTeams.get(teamid);
    }

    /**
     * Returns the player that corresponds to the player ID
     * 
     * @param playerID the id of the player
     * @return
     */
    public Player getPlayer (int playerID) {
        return myPlayers.get(playerID);
    }

    /**
     * Returns the Human Player
     * 
     * @return The Human Player
     */
    public HumanPlayer getHuman () {
        return myHuman;
    }

    public Collection<Player> getAll () {
        return myPlayers.values();
    }

    public void update (double elapsedTime) {
        for (Player p : getAll()) {
            p.update(elapsedTime);
        }
    }

    public int getTeamID (int playerID) {
        return myPlayers.get(playerID).getTeamID();
    }
}
