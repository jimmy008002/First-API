package bis;

import dao.*;
import model.*;

public class BISUserAuth {
	public String WebLogin(Request request) {
		return new DAOUserAuth().WebLogin(request);
	}

	public String GetUserWebAuth(Request request) {
		return new DAOUserAuth().GetUserWebAuth(request);
	}

	public String InsertUserWebAuth(Request request) {
		return new DAOUserAuth().InsertUserWebAuth(request);
	}

	public String UpdateUserWebAuth(Request request) {
		return new DAOUserAuth().UpdateUserWebAuth(request);
	}

	public String GetModelWebAuth(Request request) {
		return new DAOUserAuth().GetModelWebAuth(request);
	}

	public String DeleteUserWebAuth(Request request) {
		return new DAOUserAuth().DeleteUserWebAuth(request);
	}
	public String GetUserWebAuthInUserManagement(Request request) {
		return new DAOUserAuth().GetUserWebAuthInUserManagement(request);
	}

}
