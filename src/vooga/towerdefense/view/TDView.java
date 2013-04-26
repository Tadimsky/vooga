package vooga.towerdefense.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vooga.towerdefense.controller.Controller;
import vooga.towerdefense.view.gamescreens.GameElementInformationScreen;
import vooga.towerdefense.view.gamescreens.GameStatsScreen;
import vooga.towerdefense.view.gamescreens.MapScreen;
import vooga.towerdefense.view.gamescreens.ShopScreen;
import vooga.towerdefense.view.introscreens.LevelsSelectorScreen;
import vooga.towerdefense.view.introscreens.MapsSelectorScreen;
import vooga.towerdefense.view.introscreens.SplashScreen;


/**
 * Container that holds the screens for the view.
 * 
 * @author Leonard K. Ng'eno
 * @author Angelica Schwartz
 * 
 */
public class TDView {

    private static final String TITLE_KEYWORD = "GameTitle";
    private static final String NEXT_BUTTON_KEYWORD = "NextButtonName";
//    private static final Dimension SIZE = new Dimension(1100, 800);
//    private static final Dimension MAP_WINDOW_SIZE = new Dimension(800, 600);
//    private static final Dimension EAST_WINDOW_SIZE = new Dimension(200, 600);
//    private static final Dimension SOUTH_WINDOW_SIZE = new Dimension(1000, 200);
//    private static final Dimension SPLASH_SCREEN_SIZE = new Dimension(800, 600);
//    private static final Dimension STATS_WINDOW_SIZE = new Dimension(200, 300);
//    private static final Dimension INFO_WINDOW_SIZE = new Dimension(200, 300);
//    private static final Dimension SHOP_WINDOW_SIZE = new Dimension(600, 100);
//    private static final Dimension WAVE_WINDOW_SIZE = new Dimension(200, 100);
    private JFrame myFrame;
    private JPanel myPanel;
    private Dimension mySize;
    private JButton myNextScreenButton;
    private Controller myController;

    private SplashScreen mySplashScreen;
    private MapsSelectorScreen myMapSelector;
    private LevelsSelectorScreen myLevelSelector;
    
    private MapScreen myMapScreen;
    private ShopScreen myShopScreen;
    private GameStatsScreen myPlayerStatsScreen;
    private GameElementInformationScreen myInformationScreen;

    /**
     * constructor.
     * 
     * @param controller
     */
    public TDView (Controller controller) {
        myController = controller;
        createGUI();
    }
    
    /**
     * sets the size of this view.
     * @param size is a Dimension
     */
    public void setSize (Dimension size) {
        mySize = size;
        myFrame.setPreferredSize(mySize);
    }

    /**
     * creates this view.
     */
    public void createGUI () {
        myFrame = new JFrame(myController.getStringFromResources(TITLE_KEYWORD));
        myPanel = new JPanel();
        myFrame.setContentPane(myPanel);
        myFrame.setPreferredSize(mySize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mySplashScreen = new SplashScreen(mySize);
        addScreen(mySplashScreen, BorderLayout.CENTER);
        myFrame.getContentPane().add(nextScreenButton());
        
//        myMapScreen = new MapScreen(MAP_WINDOW_SIZE, myController);
//
//        myStatsWindow = new InformationScreen("Stats", STATS_WINDOW_SIZE);
//        myInformationWindow =
//                new GameElementInformationScreen("Info", INFO_WINDOW_SIZE, myController);
//        Map<JPanel, String> screens = new HashMap<JPanel, String>();
//        screens.put(myStatsWindow, BorderLayout.NORTH);
//        screens.put(myInformationWindow, BorderLayout.SOUTH);
//        myEastWindow = new MultipleScreenPanel(EAST_WINDOW_SIZE, screens);
//
//        myShopScreen = new ShopScreen(SHOP_WINDOW_SIZE, myController);
//        myNextWaveScreen = new NextWaveScreen(WAVE_WINDOW_SIZE, myController);
//        screens = new HashMap<JPanel, String>();
//        screens.put(myShopScreen, BorderLayout.CENTER);
//        screens.put(myNextWaveScreen, BorderLayout.EAST);
//        mySouthWindow = new MultipleScreenPanel(SOUTH_WINDOW_SIZE, screens);
//
        }
//
//    /**
//     * adds the new screens to the view.
//     */
//    public void assembleScreens () {
//        myFrame.remove(myLevelSelector);
//
//        myFrame.getContentPane().add(myMapScreen, BorderLayout.CENTER);
//        myFrame.getContentPane().add(myEastWindow, BorderLayout.EAST);
//        myFrame.getContentPane().add(mySouthWindow, BorderLayout.SOUTH);
//
//        myFrame.pack();
//        myFrame.setVisible(true);
//
//    }

    /**
     * Removes the splash screen and displays the map choices.
     */
    public void showMapChoicesScreen () {
        myFrame.remove(mySplashScreen);
        myNextScreenButton.setVisible(false);
        myMapSelector = new MapsSelectorScreen(mySize, this);
        addScreen(myMapSelector, BorderLayout.CENTER);
    }

    /**
     * Removes the map choices screen and shows the level
     * difficulty choices screen.
     */
    public void showLevelDifficultyChoicesScreen () {
        myFrame.remove(myMapSelector);
        myLevelSelector = new LevelsSelectorScreen(mySize, this);
        addScreen(myLevelSelector, BorderLayout.CENTER);
    }

    /**
     * adds the parameter screen to the view.
     * 
     * @param screen
     */
    public void addScreen (JPanel screen, String location) {
        myFrame.getContentPane().add(screen, location);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    /**
     * helper method that creates the button to move to the
     * next screen.
     * 
     * @return the JButton
     */
    private Component nextScreenButton () {
        myNextScreenButton = new JButton(myController.getStringFromResources(NEXT_BUTTON_KEYWORD));
        myNextScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                showMapChoicesScreen();
            }
        });
        return myNextScreenButton;
    }
    
    /**
     * sets the map screen appropriately.
     * @param mapScreen
     */
    public void setMapScreen(MapScreen mapScreen) {
        myMapScreen = mapScreen;
    }

    /**
     * gets the map screen for this view.
     * 
     * @return the MapScreen
     */
    public MapScreen getMapScreen () {
        return myMapScreen;
    }

    /**
     * Gets the information screen that displays the
     * GameElement information for this view.
     * 
     * @return the GameElementInformationScreen
     */
    public GameElementInformationScreen getGameElementInfoScreen () {
        return myInformationScreen;
    }
    
    /**
     * Sets the information screen that displays the
     * game element information for this view.
     * 
     * @param the GameElementInformationScreen
     */
    public void setGameElementInfoScreen (GameElementInformationScreen infoScreen) {
        myInformationScreen = infoScreen;
    }

    /**
     * Gets the information screen that displays the
     * player information for this view.
     * 
     * @return the InformationScreen
     */
    public GameStatsScreen getPlayerInfoScreen () {
        return myPlayerStatsScreen;
    }
    
    /**
     * Sets the information screen that displays the
     * player information for this view.
     * 
     * @param the InformationScreen
     */
    public void setPlayerInfoScreen (GameStatsScreen infoScreen) {
        myPlayerStatsScreen = infoScreen;
    }
    
    /**
     * sets the shop screen appropriately.
     * @param shopScreen
     */
    public void setShopScreen(ShopScreen shopScreen) {
        myShopScreen = shopScreen;
    }

    /**
     * Gets the shop screen for this view.
     * 
     * @return the ShopScreen
     */
    public ShopScreen getShopScreen () {
        return myShopScreen;
    }
}
