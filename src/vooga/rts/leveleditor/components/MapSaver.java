package vooga.rts.leveleditor.components;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vooga.rts.util.Location;

public class MapSaver {

    private static final String RESOURCE_PATH = "vooga.rts.leveleditor.resource.";
    
    
    private DocumentBuilderFactory myFactory ;
    private DocumentBuilder myBuilder;
    private Document myDocument;
    
    private EditableMap mySavingMap; 
    
    private ResourceBundle myTerrainResources;
    private ResourceBundle myTileResources;
    
    
    public MapSaver(EditableMap map) throws ParserConfigurationException {
        myFactory = DocumentBuilderFactory.newInstance();
        myBuilder = myFactory.newDocumentBuilder();
        myDocument = myBuilder.newDocument();
        mySavingMap = map;
        myTerrainResources = ResourceBundle.getBundle(RESOURCE_PATH + "TerrainIndex" );
        myTileResources = ResourceBundle.getBundle(RESOURCE_PATH+"TileIndex");
    }
    
    public void generateMapFile(File objectiveFile) throws TransformerException, IOException {
        
        String filePath = objectiveFile.getPath();
        String fileName = objectiveFile.getName();
        
        String relativePath = filePath.substring(0,filePath.indexOf(fileName));
        String XMLPath = relativePath + fileName + "/" + fileName +".xml";
        String imagePath = relativePath + fileName + "/images";
         
        File mapFolder = new File(relativePath + fileName);       
        mapFolder.mkdirs();
        
        File mapXMLFile = new File(XMLPath);
        
        File imageFolder = new File(imagePath);
        imageFolder.mkdirs();
        
        storeImages(imagePath);
        
        Element root = myDocument.createElement("Map");
        myDocument.appendChild(root);
        
        
        appendInfo(root);
        appendResourceInfo(root);
        appendTile(root);
        appendTerrain(root);
        appendResource(root);
        
        
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        //transformer.setOutputProperty("http://xml.apache.org/xslt}indent-amount", "2");
        
        FileOutputStream out = new FileOutputStream(mapXMLFile);    
        
        
        StreamResult result = new StreamResult(out);
        transformer.transform( new DOMSource(myDocument), result);
        
    }
    
    private void storeImages(String path) {
        
        storeTileImages(path);
        storeTerrainImages(path);
        storeResourceImages(path);
    }
    
    private void storeTileImages(String path) {
        File tileFolder = new File(path + "/tiles");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/tiles/";
        
        Map<String,BufferedImage> tileInformation = new HashMap<String,BufferedImage>();
        
        for(int i = 0 ; i < mySavingMap.getMyXSize() ; i++) {
            for(int j = 0 ; j < mySavingMap.getMyYSize() ; j++) {
                if(mySavingMap.getMapNode(i, j).getMyTile().getMyID() != 0) {
                    String imageName = mySavingMap.getMapNode(i, j).getMyTile().getMyImageName();
                    if( !tileInformation.containsKey(imageName) ) {
                        BufferedImage currentImage = mySavingMap.getMapNode(i, j).getMyTile().getMyImage();
                        tileInformation.put(imageName, currentImage);
                    }
                }
            }
        }
        
        for(String str : tileInformation.keySet()) {
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            try {
                ImageIO.write(tileInformation.get(str), format, bufferFile);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
    
    private void storeTerrainImages(String path) {
        File tileFolder = new File(path + "/terrains");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/terrains/";
        
        Map<String,BufferedImage> terrainInformation = new HashMap<String,BufferedImage>();
        
        for(Integer i : mySavingMap.getLayerMap().keySet()) {
            MapLayer myLayer = mySavingMap.getLayer(i);
            for(Terrain ter : myLayer.getTerrainSet()) {
                
                String myImageName = ter.getMyImageName();
                BufferedImage myImage = ter.getMyImage();
                if( !terrainInformation.containsKey(myImageName)) {
                    terrainInformation.put(myImageName, myImage);
                }
            }
        }
        
        for(String str : terrainInformation.keySet()) {
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            try {
                ImageIO.write(terrainInformation.get(str), format, bufferFile);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }

    
    private void storeResourceImages(String path) {
        File tileFolder = new File(path + "/resources");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/resources/";
        
        Map<String,BufferedImage> resourceInformation = new HashMap<String,BufferedImage>();
        
        for(Resource res : mySavingMap.getResourceSet()) {
            String myImageName = res.getMyImageName();
            BufferedImage myImage = res.getMyImage();
            if( !resourceInformation.containsKey(myImageName)) {
                resourceInformation.put(myImageName, myImage);
            }
            
        }
        
        for(String str : resourceInformation.keySet()) {
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            try {
                ImageIO.write(resourceInformation.get(str), format, bufferFile);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    
    }
    
    private String getFileFormat(String fileName) {
        
        return fileName.substring(fileName.indexOf(".")+1);
    }
    private void appendInfo(Element root) {
        Element info = myDocument.createElement("MapInfo");
        Element name = myDocument.createElement("Name");
        name.appendChild(myDocument.createTextNode(mySavingMap.getMyMapName()));
        info.appendChild(name);
        
        Element description = myDocument.createElement("Desc");
        description.appendChild(myDocument.createTextNode(mySavingMap.getMyDescription()));
        info.appendChild(description);
        
        Element players = myDocument.createElement("Players");
        players.setAttribute("number", mySavingMap.getMyPlayerNumber()+"");
        for(Integer i : mySavingMap.getAllPlayers().keySet()) {
            Location loc = mySavingMap.getAllPlayers().get(i);
            int x = (int)loc.getX();
            int y = (int)loc.getY();
            Element myPlayer = myDocument.createElement("Player");
            myPlayer.setAttribute("ID", i+"");
            myPlayer.setAttribute("X", x+"" );
            myPlayer.setAttribute("Y", y+"" );
            players.appendChild(myPlayer);
        }
        info.appendChild(players);
        
        
        root.appendChild(info);
    }
    
    private void appendResourceInfo(Element root) {
        Element resourceInfo = myDocument.createElement("Resourceinfo");
        Element sizeInfo = myDocument.createElement("SizeInfo");
        Element tileIndex = myDocument.createElement("tileindex");
        Element terrainIndex = myDocument.createElement("terrainindex");
        
        Element tileSize = myDocument.createElement("tilesize");
        tileSize.setAttribute("width", mySavingMap.getMapNode(0, 0).getMyWidth()+"");
        tileSize.setAttribute("height", mySavingMap.getMapNode(0, 0).getMyHeight()+"");
        sizeInfo.appendChild(tileSize);
        
        Element tileAmount = myDocument.createElement("tileamount");
        tileAmount.setAttribute("X", mySavingMap.getMyXSize()+"");
        tileAmount.setAttribute("Y", mySavingMap.getMyYSize()+"");
        sizeInfo.appendChild(tileAmount);
        
        for(String str : myTileResources.keySet()) {
            String value = myTileResources.getString(str);
            String[] content = value.split("&");
            String name = content[0];
            String imagePath = content[1];
            Element newTile =  myDocument.createElement("tiletype");
            newTile.setAttribute("ID", str);
            newTile.setAttribute("image", imagePath);
            newTile.setAttribute("name", name);
            tileIndex.appendChild(newTile);
        }
        
        for(String str : myTerrainResources.keySet()) {
            String value = myTerrainResources.getString(str);
            String[] content = value.split("&");
            String name = content[0];
            String imagePath = content[1];
            String walkAbility = content[2];
            Element newTerrain =  myDocument.createElement("terraintype");
            newTerrain.setAttribute("ID", str);
            newTerrain.setAttribute("image", imagePath);
            newTerrain.setAttribute("name", name);
            newTerrain.setAttribute("walkAbility", walkAbility);
            terrainIndex.appendChild(newTerrain);
        }
        
        resourceInfo.appendChild(sizeInfo);
        resourceInfo.appendChild(tileIndex);
        resourceInfo.appendChild(terrainIndex);
        
        root.appendChild(resourceInfo);
        
    }
    
    private void appendTile(Element root) {
        Element tile = myDocument.createElement("tiles");
        
        int x = mySavingMap.getMyXSize();
        int y = mySavingMap.getMyYSize();
        
        for(int i= 0 ; i < x ; i++) {
            for(int j = 0; j<y ; j++) {
                Tile bufferTile = mySavingMap.getMapNode(i, j).getMyTile();
                Element currentTile = myDocument.createElement("tile"); 
                currentTile.setAttribute("ID", bufferTile.getMyID()+"");
                tile.appendChild(currentTile);
            }
        }
        
        root.appendChild(tile);
        
    }

    private void appendTerrain(Element root) {
        Element terrains = myDocument.createElement("terrains");
        
        int layerCount = mySavingMap.getLayerNumber();
        
        for(int i = 1 ; i < layerCount+1 ; i++) {
            
            Element layer = myDocument.createElement("layer");
            layer.setAttribute("level", i+"");
            
            
            for(Terrain ter : mySavingMap.getLayer(i).getTerrainSet()) {
                
                int ID = ter.getMyID();
                int x = ter.getMyX();
                int y = ter.getMyY();
                Element newTerrain =  myDocument.createElement("terrain");
                newTerrain.setAttribute("ID", ID+"");
                newTerrain.setAttribute("X", x+"");
                newTerrain.setAttribute("Y", y+"");
                layer.appendChild(newTerrain);
            }
            terrains.appendChild(layer);
        }
        
        root.appendChild(terrains);
        
    }
   
    private void appendResource(Element root) {
        Element resources = myDocument.createElement("Resources");
        
        for(Resource res : mySavingMap.getResourceSet()) {
            
            int ID = res.getMyID();
            int x = res.getMyX();
            int y = res.getMyY();
            Element newTerrain =  myDocument.createElement("resource");
            newTerrain.setAttribute("ID", ID+"");
            newTerrain.setAttribute("X", x+"");
            newTerrain.setAttribute("Y", y+"");
            resources.appendChild(newTerrain);
        }
        
        root.appendChild(resources);
       
    }


}

