package bis;

import dao.*;
import model.*;

public class BISUser {
	
	public String GetUser(Request request){
		return new DAOUser().GetUser(request);
	}
		
	public String InsertUser(Request request){
		return new DAOUser().InsertUser(request);
	}
	
	public String UpdateUser(Request request){
		return new DAOUser().UpdateUser(request);
	}		
	
}
