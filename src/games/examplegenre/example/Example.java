
package games.examplegenre.example;

import java.io.IOException;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;

public class Example extends Game {

    public Example (ArcadeInteraction arcade) {
        super(arcade);
    }
    
    @Override
    public void run () {
        String url = "http://breadfish.de/";
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            // win condition is wait 5 sec.
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // update the score before killing it 
        getArcade().getUserGameData(this).setScore(42);
        getArcade().killGame();

    }

}

