
package vooga.scroller.test_sprites;

import java.awt.Dimension;
import java.util.List;
import vooga.scroller.design_patterns.State;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprite_superclasses.NonStaticEntity;
import vooga.scroller.sprite_superclasses.Player;
import vooga.scroller.sprite_superclasses.StaticEntity;
import vooga.scroller.util.Location;
import vooga.scroller.util.Pixmap;
import vooga.scroller.view.View;

public class Mario extends Player {

<<<<<<< HEAD
    private Mario_CH collisionHandler;
    List<MainState> myStates;    
    MainState currentState; 
=======
    List<State> myStates;    
    State currentState; 
>>>>>>> master
    View myView;
    private Location myOriginalCenter;
    private Dimension mySize;
    private Pixmap myImage;
    
    

    public Mario (Pixmap image, Location center, Dimension size, View view, ScrollingManager sm) {
        super(image, center, size, view, sm);
        myView = view;
        myOriginalCenter = center;
        mySize = size;
        myImage = image;
        // TODO Auto-generated constructor stub
    }

    public void print() {
        System.out.println("Mario");
    }


   
    
    
    public void update(double elapsedTime, Dimension bounds) {
//        Commented out for scrolling testing
//        currentState.update();
        // move based on input
//      ONLY FOR TESTING
        int key = myView.getLastKeyPressed();
        if (key == MOVE_LEFT)
        {
            translate(LEFT_VELOCITY);
        }
        if (key == MOVE_RIGHT)
        {
            translate(RIGHT_VELOCITY);
        }
        if (key == MOVE_UP)
        {
            translate(UP_VELOCITY);
        }
        if (key == MOVE_DOWN)
        {
            translate(DOWN_VELOCITY);
        }

        super.update(elapsedTime, bounds);
        
        //        ONLY FOR TESTING
    }
   
    public void changeState(MainState newState) {
        currentState = newState;
    }
    
}



