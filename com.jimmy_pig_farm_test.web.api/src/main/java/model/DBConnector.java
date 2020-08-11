package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	public final String IP = "192.168.1.171";
	public final String PORT = "1521";
	public final String SID = "orcl";
	public final String USERNAME = "C##freedy_public";
	public final String PASSWORD = "20130901";
	
	public Connection getConnection() {
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
	        return DriverManager.getConnection(  
	        		getConnectionString(),USERNAME,PASSWORD); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return null;
		}
	}
	
	public String getConnectionString() {
		return "jdbc:oracle:thin:@" + IP + ":" + PORT + ":" + SID;
	}
}
