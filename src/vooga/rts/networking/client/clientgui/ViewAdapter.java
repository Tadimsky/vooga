package vooga.rts.networking.client.clientgui;

import vooga.rts.networking.client.ClientModel;

/**
 * Provides an abstract view adapter for the client GUI.
 * @author Sean Wareham
 * @author Henrique Moraes
 *
 */
public abstract class ViewAdapter {
    private ViewContainerPanel myContainerPanel;
    private ClientModel myModel;

    /**
     * Instantiates the class with the given model and game name.
     * @param model model given
     * @param gameName name of game
     */
    public ViewAdapter (ClientModel model, String gameName) {
        myModel = model;
        myContainerPanel = new ViewContainerPanel(gameName);
    }

    /**
     * returns view.
     * @return the view used by all networking functions
     */
    public ViewContainerPanel getView () {
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

    /**
     * returns model
     * @return the model
     */
    protected ClientModel getMyModel () {
        return myModel;
    }

}
