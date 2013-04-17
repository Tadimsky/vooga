package vooga.towerdefense.arcadeinteraction;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.model.GameController;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import arcade.games.UserGameData;

/**
 * Implements abstract methods from Game class in arcade.
 *
 * @author Angelica Schwartz
 */
public class TowerDefenseGame extends Game {

    /**
     * constructor.
     */
    public TowerDefenseGame (ArcadeInteraction arcade) {
        super(arcade);
    }

    /**
     * implements abstract method specifically for TowerDefense.
     */
    @Override
    public UserGameData generateNewProfile () {
        return new TowerDefenseUserGameData();
    }

    /**
     * runs the TowerDefense game.
     */
    @Override
    public void run () {
        GameController game = new GameController(new Controller());
        game.start();
    }

}