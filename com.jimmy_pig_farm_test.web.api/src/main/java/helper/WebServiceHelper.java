package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceHelper {
	
	public String jsonRequest(String request, String endpoint) {
        StringBuilder sb = null;
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");


            OutputStream os = connection.getOutputStream();
            os.write(request.getBytes("UTF-8"));
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        if(sb != null){
            return sb.toString();
        } else {
            return null;
        }
	}
	
}
