package vooga.rts.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import vooga.rts.gui.Button;
import vooga.rts.util.Location;


public class ActionButton extends Button {

    private String myText;
    private Color myBGColor;
    private Color myTextColor;
    private int fontSize;
    private int myXPadding = 5;
    private int myYPadding = 10;
    private int myID;

    public ActionButton (String text, int i, Color d, int f, Location pos) {
        super(null, null, pos);
        myText = text;
        myTextColor = d;
        fontSize = f;
        myID = i;
    }

    @Override
    public void update (double elapsedTime) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void paint (Graphics2D pen) {
        pen.setFont(new Font("Arial", Font.BOLD, fontSize));
        int width = pen.getFontMetrics().stringWidth(myText);
        int height = pen.getFontMetrics().getHeight();
        
        mySize = new Dimension(width, height);
        pen.setColor(myTextColor);
        pen.drawString(myText, (int) myPos.getX(), (int) myPos.getY() + height);
        // pen.drawRect((int) myPos.getX(), (int) myPos.getY()-height, width, height);

    }
    
    public int getID () {
        return myID;
    }

    @Override
    public void processClick () {
        update(0);
    }

    @Override
    public void processHover () {
        // TODO Auto-generated method stub

    }

}
