package ie.gmit.sw.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDB {
	
	public static HashMap<Integer, UserAccount> AccountUsers = new HashMap<Integer, UserAccount>();
    static{
    	AccountUsers.put(1, new UserAccount(01, "abc", "abc@gmail.com", "abc"));
    	AccountUsers.put(2, new UserAccount(02, "def", "def@gmail.com", "def"));
    }
    
    public static List<UserAccount> getUsers(){
        return new ArrayList<UserAccount>(AccountUsers.values());
    }
    
    public static UserAccount GetUserID(Integer UserID){
        return AccountUsers.get(UserID);
    }
    
    public static void UpdateUser(Integer UserID, UserAccount user){
    	AccountUsers.put(UserID, user);
    }
    
    public static void RemoveUser(Integer UserID){
    	AccountUsers.remove(UserID);
    }

}
