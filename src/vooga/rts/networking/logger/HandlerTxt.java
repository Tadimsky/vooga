package vooga.rts.networking.logger;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;


/**
 * Class that sets a handler to the logger and outputs a txt file of file
 * records
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerTxt implements IHandlerFormat {
    private static final String TXT_EXT = ".txt";
    private static final String ERROR_MESSAGE =
            "Error in creating txt format handler";
    private String myFileName;

    /**
     * Constructor
     * 
     * @param fileName name of the file to be written to
     */
    public HandlerTxt (String fileName) {
        myFileName = fileName;
    }

    /**
     * Constructor
     * Sets the file output name to a default value
     */
    public HandlerTxt () {
        myFileName = LoggerSetup.DEFAULT_NAME;
    }

    @Override
    public Handler getFormatHandler () {
        Handler handler = null;
        try {
            handler = new FileHandler(myFileName + TXT_EXT);
            handler.setFormatter(new SimpleFormatter());
        }
        catch (Exception e) {
            NetworkLogger.LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }
}
