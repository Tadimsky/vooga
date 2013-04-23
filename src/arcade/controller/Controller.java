package arcade.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import arcade.database.Database;
import arcade.exceptions.CorruptedDatabaseException;
import arcade.exceptions.InvalidPaymentException;
import arcade.exceptions.LoginErrorException;
import arcade.exceptions.UsernameTakenException;
import arcade.games.ArcadeInteraction;
import arcade.games.Game;
import arcade.games.GameData;
import arcade.games.GameInfo;
import arcade.games.Score;
import arcade.games.MultiplayerGame;
import arcade.games.User;
import arcade.games.UserGameData;
import arcade.model.payment.DukePaymentManager;
import arcade.model.payment.PaymentManager;
import arcade.view.MainView;
import arcade.view.forms.LoginView;

public class Controller implements ArcadeInteraction {
		// Locations
		private static final String RESOURCE_LOCATION = "arcade.resources.";
		private static final String PAYMENT_MANAGER_LOCATION = "arcade.model.payment.";
		
		// Messages
		public static final String DEFAULT_LOGIN_MESSAGE = "";
		private static final String LOGIN_FAILURE_MESSAGE = "The username or password you entered is incorrect";
		private static final String REGISTER_FAILURE_MESSAGE = "That username is already taken.";
		
		// Status parameterrs
		private static final String NO_USER_IMAGE = "";
		
		// View
		private LoginView myLoginView;
		
		// Resource
		private ResourceBundle myResources;
		
		// Models
		private Database myDb;
		private Map<String, GameInfo> myGameInfos;
		private PaymentManager myPaymentManager;
		
		private Game myCurrentGame;
		private MultiplayerGame myCurrentMultiplayerGame;
		
		// Parameters
		private String myLanguage;
		private String myUser;
	
		
	public Controller(String language) {
		myLanguage = language;
		myResources = ResourceBundle.getBundle(RESOURCE_LOCATION + language);
		myDb = new Database();
		myGameInfos = new HashMap<String, GameInfo>();
		myLoginView = new LoginView(this, myResources);
	}

	
	public Controller(ResourceBundle rb, String language) {
		myResources = rb;
		myLanguage = language;
	}

	
	public void setLoginView(LoginView login) {
		myLoginView = login;
	}
	
	
	public void authenticate(String username, String password) throws LoginErrorException {
		if (loginCreteriaNotSatisfied(username,password)) {
			throw new LoginErrorException();
		}
		myLoginView.dispose();
		organizeSnapshots();
		new MainView(this, myResources);
	}
	
	
	private boolean loginCreteriaNotSatisfied(String username, String password) {
		return !myDb.authenticateUsernameAndPassword(username, password);
	}
	
	
	private void organizeSnapshots() {
		List<String> gameNames = myDb.retrieveListOfGames();
		for (String name : gameNames) {
			try {
				addNewGameInfoToList(name);
			} catch (MissingResourceException e) {
				continue;
			}

		}
	}
	
	
	private void addNewGameInfoToList(String gameName) throws MissingResourceException {
		GameInfo g = new GameInfo(myDb, gameName);
		myGameInfos.put(g.getName(), g);
	}
	

	
	
	/**
	 * Create a new user profile by entering user-specific information. This
	 * information is eventually stored in the database.
	 * 
	 * @throws UsernameTakenException
	 */
	public void createNewUserProfile(String username, String pw, String firstname, String lastname, String dateOfBirth) 
			throws UsernameTakenException {
		createNewUserProfile(username, pw, firstname, lastname, dateOfBirth, NO_USER_IMAGE);
	}

	public void createNewUserProfile(String username, String pw, String firstname, String lastname, String dateOfBirth,
			String imageFilePath) throws UsernameTakenException {
		if (usernameInDatabase(username)) {
			throw new UsernameTakenException();
		}
		myDb.createUser(username, pw, firstname, lastname, dateOfBirth, imageFilePath);
		try {
			authenticate(username, pw);
		} catch (LoginErrorException e) {
			// this can't happen because just added to db.
			throw new CorruptedDatabaseException();
		}
	}
	
	private boolean usernameInDatabase(String username){
		return myDb.usernameExists(username);
	}

	public void deleteUser(String username) {
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
	 */
	public void publish(GameSpecificData data){
		myDb.createGame(data);
		addNewGameInfoToList(data.getName());
	}
	
	/*
	public void publish(String name, String genre, String author, double price, String extendsGame, String extendsMultiplayerGame, int ageRating,
			boolean singlePlayer, boolean multiplayer, String thumbnailPath, String adScreenPath, String description) {
		// print
		System.out.println(extendsGame);
		System.out.println(extendsMultiplayerGame);
		
		// Put info in DB
		myDb.createGame(name.toLowerCase(), genre.toLowerCase(), author, price,
				formatClassFilePath(extendsGame),
				formatClassFilePath(extendsMultiplayerGame), ageRating,
				singlePlayer, multiplayer, thumbnailPath, adScreenPath,
				description);
		
		// Put game info in map
		addNewGameInfoToList(name);
	}
	*/
	
	/*
	private String formatClassFilePath(String path) {
		if (path == null)
			return null;
		// split on file extension
		String[] split = path.split("."); // take everything before file extension and after src to get java relative filepath.
		List<String> list = Arrays.asList(split);
		if (list.contains("src")) {
			// this means you got the absolute file path, so you need to get java relative file path (i.e. after src/ )
			path = split[0].split("src")[1];
		}
		split = path.split("/");
		String ret = "";
		for (String str : split) {
			ret += str;
			ret += ".";
		}
		// remove the hanging period
		ret = ret.substring(0, ret.length() - 1);
		System.out.println("this is ret" + ret);
		return ret;
	}
	 */
	
	

	

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
	public void performTransaction(GameInfo game, String transactionType, String[] paymentInfo) throws InvalidPaymentException {
		try {
			Class<?> paymentManagerClass = Class.forName(PAYMENT_MANAGER_LOCATION + transactionType);
			myPaymentManager = (PaymentManager) paymentManagerClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new InvalidPaymentException();
		} catch (InstantiationException e) {
			throw new InvalidPaymentException();
		} catch (IllegalAccessException e) {
			throw new InvalidPaymentException();
		}

		myPaymentManager.doTransaction(paymentInfo);
		// TODO: write code here for moving game from Store to GameCenter
	}

	/**
	 * Rate a specific game, store in user-game database
	 */
	public void rateGame(double rating, String gameName) {
		myDb.updateRating(myUser, gameName, rating);
	}

	public void playGame(GameInfo gameinfo) {
		myCurrentGame = gameinfo.getGame(this);
		myCurrentGame.run();
	}

	public void playMultiplayerGame(GameInfo gameinfo) {
		MultiplayerGame game = gameinfo.getMultiplayerGame(this);
		game.run();
	}

	/**
	 * TODO: Get the list of games from the database.
	 * 
	 * @return
	 */
	public Collection<GameInfo> getGameList() {
		return myGameInfos.values();
	}

	

	/**
	 * GameDetailPanel must call this method to get game-specific info.
	 * 
	 * @param gameName
	 *            : name of the chosen game (String)
	 * @return
	 */
	public GameInfo getGameDetail(String gameName) {
		return myGameInfos.get(gameName);
	}

	/**
	 * TODO: Must add user-game specific detail
	 * 
	 * @param user
	 *            ,game (whatever that identifies the user and the game)
	 * @return
	 */
	public UserGameData getUserGameData(String user, String game) {
		// Query database to get info specific to the user and the game (e.g.
		// scores)
		return new UserGameData();
	}

	@Override
	public User getUser() {
		// TODO get the user's avatar, figure out how we are implementing user
		// info for games
		return null;
	}

	public double getAverageRating(String gameName) {
		return myDb.getAverageRating(gameName);
	}

	@Override
	public Score getHighScores(int n) {
		// TODO I wish I understood how we are planning on implementing high
		// scores . . .
		// nonetheless: do database stuff here
		return null;
	}

	@Override
	public void killGame() {
		// save the usergamedata and game data if applicable, and return to
		// detail screen

	}

	@Override
	public UserGameData getUserGameData(String gameName) {
		UserGameData ugd = myDb.getUserGameData(gameName, myUser);
		if (ugd == null) {
			// use reflection to find the game class and call the generate user
			// profile method
		}
		return ugd;
	}

	@Override
	public GameData getGameData(String gameName) {
		GameData gd = myDb.getGameData(gameName);
		if (gd == null) {
			// use reflection to find the game class and call the generate game
			// method
		}
		return gd;
	}
}
