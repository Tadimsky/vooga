package vooga.rts.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 
 * @author Jonathan Schmidt
 * 
 */
public class Window {

    private Canvas myCanvas;
    private JFrame myFrame;

    public static Dimension SCREEN_SIZE;

    private boolean myFullscreen = false;

    private GraphicsDevice myGraphics;

    private DisplayMode myPrevDispMode;

    public Window () {
        myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setUndecorated(true);
        myFrame.setIgnoreRepaint(true);

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run () {
                    // TODO Auto-generated method stub
                    myFrame.setVisible(true);
                    myFrame.createBufferStrategy(2);
                    myCanvas = new Canvas(myFrame.getBufferStrategy());
                    myFrame.add(myCanvas);
                }
            });
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setFullscreen (boolean fullscreen) {
        myGraphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (!myGraphics.isFullScreenSupported()) {
            return;
        }
        DisplayMode displayMode = myGraphics.getDisplayMode();
        myPrevDispMode = myGraphics.getDisplayMode();

        if (myFullscreen == fullscreen) {
            return;
        }
        myFullscreen = fullscreen;
        if (myFullscreen) {
            myFrame.setVisible(false);
            myFrame.dispose();
            myFrame.setUndecorated(true);
            try {
                myGraphics.setFullScreenWindow(myFrame);
            }
            finally {
                myGraphics.setDisplayMode(displayMode);
                myFrame.setResizable(false);
                myFrame.setAlwaysOnTop(false);
                myFrame.setVisible(true);
            }
        }
        else {

            myFrame.setVisible(false);
            myFrame.dispose();
            myFrame.setUndecorated(false);
            try {
                if (myGraphics.isFullScreenSupported()) {
                    myGraphics.setFullScreenWindow(null);

                }
                else {
                    System.out.println("Fail");
                }
            }
            finally {
                myFrame.setLocationRelativeTo(null);
                myGraphics.setDisplayMode(myPrevDispMode);
                myFrame.setMinimumSize(new Dimension(SCREEN_SIZE));
                myFrame.setResizable(true);
                myFrame.setVisible(true);
            }
        }
        SCREEN_SIZE =
                new Dimension(myGraphics.getDisplayMode().getWidth(), myGraphics.getDisplayMode()
                        .getHeight());
        myFrame.repaint();
    }

    public Canvas getCanvas () {
        return myCanvas;
    }

    public JFrame getJFrame () {        
        return myFrame;
    }
    
    public boolean hasFocus() {
        return myFrame.hasFocus();
    }
}
