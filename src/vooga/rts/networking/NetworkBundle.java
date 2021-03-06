package vooga.rts.networking;

import java.util.ResourceBundle;


/**
 * Class used to manage resources of the networking package
 * By default, the resourcebundle seeks an english.properties file and can be
 * accessed statically to preserve lines of code
 * 
 * However, the user can still get a different resourcebundle if he sets the language
 * and/or path
 * 
 * @author Henrique Moraes
 * 
 */
public class NetworkBundle {

    private static String LANGUAGE = "english";
    private static String RESOURCE_PACKAGE = "vooga.rts.networking.resources.";
    private static ResourceBundle BUNDLE = ResourceBundle.getBundle(RESOURCE_PACKAGE + LANGUAGE);
    private static String myLanguage;
    private static String myRelativePath = RESOURCE_PACKAGE;
    private static String CONFIG = "configuration";
    private static ResourceBundle CONFIGURATION_BUNDLE = ResourceBundle.getBundle(RESOURCE_PACKAGE + CONFIG);

    /**
     * 
     * @return ResourceBundle
     */
    public static ResourceBundle getBundle () {
        return BUNDLE;
    }

    /**
     * 
     * @param key Key at properties file
     * @return value associated with the string
     */
    public static String getString (String key) {
        return BUNDLE.getString(key);
    }
    
    /**
     * Returns the configuration item
     * @param key of properties value
     * @return value associated with string
     */
    public static String getConfigurationItem (String key) {
        return CONFIGURATION_BUNDLE.getString(key);
    }
    
    /**
     * Returns whether the config bundle contains the key.
     * @param key of properties value
     * @return whether key is contained
     */
    public static boolean containsConfigurationItem (String key) {
        return CONFIGURATION_BUNDLE.containsKey(key);
    }

    /**
     * Sets the language used by this resourcebundle according to its filename
     * 
     * @param language Name of the file in which resources are located
     */
    public static void setLanguage (String language) {
        myLanguage = language;
        BUNDLE = ResourceBundle.getBundle(myRelativePath + myLanguage);
    }

    /**
     * 
     * @param path The relative path to the desired file
     */
    public static void setRelativePath (String path) {
        if (path.charAt(path.length() - 1) != '.') {
            path = path + '.';
        }
        myRelativePath = path;
    }
}
