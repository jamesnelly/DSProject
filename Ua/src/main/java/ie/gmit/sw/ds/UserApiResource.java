// Adapted from https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
package ie.gmit.sw.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Response.Status;

// this will handle requests for the base path
@Path("/users")
// this will return JSON responses 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApiResource {

	private HashMap<Integer, UserAccount> AccountUsers = new HashMap<Integer, UserAccount>();

	private final Validator validator;

	public UserApiResource(Validator validator) {
		this.validator = validator;
	}

	// showing a list of all users
	@GET
	public Response getUsers() {
		return Response.ok(UserDB.getUsers()).build();
	}

	// getting a specific user by ID
	@GET
	@Path("/{UserID}")
	public Response GetUserID(@PathParam("UserID") int UserID) {
		UserAccount AccountUsers = UserDB.GetUserID(UserID);
		if (AccountUsers != null)
			return Response.ok(AccountUsers).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	// Delete user by ID
	@DELETE
	@Path("/{UserID}")
	public Response RemoveUserByID(@PathParam("UserID") int UserID) {
		UserAccount AccountUsers = UserDB.GetUserID(UserID);
		if (AccountUsers != null) {
			UserDB.RemoveUser(UserID);
			return Response.ok().build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}

	// update user <NOT working for me getting Error code 400 (Unable to process
	// JSON)>
	@PUT
	@Path("/{UserID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response UpdateUserByID(@PathParam("UserID") int UserID, UserAccount AccountUsers) {
		// Validation begins
		Set<ConstraintViolation<UserAccount>> violations = validator.validate(AccountUsers);
		UserAccount USER = UserDB.GetUserID(AccountUsers.UserID);
		if (violations.size() > 0) {
			ArrayList<String> validationMessages = new ArrayList<String>();
			for (ConstraintViolation<UserAccount> violation : violations) {
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		if (USER != null) {
			UserAccount.setUserID(UserID);
			UserDB.UpdateUser(UserID, AccountUsers);
			return Response.ok(AccountUsers).build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}
}