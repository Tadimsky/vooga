package vooga.scroller.test_sprites;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import vooga.rts.util.Vector;
import vooga.scroller.level_editor.ISpriteLibrary;
import vooga.scroller.sprite_superclasses.NonStaticEntity;
import vooga.scroller.sprite_superclasses.Player;
import vooga.scroller.sprite_superclasses.StaticEntity;
import vooga.scroller.util.Location;
import vooga.scroller.util.Pixmap;
import vooga.scroller.util.Sprite;

/**
 * This class is a convenient way to gather all the classes and use reflection
 * to retrieve all classes. Not sure if this is the best way to implement this.
 * But it is definitely better than having to parse the package. -DF
 * The Classes are static to allow instantiation w/o an instance of MarioLib.
 * TODO - Decide Whether to keep this implementation or switch to ENUM
 * Moreover, it is an example of the sprite-specification file the game designer
 * will need to provide.
 */
public class MarioLib implements ISpriteLibrary {
    private static final Dimension DEFAULT_SIZE = new Dimension(30, 30);
    private static final Location DEFAULT_LOC = new Location(30, 30);

    
    public static class Coin extends StaticEntity {

        private static final String DEFAULT_IMG = "coin.gif";
        
        public Coin() {
            this(DEFAULT_LOC);
        }
        
        public Coin (Location center) {
            super(new Pixmap(DEFAULT_IMG), center, DEFAULT_SIZE);
        }

        
        public void print() {
            System.out.println("Coin");
        }
    }
    
    public static class Koopa extends NonStaticEntity {
        
        private static final String DEFAULT_IMG ="koopa.gif";
        
        public Koopa() {
            this(DEFAULT_LOC);
        }

        public Koopa (Location center) {
            super(new Pixmap(DEFAULT_IMG), center, DEFAULT_SIZE);
        }

        public void print() {
            System.out.println("Koopa");
        }
        
        public void update(double elapsedTime, Dimension bounds) {
            changeVelocity(trackPlayer(45, 100)); //want to make this call every X seconds
            super.update(elapsedTime, bounds);
        }
        
    }

    public static class Turtle extends NonStaticEntity {

        private static final String DEFAULT_IMG = "turtle.gif";

        public Turtle() {
            this(DEFAULT_LOC);
        }
        
        public Turtle (Location center) {
            super(new Pixmap(DEFAULT_IMG), center, DEFAULT_SIZE);
            // TODO Auto-generated constructor stub
        }

        public void print() {
            System.out.println("Turtle");
        }
        
        public void update(double elapsedTime, Dimension bounds) {
            //changeVelocity(trackPlayer(70, 150)); //want to make this call every X seconds
            super.update(elapsedTime, bounds);
        }

    }

    public static class Platform extends StaticEntity{
        
        private static final String DEFAULT_IMG = "platform.gif";
        
        public Platform() {
            this(DEFAULT_LOC);
        }

        public Platform (Location center) {
            super(new Pixmap(DEFAULT_IMG), center, DEFAULT_SIZE);
        }
    
        public void print() {
            System.out.println("Platform");
        }    
    }
    
    public static class MovingPlatform extends NonStaticEntity {
        
        private static final String DEFAULT_IMG = "platform.gif";

        public MovingPlatform() {
            this(DEFAULT_LOC);
        }
        
        public MovingPlatform (Location center) {
            super(new Pixmap(DEFAULT_IMG), center, new Dimension(100, 30));
        }
        
        public void update(double elapsedTime, Dimension bounds) {
            System.out.println(getTop() + " " + getBottom());
            changeVelocity(upAndDown(0, 200, 60)); //want to make this call every X seconds
            super.update(elapsedTime, bounds);
        }
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Sprite>[] getSpritesClasses() {
        return (Class<? extends Sprite>[]) this.getClass().getClasses();
    }

}