package bis;

import com.google.gson.Gson;

import dao.*;
import model.Request;

public class BISProject {

	public String GetProjectStatus(Request request) {
		return new Gson().toJson(new DAOProject().GetProjectStatus(request));
	}

	public String GetProjectTheme(Request request) {
		return new Gson().toJson(new DAOProject().GetProjectTheme(request));
	}

	public String GetUIContentOrder(Request request) {
		return new Gson().toJson(new DAOProject().GetUIContentOrder(request));
	}
}
