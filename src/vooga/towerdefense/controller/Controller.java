package vooga.towerdefense.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.controller.modes.BuildMode;
import vooga.towerdefense.controller.modes.ControlMode;
import vooga.towerdefense.controller.modes.SelectMode;
import vooga.towerdefense.factories.actionfactories.WaveActionFactory;
import vooga.towerdefense.factories.definitions.GameElementDefinition;
import vooga.towerdefense.factories.definitions.UnitDefinition;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.gameElements.Wave;
import vooga.towerdefense.model.GameLoop;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.model.GameModel;
import vooga.towerdefense.model.MapLoader;
import vooga.towerdefense.model.levels.Level;
import vooga.towerdefense.model.rules.NextLevelRule;
import vooga.towerdefense.model.rules.Rule;
import vooga.towerdefense.model.rules.WinRule;
import vooga.towerdefense.model.shop.Shop;
import vooga.towerdefense.model.shop.ShopItem;
import vooga.towerdefense.model.tiles.Tile;
import vooga.towerdefense.model.tiles.factories.TileFactory;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;
import vooga.towerdefense.view.TDView;

/**
 * Controller is the channel of communication between the Model and the View.
 * 
 * @author Angelica Schwartz
 * @author Erick Gonzalez
 * @author Leonard K. Ng'eno
 * @author Jimmy Longley
 */
public class Controller {

	/**
	 * location of resource bundle.
	 */
	private static final String DEFAULT_RESOURCE_PACKAGE = "vooga/towerdefense/resources.";
	/**
	 * resource bundle for this controller.
	 */
	private ResourceBundle myResourceBundle;
	/**
	 * model for this game.
	 */
	private GameModel myModel;
	/**
	 * view for this game.
	 */
	private TDView myView;
	/**
	 * control mode for the controller.
	 */
	private ControlMode myControlMode;

	// TODO: controller constructor should take waves & map in order to
	// initialize GameModel?
	// TODO: fix where the parameters come from
	public Controller(String language) {

		List<Wave> waves = new ArrayList<Wave>();
		String path = "/vooga/towerdefense/resources/map_loadfile.xml";
		//String path = "C:/Users/Leonard/Desktop/308/vooga/src/vooga/towerdefense/resources/map_loadfile.xml";
//		String path = "/Users/XuRui/Documents/CS308workspace/vooga/src/vooga/towerdefense/resources/map_loadfile.xml";
		//String path = "C:\\Users\\JLongley\\workspace\\vooga\\src\\vooga\\towerdefense\\resources\\map_loadfile.xml";
		MapLoader loader = new MapLoader(path);
		List<GameMap> maps = loader.loadMaps();
		GameMap map = maps.get(2);
		// FIXME: Hardcoded for testing trolls
		// ExampleAuraTowerFactory codeStyleGenerator = new
		// ExampleAuraTowerFactory(
		// map, "Tree of Doom", null);
		// codeStyleGenerator.initialize(map);
		// GameElement duvallTheMighty = codeStyleGenerator
		// .createElement(new Location(450, 200));
		// map.addGameElement(duvallTheMighty);
		//
		// waves.add(WaveFactory.createWave(new ExampleUnitFactory("Troll",
		// new TrollUnitDefinition(), map), 25, map, map
		// .getTile(new Point(25, 275))));
		setLanguage(language);

		List<Level> levels = new ArrayList<Level>();
		List<Action> actions = new ArrayList<Action>();

		GameElementFactory factory = new GameElementFactory("Tester", new UnitDefinition());
		WaveActionFactory waveFactory = new WaveActionFactory(10, 200, factory,
				map);
		waveFactory.initialize(map);
		Action action = waveFactory.createAction(null);
		actions.add(action);
		List<Rule> levelRules = new ArrayList<Rule>();

		Level level = new Level(actions, levelRules);
		levels.add(level);
		List<Rule> rules = new ArrayList<Rule>();
		
		myView = new TDView(this);
		
		myModel = new GameModel(this, levels, rules, map, new Shop(map));
		
		rules.add(new WinRule(myModel));
		rules.add(new NextLevelRule(myModel));
		myControlMode = new SelectMode();
	}
	
	/**
	 * gets the view.
	 * @return the view for this controller.
	 */
	public TDView getView() {
	    return myView;
	}

	/**
	 * cancels the purchase and stops painting ghost image.
	 */
	public void cancelPurchaseFromShop() {
		myModel.getMap().resetGhostImage();
		myControlMode = new SelectMode();
		setVisibilityOfShopCancelButton(false);
	}

	private void setVisibilityOfShopCancelButton(boolean visibility) {
		myView.getShopScreen().setCancelButtonVisibility(visibility);
	}

	/**
	 * displays information about the GameElement on the tile.
	 * 
	 * @param p
	 *            is the point that was clicked.
	 */
	public void displayElementInformation(GameElement e) {
		if (e != null) {
			// TODO: update this to reflect actual properties
			myView.getGameElementInfoScreen().displayInformation(
					"Stuff about my clicked tower");
			// myView.getTowerInfoScreen().displayInformation(e.getAttributes().toString());
			if (e.getAttributeManager().hasUpgrades()) {
				List<String> upgrades = new ArrayList<String>(e
						.getAttributeManager().getUpgrades().keySet());
				myView.getGameElementInfoScreen().displayUpgradesAndButton(
						upgrades);
			}
		} else {
			myView.getGameElementInfoScreen().clearScreen();
		}
	}

	/**
	 * updates the display on the MapScreen.
	 */
	public void displayMap() {
		myView.getMapScreen().update();
	}

	/**
	 * places the new item onto the map & changes the mode back to SelectMode.
	 * 
	 * @param item
	 * @param p
	 */
	// TODO The item that should be added should be a new instance of the one in
	// the shop!!!
	public void fixItemOnMap(GameElement item, Point p) {
		GameElement newItem = createNewElement(item);
		Location snappedLocation = getPointSnappedToGrid(new Location(p.getX(),
				p.getY()));
		newItem.setCenter(snappedLocation.getX(), snappedLocation.getY());
		Tile myTile = myModel.getTile(p);
		myTile.setTower(newItem);

		myModel.getMap().addToMap(newItem, myTile);
		displayMap();
		myControlMode = new SelectMode();
		setVisibilityOfShopCancelButton(false);
	}

	/**
	 * gets the associated game element at a point.
	 * 
	 * @param p
	 * @return the game element
	 */
	public GameElement getItemAt(Point p) {
		Tile tile = myModel.getTile(p);
		if (tile.containsElement()) {
			return tile.getElement();
		}
		return null;
	}

	/**
	 * gets the resource bundle for this controller.
	 * 
	 * @return the resource bundle
	 */
	public ResourceBundle getResourceBundle() {
		return myResourceBundle;
	}

	/**
	 * Get the matching string from the resource bundle.
	 * 
	 * @param s
	 *            is the string to match
	 * @return the appropriate string in the selected language
	 */
	public String getStringFromResources(String s) {
		return myResourceBundle.getString(s);
	}

	/**
	 * 
	 * @param item
	 *            Object to create new instance of
	 * @return new instance of item
	 */
	private GameElement createNewElement(GameElement item) {
		try {
			Class<? extends GameElement> myClass = item.getClass();
			@SuppressWarnings("rawtypes")
			Class[] types = { Pixmap.class, Location.class, Dimension.class,
					List.class, String.class };
			Constructor<? extends GameElement> constructor = myClass
					.getConstructor(types);
			Object[] parameters = { item.getPixmap(), item.getCenter(),
					item.getSize(), item.getActions()};
			Object myNewItem = constructor.newInstance(parameters);
			return (GameElement) myNewItem;
		} catch (InvocationTargetException e) {
			// ??
			System.out.println(e.getMessage());
		}

		catch (Exception e) {
			// ??
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * handles a click to the map appropriately depending on the mode.
	 * 
	 * @param p
	 *            is the location of the click
	 */
	public void handleMapMouseDrag(Point p) {
		myControlMode.handleMapMouseDrag(p, this);
	}

	/**
	 * handles a mouse drag on the map appropriately depending on the mode.
	 * 
	 * @param p
	 *            is the location of the mouse
	 */
	public void handleMapClick(Point p) {
		myControlMode.handleMapClick(p, this);
	}

	/**
	 * changes the mode to BuildMode and gets the item the user wants to build
	 * from the Shop.
	 * 
	 * @param itemName
	 *            is the name of the item the user wants to buy
	 */
	public void handleShopClickOnItem(Point p) {
		ShopItem itemToBuy = myModel.getShopItem(p);
		if (itemToBuy == null)
			return;

		List<Action> actions = new ArrayList<Action>();
		GameElementFactory factory = itemToBuy.getFactory();
		GameElement t = factory.createElement(new Location(p.getX(), p.getY()));
		BuildMode myNewMode = new BuildMode();
		myNewMode.setItemToBuild(t);
		myControlMode = myNewMode;
	}

	/**
	 * starts the next level in the model.
	 */
	public void startNextLevel() {
		myModel.startNextLevel();
	}

	public Location getPointSnappedToGrid(Location location) {
		return myModel.getMap().getTile(location).getCenter();

	}

	/**
	 * paints the ghost image of the item on the MapScreen on the mouse's
	 * location.
	 * 
	 * @param p
	 *            is the mouselocation
	 * @param itemImage
	 *            is the image
	 */
	public void paintGhostImage(Pixmap itemImage, Location location,
			Dimension size) {
		displayMap();
		myModel.getMap().addGhostImage(itemImage, location, size);
	}

	/**
	 * paints the map.
	 * 
	 * @param pen
	 */
	public void paintMap(Graphics pen) {
		myModel.paintMap((Graphics2D) pen);
	}

	/**
	 * updates the model.
	 * 
	 * @param elapsedTime
	 */
	public void update(double elapsedTime) {
		myModel.update(elapsedTime);
	}

	/**
	 * upgrades the item to the new type.
	 * 
	 * @param upgradeName
	 */
	// TODO: Fix for game elements to be towers. -matthew
	public void upgradeSelectedItemTo(String upgradeName) {
		GameElement t = ((SelectMode) myControlMode).getCurrentlySelectedItem();
		// t.upgrade(upgradeName);
		// TODO: implement upgrade stuff on backend (ask unit team for tower
		// upgrade info!)
	}

	/**
	 * Sets the language
	 * 
	 * @param language
	 *            the language to set the controller to
	 */
	public void setLanguage(String language) {
		try {
			myResourceBundle = ResourceBundle
					.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start the game controller.
	 */
	public void start() {
		GameLoop game = new GameLoop(this);
		game.start();
	}

	public void paintShop(Graphics pen) {
		myModel.paintShop((Graphics2D) pen);
	}

	/**
	 * Used to determine if a ghost image should be painted, it tests if a tower
	 * can be built at a particular point.
	 * 
	 * @param p
	 * @return
	 */
	public boolean canBuildHere(Point p, int tilesWide, int tilesTall) {
		boolean canBuild = true;
		for (int i = 0; i < tilesWide; i++) {
			for (int j = 0; j < tilesTall; j++) {
				Location location = new Location(p.getX() + i
						* TileFactory.TILE_DIMENSIONS.getWidth(), p.getY() + j
						* TileFactory.TILE_DIMENSIONS.getHeight());
				canBuild = canBuild & myModel.getMap().isBuildable(location);
			}
		}

		return canBuild;
	}

	public void displayPlayerStatistics(Map<String, Integer> playerData) {
		String info = "Player info: \n\n";
		for (String key : playerData.keySet())
			info += key + ": " + playerData.get(key) + "\n";
		myView.getPlayerInfoScreen().displayInformation(info);
	}

}
