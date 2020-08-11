package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import helper.DateTimeHelper;
import helper.FileHelper;
import model.Request;

import java.util.ArrayList;
import java.util.Map;
public class DAOdetect extends AppTech{

    int db_position = addDBConnection("project_db", "pig_farm");

    public String Getdectect(Request request) {
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

}