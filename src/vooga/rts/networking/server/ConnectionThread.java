package vooga.rts.networking.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import vooga.rts.networking.communications.Message;
import vooga.rts.networking.communications.clientmessages.InitialConnectionMessage;


/**
 * Represents a server-side connection between the client and the server.
 * 
 * @author David Winegar
 * @author Henrique Morales
 * 
 */
public class ConnectionThread extends Thread {
    private Socket mySocket;
    private ObjectInputStream myInput;
    private ObjectOutputStream myOutput;
    private int myID;
    private String myUserName;
    private String myGameName;
    private IMessageReceiver myMessageServer;
    private boolean myConnectionActive = false;

    /**
     * Represents a thread that communicates to a client
     * 
     * @param socket socket used for establishing the connection
     * @param id number of connection
     */
    ConnectionThread (Socket socket, IMessageReceiver server, int id) {
        mySocket = socket;
        myMessageServer = server;
        myID = id;

        try {
            myInput = new ObjectInputStream(mySocket.getInputStream());
            myOutput = new ObjectOutputStream(mySocket.getOutputStream());
        }
        catch (IOException e) {
            // TODO add logger
            e.printStackTrace();
        }
    }

    /**
     * Switches which IMessageReceiver to send messages to.
     * 
     * @param server to switch to
     */
    public void switchMessageServer (IMessageReceiver server) {
        myMessageServer = server;
    }

    /**
     * Keeps listening for messages and adds to the server's message queue
     */
    @Override
    public void run () {
        myConnectionActive = true;
        try {
            Object obj = myInput.readObject();

            // Checks to see if first object passed is initial connection message
            if (obj instanceof InitialConnectionMessage) {
                sendToMessageServer(obj);
            }
            else {
                // first object is not initial connection message
                myConnectionActive = false;
                myMessageServer.removeConnection(this);
                return;
            }

            while (myConnectionActive) {
                obj = myInput.readObject();
                if (obj instanceof Message) {
                    sendToMessageServer(obj);
                }
            }
        }
        catch (IOException e) {
            // TODO add logger
            e.printStackTrace();
            close();
        }
        catch (ClassNotFoundException e) {
            // TODO add logger
            e.printStackTrace();
            close();
        }
    }

    /**
     * sends object to message server
     */
    private void sendToMessageServer (Object obj) {
        Message message = (Message) obj;
        myMessageServer.receiveMessageFromClient(message, this);
    }

    /**
     * Closes streams and socket of this thread
     * TODO catch exceptions
     */
    public void close () {
        myConnectionActive = false;
        try {
            if (myOutput != null) {
                myOutput.close();
            }
            if (myInput != null) {
                myInput.close();
            }
            if (mySocket != null) {
                mySocket.close();
            }
        }
        catch (IOException e) {
            // TODO logger
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a Message object to the client
     * 
     * @param m Message object to be sent
     */
    public void sendMessage (Message m) {
        if (!mySocket.isConnected()) {
            close();
        }
        try {
            myOutput.writeObject(m);
        }
        catch (IOException e) {
            // TODO add logger
            e.printStackTrace();

        }
    }

    /**
     * gets the ID for the connection thread
     * @return id
     */
    public int getID () {
        return myID;
    }

    /**
     * Gets the user name for the connection thread.
     * @return username
     */
    public String getUserName () {
        return myUserName;
    }

    /**
     * Gets the name of the game the user is playing.
     * @return game name
     */
    public String getGameName () {
        return myGameName;
    }

    /**
     * Sets the user name - can only be set once.
     * 
     * @param userName name to set
     */
    public void setUserName (String userName) {
        if (myUserName == null) {
            myUserName = userName;
        }
    }

    /**
     * Sets the game name - can only be set once.
     * 
     * @param gameName name to set
     */
    public void setGameName (String gameName) {
        if (myGameName == null) {
            myGameName = gameName;
        }
    }

}
