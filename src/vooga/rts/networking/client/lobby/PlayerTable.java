package vooga.rts.networking.client.lobby;

import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import vooga.rts.player.RTSPlayer;


public class PlayerTable extends JScrollPane {

   

    /**
     * 
     */
    private static final long serialVersionUID = -1671059744815042087L;
    public JTable myTable;

    public PlayerTable (String[] factions, List<RTSPlayer> players, String[] columnTitles) {
//        this.add(playerTable(players, factions, columnTitles));
        myTable = playerTable(players, factions, columnTitles);
    }
    
    private JTable playerTable(List<RTSPlayer> players, String [] factions, String [] columnTitles) {
        //TODO: compose these two, perhaps into a map.
        final List<TableCellEditor> editors = new ArrayList<TableCellEditor>();
        final List<TableCellEditor> teamsEditors = new ArrayList<TableCellEditor>();
        Object[][] startingTable = new Object[players.size()][3];
        
        int count = 0;
      //TODO: add handling for null.  also only want to have an editor when is either a bot or the current player.
        for (RTSPlayer player : players) {
//          if (player.equals(currentPlayer))| {
              JComboBox<String> factionSelector = factionComboBox(player, factions);
              DefaultCellEditor editor = new DefaultCellEditor( factionSelector);
              editors.add(editor);
              
              
              JComboBox<Integer> teamSelector = teamComboBox(player, new Integer[] {1,3,4});
              DefaultCellEditor edit = new DefaultCellEditor(teamSelector);
              teamsEditors.add(edit);
              
              
//          }
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
            private static final long serialVersionUID = -4151956216349241397L;

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
        
        return table;
    }
    
    private JComboBox<String> factionComboBox(final RTSPlayer player, String[] choices) {
        final JComboBox<String> factionSelector = new JComboBox<String>(choices);
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
    private JComboBox<Integer> teamComboBox(final RTSPlayer player, Integer[] teams) {
        final JComboBox<Integer> teamSelector = new JComboBox<Integer>(teams);
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
//            player.setTeam(selectedTeam);
            //TODO: do we want the player to actually maintain state of its team?
            //probably not, will just add into our implentation.
        }
        
    }
    
    
    

    
    
    
    
   
}