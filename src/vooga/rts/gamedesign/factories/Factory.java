package vooga.rts.gamedesign.factories;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.strategy.attackstrategy.AttackStrategy;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;


/**
 * This class is in charge of the loading of input XML files for different
 * class types. It will figure out the class type this given file is in charge
 * of, and pass the information to the corresponding decoder. All the decoders
 * are loaded through an input file.
 * 
 * @author Francesco Agosti
 * @author Wenshun Liu
 */

public class Factory {
	public static final String DECODER_MATCHING_FILE = "DecodeMatchUp";
	public static final String DECODER_MATCHING_PAIR_TAG = "pair";
	public static final String DECODER_MATCHING_DECODETYPE_TAG = "type";
	public static final String DECODER_MATCHING_PATH_TAG = "decoderPath";
	
	Map<String, Decoder> myDecoders = new HashMap<String, Decoder>();
	Map<String, InteractiveEntity> mySprites;
	Map<String, Resource> myResources;
	Map<String, Strategy> myStrategies;
	Map<String, String[]> myProductionDependencies;
	Map<String, String[]> myStrategyDependencies;
	Map<String, UpgradeTree> myUpgradeTrees;
	
	
	public Factory()  {
		myDecoders = new HashMap<String, Decoder>();
		
		try {
			loadDecoder(DECODER_MATCHING_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mySprites = new HashMap<String, InteractiveEntity>();
		myResources = new HashMap<String, Resource>();
		myStrategies = new HashMap<String, Strategy>();
		myProductionDependencies = new HashMap<String, String[]>();
		myStrategyDependencies = new HashMap<String, String[]>();
		myUpgradeTrees = new HashMap<String, UpgradeTree>();
	}
	
	/**
	 * all put methods add a key, game element pair to the appropriate Map instance variable. 
	 * (The method is overloaded a few times since there are several maps).
	 * @param name
	 * @param value
	 */
	public void put(String name, InteractiveEntity value){
		mySprites.put(name, value);
	}
	
	public void put(String name, Resource resource){
		myResources.put(name, resource);
	}
	
	public void put(String name, Strategy value){
		myStrategies.put(name, value);
	}
	
	public void put(String name, UpgradeTree upgradeTree){
		myUpgradeTrees.put(name, upgradeTree);
	}
	
	public Map<String, UpgradeTree> getUpgradeTrees(){
		return myUpgradeTrees;
	}
	
	/**
	 * Returns an Attack Strategy from a map of strategies. 
	 * (this format is used so that you do not have to cast later on). 
	 * @param key
	 * @return AttackStrategy
	 */
	public AttackStrategy getAttackStrategy(String key){
		return (AttackStrategy) myStrategies.get(key);
	}
	
	/**
	 * Returns a Gather Strategy from a map of strategies. 
	 * @param key
	 * @return GatherStrategy
	 */
	public GatherStrategy getGatherStrategy(String key){
		return (GatherStrategy) myStrategies.get(key);
	}
	
	/**
	 * Returns an Occupy Strategy from a map of strategies. 
	 * @param key
	 * @return OccupyStrategy
	 */
	public OccupyStrategy getOccupyStrategy(String key){
		return (OccupyStrategy) myStrategies.get(key);
	}
	
	/**
	 * Returns an Interactive Entity from a map of InteractiveEntities
	 * @param key
	 * @return InteractiveEntity
	 */
	public InteractiveEntity getInteractiveEntity(String key){
		return mySprites.get(key);
	}
	
	/**
	 * Returns an Resource from a map of Resources
	 * @param key
	 * @return InteractiveEntity
	 */
	public Resource getResource(String key){
		return myResources.get(key);
	}
	
	/**
	 * Puts a production dependency (tells the factory what "name" can produce) in
	 * a dependency map. 
	 * 
	 * @param name
	 * @param itProduces
	 */
	public void putProductionDependency(String name, String[] itProduces){
		myProductionDependencies.put(name, itProduces);
	}
	
	/**
	 * Puts a strategy dependency (tells the factory what strategies "name" uses) in
	 * a dependency map. 
	 * @param name
	 * @param strategies
	 */
	public void putStrategyDependency(String name, String[] strategies){
		myStrategyDependencies.put(name, strategies);
	}
	
	/**
	 * Creates decoders by loading the input file that specifies the path of
	 * each Decoder and the type of class it is in charge of. Puts the decoders
	 * and their corresponding types into a map.
	 * 
	 * This method will be called when the Factory class is created.
	 * 
	 * @param fileName the name of the XML file that specifies decoder paths.
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private void loadDecoder(String fileName) throws ClassNotFoundException, IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParserConfigurationException, SAXException, IOException {
		File file = new File(getClass().getResource(fileName).getFile());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		
		NodeList nodeLst = doc.getElementsByTagName(DECODER_MATCHING_PAIR_TAG);
		
		for (int i = 0; i < nodeLst.getLength(); i++) {
			Element pairElmnt = (Element) nodeLst.item(i);
			
			Element typeElmnt = (Element)pairElmnt.getElementsByTagName(DECODER_MATCHING_DECODETYPE_TAG).item(0);
			NodeList typeList = typeElmnt.getChildNodes();
			String type = ((Node) typeList.item(0)).getNodeValue();
			
			Element pathElmnt = (Element)pairElmnt.getElementsByTagName(DECODER_MATCHING_PATH_TAG).item(0);
			NodeList pathList = pathElmnt.getChildNodes();
			String path = ((Node) pathList.item(0)).getNodeValue();
			
			Class<?> headClass =
					Class.forName(path);
			Decoder decoder = (Decoder) headClass.getConstructor(Factory.class).newInstance(this);
			myDecoders.put(type, decoder);
		}
	}
	
	/**
	 * Loads the XML file passed in and determines the type of class it provides
	 * information for. Then passes the input file to the corresponding decoder
	 * in charge of that type of class.
	 * 
	 * @param fileName the name of the XML file that provides class information
	 * and to be loaded
	 */
	public void loadXMLFile(String fileName) {
		try {
			File file = new File(getClass().getResource(fileName).getFile());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println(doc.getDocumentElement().getNodeName());
			
			//myDecoders.get(doc.getDocumentElement().getNodeName()).create(doc);
			
			NodeList head = doc.getChildNodes();
			Node childNode = head.item(0);
			NodeList children = childNode.getChildNodes();
			for(int i = 0 ; i < children.getLength() ; i++){
				Node tempNode = children.item(i);
				if(tempNode.getNodeType() == Node.ELEMENT_NODE){
					//System.out.println("CURRENT DECODER: " + tempNode.getNodeName());
					String type = tempNode.getNodeName();
					myDecoders.get(type).create(doc, type);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		initializeProducables();
		initializeStrategies();
	}
	
	/**
	 * Once instances of game elements are loaded into their maps this method adds the
	 * producables (specified from the XML file) to their respective entities. 
	 */
	private void initializeProducables(){
		for(String key :myProductionDependencies.keySet()){
			InteractiveEntity father;
			String[] produces = myProductionDependencies.get(key);
			for(String baby: produces){
				father = mySprites.get(key);
				InteractiveEntity producable = mySprites.get(baby);
				father.addProducable(producable);
			}
		}
	}
	/**
	 * Once strategies defined by the XML file are loaded into their maps this method uses
	 * the strategy dependency map
	 * 
	 */
	private void initializeStrategies(){
		for(String key: myStrategyDependencies.keySet()){
			String[] strategies = myStrategyDependencies.get(key);
			//Do the same for other strategies
			AttackStrategy attack = (AttackStrategy) myStrategies.get(strategies[0]);
			mySprites.get(key).setAttackStrategy(attack);
			OccupyStrategy occupy = (OccupyStrategy) myStrategies.get(strategies[1]);
			mySprites.get(key).setOccupyStrategy(occupy);
			GatherStrategy gather = (GatherStrategy) myStrategies.get(strategies[2]);
			mySprites.get(key).setGatherStrategy(gather);
			
		}
	}
	
	public static void main (String[] args) {
		Factory a = new Factory();
		a.loadXMLFile("Factory.xml");
		
	}
}


