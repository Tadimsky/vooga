package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Soldier;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;

/**
 * 
 * This class implements OccupyStrategy and is used as an instance in 
 * interactives for objects that can be occupied by types of Units specified.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class CanBeOccupied implements OccupyStrategy{
	public static final int DEFAULT_MAX_OCCUPIERS = 10;
	
	private List<Unit> myOccupiers;
	private List<String> myValidOccupierType;
	private int myMaxOccupiers;
	private int myOccupierID;
	
	public CanBeOccupied() {
		myOccupiers = new ArrayList<Unit>();
		myValidOccupierType = new ArrayList<String>();
		myMaxOccupiers = DEFAULT_MAX_OCCUPIERS;
		myOccupierID = 0;
	}
	
	public void getOccupied(GameEntity entity, Unit u) {
		System.out.println("current occupier ID: " + myOccupierID);
		if (myOccupiers.size() < myMaxOccupiers && verifyOccupier(entity, u)) {
			u.setVisible(false);
			if (myOccupierID == 0) {
				myOccupierID = u.getPlayerID();
			}
			u.getGameUnitManager().addEntityUnit(entity, u);
			myOccupiers.add(u);
		}
	}
	
	public void addValidClassType(Unit validOccupier) {
		Class cls = validOccupier.getClass();
		String className = cls.getName();
		myValidOccupierType.add(className);
	}
	
	private boolean verifyOccupier(GameEntity entity, Unit u) {
		Class cls = u.getClass();
		if (myOccupierID != 0 && myOccupierID != u.getPlayerID()) {
			return false;
		}
		for (String s: myValidOccupierType) {
			while (cls != null) {
				String className = cls.getName();
				//System.out.println("class name to be compared: "+ className);
				if (className.equals(s)) {
					return true;
				}
				cls = cls.getSuperclass();
			}
		}
		return false;
	}
	
	public void setOccupierID(int id) {
		myOccupierID = id;
	}
	
	public int getOccupierID() {
		return myOccupierID;
	}
	
	public List<Unit> getOccupiers() {
		return myOccupiers;
	}
	
	public void setOccupiers(ArrayList<Unit> u) {
		myOccupiers = u;
	}
	
	public int getMaxOccupiers() {
		return myMaxOccupiers;
	}
	
	/**
	 * TESTING FOR VERIFYING VALID OCCUPIER
	 * @param argus
	 */
	public static void main(String[] argus) {
		OccupyStrategy o = new CanBeOccupied();
		o.addValidClassType(new Unit());
		Soldier s = new Soldier();
		Unit u = new Unit();
		//System.out.println("Soldier: " + verifyOccupier(s));
		//System.out.println("Unit: " + verifyOccupier(u));
	}

}
