package model;

public class DBInfo {
	
	private String db_name;
	private String ip;
	private String port;
	private String username;
	private String password;
	
	public DBInfo(String db_name, String ip, String port, String username, String password) {
		this.db_name = db_name;
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getDb_name() {
		return db_name;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}