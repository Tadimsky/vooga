package arcade.database;

/**
 * Contains keywords for tables
 * @author Natalia Carvalho
 */
public interface Keys {
    //universal keys
    static final String SEPARATOR = ": ";
    static final String EQUALS = "='";
    static final String APOSTROPHE = "'";
    static final String WHERE_KEYWORD = " WHERE ";
    static final String SELECT_FROM = "SELECT * FROM ";

    //keys for comments table
    static final String COM_TABLE_NAME = "comments";
    static final String COM_GAMEID_COLUMN_FIELD = "gameid";  
    static final String COM_USERID_COLUMN_FIELD = "userid";
    static final String COM_COMMENT_COLUMN_FIELD = "commentfield";
    static final String COM_RATING_COLUMN_FIELD = "ratings";
    static final String COM_COMMENTID_COLUMN_FIELD = "commentid";
    static final int COM_GAMEID_COLUMN_INDEX = 1;
    static final int COM_USERID_COLUMN_INDEX = 2;
    static final int COM_COMMENT_COLUMN_INDEX = 3;
    static final int COM_RATING_COLUMN_INDEX = 4;
    static final int COM_COMMENTID_COLUMN_INDEX = 5;
    
    //keys for game table
    static final String GAM_TABLE_NAME = "games";  
    static final String GAM_GAMENAME_COLUMN_FIELD = "gamename";
    static final String GAM_AUTHOR_COLUMN_FIELD = "author";
    static final String GAM_GENRE_COLUMN_FIELD = "genre";
    static final String GAM_THUMBNAIL_COLUMN_FIELD = "thumbnail";
    static final String GAM_ADSCREEN_COLUMN_FIELD = "adscreen";
    static final String GAM_AGEPERMISSION_COLUMN_FIELD = "agepermission";
    static final String GAM_PRICE_COLUMN_FIELD = "price";
    static final String GAM_EXTENDSGAME_COLUMN_FIELD = "extendsgame";
    static final String GAM_EXTENDSMULTIPLAYER_COLUMN_FIELD = "extendsmultiplayegame";
    static final String GAM_SINGLEPLAYER_COLUMN_FIELD = "singleplayer";
    static final String GAM_MULTIPLAYER_COLUMN_FIELD = "multiplayer";
    static final String GAM_DESCRIPTION_COLUMN_FIELD = "description";
    static final String GAM_GAMEID_COLUMN_FIELD = "gameid";  
    
    static final int GAM_GAMENAME_COLUMN_INDEX = 1;
    static final int GAM_AUTHOR_COLUMN_INDEX = 2;
    static final int GAM_GENRE_COLUMN_INDEX = 3;
    static final int GAM_THUMBNAIL_COLUMN_INDEX = 4;
    static final int GAM_ADSCREEN_COLUMN_INDEX = 5;
    static final int GAM_AGEPERMISSION_COLUMN_INDEX = 6;
    static final int GAM_PRICE_COLUMN_INDEX = 7;
    static final int GAM_EXTENDSGAME_COLUMN_INDEX = 8; 
    static final int GAM_EXTENDSMULTIPLAYER_COLUMN_INDEX = 9;
    static final int GAM_SINGLEPLAYER_COLUMN_INDEX = 10;
    static final int GAM_MULTIPLAYER_COLUMN_INDEX = 11;
    static final int GAM_DESCRIPTION_COLUMN_INDEX = 12;
    static final int GAM_GAMEID_COLUMN_INDEX = 13;
    
    //keys for score table
    static final String SCORE_TABLE_NAME = "score";
    static final String SCORE_GAMEID_COLUMN_FIELD = "gameid";  
    static final String SCORE_USERID_COLUMN_FIELD = "userid";
    static final String SCORE_HIGHSCORE_COLUMN_FIELD = "highscore";
    static final String SCORE_SCOREID_COLUMN_FIELD = "scoreid";     
    static final int SCORE_GAMEID_COLUMN_INDEX = 1;
    static final int SCORE_USERID_COLUMN_INDEX = 2;
    static final int SCORE_HIGHSCORE_COLUMN_INDEX = 3;
    static final int SCORE_SCOREID_COLUMN_INDEX = 4;
    
    //keys for user table
    static final String USER_TABLE_NAME = "users";

    static final String USER_USERNAME_COLUMN_FIELD = "username";  
    static final String USER_PASSWORD_COLUMN_FIELD = "pw";
    static final String USER_FIRSTNAME_COLUMN_FIELD  = "firstname";
    static final String USER_LASTNAME_COLUMN_FIELD  = "lastname";
    static final String USER_DOB_COLUMN_FIELD  = "DOB";
    static final String USER_AVATAR_COLUMN_FIELD  = "avatarfilepath";
    static final String USER_USERID_COLUMN_FIELD = "userid";

    static final int USER_USERNAME_COLUMN_INDEX = 1;
    static final int USER_PASSWORD_COLUMN_INDEX = 2;
    static final int USER_FIRSTNAME_COLUMN_INDEX = 3;
    static final int USER_LASTNAME_COLUMN_INDEX = 4;
    static final int USER_DOB_COLUMN_INDEX = 5;
    static final int USER_AVATAR_COLUMN_INDEX = 6;
    static final int USER_USERID_COLUMN_INDEX = 7;
    
}
