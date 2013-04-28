package vooga.rts.leveleditor.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;


/**
 * the loader that transform a xml file to a real map
 * @author Richard Yang
 *
 */
public class MapLoader {

    private EditableMap myMap;
    private Map<Integer, String> myTileName;
    private Map<Integer, String> myTerrainName;
    private Map<Integer, String> myResourceName;

    private Map<Integer, String> myTileImageName;
    private Map<Integer, String> myTerrainImageName;
    private Map<Integer, String> myResourceImageName;
    
    private Map<Integer, BufferedImage> myTileImage;
    private Map<Integer, BufferedImage> myTerrainImage;
    private Map<Integer, BufferedImage> myResourceImage;
    
    private Map<Integer, String> myTerrainWalkAbility;
    private Map<Integer, String> myResourceAmount;
    
    private DocumentBuilderFactory myFactory;
    private DocumentBuilder myBuilder;
    private Document myDocument;

    /**
     * constructor 
     * @throws ParserConfigurationException exception
     */
    public MapLoader() throws ParserConfigurationException {
        
        
        
        myFactory = DocumentBuilderFactory.newInstance();
        myBuilder = myFactory.newDocumentBuilder();
        
        // yolo
        myTileName = new HashMap<Integer, String>();
        myTerrainName = new HashMap<Integer, String>();
        myResourceName = new HashMap<Integer, String>();
        myTileImageName = new HashMap<Integer, String>();
        myTerrainImageName = new HashMap<Integer, String>();
        myResourceImageName = new HashMap<Integer, String>();
        myTileImage = new HashMap<Integer, BufferedImage>();
        myTerrainImage = new HashMap<Integer, BufferedImage>();
        myResourceImage = new HashMap<Integer, BufferedImage>();
        
        myTerrainWalkAbility = new HashMap<Integer, String>();
        myResourceAmount = new HashMap<Integer, String>();
      
    }
    
    
    public void loadMapFile(String mapfile) throws Exception {
        URI f = getClass().getResource(mapfile).toURI();
        loadMapFile(new File(f));
    }

    
    /**
     * 
     * @param resourceFile source file
     * @throws Exception exception
     */
    public void loadMapFile(File resourceFile) throws Exception {
        
        String xmlPath = resourceFile.getPath();
        String xmlFileName = resourceFile.getName();
        String path = xmlPath.substring(0, xmlPath.indexOf(xmlFileName));
        
        
        myDocument = myBuilder.parse(resourceFile);
        Element root = myDocument.getDocumentElement();
        
        loadSizeInfo(root);
        loadInfo(root);        
        loadTypeInfo(root, path);
        loadTiles(root);
        loadTerrains(root);
        loadResources(root, path);
        
    }

    /**
     * load all information from the xml file
     * @param root root element
     */
    public void loadInfo(Element root) {
        NodeList nameList = root.getElementsByTagName("Name");
        Node nameNode = nameList.item(0);
        myMap.setMyMapName(nameNode.getTextContent());
        
        NodeList descriptionList = root.getElementsByTagName("Desc");
        Node descriptionNode = descriptionList.item(0);
        myMap.setMyMapName(descriptionNode.getTextContent());
    
        NodeList playerList = root.getElementsByTagName("Player");
        for (int i = 0; i < playerList.getLength(); i++) {
            Node playerNode = playerList.item(i);
            NamedNodeMap attributes = playerNode.getAttributes();
            String x = attributes.item(1).getNodeValue();
            String y = attributes.item(2).getNodeValue();
            myMap.addPlayer(Integer.parseInt(x), Integer.parseInt(y));
        }
    }
    
    /**
     * load all size information from the xml file
     * @param root root element
     */
    public void loadSizeInfo(Element root) {
        NodeList tileSizeList = root.getElementsByTagName("tilesize");
        NodeList tileAmountList = root.getElementsByTagName("tileamount");
        
        Node tileSizeNode = tileSizeList.item(0);
        Node tileAmountNode = tileAmountList.item(0);
       
        
        int x = Integer.parseInt(tileAmountNode.getAttributes().item(0).getNodeValue());
        int y = Integer.parseInt(tileAmountNode.getAttributes().item(1).getNodeValue());
        
        int width = Integer.parseInt(tileSizeNode.getAttributes().item(1).getNodeValue());
        int height = Integer.parseInt(tileSizeNode.getAttributes().item(0).getNodeValue());
        myMap = new EditableMap("", "", x, y, width, height);
        
    }
    
    /**
     * load all type information from the xml file
     * @param root root element
     */
    public void loadTypeInfo(Element root, String path) throws IOException {
        NodeList tileTypeList = root.getElementsByTagName("tiletype");
        NodeList terrainTypeList = root.getElementsByTagName("terraintype");
        NodeList resourceTypeList = root.getElementsByTagName("resourcetype");
        
        String tileImagePath = path + "images/tiles/";
        String terrainImagePath = path + "images/terrains/";
        String resourceImagePath = path + "images/resources/";
        
        for (int i = 0; i < tileTypeList.getLength(); i++) {
            Node tileTypeNode = tileTypeList.item(i);
            NamedNodeMap attributes = tileTypeNode.getAttributes();
            String tileID = attributes.item(0).getNodeValue();
            String tileImageName = attributes.item(1).getNodeValue();
            String tileName = attributes.item(2).getNodeValue();
            BufferedImage tileImage = ImageIO.read(new File(tileImagePath + tileImageName));
            myTileName.put(Integer.parseInt(tileID), tileName);
            myTileImageName.put(Integer.parseInt(tileID), tileImageName);
            myTileImage.put(Integer.parseInt(tileID), tileImage);
            
        }
        
        for (int i = 0; i < terrainTypeList.getLength(); i++) {
            Node terrainTypeNode = terrainTypeList.item(i);
            NamedNodeMap attributes = terrainTypeNode.getAttributes();
            String terrainID = attributes.item(0).getNodeValue();
            String terrainImageName = attributes.item(1).getNodeValue();
            String terrainName = attributes.item(2).getNodeValue();
            String terrainWalkAbility = attributes.item(3).getNodeValue();
            BufferedImage terrainImage = ImageIO.read(new File(terrainImagePath + terrainImageName));
            myTerrainName.put(Integer.parseInt(terrainID), terrainName);
            myTerrainImageName.put(Integer.parseInt(terrainID), terrainImageName);
            myTerrainWalkAbility.put(Integer.parseInt(terrainID), terrainWalkAbility);
            myTerrainImage.put(Integer.parseInt(terrainID), terrainImage);
        }
        
        for (int i = 0; i < resourceTypeList.getLength(); i++) {
            Node resourceTypeNode = resourceTypeList.item(i);
            NamedNodeMap attributes = resourceTypeNode.getAttributes();
            String resourceID = attributes.item(0).getNodeValue();
            String resourceAmount = attributes.item(1).getNodeValue();
            String resourceImageName = attributes.item(2).getNodeValue();
            String resourceName = attributes.item(3).getNodeValue();
            BufferedImage resourceImage = ImageIO.read(new File
                                                       (resourceImagePath + resourceImageName));
            myResourceName.put(Integer.parseInt(resourceID), resourceName);
            myResourceImageName.put(Integer.parseInt(resourceID), resourceImageName);
            myResourceAmount.put(Integer.parseInt(resourceID), resourceAmount);
            myResourceImage.put(Integer.parseInt(resourceID), resourceImage);
        }
    }

    /**
     * load all terrains from the xml file
     * @param root root element
     */
    public void loadTiles(Element root) {
        
        NodeList tileList = root.getElementsByTagName("tile");
        
        int y = myMap.getMyYsize();
        
        for (int i = 0; i < tileList.getLength(); i++) {
            
            int myX = i / y;
            int myY = i % y;
            
            Node tileNode = tileList.item(i);
            NamedNodeMap attributes = tileNode.getAttributes();
            int tileID = Integer.parseInt(attributes.item(0).getNodeValue());
            String tileName = myTileName.get(tileID);
            String tileImageName = myTileImageName.get(tileID);
            Pixmap tileImage = new Pixmap(myTileImage.get(tileID));
            myMap.addTile(myX, myY, tileID, tileName, tileImageName, tileImage);
        }
    }

    /**
     * load all terrains from the xml file
     * @param root root element
     */
    public void loadTerrains(Element root) {
        NodeList terrainList = root.getElementsByTagName("terrain");

        for(int j = 0 ; j < terrainList.getLength() ; j++) {
                Node terrainNode = terrainList.item(j);
                if(terrainNode.hasAttributes()) {
                    NamedNodeMap attributes = terrainNode.getAttributes();
                    int terrainID = Integer.parseInt(attributes.item(0).getNodeValue());
                    int x = Integer.parseInt(attributes.item(1).getNodeValue());
                    int y = Integer.parseInt(attributes.item(2).getNodeValue());
                    int z = Integer.parseInt(attributes.item(3).getNodeValue());
                    String terrainName = myTerrainName.get(terrainID);
                    String terrainImageName = myTerrainImageName.get(terrainID);
                    Pixmap terrainImage = new Pixmap(myTerrainImage.get(terrainID));
                    int terrainWalkAbility = Integer.parseInt(myTerrainWalkAbility.get(terrainID));
                    myMap.addTerrain(terrainImage, new Location3D(x,y,z), terrainID, terrainName, terrainImageName, terrainWalkAbility);
                }
        }
        
    } 
    /**
     *print everything in this map, used for testing
     *
     */
    public void printEverything() {
      myMap.printMatrix();
      for (int i = 0 ; i < myMap.getResourceSize() ; i++) {
          EditableResource res =  myMap.getResource(i);
          System.out.print(res.getMyID());
          System.out.print(" ");
          System.out.print(res.getType());
          System.out.print(" ");
          System.out.print(res.getMyImageName());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getX());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getY());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getZ());   
          System.out.println();
      }
  
      for (int i = 0 ; i < myMap.getTerrainSize() ; i++) {
      
          EditableTerrain res =  myMap.getTerrain(i);
      
          System.out.print(res.getMyID());
          System.out.print(" ");
          System.out.print(res.getMyName());
          System.out.print(" ");
          System.out.print(res.getMyImageName());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getX());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getY());
          System.out.print(" ");
          System.out.print(res.getWorldLocation().getZ());   
          System.out.println();
      }
    }

    /**
     * load resources from the file 
     * @param root root element
     * @param path path of the image files
     */
    public void loadResources(Element root, String path) {
        NodeList resourceList = root.getElementsByTagName("resource");
        for (int i = 0; i < resourceList.getLength(); i++) {
            Node resourceNode = resourceList.item(i);
            NamedNodeMap attributes = resourceNode.getAttributes();
            int resourceID = Integer.parseInt(attributes.item(0).getNodeValue());
            int x = Integer.parseInt(attributes.item(1).getNodeValue());
            int y = Integer.parseInt(attributes.item(2).getNodeValue());
            int z = Integer.parseInt(attributes.item(3).getNodeValue());
            String resourceName = myResourceName.get(resourceID);
            String resourceImageName = myResourceImageName.get(resourceID);
            Pixmap resourceImage = new Pixmap(myResourceImage.get(resourceID));
            int resourceAmount = Integer.parseInt(myResourceAmount.get(resourceID));
            myMap.addResource(resourceImage, new Location3D(x,y,z), resourceID, resourceName, resourceImageName, resourceAmount);
        }
    }  
    
    /**
     * get the loaded editable map
     * @return
     */
    public EditableMap getMyMap() {
        return myMap;
    }
    
}