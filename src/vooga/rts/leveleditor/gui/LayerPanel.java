package vooga.rts.leveleditor.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPanel;

/** This class create the layer panel for the level editor
 *  The layer panel shows how many layers are now on the map
 *  and the current player the editor are editting
 *  
 * @author Ziqiang Huang
 *
 */
@SuppressWarnings("serial")
public class LayerPanel extends JPanel {
    
    private Canvas myCanvas;
    private JList myLayers;
    private Vector<String> myLayerVector;
    
    public LayerPanel (Canvas canvas) {
        myCanvas = canvas;
        myLayerVector = new Vector<String>();
        myLayers = new JList(myLayerVector);
        myLayerVector.add("Layer 0");
        myLayers.setListData(myLayerVector);
        addListSelectionListener(myLayers);
        add(myLayers,BorderLayout.WEST);

    }

    private void addListSelectionListener(JList list) {
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                String s = (String) myLayers.getSelectedValue();
                int n = Integer.parseInt(s.substring(s.length()-1));
                myCanvas.getMapPanel().setCurrentLayer(n);
            }
        });
       
    }
    
    /** add layer to the map based on the number
     * 
     * @param layerNum
     */
    
    public void addLayer (int layerNum) {
        myLayerVector.add("Layer " + layerNum);
        myLayers.setListData(myLayerVector);
    }
    
    /**remove the layer from the map based on the number
     * 
     * @param layerNum
     */
    public void removeLayer (int layerNum) {
        if(layerNum>0) {
            myLayerVector.remove("Layer " + layerNum);
            myLayers.setListData(myLayerVector);
        }
    }
    
    /**
     * clear all the layers
     */
    public void clear() {
        myLayerVector.clear();
        myLayerVector.add("Layer 0");
        myLayers.setListData(myLayerVector);
    }

}
