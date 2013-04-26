package util.logger;


import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * Class that sets a handler to the logger and outputs a records to a given
 * stream
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerStream implements IVoogaHandler {
    private static final String ERROR_MESSAGE =
            "Error in creating stream format handler";
    private OutputStream myOutputStream;

    /**
     * Constructor
     * @param out OutputStream associated with the desired handler
     * if nothing is given, the stream defaults to System.out
     */
    public HandlerStream (OutputStream out) {
        if (out == null) {
            out = System.out;
        }
        myOutputStream = System.out;
    }

    /**
     * Constructor
     * defaults the stream of the handler to System.out
     */
    public HandlerStream () {
        myOutputStream = System.out;
    }
    
    public void setOutputStream (OutputStream out) {
    	myOutputStream = out;
    }

    @Override
    public Handler getHandler () {
        Handler handler = null;
        try {
            handler = new StreamHandler(myOutputStream, new SimpleFormatter());
        }
        catch (Exception e) {
            LoggerManager.DEFAULT_LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }

}
