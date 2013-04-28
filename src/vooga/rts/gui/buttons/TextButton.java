package vooga.rts.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import util.Location;
import vooga.rts.gui.Button;

public class TextButton extends Button {

	private String myText;
	private Color myBGColor;
	private Color myTextColor;
	private int fontSize;
	private int myXPadding = 5;
	private int myYPadding = 10;


	public TextButton(String text, Color c, Color d, int f, Dimension size, Location pos) {
		super(null, size, pos);
		myText = text;
		myBGColor = c;
		myTextColor = d;
		fontSize = f;
	}

	@Override
	public void update(double elapsedTime) {
		setChanged();
		notifyObservers();
	}

	@Override
	public void paint(Graphics2D pen) {
		if (myBGColor != null) {
			pen.setColor(myBGColor);
			pen.fill(new Rectangle2D.Double(myPos.x, myPos.y, mySize.width,
					mySize.height));
		}
		pen.setFont(new Font("Arial", Font.BOLD, fontSize));

		int cX = (int) (myPos.x + myXPadding);
		int cY = (int) ((myPos.y + (mySize.height / 2)) + myYPadding);
		pen.setColor(myTextColor);
		pen.drawString(myText, cX, cY);
	}

    @Override
    public void processClick () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processHover () {
        // TODO Auto-generated method stub
        
    }

}
