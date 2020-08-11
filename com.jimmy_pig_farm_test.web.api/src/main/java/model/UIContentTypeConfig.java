package model;

public class UIContentTypeConfig {
	public String type;
	public String table;
	public String table_key;


	public UIContentTypeConfig(String type, String table, String table_key) {
		super();
		this.type = type;
		this.table = table;
		this.table_key = table_key;
	}


	public UIContentTypeConfig() {
		super();
	}

}
