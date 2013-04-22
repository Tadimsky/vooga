package vooga.rts.networking.client.GUI;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import vooga.rts.networking.communications.LobbyInfo;


/**
 * Adapter between the ServerBrowserView's table and the data from the server.
 * TODO connect with actual model
 * 
 * @author David Winegar
 * 
 */
public class ServerBrowserTableAdapter extends AbstractTableModel {
    private static final long serialVersionUID = -4753703508648678535L;

    private String[] myColumnNames = { "Server Name",
                                      "Map",
                                      "Players" };

    private LobbyInfo[] myLobbies = new LobbyInfo[1];

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

    @Override
    public Object getValueAt (int arg0, int arg1) {
        if(myLobbies[arg0] == null) {
            return null;
        }
        switch(arg1) {
            case 0: return myLobbies[arg0].getLobbyName();
            case 1: return myLobbies[arg0].getMapName();
            case 2: return myLobbies[arg0].getMaxPlayers() + " / " + myLobbies[arg0].getCurrentPlayers();
            default: return null;
        }
    }

    public void addLobbies (LobbyInfo[] lobbies) {
        myLobbies = lobbies;
    }

}
