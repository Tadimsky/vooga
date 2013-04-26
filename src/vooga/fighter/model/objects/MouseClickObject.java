package vooga.fighter.model.objects;

import java.awt.geom.Point2D;

import vooga.fighter.model.ModelConstants;


public class MouseClickObject extends MouseObject {

    public MouseClickObject (Point2D loc, String pathHierarchy) {
    	super(loc, pathHierarchy);
    }
    
    @Override
    public String getImageTag(){
    	return ModelConstants.MOUSE_CLICK_IMAGE_TAG;
    }
}
