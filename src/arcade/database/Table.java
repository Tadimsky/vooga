package arcade.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates superclass table with variables other subclasses need
 * Establishes connection to database
 * @author Natalia Carvalho
 */
public abstract class Table {
    
    /**
     * where keyword
     */
    /**
     * apostrophe keyword
     */
    
    private PreparedStatement myPreparedStatement;
    private Connection myConnection;
    private ResultSet myResultSet;
    private DatabaseConnection myDatabaseConnection;
    
    /**
     * Establishes database connection and instantiates variables other subclasses 
     * need to access table
     */
    public Table() {
        myDatabaseConnection = new DatabaseConnection();
        myPreparedStatement = myDatabaseConnection.getPreparedStatement();
        myConnection = myDatabaseConnection.getConnection();
        myResultSet = myDatabaseConnection.getResultSet();
    }  
    
    /**
     * Closes connection to database
     */
    public void closeConnection() {
        myDatabaseConnection.closeConnection();
    }
    
    /**
     * Returns Database Connection
     */
    public DatabaseConnection getDatabaseConnection() {
        return myDatabaseConnection;
    }
    
    /**
     * Selects all records from a table given a tablename
     * @param tableName is name of table
     */
    public ResultSet selectAllRecordsFromTable(String tableName) {
        System.out.println();
        try {
            executeQuery(Keys.SELECT_FROM + tableName);
        }
        catch (SQLException e) {
            writeErrorMessage("Error selecting all records from table in Table.java @ Line 82");
        }
        return myResultSet;
    }
    
    /**
     * Given parameter and a columnIndex, returns that entire row entry for a table
     * @param tableName is name of table
     * @param columnField is the name of the column
     * @param parameter is parameter we are interested in
     * @param columnIndex is the index that we want the information for
     */
    public int retrieveEntryInt(String tableName, String columnField, String parameter, 
                                int columnIndex) {
        String stm = Keys.SELECT_FROM + tableName + Keys.WHERE_KEYWORD + columnField + 
                Keys.EQUALS + parameter + Keys.APOSTROPHE;
        int entry = 0;
        try {
            executeQuery(stm);
            if (myResultSet.next()) {
                entry = myResultSet.getInt(columnIndex);
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving entry integer in Table.java @ Line 103");
        }
        return entry;
    }
    
    /**
     * Given parameter and a columnIndex, returns that entire row entry for a table
     * @param tableName is name of table
     * @param columnField is the name of the column
     * @param parameter is parameter we are interested in
     * @param columnIndex is the index that we want the information for
     */
    public String retrieveEntryString(String tableName, String columnField, 
                                      String parameter, int columnIndex) {
        String stm = Keys.SELECT_FROM + tableName + Keys.WHERE_KEYWORD + columnField + 
                Keys.EQUALS + parameter + Keys.APOSTROPHE;
        String entry = "";
        try {
            executeQuery(stm);
            if (myResultSet.next()) {
                entry = myResultSet.getString(columnIndex);
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving entry string in Table.java @ Line 127");
        }
        return entry;
    }

    public ResultSet executeQuery (String stm) throws SQLException {
        myPreparedStatement = myConnection.prepareStatement(stm);
        myResultSet = myPreparedStatement.executeQuery();
        return myResultSet;
    }
    
    /**
     * Given parameter and a columnIndex, returns that entire row entry for a table
     * @param tableName is name of table
     * @param columnField is the name of the column
     * @param parameter is parameter we are interested in
     * @param columnIndex is the index that we want the information for
     */
    public boolean retrieveEntryBoolean(String tableName, String columnField, 
                                        String parameter, int columnIndex) {
        String stm = Keys.SELECT_FROM + tableName + Keys.WHERE_KEYWORD + columnField + 
                Keys.EQUALS + parameter + Keys.APOSTROPHE;
        boolean entry = false;
        try {
            executeQuery(stm);
            if (myResultSet.next()) {
                entry = myResultSet.getBoolean(columnIndex);
            }
        }
        catch (SQLException e) {
            writeErrorMessage("Error retrieving entry boolean in Table.java @ Line 156");
        }
        return entry;
    }
    
    /**
     * Executes a statement for a table
     * @param stm is the statement
     */
    public void executeStatement (String stm) {
        try {
            myPreparedStatement = myConnection.prepareStatement(stm);
            myPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Writes an error to log
     * @param errorMessage is error
     */
    public void writeErrorMessage(String errorMessage) {
        myDatabaseConnection.logError(errorMessage);
    }

}
