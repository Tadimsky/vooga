package util.logger;


import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that handles a logger. It makes use of the singleton pattern
 * to guarantee the use of a single logger in the program
 * This class does not propagate LogRecords to parent Loggers, so if the
 * It sets a console handler as a default handler
 * 
 * By default, the logger starts at ALL level so any type of message is logged
 * 
 * Note that the user does not need to use any other class in this package 
 * except this one to manage the logger
 * 
 * @author Henrique Moraes
 */
public class NetworkLogger {
    public static final Logger LOGGER =
            Logger.getLogger(NetworkLogger.class.getName());
    private static final String TXT_EXT = ".txt";
    private static final String LOG_EXT = ".log";
    public static final String DEFAULT_FILE_NAME = "Logger";
    
    private HandlerConsole myConsoleHandler = new HandlerConsole();
    private HandlerSocket mySocketHandler = new HandlerSocket();
    private HandlerStream myStreamHandler = new HandlerStream();;
    private HandlerTxt myTxtHandler = new HandlerTxt();
    private HandlerXML myXMLHandler = new HandlerXML();
    private HandlerMemory myMemoryHandler = new HandlerMemory();

    private static NetworkLogger instance = new NetworkLogger();

    /**
     * 
     * @return instance of this Network Logger
     */
    public static NetworkLogger getInstance () {
        return instance;
    }

    /**
     * Private constructor of this singleton
     */
    private NetworkLogger () {
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        addHandler(myConsoleHandler);
    }

    /**
     * Adds a handler to the logger and sets it to the logger level
     * All add handler methods eventually pass through this method
     * Can be used to set customized handlers for the logger in case
     * the user wants to extend the design
     * 
     * @param handlerType the type of handler to be added
     */
    public void addHandler (IVoogaHandler hand) {
    	Handler handler = hand.getHandler();
    	handler.setLevel(NetworkLogger.LOGGER.getLevel());
        LOGGER.addHandler(handler);
    }

    /**
     * Adds a memory handler to the logger depending on given handler and
     * constraints. A memory handler pushes all log records after a message of
     * the specified threshold level is logged
     * This API was designed to be able to combine any other handler to the
     * memoryHandler
     * WARNING the type of handler added will have level INFO. 
     * Once it is set, its level cannot be changed. If the user wishes to set
     * the handler from memory, he should set it manually and call the regular
     * addHandler()
     * 
     * @param handler the type of handler to have records pushed to
     * @param size Number of maximum records this handler will maintain
     * @param pushLevel push to handler as soon as a message of the given 
     * level is issued
     */
    public void addMemoryHandler (IVoogaHandler handler, int size, Level pushLevel) {
    	myMemoryHandler.setProperties(handler, size, pushLevel);
        addHandler(myMemoryHandler);
    }
    
    /**
     * Adds a memory handler to the logger depending on given handler and
     * constraints. A memory handler pushes all log records after a message of
     * the specified threshold level is logged
     * This API was designed to be able to combine any other handler to the
     * memoryHandler
     * WARNING the type of handler added will have level INFO. 
     * Once it is set, its level cannot be changed. If the user wishes to set
     * the handler from memory, he should set it manually and call the regular
     * addHandler()
     * @param handler the type of handler to have records pushed to
     */
    public void addMemoryHandler (IVoogaHandler handler) {
    	myMemoryHandler.setHandler(handler);
        addHandler(myMemoryHandler);
    }
    
    /**
     * 
     * Adds a handler that sends log records to the Console
     */
    public void addConsoleHandler () {
        addHandler(myConsoleHandler);
    }
    
    /**
     * 
     * Adds a handler that records messages in a txt file
     */
    public void addTxtHandler () {
    	addCustomExtensionHandler(myTxtHandler.getFileName(), TXT_EXT);
    }
    
    /**
     * 
     * Adds a handler that records messages in a txt file
     * 
     * @param fileName Name of the file to have records written to
     */
    public void addTxtHandler (String fileName) {
    	myTxtHandler.setFileName(fileName);
    	addHandler(myTxtHandler);
    }
    
    /**
     * 
     * Adds a handler that records messages in a file with user-defined extension
     * 
     * @param fileName Name of the file to have records written to
     * @param ext The extension of the file
     */
    public void addCustomExtensionHandler (String fileName, String ext) {
    	myTxtHandler.setExtension(ext);
    	addTxtHandler(fileName);
    }
    
    /**
     * 
     * Adds a handler that records messages in a .log file
     * 
     * @param fileName Name of the file to have records written to
     */
    public void addLogHandler (String fileName) {
    	addCustomExtensionHandler (fileName, LOG_EXT);
    }
    
    /**
     * 
     * Adds a handler that records messages in an XML file
     * 
     * @param fileName Name of the file to have records written to
     */
    public void addXMLHandler (String fileName) {
    	myXMLHandler.setFileName(fileName);
    	addXMLHandler();
    }
    
    /**
     * 
     * Adds a handler that records messages in an XML file
     */
    public void addXMLHandler () {
    	addHandler (myXMLHandler);
    }

    /**
     * 
     * Adds a handler that sends log records across a given stream
     * 
     * @param out Outputstream that this handler should write to
     */
    public void addStreamHandler (OutputStream out) {
        myStreamHandler.setOutputStream(out);
        addStreamHandler();
    }
    
    /**
     * 
     * Adds a handler that sends log records across a given stream
     */
    public void addStreamHandler () {
        LOGGER.addHandler(myStreamHandler.getHandler());
    }

    /**
     * 
     * Adds a handler that sends log records through a given socket
     * 
     * @param host string with the name of the host of this connection
     * @param port number of the port to be used
     */
    public void addSocketHandler (String host, int port) {
        mySocketHandler.setSocket(host, port);
        addHandler(mySocketHandler);
    }
    
    /**
     * 
     * Adds a handler that sends log records via e-mail
	 * 
	 * @param from Address from which the e-mail is sent
	 * @param to String array with recipients to send e-mail to
	 * @param server Server address
	 * @param subject Subject of e-mail
	 * @param message Text in e-mail
	 */
    public void addMailHandler (String from, String[] to,
			String server, String subject, String message) {
        addHandler(new HandlerMail(from, to, server, subject, message));
    }

    /**
     * Sets the level of the logger and all its handlers
     * 
     * @param level
     */
    public static void setLevel (Level level) {
        LOGGER.setLevel(level);
        for (Handler h : LOGGER.getHandlers()) {
            h.setLevel(level);
        }
    }

    /**
     * Logs a message into the logger with its current level
     * 
     * @param message
     */
    public static void logMessage (String message) {
        LOGGER.log(LOGGER.getLevel(), message);
    }

    /**
     * 
     * @param level Constant representing the level of the message
     * @param message String containing the message to be logged
     */
    public static void logMessage (Level level, String message) {
        LOGGER.log(level, message);
    }

}
