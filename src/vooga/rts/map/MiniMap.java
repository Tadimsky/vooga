package vooga.rts.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import vooga.rts.IGameLoop;


public class MiniMap implements IGameLoop {

    private GameMap myMap;
    private Dimension myMaxSize;
    
    private int nodesPerPixel; 

    public MiniMap (GameMap toshow, Dimension size) {
        myMap = toshow;
        myMaxSize = size;
        // nodesPerPixel = (int) myMap.getNodeMap().getWidth() / size.getWidth();
    }

    @Override
    public void update (double elapsedTime) {        
        
    }

    @Override
    public void paint (Graphics2D pen) {
        
    }

}
