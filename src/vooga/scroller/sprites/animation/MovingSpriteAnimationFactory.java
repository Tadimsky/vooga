package vooga.scroller.sprites.animation;

import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.Pixmap;

/**
 * Creates an animation for a left-right moving sprite. 
 * This requires at least 5 different images in the image package.
 * <br>
 * If the name of the default image is "mario.gif", then this factory needs images
 * <ul>
 * <li> mairo_stand_left.gif
 * <li> mario_move_left.gif
 * <li> mario_stand_right.gif
 * <li> mario_move_right.gif
 * <li> mario.gif
 * </ul>
 * 
 * @author Scott Valentine
 *
 */
public class MovingSpriteAnimationFactory {

    private static final String STAND_LEFT_ENDING = "_stand_left";
    private static final String MOVE_LEFT_ENDING = "_move_left";
    private static final String STAND_RIGHT_ENDING = "_stand_right";
    private static final String MOVE_RIGHT_ENDING = "_move_right";
    private static final String DEFAULT_ENDING = "";
    
    
    
    private Pixmap myDefaultView;
    private Pixmap myStandLeft;
    private Pixmap myMoveLeft;
    private Pixmap myStandRight;
    private Pixmap myMoveRight;
    
    
    
    /**
     * Ugly, but at the same time, not that bad. This lets us define generic sprite animations
     * by the views that the sprite uses. One cool thing we could do would be to take in just a 
     * string of a package path. From that, we could load in the images based on the default values 
     * for sprite image file names.
     * 
     * @param defaultView
     * @param moveLeft
     * @param standLeft
     * @param moveRight
     * @param standRight
     */
    public MovingSpriteAnimationFactory(Pixmap defaultView, Pixmap moveLeft, 
                                        Pixmap standLeft, Pixmap moveRight, 
                                        Pixmap standRight){
        myDefaultView = defaultView;
        myMoveLeft = moveLeft;
        myStandLeft = standLeft;
        myStandRight = standRight;
        myMoveRight = moveRight;
    }
    
    public MovingSpriteAnimationFactory(String fileLocation, String spriteFilePath) {
        String fileFormat = getFileFormat(spriteFilePath);
        String baseName = getBaseName(spriteFilePath);
        myDefaultView = new Pixmap(fileLocation, baseName + DEFAULT_ENDING + fileFormat);
        myStandRight = new Pixmap(fileLocation, baseName + STAND_RIGHT_ENDING + fileFormat);
        myMoveRight = new Pixmap(fileLocation, baseName + MOVE_RIGHT_ENDING + fileFormat);
        myStandLeft = new Pixmap(fileLocation, baseName + STAND_LEFT_ENDING + fileFormat);
        myMoveLeft = new Pixmap(fileLocation, baseName + MOVE_LEFT_ENDING + fileFormat);       
    }
    
    private String getBaseName (String spriteFilePath) {
        StringBuilder sb = new StringBuilder();
        String[] fileNameParts = spriteFilePath.split("[.]");
        for(int i =0 ; i< fileNameParts.length-1; ++i) {
            sb.append(fileNameParts[i]);
        }
        return sb.toString();
    }

    private String getFileFormat (String spriteFilePath) {
        String[] fileNameParts = spriteFilePath.split("[.]");
        return '.'+fileNameParts[fileNameParts.length-1];
    }

    /**
     * Creates a new animation specific to a sprite that represents the sprite 
     * moving left and right.
     * @param sprite is the sprite on which the animation will apply
     * @return The animation to be set in the sprite's ISpriteView.
     */
    public Animation<Sprite> generateAnimation (Sprite sprite) {       
        Animation<Sprite> result = new Animation<Sprite>(sprite);              
        AnimationState<Sprite> left = new MoveLeft(myMoveLeft, myStandLeft);
        AnimationState<Sprite> right = new MoveRight(myMoveRight, myStandRight);
        result.addAnimationState(right);
        result.addAnimationState(left);        
        result.setDefaultView(myDefaultView);        
        return result;
    }
}
