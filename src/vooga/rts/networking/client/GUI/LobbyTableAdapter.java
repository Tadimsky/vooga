package vooga.rts.networking.client.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;


/**
 * Adapter between the LobbyView's table and the data from the server.
 * TODO connect with actual model
 * 
 * @author Sean Wareham
 * 
 */
public class LobbyTableAdapter extends AbstractTableModel {
    private static final long serialVersionUID = -4753703508648678535L;
    private String[] myColumnNames = { "Player",
                                      "Team",
                                      "Faction",
                                      "Location"};
    private Object[][] myDataTable = { { 3, "hi", 3,4 }, { 4, 4, 4,5 }, { 5, 5, 5,4 } };
    String[] myFactions;
    List<Integer> myUnEditableColumns = new ArrayList<Integer>();

    public LobbyTableAdapter () {
        
    }

    @Override
    public int getColumnCount () {
        return myColumnNames.length;
    }

    @Override
    public int getRowCount () {
        return myDataTable.length;
    }

    @Override
    public String getColumnName (int col) {
        return myColumnNames[col];
    }

    @Override
    public Object getValueAt (int arg0, int arg1) {
        return myDataTable[arg0][arg1];
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        if (myUnEditableColumns.contains(column)){
            return false;
        }
        //TODO: add method that checks if row is the another players row, returns false if yes
        return true;
    }

//    TableCellEditor getCellEditor (int row, int column) {
//        TableCellEditor edit =
//                new DefaultCellEditor(new JComboBox(new String[] { "chicke 1", "choice2" }));
//        return edit;
//    }

    public void setData (Object[][] data) {
        myDataTable = data;

    }
    /**
     * Method to make a column uneditable.
     * @param column
     */
    public void setColumnUnEditable(int column) {
        myUnEditableColumns.add(column);
    }

}
