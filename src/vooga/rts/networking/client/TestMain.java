package vooga.rts.networking.client;

import javax.swing.JFrame;

public class TestMain {

    public TestMain () {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new ServerBrowserView());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
