package vooga.rts.map;

import java.awt.Dimension;
import vooga.rts.util.Location;


public class NodeFactory {

    /**
     * This class will be used to make the NodeMap. This will need to read in a
     * file and create the appropriate nodes off of it.
     */
    public NodeFactory () {

    }

    /**
     * This class will read in a file to make the map appropriately
     * 
     * @param nodeSize
     * @return
     */
    public NodeMap makeMap (int nodeSize, Dimension size) {
        int xCenter = 0;
        int yCenter = 0;
        int xNodes = size.width / nodeSize;
        int yNodes = size.height / nodeSize;
        NodeMap map = new NodeMap(xNodes, yNodes);
        for (int x = 0; x < xNodes; x++) {
            xCenter = x + nodeSize / 2;
            for (int y = 0; y < yNodes; y++) {
                yCenter = y + nodeSize / 2;
                map.put(new Node(x, y), x, y);
            }
        }
        return map;
    }
}
