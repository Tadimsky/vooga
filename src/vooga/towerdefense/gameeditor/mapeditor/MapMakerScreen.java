package vooga.towerdefense.gameeditor.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import vooga.towerdefense.model.tiles.GrassTile;
import vooga.towerdefense.model.tiles.Tile;
import util.Location;
import util.Pixmap;


/**
 * This is the screen where the game maker places the tiles in order to make
 * a map. The tiles are placed one at a time on a grid contained in the screen.
 *   
 * @author Leonard K. Ng'eno
 * 
 */
public class MapMakerScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_TILE_STRING = "grass_tile.png";
    private static final Pixmap DEFAULT_PIXMAP = new Pixmap(DEFAULT_TILE_STRING);
    private static final Dimension DEFAULT_TILE_SIZE = new Dimension(50, 50);
    private static final Location DEFAULT_LOCATION = new Location (0,0);
    private static final GrassTile DEFAULT_TILE = new GrassTile(0, DEFAULT_PIXMAP, DEFAULT_LOCATION, DEFAULT_TILE_SIZE);
    private static final int MINIMUM_TILE_SIZE = 10;
    private Dimension mySize;
    private Integer myTileSize;
    private MouseAdapter myMouseListener;
    private List<Grid> myGrids;
    private Tile myTileToBuild;
    //private Map<Point, Integer> myMapRepresentation;
    private String myMap;

    public MapMakerScreen (Dimension size) {
        setSize(size);
        setPreferredSize(size);
        mySize = size;
        myTileSize = 50;
        myGrids = new ArrayList<Grid>();
        myTileToBuild = DEFAULT_TILE;
        myMap = "";
        //myMapRepresentation = new HashMap<Point,Integer>();
        
        makeListener();
        addMouseListener(myMouseListener);
    }

    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        setBackground(Color.CYAN);
        paintGridLines(pen);
        paintTilesOnGrid(pen);
    }

    private void paintTilesOnGrid (Graphics pen) {
        for (Grid g : myGrids) {
            g.paint((Graphics2D) pen);
            //myMapRepresentation.put(g.getTopLeftCorner(), g.getTileId());
            myMap += Integer.toString(g.getTileId()) + " ";
        }
       System.out.println(myMap);
    }

    /**
     * This method resets the sizes of the tiles. This also results in the paths the
     * game maker had build being reset.
     * 
     * @param size      The size of each tile in pixels
     */
    public void setTileSizes (int size) {
        myTileSize = size;
        myTileToBuild = DEFAULT_TILE;              
        repaint();
    }

    private void paintGridLines (Graphics pen) {
        if (myTileSize > MINIMUM_TILE_SIZE) {
            myGrids.removeAll(myGrids);
            for (int i = 0; i < mySize.width; i += myTileSize) {
                for (int j = 0; j < mySize.height; j += myTileSize) {
                    pen.drawLine(i, 0, i, mySize.height);
                    pen.drawLine(0, j, mySize.width, j);
                    Grid rect = new Grid(i, j, myTileSize, myTileSize, myTileToBuild);
                    myGrids.add(rect);
                }
            }
        }

    }

    private void makeListener () {
        myMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                setTileClicked(e.getPoint());
            }
        };
    }

    private void setTileClicked (Point point) {
        for (Grid tile : myGrids) {
            if (tile.contains(point)) {
                tile.setTile(myTileToBuild);
                paintTilesOnGrid(getGraphics());
            }
        }
    }

    /**
     * Sets the tile to build to be tile t.
     * 
     * @param t  The tile to be fixed on the grid that the game maker clicks on
     */
    public void setTile (Tile t) {
        myTileToBuild = t;
    }
    
    public String getMapString() {
        return myMap;
    }
    
//    /** 
//     * 
//     * @return  a mapping of the tile's position and the tile's id as strings
//     */
//    public Map<String, String> getMapRepresentation() {
//        Map<String, String> map = new HashMap<String, String>();
//        for (Map.Entry<Point, Integer> p: myMapRepresentation.entrySet()){
//            Integer x = p.getKey().x;
//            Integer y = p.getKey().y;
//            String xS, yS, tileNum;
//            if (x != 0){
//                x = x/myTileSize;
//            }
//            xS = x.toString();
//            if (y != 0){
//                y = y/myTileSize;
//            }
//            yS = y.toString();
//            tileNum = xS + "," + yS;
//            map.put(tileNum, p.getValue().toString());
//        }
//        
//        return map;
//    }
    
    /**
     * get map width 
     * @return  width of the map
     */
    public String getMapWidth(){
        return Integer.toString(this.getWidth());
    }
    
    /**
     * get map height
     * @return  height of map
     */
    public String getMapHeight(){
        return Integer.toString(this.getHeight());
    }
}
