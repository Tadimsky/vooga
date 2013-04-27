package vooga.rts.networking.client.clientgui;

import javax.swing.JPanel;
import vooga.rts.networking.client.ClientModel;


public abstract class ViewAdapter {
    protected ViewContainerPanel myContainerPanel;
    protected ClientModel myModel;

    public ViewAdapter (IModel model, String gameName) {
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
     * 
     * @param title Title of the window
     * @param message String to inform the user
     */
    public void alertClient (String title, String message) {
        myContainerPanel.showMessageDialog(title, message);
    }
    
    public void destroyPanel () {
        myContainerPanel.removeAll();
    }

}
