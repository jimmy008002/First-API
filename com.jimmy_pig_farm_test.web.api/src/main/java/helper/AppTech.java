package helper;

import java.util.ArrayList;
import java.util.Map;

import model.DBInfo;

public class AppTech {
	private ConfigReader configReader;
	private ArrayList<SqlConnectionHelper> dbConnectionList;

	public AppTech() {
		configReader = new ConfigReader();
		dbConnectionList = new ArrayList<SqlConnectionHelper>();
	}
	
	public boolean deviceAuth(String db_type, String server_id, String sql) {
		boolean isSuccess = false;
		DBInfo dbInfo = configReader.getDBInfo(db_type, server_id);
		SqlConnectionHelper sqlHelper = new SqlConnectionHelper(dbInfo);
		
		ArrayList<Map<String, Object>> rows = sqlHelper.getTable(sql);
		
		if(rows.size() > 0){
			isSuccess = true;
		}    

		sqlHelper.close();
		return isSuccess;
	}
	
	public int addDBConnection(String db_type, String server_id) {

		DBInfo dbInfo = configReader.getDBInfo(db_type, server_id);
		int status = -1;
		if(dbInfo != null) {
			dbConnectionList.add(new SqlConnectionHelper(dbInfo));
			
			//return current db position
			return dbConnectionList.size() - 1;
		}
		
		//insert fail return -1
		return status;
	}
	
	public SqlConnectionHelper getDBConnectionByPosition(int position) {
		return dbConnectionList.get(position);
	}
	
	public void dbClose() {
		for (int i = 0; i < dbConnectionList.size(); i++) {
			dbConnectionList.get(i).close();
		}
	}
	
	public void dbClose(int position) {
		dbConnectionList.get(position).close();
	}
	
	public String getApkLink() {
		return configReader.getApkLink();
	}
	
	public String getImageLink(String server_id) {
		return configReader.getImageLink(server_id);
	}
	
	public Object getUIContentTypeConfig() {
		return configReader.getUIContentTypeConfig();
	}
}	

