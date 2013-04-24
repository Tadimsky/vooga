package vooga.rts.gui.menus.gamesubmenus;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gui.Window;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location;


public abstract class SubMenu extends Observable implements Observer {

    protected InteractiveEntity mySelectedEntity;
    private BufferedImage myBGImage;
    private Dimension mySize;
    private Location myPos;
    private boolean isFocused;

    protected static final int S_X = (int) Window.SCREEN_SIZE.getWidth();
    protected static final int S_Y = (int) Window.SCREEN_SIZE.getHeight();

    public SubMenu (String image, Dimension size, Location pos) {
        myBGImage =
                ResourceManager.getInstance().<BufferedImage> getFile(image,
                                                                      BufferedImage.class);
        mySize = size;
        myPos = pos;
    }

    public void setSelectedEntity (InteractiveEntity i) {
        mySelectedEntity = i;
    }

    public void paint (Graphics2D pen) {
        if (myBGImage != null) {
            pen.drawImage(myBGImage, (int) myPos.x, (int) myPos.y, mySize.width, mySize.height, null);
        }
    }

    public boolean checkWithinBounds (int x, int y) {
        return (x > myPos.x && y > myPos.y && x < (myPos.x + mySize.width) && y < (myPos.y + mySize.height));
    }

    public boolean checkWithinBounds (Location l) {
        return checkWithinBounds((int) l.getX(), (int) l.getY());
    }

    public abstract void processClick (int x, int y);

    public abstract void processHover (int x, int y);

    public  void update (double elapsedTime) {};

    public void setFocused (boolean b) {
        isFocused = b;
    }
    
    public void processClick () {
        // TODO Auto-generated method stub
        
    }

}
