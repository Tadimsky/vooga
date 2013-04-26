package arcade.controller;

import java.util.Arrays;
import java.util.List;

public class UserSpecificData {
	/**
	 * This class is used to hold user-specific data entered by user during registration. 
	 * When the user presses the "register" button, the view stores all the user-specific 
	 * information in this class and passes to controller.
	 * 
	 * Honestly, I do not think this class is necessary. However, our TA Jimmy was concerned that the 
	 * publish() method in the controller is receiving to many input variables and recommended that
	 * we use a class to encapsulate these variables.
	 * 
	 * If this class remains in final implementation, it may have to be moved to a different package.
	 * 
	 * @author Eunsu (Joe) Ryu - jesryu
	 */
	
	private static final String NO_USER_IMAGE = "";
	
	private String username, password, firstname, lastname, dateOfBirth, imageFilePath;
	
	
	public UserSpecificData(String un, String pw, String fn, String ln, String dob){
		this(un,pw,fn,ln,dob,NO_USER_IMAGE);
	}
	
	public UserSpecificData(String un, String pw, String fn, String ln, String dob, String ifp){
		username = un;
		password = pw;
		firstname = fn;
		lastname = ln;
		dateOfBirth = dob;
		imageFilePath = ifp;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getFirstName() {
		return firstname;
	}
	
	public String getLastName() {
		return lastname;
	}
	
	public String getDOB(){
		return dateOfBirth;
	}
	
	public String getImageFilePath () {
		return imageFilePath;
	}
	
	
	
	
	
}
