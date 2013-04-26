package arcade.controller;

import java.util.Arrays;
import java.util.List;

public class GameSpecificData {
	/**
	 * This class is used to hold game-specific data entered by developer. When the publisher
	 * presses the "publish" button, The view stores all the game-specific information in this class
	 * and passes to controller.
	 * 
	 * Honestly, I do not think this class is necessary. However, our TA Jimmy was concerned that the 
	 * publish() method in the controller is receiving to many input variables and recommended that
	 * we use a class to encapsulate these variables.
	 * 
	 * If this class remains in final implementation, it may have to be moved to a different package.
	 * 
	 * @author Eunsu (Joe) Ryu - jesryu
	 */
	
	private String gameName;
	private String gameGenre;
	private String gameAuthor;
	private double gamePrice;
	private String extendsGamePath;
	private String extendsMultiplayerGamePath;
	private int gameAgeRating;
	private boolean gameIsSinglePlayer;
	private boolean gameIsMultiPlayer;
	private String gameThumbnailFilePath;
	private String gameAdScreenPath;
	private String gameDescription;
	
	public GameSpecificData(String name, String genre, String author, double price, String extendsGame, String extendsMultiplayerGame, int ageRating,
			boolean singleplayer, boolean multiplayer, String thumbnailPath, String adScreenPath, String description){
		gameName = name.toLowerCase();
		gameGenre = genre.toLowerCase();
		gameAuthor = formatFilePath(author);
		gamePrice = price;
		extendsGamePath = formatFilePath(extendsGame);
		extendsMultiplayerGamePath = formatFilePath(extendsMultiplayerGame);
		gameAgeRating = ageRating;
		gameIsSinglePlayer = singleplayer;
		gameIsMultiPlayer = multiplayer;
		gameThumbnailFilePath = thumbnailPath;
		gameAdScreenPath = adScreenPath;
		gameDescription = description;
	}
	
	public String getName(){
		return gameName;
	}
	
	public String getGenre() {
		return gameGenre;
		
	}
	
	public String getAuthor () {
		return gameAuthor;
	}

	public String getExtendsGame(){
		return extendsGamePath;
	}
	
	public String getExtendsMultiplyaer(){
		return extendsMultiplayerGamePath;
	}

	public double getPrice() {
		return gamePrice;
	}
	
	public int getAgeRating(){
		return gameAgeRating;
	}
	
	public boolean isSinglePlayer(){
		return gameIsSinglePlayer;
	}
	
	public boolean isMultiplayer(){
		return gameIsMultiPlayer;
	}
	
	public String getThumbnailPath(){
		return gameThumbnailFilePath;
	}
	
	public String getAdScreenPath(){
		return gameAdScreenPath;
	}
	
	public String getDescription(){
		return gameDescription;
	}
	
	
	
//	private String formatFilePath(String path){
//		String start = substringStartingWith(path, "src");
//		String output = "";
//		for (int i = 0; i < start.length(); i++) {
//			char c = start.charAt(i);
//			if (c == '.') break;
//			if (c == '/') output += ".";
//			else output += c;
//		}
//		return output;
//	}
//	
	/**
	 * Tedious Java string manipulation to change something like:
	 * C://blah/blah/blah/src/games/rts/ageOfEmpires/game.java to
	 * games.rts.ageOfEmpires.game so replace slashes with periods and remove
	 * the file extension
	 */
	private String formatFilePath(String path) {
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
	
	
	private String substringStartingWith(String prefix, String target){
		return target.substring(target.indexOf(prefix));
	}
}
