package vooga.scroller.collision_manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import vooga.scroller.level_editor.Level;
import vooga.scroller.marioGame.spritesDefinitions.collisions.VisitMethods;
import vooga.scroller.sprites.Sprite;


/**
 * Currently, we are handling all collisions through CollisionManager.
 * CollisionManager uses reflection to figure out which visit() method needs to
 * be called for the Sprites that have just intersected. This is a much more elegant
 * way of handling collisions than the Visitor method we were previously using. 
 * <br>
 * <br>
 * Collisions can occur on two levels. The recommended level is when the two sprites 
 * have an interface implementation. For example, rather than have a Mario/Koopa collision,
 * we would much prefer the more general IPlayer/IEnemy collision. This allows us to 
 * condense the number of visit() methods needed. However, sometimes you need a specific 
 * collision between a sprite that doesn't fit in any interface. In that case, you can 
 * define the collision on a sprite level. For example, you could have a Mario/Star collision. 
 * You would need to have a visit method that looks like "visit(Mario mario, Star star)" to handle 
 * that kind of collision. 
 * 
 * 
 * @author Jay Wang
 * 
 */

public class CollisionManager {

    Level myLevel;
    private VisitMethods visit;

    public CollisionManager (Level level) {
        myLevel = level;
        visit = new VisitMethods();
    }

    /**
     * When two sprites intersect, handleCollision() is the method you want to call. This method takes 
     * two sprites and IF you have defined a collision for these two sprites, handleCollision() will 
     * pull the correct visit method and invoke it on your sprites. This is done through Java's reflection 
     * feature. Specifically, what it is doing is matching method signatures. 
     * 
     * @param sprite1
     * @param sprite2
     */
    public void handleCollision (Sprite sprite1, Sprite sprite2) {

        Class<? extends Sprite> clazz1 = sprite1.getClass();
        Class<? extends Sprite> clazz2 = sprite2.getClass();
        
        
        
        @SuppressWarnings("rawtypes")
        List<Class> classList = new ArrayList<Class>();

        classList.add(clazz1);
        classList.add(clazz2);
        
        
        addInterfaces(clazz1, classList);
        addInterfaces(clazz2, classList);
        addSuperClasses(clazz1, classList);
        addSuperClasses(clazz2, classList);
        
        // classArray has all possible (need all 2 combos)
        Object[] sprites = { sprite1, sprite2 };

        for(int i = 0; i < classList.size(); ++i){
            for(int j =0; j < classList.size(); ++j){
                @SuppressWarnings("rawtypes")
                Class[] classArray = {classList.get(i), classList.get(j)};
                invokeVisit(classArray, sprites);
            }
        }
        
        
        
        
//        if (getInterfaces(clazz1) == null ||
//            getInterfaces(clazz2) == null) {
//            @SuppressWarnings("rawtypes")
//            Class[] classArray = { clazz1, clazz2 };
//            invokeVisit(classArray, sprites);
//        }
//
//        else {
//            @SuppressWarnings("rawtypes")
//            Class[] classArray =
//                    { getInterfaces(clazz1)[0], getInterfaces(clazz2)[0] };
//            Object[] sprites = { sprite1, sprite2 };
//            invokeVisit(classArray, sprites);
//        }
    }
    
    
    @SuppressWarnings("rawtypes")
    private void addSuperClasses(Class clazz, List<Class> classList){
        Class superClass = clazz.getSuperclass();
        if(superClass != null){
            classList.add(superClass);
            addSuperClasses(superClass, classList);
        }        
    }
    
    @SuppressWarnings("rawtypes")
    private void addInterfaces(Class clazz, List<Class> classList){
            for(Class c: clazz.getInterfaces()){
                classList.add(c);
            }
            if(clazz.getSuperclass() != null){
                addInterfaces(clazz.getSuperclass(), classList);
            }
    }
    
    
    @SuppressWarnings("rawtypes")
    public Class[] getInterfaces(Class clazz) {
        if(clazz.getInterfaces().length != 0)
            return clazz.getInterfaces();
        if(clazz.getSuperclass() == null)
            return null;
        return getInterfaces(clazz.getSuperclass());
        
    }

    private void invokeVisit (@SuppressWarnings("rawtypes") Class[] classArray, Object[] sprites) {
        try {
            Method method = visit.getClass().getMethod("visit", classArray);

            method.invoke(visit, sprites);
        }

        catch (SecurityException e) {           
            e.printStackTrace();
        }

        /**
         * If there is No Such Method, that means the game designer does not want
         * anything to happen when this collision occurs.
         */
        catch (NoSuchMethodException e) {
            return;
        }

        catch (IllegalArgumentException e) {
            //e.printStackTrace();
        }

        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
