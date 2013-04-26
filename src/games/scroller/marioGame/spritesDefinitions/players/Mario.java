package games.scroller.marioGame.spritesDefinitions.players;

import java.awt.Dimension;
import util.Location;
import util.Vector;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import vooga.scroller.level_management.IInputListener;
import vooga.scroller.marioGame.spritesDefinitions.MarioLib;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.animation.movement.MoveLeft;
import vooga.scroller.sprites.animation.movement.MoveRight;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.ISpriteView;
import vooga.scroller.util.Pixmap;
import vooga.scroller.util.physics.Force;
import vooga.scroller.util.physics.Gravity;
import vooga.scroller.view.GameView;

@InputClassTarget
public class Mario extends Player implements IInputListener{


    private static final String CONTROLS_FILE_PATH = "games/scroller/marioGame/controls/MarioMapping";
   
    
    private static final int MAX_JUMPS = 2;
    private static final int MAX_HORIZONTAL_SPEED = 5;
    private static final Pixmap DEFAULT_IMAGE = MarioLib.makePixmap("mario_stand_right.gif");
//    private static final int DEATH_PENALTY = 1000;


    private static final Vector JUMP_VELOCITY = new Vector(Sprite.UP_DIRECTION, 100);


    //private static final double MOVE_MAGNITUDE = 10;


    private static final double MAX_SPEED = 300;


    private static final ISpriteView MOVE_LEFT = MarioLib.makePixmap("mario_move_left.gif");


    private static final ISpriteView STAND_LEFT = MarioLib.makePixmap("mario_stand_left.gif");


    private static final double SPEED = 100;


    private static final ISpriteView MOVE_RIGHT = MarioLib.makePixmap("mario_move_right.gif");


    private static final ISpriteView STAND_RIGHT = MarioLib.makePixmap("mario_stand_right.gif");

   
    private int myJumpCount;
    private Force myGravity;


    public Mario (Location center, Dimension size, GameView gameView, ScrollingManager sm) {
        super(DEFAULT_IMAGE, center, size, gameView, sm, new Integer(1), new Integer (1));
        //MarioLib.addLeftRightAnimationToPlayer(this, "mario.gif");
        myJumpCount = 0;
        myGravity = new Gravity(this);
        
        
        intializeStates();

    }

    /**
     * Initialize all  possible states, including movement for mario.
     */
    private void intializeStates () {
        this.addPossibleState(MoveLeft.STATE_ID, new MoveLeft(MOVE_LEFT, STAND_LEFT, SPEED));
        this.addPossibleState(MoveRight.STATE_ID, new MoveRight(MOVE_RIGHT, STAND_RIGHT, SPEED));
    }

    @Override
    public void update (double elapsedTime, Dimension bounds) {
        myGravity.apply();

        if (myJumpCount == MAX_JUMPS && this.getVelocity().getComponentVector(Sprite.UP_DIRECTION).getMagnitude() < .5) {
            myJumpCount = 0;
        }
        
        super.update(elapsedTime, bounds);
        checkSpeed();
    }

    private void checkSpeed () {
        double speed = this.getVelocity().getMagnitude();       
        if(speed > MAX_SPEED){
            double angle = this.getVelocity().getDirection();
            this.setVelocity(angle, MAX_SPEED);
        }       
    }

    @Override
    public void handleDeath () {
        
        
    }

    public Player getPlayer () {
        return this;
    }   
    
    @Override
    public String getInputFilePath () {
        return CONTROLS_FILE_PATH;
    }

    @InputMethodTarget(name = "leftstart")
    public void walkLeft() {
        System.out.println("start");
        this.activateState(MoveLeft.STATE_ID);
    }
    
    @InputMethodTarget(name = "leftend")
    public void stopLeft() {        
        System.out.println("end");
        this.deactivateState(MoveLeft.STATE_ID);
    }
    
    @InputMethodTarget(name = "rightstart")
    public void walkRight() {
        this.activateState(MoveRight.STATE_ID);
    }
    
    @InputMethodTarget(name = "rightend")
    public void stopRight() {
        this.deactivateState(MoveRight.STATE_ID);
    }
    
    @InputMethodTarget(name = "jump")
    public void jump() {
        if(this.getVelocity().getComponentVector(Sprite.UP_DIRECTION).getMagnitude() < .5 &&
            this.getVelocity().getComponentVector(Sprite.DOWN_DIRECTION).getMagnitude() < .5 && myJumpCount < MAX_JUMPS ) {           
            addVector(JUMP_VELOCITY);
            myJumpCount +=1;
        }
    }

    
    public void incrementScore (int increment) {
        // TODO Auto-generated method stub
        
    }
}



