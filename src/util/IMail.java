package util;

/**
 * Interface that ensures that an object that handles e-mails
 * will have all the necessary information
 * @author Henrique Moraes
 *
 */
public interface IMail {
	
	/**
	 * Sets the properties of e-mail messages 
	 * @param from address from sender
	 * @param to String array with recipients to receive e-mail
	 * @param server Server Address
	 * @param subject Subject of e-mail
	 * @param message Text in e-mail
	 */
	public void setProperties(String from, String[] to,
			String server, String subject, String message);
}
