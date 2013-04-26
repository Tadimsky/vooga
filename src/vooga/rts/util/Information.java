package vooga.rts.util;

import java.awt.image.BufferedImage;
import java.util.Map;

import vooga.rts.resourcemanager.ResourceManager;

/**
 * This class stores information relevant to anything that will display Actions onto the GUI
 * 
 * @author Kevin Oh
 * 
 */
public class Information {
	private String myName = "";
	private String myDescription = "";
	private Map<String, Integer> myCost;
	private int myUniqueID; //for networking
	private BufferedImage myButtonImage;

	public Information(String name, String description,
			Map<String, Integer> cost, String buttonImagePath) {
		myName = name;
		myDescription = description;
		myCost = cost;
		myButtonImage = ResourceManager.getInstance().<BufferedImage> getFile(buttonImagePath, BufferedImage.class);
	}

	public String getName() {
		return myName;
	}
	
	public BufferedImage getButtonImage() {
		return myButtonImage;
	}
	
	public String getDescription() {
		return myDescription;
	}
	public Map<String, Integer> getCost() {
		return myCost;
	}
}
