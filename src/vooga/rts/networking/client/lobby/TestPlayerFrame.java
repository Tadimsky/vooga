package vooga.rts.networking.client.lobby;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
//import vooga.rts.player.RTSPlayer;
//TODO: still need to make handling for when is an empty player, or not the user. currently alows for change which we likely do not want
//

public class TestPlayerFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    List<TableCellEditor> editors = new ArrayList<TableCellEditor>();
    List<TableCellEditor> teamsEditors = new ArrayList<TableCellEditor>();
    Map<Integer, List<TableCellEditor>> myColEditors;
    public TestPlayerFrame()
    {
        
        List<RTSPlayer> myPlayers = new ArrayList<RTSPlayer>();
        String[] columnTitles = {"Player","Team" ,"Faction"};
        String currentPlayer = "john";
        
        
        myPlayers.add(new RTSPlayer("john"));
        myPlayers.add(new RTSPlayer("tom"));
        myPlayers.add(null);
        myPlayers.add(new RTSPlayer("bob"));
        myPlayers.add(null);
        // Create the editors to be used for each row
        String[] factions = {"Toss", "terran" , "zerg"};
        Object[][] startingTable = new Object [myPlayers.size()][3];
        
        int count = 0;
        //TODO: add handling for null.  also only want to have an editor when is either a bot or the current player.
        for (RTSPlayer player : myPlayers) {
//            if (player.equals(currentPlayer))| {
                JComboBox factionSelector = factionComboBox(player, factions);
                DefaultCellEditor editor = new DefaultCellEditor( factionSelector);
                editors.add(editor);
                
                
                JComboBox teamSelector = teamComboBox(player, new Integer[] {1,3,4});
                DefaultCellEditor edit = new DefaultCellEditor(teamSelector);
                teamsEditors.add(edit);
//            }
            if (player == null){
                startingTable[count][0]= "empty";
                editor.stopCellEditing();
            }else{
                startingTable[count][0]= player.toString();
                startingTable[count][1] =factions[0];
                startingTable[count][2] = 2;
                
            }
            count++;
        }

        DefaultTableModel model = new DefaultTableModel(startingTable, columnTitles);
        JTable table = new JTable(model)
        {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            //  Determine editor to be used by row
            public TableCellEditor getCellEditor(int row, int column)
            {
                int modelColumn = convertColumnIndexToModel( column );

                if (modelColumn == 1 )
                    return editors.get(row);
                if (modelColumn ==2)
                    return teamsEditors.get(row);
                else
                    return super.getCellEditor(row, column);
            }
        };
//        table.setdefa
        table.getColumn("Faction").setCellEditor(null);
        
        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );
    }
    //TODO: add ability to change what the available table editors are.  maybe make
    //an inner class extension of table editor that takes paramater player as well
    //to make my life easier
    public void updateAvailableTeams(Integer choice) {
        
    }

    public static void main(String[] args)
    {
        TestPlayerFrame frame = new TestPlayerFrame();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }
    private JComboBox factionComboBox(final RTSPlayer player, String[] choices) {
        final JComboBox factionSelector = new JComboBox(choices);
        factionSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                updateFaction(player, (String)factionSelector.getSelectedItem());
            }
        });
        return factionSelector;
    }
    
    
    private void updateFaction(RTSPlayer player, String faction) {
        if (player != null){
            player.setFaction(faction);
        }
    }
    /**
     * Method to create a selector for teams
     * TODO: currently there is no support for if teams are "full"  is more like age of empires
     * where team size is irrelevant.  If we decide this path is O.K. we will somehow need
     * to enforce that there is at least 2 distinct teams (in theory, i suppose we dont and it could be a civ building type thing..)
     * TODO: change so that it reads in available teams from the LobbyInfo. this way
     * can call this method again within an update method and only the available teams
     * will appear
     * @param player
     * @param teams
     * @return
     */
    private JComboBox teamComboBox(final RTSPlayer player, Integer[] teams) {
        final JComboBox teamSelector = new JComboBox(teams);
        teamSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeam(player, (Integer) teamSelector.getSelectedItem());
            }

            
        });
        return teamSelector;
    }
    
    private void updateTeam (RTSPlayer player, Integer selectedTeam) {
        if (player != null) {
            player.setTeam(selectedTeam);
        }
        
    }
    
    
    class RTSPlayer {
        private String myName;
        RTSPlayer( String name) {
            myName = name;
        }
        public void setTeam (Integer selectedTeam) {
            // TODO Auto-generated method stub
            
        }
        public void setFaction(String faction){
            
        }
        @Override
        public String toString() {
            if (myName ==null) return "Empty";
            return myName;
        }
    }
}