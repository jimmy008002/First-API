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

public class DAODashBoard extends AppTech {
	int db_position = addDBConnection("project_db", "seto_common");

	// GetTotalUsageOfCustomer
	public String GetTotalUsageOfCustomer(Request request) {
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

		String sql = "Select count(*) as \"CustomerNo\" from member ";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);
	}

	public String GetTotalNumberOfArticle(Request request) {
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

		String sql = "select count(DISTINCT article_content.article_content_name) as \"ArticleNumber\"  from article_content ";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);
	}

	public String GetCurrentMonthPaymentNumber(Request request) {
		String currentDatetime = new DateTimeHelper().getOracleCurrentTimeStamp();
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

		String sql = "select count(DISTINCT cat_title) as \"CategoryNo\"  from cat_content  ";

		ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql);

		responseMessage.set200Message();
		responseMessage.setBody(rows);

		dbClose();

		return new Gson().toJson(responseMessage);
	}

}
