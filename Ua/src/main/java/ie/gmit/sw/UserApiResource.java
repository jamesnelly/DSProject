package ie.gmit.sw;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApiResource {

	private HashMap<Integer, UserAccount> AccountUsers = new HashMap<Integer, UserAccount>();

	public UserApiResource() {
		UserAccount test1 = new UserAccount(01, "abc", "abc@gmail.com", "abc");
		UserAccount test2 = new UserAccount(02, "def", "def@gmail.com", "def");
		AccountUsers.put(test1.UserID, test1);
		AccountUsers.put(test2.UserID, test2);
	}

	// showing a list of all users
	@GET
	public Collection<UserAccount> getUsers() {
		return AccountUsers.values();
	}

	// getting a specific user by ID
	@GET
	@Path("/{UserID}")
	public UserAccount getUserID(@PathParam("UserID") int UserID) {
		return AccountUsers.get(UserID);
	}

	// Delete user by ID
	@DELETE
	@Path("/{UserID}")
	public Collection<UserAccount> DeleteUserByID(@PathParam("UserID") int UserID) {
		if (!AccountUsers.values().isEmpty()) {
			AccountUsers.remove(UserID);
		}
		return AccountUsers.values();
	}
	
}
