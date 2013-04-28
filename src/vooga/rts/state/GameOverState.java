package vooga.rts.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.controller.Controller;
import vooga.rts.gui.menus.GameOverMenu;


public class GameOverState extends SubState implements Controller, Observer {

    GameOverMenu myGameOverMenu;

    public GameOverState (Observer observer) {
        super(observer);
        myGameOverMenu = new GameOverMenu();
        myGameOverMenu.addObserver(this);
    }

    @Override
    public void receiveCommand (Command command) {
        if (command instanceof ClickCommand) {
            ClickCommand c = (ClickCommand) command;
            myGameOverMenu.handleMouseDown(c.getPosition());
        }
    }

    @Override
    public void update (double elapsedTime) {
    }

    @Override
    public void paint (Graphics2D pen) {
        myGameOverMenu.paint(pen);
    }

    @Override
    public void sendCommand (Command command) {
    }

    @Override
    public void update (Observable o, Object arg) {
        System.exit(0);
    }

    @Override
    public void activate () {
        // TODO Auto-generated method stub

    }

}
