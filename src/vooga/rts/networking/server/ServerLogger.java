package vooga.rts.networking.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * This class facilitates event logging to one unified log file.
 * The output of this logger does not have to be a file output, but in this
 * implementation it is.
 * 
 * A singleton pattern was chosen because it seemed to be the most closed form solution
 * to the problem
 * without having to teach all team members the intricacies of file logging.
 * I plan on speaking with a TA or Professor Duvall before releasing this
 * as the final implementation (with the hope of finding a better solution),
 * but a working prototype was needed.
 * 
 * @author srwareham
 * 
 */
public class ServerLogger {

    public final static Logger myLogger = Logger.getLogger("Test");

    private static ServerLogger instance = null;

    public static ServerLogger getInstance ()  {
        if (instance == null) {
            //Cannot possibly add handling, as we are unable to log the error if the logger fails!
            try {
                prepareLogger();
            }
            catch (SecurityException e) {
                // 
                e.printStackTrace();
            }
            catch (IOException e) {                
                e.printStackTrace();
            }
            instance = new ServerLogger();
        }
        return instance;
    }

    private static void prepareLogger () throws SecurityException, IOException {
        Handler myFileHandler = new FileHandler("temp.txt");
        myFileHandler.setFormatter(new SimpleFormatter());
        myLogger.addHandler(myFileHandler);
        myLogger.setUseParentHandlers(false);
        myLogger.setLevel(Level.FINEST);
    }

}
