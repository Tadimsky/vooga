package games.example;

import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import arcade.games.UserGameData;

public class Example extends Game {

    public Example (ArcadeInteraction arcade) {
        super(arcade);
    }

    @Override
    public UserGameData generateNewProfile () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run () {
        System.out.println("running the game");

    }

}
