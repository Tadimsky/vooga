package vooga.rts.gamedesign.factories;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
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
import vooga.rts.gamedesign.sprite.gamesprites.Projectile;
import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.strategy.attackstrategy.AttackStrategy;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.gamedesign.strategy.production.CanProduce;
import vooga.rts.gamedesign.strategy.production.CannotProduce;
import vooga.rts.gamedesign.strategy.production.ProductionStrategy;
import vooga.rts.gamedesign.strategy.upgradestrategy.CanUpgrade;
import vooga.rts.gamedesign.strategy.upgradestrategy.UpgradeStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.gamedesign.weapon.Weapon;
import vooga.rts.util.Location3D;


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
    public static final String MATCHING_PAIR_TAG = "pair";
    public static final String MATCHING_TYPE_TAG = "type";
    public static final String MATCHING_PATH_TAG = "path";

    Map<String, String> myDecoderPaths;
    Map<String, Decoder> myDecoders;
    Map<String, InteractiveEntity> mySprites;
    Map<String, Resource> myResources;
    Map<String, Strategy> myStrategies;
    Map<String, Weapon> myWeapons;
    Map<String, Projectile> myProjectiles;
    Map<String, String[]> myProductionDependencies;
    Map<String, String[]> myStrategyDependencies;
    Map<String, UpgradeTree> myUpgradeTrees;
    Map<String, String[]> myWeaponDependencies;
    Map<String, String> myProjectileDependencies;

    public Factory () {
        myDecoderPaths = new HashMap<String, String>();
        myDecoders = new HashMap<String, Decoder>();

        try {
            loadMappingInfo(DECODER_MATCHING_FILE, myDecoderPaths);
            createDecoders();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        mySprites = new HashMap<String, InteractiveEntity>();
        myResources = new HashMap<String, Resource>();
        myStrategies = new HashMap<String, Strategy>();
        myWeapons = new HashMap<String, Weapon>();
        myProjectiles = new HashMap<String, Projectile>();
        myProductionDependencies = new HashMap<String, String[]>();
        myStrategyDependencies = new HashMap<String, String[]>();
        myWeaponDependencies = new HashMap<String, String[]>();
        myProjectileDependencies = new HashMap<String, String>();
        myUpgradeTrees = new HashMap<String, UpgradeTree>();
    }

    /**
     * all put methods add a key, game element pair to the appropriate Map instance variable.
     * (The method is overloaded a few times since there are several maps).
     * 
     * @param name
     * @param value
     */
    public void put (String name, InteractiveEntity value) {
        mySprites.put(name, value);
    }

    public void put (String name, Resource resource) {
        myResources.put(name, resource);
    }

    public void put (String name, Strategy value) {
        myStrategies.put(name, value);
    }

    public void put (String name, UpgradeTree upgradeTree) {
        System.out.println("puts here");
        myUpgradeTrees.put(name, upgradeTree);
    }

    public void put (String name, Weapon weapon) {
        myWeapons.put(name, weapon);
    }

    public void put (String name, Projectile proj) {
        myProjectiles.put(name, proj);
    }

    public Map<String, UpgradeTree> getUpgradeTrees () {
        return myUpgradeTrees;
    }

    /**
     * Returns an Attack Strategy from a map of strategies.
     * (this format is used so that you do not have to cast later on).
     * 
     * @param key
     * @return AttackStrategy
     */
    public AttackStrategy getAttackStrategy (String key) {
        return (AttackStrategy) myStrategies.get(key);
    }

    /**
     * Returns a Gather Strategy from a map of strategies.
     * 
     * @param key
     * @return GatherStrategy
     */
    public GatherStrategy getGatherStrategy (String key) {
        return (GatherStrategy) myStrategies.get(key);
    }

    /**
     * Returns an Occupy Strategy from a map of strategies.
     * 
     * @param key
     * @return OccupyStrategy
     */
    public OccupyStrategy getOccupyStrategy (String key) {
        return (OccupyStrategy) myStrategies.get(key);
    }

    /**
     * Returns an Upgrade Strategy from a map of strategies.
     * 
     * @param key
     * @return UpgradeStrategy
     */
    public UpgradeStrategy getUpgradeStrategy (String key) {
        return (UpgradeStrategy) myStrategies.get(key);
    }

    /**
     * Returns the whole map of Entities
     * (you do not need to return any other maps because entities encapsulate other objects).
     */
    public Map<String, InteractiveEntity> getEntitiesMap () {
        return mySprites;
    }

    /**
     * Returns the whole map of resources.
     */
    public Map<String, Resource> getResourceMap () {
        return myResources;
    }

    /**
     * Returns an Interactive Entity from a map of InteractiveEntities
     * 
     * @param key
     * @return InteractiveEntity
     */
    public InteractiveEntity getInteractiveEntity (String key) {
        return mySprites.get(key);
    }

    /**
     * Returns an Resource from a map of Resources
     * 
     * @param key
     * @return InteractiveEntity
     */
    public Resource getResource (String key) {
        return myResources.get(key);
    }

    /**
     * Puts a production dependency (tells the factory what "name" can produce) in
     * a dependency map.
     * 
     * @param name
     * @param itProduces
     */
    public void putProductionDependency (String name, String[] itProduces) {
        myProductionDependencies.put(name, itProduces);
    }

    /**
     * Puts a strategy dependency (tells the factory what strategies "name" uses) in
     * a dependency map.
     * 
     * @param name
     * @param strategies
     */
    public void putStrategyDependency (String name, String[] strategies) {
        myStrategyDependencies.put(name, strategies);
    }

    /**
     * Puts a weapon dependency (tells the factory what weapon "name" uses) in
     * a dependency map.
     * 
     * @param name
     * @param strategies
     */
    public void putWeaponDependency (String name, String[] weapons) {
        myWeaponDependencies.put(name, weapons);
    }

    /**
     * Puts a projectile dependency (tells the factory what projectile "name" uses) in
     * a dependency map.
     * 
     * @param name
     * @param strategies
     */
    public void putProjectileDependency (String name, String projectile) {
        myProjectileDependencies.put(name, projectile);
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
    public void loadMappingInfo (String fileName, Map map) throws ClassNotFoundException,
                                                          IllegalArgumentException,
                                                          SecurityException,
                                                          InstantiationException,
                                                          IllegalAccessException,
                                                          InvocationTargetException,
                                                          NoSuchMethodException,
                                                          ParserConfigurationException,
                                                          SAXException, IOException {
        URI f = null;
        try {
            f = getClass().getResource(fileName).toURI();
        }
        catch (URISyntaxException e) {            
            return;
        }
        File file = new File(f);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        NodeList nodeLst = doc.getElementsByTagName(MATCHING_PAIR_TAG);

        for (int i = 0; i < nodeLst.getLength(); i++) {
            Element pairElmnt = (Element) nodeLst.item(i);

            Element typeElmnt = (Element) pairElmnt.getElementsByTagName(MATCHING_TYPE_TAG).item(0);
            NodeList typeList = typeElmnt.getChildNodes();
            String type = ((Node) typeList.item(0)).getNodeValue();

            Element pathElmnt = (Element) pairElmnt.getElementsByTagName(MATCHING_PATH_TAG).item(0);
            NodeList pathList = pathElmnt.getChildNodes();
            String path = ((Node) pathList.item(0)).getNodeValue();

            map.put(type, path);
        }
    }

    private void createDecoders () throws IllegalArgumentException, SecurityException,
                                  InstantiationException, IllegalAccessException,
                                  InvocationTargetException, NoSuchMethodException,
                                  ClassNotFoundException {
        for (String key : myDecoderPaths.keySet()) {
            Class<?> headClass = Class.forName(myDecoderPaths.get(key));
            Decoder decoder = (Decoder) headClass.getConstructor(Factory.class).newInstance(this);
            myDecoders.put(key, decoder);
        }
    }

    /**
     * Loads the XML file passed in and determines the type of class it provides
     * information for. Then passes the input file to the corresponding decoder
     * in charge of that type of class.
     * 
     * @param fileName the name of the XML file that provides class information
     *        and to be loaded
     */
    public void loadXMLFile (String fileName) {
        try {
            URI f = null;
            try {
                f = getClass().getResource(fileName).toURI();
            }
            catch (URISyntaxException e) {            
                return;
            }
            File file = new File(f);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());
            NodeList head = doc.getChildNodes();
            Node childNode = head.item(0);
            NodeList children = childNode.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node tempNode = children.item(i);
                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                    String type = tempNode.getNodeName();
                    myDecoders.get(type).create(doc, type);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        initializeProjectiles();
        initializeWeapons();
        initializeStrategies();
        initializeProducables();

    }

    /**
     * Once instances of game elements are loaded into their maps this method adds the
     * producables (specified from the XML file) to their respective entities.
     */
    private void initializeProducables () {
        for (String key : myProductionDependencies.keySet()) {
            System.out.println(key);
            String[] produces = myProductionDependencies.get(key);
            mySprites.get(key)
                    .setProductionStrategy((ProductionStrategy) new CanProduce((Building) mySprites
                                                   .get(key)));
            for (String baby : produces) {
                InteractiveEntity producable = mySprites.get(baby);
                // this should not be new unit
                (mySprites.get(key).getProductionStrategy()).addProducable(new Unit());
            }
        }
    }

    /**
     * Once strategies defined by the XML file are loaded into their maps this method uses
     * the strategy dependency map to add strategies to their holder
     * 
     */
    private void initializeStrategies () {
        for (String key : myStrategyDependencies.keySet()) {
            String[] strategies = myStrategyDependencies.get(key);
            AttackStrategy attack = (AttackStrategy) myStrategies.get(strategies[0]);
            mySprites.get(key).setAttackStrategy(attack);
            OccupyStrategy occupy = (OccupyStrategy) myStrategies.get(strategies[1]);
            mySprites.get(key).setOccupyStrategy(occupy);
            GatherStrategy gather = (GatherStrategy) myStrategies.get(strategies[2]);
            mySprites.get(key).setGatherStrategy(gather);

            UpgradeStrategy upgrade = (UpgradeStrategy) myStrategies.get(strategies[3]);
            mySprites.get(key).setUpgradeStrategy(upgrade);
            if (upgrade instanceof CanUpgrade) {
                UpgradeTree relatedUpgradeTree = myUpgradeTrees.get(strategies[4]);
                mySprites.get(key).setUpgradeTree(relatedUpgradeTree);
            }
        }
    }

    /**
     * Once weapons defined by the XML file are loaded into their map this method uses
     * the weapon dependency map to assign weapons to the correct entity.
     * 
     * @param args
     */
    private void initializeWeapons () {
        for (String key : myWeaponDependencies.keySet()) {
            String[] weapons = myWeaponDependencies.get(key);
            InteractiveEntity holder = mySprites.get(key);
            for (String weapon : weapons) {
                Weapon toAdd = myWeapons.get(weapon);
                holder.addWeapon(toAdd);
            }
        }
    }

    /**
     * Once projectiles that have been defined in the XML file have been loaded into their
     * maps this method gives the projectiles to their weapon.
     * 
     * @param args
     */
    private void initializeProjectiles () {
        for (String key : myProjectileDependencies.keySet()) {
            String projectile = myProjectileDependencies.get(key);
            Weapon holder = myWeapons.get(key);
            Projectile toAdd = myProjectiles.get(projectile);
            holder.setProjectile(toAdd);
        }

    }

    public static void main (String[] args) {
        Factory a = new Factory();
        a.loadXMLFile("Factory.xml");

    }
}
