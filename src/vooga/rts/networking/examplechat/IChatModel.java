package vooga.rts.networking.examplechat;

/**
 * Interface between the chat model and the chat panel view.
 * 
 * @author David Winegar
 * 
 */
public interface IChatModel {

    /**
     * Called when a message is entered by the user.
     * 
     * @param message entered
     */
    public void messageEntered (String message);

    /**
     * Creates server browser model and sets it as the view.
     * 
     * @param name username
     */
    public void switchToServerBrowser (String userName);
}
