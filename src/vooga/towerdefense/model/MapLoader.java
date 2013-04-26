package vooga.towerdefense.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.w3c.dom.Element;

import util.XMLTool;
import vooga.towerdefense.model.tiles.Tile;
import vooga.towerdefense.model.tiles.factories.DefaultTileFactory;
import vooga.towerdefense.model.tiles.factories.GrassTileFactory;
import vooga.towerdefense.model.tiles.factories.PathTileFactory;
import vooga.towerdefense.model.tiles.factories.TileFactory;
import util.Location;
import util.Pixmap;

/**
 * This class is responsible for constructing GameMap objects
 * from an xml file describing different game maps.
 * 
 * An example of this xml file is shown below:
 * 
 * <map>
 *      <map1>
 *              <image></image>
 *              <dimension>
 *                      <width>800</width>
 *                      <height>600</height>            
 *              </dimension>
 *              <tile_size>50</tile_size>
 *              <grid>
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0
 *                      0 0 0 1 0 0 0 1 0 0 0 1 1 1 0 0
 *                      0 0 0 1 0 0 0 1 0 0 0 1 0 1 0 0
 *                      1 1 1 1 0 0 0 1 1 1 1 1 0 1 1 1 
 *                      0 0 0 1 0 0 0 1 0 0 0 1 0 1 0 0
 *                      0 0 0 1 0 0 0 1 0 0 0 1 1 1 0 0
 *                      0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *              </grid>
 *      </map1>
 *      <map2>
 *              <image></image>
 *              <dimension>
 *                      <width>800</width>
 *                      <height>600</height>            
 *              </dimension>
 *              <tile_size>50</tile_size>
 *              <grid>
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *                      0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *              </grid>
 *      </map2>
 * </map>
 * 
 * This file is read, and a List of GameMap objects can be returned.
 * 
 * @author Erick Gonzalez
 */
public class MapLoader {
    /**
     * Relative path to the map file.
     */
    public static final String MAPTILES_FILENAME = "/vooga/towerdefense/resources/map.txt";
    
    // map_loadfile.xml tags
    private static final String MAP_TAG = "map";
    private static final String IMAGE_TAG = "image";
    private static final String DIMENSION_TAG = "dimension";
    private static final String WIDTH_TAG = "width";
    private static final String HEIGHT_TAG = "height";
    private static final String TILE_SIZE_TAG = "tile_size";
    private static final String GRID_TAG = "grid";
    
    private XMLTool myXMLTool;    
    private Map<Integer, TileFactory> myTileIdMap;
    
    /**
     * 
     * @param mapFilePath a path to the map XML file
     */
    public MapLoader(String mapFilePath) {
        myXMLTool = new XMLTool(mapFilePath);        
        initTileIdMap();
    }
    
    private void initTileIdMap() {
        myTileIdMap = new HashMap<Integer, TileFactory>();
        myTileIdMap.put(0, new DefaultTileFactory());
        myTileIdMap.put(1, new GrassTileFactory());
        myTileIdMap.put(2, new PathTileFactory());
    }
    
    /**
     * Loads all the maps described in the xml file.
     * 
     * @return a list of GameMap objects
     */
    public List<GameMap> loadMaps() {
        List<GameMap> gameMaps = new ArrayList<GameMap>();
        Element mapElement = myXMLTool.getElement(MAP_TAG);
        Map<String, Element> m = myXMLTool.getChildrenElementMap(mapElement);
        for (Element e : m.values()) {
            GameMap map = loadMap(e);
            gameMaps.add(map);
        }
        return gameMaps;
    }
    
    private GameMap loadMap(Element mapNameElement) {
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(mapNameElement);
        Pixmap mapImage = loadMapImage(subElements.get(IMAGE_TAG));
        Dimension mapDimensions = loadMapDimensions(subElements.get(DIMENSION_TAG));
        Dimension tileSize = loadMapTileSize(subElements.get(TILE_SIZE_TAG));
        Tile[][] mapGrid = loadTiles(subElements.get(GRID_TAG), mapDimensions, tileSize);
        return new GameMap(mapGrid, mapImage, mapDimensions, null);
    }
    
    private Pixmap loadMapImage(Element imageElement) {
        String imagePath = myXMLTool.getContent(imageElement);
        return new Pixmap(imagePath);
    }
    
    private Dimension loadMapDimensions(Element dimensionElement) {
        Map<String, Element> subElements = myXMLTool.getChildrenElementMap(dimensionElement);
        int width = getMapWidth(subElements.get(WIDTH_TAG));
        int height = getMapHeight(subElements.get(HEIGHT_TAG));
        return new Dimension(width, height);
    }
    
    private int getMapWidth(Element widthElement) {
        return Integer.parseInt(myXMLTool.getContent(widthElement));
    }
    
    private int getMapHeight(Element heightElement) {
        return Integer.parseInt(myXMLTool.getContent(heightElement));
    }
    
    private Dimension loadMapTileSize(Element tileSizeElement) {
        int tileSize = Integer.parseInt(myXMLTool.getContent(tileSizeElement));
        return new Dimension(tileSize, tileSize);
    } 
    
    private Tile[][] loadTiles(Element tilesElement, Dimension mapDimensions, 
                               Dimension tileDimensions) {
        String gridString = myXMLTool.getContent(tilesElement);
        Scanner reader = new Scanner(gridString);
        
        int horizontalTileCount = (int) (mapDimensions.getWidth() / tileDimensions.getWidth());
        int verticalTileCount = (int) (mapDimensions.getHeight() / tileDimensions.getHeight());

        Tile[][] grid = new Tile[horizontalTileCount][verticalTileCount];

        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                int xCenter = (int) (j * tileDimensions.getWidth() + 
                        tileDimensions.getWidth() / 2);
                int yCenter = (int) (i * tileDimensions.getHeight() + 
                        tileDimensions.getHeight() / 2);
                int tileId = reader.nextInt();
                grid[j][i] = getTileFactory(tileId).createTile(tileId, 
                                                               new Location(xCenter, yCenter));
            }
        }
        return grid;
    }
    
    private TileFactory getTileFactory(int tileId) {
        return myTileIdMap.get(tileId);
    }
}
