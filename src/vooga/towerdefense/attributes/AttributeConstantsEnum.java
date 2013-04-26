package vooga.towerdefense.attributes;

/**
 * Contains constants for use in defining attribute names
 * 
 * Should this be an Enum?
 * 
 * @author Matthew Roy
 * @author Zhen Gou
 * @author Jimmy Longley
 * @author Yoshida
 * 
 */
public enum AttributeConstantsEnum {
    NAME("Name"),
    HEALTH("Health"),
    MONEY("Money"),
    SCORE("Score"),
    SHAPE("Shape"),
    AFFILIATION("Affiliation"),
    ATTACK_DAMAGE("Attack Damage"),
    ATTACK_RADIUS("Attack Range"),
    NUM_OF_TARGETS("Number of Targets"),
    TILES_WIDE("Tiles wide"),
    TILES_TALL("Tiles tall"),
    
    /**
     * Attacks per second
     */
    ATTACK_INTERVAL("Attack Speed"),
    ARMOR("Armor"),
    MOVE_SPEED("Move Speed"),
    DIRECTION("Direction"),
    UPGRADES("Upgrades"),
    COST("Cost"),
    
    PROJECTILE_FACTORY("projectile factory"),
    AURA_EFFECT("Aura");
    
    private String attributeConstant;
    
    AttributeConstantsEnum(String s) {
        attributeConstant = s;
    }
    
    public String getStatusCode () {
        return attributeConstant;
    }
}
