package vooga.rts.networking.client;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class TestMain {

    /**
     * @param args
     */
    public static void main (String[] args) {
        List<String> factions = new ArrayList<String>();
        factions.add("protoss");
        factions.add("zerg");
        List<String> maps = new ArrayList<String>();
        maps.add("map1");
        maps.add("map2");
        List<Integer> maxPlayers = new ArrayList<Integer>();
        maxPlayers.add(4);
        maxPlayers.add(6);
        ClientModel model =
                new ClientModel("Test Game", "User 1", factions, maps, maxPlayers);

        JFrame frame = new JFrame();
        frame.add(model.getView());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setVisible(true);
        frame.pack();
    }

}
