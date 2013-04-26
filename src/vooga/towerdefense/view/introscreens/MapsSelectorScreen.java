package vooga.towerdefense.view.introscreens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

import util.Location;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.model.MapLoader;
import util.Pixmap;
import vooga.towerdefense.view.TDView;


/**
 * 
 * This screen enables the player to select the type of map that they would
 * like to play in.
 * 
 * @author Leonard K. Ng'eno
 * 
 */
public class MapsSelectorScreen extends SelectScreen {
    private static final String CHECKED_IMAGE = "checked.gif";
    private static final long serialVersionUID = 1L;
    private static final Dimension SIZE = new Dimension(200, 200);
    private static final String PATH = "/vooga/towerdefense/view/precreatedmaps/tdmap1.xml";
    private MouseAdapter myMouseListener;
    private Pixmap myMap1;
    private Pixmap myMap2;
    private Pixmap myMap3;
    private Pixmap myMap4;
    private Map<Pixmap, Rectangle> myMapImages;
    private JButton myNextScreenButton;
    private boolean myMapSelected = false;
    private String myPrevName = "";
    private MapLoader myMapLoader;

    public MapsSelectorScreen (Dimension size, TDView view) {
        super(size, view);
        setInputListener();
        myMapImages = new HashMap<Pixmap, Rectangle>();
        initMapImages();
        addMouseListener(myMouseListener);
        add(makeNextScreenButton(), BorderLayout.SOUTH);
    }

    private Component makeNextScreenButton () {
        myNextScreenButton = new JButton("NEXT");
        myNextScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (myMapSelected == true) {
                    getView().showLevelDifficultyChoicesScreen();
                }
            }
        });
        return myNextScreenButton;
    }

    // TODO placeholder! Creation of Pixmaps needs to be cleaned up
    private void initMapImages () {
        myMapLoader = new MapLoader(PATH);
        List<GameMap> myGameMaps = myMapLoader.loadMaps();
        System.out.println("mapps: " + myGameMaps);
        
        myMap1 = new Pixmap("map1.gif");
        myMap2 = new Pixmap("map2.gif");
        myMap3 = new Pixmap("map3.gif");
        myMap4 = new Pixmap("map4.gif");

        myMapImages.put(myMap1, new Rectangle(new Point(100, 50), SIZE));
        myMapImages.put(myMap2, new Rectangle(new Point(450, 50), SIZE));
        myMapImages.put(myMap3, new Rectangle(new Point(100, 350), SIZE));
        myMapImages.put(myMap4, new Rectangle(new Point(450, 350), SIZE));

    }

    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        displayImages((Graphics2D) pen);
        myNextScreenButton.setVisible(true);
    }

    private void setInputListener () {
        myMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                checkPositionClicked(e.getPoint());
            }
        };
    }

    @Override
    public void checkPositionClicked (Point point) {

        if (!myPrevName.isEmpty()) {
            for (Map.Entry<Pixmap, Rectangle> entry1 : myMapImages.entrySet()) {
                if (entry1.getKey().getFilePath().equals(CHECKED_IMAGE)) {
                    entry1.getKey().setImage(myPrevName);
                    repaint();
                }
            }
        }

        for (Map.Entry<Pixmap, Rectangle> entry : myMapImages.entrySet()) {
            if (entry.getValue().contains(point)) {
                myMapSelected = true;
                selectedImage(entry.getKey());
            }
        }
    }

    private void selectedImage (Pixmap myImage) {
        myPrevName = myImage.getFilePath();
        myImage.setImage(CHECKED_IMAGE);
        repaint();
    }

    @Override
    public void displayImages (Graphics2D pen) {
        for (Map.Entry<Pixmap, Rectangle> entry : myMapImages.entrySet()) {
            entry.getKey().paint(pen,
                                 new Location(entry.getValue().getCenterX(), entry.getValue()
                                         .getCenterY()), SIZE);
        }
    }

}
