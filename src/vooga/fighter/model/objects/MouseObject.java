package vooga.fighter.model.objects;

import java.awt.geom.Point2D;

import vooga.fighter.model.ModelConstants;
import vooga.fighter.model.loaders.MouseLoader;
import vooga.fighter.model.utils.UpdatableLocation;

public class MouseObject extends GameObject {

	
    private int myTicks;
    
	public MouseObject(Point2D loc, String pathHierarchy) {
        setLocation(new UpdatableLocation(loc.getX(), loc.getY()));
        setLoader(new MouseLoader(this, pathHierarchy));
        setImageData();
        myTicks = 0;
	}
	

    @Override
    public boolean shouldBeRemoved () {
        return (myTicks > 0);
    }

    @Override
    public void completeUpdate () {
        myTicks++;
    }
	
    public String getImageTag(){
    	return ModelConstants.MOUSE_IMAGE_TAG;
    }

}
