package vooga.fighter.model.objects;

import vooga.fighter.model.MenuMode;
import vooga.fighter.model.loaders.MenuLoader;

/**
 * 
 * @author Jerry and Jack
 *
 */
public class MenuObject extends GameObject {
    private String myNext;
    private String myValue;
    private int myGridNum;
    private int myUp;
    private int myDown;
    private int myLeft;
    private int myRight;
    
    MenuMode myDelegate;
    


    public MenuObject (String choice, MenuMode delegate, String pathHierarchy) {
        setLoader(new MenuLoader(choice, this, pathHierarchy));
        myDelegate = delegate;

    }

    @Override
    public void completeUpdate () {

    }
    
    public int getUp(){
    	return myUp;
    }
    
    public int getDown(){
    	return myDown;
    }
    public int getLeft(){
    	return myLeft;
    }
    
    public int getRight(){
    	return myRight;
    }
    
    public int getNum(){
    	return myGridNum;
    }
    
    public void setUp(int up){
    	myUp = up;
    }
    
    public void setDown(int down){
    	myDown = down;
    }
    
    public void setRight(int right){
    	myRight = right;
    }
    
    public void setLeft(int left){
    	myLeft = left;
    }
    
    public void setNum(int num){
    	myGridNum = num;
    }
    
    public String getValue() {
        return myValue;
    }
    
    public void setValue(String value) {
        myValue = value;
    }
    
    public void setNext(String next) {
        myNext = next;
    }
    
    public String getNext(){
    	return myNext;
    }
    
    public void tellDelegate(){
    	myDelegate.setChoice(myValue);

    }

}
