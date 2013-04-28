package vooga.rts.gui.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import vooga.rts.gui.Menu;

public class GameOverMenu extends Menu {

    public GameOverMenu() {
        
    }
    
    @Override
    public void paint (Graphics2D pen) {
        pen.setFont(new Font("Helvetica", Font.BOLD, 100));
        pen.setColor(Color.red);
        pen.drawString("Game Over...", 150, 300);
    }
    
    @Override
    public void handleMouseDown(int x, int y) {
        setChanged();
        notifyObservers();
    }
}
