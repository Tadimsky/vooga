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
    private String myExtension;

    /**
     * Constructor
     * Sets the file output name to a default value
     */
    public HandlerTxt () {
        this(LoggerManager.DEFAULT_FILE_NAME, TXT_EXT);
    }
    
    /**
     * Constructor
     * @param fileName Sets the file output name to the string given
     */
    public HandlerTxt (String fileName) {
    	this(fileName, TXT_EXT);
    }
    
    /**
     * Constructor
     * @param fileName Sets the file output name to the string given
     */
    public HandlerTxt (String fileName, String ext) {
        myFileName = fileName;
        myExtension = ext;
    }
    
    /**
     * 
     * @return Name of the file this handler is writing to
     */
    public String getFileName() {
    	return myFileName;
    }
    
    /**
     * 
     * @param file Name of the file (without spaces) to write log
     * records to
     */
    public void setFileName(String file) {
    	myFileName = file.replace("\\s+", "");
    }
    
    /**
     * 
     * @param ext Extension for this file
     */
    public void setExtension (String ext) {
    	if (ext.charAt(0) == '.') {
    		myExtension = ext;
    	}
    	else {
    		LoggerManager.DEFAULT_LOGGER.log(Level.INFO, EXTENSION_ERROR);
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
            LoggerManager.DEFAULT_LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }
}
