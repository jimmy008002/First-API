package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import model.Request;

import java.util.ArrayList;
import java.util.Map;

public class DAOAR_TAG extends AppTech {
    int db_position = addDBConnection("project_db", "pig_farm");

    public String GetAR(Request request) {

        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "select * FROM AR_TAG ";


        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }




    public String InsertAR(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();

        String sql = "INSERT INTO AR_TAG (farm_id,admin_user_id,AR_tag,AR_tag_time) " +
                "VALUES (? , ?,?,?)";

        values.clear();
        values.add(request.body.ar.farm_id);
        values.add(request.body.ar.admin_user_id);
       values.add(request.body.ar.AR_tag);
        values.add(request.body.ar.AR_tag_time);

        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(0);
        dbClose();
        return new Gson().toJson(responseMessage);
    }






}
