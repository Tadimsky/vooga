package vooga.rts.gamedesign.action;

import java.lang.reflect.InvocationTargetException;

import vooga.rts.util.Pixmap;
/*
 * This is the abstract super class for Actions
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 */

public class Action {
    public String myName;
    public Pixmap myImage; //image of the action, probably will change. 
    public String myDescription;

    public Action(String name, Pixmap image, String description) {
        myName = name; 
        myImage = image;
        myDescription = description;
    }
    
    
    public String getDescription(){
        return myDescription;
    }
    public Pixmap getImage(){
        return myImage;
    }
    public String getName(){
        return myName;
    }
    /*
     * This is the method to execute the action. 
     * 
     */
    public void apply(int playerID) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException{
        
    }
    
    
    

}
