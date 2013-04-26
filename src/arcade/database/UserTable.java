package arcade.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and updates user table
 * @author Natalia Carvalho
 */
public class UserTable extends Table {

    private Connection myConnection;
    private PreparedStatement myPreparedStatement; 
    private ResultSet myResultSet;
    
    /**
     * Constructor but eventually I want to make this part of the abstract class
     */
    public UserTable() {
        super();
        myConnection = getDatabaseConnection().getConnection();
        myPreparedStatement = getDatabaseConnection().getPreparedStatement();
        myResultSet = getDatabaseConnection().getResultSet();
    }

    /**
     * Authenticates entry when username and password are present in user table
     * @param username is the username
     * @param password is the password
     * @return true if valid username/password; false otherwise
     */
    public boolean authenticateUsernameAndPassword(String username, String password) {
        String stm = "SELECT username, pw FROM users WHERE username = '" + 
                    username + Keys.APOSTROPHE;
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet  = myPreparedStatement.executeQuery();
            if (myResultSet.next()) {
                if (myResultSet.getString(Keys.USER_PASSWORD_COLUMN_INDEX).equals(password)) {
                    return true;
                }
            }

        }
        catch (SQLException e) {
            writeErrorMessage("Error authenticating in UserTable.java @ Line 58");
        }

        return false;
    }
    
    /**
     * Returns true if usernameExists, false otherwise
     * @param username is the username
     */
    public boolean usernameExists(String username) {
        String stm = "SELECT username FROM users WHERE username='" + username + Keys.APOSTROPHE;
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myResultSet  = myPreparedStatement.executeQuery();
            if (myResultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error determining if username exists in UserTable.java @ Line 81");
        }
        return false;
    }

    /**
     * Adds a user to user table based on information
     * @param user is the username
     * @param pw is the password
     * @param firstname is firstname
     * @param lastname is lastname
     * @param dob is date of birth
     */
    public boolean createUser(String user, String pw, String firstname, 
                        String lastname, String dob) {
        if (usernameExists(user)) {
            return false;
        }
        String stm = "INSERT INTO users(username, pw, firstname, lastname, DOB) " +
                "VALUES(?, ?, ?, ?, ?)";
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.setString(Keys.USER_USERNAME_COLUMN_INDEX, user);
            myPreparedStatement.setString(Keys.USER_PASSWORD_COLUMN_INDEX, pw);
            myPreparedStatement.setString(Keys.USER_FIRSTNAME_COLUMN_INDEX, firstname);
            myPreparedStatement.setString(Keys.USER_LASTNAME_COLUMN_INDEX, lastname);
            myPreparedStatement.setString(Keys.USER_DOB_COLUMN_INDEX, dob);
            myPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            writeErrorMessage("Error creating user in UserTable.java @ Line 109");
        }
        return true;
    }
    
    /**
     * Adds a user to user table based on information when avatar is present
     * @param user is the username
     * @param pw is the password
     * @param firstname is firstname
     * @param lastname is lastname
     * @param dob is date of birth
     * @param filepath is the filepath
     */
    public boolean createUser(String user, String pw, String firstname, 
                              String lastname, String dob, String filepath) {
        if (usernameExists(user)) {
            return false;
        }
        createUser(user, pw, firstname, lastname, dob);
        updateAvatar(user, filepath);
        return true;
    }
    
    /**
     * Returns the userid when given the username
     * @param username is the username
     */
    public String retrieveUserId(String username) {
        return retrieveEntryString(Keys.USER_TABLE_NAME, Keys.USER_USERNAME_COLUMN_FIELD, username, 
                                   Keys.USER_USERNAME_COLUMN_INDEX);
    }
    
    /**
     * Given a username, retrieves the date of birth
     * @param username is the user
     */
    public String retrieveDOB(String username) {
        return retrieveEntryString(Keys.USER_TABLE_NAME, Keys.USER_USERNAME_COLUMN_FIELD, 
                                   username, Keys.USER_DOB_COLUMN_INDEX);
    }
    
    /**
     * Given a username, retrieves avatar filepath
     * @param username is the username
     */
    public String retrieveAvatar(String username) {
        return retrieveEntryString(Keys.USER_TABLE_NAME, Keys.USER_USERNAME_COLUMN_FIELD, 
                                   username, Keys.USER_AVATAR_COLUMN_INDEX);
    }

    /**
     * Given a username, deletes that user from userTable
     * @param username is user
     */
    public void deleteUser(String username) {
        String stm = "DELETE FROM " + Keys.USER_TABLE_NAME + " WHERE " + 
                Keys.USER_USERNAME_COLUMN_FIELD + Keys.EQUALS + username + Keys.APOSTROPHE;
        executeStatement(stm); 

    }
    
    /**
     * Given a username and a filepath, updates avatar
     * @param user is username
     * @param filepath is the filepath of the avatar
     */
    public void updateAvatar(String user, String filepath) {
        String userid = retrieveUserId(user);
        String stm = "UPDATE " + Keys.USER_TABLE_NAME + " SET " + Keys.USER_AVATAR_COLUMN_FIELD + 
                Keys.EQUALS + "filepath" + "' WHERE " + Keys.USER_USERID_COLUMN_FIELD + 
                Keys.EQUALS + userid + Keys.APOSTROPHE;   
        executeStatement(stm);
    }
    
    /**
     * Returns a list of all the games
     */
    public List<String> retrieveUsernames() {
        myResultSet = selectAllRecordsFromTable(Keys.USER_TABLE_NAME);
        List<String> myUsernames = new ArrayList<String>();
        try {
            while (myResultSet.next()) {
                myUsernames.add(myResultSet.getString(Keys.USER_USERNAME_COLUMN_INDEX));
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving usernames in UserTable.java @ Line 197");
        }
        return myUsernames; 
    }
    
    
    /**
     * Prints entire table
     */
    public void printEntireTable () {
        myResultSet = selectAllRecordsFromTable(Keys.USER_TABLE_NAME);
        try {
            while (myResultSet.next()) {
                System.out.print(myResultSet.getString(Keys.USER_USERNAME_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.USER_PASSWORD_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.USER_FIRSTNAME_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.USER_LASTNAME_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.USER_DOB_COLUMN_INDEX) + Keys.SEPARATOR);
                System.out.print(myResultSet.getString(Keys.USER_AVATAR_COLUMN_INDEX) + 
                                 Keys.SEPARATOR);
                System.out.println(myResultSet.getString(Keys.USER_USERID_COLUMN_INDEX));
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error printing entire table in UserTable.java @ Line 216");
        }
    }

}