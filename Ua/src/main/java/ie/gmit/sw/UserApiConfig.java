package ie.gmit.sw;

import io.dropwizard.Configuration;

public class UserApiConfig extends Configuration {

	public String Host = "localhost";

	private int port = 5000;

	public String getHost() {
		return Host;
	}

	public int getPort() {
		return port;
	}
}