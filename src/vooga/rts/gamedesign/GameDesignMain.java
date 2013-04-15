package vooga.rts.gamedesign;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import vooga.rts.gamedesign.sprite.rtsprite.Projectile;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.units.Soldier;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.units.Unit;
import vooga.rts.gamedesign.strategy.attackstrategy.CanAttack;
import vooga.rts.gamedesign.strategy.attackstrategy.CannotAttack;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Location;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import vooga.rts.gamedesign.Weapon;

public class GameDesignMain {

    /**
     * @param args
     * @throws CloneNotSupportedException 
     * @throws NoSuchMethodException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws IllegalArgumentException 
     */
    public static void main(String[] args) {

//        Pixmap p = new Pixmap(ResourceManager.instance().loadFile("images/soldier.png"));
//        Location l1 = new Location(40,50);
//        Location l2 = new Location(20,20);
//        Dimension s = new Dimension(100,100);
//        Sound soun = new Sound("pikachu.wav");
//
//        Unit a = new Soldier(p,l1,s,soun,3,40);
//        a.setAttackStrategy(new CannotAttack());
//
//        Unit b = new Soldier(p,l2,s,soun,2,50);
//        Projectile proj = new Projectile(new Pixmap(ResourceManager.instance().loadFile("images/bullet.png")), l2, new Dimension(30, 30), 1, 10, 1);
//        b.setAttackStrategy(new CanAttack());
//
//        //b.addWeapons(new Weapon(0, proj, 200, b.getCenter(),20));
//        System.out.println("lol");
//        
//        if(b.inRange(a)){
//            System.out.println("a in range b");
//        }

    }

}
