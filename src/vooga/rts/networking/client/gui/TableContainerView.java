package vooga.rts.networking.client.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


/**
 * View that encapsulates a JTable inside a JScrollPane. In this project, it is used as the view for
 * the server browser.
 * 
 * @author David Winegar
 * 
 */
public class TableContainerView extends JPanel {

    private static final long serialVersionUID = 5915312995153741263L;
    private JTable myTable;

    /**
     * Initialize view.
     * 
     * @param adapter abstract table model
     */
    public TableContainerView (AbstractTableModel adapter) {
        myTable = new JTable(adapter);
        myTable.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(myTable);
        add(pane);
    }

    /**
     * Returns if the user has selected a row.
     * 
     * @return true if row selected
     */
    public boolean hasSelectedRow () {
        return myTable.getSelectedRow() != -1;
    }

    /**
     * Returns the selected row.
     * 
     * @return row selected
     */
    public int getSelectedRow () {
        return myTable.getSelectedRow();
    }

}
