package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import helper.DateTimeHelper;
import helper.FileHelper;
import model.Request;

import java.util.ArrayList;
import java.util.Map;
public class DAOburn extends AppTech{

    int db_position = addDBConnection("project_db", "pig_farm");

    public String Getburn(Request request) {
        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "select pig_barn_name FROM pig_barn_list where (1=1 and pig_barn_id =? )";
        values.clear();
        values.add(request.body.pigbarn.pig_barn_id);

        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);


        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }


    public String Insertburn(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();

        String sql = "INSERT INTO pig_barn_list (pig_barn_id, pig_barn_name, admin_user_id) VALUES (? , ?, ?)";

        values.clear();
        values.add(request.body.pigbarn.pig_barn_id);
        values.add(request.body.pigbarn.pig_barn_name);
        values.add(request.body.pigbarn.admin_user_id);


        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(0);
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String Deleteburn(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();
        String sql = "";

        sql = "DELETE FROM pig_barn_list WHERE (pig_barn_id = ? and pig_barn_name = ?) ";
        values.add(request.body.pigbarn.pig_barn_id);
        values.add(request.body.pigbarn.pig_barn_name);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }



}