package vooga.rts.networking.client;

public class GameMap {
    private String myName;
    private int myMaxPlayers;
    private int myPlayersPerTeam;
    private String myFilePath;

    public GameMap (String name, String pathofMapFile, int maxPlayers, int playersPerTeam) {
        myName = name;
        myFilePath = pathofMapFile;
        myMaxPlayers = maxPlayers;
        myPlayersPerTeam = playersPerTeam;
    }
}
