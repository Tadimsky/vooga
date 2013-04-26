package vooga.fighter.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import vooga.fighter.controller.ViewDataSource;

public class HorizontalReverse {
	
	public Image myImage;
	
	public HorizontalReverse() {
		
	}
	
    protected void paintReverse(Graphics2D pen, ViewDataSource data, int objectNumber) {
    	pen.setTransform(AffineTransform.getScaleInstance(1, -1));
    	data.getPaintable(objectNumber).paint(pen, data.getLocation(objectNumber),data.getSize(objectNumber));
    }
    
    

}
