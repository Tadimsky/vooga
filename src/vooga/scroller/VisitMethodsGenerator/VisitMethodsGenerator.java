package vooga.scroller.VisitMethodsGenerator;

import java.io.IOException;
import util.Secretary;

/**
 * This is an automated code generator that I wrote half for fun, half for actual use. The idea 
 * is that if you are implementing all the collision logic, it can be a real pain to generate all 
 * those visit methods, especially if you need visit methods for > 8 things. The number of visit 
 * methods you will always need is n choose 2, where n is the number of different entities (different 
 * with regards to how they collide with each other). 
 * <br>
 * <br>
 * This class basically allows you to generate all the visit methods you need by passing in a location, 
 * a file name, and a String array that has all the entities (as Strings) you want visit methods for. 
 * 
 * @author Jay Wang
 *
 */
public class VisitMethodsGenerator {

    private Secretary mySecretary;
        
    private static final String PART_ONE = "public void visit (";
    private static final String PART_TWO = ") {}";
    private static final String COMMA = ", ";
        
   
    public VisitMethodsGenerator (String[] names, String dir_location, String file_name) throws IOException {
        mySecretary = new Secretary(dir_location, file_name);
    }

    /**
     * This method is a helper I created to generate all the visit methods CollisionManager
     * uses. It can be a real pain typing out all those visit methods. This method merely
     * takes a list of Strings - you need a unique String for each sprite type you have -
     * and it writes all combinations of sprite combinations to calculate all visit method
     * combinations.
     * 
     * The result is stored in a file called visitMethods.txt under the files package of
     * collision_manager.
     * 
     * @param List<Strings> spriteStrings
     * @author Jay Wang
     * @throws IOException 
     */
    public void generateVisitMethods (String[] names) throws IOException {
        System.out.println("called");
        for (int i = 0; i < names.length; i++) {
            for (int j = i + 1; j < names.length; j++) {

                mySecretary.write(PART_ONE + names[i] + COMMA + names[j] +
                                  PART_TWO);
            }
        }
    }
}
