package vooga.rts.player;

import java.awt.Graphics2D;
import vooga.rts.IGameLoop;
import vooga.rts.commands.Command;
import vooga.rts.controller.Controller;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.manager.IndividualResourceManager;
import vooga.rts.manager.Manager;
import vooga.rts.resourcemanager.ResourceManager;


/**
 * Sends commands to its unit manager from either the human input, the network
 * input or the ai input.]
 * 
 * @author challenherzberg-brovold
 * 
 */
public class Player implements Controller, IGameLoop {

    protected Manager myManager;
    private IndividualResourceManager myResources;

    private int myPlayerID;
    private int myTeamID;

    public Player (int playerid, int teamID) {
        myManager = new Manager(playerid);
        myPlayerID = playerid;
        myTeamID = teamID;
        myResources = new IndividualResourceManager();
    }

    /**
     * @return the resources of the player
     */
    public IndividualResourceManager getResouces () {
        return myResources;
    }

    @Override
    public void sendCommand (Command command) {
        // TODO Auto-generated method stub
    }

    public Manager getManager () {
        return myManager;
    }

    public void add (InteractiveEntity unit) {
        unit.setPlayerID(myPlayerID);
        myManager.add(unit);
    }

    @Override
    public void paint (Graphics2D pen) {
        // myManager.paint(pen);
    }

    @Override
    public void update (double elapsedTime) {
        myManager.update(elapsedTime);
    }

    /**
     * @return the playerID
     */
    public int getPlayerID () {
        return myPlayerID;
    }

    /**
     * @return the teamID
     */
    public int getTeamID () {
        return myTeamID;
    }

}
