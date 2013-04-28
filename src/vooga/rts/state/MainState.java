package vooga.rts.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import util.input.Input;
import vooga.rts.commands.Command;
import vooga.rts.controller.InputController;
import vooga.rts.game.RTSGame;
import vooga.rts.gui.Window;
import vooga.rts.gui.menus.MultiMenu;
import vooga.rts.util.Scale;
import vooga.rts.util.TimeIt;


/**
 * The main state of the game. It keeps track of which sub-state the game is in
 * (Loading, menu or game state), and switches between them as needed.
 * 
 * @author challenherzberg-brovold
 * 
 */

public class MainState implements State, Observer {

    private final static String DEFAULT_INPUT_LOCATION = "vooga.rts.resources.properties.Input";
    private Window myWindow;
    private Map<SubState, SubState> myStates;

    private SubState myActiveState;
    private Timer myTimer;
    private InputController myController;
    private boolean myReady;

    public MainState () {
        myReady = false;
        myStates = new HashMap<SubState, SubState>();

        myWindow = new Window();
        myWindow.setFullscreen(true);
        LoadingState loader = new LoadingState(this);
        setActiveState(loader);
        render();

        MenuState menu = new MenuState(this, getWindow().getJFrame());
        myStates.put(loader, menu);
        GameState game = new GameState(this);
        myStates.put(menu, game);
        myStates.put(game, menu);

        Input input = new Input(DEFAULT_INPUT_LOCATION, myWindow.getCanvas());
        myController = new InputController(this);
        input.addListenerTo(myController);
    }

    @Override
    public void receiveCommand (Command command) {
        myActiveState.receiveCommand(command);
    }

    @Override
    public void update (double elapsedTime) {
        myController.processCommands();
        myActiveState.update(elapsedTime);
    }

    @Override
    public void paint (Graphics2D pen) {
        Scale.scalePen(pen);
        myActiveState.paint(pen);
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof LoadingState) {
            MenuState m = new MenuState(this, myWindow.getJFrame());
            setActiveState(m);
            m.setMenu(0);
            
        }
        else if (o instanceof MenuState) {
            setActiveState(new GameState(this));
        } else if (o instanceof GameState) {
            setActiveState(new GameOverState(this));
        }
    }

    /**
     * Sets the substate of the game to the next one.
     */
    private void setActiveState (SubState s) {
        myActiveState = s;
    }

    private void render () {

        Graphics2D graphics = myWindow.getCanvas().getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, myWindow.getCanvas().getWidth(), myWindow.getCanvas().getHeight());
        
        if (myActiveState instanceof MenuState) {
            MenuState m = (MenuState) myActiveState;
            if (m.getCurrentMenu() instanceof MultiMenu) {
                myWindow.getCanvas().repaint();
                return;
            }
        }
        paint(graphics);
        myWindow.getCanvas().render();
    }

    /**
     * Returns whether the Main State is ready yet.
     * This means that all the sub states have been created
     * and the timer is ticking.
     * 
     * @return the ready state
     */
    public boolean isReady () {
        return myReady;
    }

    /**
     * @return the window
     */
    public Window getWindow () {
        return myWindow;
    }

    public void start () {
        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            private long lastNano = System.nanoTime();

            @Override
            public void run () {
                long curNano = System.nanoTime();
                double change = curNano - lastNano;
                change /= 1000000000;
                update(change);
                if (myWindow.hasFocus()) {
                    render();
                }
                lastNano = curNano;

            }
        }, 500, (long) (RTSGame.TIME_PER_FRAME() * 1000));
        myReady = true;
    }

    public void stop () {
        myTimer.cancel();
    }
}
