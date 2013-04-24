package vooga.rts.networking.communications;

/**
 * Represents a timeStamp that will be sent across the network. It is based on parameters passed in
 * by user, however it will still store a system time to use in some cases.
 * 
 * @author Henrique Moraes
 * @author David Winegar
 * 
 */
public class UserTimeStamp extends TimeStamp {

    private static final long serialVersionUID = 1673418588253438258L;
    private long myStartTime = 0;
    private long mySystemStartTime;

    /**
     * Starts at passed in time.
     * 
     * @param startTime time to start
     */
    public UserTimeStamp (Long startTime) {
        myStartTime = startTime;
        resetStamp();
    }

    /**
     * Instantiates a timestamp with starting time 0.
     */
    public UserTimeStamp () {
        resetStamp();
    }

    /**
     * Resets time stamp to default value passed in.
     */
    @Override
    public void resetStamp () {
        setInitialTime(myStartTime);
        setFinalTime(DEFAULT_VALUE);
        mySystemStartTime = System.currentTimeMillis();
    }

    @Override
    public long getDifference () {
        if (getFinalTime() == DEFAULT_VALUE) {
            setFinalTime(System.currentTimeMillis() - mySystemStartTime);
        }
        return getFinalTime() - getInitialTime();
    }

    @Override
    public long stamp () {
        setFinalTime(System.currentTimeMillis() - mySystemStartTime);
        return getDifference();
    }

    @Override
    public long stamp (long time) {
        setFinalTime(time);
        return getDifference();
    }

}
