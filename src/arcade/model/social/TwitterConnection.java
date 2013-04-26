package arcade.model.social;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * This class allows the Model to interface with the twitter API.
 * 
 * The implementation uses the external twitter4j library, which basically 
 * does all the work for us.
 * 
 * Because the only functionality we needed to use was to send a tweet, that is
 * the only functionality supported in this class, but more features could
 * be added later.
 * 
 * To use the send a tweet feature, first create a new TwitterConnection, and
 * call newRequest().  That method will return a URL where the user can authorize
 * us to use their Twitter account.  After the user agrees, s/he will get a pin.
 * Then call sendTweet() with the pin the user has and the text to tweet.
 * 
 * @author Ellango
 *
 */
public class TwitterConnection {
    /**
     * Consumer Secret should probably be kept a secret...
     */
    private static final String CONSUMER_KEY = "pUIkq79OeuKPgbbGDKwQ";
    private static final String CONSUMER_SECRET = "JLuj42An4zgebJfRNOvPT53kyI2LCDlbasTxd7cR8w";
    
    private Twitter myTwitter;
    private RequestToken myRequestToken;
    
    /**
     * Prepares the connection by setting the credentials.
     */
    public TwitterConnection() {
        myTwitter = TwitterFactory.getSingleton();
        myTwitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    }

    /**
     * Sets up a new request to get access to a user's Twitter account.
     * 
     * Returns a URL that they can visit to log in and authorize this request.
     * After a user logs in at the provided URL, s/he will have a pin that can
     * be used to call other methods in this class.
     * 
     * @return
     * @throws TwitterException
     */
    public String newRequest() throws TwitterException {
        myRequestToken = myTwitter.getOAuthRequestToken();
        return myRequestToken.getAuthorizationURL();
    }
    
    /**
     * Sends a tweet with the provided text.  Requires the pin the user received
     * from logging in after calling newRequest().
     * 
     * @param pin
     * @param text
     * @throws TwitterException
     */
    public void sendTweet(String pin, String text) throws TwitterException {
        AccessToken accessToken = myTwitter.getOAuthAccessToken(myRequestToken, pin);
        myTwitter.setOAuthAccessToken(accessToken);
        myTwitter.updateStatus(text);
    }
}
