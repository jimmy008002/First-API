package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import model.FCMRequestBody;

public class FCMHelper {
	
	public FCMHelper() {
		
	}

	
	public boolean sendNotification(String token, String title, String message) {
		boolean status = true;
        try {
            StringBuilder sb = null;
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", "key=AAAA_y57-UM:APA91bHRtH5aJq43jjE3qY1dyZsdqphFBSBtpv5Bs3nrT6oT73rLyAcc708_GFd93LiBr3tuc_HfP6aHzX24L7j8DG-m6CN5Lzj7PZ8krjmjHoWS2qOxuP7NDJO8UBxYmdF-UZ0dUK2u");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            
            FCMRequestBody requestJson = new FCMRequestBody(token, message, title);

            OutputStream os = connection.getOutputStream();
            os.write(new Gson().toJson(requestJson).getBytes("UTF-8"));
            os.close();

            InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuilder();
            String line = null;
            while((line = br.readLine() ) != null) {
                sb.append(line+"\n");
            }
            in.close();
            connection.disconnect();
            
            //string to json
            JSONObject jsonObject = new JSONObject(sb.toString());
            int success = jsonObject.getInt("success");
            int failure = jsonObject.getInt("failure");
            
            if(success == 1) {
            		status = true;
            } 
            
            if(failure == 1) {
            		status = false;
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = false;
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        } 
		
		return status;
	}
}
