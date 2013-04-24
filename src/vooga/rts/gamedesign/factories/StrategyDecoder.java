package vooga.rts.gamedesign.factories;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.strategy.attackstrategy.AttackStrategy;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.util.Location3D;
import vooga.rts.util.ReflectionHelper;

/**
 * Decodes all the strategies defined in the game element XML file, initializes them,
 * and stores them in the Strategy map that is located in the factory. 
 * @author agostif
 *
 */
public class StrategyDecoder extends Decoder{

	private static final String ATTACK_TAG = "attacks";
	private static final String OCCUPY_TAG = "occupys";
	private static final String GATHER_TAG = "gathers";
	
	
	Factory myFactory;
	
	public StrategyDecoder(Factory factory){
		myFactory = factory;
	}
	
	/**
	 * Takes in a list of ____Strategies (and their source files) initializes them, and puts them in
	 * the Strategy map found in the factory. 
	 * @param list
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void getSources(NodeList list) {
		for(int j = 0 ; j < list.getLength() ; j++ ){
			Node Node = list.item(j);
			if(Node.getNodeType() == Node.ELEMENT_NODE){
				String path = Node.getAttributes().item(0).getTextContent();
				String key = Node.getNodeName();
				
				//This class needs to change because strategy constructors have changed. 
				Strategy strat = (Strategy) ReflectionHelper.makeInstance(path);
				myFactory.put(key, strat);
				
			}
		}
	}
	
	/**
	 * Parses through the XML file in order to generate the respective strategy NodeLists. Then calls getSources
	 * which runs through the lists and makes the strategies. 
	 */
	@Override
	public void create(Document doc, String type) {
		NodeList attackLst = doc.getElementsByTagName(ATTACK_TAG).item(0).getChildNodes();
		NodeList occupyLst = doc.getElementsByTagName(OCCUPY_TAG).item(0).getChildNodes();
		NodeList gatherLst = doc.getElementsByTagName(GATHER_TAG).item(0).getChildNodes();
		
		getSources(attackLst);
		getSources(occupyLst);
		getSources(gatherLst);
	}

}
