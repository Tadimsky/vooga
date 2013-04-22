package vooga.rts.leveleditor.components;

import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

public abstract class MapComponent {
    protected static final String RELATIVE_PATH = "vooga.rts.leveleditor.resource.";
    
    protected int myID;
    protected String myName;
    protected String myImageName;
    protected BufferedImage myImage;
    protected ResourceBundle myResource;
    
    
    public MapComponent(String bundleName) {
        myResource = ResourceBundle.getBundle(RELATIVE_PATH+bundleName);
    }
    
    public MapComponent(String bundleName, int id) {
        this(bundleName);
        setID(id);
    }
    
    public MapComponent(int id , String name , String imageName) {
        myID = id;
        myName = name;
        myImageName = imageName;
    }

    public void setID(int id) {
        myID = id;
    }
    
    
    
    public void setMyName (String myName) {
        this.myName = myName;
    }

    public void setMyImageName (String myImageName) {
        this.myImageName = myImageName;
    }

    public void setMyImage (BufferedImage myImage) {
        this.myImage = myImage;
    }

    public int getMyID () {
        return myID;
    }

    public String getMyName () {
        return myName;
    }

    public String getMyImageName() {
        return myImageName;
    }
    
    public BufferedImage getMyImage () {
        return myImage;
    }
}
