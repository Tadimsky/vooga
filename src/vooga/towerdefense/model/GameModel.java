package vooga.towerdefense.model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.model.gamestatistics.Player;
import vooga.towerdefense.model.levels.Level;
import vooga.towerdefense.model.rules.Rule;
import vooga.towerdefense.model.shop.Shop;
import vooga.towerdefense.model.shop.ShopItem;
import vooga.towerdefense.model.tiles.Tile;


/**
 * GameModel holds all of the state and behavior for a particular game of
 * towerdefense
 * 
 * @author Jimmy Longley
 * @author Erick Gonzalez
 */
public class GameModel {
    private Controller myController;
    private GameMap myGameMap;
    private Player myPlayer;
    private List<Level> myLevels;
    private int myCurrentLevel;
    private List<Rule> myRules;
    private List<Action> myActiveActions;
    private Shop myShop;

/**
 * 
 * @param controller
 * @param levels
 * @param rules
 * @param gameMap
 * @param shop
 */
    public GameModel (Controller controller, List<Level> levels, List<Rule> rules, GameMap gameMap, Shop shop) {
        myController = controller;
        myLevels = levels;
        myRules = rules;
        myGameMap = gameMap;
        myShop = shop;
        myCurrentLevel = 0;
        myPlayer = new Player(controller);
        myActiveActions = new ArrayList<Action>();
    }
    
    /**
     * Updates the game during an iteration of the game loop.
     * 
     * @param elapsedTime time elapsed since last clock tick
     */
    public void update (double elapsedTime) {
    	updateActions(elapsedTime);
    	myGameMap.update(elapsedTime);
    	checkRules();
    }
    
    private void updateActions(double elapsedTime) {
    	for(Action action : myActiveActions)
    		action.update(elapsedTime);
    }
    
    /**
     * Applys each of the current applicable rules in play
     */
    private void checkRules() {
    	for(Rule rule : myRules)
    		rule.apply();
    	for(Rule rule : myLevels.get(myCurrentLevel).getRules())
    		rule.apply();
    }

    /**
     * Given a point p, returns the tile object that contains this point.
     * 
     * @param p a point
     * @return a tile at the given point
     */
    public Tile getTile (Point p) {
        return myGameMap.getTile(p);
    }

    /**
     * Jumps to the next wave on the list.
     */
    public void startNextLevel () {
    	if (myLevels.size()>myCurrentLevel) {
            myCurrentLevel++;
        }
        else {
            win();
        }
    }
    
    /**
     * The function called when the winning conditions are met.
     */
    public void win() {
    	//TODO: implement
    	System.out.println("YOU WIN");
    }

    /**
     * paints the map
     * 
     * @param pen a pen
     */
    public void paintMap (Graphics2D pen) {
        myGameMap.paint(pen);
    }

    /**
     * 
     * @return a GameMap object
     */
    public GameMap getMap () {
        return myGameMap;
    }

    /**
     * Paints the shop.
     * 
     * @param pen a pen
     */
    public void paintShop (Graphics2D pen) {
        myShop.paint(pen);
    }

    /**
     * Given a point p, retrieves the shop item at this point.
     * 
     * @param p a point
     * @return the ShopItem at the given point p.
     */
    public ShopItem getShopItem (Point p) {
        return myShop.getShopItem(p);
    }
    
    public Player getPlayer() {
    	return myPlayer;
    }

    
	public void addActions(List<Action> actions) {
		myActiveActions.addAll(actions);
	}

	public Level getActiveLevel() {
		return myLevels.get(myCurrentLevel);
	}
}
