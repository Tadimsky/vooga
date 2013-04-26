package vooga.fighter.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import vooga.fighter.model.objects.EnvironmentObject;
import vooga.fighter.model.objects.MapObject;
import vooga.fighter.model.utils.UpdatableLocation;
import vooga.towerdefense.util.XMLTool;


/**
 * Class used to write out map objects to the specific xml format used by the model team.
 * this version of a map writer writes raw strings instead of using a tool in order to 
 * conform fully to our xml format.
 * @author matthewparides
 *
 */
public class RawMapWriter {
	private static final String INPUT_PATHWAY = "vooga.fighter.config.maps";
	
	private MapObject myWriteSource;
	private ArrayList<String> myWriteOutLines;
	private XMLTool myXMLWriter;
	private Element myFakeRoot; 
	private Element myRoot;
	private String mySoundFilePath;
	private List<String> myBackgroundFilePaths;
	
	public RawMapWriter(MapObject map, String soundFilePath, List<String> backgroundFilePaths) {
		myWriteSource = map;
		myBackgroundFilePaths = backgroundFilePaths;
	}
	
	public void writeMap() {
		String name = myWriteSource.getName();
		List<UpdatableLocation> startingPos = myWriteSource.getStartPositions();
		List<EnvironmentObject> enviroObjects = myWriteSource.getEnviroObjects();
		//start writeout
		myWriteOutLines.add(writeMapHeaderString());
		for(UpdatableLocation loc: startingPos) {
			myWriteOutLines.add(writeStartPosString(loc));
		}
		myWriteOutLines.add(writeStateHeaderString("background"));
		for(String str: myBackgroundFilePaths) {
			myWriteOutLines.add(writeStateString(str));
		}
		myWriteOutLines.add("</state>");
		for(EnvironmentObject enviro: enviroObjects) {
			myWriteOutLines.add(writeEnvironmentObjectString(enviro));
		}
		myWriteOutLines.add("</map>");
	}
	
	private String writeMapHeaderString() {
		String ret= "map mapName=\"" + myWriteSource.getName() + "\" " +
				"xSize=\"1024\" ySize=\"871\">";
		return ret;
	}
	
	private String writeStartPosString(UpdatableLocation loc) {
		String ret = "startingPos xCoord=\"" + 
				loc.getLocation().getX() + "\" yCoord=\"" + loc.getLocation().getY() + "\"/>";
		return ret;
	}
	
	private String writeEnvironmentObjectString(EnvironmentObject enviro) {
		String ret = "environmentObject objectName=\"" + 
				enviro.getName() + "\" xCoord=\"" + enviro.getLocation().getLocation().getX() 
				+ "\" yCoord=\"" + enviro.getLocation().getLocation().getY() + "\" />";
		return ret;
	}
	
	private String writeStateHeaderString(String stateName) {
		String ret = "<state stateName = \"" + stateName + "\">";
		return ret;
	}
	
	private String writeStateString(String statePath) {
		String ret = "<frame image = \"" + statePath + "\" />";
		return ret;
	}
}

/*
<map mapName="RainbowBackground" xSize="1024" ySize="871">
<startingPos xCoord="140" yCoord="300"/>
<startingPos xCoord="200" yCoord="300"/>
<state stateName = "background">
    <frame image = "fighter/images/RainbowBackground.jpeg" />
</state>
<environmentObject objectName="platform" xCoord="300" yCoord="300" />
</map>
*/