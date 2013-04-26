package arcade.games;
import java.awt.Component;


/**
 * All genre writers should extend this class to write their games, so that
 * it can be played in the Arcade.
 * 
 * @author ArcadeTeam
 */
public abstract class Game{
    
    private ArcadeInteraction myArcade;
    private UserGameData myUserGameData;
    
    /**
     * Constructs a game with the arcade interface and the user game data
     * 
     * @param arcade ArcadeInteraction
     */
    public Game(ArcadeInteraction arcade) {
        myArcade = arcade;
        //myUserGameData = myArcade.getUserGameData( this );
    }    
    
    
    /**
     * This method will be called by the arcade if the user has never played
     * the game before, and this data will be associated with the user currently
     * playing.   You should override this to return your specific implementation of 
     * UserGameData if your game uses an extension of this class.
     * @return the specific subclass of UserGameData for each game.
     */
    public  UserGameData generateNewProfile(){
        return new UserGameData();
    }
    
    
    /**
     * This method will be called by the arcade if no one has ever played this game 
     * and we need to generate a gamedata object. If you dont plan on using gameData objects
     * then you dont need to implement this method, but if you don't then you cant 
     *  call getGameData from ArcadeInteraction
     *  
     *  You should override this to return your specific implementation of 
     *  GameData if your game uses an extension of this class.
     * @return a new instance of GameData for each game.
     *  
     *  
     * @return the specific subclass of GameData for each game.
     */
    public  GameData generateNewGameProfile(){
        return new GameData();
    }
    /**
     * starts the game (should probably start running the game loop)
     *
     */
    public abstract void run ();
    
    /**
     * The ArcadeInteraction is the access the game has  
     * to arcade elements (e.g. user name, user's saved game data)
     *
     */
    protected ArcadeInteraction getArcade() {
        return myArcade;
    }

}
