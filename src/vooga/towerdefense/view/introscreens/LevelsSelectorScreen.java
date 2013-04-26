package vooga.towerdefense.view.introscreens;

import java.awt.BorderLayout;
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
import java.util.Map;
import javax.swing.JButton;
import util.Location;
import util.Pixmap;
import vooga.towerdefense.view.TDView;


/**
 * This view screen enables the player to select the mode they want to play in.
 * There is easy, medium and hard modes.
 * 
 * @author Leonard K. Ng'eno
 * 
 */
public class LevelsSelectorScreen extends SelectScreen {
    private static final String CHECKED_IMAGE = "checked.gif";
    private static final long serialVersionUID = 1L;
    private static final Dimension SIZE = new Dimension(150, 150);
    private MouseAdapter myMouseListener;
    private Pixmap myEasyLevel;
    private Pixmap myMediumLevel;
    private Pixmap myHardLevel;
    private Map<Pixmap, Rectangle> myLevelsImages;
    private JButton myNextScreenButton;
    private boolean myLevelSelected = false;
    private String myPrevName = "";
    
    public LevelsSelectorScreen(Dimension size, TDView view) {
        super(size, view);
        setInputListener();
        add(makeNextScreenButton(), BorderLayout.SOUTH);
        myLevelsImages = new HashMap<Pixmap, Rectangle>();
        initLevelsImages();
        addMouseListener(myMouseListener);
    }
    
    // TODO Placeholder!! Need to fix how images are created
    private void initLevelsImages () {
        myEasyLevel = new Pixmap("easy.gif");
        myMediumLevel = new Pixmap("medium.gif");
        myHardLevel = new Pixmap("hard.gif");
        
        myLevelsImages.put(myEasyLevel, new Rectangle(new Point(100, 200), SIZE));
        myLevelsImages.put(myMediumLevel, new Rectangle(new Point(300, 200), SIZE));
        myLevelsImages.put(myHardLevel, new Rectangle(new Point(500, 200), SIZE));
    }
    
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        displayImages((Graphics2D) pen);
    }
    
    @Override
    public void displayImages (Graphics2D pen) {
        for (Map.Entry<Pixmap, Rectangle> entry : myLevelsImages.entrySet()) {
            entry.getKey().paint(pen,
                                 new Location(entry.getValue().getCenterX(), entry.getValue()
                                         .getCenterY()), SIZE);
        }
    }
    
    private JButton makeNextScreenButton () {
        myNextScreenButton = new JButton("NEXT");
        myNextScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (myLevelSelected == true) {
                    getView().createGUI();
                }
            }
        });
        return myNextScreenButton;
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
            for (Map.Entry<Pixmap, Rectangle> entry1 : myLevelsImages.entrySet()) {
                if (entry1.getKey().getFilePath().equals(CHECKED_IMAGE)) {
                    entry1.getKey().setImage(myPrevName);
                    repaint();
                }
            }
        }
        
        for (Map.Entry<Pixmap, Rectangle> entry : myLevelsImages.entrySet()) {
            if (entry.getValue().contains(point)) {
                myLevelSelected = true;
                selectedImage(entry.getKey());
            }
        }
    }
    
    private void selectedImage (Pixmap myImage) {
        myPrevName = myImage.getFilePath();
        myImage.setImage(CHECKED_IMAGE);
        repaint();
    }
}
