package vooga.rts.gamedesign.factories;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vooga.rts.gamedesign.sprite.rtsprite.interactive.buildings.Building;

public class BuildingDecoder extends Decoder{
	
	private static final String TYPE_TAG = "building";
	private static final String COST_TAG = "cost";
	
	
	Factory myFactory;

	public BuildingDecoder(Factory factory){
		myFactory = factory;
	}

	@Override
	public Building create(Document doc) {
		
		NodeList nodeLst = doc.getElementsByTagName(TYPE_TAG);
		
		for(int i = 0 ; i < nodeLst.getLength() ; i++){
			Node nNode = nodeLst.item(i);
			
			
			
			
		}
		return null;
		

	}
}
