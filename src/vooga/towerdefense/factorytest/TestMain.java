package vooga.towerdefense.factorytest;

import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.gameElements.Tower;
import vooga.towerdefense.util.Location;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestTowerDefinition myTowerDef = new TestTowerDefinition();
	    TestTowerFactory myTowerFactory = new TestTowerFactory("TestTower", myTowerDef);
	    GameElement testTower = myTowerFactory.makeTower(new Location(100, 200));
	    System.out.println(testTower.getAttributeManager().getAttribute("health").getName());
	    
	}

}