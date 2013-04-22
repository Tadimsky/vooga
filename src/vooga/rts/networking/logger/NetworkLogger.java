package vooga.rts.networking.logger;


import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that handles a logger. It makes use of the singleton pattern
 * to guarantee the use of a single logger in the program
 * This class does not propagate LogRecords to parent Loggers, so if the
 * user wants the log printed on the console, he should manually call addHandler
 * with FORMAT_CONSOLE as input
 * 
 * By default, the logger starts at ALL level so any type of message is logged
 * 
 * >>>Note that the user does not need to use any other class in this package 
 * except this one to manage the logger
 * 
 * @author Henrique Moraes
 */
public class NetworkLogger {
    public static final Logger LOGGER =
            Logger.getLogger(NetworkLogger.class.getName());
    public static final int FORMAT_XML = 1221;
    public static final int FORMAT_TXT = 1356;
    public static final int FORMAT_CONSOLE = 1209;
    public static final int FORMAT_STREAM = 1200;
    public static final int FORMAT_SOCKET = 1333;
    public static final int FORMAT_LOG_FILE = 1398;

    private static NetworkLogger instance = null;
    private LoggerSetup mySetup;

    /**
     * 
     * @return instance of this Network Logger
     */
    public static NetworkLogger getInstance () {
        if (instance == null) {
            instance = new NetworkLogger();
        }
        return instance;
    }

    /**
     * Private constructor of this singleton
     */
    private NetworkLogger () {
        mySetup = new LoggerSetup();
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        addHandler(FORMAT_CONSOLE);
    }

    /**
     * Adds a handler to the logger depending on static constants
     * 
     * @param handlerType the type of handler to be added
     */
    public void addHandler (int handlerType) {
        mySetup.addHandler(handlerType);
    }

    /**
     * Adds a memory handler to the logger depending on static constants and
     * constraints. A memory handler pushes all log records after a message of
     * the specified threshold level is logged
     * This API was designed to be able to combine any other handler to the
     * memoryHandler
     * WARNING the type of handler added will have the same level as the
     * current level of the logger. Once it is set, its level cannot be changed
     * 
     * @param handlerType the type of handler to have records pushed to
     * @param size Number of maximum records this handler will maintain
     * @param push as soon as a message of the given level is issued
     * @param args any necessary argument to create Socket or Stream handlers
     */
    public void addMemoryHandler (int handlerType, int size, Level pushLevel, Object ... args) {
        mySetup.addMemoryHandler(handlerType, size, pushLevel, args);
    }

    /**
     * 
     * Adds a handler that sends log records across a given stream
     * 
     * @param Output stream in case using a stream handler
     */
    public void addStreamHandler (OutputStream out) {
        mySetup.addStreamHandler(out);
    }

    /**
     * 
     * Adds a handler that sends log records through a given socket
     * 
     * @param host string with the name of the host of this connection
     * @param port number of the port to be used
     */
    public void addSocketHandler (String host, int port) {
        mySetup.addSocketHandler(host, port);
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
    
    /**
     * 
     * @param fileName name of the file to write to in case a file handler is used
     */
    public void setFileName (String fileName) {
        mySetup.setFileName(fileName);
    }

}
