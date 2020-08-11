package bis;

import com.google.gson.Gson;

import dao.*;
import dto.DTOResponseMessage;
import helper.JWTHelper;
import model.Request;

public class BISFileInfo {
	public String UploadImage(Request request) {
		return new DAOFile().UploadImage(request);
	}
}