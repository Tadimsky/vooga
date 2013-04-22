package vooga.rts.networking.client.GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;


/**
 * View for the server browser.
 * 
 * @author David Winegar
 * 
 */
public class ServerBrowserView extends JPanel {

    private static final long serialVersionUID = 5915312995153741263L;
    JTable myTable;

    /**
     * Initialize view.
     * 
     * @param adapter adapter for server browser table
     * @param gameName name of the game
     */
    public ServerBrowserView (TableModel adapter) {
        myTable = new JTable(adapter);
        myTable.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(myTable);
        add(pane);
    }

}
