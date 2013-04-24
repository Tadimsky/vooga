package vooga.rts.leveleditor.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vooga.rts.util.Location;
import vooga.rts.util.Pixmap;

/**
 * this class is responsible for generate the map file
 * @author Richard Yang
 *
 */
public class MapSaver {
    
    private DocumentBuilderFactory myFactory;
    private DocumentBuilder myBuilder;
    private Document myDocument;
    
    private EditableMap mySavingMap; 
    
    /**
     * constructor 
     * @param map the map we want to save 
     * @throws ParserConfigurationException xml parser exception
     */
    public MapSaver(EditableMap map) throws ParserConfigurationException {
        myFactory = DocumentBuilderFactory.newInstance();
        myBuilder = myFactory.newDocumentBuilder();
        myDocument = myBuilder.newDocument();
        mySavingMap = map;
    }
    
    /**
     * generate a map file
     * @param objectiveFile the objective file
     * @throws Exception exception
     */
    public void generateMapFile(File objectiveFile) throws Exception {
        
        String filePath = objectiveFile.getPath();
        String fileName = objectiveFile.getName();
        myDocument = myBuilder.newDocument();
        
        String relativePath = filePath.substring(0, filePath.indexOf(fileName));
        String xmlPath = relativePath + fileName + "/" + fileName + ".xml";
        String imagePath = relativePath + fileName + "/images";
         
        File mapFolder = new File(relativePath + fileName);       
        mapFolder.mkdirs();
        
        File mapXMLFile = new File(xmlPath);
        
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
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //transformer.setOutputProperty("http://xml.apache.org/xslt}indent-amount", "2");
        
        FileOutputStream out = new FileOutputStream(mapXMLFile);    
        
        
        StreamResult result = new StreamResult(out);
        transformer.transform(new DOMSource(myDocument), result);
        
    }
    
    /**
     * store the images into certain path
     * @param path path we want to store
     */
    private void storeImages(String path) {
        
        storeTileImages(path);
        storeTerrainImages(path);
        storeResourceImages(path);
    }
    
    /**
     * store all tile images
     * @param path objective path
     */
    private void storeTileImages(String path) {
        File tileFolder = new File(path + "/tiles");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/tiles/";
        
        Map<String, Pixmap> tileInformation = new HashMap<String, Pixmap>();
        
        for (int i = 0; i < mySavingMap.getMyXsize(); i++) {
            for (int j = 0; j < mySavingMap.getMyYsize(); j++) {
                if (mySavingMap.getMyTile(i, j).getMyID() != 0) {
                    String imageName = mySavingMap.getMyTile(i, j).getMyImageName();
                    if (!tileInformation.containsKey(imageName)) {
                        Pixmap currentImage = mySavingMap.getMyTile(i, j).getImage();
                        tileInformation.put(imageName, currentImage);
                    }
                }
            }
        }
        
        for (String str : tileInformation.keySet()) {
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            try {
                ImageIO.write((BufferedImage)tileInformation.get(str).getMyImage(), 
                              format, bufferFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * store all terrain images
     * @param path objective path
     */
    private void storeTerrainImages(String path) {
        File tileFolder = new File(path + "/terrains");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/terrains/";
        
        Map<String, BufferedImage> terrainInformation = new HashMap<String, BufferedImage>();
        
        for (int i = 0; i < mySavingMap.getTerrainSize(); i++) {
            EditableTerrain ter = mySavingMap.getTerrain(i);   
            String myImageName = ter.getMyImageName();
            BufferedImage myImage = (BufferedImage)ter.getMyImage();
            
            if (!terrainInformation.containsKey(myImageName)) {
                 terrainInformation.put(myImageName, myImage);
            }
        }
        
        
        for (String str : terrainInformation.keySet()) {
            
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            
            try {
                ImageIO.write(terrainInformation.get(str), format, bufferFile);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
            
        }
    }

    /**
     * store all resource images
     * @param path objective path
     */
    private void storeResourceImages(String path) {
        File tileFolder = new File(path + "/resources");
        tileFolder.mkdirs();
        
        String tileImagePath = path + "/resources/";
        
        Map<String, BufferedImage> resourceInformation = new HashMap<String, BufferedImage>();
        
        for (int i = 0; i < mySavingMap.getResourceSize(); i++) {
            EditableResource res = mySavingMap.getResource(i);
            String myImageName = res.getMyImageName();
            BufferedImage myImage = (BufferedImage)res.getMyImage();
            if (!resourceInformation.containsKey(myImageName)) {
                resourceInformation.put(myImageName, myImage);
            }
            
        }
        
        for (String str : resourceInformation.keySet()) {
            File bufferFile = new File(tileImagePath + str);
            String format = getFileFormat(str);
            try {
                ImageIO.write(resourceInformation.get(str), format, bufferFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    
    }
    
    /**
     * get the format of a file based on its name
     * @param fileName the name of the file
     * @return String
     */
    private String getFileFormat(String fileName) {
        
        return fileName.substring(fileName.indexOf(".") + 1);
    }
    
    /**
     * append the information of this map in xml file
     * @param root root element
     */
    private void appendInfo(Element root) {
        Element info = myDocument.createElement("MapInfo");
        Element name = myDocument.createElement("Name");
        name.appendChild(myDocument.createTextNode(mySavingMap.getMyMapName()));
        info.appendChild(name);
        
        Element description = myDocument.createElement("Desc");
        description.appendChild(myDocument.createTextNode(mySavingMap.getMyDescription()));
        info.appendChild(description);
        
        Element players = myDocument.createElement("Players");
        players.setAttribute("number", mySavingMap.getMyPlayerNumber() + "");
        for (Integer i : mySavingMap.getAllPlayers().keySet()) {
            Location loc = mySavingMap.getAllPlayers().get(i);
            int x = (int)loc.getX();
            int y = (int)loc.getY();
            Element myPlayer = myDocument.createElement("Player");
            myPlayer.setAttribute("ID", i + "");
            myPlayer.setAttribute("X", x + "");
            myPlayer.setAttribute("Y", y + "");
            players.appendChild(myPlayer);
        }
        info.appendChild(players);
        
        
        root.appendChild(info);
    }
    
    /**
     * append the resource information of this map in xml file
     * @param root root element
     */
    private void appendResourceInfo(Element root) {
        Element resourceInfo = myDocument.createElement("Resourceinfo");
        Element sizeInfo = myDocument.createElement("SizeInfo");
        Element tileIndex = myDocument.createElement("tileindex");
        Element terrainIndex = myDocument.createElement("terrainindex");
        Element resourceIndex = myDocument.createElement("resourceIndex");
        
        Element tileSize = myDocument.createElement("tilesize");
        tileSize.setAttribute("width", mySavingMap.getMyTileWidth() + "");
        tileSize.setAttribute("height", mySavingMap.getMyTileHeight() + "");
        sizeInfo.appendChild(tileSize);
        
        Element tileAmount = myDocument.createElement("tileamount");
        tileAmount.setAttribute("X", mySavingMap.getMyXsize() + "");
        tileAmount.setAttribute("Y", mySavingMap.getMyYsize() + "");
        sizeInfo.appendChild(tileAmount);
   
        Set<Integer> myTileIndex = new HashSet<Integer>();
        Set<Integer> myTerrainIndex = new HashSet<Integer>();
        Set<Integer> myResourceIndex = new HashSet<Integer>();
        
        for (int i = 0; i < mySavingMap.getMyXsize(); i++) {
            for (int j = 0; j < mySavingMap.getMyYsize(); j++) {
                EditableTile currentTile = mySavingMap.getMyTile(i, j);
                int id = currentTile.getMyID();
                if (!myTileIndex.contains(id) && id != 0) { 
                    myTileIndex.add(id);
                    Element newTile =  myDocument.createElement("tiletype");
                    newTile.setAttribute("ID", id + "");
                    newTile.setAttribute("image", currentTile.getMyImageName());
                    newTile.setAttribute("name", currentTile.getMyName());
                    tileIndex.appendChild(newTile);
                }
            }
        }
        
         
        
        for (int i = 0; i < mySavingMap.getTerrainSize(); i++) {
            
            EditableTerrain currentTerrain = mySavingMap.getTerrain(i);
            int id = currentTerrain.getMyID();
            if (!myTerrainIndex.contains(id)) {
                myTerrainIndex.add(id);
                Element newTerrain =  myDocument.createElement("terraintype");
                newTerrain.setAttribute("ID", id + "");
                newTerrain.setAttribute("image", currentTerrain.getMyImageName());
                newTerrain.setAttribute("name", currentTerrain.getMyName());
                newTerrain.setAttribute("walkAbility", currentTerrain.getMyWalkAbility() + "");
                terrainIndex.appendChild(newTerrain);
                    
            }
        }
        
        for (int i = 0; i < mySavingMap.getResourceSize(); i++) {    
            EditableResource currentResource = mySavingMap.getResource(i);
            int id = currentResource.getMyID();
            if (!myResourceIndex.contains(id)) {
                myResourceIndex.add(id);
                Element newResource =  myDocument.createElement("resourcetype");
                newResource.setAttribute("ID", id + "");
                newResource.setAttribute("image", currentResource.getMyImageName());
                newResource.setAttribute("name", currentResource.getType());
                newResource.setAttribute("amount", currentResource.getMyAmount() + "");
                resourceIndex.appendChild(newResource);
                    
            }
        }
        
        resourceInfo.appendChild(sizeInfo);
        resourceInfo.appendChild(tileIndex);
        resourceInfo.appendChild(terrainIndex);
        resourceInfo.appendChild(resourceIndex);
        
        root.appendChild(resourceInfo);
        
    }
    
    /**
     * append the tile this map in xml file
     * @param root root element
     */
    
    private void appendTile(Element root) {
        Element tile = myDocument.createElement("tiles");
        
        int x = mySavingMap.getMyXsize();
        int y = mySavingMap.getMyYsize();
        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                EditableTile bufferTile = mySavingMap.getMyTile(i, j);
                Element currentTile = myDocument.createElement("tile"); 
                currentTile.setAttribute("ID", bufferTile.getMyID() + "");
                tile.appendChild(currentTile);
            }
        }
        
        root.appendChild(tile);
        
    }
    
    /**
     * append the terrains of this map in xml file
     * @param root root element
     */
    private void appendTerrain(Element root) {
        Element terrains = myDocument.createElement("terrains");
         
        for (int i = 0; i < mySavingMap.getTerrainSize(); i++) {
            EditableTerrain ter = mySavingMap.getTerrain(i);    
            int ID = ter.getMyID();
            int x = (int)ter.getWorldLocation().getX();
            int y = (int)ter.getWorldLocation().getY();
            int z = (int)ter.getWorldLocation().getZ();
            Element newTerrain =  myDocument.createElement("terrain");
            newTerrain.setAttribute("ID", ID + "");
            newTerrain.setAttribute("X", x + "");
            newTerrain.setAttribute("Y", y + "");
            newTerrain.setAttribute("Z", z + "");
            terrains.appendChild(newTerrain);
        }
        
        
        root.appendChild(terrains);
        
    }
   
    /**
     * append the resource information of this map in xml file
     * @param root root element
     */
    private void appendResource(Element root) {
        Element resources = myDocument.createElement("Resources");
        
        for (int i = 0; i < mySavingMap.getResourceSize(); i++) {
            EditableResource res = mySavingMap.getResource(i);
            int ID = res.getMyID();
            int x = (int)res.getWorldLocation().getX();
            int y = (int)res.getWorldLocation().getY();
            int z = (int)res.getWorldLocation().getZ();
            int amount = (int)res.getMyAmount();
            
            Element newResource =  myDocument.createElement("resource");
            newResource.setAttribute("ID", ID + "");
            newResource.setAttribute("X", x + "");
            newResource.setAttribute("Y", y + "");
            newResource.setAttribute("Z", z + "");
            newResource.setAttribute("amount", amount + "");
            resources.appendChild(newResource);
        }
        
        root.appendChild(resources);
       
    }


}

