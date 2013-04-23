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

    private static final String SEPARATOR = ": ";
    
    private static final String GAMENAME_COLUMN_FIELD = "gamename";
    private static final String AUTHOR_COLUMN_FIELD = "author";
    private static final String GENRE_COLUMN_FIELD = "genre";
    private static final String THUMBNAIL_COLUMN_FIELD = "thumbnail";
    private static final String ADSCREEN_COLUMN_FIELD = "adscreen";
    private static final String AGEPERMISSION_COLUMN_FIELD = "agepermission";
    private static final String PRICE_COLUMN_FIELD = "price";
    private static final String EXTENDSGAME_COLUMN_FIELD = "extendsgame";
    private static final String EXTENDSMULTIPLAYER_COLUMN_FIELD = "extendsmultiplayegame";
    private static final String SINGLEPLAYER_COLUMN_FIELD = "singleplayer";
    private static final String MULTIPLAYER_COLUMN_FIELD = "multiplayer";
    private static final String DESCRIPTION_COLUMN_FIELD = "description";
    private static final String GAMEFILEPATH_COLUMN_FIELD = "gamefilepath";
    private static final String GAMEID_COLUMN_FIELD = "gameid";  
    
    private static final int GAMENAME_COLUMN_INDEX = 1;
    private static final int AUTHOR_COLUMN_INDEX = 2;
    private static final int GENRE_COLUMN_INDEX = 3;
    private static final int THUMBNAIL_COLUMN_INDEX = 4;
    private static final int ADSCREEN_COLUMN_INDEX = 5;
    private static final int AGEPERMISSION_COLUMN_INDEX = 6;
    private static final int PRICE_COLUMN_INDEX = 7;
    private static final int EXTENDSGAME_COLUMN_INDEX = 8; 
    private static final int EXTENDSMULTIPLAYER_COLUMN_INDEX = 9;
    private static final int SINGLEPLAYER_COLUMN_INDEX = 10;
    private static final int MULTIPLAYER_COLUMN_INDEX = 11;
    private static final int DESCRIPTION_COLUMN_INDEX = 12;
    private static final int GAMEFILEPATH_COLUMN_INDEX = 13;
    private static final int GAMEID_COLUMN_INDEX = 14;

    private static final String TABLE_NAME = "games";  

    private Connection myConnection;
    private PreparedStatement myPreparedStatement; 
    private ResultSet myResultSet;
    
    /**
     * GameTable constructor
     */
    public GameTable() {
        super();
        myConnection = getConnection();
        myPreparedStatement = getPreparedStatement();
        myResultSet = getResultSet();
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
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                   gameName, GAMENAME_COLUMN_INDEX);
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
        String stm = "INSERT INTO " + TABLE_NAME + "(gamename, author, genre, thumbnail, " +
                "adscreen, agepermission, price, extendsgame, " +
                "extendsmultiplayergame, " + "singleplayer, multiplayer, description) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.setString(GAMENAME_COLUMN_INDEX, gameName);
            myPreparedStatement.setString(AUTHOR_COLUMN_INDEX, author);
            myPreparedStatement.setString(GENRE_COLUMN_INDEX, genre);
            myPreparedStatement.setString(THUMBNAIL_COLUMN_INDEX, thumbnailPath);
            myPreparedStatement.setString(ADSCREEN_COLUMN_INDEX, adscreenPath);
            myPreparedStatement.setInt(AGEPERMISSION_COLUMN_INDEX, ageRating);
            myPreparedStatement.setDouble(PRICE_COLUMN_INDEX, price);
            myPreparedStatement.setString(EXTENDSGAME_COLUMN_INDEX, extendsGame);
            myPreparedStatement.setString(EXTENDSMULTIPLAYER_COLUMN_INDEX, extendsMultiplayerGame);
            myPreparedStatement.setBoolean(SINGLEPLAYER_COLUMN_INDEX, singlePlayer);
            myPreparedStatement.setBoolean(MULTIPLAYER_COLUMN_INDEX, multiplayer);
            myPreparedStatement.setString(DESCRIPTION_COLUMN_INDEX, description);
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
        String stm = "SELECT " + GAMENAME_COLUMN_FIELD + " FROM "  + TABLE_NAME;
        List<String> myGameNames = new ArrayList<String>();
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet = myPreparedStatement.executeQuery();
            while (myResultSet.next()) {
                myGameNames.add(myResultSet.getString(GAMENAME_COLUMN_INDEX));
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
        String stm = "DELETE FROM " + TABLE_NAME + Table.WHERE_KEYWORD + 
                GAMENAME_COLUMN_FIELD + Table.EQUALS + gameName + Table.APOSTROPHE;
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
        myResultSet = selectAllRecordsFromTable(TABLE_NAME);
        try {
            while (myResultSet.next()) {
                System.out.print(myResultSet.getString(GAMENAME_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(AUTHOR_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(GENRE_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(THUMBNAIL_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(ADSCREEN_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getInt(AGEPERMISSION_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getDouble(PRICE_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(EXTENDSGAME_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(EXTENDSMULTIPLAYER_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getBoolean(SINGLEPLAYER_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getBoolean(MULTIPLAYER_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(DESCRIPTION_COLUMN_INDEX) + SEPARATOR);
                System.out.print(myResultSet.getString(GAMEFILEPATH_COLUMN_INDEX) + SEPARATOR);
                System.out.println(myResultSet.getString(GAMEID_COLUMN_INDEX));
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error printing entire table in GameTable.java @ Line 182");
        }
    }
    
    /**
     * Given a gamename, retrieves genre
     * @param gameName is the gamename
     */
    public String getGenre(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, gameName, GENRE_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves author
     * @param gameName is the gamename
     */
    public String getAuthor(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, gameName, AUTHOR_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves thumbnail path
     * @param gameName is the gamename
     */
    public String getThumbnailPath(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, gameName, 
                                   THUMBNAIL_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves adscreen path
     * @param gameName is the gamename
     */
    public String getAdScreenPath(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD,
                                   gameName, ADSCREEN_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves author
     * @param gameName is the gamename
     */
    public int getAgePermission(String gameName) {
        return retrieveEntryInt(TABLE_NAME, GAMENAME_COLUMN_FIELD, gameName, AUTHOR_COLUMN_INDEX);
    }
    
    
    /**
     * Given a gamename, retrieves price
     * @param gameName is the gamename
     */
    public double getPrice(String gameName) {
        return retrieveEntryDouble(gameName, PRICE_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgame
     * @param gameName is the gamename
     */
    public String getExtendsGame(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                   gameName, EXTENDSGAME_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public String getExtendsGameMultiplayer(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                   gameName, EXTENDSMULTIPLAYER_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public boolean getIsSinglePlayer(String gameName) {
        return retrieveEntryBoolean(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                    gameName, SINGLEPLAYER_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves extendsgamemultiplayer
     * @param gameName is the gamename
     */
    public boolean getIsMultiplayer(String gameName) {
        return retrieveEntryBoolean(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                    gameName, MULTIPLAYER_COLUMN_INDEX);
    }
    
    
    /**
     * Given a gamename, retrieves description
     * @param gameName is the gamename
     */
    public String getDescription(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                   gameName, DESCRIPTION_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename, retrieves description
     * @param gameName is the gamename
     */
    public String getGameFilePath(String gameName) {
        return retrieveEntryString(TABLE_NAME, GAMENAME_COLUMN_FIELD, 
                                   gameName, GAMEFILEPATH_COLUMN_INDEX);
    }
    
    /**
     * Given a gamename and a column_index, returns that entire row entry
     * @param gameName is the gamename
     * @param columnIndex is the index that we want the information for
     */
    public double retrieveEntryDouble(String gameName, int columnIndex) {
        String stm = "SELECT * FROM " + TABLE_NAME + Table.WHERE_KEYWORD + 
                GAMENAME_COLUMN_FIELD + Table.EQUALS + gameName + Table.APOSTROPHE;
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