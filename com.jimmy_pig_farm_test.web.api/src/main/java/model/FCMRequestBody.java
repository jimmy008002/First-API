package model;

public class FCMRequestBody {
	public String to;
	public FCMRequestBodyNotification data;
	
	public FCMRequestBody(String to, String body, String title) {
		this.to = to;
		data = new FCMRequestBodyNotification(body, title);
	}
	 
}
