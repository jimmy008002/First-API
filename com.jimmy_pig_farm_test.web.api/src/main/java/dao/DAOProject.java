package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dto.DTOResponseMessage;
import helper.AppTech;
import model.Request;

public class DAOProject extends AppTech{
	
	//GetProjectStatus
	public DTOResponseMessage GetProjectStatus(Request request){
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		responseMessage.set401Message();
		responseMessage.setBody(""); 		
		
		int db_position = addDBConnection("project_db", "seto_common");
		
		if(db_position > -1) {
			ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("select * from project_info"
					+ " where project_info_id = " + request.body.projectInfo.project_info_id + "");
			
			if(rows.size()>0) {
				responseMessage.set200Message();
				responseMessage.setBody(rows);
			}
 	
		}
		
		dbClose();
		return responseMessage;
	}
	
	//GetProjectTheme
	public DTOResponseMessage GetProjectTheme(Request request){
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		responseMessage.set401Message();
		responseMessage.setBody(""); 		
		
		int db_position = addDBConnection("project_db", "freedy_dataalliance");
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		
		if(db_position > -1) {	
			//Get theme
			ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("select * from app_theme"
					+ " where project_info_id = " + request.body.projectInfo.project_info_id + "");
			
			if(rows.size()>0) {
				
				res.put("app_theme_id", rows.get(0).get("app_theme_id"));
				res.put("app_color", rows.get(0).get("app_color"));
				res.put("app_title", rows.get(0).get("app_title"));
				res.put("app_theme_name", rows.get(0).get("app_theme_name"));
				res.put("app_color", rows.get(0).get("app_color"));
				res.put("app_menu_image", getImageLink("common") + rows.get(0).get("app_menu_image_path") + "." + rows.get(0).get("app_menu_image_type"));
				
				//Get menu bar item
				ArrayList<Map<String, Object>> rows_1 = getDBConnectionByPosition(db_position).getTable("select menu_item_sequence, menu_item_content, menu_item_title, menu_item_type, app_menu_bar_config_id, concat('" + getImageLink("commmon") + "', menu_item_icon_path , '.', menu_item_icon_type) as app_menu_item_image from app_menu_bar_config"
						+ " where app_theme_id = " + rows.get(0).get("app_theme_id") + "");
				
				res.put("app_menu_item", rows_1);
				
			}

			
			responseMessage.set200Message();
			responseMessage.setBody(res); 	
		}
		
		dbClose();
		return responseMessage;
	}
	
	//GetUIContentOrder
	public DTOResponseMessage GetUIContentOrder(Request request){
		DTOResponseMessage responseMessage = new DTOResponseMessage();
		responseMessage.set401Message();
		responseMessage.setBody(""); 		
		
		int db_position = addDBConnection("project_db", "freedy_dataalliance");
				
		if(db_position > -1) {	
			
			//HashMap<String, UIContentTypeConfig> hm_config = (HashMap<String, UIContentTypeConfig>) getUIContentTypeConfig();
			
			ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable("select * from ui_content_order, ui_content_type"
					+ " where ui_content_order.project_info_id = " + request.body.projectInfo.project_info_id 
					+ " and ui_content_order.ui_content_type_id = ui_content_type.ui_content_type_id"
					+ " order by ui_content_order.sequence");

			responseMessage.set200Message();
			responseMessage.setBody(rows); 
				
		}
		
		dbClose();
		return responseMessage;
	}
	
}
