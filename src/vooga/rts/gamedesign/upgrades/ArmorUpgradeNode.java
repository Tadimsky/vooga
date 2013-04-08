package vooga.rts.gamedesign.upgrades;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import vooga.rts.gamedesign.sprite.rtsprite.interactive.Interactive;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.units.Soldier;
import vooga.rts.util.Location;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;

public class ArmorUpgradeNode extends UpgradeNode {
	
	@Override
	public void apply(Interactive interactive) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
		Class thisClass = interactive.getClass();
		System.out.println(thisClass.getName());
		//Object iClass = thisClass.newInstance();
		Class[] params = new Class[] {int.class};
		Object[] args = new Object[] {50};
		Method thisMethod = thisClass.getDeclaredMethod("myHealth", params);
		thisMethod.invoke(interactive, args);
	}
	
	
	public static void main (String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    	ArmorUpgradeNode a = new ArmorUpgradeNode();
		Interactive i = new Soldier(new Pixmap("soldier.png"), new Location(0,0), new Dimension(50, 50), new Sound("pikachu.wav"), 0, 0);
    	System.out.println(i.getHealth());
    	a.apply(i);
    	System.out.println(i.getHealth());
	}
}
