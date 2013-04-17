package vooga.rts.leveleditor.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import vooga.rts.input.Input;
import vooga.rts.input.InputClassTarget;
import vooga.rts.input.InputMethodTarget;
import vooga.rts.input.PositionObject;
import vooga.rts.leveleditor.components.EditableMap;
import vooga.rts.leveleditor.components.EditableNode;
import vooga.rts.util.Location;

@InputClassTarget
public class MapPanel extends JComponent {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";
    public static final String PLAYER_IMAGE_PATH = "/vooga/rts/leveleditor/resource/PlayerSign.gif";
    public static final Dimension DEFAULT_MAP_SIZE  = new Dimension (600,600);
    public static final double ZOOM_RATE = 1.25;
    public static final int DEFAULT_TILE_WIDTH = 50;
    public static final int DEFAULT_TILE_HEIGHT = 50;
    public static final int RESOURCEMODE = 1;
    public static final int PLAYERMODE = 2;

    private Canvas myCanvas;
    private EditableMap myMap;
    private Input myInput;
    private int myWidth;
    private int myHeight;
    private int myTileWidth;
    private int myTileHeight;
    private boolean myRemoveFlag;
    private int myMode;
    private BufferedImage myPlayerImage;

    public MapPanel(Canvas canvas) {
        myCanvas = canvas;
        myMap = new EditableMap();
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);
        myWidth = 0;
        myHeight = 0;
        myTileWidth = DEFAULT_TILE_WIDTH;
        myTileHeight = DEFAULT_TILE_HEIGHT;
        try {
            myPlayerImage = ImageIO.read(this.getClass().getResource(PLAYER_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPanelSize();
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension(myTileWidth*myWidth, myTileHeight*myHeight));       
    }

    @Override
    public void paintComponent (Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,myWidth*myTileWidth, myHeight*myTileHeight);


        g.setColor(Color.gray);
        for(int i=0; i<myWidth; i++) {
            g.drawLine(i*myTileWidth, 0, i*myTileWidth, myHeight*myTileHeight);
        }

        for(int j=0; j<myHeight; j++) {
            g.drawLine(0,j*myTileHeight, myWidth*myTileWidth, j*myTileHeight);
        }

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor(Color.black);
        g.drawLine(0, 0, myWidth * myTileWidth, 0);
        g.drawLine(0, 0, 0, myHeight * myTileHeight);
        g.drawLine(myWidth * myTileWidth, 0, myWidth * myTileWidth, myHeight * myTileHeight);
        g.drawLine(0, myHeight * myTileHeight, myWidth * myTileWidth, myHeight * myTileHeight);

        //paint Node
        for(int i=0; i<myMap.getWidth(); ++i) {
            for(int j=0; j<myMap.getHeight(); ++j) {
                if(myMap.getMapNode(i, j).getOccupied()){
                    try {
                        myMap.getMapNode(i, j).paint(g);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }              
            }
        }
        
        //paint Player
        for(Location c : myMap.getLocationMap().values()) {
            g.drawImage(myPlayerImage, (int)(c.getX()), (int)(c.getY()),null);
        }
    }

    public void setWidth(int w) {
        myWidth = w;
        setPanelSize();
        repaint();
    }

    public void setHeight(int h) {
        myHeight = h;
        setPanelSize();
        repaint();
    }

    public void setTileWidth(int w) {
        myTileWidth = w;
        setPanelSize();
        repaint();
    }

    public void setTileHeight(int h) {
        myTileHeight = h;
        setPanelSize();
        repaint();
    }

    public void initializeMap(int w, int h) {
        myMap = new EditableMap(w,h);

    }

    public void ZoomIn() {
        myMap.zoomIn();
        myTileWidth = (int) (myTileWidth * ZOOM_RATE);
        myTileHeight = (int) (myTileHeight * ZOOM_RATE);
        setPanelSize();
        repaint();
    }

    public void ZoomOut() {
        myMap.zoomOut();
        myTileWidth = (int) (myTileWidth / ZOOM_RATE);
        myTileHeight = (int) (myTileHeight / ZOOM_RATE);
        setPanelSize();
        repaint();        
    }

    public void placeResource(int x, int y) {
        x=x/myTileWidth;
        y=y/myTileHeight;
        if(x>=0 && x<myWidth && y>=0 && y<myHeight){
            EditableNode n = myMap.getMapNode(x, y);
            if(!myRemoveFlag){
                n.setTileType(myCanvas.getCurrentSelectResource().getName());
                n.setImage(myCanvas.getCurrentSelectResource().getImage());
                n.setOccupied(true);
            } else {
                n.reset();
            }
            repaint();
        }
    }

    public void placePlayer(int x, int y) {
        int nodex=x/myTileWidth;
        int nodey=y/myTileHeight;
        EditableNode n = myMap.getMapNode(nodex, nodey);
        if(!myRemoveFlag){
            myMap.addPlayer(x, y);
            n.setPlayerIndex(myMap.getMyPlayerNumber());
        } else {
            myMap.removePlayer(n.getPlayerIndex());
        }
        repaint();
    }
    
    public void clear() {
        myMap.clearMap();
        repaint();
    }

    public void setRemoveFlag(boolean b) {
        myRemoveFlag = b; 
    }

    public EditableMap getMyMap() {
        return myMap;
    }
    
    public void setMode(int mode) {
        myMode = mode;       
    }
    
    @InputMethodTarget(name="onLeftMouseDown")
    public void testClick (PositionObject p) {
        switch (myMode) {
            case RESOURCEMODE:
                placeResource((int)(p.getX()), (int)(p.getY()));
                break;
            case PLAYERMODE:
                placePlayer((int)(p.getX()), (int)(p.getY()));
                break;
            default: break;  
        }
    }



    @InputMethodTarget(name="onMouseDrag")
    public void testDrag (PositionObject p) {
        if(myMode == RESOURCEMODE) {
            placeResource((int)(p.getX()), (int)(p.getY()));
        }
    }



}
