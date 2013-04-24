package vooga.rts.networking.communications;

public interface IMessage {

    /**
     * A Message is the object sent between the server and the client.
     * A Designer may subclass Message to send whatever form of data they wish.
     * There is also a SystemLevel Message subclass that is used to communicate
     * between the server for managerial tasks; the developer likely will not need
     * to worry about these.
     * 
     * @author Sean Wareham
     * 
     *
        private TimeStamp myTimeStamp;

        /**
         * Constructor for this class
         * Creates a timestamp for this message with the given time
         * as the initial time
         * 
         * @param timeStamp timestamp passed in
         */


        /**
         * 
         * @return this message's timestamp
         */
        public TimeStamp getTimeStamp ();

        /**
         * Call this method to reset the initial time to the current one and
         * the final time to the default
         */
        public void resetTime ();

        /**
         * Call this method to mark the time received (final time)
         */
        public void stampTime ();

        /**
         * Call this method to mark the time received (final time)
         * @param time to stamp
         */
        public void stampTime (long time);

        /**
         * 
         * @return time message was created or reset in milliseconds
         */
        public long getInitialTime ();

        /**
         * 
         * @return time this message was stamped in milliseconds
         */
        public long getFinalTime ();

        @Override
        public boolean equals (Object object);

        @Override
        public int hashCode ();

        /**
         * Compares based on timestamps
         * @param message to compare
         */
        
        public int compareTo (Message message);

    

}
