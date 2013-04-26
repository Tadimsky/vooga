package arcade.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and updates game table
 * this clearly needs to be refactored because there is duplicate code.
 * @author Natalia Carvalho
 */
public class GameTable extends Table {

    private Connection myConnection;
    private PreparedStatement myPreparedStatement; 
    private ResultSet myResultSet;
    
    /**
     * GameTable constructor
     */
    public GameTable() {
        super();
        myConnection = getDatabaseConnection().getConnection();
        myPreparedStatement = getDatabaseConnection().getPreparedStatement();
        myResultSet = getDatabaseConnection().getResultSet();
    }

    /**
     * Returns true if gameName already exists, false otherwise
     * @param gameName is the name of game
     */
    public boolean gameNameExists(String gameName) {
        String stm = "SELECT gamename FROM games WHERE gamename='" + gameName + "'";
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet  = myPreparedStatement.executeQuery();
            if (myResultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error determining if game name exists in GameTable.jave @Line 70");
        }
        return false;
    }
    
    /**
     * Given a gameName, retrieves a gameID
     * @param gameName is the game's name
     */
    public String retrieveGameId(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_GAMENAME_COLUMN_INDEX);
    }
    
    /**

     * Given the gameName, adds a game with needed information to database
     * @param gameName of game
     * @param author of game
     * @param genre of game
     * @param price of game
     * @param extendsGame string
     * @param extendsMultiplayerGame string
     * @param ageRating permissions
     * @param singlePlayer is true if game is for singleplayer
     * @param multiplayer is true if game is a multiplayer game
     * @param thumbnailPath is where game thumbnail resides
     * @param adscreenPath is where adscreen resides
     * @param description of game
     */
    public boolean createGame(String gameName, String author, String genre, double price, 
                              String extendsGame, String extendsMultiplayerGame, int ageRating, 
                              boolean singlePlayer, boolean multiplayer, String thumbnailPath, 
                              String adscreenPath, String description) {
        if (gameNameExists(gameName)) {
            return false;
        }
        String stm = "INSERT INTO " + Keys.GAM_TABLE_NAME + "(gamename, author, genre, thumbnail, " +
                "adscreen, agepermission, price, extendsgame, " +
                "extendsmultiplayergame, " + "singleplayer, multiplayer, description) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.setString(Keys.GAM_GAMENAME_COLUMN_INDEX, gameName);
            myPreparedStatement.setString(Keys.GAM_AUTHOR_COLUMN_INDEX, author);
            myPreparedStatement.setString(Keys.GAM_GENRE_COLUMN_INDEX, genre);
            myPreparedStatement.setString(Keys.GAM_THUMBNAIL_COLUMN_INDEX, thumbnailPath);
            myPreparedStatement.setString(Keys.GAM_ADSCREEN_COLUMN_INDEX, adscreenPath);
            myPreparedStatement.setInt(Keys.GAM_AGEPERMISSION_COLUMN_INDEX, ageRating);
            myPreparedStatement.setDouble(Keys.GAM_PRICE_COLUMN_INDEX, price);
            myPreparedStatement.setString(Keys.GAM_EXTENDSGAME_COLUMN_INDEX, extendsGame);
            myPreparedStatement.setString(Keys.GAM_EXTENDSMULTIPLAYER_COLUMN_INDEX, 
                                          extendsMultiplayerGame);
            myPreparedStatement.setBoolean(Keys.GAM_SINGLEPLAYER_COLUMN_INDEX, singlePlayer);
            myPreparedStatement.setBoolean(Keys.GAM_MULTIPLAYER_COLUMN_INDEX, multiplayer);
            myPreparedStatement.setString(Keys.GAM_DESCRIPTION_COLUMN_INDEX, description);
            myPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            writeErrorMessage("Error creating game in GameTable.java @ Line 119");
        }
        return true;
    }

    
    /**
     * Returns a list of all the games
     */
    public List<String> retrieveGameList() {
        String stm = "SELECT " + Keys.GAM_GAMENAME_COLUMN_FIELD + " FROM "  + Keys.GAM_TABLE_NAME;
        List<String> myGameNames = new ArrayList<String>();
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet = myPreparedStatement.executeQuery();
            while (myResultSet.next()) {
                myGameNames.add(myResultSet.getString(Keys.GAM_GAMENAME_COLUMN_INDEX));
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving game list in GameTable.java @Line 148");
        }
        return myGameNames; 
    }
     
    /**
     * Given a game, deletes that game from gameTable
     * @param gameName is gameName
     */
    public void deleteGame(String gameName) {
        String stm = "DELETE FROM " + Keys.GAM_TABLE_NAME + Keys.WHERE_KEYWORD + 
                Keys.GAM_GAMENAME_COLUMN_FIELD + Keys.EQUALS + gameName + Keys.APOSTROPHE;
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            writeErrorMessage("Error deleting game in GameTable.java @ Line 168");
        }
    }
     
    /**
     * Prints entire table
     */
    public void printEntireTable () {
        myResultSet = selectAllRecordsFromTable(Keys.GAM_TABLE_NAME);
        try {
            while (myResultSet.next()) {
                System.out.print(myResultSet.getString(Keys.GAM_GAMENAME_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_AUTHOR_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_GENRE_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_THUMBNAIL_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_ADSCREEN_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getInt(Keys.GAM_AGEPERMISSION_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getDouble(Keys.GAM_PRICE_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_EXTENDSGAME_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_EXTENDSMULTIPLAYER_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getBoolean(Keys.GAM_SINGLEPLAYER_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getBoolean(Keys.GAM_MULTIPLAYER_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.GAM_DESCRIPTION_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.println(myResultSet.getString(Keys.GAM_GAMEID_COLUMN_INDEX));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            //writeErrorMessage("Error printing entire table in GameTable.java @ Line 182");
        }
    }
    
    /**
     * Given a gamename, retrieves genre
     * @param gameName is the gamename
     */
    public String getGenre(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_GENRE_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves author
     * @param gameName is the gamename
     */
    public String getAuthor(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_AUTHOR_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves thumbnail path
     * @param gameName is the gamename
     */
    public String getThumbnailPath(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, gameName, 
                                   Keys.GAM_THUMBNAIL_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves adscreen path
     * @param gameName is the gamename
     */
    public String getAdScreenPath(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD,
                                   gameName, Keys.GAM_ADSCREEN_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves age permission
     * @param gameName is the gamename
     */
    public int getAgePermission(String gameName) {
        return retrieveEntryInt(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                gameName, Keys.GAM_AGEPERMISSION_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves price
     * @param gameName is the gamename
     */
    public double getPrice(String gameName) {
        return retrieveEntryDouble(gameName, Keys.GAM_PRICE_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgame
     * @param gameName is the gamename
     */
    public String getExtendsGame(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_EXTENDSGAME_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public String getExtendsGameMultiplayer(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_EXTENDSMULTIPLAYER_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public boolean getIsSinglePlayer(String gameName) {
        return retrieveEntryBoolean(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                    gameName, Keys.GAM_SINGLEPLAYER_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public boolean getIsMultiplayer(String gameName) {
        return retrieveEntryBoolean(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                    gameName, Keys.GAM_MULTIPLAYER_COLUMN_INDEX);
    }
    
    
    /**
     * Given a gamename, retrieves description
     * @param gameName is the gamename
     */
    public String getDescription(String gameName) {
        return retrieveEntryString(Keys.GAM_TABLE_NAME, Keys.GAM_GAMENAME_COLUMN_FIELD, 
                                   gameName, Keys.GAM_DESCRIPTION_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename and a column_index, returns that entire row entry
     * @param gameName is the gamename
     * @param columnIndex is the index that we want the information for
     */
    public double retrieveEntryDouble(String gameName, int columnIndex) {
        String stm = "SELECT * FROM " + Keys.GAM_TABLE_NAME + Keys.WHERE_KEYWORD + 
                Keys.GAM_GAMENAME_COLUMN_FIELD + Keys.EQUALS + gameName + Keys.APOSTROPHE;
        double entry = 0;
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet = myPreparedStatement.executeQuery();
            if (myResultSet.next()) {
                entry = myResultSet.getDouble(columnIndex);
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving entry double in GameTable.java @ Line 320");
        }
        return entry;
    }
}