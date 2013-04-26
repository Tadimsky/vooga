package vooga.rts.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.rts.player.HumanPlayer;
import vooga.rts.player.Player;
import vooga.rts.player.Team;


public class PlayerManager {
    private List<Player> myPlayers;
    private Map<Integer, Team> myTeams;
    private HumanPlayer myHuman;

    public PlayerManager () {
        myTeams = new HashMap<Integer, Team>();
        myPlayers = new ArrayList<Player>();
    }

    /**
     * Adds a player to the game
     * 
     * @param player to add
     * @param teamID of the player.
     */
    public void addPlayer (Player player, int teamID) {
        myPlayers.add(player);
        if (myTeams.get(teamID) == null) {
            addTeam(teamID);
        }
        myTeams.get(teamID).addPlayer(player);
    }

    private void addTeam (int teamID) {
        myTeams.put(teamID, new Team(teamID));
    }

    /**
     * Creates a new player with the specified team ID
     * 
     * @param teamID the team ID of the player.
     */
    public void addPlayer (int teamID) {
        Player result;
        if (myPlayers.size() == 0) {
            myHuman = new HumanPlayer(0, teamID);
            result = myHuman;
        }
        else {
            result = new Player(myPlayers.size(), teamID);
        }
        addPlayer(result, teamID);
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

    public List<Player> getAll () {
        return myPlayers;
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
