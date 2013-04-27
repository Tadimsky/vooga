package vooga.rts.networking.client.clientgui;

import java.util.List;
import javax.swing.JPanel;
import vooga.rts.networking.client.ClientModel;
import vooga.rts.networking.communications.LobbyInfo;

public abstract class ViewAdapter {
    protected ViewContainerPanel myContainerPanel;
    protected ClientModel myModel;
    
    public ViewAdapter(IModel model, String gameName) {
        myModel = (ClientModel) model;
        myContainerPanel = new ViewContainerPanel(gameName);
    }
    
    /**
     * 
     * @return the view used by all networking functions
     */
    public JPanel getView () {
        return myContainerPanel;
    }

    /**
     * Displays a window to the user with a specific message
     * @param title Title of the window
     * @param message String to inform the user
     */
    public void alertClient (String title, String message) {
        myContainerPanel.showMessageDialog(title, message);
    }

}
