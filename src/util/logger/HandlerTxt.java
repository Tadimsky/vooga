package util.logger;


import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;


/**
 * Class that sets a handler to the logger and outputs a txt file of file
 * records
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerTxt implements IVoogaHandler {
    private static final String TXT_EXT = ".txt";
    private static final String ERROR_MESSAGE =
            "Error in creating txt format handler";
    private static final String EXTENSION_ERROR =
            "Not a valid extension";
    private String myFileName;
    private String myExtension = TXT_EXT;

    /**
     * Constructor
     * Sets the file output name to a default value
     */
    public HandlerTxt () {
        myFileName = NetworkLogger.DEFAULT_FILE_NAME;
    }
    
    public String getFileName() {
    	return myFileName;
    }
    
    public void setFileName(String file) {
    	myFileName = file.replace("\\s+", "");
    }
    
    public void setExtension (String ext) {
    	if (ext.charAt(0) == '.') {
    		myExtension = ext;
    	}
    	else {
    		NetworkLogger.getLogger().log(Level.INFO, EXTENSION_ERROR);
    	}
    }

    @Override
    public Handler getHandler () {
        Handler handler = null;
        try {
            handler = new FileHandler(myFileName + myExtension);
            handler.setFormatter(new SimpleFormatter());
        }
        catch (Exception e) {
            NetworkLogger.LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }
}
