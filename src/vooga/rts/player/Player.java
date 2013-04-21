package vooga.rts.player;

import java.awt.Graphics2D;
import vooga.rts.IGameLoop;
import vooga.rts.commands.Command;
import vooga.rts.controller.Controllable;
import vooga.rts.controller.Controller;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.manager.Manager;

public class Player implements Controller, IGameLoop {

    private Manager myManager;
    private int myTeamID;

    public Player (int id) {
        myManager = new Manager();
        myTeamID = id;
    }

    @Override
    public void sendCommand (Command command) {
        // TODO Auto-generated method stub
    }

    public Controllable getManager () {
        return myManager;
    }

    public void add (Unit unit) {
        myManager.add(unit);
    }

    @Override
    public void paint (Graphics2D pen) {
        myManager.paint(pen);
    }

    @Override
    public void update (double elapsedTime) {
        myManager.update(elapsedTime);
    }
}
