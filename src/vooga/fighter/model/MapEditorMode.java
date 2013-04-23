package vooga.fighter.model;

import java.awt.Dimension; 
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import util.Location;
import util.input.src.input.PositionObject;
import vooga.fighter.model.loaders.EnvironmentObjectLoader;
import vooga.fighter.model.objects.CharacterObject;
import vooga.fighter.model.objects.EnvironmentObject;
import vooga.fighter.model.objects.GameObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.objects.MouseClickObject;
import vooga.fighter.model.utils.ImageDataObject;
import vooga.fighter.model.utils.State;
import vooga.fighter.model.utils.UpdatableLocation;


/**
 * 
 * @author matthewparides, james
 * 
 */
public class MapEditorMode extends Mode {

    private List<UpdatableLocation> myStartLocations;
    private String myMapName;
    private MapObject myMap;
    private List<EnvironmentObject> myEnviroObjects; //all environmental objects that can be placed
    private EnvironmentObject myCurrentSelection;
    private int myEnviroIndex; //the list index of the current environment object selected

    public MapEditorMode (CollisionManager cd) {
        super(cd);
        myStartLocations = new ArrayList<UpdatableLocation>();
        //myMapName = mapName;
        myMap = null;
        myEnviroObjects = new ArrayList<EnvironmentObject>();
        myCurrentSelection = null;
        myEnviroIndex = 0;
        EnvironmentObjectLoader loader = new EnvironmentObjectLoader();
        myEnviroObjects = (ArrayList<EnvironmentObject>)loader.getEnvironmentObjects();
        initializeEnviroObjects();
    }

    /**
     * Overrides superclass initialize method by creating all objects in the level.
     */
    public void initializeMode () {
        loadMap(myMapName);
    }
    
    public void setMap(MapObject map){
    	myMap = map;
    	addObject(map);
    }
    
    /**
     * initializes all of the environment objects for this editor to be displayed in the upper left hand corner
     * when selected. Also selects the first object as the current object.
     */
    public void initializeEnviroObjects() {
    	for(EnvironmentObject enviro: myEnviroObjects) {
    		double xOffset = enviro.getImageData().getMySize().getWidth();
    		double yOffset = enviro.getImageData().getMySize().getHeight();
    		ImageDataObject newImageLocation = new ImageDataObject(enviro.getImageData().getMyImage(),
    				new Location(xOffset, yOffset), enviro.getImageData().getMySize());
    		enviro.setImageData(newImageLocation);
    	}
    	addObject(myEnviroObjects.get(0));
    }

    /**
     * Updates level mode by calling update in all of its objects.
     */
    public void update (double stepTime, Dimension bounds) {
        List<GameObject> myObjects = getMyObjects();
        handleCollisions();
        for (int i = 0; i < myObjects.size(); i++) {
            GameObject object = myObjects.get(i);
            State state = object.getCurrentState();
            // System.out.printf("Updating %s:\n", object.getClass().toString());
            // System.out.printf("Object current state:\ncurrentFrame: %d\nnumFrames: %d\nNull checks:\nImage: %b\nRectangle: %b\nSize: %b\n",
            // state.myCurrentFrame, state.myNumFrames, (state.getCurrentImage()==null),
            // (state.getCurrentRectangle()==null),
            // (state.getCurrentSize()==null));
            object.update();
            if (object.shouldBeRemoved()) {
                myObjects.remove(object);
                i--;
            }
        }
//        if (shouldModeEnd()) {
 //           signalTermination();
  //      }
    }

    /**
     * Loads the environment objects for a map using the ObjectLoader.
     */
    public void loadMap (String mapName) {
        myMap = new MapObject(mapName);
        myStartLocations = myMap.getStartPositions();
        addObject(myMap);
        List<EnvironmentObject> mapObjects = myMap.getEnviroObjects();
        for (EnvironmentObject object : mapObjects) {
            addObject(object);
        }
    }


    /**
     * Creates the list of image data objects and returns it.
     */
    public List<ImageDataObject> getImageData () {
        List<ImageDataObject> result = new ArrayList<ImageDataObject>();
        for (GameObject object : getMyObjects()) {
            result.add(object.getImageData());
        }
        return result;
    }

    /**
     * Carries out actions associated with a user selecting a location on the map.
     * If they pressed on an existing environment object, that object will be removed.
     * If they pressed an open space, the currently selected environment object will be
     * placed in that location.
     * @param point
     */
    public void select (Point2D point) {
    	MouseClickObject click = new MouseClickObject(point);
    	addObject(click);
    	handleCollisions();
    	removeObject(click);
    	boolean removeExecuted = false;
    	for(GameObject obj: getMyObjects()) {
    		if(obj.shouldBeRemoved()) {
    			removeObject(obj);
    			myMap.removeEnviroObject((EnvironmentObject)obj);
    			removeExecuted = true;
    		}
    		
    	}
    	if(!removeExecuted) {
	    	UpdatableLocation currentLoc = new UpdatableLocation(point.getX(), point.getY());
	    	EnvironmentObject newObj = new EnvironmentObject(myCurrentSelection.getName(), currentLoc);
	    	myMap.addEnviroObject(newObj);
	    	addObject(newObj);
    	}
    }
    
    public void writeMap() {
    	MapWriter writer = new MapWriter(myMap);
    }

    /**
     * selects the next environment object in the list of environment objects (myEnviroObjects)
     */
    public void nextObject () {
    	removeObject(myCurrentSelection);
    	myEnviroIndex++;
    	if(myEnviroIndex == myEnviroObjects.size()) {
    		myEnviroIndex = 0;
    	}
    	myCurrentSelection = myEnviroObjects.get(myEnviroIndex);
    	addObject(myCurrentSelection);
    }

    /**
     * selects the previous environment object in the list of environment objects (myEnviroObjects)
     */
    public void prevObject () {
    	removeObject(myCurrentSelection);
    	myEnviroIndex--;
    	if(myEnviroIndex == -1) {
    		myEnviroIndex = (myEnviroObjects.size() - 1);
    	}
    	myCurrentSelection = myEnviroObjects.get(myEnviroIndex);
    	addObject(myCurrentSelection);
    }

    public MapObject getMap () {
        return myMap;
    }
}
