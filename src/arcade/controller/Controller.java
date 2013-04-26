
package arcade.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import twitter4j.TwitterException;
import util.FilePathFormatter;
import arcade.database.Database;
import arcade.exceptions.CorruptedDatabaseException;
import arcade.exceptions.InvalidPaymentException;
import arcade.exceptions.LoginErrorException;
import arcade.exceptions.UsernameTakenException;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import arcade.games.GameData;
import arcade.games.GameInfo;
import arcade.games.UserGameData;
import arcade.model.payment.PaymentManager;
import arcade.model.social.TwitterConnection;
import arcade.view.MainView;
import arcade.view.forms.LoginView;


public class Controller implements ArcadeInteraction {
    // Locations
    private static final String RESOURCE_LOCATION = "arcade.resources.";
    private static final String PAYMENT_MANAGER_LOCATION = "arcade.model.payment.";
    
    private static final String SOURCE = "src";
	private static final String PACKAGE_LOCATION = "arcade/resources";
	private static final String FILENAME = "userPurchases";
	private static final String EXTENSION = ".properties";
	private static final String FILEWRITER_LOCATION = SOURCE + "/" + PACKAGE_LOCATION + "/" + FILENAME + "1" + EXTENSION;
	private static final String TMP_LOCATION = SOURCE + "/" + PACKAGE_LOCATION + "/" + FILENAME + "2" + EXTENSION;
	private static final String PURCHASE_RB_LOCATION = RESOURCE_LOCATION + FILENAME + "1";

    

    // Messages
    public static final String DEFAULT_LOGIN_MESSAGE = "";

    // Status parameterrs
    private static final String NO_USER_IMAGE = "";

    // View
    private LoginView myLoginView;
    private MainView myView;

    // Resource
    private ResourceBundle myResources;
    private ResourceBundle userPurchaseHistory;

    // Models
    private Database myDb;
    private Map<String, GameInfo> myGameInfos;
    private Collection<GameInfo> myPurchasedGames;
    private PaymentManager myPaymentManager;
    private TwitterConnection myTwitter;
    private FilePathFormatter myFilePathFormatter = new FilePathFormatter();

    private Game myCurrentGame;
    private GameInfo myCurrentGameInfo;

    public GameInfo getCurrentGameInfo () {
        return myCurrentGameInfo;
    }


    // Parameters
    private String myLanguage;
    private String myCurrentUser;
    private UserGameData myCurrentUserGameData;
    private GameData myCurrentGameData;

    public Controller (String language) {
        myLanguage = language;
        myResources = ResourceBundle.getBundle(RESOURCE_LOCATION + language);
        userPurchaseHistory = ResourceBundle.getBundle(PURCHASE_RB_LOCATION);
        myDb = new Database();
        myGameInfos = new HashMap<String, GameInfo>();
        myPurchasedGames = new ArrayList<GameInfo>();
        myLoginView = new LoginView(this, myResources);
    }

    /*
     * public Controller(ResourceBundle rb, String language) {
     * myResources = rb;
     * myLanguage = language;
     * }
     */

    public void setLoginView (LoginView login) {
        myLoginView = login;
    }

    public void authenticate (String username, String password) throws LoginErrorException {
        if (loginCreteriaNotSatisfied(username, password)) { throw new LoginErrorException(); }
        myLoginView.dispose();
        myCurrentUser = username;
        organizeSnapshots();
        myView = new MainView(this, myResources);
    }

    private boolean loginCreteriaNotSatisfied (String username, String password) {
        return !myDb.authenticateUsernameAndPassword(username, password);
    }

    private void organizeSnapshots () {
        List<String> gameNames = myDb.retrieveListOfGames();
        for (String name : gameNames) {
            try {
                addNewGameInfoToList(name);
            }
            catch (MissingResourceException e) {
                continue;
            }

        }
    }

    private void addNewGameInfoToList (String gameName) throws MissingResourceException {
        GameInfo g = new GameInfo(myDb, gameName);
        myGameInfos.put(g.getName(), g);
    }

    /**
     * Create a new user profile by entering user-specific information. This
     * information is eventually stored in the database.
     * 
     * @throws UsernameTakenException
     * @throws IOException 
     */
    public void createNewUserProfile (UserSpecificData data) throws UsernameTakenException, IOException {
        if (usernameInDatabase(data.getUsername())) throw new UsernameTakenException();
        myDb.createUser(data);
        try {
        	String username = data.getUsername();
        	String password = data.getPassword();
            authenticate(username, password);
            if (!loginCreteriaNotSatisfied(username, password)) {
            	System.out.println("111");
            	recordUserInPurchaseResourceFile(username);;
            }
        }
        catch (LoginErrorException e) {
            throw new CorruptedDatabaseException();
        }
    }
    
    // Personalized Game-Center methods (trying without database)
    
    private void closeWriters(FileWriter fw, BufferedWriter bw) throws IOException {
		bw.close();
		fw.close();
	}
	
	private void closeReaders(FileReader fr, BufferedReader br) throws IOException {
		br.close();
		fr.close();
	}
	
	private void writeLine(BufferedWriter bw, String line) throws IOException {
		bw.write(line);
		bw.newLine();
	}
	
	private File createFileForLocation(String loc) {
		File file = new File(loc);
		if (file.exists()) {
			try{ 
				file.createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
    
	
	private void copyFile(File origin, File target) throws IOException {
		copyFile(origin, target, null);
	}
	
	private void copyFile (File origin, File target, String newUser) throws IOException {
		FileReader fr = new FileReader(origin);
		BufferedReader br = new BufferedReader(fr);
		
		FileWriter fw = new FileWriter(target);
		BufferedWriter bw = new BufferedWriter(fw);
		
		String line;
		while ((line = br.readLine())!=null){
			writeLine(bw, line);
		}
		if (newUser != null) writeLine(bw, newUser);
		closeWriters(fw, bw);
		closeReaders(fr, br);
	}
	
	
	private String formatUsernameForResourceFile(String username) {
		return username + " =";
	}
    
	
	public void recordUserInPurchaseResourceFile(String username) throws IOException {
		File file1 = createFileForLocation(FILEWRITER_LOCATION); 
		File file2 = createFileForLocation(TMP_LOCATION);
		copyFile(file2,file1,formatUsernameForResourceFile(username));
		copyFile(file1,file2);
	}
	
	public void recordGamePurchaseHistory(String username, String gameName) throws IOException{
		File file1 = createFileForLocation(FILEWRITER_LOCATION); 
		File file2 = createFileForLocation(TMP_LOCATION);
		replaceLine(file2,file1,username,gameName);
		copyFile(file1,file2);
	}
	
	private void replaceLine(File temp, File target, String username, String gameName) throws IOException{
		FileReader fr = new FileReader(temp);
		BufferedReader br = new BufferedReader(fr);
		
		FileWriter fw = new FileWriter(target);
		BufferedWriter bw = new BufferedWriter(fw);
		
		String line;
		while ((line = br.readLine())!=null){
			if (line.contains(username)) writeLine(bw, line + " "+ gameName);
			else writeLine(bw,line);
		}
		closeWriters(fw, bw);
		closeReaders(fr, br);
	}
	
	public String[] getGamesForUser(String username){
		String games = userPurchaseHistory.getString(username);
		return games.split(" ");
	}
		
		

    /*
     * public void createNewUserProfile(String username, String pw, String firstname, String
     * lastname, String dateOfBirth)
     * throws UsernameTakenException {
     * createNewUserProfile(username, pw, firstname, lastname, dateOfBirth, NO_USER_IMAGE);
     * }
     * 
     * public void createNewUserProfile(String username, String pw, String firstname, String
     * lastname, String dateOfBirth,
     * String imageFilePath) throws UsernameTakenException {
     * if (usernameInDatabase(username)) {
     * throw new UsernameTakenException();
     * }
     * myDb.createUser(username, pw, firstname, lastname, dateOfBirth, imageFilePath);
     * try {
     * authenticate(username, pw);
     * } catch (LoginErrorException e) {
     * // this can't happen because just added to db.
     * throw new CorruptedDatabaseException();
     * }
     * }
     */

    private boolean usernameInDatabase (String username) {
        return myDb.usernameExists(username);
    }

    public void deleteUser (String username) {
        myDb.deleteUser(username);
    }

    /**
     * This should be called after a developer enters the information about his
     * / her game. The method will add the game entry to the database and create
     * a new GameInfo to display in the gamecenter.
     * 
     * This sanitizes all the input so we guarantee that all names an genres are
     * lowercase on the backend.
     * 
     * @param gameName
     * @param genre
     * @throws IOException 
     */
    /*
     * public void publish(GameSpecificData data){
     * myDb.createGame(data);
     * addNewGameInfoToList(data.getName());
     * }
     */

    public void publish (String name,
                         String genre,
                         String author,
                         double price,
                         String extendsGame,
                         String extendsMultiplayerGame,
                         int ageRating,
                         boolean singlePlayer,
                         boolean multiplayer,
                         String thumbnailPath,
                         String adScreenPath,
                         String description) throws IOException {
        // print
//        System.out.println(extendsGame);
//        System.out.println(extendsMultiplayerGame);

        // Put info in DB
        myDb.createGame(name.toLowerCase(), genre.toLowerCase(), author, price,
                        myFilePathFormatter.formatClassFilePath(extendsGame),
                        myFilePathFormatter.formatClassFilePath(extendsMultiplayerGame), ageRating,
                        singlePlayer, multiplayer, thumbnailPath, adScreenPath,
                        description);

        // Put game info in map
        addNewGameInfoToList(name);
    }

    
    
    /**
     * First creates the appropriate PaymentManager for the transactionType if
     * the transactionType is Duke, then the DukePaymentManager is created.
     * 
     * Then tries to complete the transaction with the paymentInfo. If the
     * transaction is unsuccessful, the InvalidPaymentExecption is thrown.
     * 
     * @param transactionType
     * @param paymentInfo
     * @throws InvalidPaymentException
     */
    public void performTransaction (GameInfo game, String transactionType, String[] paymentInfo)
                                                                                                throws InvalidPaymentException {
        try {
            Class<?> paymentManagerClass =
                    Class.forName(PAYMENT_MANAGER_LOCATION + transactionType);
            myPaymentManager = (PaymentManager) paymentManagerClass.newInstance();
            recordGamePurchaseHistory(myCurrentUser, game.getName());
        }
        catch (ClassNotFoundException e) {
            throw new InvalidPaymentException();
        }
        catch (InstantiationException e) {
            throw new InvalidPaymentException();
        }
        catch (IllegalAccessException e) {
            throw new InvalidPaymentException();
        } catch (IOException e) {
			throw new InvalidPaymentException();
		}

        myPaymentManager.doTransaction(paymentInfo);
        myPurchasedGames.add(game);
        
    }

    
    
    
    /**
     * Sets up a new twitter request to get access to a user's account.
     * Returns a URL that a user can access to authorize.
     * 
     * @return
     * @throws TwitterException
     */
    public String setUpTwitterRequest () throws TwitterException {
        myTwitter = new TwitterConnection();
        return myTwitter.newRequest();
    }

    
    
    /**
     * After the user authorizes the twitter request, s/he will have a pin.
     * This gets access using the provided pin, and sends a tweet containing
     * text.
     * 
     * @param pin
     * @param text
     * @throws TwitterException
     */
    public void sendTweet (String pin, String text) throws TwitterException {
        myTwitter.sendTweet(pin, text);
    }

    
    
    /**
     * Rate a specific game, store in user-game database
     */
    public void commentAndRateGame (String comment, double rating, String gameName) {
        myDb.insertCommentAndRating(myCurrentUser, gameName, comment, rating);
    }

    
    
    public void playGame (GameInfo gameinfo) {
        myCurrentGameInfo = gameinfo;
        myCurrentGame = gameinfo.getGame(this);
        myCurrentUserGameData = getUserGameData(myCurrentGame);
        myCurrentGame.run();
    }


    private void print(Object print){
        System.out.println(print);
    }
    /**
     * Get the list of games from the database.
     * 
     * @return
     */
    public Collection<GameInfo> getGameList () {
        return myGameInfos.values();
    }

    
    /**
     * The collection of all games purchased by the user.  Unfortunately, only
     * persists through current session, not saved in database.
     * @return
     */
    public Collection<GameInfo> getGamesPurchased() {
        return myPurchasedGames;
    }
    
    
    
    /**
     * GameDetailPanel must call this method to get game-specific info.
     * 
     * @param gameName: name of the chosen game (String)
     * @return
     */
    public GameInfo getGameDetail (String gameName) {
        return myGameInfos.get(gameName);
    }

    /**
     * UserProfile must call this method to retrieve User-specific information
     */

    

    /**
     * TODO: Must add user-game specific detail
     * 
     * @param user, game (whatever that identifies the user and the game)
     * @return
     */
    @Override
    public UserGameData getUserGameData (Game game) {
        if (myCurrentUserGameData == null ){
            //myCurrentUserGameData =  myCurrentGameInfo.getUserGameData(game , myCurrentUser);
            myCurrentUserGameData = myCurrentGame.generateNewProfile();
        }
        return myCurrentUserGameData;
    }

    @Override
    public void killGame () {
        int score = getCurrentUserGameData().getLastScore();
        myDb.addNewHighScore(myCurrentUser, myCurrentGameInfo.getName(),  score);
        myView.showEndGameView(score);
        //myDb.storeUserGameData(getCurrentGame(), myCurrentUser, getCurrentUserGameData());
        myCurrentGame = null;
        myCurrentGameInfo = null;
       
    }

    
    private UserGameData getCurrentUserGameData(){
        //return myCurrentGameInfo.getUserGameData(myCurrentGame, myCurrentUser);
        return myCurrentUserGameData;
    }
    

    @Override
    public String getCurrentGame () {
        return myCurrentGameInfo.getName();
    }

    
    @Override
    public GameData getGameData () {
        if (myCurrentGameData == null ){
            myCurrentGameData =  myCurrentGameInfo.getGameData(myCurrentGame );
        }
        return myCurrentGameData;
    }
}

