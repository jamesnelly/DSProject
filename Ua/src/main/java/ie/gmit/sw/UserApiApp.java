package ie.gmit.sw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class UserApiApp extends Application<UserApiConfig> {
	private static final Logger logger = LoggerFactory.getLogger(UserApiApp.class);

	public static void main(String[] args) throws Exception {
		new UserApiApp().run(args);
	}
	@Override
	public void run(UserApiConfig UserApiConfig, Environment environment) throws Exception {

		logger.info("Getting Rest Resource");
		final UserApiResource resource = new UserApiResource();

		environment.jersey().register(new UserApiResource());

		final UserHealthCheck healthCheck = new UserHealthCheck();
		environment.healthChecks().register("exp", healthCheck);
	}
}