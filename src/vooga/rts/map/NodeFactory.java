package vooga.rts.map;

import java.awt.Dimension;


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
        int xNodes = size.width / nodeSize;
        int yNodes = size.height / nodeSize;
        NodeMap map = new NodeMap(xNodes, yNodes);
        for (int x = 0; x < xNodes; x++) {
            for (int y = 0; y < yNodes; y++) {
                map.put(new Node(x, y), x, y);
            }
        }
        return map;
    }
}
