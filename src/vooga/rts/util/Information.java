package vooga.rts.util;

import java.util.Map;

/**
 * This class stores information relevant to a sprite
 * 
 * @author Kevin Oh
 * 
 */
public class Information {
	private String myName;
	private String myDescription;
	private Map<String, Integer> myCost;

	public Information(String name, String description,
			Map<String, Integer> cost) {
		myName = name;
		myDescription = description;
		myCost = cost;
	}

	public String getName() {
		return myName;
	}

	public String getDescription() {
		return myDescription;
	}
	public Map<String, Integer> getCost() {
		return myCost;
	}
}
