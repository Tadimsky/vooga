package util.logger;


import java.util.logging.Handler;


/**
 * Interface that implements setFormatHandler method for creation of handlers
 * 
 * @author Henrique Moraes
 * 
 */
public interface IVoogaHandler {

    /**
     * Sets a handler for the input
     * 
     * @param log the logger being used for the programm
     */
    public Handler getHandler ();
}
