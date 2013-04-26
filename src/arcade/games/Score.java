package arcade.games;

/**
 * Data structure for representing a score in the view. Instantiated by the Database and
 * is read only.
 * 
 */
public class Score implements Comparable<Score>{
    String myGame;
    String myUser;
    double myScore;

    public Score (String gameName, String userName, double score) {
        myGame = gameName;
        myUser = userName;
        myScore = score;
    }

    public String getGame () {
        return myGame;
    }

    public String getUser () {
        return myUser;
    }

    public double getScore () {
        return myScore;
    }

    @Override
    public int compareTo (Score other) {
        return (int) (this.getScore() - other.getScore());
    }

}
