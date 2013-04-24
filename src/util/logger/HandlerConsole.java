package util.logger;


import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;


/**
 * Class that sets a handler to the logger and outputs a file
 * records to the console
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerConsole implements IVoogaHandler {
    private static final String ERROR_MESSAGE =
            "Error in creating Console based handler";

    @Override
    public Handler getHandler () {
        Handler handler = null;
        try {
            handler = new ConsoleHandler();
            handler.setFormatter(new SimpleFormatter());
        }
        catch (Exception e) {
            NetworkLogger.LOGGER.severe(ERROR_MESSAGE);
        }
        return handler;
    }
}
