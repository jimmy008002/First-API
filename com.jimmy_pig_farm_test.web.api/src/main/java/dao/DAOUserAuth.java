package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dto.*;
import helper.*;
import model.*;

public class DAOUserAuth extends AppTech {
	int db_position = addDBConnection("project_db", "seto_common");

	// WebLogin
	public String WebLogin(Request request) {
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		responseMessage.set401Message();
		responseMessage.setBody("");
		MToken token = new MToken();
		token.token = "";
		JWTHelper jwtHelper = new JWTHelper();
		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position)
				.getTable("SELECT * FROM user WHERE user_login = '" + request.body.username + "' AND user_password = '"
						+ request.body.password + "' AND user_status = 'ACTIVE'");

		if (rows.size() > 0) {
			token.token = jwtHelper.createToken(String.valueOf(rows.get(0).get("user_login")),
					String.valueOf(rows.get(0).get("user_password")));

			String user_id = String.valueOf(getDBConnectionByPosition(db_position)
					.getSingleValue("SELECT user_id FROM user WHERE user_login = '" + request.body.username + "' "));

			ArrayList<Object> values = new ArrayList<Object>();
			values.add(token.token);
			values.add(user_id);
			getDBConnectionByPosition(db_position).insertValue("INSERT INTO user_auth (token, user_id ) VALUES (?, ?)",
					values);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("token", token.token);
			result.put("user_id", user_id);
			ArrayList<Map<String, Object>> rows_2 = getDBConnectionByPosition(db_position)
					.getTable("select project_info.project_info_name from user , project_info, user_project "
							+ "where user.user_id = user_project.user_id "
							+ "and project_info.project_info_id = user_project.project_info_id "
							+ "and user_project.user_id = '" + user_id + "' ");
			String[] projectName = new String[rows_2.size()];
			for (int i = 0; i < rows_2.size(); i++) {
				projectName[i] = rows_2.get(i).get("project_info_name").toString();
			}
			result.put("project", projectName);
			responseMessage.setBody(result);
			responseMessage.set200Message();
		} else {
			ArrayList<Map<String, Object>> rows_1 = getDBConnectionByPosition(db_position)
					.getTable("SELECT * FROM user WHERE user_name = '" + request.body.username + "'");
			if (rows_1.size() > 0) {
				responseMessage.set405Message();
			} else {
				responseMessage.set406Message();
			}
		}

		dbClose();

		return new Gson().toJson(responseMessage);
	}

	// UpdateUserWebAuth
	public String UpdateUserWebAuth(Request request) {
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

		String sql = "UPDATE user_web_auth set web_auth_id = ?, user_id = ? where user_web_auth_id = ?";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(request.body.userWebAuth.web_auth_id);
		values.add(request.body.userWebAuth.user_id);
		values.add(request.body.userWebAuth.user_web_auth_id);

		if (getDBConnectionByPosition(db_position).updateValue(sql, values)) {
			responseMessage.set200Message();
		} else {
			responseMessage.set422Message();
		}
		dbClose();
		return new Gson().toJson(responseMessage);
	}

	// GetUserWebAuth
	public String GetUserWebAuth(Request request) {
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		int authRes = new JWTHelper().authToken(request.header.token);
		if (authRes == -401) {
			responseMessage.set401Message();
			return new Gson().toJson(responseMessage);
		}

		if (authRes == -409) {
			return new Gson().toJson(responseMessage);
		}
		String project_info_id = String.valueOf(getDBConnectionByPosition(db_position)
				.getSingleValue("SELECT project_info_id FROM project_info WHERE project_info_name = '"
						+ request.body.projectInfo.project_info_name + "' "));

		String user_id = String.valueOf(getDBConnectionByPosition(db_position)
				.getSingleValue("SELECT user_id FROM user_auth WHERE token = '" + request.header.token + "' "));

		String sql = "select * from project_info, web_auth,web_auth_info "
				+ "where web_auth.web_auth_info_id = web_auth_info.web_auth_info_id "
				+ "and project_info.project_info_id = web_auth_info.project_info_id " + "and web_auth.user_id = ? "
				+ "and web_auth_info.project_info_id = ? "
				+ "order by web_auth_info.project_info_id , web_auth_info.parent_id, web_auth_info.sequence ";
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(user_id);
		values.add(project_info_id);
		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);
	}

	// DeleteUserWebAuth
	public String DeleteUserWebAuth(Request request) {
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

		if (request.body.userWebAuth.user_id != -1 || request.body.userWebAuth.web_auth_id != -1
				|| request.body.userWebAuth.user_web_auth_id != -1) {

			String sql = "delete from user_web_auth where 1=1 ";

			if (request.body.userWebAuth.user_id != -1) {
				sql += "and user_id = '" + request.body.userWebAuth.user_id + "' ";
			}

			if (request.body.userWebAuth.web_auth_id != -1) {
				sql += "and web_auth_id = '" + request.body.userWebAuth.web_auth_id + "' ";
			}

			if (request.body.userWebAuth.user_web_auth_id != -1) {
				sql += "and user_web_auth_id = '" + request.body.userWebAuth.user_web_auth_id + "' ";
			}

			ArrayList<Object> values = new ArrayList<Object>();

			if (getDBConnectionByPosition(db_position).updateValue(sql, values)) {
				responseMessage.set200Message();
			} else {
				responseMessage.set422Message();
			}
		} else {
			responseMessage.set410Message();
		}
		dbClose();
		return new Gson().toJson(responseMessage);
	}

	// InsertUserWebAuth
	public String InsertUserWebAuth(Request request) {
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

		String sql = "insert into user_web_auth (user_id, web_auth_id) values (?, ?)";

		ArrayList<Object> values = new ArrayList<Object>();
		values.add(request.body.userWebAuth.user_id);
		values.add(request.body.userWebAuth.web_auth_id);

		if (getDBConnectionByPosition(db_position).updateValue(sql, values)) {
			responseMessage.set200Message();
		} else {
			responseMessage.set422Message();
		}

		dbClose();
		return new Gson().toJson(responseMessage);
	}

	// GetModelWebAuth
	public String GetModelWebAuth(Request request) {
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

		String sql = "SELECT * FROM web_auth_info, project_info where web_auth_info.project_info_id = project_info.project_info_id ";

		if (request.body.webAuthModel.web_auth_model_id != -1) {
			sql += "and web_auth_model.web_auth_model_id = " + request.body.webAuthModel.web_auth_model_id + " ";
		}

		sql += "order  by parent_id, sequence";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);

	}

	// GetUserWebAuthInUserManagement
	public String GetUserWebAuthInUserManagement(Request request) {
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
		ArrayList<Object> values = new ArrayList<Object>();
		String sql = "SELECT * FROM web_auth, web_auth_info where web_auth.web_auth_info_id = web_auth_info.web_auth_info_id ";

		if (request.body.user.user_id != -1) {
			sql += "and web_auth.user_id = ? ";
			values.add(request.body.user.user_id);
		}

		sql += "order by parent_id, sequence";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);

	}
}
