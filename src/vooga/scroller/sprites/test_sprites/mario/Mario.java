package vooga.scroller.sprites.test_sprites.mario;

import java.awt.Dimension;
import util.Location;
import util.Vector;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import vooga.scroller.level_management.IInputListener;
import vooga.scroller.scrollingmanager.ScrollingManager;
import vooga.scroller.sprites.interfaces.IPlatform;
import vooga.scroller.sprites.interfaces.IPlayer;
import vooga.scroller.sprites.superclasses.NonStaticEntity;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.Pixmap;
import vooga.scroller.util.Sprite;
import vooga.scroller.view.View;

@InputClassTarget
public class Mario extends Player implements IPlayer, IInputListener{


    private static final String CONTROLS_FILE_PATH = "vooga/scroller/resources/controls/MarioMapping";
    private static final String MULTIPLAYER_FILE_PATH ="vooga/scroller/resources/controls/MultiPlayerMapping";
    
    
    private static final int MAX_JUMPS = 2;
    private static final int MAX_HORIZONTAL_SPEED = 5;
    private static final Pixmap DEFAULT_IMAGE = new Pixmap("standright.png");
    private static final int DEATH_PENALTY = 1000;

   
    private static int health = 5;
    private int myJumpCount;

    public Mario (Location center, Dimension size, View view, ScrollingManager sm) {
        super(DEFAULT_IMAGE, center, size, view, sm, health);
        myJumpCount = 0;
    }

    public void print() {
        System.out.println("Mario");
    }
    
    public void hit(NonStaticEntity nse){
        int hit = nse.getHit();
        takeHit(hit);
    }

    public int getHP () {
        return health;
    }
    
    public void scorePoints(int value) {
        this.getStatistic().addValue(value);
    }
    
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        if (myJumpCount == MAX_JUMPS && this.getVelocity().getComponentVector(Sprite.UP_DIRECTION).getMagnitude() < .5) {
            myJumpCount = 0;
        }
        
        double leftMag = this.getVelocity().getComponentVector(Sprite.LEFT_DIRECTION).getMagnitude();
        double rightMag = this.getVelocity().getComponentVector(Sprite.RIGHT_DIRECTION).getMagnitude();
        
        if( leftMag > MAX_HORIZONTAL_SPEED){
            Vector left = new Vector(LEFT_DIRECTION,MAX_HORIZONTAL_SPEED- leftMag);
            this.addVector(left);
        }
        
        if( rightMag > MAX_HORIZONTAL_SPEED){
            Vector right= new Vector(RIGHT_DIRECTION,MAX_HORIZONTAL_SPEED- rightMag);
            this.addVector(right);
        }
        super.update(elapsedTime, bounds);
    }

    @Override
    public void handleDeath () {
        this.setCenter(this.getOriginalLocation().x, this.getOriginalLocation().y);
        takeDeathPenalty();
    }

    private void takeDeathPenalty () {
        this.getStatistic().removeValue(DEATH_PENALTY);
    }

    public int getHealth () {
        // TODO Auto-generated method stub
        return 1;
    }

    public void takeHit (int health2) {
        // TODO Auto-generated method stub
        
    }
    
    @InputMethodTarget(name = "left")
    public void walkLeft() {        
        Vector force = this.getVelocity().getComponentVector(Player.RIGHT_DIRECTION);
        force.negate();
        this.addVector(force);       
        this.addVector(Player.LEFT_VELOCITY);
        this.translate(Player.LEFT_VELOCITY);
    }
    
    @InputMethodTarget(name = "right")
    public void walkRight() {
        // TODO: set max speed for player
        Vector force = this.getVelocity().getComponentVector(Player.LEFT_DIRECTION);
        force.negate();
        this.addVector(force);        
        this.addVector(Player.RIGHT_VELOCITY);
        this.translate(Player.RIGHT_VELOCITY);
    }
    
    @InputMethodTarget(name = "jump")
    public void jump() {
        if(this.getVelocity().getComponentVector(Sprite.UP_DIRECTION).getMagnitude() < .5 &&
            this.getVelocity().getComponentVector(Sprite.DOWN_DIRECTION).getMagnitude() < .5 && myJumpCount < MAX_JUMPS ) {           
            this.addVector(UP_VELOCITY);
            myJumpCount +=1;
        }
    }

    @Override
    public String getInputFilePath () {
        return MULTIPLAYER_FILE_PATH;
    } 
}



