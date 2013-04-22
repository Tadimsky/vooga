package vooga.rts.networking.client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;


/**
 * View for the Lobby.
 * 
 * @author Sean Wareham
 * 
 */
public class LobbyView extends JPanel {

    private static final long serialVersionUID = 5915312995153741263L;
    private Map<Integer, TableCellEditor> myTableCellEditors;
    private LobbyTableAdapter myAdapter;

    JTable myTable;

    /**
     * Initialize view.
     * 
     * @param adapter adapter for Lobby's player table
     */
    public LobbyView (LobbyTableAdapter adapter) {
        myAdapter = adapter;
        testCellEditors();
//       uncomment when ready to accept parameters buildCellEditors(players,factions, teams, locations);
        //TODO: NOTE: all parameters MUST be String[].  cannot have int[] and string[]. must be homogenously string[]  for buildCellEditors
        myTable = new JTable(myAdapter) {
            /**
             * 
             */
            private static final long serialVersionUID = -1745108660965050209L;

            @Override
            public TableCellEditor getCellEditor (int row, int column) {
                TableCellEditor editor = myTableCellEditors.get(column);
                if (editor == null) {
                    return super.getCellEditor(row, column);
                }
                return editor;

            }
        };
        myTable.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(myTable);
        add(pane);
    }
    
    private void testCellEditors() {
        String[] players = new String[] {};
        String[] factions = new String[] {"Terran", "Protoss", "Zerg"};
        String[] teams = new String[] {"1", "2",  "3"};
        String[] locations = new String[] {"String1" , "String 2"};
        buildCellEditors(players,factions, teams, locations);
    }
/**
 * method to take in arbitrarily large set of choices and create editors for each choice / column 
 (each choice set represents the options for that column)
 * empty string will result in that column being uneditable
 * @param args
 */
    private void buildCellEditors(String[] ... args) {
        myTableCellEditors = new HashMap<Integer, TableCellEditor> ();
       
        for (int i =0 ; i<args.length; i++) {
            String[] choices = args[i];
            if (choices.length ==0){
                myAdapter.setColumnUnEditable(i);
                continue;
            }
            final JComboBox choiceSelector = new JComboBox(choices);
            choiceSelector.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed (ActionEvent e) {
//                    choiceSelector.getSelectedItem();//TODO add some behavior here,
                    //give state to player, change display ...
                }
            });
            TableCellEditor editor = new DefaultCellEditor(choiceSelector);
            myTableCellEditors.put(i, editor);
        }
    }
}
