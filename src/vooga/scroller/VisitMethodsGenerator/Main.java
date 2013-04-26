package vooga.scroller.VisitMethodsGenerator;

import java.io.IOException;

/**
 * This is a Main class that allows you to run the VisitMethodsGenerator. 
 * 
 * @author Jay Wang
 *
 */
public class Main {
    
    private static final String DIRECTORY_LOCATION = "src/vooga/scroller/VisitMethodsGenerator/files/";
    private static final String FILE_NAME = "visitMethods.txt";

    /**
     * @param args
     * @throws IOException 
     */
    public static void main (String[] args) throws IOException {

        String[] names = {"IPlayer player", "ILevelPortal levelPortal", "IEnemy enemy", "ICollectible collectible"}; //TODO: FILL IN THE SPRITES YOU ARE USING 
        VisitMethodsGenerator generator = new VisitMethodsGenerator(names, DIRECTORY_LOCATION, FILE_NAME);
        generator.generateVisitMethods(names);
        
    }
}
