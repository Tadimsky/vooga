package vooga.fighter.controller;

import java.util.ArrayList;
import java.util.List;

import arcade.games.Score;
import arcade.games.UserGameData;

/**
 * Information about the broader game state. includes game mode, selected map and characters.
 * This gets passed between controllers, and modes use this information to construct themselves
 * 
 * @author Matt Parides
 * @author Jerry Li
 * @author Jack Matteucci
 * 
 */
public class GameInfo extends UserGameData{

    private String myGameMode;
    private String myMapName;
    private int myMapCount;
    private String myModeName;
    private List<String> myCharacters;
    private Integer myNumCharacters;
    private List<Integer> myScores;
    private List<String> myMapsPlayed;
    private List<String> myMapNames;
    private Score myHighScores;
    private List<Integer> myWinners;
    private GameLoopInfo myGameLoopInfo;
    /**
     * Constructs game info with a list of map names.
     * Creates new lists of character names and scores
     * and winners
     * @param mapNames
     */
    public GameInfo (List<String> mapNames) {
        myCharacters = new ArrayList<String>();
        myMapsPlayed = new ArrayList<String>();
        myScores = new ArrayList<Integer>();
        myGameMode = "Fighting Game";
        myMapNames = mapNames;
        myWinners = new ArrayList<Integer>();
    }

    /**
     * Constructs games with given list of mapnames, characters, and
     * a string map. 
     * @param mapNames
     * @param characters
     * @param map
     */
    public GameInfo (List<String> mapNames, List<String> characters, String map) {
        this(mapNames);
        myCharacters = characters;
        myMapName = map;
    }
    
    /**
     * Sets the game loop info
     * @param info gameloopinfo
     */
    public void setGameLoopInfo(GameLoopInfo info) {
        myGameLoopInfo = info;
    }
    
    /**
     * Sets the mode name
     * @param name the mode name
     */
    public void setModeName(String name) {
        myModeName = name;
    }
   
    /**
     * Set list of scores
     * @param scores    list of scores
     */
    public void setScores(List<Integer> scores) {
        myScores = scores;
    }

    /**
     * returns score at index
     * @param index     int index
     * @return
     */
    public int getScore(int index) {
        return myScores.get(index);
    }
    
    /**
     * Reset all information
     */
    public void reset(){
        myCharacters.clear();
        myMapName = null;
        myNumCharacters = 0;
        myModeName = null;
    }

    /**
     * sets score at index
     * @param index     index
     * @param score     score
     */
    public void setScore(int index, int score) {
        myScores.set(index, score);
    }

    /**
     * Return list of (double) scores
     */
    public List<Double> getScores() {
        List<Double> myArcadeScores = new ArrayList<Double>();
        for(Integer i : myScores){
            myArcadeScores.add((double) i);
        }
        return myArcadeScores;
    }

    /**
     * Return list of integer scores
     * @return
     */
    public List<Integer> getIntScores() {
        return myScores;
    }


    /**
     * Returns gamemode name
     * @return
     */
    public String getGameMode () {
        return myGameMode;
    }

    /**
     * Set game mode name
     * @param gameMode
     */
    public void setGameMode (String gameMode) {
        myGameMode = gameMode;
    }

    /**
     * Return map name
     * @return
     */
    public String getMapName () {
        return myMapName;
    }

   /**
    * Set map name
    * @param map name
    */
    public void setMapName (String map) {
        myMapName = map;
    }

    /**
     * Return list of characters
     * @return
     */
    public List<String> getCharacters () {
        return myCharacters;
    }
    
    /**
     * Add character to list
     * @param character
     */
    public void addCharacters(String character) {
        myCharacters.add(character);
    }
    
    /**
     * set character at index
     * @param index     index
     * @param character string character
     */
    public void setCharacter (int index, String character) {
        myCharacters.set(index, character);
    }
    
    /**
     * set list of characters
     * @param characters
     */
    public void setCharacters (List<String> characters) {
        myCharacters = characters;
    }

    /**
     * Return the number of players
     * @return
     */
    public int getNumCharacters () {
        return myNumCharacters;
    }

    /**
     * Set the number of characters
     * @param numCharacters
     */
    public void setNumCharacters (int numCharacters) {
        myNumCharacters = numCharacters;
    }

    
    /**
     * Return number of maps
     * @return
     */
    public int getMapCount(){
        return myMapNames.size();
    }
    
    /**
     * Get the maps played
     * @return
     */
    public List<String> getMapsPlayed(){
        return myMapsPlayed;
    }
    
    /**
     * Return list of map names
     * @return
     */
    public List<String> getMapNames(){
        return myMapNames;
    }
    
    public void setHighScores(Score highscores){
    	myHighScores = highscores;
    }
    
    public Score getHighScores(){
    	return myHighScores;
    }
    
    /**
     * Get list of winners (by player id)
     * @return
     */
    public List<Integer> getWinners() {
        return myWinners;
    }
    
    /**
     * set winners 
     * @param winners
     */
    public void setWinners(List<Integer> winners) {
        myWinners = winners;
    }
    
    /**
     * Add a winner
     * @param winner
     */
    public void addWinners(int winner) {
        myWinners.add(winner);
    }

}
