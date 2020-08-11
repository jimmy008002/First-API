package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import model.File;
import model.MMSBody;
import model.Request;
import helper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

import helper.MMSHelper;

public class DAOMMSRecord extends AppTech {
    int db_position = addDBConnection("project_db", "towngas");
    MMSHelper mmsHelper = new MMSHelper();
    SMSHelper smsHelper = new SMSHelper();
    // GetadvContent
    public String GetMMSRecord(Request request) {

        ArrayList<Object> values = new ArrayList<Object>();
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "SELECT * FROM mms_record order by mms_record_id desc limit ? ";

        values.clear();
        values.add(request.header.return_size);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(rows.size());
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String InsertMMSRecord(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
//        int authRes = new JWTHelper().authToken(request.header.token);
//        if (authRes == -401) {
//            responseMessage.set401Message();
//            return new Gson().toJson(responseMessage);
//        }
//
//        if (authRes == -409) {
//            responseMessage.set409Message();
//            return new Gson().toJson(responseMessage);
//        }
        ArrayList<Object> values = new ArrayList<Object>();
        String sql = "";
        sql = "select * from admin_user where 1=1 and admin_user_account = ? ";
        values.add(request.body.adminUser.admin_user_account);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

        //send Email logic

        //Send MMS logic

        if (rows.size() != 0) {
            responseMessage.set413Message("Duplicate User name");
            return new Gson().toJson(responseMessage);
        }
        sql = "INSERT INTO admin_user (admin_user_account, admin_user_password) VALUES (?, ?) ";
        values.clear();
        values.add(request.body.adminUser.admin_user_account);
        values.add(request.body.adminUser.admin_user_password);

        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(rows.size());
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String MMSFileUpload(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        responseMessage.set200Message();
        ArrayList<Object> values = new ArrayList<Object>();
        boolean haveEmail = false;
        boolean haveMobile = false;
        String sql = "select now()";
        values.clear();
        ArrayList<Map<String, Object>> timeResult = getDBConnectionByPosition(db_position).getTable(sql, values);
        String fileName = timeResult.get(0).get("now()").toString().replace(" ", "").replace(":", "").replace("-", "").replace(".0", "");
        sql = "select * from send_pool";
        values.clear();
        ArrayList<Map<String, Object>> sendPoolResult = getDBConnectionByPosition(db_position).getTable(sql, values);
        if (sendPoolResult.size() != 0) {
            for (int i = 0; i < sendPoolResult.size(); i++) {
                if (sendPoolResult.get(i).get("send_pool_method").equals("1") || sendPoolResult.get(i).get("send_pool_method").equals("3")) {
                    haveMobile = true;
                    break;
                }
            }
            for (int i = 0; i < sendPoolResult.size(); i++) {
                if (sendPoolResult.get(i).get("send_pool_method").equals("2") || sendPoolResult.get(i).get("send_pool_method").equals("3")) {
                    haveEmail = true;
                    break;
                }
            }
        }
        if (haveMobile) {
            if (new FileHelper().fileEncoder("/var/www/html/towngas/", fileName + "." + request.body.file_type, request.body.file_encoded)) {
                sql = "INSERT INTO file (file_name, file_type) VALUES ( ?,? )";
                values.clear();
                values.add(fileName);
                values.add(request.body.file_type);
                getDBConnectionByPosition(db_position).insertValue(sql, values);
                try {
                    if (mmsHelper.SendMMSFile(fileName, request.body.file_type)) {
                        for (int i = 0; i < sendPoolResult.size(); i++) {
                            if (sendPoolResult.get(i).get("send_pool_method").equals("1") || sendPoolResult.get(i).get("send_pool_method").equals("3")) {
                                MMSBody mmsBody = new MMSBody();
                                mmsBody.setMMSBody();
                                mmsBody.setImgfile(fileName);
                                mmsBody.setPhone(sendPoolResult.get(i).get("send_pool_phone_number").toString());
                                System.out.println(new Gson().toJson(mmsBody));
                                if (mmsHelper.SendMMS(mmsBody)) {
                                    sql = "INSERT INTO mms_record (mms_record_phone, mms_record_subject, mms_record_msg, mms_record_img_url, mms_record_dnc)" +
                                            " VALUES (? , ? , ? , ? , ?) ; ";
                                    values = new ArrayList<Object>();
                                    values.clear();
                                    values.add(mmsBody.phone);
                                    values.add(mmsBody.subject);
                                    values.add(mmsBody.msg);
                                    values.add(mmsBody.imgfile);
                                    values.add("");
                                    getDBConnectionByPosition(db_position).insertValue(sql, values);
                                }
                                ;
                            }
                        }
                    } else {
                        responseMessage.set400Message();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (haveEmail) {

        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String SMSFileUpload(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        responseMessage.set200Message();
        ArrayList<Object> values = new ArrayList<Object>();
        boolean haveEmail = false;
        boolean haveMobile = false;
        String sql = "select * from send_pool";
        values.clear();
        ArrayList<Map<String, Object>> sendPoolResult = getDBConnectionByPosition(db_position).getTable(sql, values);
        if (sendPoolResult.size() != 0) {
            for (int i = 0; i < sendPoolResult.size(); i++) {
                if (sendPoolResult.get(i).get("send_pool_method").equals("1") || sendPoolResult.get(i).get("send_pool_method").equals("3")) {
                    haveMobile = true;
                    break;
                }
            }
            for (int i = 0; i < sendPoolResult.size(); i++) {
                if (sendPoolResult.get(i).get("send_pool_method").equals("2") || sendPoolResult.get(i).get("send_pool_method").equals("3")) {
                    haveEmail = true;
                    break;
                }
            }
        }
        if (haveMobile) {

        }
        if (haveEmail) {

        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String SendEmail(Request request) {

        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();
        SMTPHelper smtpHelper = new SMTPHelper();
        smtpHelper.SendEmail("hihi");
        responseMessage.set200Message();

        dbClose();
        return new Gson().toJson(responseMessage);
    }

}
