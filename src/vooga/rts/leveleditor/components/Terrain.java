package vooga.rts.leveleditor.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import vooga.rts.leveleditor.gui.MapPanel;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location;


/**
 * all class of terrains, which is used as a part of map
 * 
 * @author Richard Yang
 * 
 */

public class Terrain extends MapComponent {

    private static final String BUNDLE_NAME = "TerrainIndex";

    protected Location myLocation;

    private int myWalkAbility;
    private int myImageWidth;
    private int myImageHeight;

    public Terrain (Location loc, int ID) {
        super(BUNDLE_NAME);
        setType(ID);
        myLocation = loc;
    }

    public Terrain (int x, int y, int ID) {

        this(new Location(x, y), ID);
    }
    
    public Terrain(int x, int y , int ID, String name, String imageName) {
        super(ID,name,imageName);
        myLocation = new Location(x,y);
    }

    public Terrain (int i) {
        this(0, 0, i);
    }

    public void setType (int id) {
        super.setID(id);
        try {
            refreshImage();
        }
        catch (Exception e) {
        }
    }


    public void refreshImage () throws IOException {
        if (myResource.containsKey(myID + "")) {
            String content = myResource.getString(myID + "");
            String[] buffer = content.split("&");
            myName = buffer[0];
            myImageName = buffer[1];
            myWalkAbility = Integer.parseInt(buffer[2]);
            myImage =
                    ResourceManager.getInstance().<BufferedImage> getFile(myImageName,
                                                                          BufferedImage.class);
            myImageWidth = myImage.getWidth();
            myImageHeight = myImage.getHeight();
        }
    }

    public int getMyWalkAbility () {
        return myWalkAbility;
    }

    public Location getMyLocation () {
        return myLocation;
    }

    public int getMyX () {
        return (int) myLocation.getX();
    }

    public int getMyY () {
        return (int) myLocation.getY();
    }
    
    public int getImageWidth() {
        return myImageWidth;
    }
    
    public int getImageHeight() {
        return myImageHeight;
    }

    public void zoomIn() {
        myLocation = new Location(myLocation.getX()*MapPanel.ZOOM_RATE,myLocation.getY()*MapPanel.ZOOM_RATE);
        myImageWidth = (int)(myImageWidth*MapPanel.ZOOM_RATE);
        myImageHeight = (int)(myImageHeight*MapPanel.ZOOM_RATE);
    }
    
    public void zoomOut() {
        myLocation = new Location(myLocation.getX()/MapPanel.ZOOM_RATE,myLocation.getY()/MapPanel.ZOOM_RATE);
        myImageWidth = (int)(myImageWidth/MapPanel.ZOOM_RATE);
        myImageHeight = (int)(myImageHeight/MapPanel.ZOOM_RATE);     
    }

    public void paint (Graphics pen) {
        pen.drawImage(myImage, getMyX(), getMyY(), myImageWidth,myImageHeight,null);
    }

}
