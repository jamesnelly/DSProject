// Adapted from https://github.com/john-french/artistAPI-dropwizard
package ie.gmit.sw.ds;

import com.codahale.metrics.health.HealthCheck;

public class UserHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		
		return Result.healthy();
	}

}
