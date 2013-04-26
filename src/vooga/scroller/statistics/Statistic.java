package vooga.scroller.statistics;

/**
 * This interface is used to set standard methods in order to collect and keep track of player data
 * and statistics.
 * 
 * @author Scott Valentine
 *
 */
public interface Statistic {

    /**
     * Adds the value the this statistic's data
     * 
     * @param val to be removed form statistic.
     */
    public void addValue(int val);
    
    /**
     * Removes the value from this statistic's data.
     * 
     * @param val to be removed from this statistic.
     */
    public void removeValue(int val);
    
    /**
     * Gives an aggregate value for statistic. This could be a sum (in the case of score)
     * or the size of the data set (for the implementation of a user inventory).
     * 
     * @return The aggregate value of statistic.
     */
    public int getAggregateValue();
    
    /**
     * Gives the name of this statistic. This is often used for displaying purposes. 
     * For example, Score would return the name “Score”, while in a player inventory, 
     * it might return the name of the current item.
     * 
     * @return The name of statistic.
     */
    public String getName();
    
}
