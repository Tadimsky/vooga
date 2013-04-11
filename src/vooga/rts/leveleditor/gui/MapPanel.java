package vooga.rts.leveleditor.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JViewport;
import vooga.rts.leveleditor.components.EditableMap;
import vooga.rts.leveleditor.components.EditableNode;

public class MapPanel extends JComponent implements MouseListener, MouseMotionListener {
    
    public static final Dimension DEFAULT_MAP_SIZE  = new Dimension (600,600);
    public static final double ZOOM_RATE = 1.25;
    public static int DEFAULT_TILE_WIDTH = 50;
    public static int DEFAULT_TILE_HEIGHT = 50;
    
    private Canvas myCanvas;
    private EditableMap myMap;
    private int myWidth;
    private int myHeight;
    private int myTileWidth;
    private int myTileHeight;
    private boolean myRemoveFlag;
    
    public MapPanel(Canvas canvas) {
        myCanvas = canvas;
        myMap = new EditableMap();
        myWidth = 0;
        myHeight = 0;
        myTileWidth = DEFAULT_TILE_WIDTH;
        myTileHeight = DEFAULT_TILE_HEIGHT;
        setPanelSize();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    private void setPanelSize() {
        setPreferredSize(new Dimension(myTileWidth*myWidth, myTileHeight*myHeight));       
    }

    @Override
    public void paintComponent (Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,myWidth*myTileWidth, myHeight*myTileHeight);
        

        g.setColor(Color.gray);
        for(int i=0; i<myWidth; i++) {
            g.drawLine(i*myTileWidth, 0, i*myTileWidth, myHeight*myTileHeight);
        }

        for(int j=0; j<myHeight; j++) {
            g.drawLine(0,j*myTileHeight, myWidth*myTileWidth, j*myTileHeight);
        }

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor(Color.black);
        g.drawLine(0, 0, myWidth * myTileWidth, 0);
        g.drawLine(0, 0, 0, myHeight * myTileHeight);
        g.drawLine(myWidth * myTileWidth, 0, myWidth * myTileWidth, myHeight * myTileHeight);
        g.drawLine(0, myHeight * myTileHeight, myWidth * myTileWidth, myHeight * myTileHeight);

        //paint Node
        for(int i=0; i<myMap.getWidth(); ++i) {
            for(int j=0; j<myMap.getHeight(); ++j) {
                if(myMap.getMapNode(i, j).getOccupied()) {
                    try {
                        myMap.getMapNode(i, j).paint(g);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        
    }
    
    public void setWidth(int w) {
        myWidth = w;
        setPanelSize();
        repaint();
    }
    
    public void setHeight(int h) {
        myHeight = h;
        setPanelSize();
        repaint();
    }
    
    public void setTileWidth(int w) {
        myTileWidth = w;
        setPanelSize();
        repaint();
    }
    
    public void setTileHeight(int h) {
        myTileHeight = h;
        setPanelSize();
        repaint();
    }
    
    public void initializeMap(int w, int h) {
        myMap = new EditableMap(w,h);
        
    }
    
    public void ZoomIn() {
        myMap.ZoomIn();
        myTileWidth = (int) (myTileWidth * ZOOM_RATE);
        myTileHeight = (int) (myTileHeight * ZOOM_RATE);
        setPanelSize();
        repaint();
    }

    public void ZoomOut() {
        myMap.ZoomOut();
        myTileWidth = (int) (myTileWidth / ZOOM_RATE);
        myTileHeight = (int) (myTileHeight / ZOOM_RATE);
        setPanelSize();
        repaint();        
    }

    public void placeResource(int x, int y) {
        x=x/myTileWidth;
        y=y/myTileHeight;
        if(x>=0 && x<myWidth && y>=0 && y<myHeight){
            EditableNode n = myMap.getMapNode(x, y);
            if(!myRemoveFlag){
                n.addFeature(myCanvas.getCurrentSelectResource().getID());
                n.setImage(myCanvas.getCurrentSelectResource().getImage());
                n.setOccupied(true);
            } else {
                n.reset();
            }
            repaint();
        }
    }
    

    public void clear() {
        myMap.clear();
        repaint();
    }
    
    public void setRemoveFlag(boolean b) {
        myRemoveFlag = b; 
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        placeResource(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        placeResource(e.getX(),e.getY());  
    }


    @Override
    public void mouseMoved(MouseEvent arg0) {

    }




}
