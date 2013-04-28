package vooga.rts.game;

import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import vooga.rts.gamedesign.factories.Factory;
import vooga.rts.resourcemanager.ImageLoader;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.resourcemanager.SoundLoader;
import vooga.rts.state.MainState;

public abstract class RTSGame extends Game {
    public static final int FPS = 60;
    
    private final static String DEFAULT_RESOURCE_LOCATION = "/vooga/rts/resources/";
    
    private MainState myState;
    private static Factory myFactory;

    public static double TIME_PER_FRAME () {
        double persecond = 1 / (double) FPS;
        return persecond;
    }

    public RTSGame (ArcadeInteraction arcade, String gameDefinition) {      
        super(arcade);        
        ResourceManager.getInstance().registerResourceLoader(new ImageLoader());
        ResourceManager.getInstance().registerResourceLoader(new SoundLoader());
        ResourceManager.getInstance().setResourceBase(DEFAULT_RESOURCE_LOCATION);
        
        // Build game with game Definition file
        //myFactory = new Factory();
       // myFactory.loadXMLFile(gameDefinition);
        
        myState = new MainState();
    }

    @Override
    public void run () {
        myState.start(); 
    }
    
    public static Factory getFactory() {
        return myFactory;
    }
}
