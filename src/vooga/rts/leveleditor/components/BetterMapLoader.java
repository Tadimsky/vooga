package vooga.rts.leveleditor.components;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BetterMapLoader {

    private EditableMap myMap;
    private Map<Integer, String> myTileName;
    private Map<Integer, String> myTerrainName;

    private Map<Integer, String> myTileImageName;
    private Map<Integer, String> myTerrainImageName;
   
    private Map<Integer, String> myTerrainWalkAbility;
    
    private DocumentBuilderFactory myFactory ;
    private DocumentBuilder myBuilder;
    private Document myDocument;


    public BetterMapLoader(EditableMap map) throws ParserConfigurationException {
        
        
        myFactory = DocumentBuilderFactory.newInstance();
        myBuilder = myFactory.newDocumentBuilder();
        
        myTileName = new HashMap<Integer,String>();
        myTerrainName = new HashMap<Integer,String>();
        myTileImageName = new HashMap<Integer,String>();
        myTerrainImageName = new HashMap<Integer,String>();
        myTerrainWalkAbility = new HashMap<Integer,String>();
        
        
       myMap = map;       
        
 
    }


    public void loadMapFile(File resourceFile) throws SAXException, IOException {
        
        myDocument = myBuilder.parse(resourceFile);
        Element root = myDocument.getDocumentElement();
        
        loadInfo(root);
        loadSizeInfo(root);
        loadTypeInfo(root);
        loadTiles(root);
        loadTerrains(root);
        loadResources(root);

    }

    public void loadInfo(Element root) {
        NodeList nameList = root.getElementsByTagName("Name");
        Node nameNode = nameList.item(0);
        myMap.setMyMapName(nameNode.getTextContent());
        
        NodeList descriptionList = root.getElementsByTagName("Desc");
        Node descriptionNode = descriptionList.item(0);
        myMap.setMyMapName(descriptionNode.getTextContent());
    
        NodeList playerList = root.getElementsByTagName("Player");
        for(int i = 0 ; i < playerList.getLength() ; i++ ) {
            Node playerNode = playerList.item(i);
            NamedNodeMap attributes = playerNode.getAttributes();
            String x = attributes.item(1).getNodeValue();
            String y = attributes.item(2).getNodeValue();
            myMap.addPlayer(Integer.parseInt(x),Integer.parseInt(y));
        }
    }

    public void loadSizeInfo(Element root) {
        NodeList tileSizeList = root.getElementsByTagName("tilesize");
        NodeList tileAmountList = root.getElementsByTagName("tileamount");
        
        Node tileSizeNode = tileSizeList.item(0);
        Node tileAmountNode = tileAmountList.item(0);
        
        String x = tileAmountNode.getAttributes().item(0).getNodeValue();
        String y = tileAmountNode.getAttributes().item(1).getNodeValue();
        
        String width = tileSizeNode.getAttributes().item(1).getNodeValue();
        String height = tileSizeNode.getAttributes().item(0).getNodeValue();
        myMap.setMyXSize(Integer.parseInt(x));
        myMap.setMyYSize(Integer.parseInt(y));
        myMap.initializeMap(Integer.parseInt(width), Integer.parseInt(height));
        
    }
    
    public void loadTypeInfo(Element root) {
        NodeList tileTypeList = root.getElementsByTagName("tiletype");
        NodeList terrainTypeList = root.getElementsByTagName("terraintype");
        
        for(int i = 0 ; i < tileTypeList.getLength() ; i++ ) {
            Node tileTypeNode = tileTypeList.item(i);
            NamedNodeMap attributes = tileTypeNode.getAttributes();
            String tileID = attributes.item(0).getNodeValue();
            String tileImageName = attributes.item(1).getNodeValue();
            String tileName = attributes.item(2).getNodeValue();
            myTileName.put(Integer.parseInt(tileID), tileName);
            myTileImageName.put(Integer.parseInt(tileID), tileImageName);
        }
        
        for(int i = 0 ; i < terrainTypeList.getLength() ; i++ ) {
            Node terrainTypeNode = terrainTypeList.item(i);
            NamedNodeMap attributes = terrainTypeNode.getAttributes();
            String terrainID = attributes.item(0).getNodeValue();
            String terrainImageName = attributes.item(1).getNodeValue();
            String terrainName = attributes.item(2).getNodeValue();
            String terrainWalkAbility = attributes.item(3).getNodeValue();
            myTerrainName.put(Integer.parseInt(terrainID), terrainName);
            myTerrainImageName.put(Integer.parseInt(terrainID), terrainImageName);
            myTerrainWalkAbility.put(Integer.parseInt(terrainID), terrainWalkAbility);
        }
    
    
    }

    public void loadTiles(Element root) {
        
        NodeList tileList = root.getElementsByTagName("tile");
        
        int y = myMap.getMyYSize();
        
        for(int i = 0 ; i < tileList.getLength() ; i++ ) {
            
            int myX = i/y;
            int myY = i%y;
            
            Node tileNode = tileList.item(i);
            NamedNodeMap attributes = tileNode.getAttributes();
            String tileID = attributes.item(0).getNodeValue();
            myMap.getMapNode(myX, myY).setTile(Integer.parseInt(tileID));
        }
    }

    public void loadTerrains(Element root) {
        
        
        NodeList layerList = root.getElementsByTagName("layer");

        for(int i = 0 ; i < layerList.getLength() ; i++) {
            Node layerNode = layerList.item(i); 
            String layerIndex = layerNode.getAttributes().item(0).getNodeValue();
            NodeList terrainList = layerNode.getChildNodes();
            
            for(int j = 0 ; j < terrainList.getLength() ; j++) {
                Node terrainNode = terrainList.item(j);
                if(terrainNode.hasAttributes()) {
                    NamedNodeMap attributes = terrainNode.getAttributes();
                    String id = attributes.item(0).getNodeValue();
                    String x = attributes.item(1).getNodeValue();
                    String y = attributes.item(2).getNodeValue();
                    myMap.addTerrain(Integer.parseInt(layerIndex), new Terrain(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(id)));
                }
            }
        }
    }

    public void loadResources(Element root) {
        NodeList resourceList = root.getElementsByTagName("resource");
        for(int i = 0 ; i < resourceList.getLength() ; i++) {
            Node resourceNode = resourceList.item(i);
            NamedNodeMap attributes = resourceNode.getAttributes();
            String id = attributes.item(0).getNodeValue();
            String x = attributes.item(1).getNodeValue();
            String y = attributes.item(2).getNodeValue();
            myMap.addResource(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(id));
        }
    }  
}


