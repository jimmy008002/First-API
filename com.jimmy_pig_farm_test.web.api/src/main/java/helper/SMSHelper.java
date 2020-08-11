package helper;

import model.SMSBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;



//1.    Send the photo to access you for ref
//http://m.accessyou-api.com/sms/sendsmsutf8.php?accountno=1103409&pwd=asdf7841fs&imgfile=12345&msg=
//&phone=&size=l

public class SMSHelper extends AppTech {
    private final OkHttpClient httpClient = new OkHttpClient();
    int db_position = addDBConnection("project_db", "towngas");
    String accountno = "";
    String pwd = "";

    public boolean SendMMS(SMSBody smsBody) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder().scheme("http").host("m.accessyou-api.com").addPathSegment("sms").addPathSegment("sendsmsutf8.php")
                .addQueryParameter("accountno", accountno)
                .addQueryParameter("pwd", pwd)
                .addQueryParameter("msg", smsBody.msg)
                .addQueryParameter("phone", smsBody.phone)
                .addQueryParameter("size", "l") //must have zone number for phone number
                .build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        System.out.println(request);
                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        System.out.println("error: " + response);
                        return false;
                    } else {
                        System.out.println("SMS success: " + response);
                return true;
            }
        }

    }
}

