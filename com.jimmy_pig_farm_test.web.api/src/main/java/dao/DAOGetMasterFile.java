package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import model.Request;

import java.util.ArrayList;
import java.util.Map;

public class DAOGetMasterFile extends AppTech {
    int db_position = addDBConnection("project_db", "pig_farm");

    public String GetMasterFile(Request request) {

        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "select farm_chinese_name, farm_english_name FROM master_file WHERE (1=1 and farm_id = ?)";

        values.clear();
        values.add(request.body.masterFile.farm_id);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String newGetMasterFile(Request request) {

        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "select farm_id, admin_update_time FROM master_file ";

        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }


    public String Insertmaster(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();

        String sql = "INSERT INTO master_file (farm_id, farm_chinese_name, farm_english_name, admin_user_id) " +
                "VALUES (? , ?, ?, 2)";

values.clear();
        values.add(request.body.masterFile.farm_id);
        values.add(request.body.masterFile.farm_chinese_name);
        values.add(request.body.masterFile.farm_english_name);

        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(0);
        dbClose();
        return new Gson().toJson(responseMessage);
    }



    public String newInsertmaster(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();

        String sql = "INSERT INTO master_file (farm_id, admin_update_time) " +
                "VALUES (?,?)";
        values.clear();
        values.add(request.body.masterFile.farm_id);


        values.add(request.body.masterFile.admin_update_time);


        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(0);
        dbClose();
        return new Gson().toJson(responseMessage);
    }




    public String Updatemaster(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();
        String sql = "";
        sql = "UPDATE master_file SET farm_chinese_name = ?, farm_english_name = ? WHERE (farm_id = ? );\n";
        values.clear();
        values.add(request.body.masterFile.farm_chinese_name);
        values.add(request.body.masterFile.farm_english_name);
        values.add(request.body.masterFile.farm_id);



        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String Deletemaster(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();
        String sql = "";

        sql = "DELETE FROM master_file WHERE (farm_id = ? and farm_chinese_name = ? and farm_english_name = ?) ";
        values.add(request.body.masterFile.farm_id);
        values.add(request.body.masterFile.farm_chinese_name);
        values.add(request.body.masterFile.farm_english_name);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }

}
