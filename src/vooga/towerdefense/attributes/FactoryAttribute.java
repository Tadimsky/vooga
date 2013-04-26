package vooga.towerdefense.attributes;

import vooga.towerdefense.factories.elementfactories.GameElementFactory;

/**
 * FactoryAttribute describes the factories usable by GameElement
 * 
 * @author XuRui
 *
 */
public class FactoryAttribute {
	
	private String myName;
	private GameElementFactory myFactory;
	
	public FactoryAttribute(String factoryName, GameElementFactory factory){
		myName = factoryName;
		myFactory = factory;
	}
	
	public String getName () {
		return myName;
	}
	
	public GameElementFactory getFactory(){
		return myFactory;
	}
	
	public void modifyFactory(GameElementFactory newFactory){
		myFactory = newFactory;
	}
	

}
