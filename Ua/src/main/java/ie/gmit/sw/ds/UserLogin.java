package ie.gmit.sw.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin {

	// this class is for users to login once everything is connected up.
	private int UserID;
	private String Password;

	@JsonProperty
	public int getUserID() {
		return UserID;
	}
	@JsonProperty
	public String getPassword() {
		return Password;
	}
}
