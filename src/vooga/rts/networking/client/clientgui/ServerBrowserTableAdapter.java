package vooga.rts.networking.client.clientgui;

import javax.swing.table.AbstractTableModel;
import vooga.rts.networking.NetworkBundle;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Adapter between the ServerBrowserView's table and the data from the server. Overrides
 * AbstractTableModel and serves as a adapter between the LobbyInfo[] coming in and the table it
 * serves.
 * 
 * @author David Winegar
 * 
 */
public class ServerBrowserTableAdapter extends AbstractTableModel {
    private static final long serialVersionUID = -4753703508648678535L;

    private String[] myColumnNames = { NetworkBundle.getString("ServerName"),
                                      NetworkBundle.getString("Map"),
                                      NetworkBundle.getString("Players") };

    // initialize with no lobbies
    private LobbyInfo[] myLobbies = new LobbyInfo[0];

    @Override
    public int getColumnCount () {
        return myColumnNames.length;
    }

    @Override
    public int getRowCount () {
        return myLobbies.length;
    }

    @Override
    public String getColumnName (int col) {
        return myColumnNames[col];
    }

    /**
     * Gets the lobby ID of the row passed in.
     * 
     * @param row to get
     * @return lobbyID
     */
    public int getidOfRow (int row) {
        return myLobbies[row].getId();
    }

    /**
     * Use the lobbies array as a way of getting the value at a point, with the first column being
     * lobby names, second being map names, and third being current players and max players.
     * @param arg0 row
     * @param arg1 column
     */
    @Override
    public Object getValueAt (int arg0, int arg1) {
        if (myLobbies[arg0] == null) return null;
        switch (arg1) {
            case 0:
                return myLobbies[arg0].getLobbyName();
            case 1:
                return myLobbies[arg0].getMapName();
            case 2:
                return myLobbies[arg0].getNumberOfPlayers() + " / " +
                       myLobbies[arg0].getMaxPlayers();
            default:
                return null;
        }
    }

    /**
     * Changes the underlying model to the new LobbyInfo[] passed in.
     * 
     * @param lobbies to display
     */
    public void changeLobbies (LobbyInfo[] lobbies) {
        myLobbies = lobbies;
    }

}
