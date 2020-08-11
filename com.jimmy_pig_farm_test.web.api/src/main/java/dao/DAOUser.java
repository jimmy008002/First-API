package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dto.DTOResponseMessage;
import helper.AppTech;
import helper.JWTHelper;
import model.Request;

public class DAOUser extends AppTech {
	int db_position = addDBConnection("project_db", "towngas");

	// GetUser
	public String GetUser(Request request) {
		DTOResponseMessage responseMessage = new DTOResponseMessage();
//		int authRes = new JWTHelper().authToken(request.header.token);
//		if (authRes == -401) {
//			responseMessage.set401Message();
//			return new Gson().toJson(responseMessage);
//		}
//
//		if (authRes == -409) {
//			return new Gson().toJson(responseMessage);
//		}
		ArrayList<Object> values = new ArrayList<Object>();

		String sql = "select * from admin_user where 1=1 ";

		if (request.body.user.user_id != -1) {
			sql += "and admin_user.user_id= ? ";
			values.add(request.body.user.user_id);
		}

		if (!request.body.user.user_status.equals("")) {
			sql += "and user.user_status = ? ";
			values.add(request.body.user.user_status);
		}

		sql += "order by user.user_id ";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);
	}

	// InsertUser
	public String InsertUser(Request request) {
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		int authRes = new JWTHelper().authToken(request.header.token);
		if (authRes == -401) {
			responseMessage.set401Message();
			return new Gson().toJson(responseMessage);
		}

		if (authRes == -409) {
			responseMessage.set409Message();
			return new Gson().toJson(responseMessage);
		}
		String user_id = String.valueOf(getDBConnectionByPosition(db_position)
				.getSingleValue("SELECT user_id FROM user_auth WHERE token = '" + request.header.token + "' "));
		String sql = "insert into user (user_login,user_password, user_status, role_id, user_update_user  ) values (?, ?, ?, ?, ?)";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(request.body.user.user_login);
		values.add(request.body.user.user_password);
		values.add(request.body.user.user_status);
		values.add(request.body.user.role_id);
		values.add(user_id);
		if (getDBConnectionByPosition(db_position).updateValue(sql, values)) {
			responseMessage.set200Message();
		} else {
			responseMessage.set422Message();
		}

		dbClose();
		return new Gson().toJson(responseMessage);
	}

	// UpdateUser
	public String UpdateUser(Request request) {
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		int authRes = new JWTHelper().authToken(request.header.token);
		if (authRes == -401) {
			responseMessage.set401Message();
			return new Gson().toJson(responseMessage);
		}

		if (authRes == -409) {
			responseMessage.set409Message();
			return new Gson().toJson(responseMessage);
		}
		String user_id = String.valueOf(getDBConnectionByPosition(db_position)
				.getSingleValue("SELECT user_id FROM user_auth WHERE token = '" + request.header.token + "' "));
		String sql = "UPDATE user set user_login = ?, user_password = ?, user_status = ?, role_id = ? , user_update_user = ? where user_id = ?";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(request.body.user.user_login);
		values.add(request.body.user.user_password);
		values.add(request.body.user.user_status);
		values.add(request.body.user.role_id);
		values.add(user_id);
		values.add(request.body.user.user_id);
		getDBConnectionByPosition(db_position).updateValue(sql, values);
		values.clear();
		// Clear web_auth record
		sql = "Delete from web_auth where user_id = ? ";
		values.add(request.body.user.user_id);
		getDBConnectionByPosition(db_position).updateValue(sql, values);
		values.clear();

		// create web_auth record

		for (int i = 0; i < request.body.userWebAuth.web_auth_id_AL.size(); i++) {
			sql = "insert into web_auth ( web_auth_info_id, user_id  ) values (?, ?)";
			values.add(request.body.userWebAuth.web_auth_id_AL.get(i));
			values.add(request.body.user.user_id);
			getDBConnectionByPosition(db_position).updateValue(sql, values);
			values.clear();
		}
		responseMessage.set200Message();
		dbClose();
		return new Gson().toJson(responseMessage);
	}

}
