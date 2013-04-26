package arcade.database;
import arcade.games.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and updates user table
 * this clearly needs to be refactored because there is duplicate code
 * @author Natalia Carvalho
 */
public class ScoreTable extends Table {

    private Connection myConnection;
    private PreparedStatement myPreparedStatement; 
    private ResultSet myResultSet;
    
    /**
     * Constructor but eventually I want to make this part of the abstract class
     */
    public ScoreTable() {
        super();
        myConnection = getDatabaseConnection().getConnection();
        myPreparedStatement = getDatabaseConnection().getPreparedStatement();
        myResultSet = getDatabaseConnection().getResultSet();
    }
    
    /**
     * Add new user game data to table 
     * @param gameid is game id
     * @param userid is user id
     * @param highscore of the game
     */
    public void addNewHighScore (String gameid, String userid, int highscore) {
        
        String stm = "INSERT INTO score(gameid, userid, highscore) VALUES (?, ?, ?)";
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.setString(Keys.SCORE_GAMEID_COLUMN_INDEX, gameid);
            myPreparedStatement.setString(Keys.SCORE_USERID_COLUMN_INDEX, userid);
            myPreparedStatement.setInt(Keys.SCORE_HIGHSCORE_COLUMN_INDEX, highscore);
            myPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            //writeErrorMessage("Error adding new high score in ScoreTable.java @ Line 54");
        }
    }
    
    /**
     * Gets scores for a given game
     * @param gameid is game id
     * @param userid is user id
     * @param gameName is name of game
     * @param userName is user
     */
    public List<Score> getScoresForGame(String gameid, String userid, 
                                        String gameName, String userName) {
        String stm = "SELECT * FROM score WHERE gameid='" + 
                    gameid + "' AND userid='" + userid + "'";
        List<Score> scores = new ArrayList<Score>();
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet  = myPreparedStatement.executeQuery();
            while (myResultSet.next()) {
                Score score = new Score(gameName, userName, 
                                        myResultSet.getInt(Keys.SCORE_HIGHSCORE_COLUMN_INDEX));
                scores.add(score);
            }
            return scores;
        }
        catch (SQLException e) {
            writeErrorMessage("Error getting score for game in ScoreTable.java @ Line 78");
        }
        return null;
    }

    /**
     * Prints entire table
     */
    public void printEntireTable () {
        myResultSet = selectAllRecordsFromTable(Keys.SCORE_TABLE_NAME);
        try {
            while (myResultSet.next()) {
                System.out.print(myResultSet.getString(Keys.SCORE_GAMEID_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.SCORE_USERID_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);                
                System.out.print(myResultSet.getDouble(Keys.SCORE_HIGHSCORE_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.println(myResultSet.getString(Keys.SCORE_SCOREID_COLUMN_INDEX) + 
                                   Keys.SEPARATOR);
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error printing entire table in ScoreTable.java @ Line 99");
        }
    }

  

}