package helper;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.json.*;

import com.google.gson.Gson;

import model.*;

public class ConfigReader {
	
	private JSONObject jsonConfig;
	
	public ConfigReader() {
		getConfig();
	}
	
	public JSONObject getJsonConfig() {
		return jsonConfig;
	}
	
	public void getConfig() {
		try {
            String contents = Files.lines(Paths.get("/opt/tomcat/apptech_server_config.json")).collect(Collectors.joining("\n"));
			jsonConfig = new JSONObject(contents);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DBInfo getDBInfo(String db_type, String server_id) {
		DBInfo dbInfo = null;
        try {
//			dbInfo = new DBInfo("towngas", "119.81.130.178", "3306","Developer", "1001AppTech");
			if(getJsonConfig() != null) {
				JSONArray jsonArray = jsonConfig.getJSONArray(db_type);

				for(int i=0;i<jsonArray.length();i++){
					if(jsonArray.getJSONObject(i).getString("server_id").equals(server_id)) {
						dbInfo = new DBInfo(jsonArray.getJSONObject(i).getString("db_name"), jsonArray.getJSONObject(i).getString("ip"), jsonArray.getJSONObject(i).getString("port"), jsonArray.getJSONObject(i).getString("username"), jsonArray.getJSONObject(i).getString("password"));
					}
				}
			}
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return dbInfo;
	}
	
	public String getApkLink() {
		String link = null;
        try {
			if(getJsonConfig() != null) {
				link = jsonConfig.getString("apk_link");
			}
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return link;
	}
	
	public String getImageLink(String server_id) {
		String link = null;
		
        try {
        	
			if(getJsonConfig() != null) {
				link = jsonConfig.getString("image_link");
			}
			
			if(getJsonConfig() != null) {
				JSONArray jsonArray = jsonConfig.getJSONArray("project_db");
				
	            for(int i=0;i<jsonArray.length();i++){
	                if(jsonArray.getJSONObject(i).getString("server_id").equals(server_id)) {
	                	link = jsonArray.getJSONObject(i).getString("image_link");
	                }
	            }
			}
			
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return link;
	}

	public HashMap<String, UIContentTypeConfig> getUIContentTypeConfig(){
		HashMap<String, UIContentTypeConfig> hm = new HashMap<String, UIContentTypeConfig>();
		
        try {
        	
        		JSONArray jsonArray = jsonConfig.getJSONArray("ui_content_type_list");
        		
	        for(int i=0;i<jsonArray.length();i++){
	        		hm.put(jsonArray.getJSONObject(i).getString("type"), new UIContentTypeConfig(jsonArray.getJSONObject(i).getString("type"), jsonArray.getJSONObject(i).getString("table"), jsonArray.getJSONObject(i).getString("table_key")));
        		}
        	
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return hm;
	}
}
