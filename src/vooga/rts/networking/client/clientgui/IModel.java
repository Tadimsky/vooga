package vooga.rts.networking.client.clientgui;

/**
 * Represents the ClientModel to the LobbyView so that it can update the Player at the given
 * position.
 * 
 * @author David Winegar
 * 
 */
public interface IModel {

    /**
     * Updates the Player at the given position with the changed faction.
     * 
     * @param faction to change to
     * @param position of player
     */
    public void updateFaction (String faction, int position);

    /**
     * Updates the Player at the given position with the changed team.
     * 
     * @param team to change to
     * @param position of player
     */
    public void updateTeam (int team, int position);
}
