package vooga.rts.leveleditor.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JComponent;
import util.input.Input;
import util.input.InputClassTarget;
import util.input.InputMethodTarget;
import util.input.PositionObject;
import vooga.rts.leveleditor.components.EditableMap;
import vooga.rts.leveleditor.components.EditableNode;
import vooga.rts.leveleditor.components.MapLayer;
import vooga.rts.leveleditor.components.Resource;
import vooga.rts.leveleditor.components.Terrain;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Camera;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.PointTester;


@InputClassTarget
public class MapPanel extends JComponent {

    public static final String INPUT_DIR = "vooga.rts.resources.properties.Input";
    public static final String PLAYER_IMAGE_PATH = "Player1.png";
    public static final Dimension DEFAULT_MAP_SIZE = new Dimension(600, 600);
    public static final double ZOOM_RATE = 1.25;
    public static final int RESOURCEMODE = 1;
    public static final int PLAYERMODE = 2;
    public static final int TERRAINMODE = 3;
    public static final int TILEMODE = 4;

    private Canvas myCanvas;
    private EditableMap myMap;
    private Input myInput;
    private int myWidth;
    private int myHeight;
    private int myTileWidth;
    private int myTileHeight;
    private int myCurrentLayer;
    private int myMaxLayer;
    private boolean myRemoveFlag;
    private int myMode;
    private BufferedImage myPlayerImage;
    
    //private PointTester pt;

    public MapPanel (Canvas canvas) {
        myCanvas = canvas;
        myMap = new EditableMap();
        myInput = new Input(INPUT_DIR, this);
        myInput.addListenerTo(this);
        myWidth = 0;
        myHeight = 0;
        myCurrentLayer = 0;
        myMaxLayer = 0;
        myTileWidth = 50;
        myTileHeight = 50;
        myPlayerImage =
                ResourceManager.getInstance().<BufferedImage> getFile(PLAYER_IMAGE_PATH,
                                                                      BufferedImage.class);
        //pt = new PointTester();
        setPanelSize();
        
    }

    private void setPanelSize () {
        Dimension d = new Dimension(2*myTileWidth * myWidth, myTileHeight * myHeight);
        setPreferredSize(d);
        Camera.instance().setMapSize(d);
        Camera.instance().setViewSize(d);
        Camera.instance().setWorldLocation(new Location3D((myTileWidth * myWidth)/2, (myTileHeight * myHeight)/2,0));
    }

    @Override
    public void paintComponent (Graphics g) {
        //pt.paint((Graphics2D)g);
        //Camera.instance().setWorldLocation(new Location3D());
        g.setColor(Color.white);
        g.fillRect(0, 0, 2*myWidth * myTileWidth, myHeight * myTileHeight);
        Camera.instance().paint((Graphics2D)g);
        g.setColor(Color.gray);
        for (int i = 0; i < myWidth; i++) {
//            g.drawLine(i * myTileWidth, 0, i * myTileWidth, myHeight * myTileHeight);
            drawLineTest(i * myTileWidth, 0, i * myTileWidth, myHeight * myTileHeight,g);
        }

        for (int j = 0; j < myHeight; j++) {
//            g.drawLine(0, j * myTileHeight, myWidth * myTileWidth, j * myTileHeight);
            drawLineTest(0, j * myTileHeight , myWidth * myTileWidth, j * myTileHeight,g);
        }

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.setColor(Color.black);
        
        drawLineTest(0, 0, myWidth * myTileWidth, 0, g);
        drawLineTest(0, 0, 0, myHeight * myTileHeight,g);
        drawLineTest(myWidth * myTileWidth, 0, myWidth * myTileWidth, myHeight * myTileHeight,g);
        drawLineTest(0, myHeight * myTileHeight, myWidth * myTileWidth, myHeight * myTileHeight,g);
//
//        g.drawLine(0, 0, myWidth * myTileWidth, 0);
//        g.drawLine(0, 0, 0, myHeight * myTileHeight);
//        g.drawLine(myWidth * myTileWidth, 0, myWidth * myTileWidth, myHeight * myTileHeight);
//        g.drawLine(0, myHeight * myTileHeight, myWidth * myTileWidth, myHeight * myTileHeight);

        // paint Node
        for (int i = 0; i < myMap.getWidth(); ++i) {
            for (int j = 0; j < myMap.getHeight(); ++j) {
                if (myMap.getMapNode(i, j).getOccupied()) {
                    try {
                        myMap.getMapNode(i, j).paint(g);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // paint Player
        for (Location c : myMap.getLocationMap().values()) {
            g.drawImage(myPlayerImage, (int) (c.getX()), (int) (c.getY()), null);
        }

        // paint Terrain
        for (MapLayer m : myMap.getLayerMap().values()) {
            for (Terrain t : m.getTerrainSet()) {
                t.paint(g);
            }
        }

        // paint Resource
        for (Resource r : myMap.getResourceSet()) {
            r.paint(g);
        }
        //Camera.instance().paint((Graphics2D)g);
    }
    
    public void drawLineTest(int x1, int y1, int x2, int y2, Graphics g) {
        Point p1 = new Point(x1,y1);
        Point2D loc1 = Camera.instance().worldToView(new Location3D(p1));
        Point p2 = new Point(x2,y2);
        Point2D loc2 = Camera.instance().worldToView(new Location3D(p2));
        g.drawLine((int)loc1.getX(), (int)loc1.getY(), (int)loc2.getX(), (int)loc2.getY());
    }

    public void setWidth (int w) {
        myWidth = w;
        setPanelSize();
        repaint();
    }

    public void setHeight (int h) {
        myHeight = h;
        setPanelSize();
        repaint();
    }

    public void setTileWidth (int w) {
        myTileWidth = w;
        setPanelSize();
        repaint();
    }

    public void setTileHeight (int h) {
        myTileHeight = h;
        setPanelSize();
        repaint();
    }

    public void initializeMap (String name, String desc, int width, int height, int tileWidth, int tileHeight) {
        myMap = new EditableMap(name,desc,width, height, tileWidth, tileHeight);
    }

    public void ZoomIn () {
        myMap.zoomIn();
        myTileWidth = (int) (myTileWidth * ZOOM_RATE);
        myTileHeight = (int) (myTileHeight * ZOOM_RATE);
        setPanelSize();
        repaint();
    }

    public void ZoomOut () {
        myMap.zoomOut();
        myTileWidth = (int) (myTileWidth / ZOOM_RATE);
        myTileHeight = (int) (myTileHeight / ZOOM_RATE);
        setPanelSize();
        repaint();
    }

    public void placeResource (int x, int y) {
        myMap.addResource(x, y, myCanvas.getCurrentSelectResource().getMyID());
        repaint();
    }

    public void placeTerrain (int x, int y) {
        Terrain t = new Terrain(new Location(x, y), myCanvas.getCurrentSelectTerrain().getMyID());
        myMap.addTerrain(myCurrentLayer, t);
        repaint();
    }

    // test printing matrix bug switch x and y
    private void placeTile (int x, int y) {
        x = x / myTileHeight;
        y = y / myTileWidth;
        if (x >= 0 && x < myWidth && y >= 0 && y < myHeight) {
            EditableNode n = myMap.getMapNode(y, x);
            if (!myRemoveFlag) {
                n.setTile(myCanvas.getCurrentSelectTile().getMyID());
                n.setOccupied(true);
            }
            else {
                n.reset();
            }
            myMap.printMatrix();
            repaint();
        }

    }

    public void placePlayer (int x, int y) {
        int nodex = x / myTileWidth;
        int nodey = y / myTileHeight;
        EditableNode n = myMap.getMapNode(nodex, nodey);
        if (!myRemoveFlag) {
            myMap.addPlayer(x, y);
            n.setPlayerIndex(myMap.getMyPlayerNumber());
        }
        else {
            myMap.removePlayer(n.getPlayerIndex());
        }
        repaint();
    }

    public void clear () {
        myCurrentLayer = 0;
        myMaxLayer = 0;
        myMap.clearMap();
        myMap.getLayerMap().clear();
        myMap.getResourceSet().clear();
        repaint();
    }

    public void setRemoveFlag (boolean b) {
        myRemoveFlag = b;
    }

    public EditableMap getMyMap () {
        return myMap;
    }

    public void setMode (int mode) {
        myMode = mode;
    }

    public void setCurrentLayer (int n) {
        myCurrentLayer = n;

    }

    public int getCurrentLayer () {
        return myCurrentLayer;
    }

    public int getMaxLayer () {
        return myMaxLayer;
    }

    public void addLayer () {
        myMaxLayer++;
        myMap.getLayerMap().put(myMaxLayer, new MapLayer());
    }

    public void removeLayer () {
        myMap.getLayerMap().remove(myMaxLayer);
        myMaxLayer--;
    }

    @InputMethodTarget(name = "onLeftMouseDown")
    public void testClick (PositionObject p) {
        
        switch (myMode) {
            case RESOURCEMODE:
                placeResource((int) (p.getX()), (int) (p.getY()));
                break;
            case PLAYERMODE:
                placePlayer((int) (p.getX()), (int) (p.getY()));
                break;
            case TERRAINMODE:
                placeTerrain((int) (p.getX()), (int) (p.getY()));
                break;
            case TILEMODE:
                placeTile((int) (p.getX()), (int) (p.getY()));
                break;
            default:
                break;
        }
    }

    @InputMethodTarget(name = "onMouseDrag")
    public void testDrag (PositionObject p) {
        if (myMode == TILEMODE) {
            placeTile((int) (p.getX()), (int) (p.getY()));
        }
    }

}
