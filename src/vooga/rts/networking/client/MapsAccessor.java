package vooga.rts.networking.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import vooga.rts.networking.server.GameContainer;

public class MapsAccessor {
    private static final String DEFAULT_PACKAGE = "vooga.rts.";
    private static final String DEFAULT_RESOURCE = ".resources.maps";
    
    private MapsAccessor() {    
    }
    
    /**
     * 
     * @param gameName name of the game as in the package path
     * @return map containing game specific information
     */
    public static Map<String, Integer> getMaps(String gameName){
    	String path = DEFAULT_PACKAGE + gameName + DEFAULT_RESOURCE;
    	ResourceBundle resource = ResourceBundle.getBundle(path);
    	Map<String,Integer> mapInfo = new HashMap<String,Integer>();
    	for (String s : resource.keySet()){	
    		String info = resource.getString(s);
    		String[] infoArray = info.split(",\\s+");
    		mapInfo.put(infoArray[0], Integer.parseInt(infoArray[1]));	
    	}
    	return mapInfo;
    }
}
