package vooga.scroller.marioGame.spritesDefinitions.physics;

import java.util.ArrayList;
import java.util.List;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.physics.Force;
import vooga.scroller.util.physics.Gravity;
import vooga.scroller.util.physics.Viscosity;

public class ForceBundle {
    
    List<Force> forces;
    
    public ForceBundle (Sprite sprite) {
        forces = new ArrayList<Force>();
        forces.add(new Gravity(sprite));
        forces.add(new Viscosity(sprite));
    }
    
    public void apply() {
        for (Force force : forces) {
            force.apply();
        }
    }

}
