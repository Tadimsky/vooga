package util.logger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.MemoryHandler;

/**
 * Class that sets a memory handler to the logger and outputs a records 
 * through a given handler after a defined level message is issued
 * 
 * @author Henrique Moraes
 * 
 */
public class HandlerMemory implements IVoogaHandler{
	private static final Level DEFAULT_PUSH_LEVEL = Level.SEVERE;
	private static final Level DEFAULT_HANDLER_LEVEL = Level.INFO;
	private static final int DEFAULT_LIMIT = 100;
	private static final String ERROR_MESSAGE =
            "Error in creating memory format handler";
	
	private Level myPushLevel;
	private Level myHandlerLevel = DEFAULT_HANDLER_LEVEL;
	private int mySize;
	private IVoogaHandler myHandler;
	
	/**
	 * Constructor
	 * @param handler the type of handler to have records pushed to
     * @param size Number of maximum records this handler will maintain
     * @param pushLevel push to handler as soon as a message of the given
	 */
	public HandlerMemory(IVoogaHandler hand, int size, Level pushLevel) {
		setProperties(hand,size,pushLevel);
	}
	
	/**
	 * Constructor
	 * Sets default size and push level
	 */
	public HandlerMemory() {
		myPushLevel = DEFAULT_PUSH_LEVEL;
		mySize = DEFAULT_LIMIT;
	}
	
	/**
	 * 
	 * @param handler the type of handler to have records pushed to 
	 */
	public void setHandler(IVoogaHandler hand) {
		myHandler = hand;
	}
	
	/**
	 * 
	 * @param level level of the handler in which records are written to
	 */
	public void setHandlerLevel(Level level) {
		myHandlerLevel = level;
	}
	
	/**
	 * 
	 * @param size Number of maximum records this handler will maintain
	 */
	public void setSize(int size) {
		mySize = size;
	}

	/**
	 * 
	 * @param pushLevel push to handler as soon as a message of the given
	 */
	public void setPushLevel(Level pushLevel) {
		myPushLevel = pushLevel;
	}

	/**
	 * 
	 * @param handler the type of handler to have records pushed to
     * @param size Number of maximum records this handler will maintain
     * @param pushLevel push to handler as soon as a message of the given
	 */
	public void setProperties(IVoogaHandler hand, int size, Level pushLevel) {
		setHandler(hand);
		setSize(size);
		setPushLevel(pushLevel);
	}
	
	@Override
	public Handler getHandler() {
		 Handler memHandler = null;
	        try {
	        	Handler handler = myHandler.getHandler();
	        	handler.setLevel(myHandlerLevel);
	            memHandler = new MemoryHandler(handler,mySize,myPushLevel);
	        }
	        catch (Exception e) {
	            NetworkLogger.LOGGER.severe(ERROR_MESSAGE);
	        }
	        return memHandler;
	}

}
