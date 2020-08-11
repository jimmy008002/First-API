package helper;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;

import model.MMSBody;

import javax.ws.rs.GET;


//1.    Send the photo to access you for ref
//http://m.accessyou-api.com/mms/upload_img.php?accountno=1103409&user=apptech.34069.mms&pwd=asdf7841fs&imgfile=12345
//&img_url=http://119.81.130.181/towngas/test.jpg
//2.    Send the MMS with ref
//http://m.accessyou-api.com/mms/sendmms.php?user=apptech.34069.mms&pwd=asdf7841fs&subject=hello&msg=testing&phone=85292961617
//&accountno=11034069&imgfile=123456

public class MMSHelper extends AppTech {
    private final OkHttpClient httpClient = new OkHttpClient();
    int db_position = addDBConnection("project_db", "towngas");

    public boolean SendMMSFile(String fileName, String fileType) throws IOException {

        //  must use 80 port to get the img
        //  file must be under 200KB
        //  file must be in jpg and png format
        //  fileName = "test.jpg"; // for tesing only
        String url = "http://119.81.130.181/towngas/" + fileName + "." + fileType; // using the testing server
        HttpUrl httpUrl = new HttpUrl.Builder().scheme("http").host("m.accessyou-api.com").addPathSegment("mms").addPathSegment("upload_img.php")
                .addQueryParameter("accountno", "11034069")  // add request headers
                .addQueryParameter("user", "apptech.34069.mms")
                .addQueryParameter("pwd", "asdf7841fs")
                .addQueryParameter("imgfile", fileName) //use timestamp
                .addQueryParameter("img_url", url).build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        System.out.println(request);
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("MMS error: " + response);
                return false;
            } else {
                System.out.println("MMS success: " + response);
                String sql = "INSERT INTO upload_mms_file_ref (upload_mms_file_ref_name) VALUES ( ? ) ";
                ArrayList<Object> values = new ArrayList<Object>();
                values.clear();
                values.add(fileName);
                getDBConnectionByPosition(db_position).insertValue(sql, values);
                dbClose();
                return true;
            }
        }

    }

    public boolean SendMMS(MMSBody mmsBody) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder().scheme("http").host("m.accessyou-api.com").addPathSegment("mms").addPathSegment("sendmms.php")
                .addQueryParameter("user", "apptech.34069.mms")  // add request headers
                .addQueryParameter("pwd", "asdf7841fs")
                .addQueryParameter("subject", mmsBody.subject)
                .addQueryParameter("msg", mmsBody.msg)
                .addQueryParameter("phone", mmsBody.phone) //must have zone number for phone number
                .addQueryParameter("accountno", "11034069")
                .addQueryParameter("imgfile", mmsBody.imgfile).build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        System.out.println(request);
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("error: " + response);
                return false;
            } else {
                System.out.println("MMS success: " + response);
                return true;
            }
        }

    }
}

