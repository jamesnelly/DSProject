//Adapted from https://github.com/john-french/artistAPI-dropwizard
package ie.gmit.sw;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccount {

	int UserID;
	String UserName;
	String Email;
	String Password;

	public UserAccount() {
	}

	public UserAccount(int UserID, String UserName, String Email, String Password) {
		this.UserID = UserID;
		this.UserName = UserName;
		this.Email = Email;
		this.Password = Password;
	}

	// No setters as we want our class to be immutable
	@JsonProperty
	public int getUserID() {
		return UserID;
	}

	public static void setUserID(int UserID) {
		// this.UserID = UserID;
	}

	@JsonProperty
	public String getUserName() {
		return UserName;
	}

	@JsonProperty
	public String getEmail() {
		return Email;
	}

	@JsonProperty
	public String getPassword() {
		return Password;
	}

}