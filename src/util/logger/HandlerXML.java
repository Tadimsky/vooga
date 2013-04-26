package util.logger;


import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.XMLFormatter;


/**
 * Class that sets a handler to the logger and outputs an XML file of file
 * records
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerXML implements IVoogaHandler {
    private static final String XML_EXT = ".xml";
    private static final String ERROR_MESSAGE =
            "Error in creating XML format handler";
    private String myFileName;

    /**
     * Constructor
     * 
     * @param fileName name of the file to be written to
     */
    public HandlerXML (String fileName) {
        myFileName = fileName;
    }

    /**
     * Constructor
     * Sets the file output name to a default value
     */
    public HandlerXML () {
        myFileName = LoggerManager.DEFAULT_FILE_NAME;
    }
    
    public void setFileName (String file) {
    	myFileName = file;
    }

    @Override
    public Handler getHandler () {
        Handler handler = null;
        try {
            handler = new FileHandler(myFileName + XML_EXT);
            handler.setFormatter(new XMLFormatter());
        }
        catch (Exception e) {
            LoggerManager.DEFAULT_LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }

}
