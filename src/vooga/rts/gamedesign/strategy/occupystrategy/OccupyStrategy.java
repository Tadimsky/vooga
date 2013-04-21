package vooga.rts.gamedesign.strategy.occupystrategy;

import java.util.ArrayList;
import java.util.List;

import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.strategy.Strategy;

/**
 * This interface is implemented by the classes CanBeOccupied and 
 * CannotBeOccupied that are then used as instance variables in the classes
 * that could possibly be occupied by Units. If the unit currently can be 
 * occupied, it will have an instance of CanBeOccupied, otherwise it will have
 * an instance of CannotBeOccupied. In this way, occupying specific features
 * can be refactored out of the Sprite hierarchy.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public interface OccupyStrategy extends Strategy{
	public void getOccupied(GameEntity entity, Unit u);
	
	public void addValidClassType(Unit u);
	
	public void setOccupierID(int id);
	
	public List<Unit> getOccupiers();
	
	public void setOccupiers(ArrayList<Unit> u);
	
	public int getMaxOccupiers();
	
	public int getOccupierID();

}