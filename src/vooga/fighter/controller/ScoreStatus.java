package vooga.fighter.controller;

import java.util.List;

 /**
  * 
 * @author Jerry Li
 *
 */
public class ScoreStatus {
     
    private String myWinner;
    
    public ScoreStatus(List<Integer> winners) {
        for (int i =0; i < winners.size(); i++) {
            myWinner = "Player " + winners.get(i) + " won!";
        }
    }
   

  
}