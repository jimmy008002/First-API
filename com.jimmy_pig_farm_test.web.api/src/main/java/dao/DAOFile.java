package dao;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import dto.DTOResponseMessage;
import helper.*;
import model.FileInfo;
import model.Request;

public class DAOFile extends AppTech {
    int db_position = addDBConnection("project_db", "towngas");

    // FileUpload
    public String UploadImage(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String sql = "";
        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        String currentTime = dateTimeHelper.getDateTimeSec();
        String fileName = currentTime + "." + request.body.file_type;

        if (new FileHelper().fileEncoder("/var/www/html/towngas/", fileName, request.body.file_encoded)) {
            sql = "INSERT INTO file (file_name) VALUES ( ? )";
            values.clear();
            values.add(currentTime);
            getDBConnectionByPosition(db_position).insertValue(sql, values);
            sql = "select max(file_id) as \"file_id\" from file ";
            values.clear();
            ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
            String file_id  = rows.get(0).get("file_id").toString();

            // For multiple cameras solution Jackel
            sql = "INSERT INTO capture_image_ref (file_id, camera_config_id) VALUES (?, ?)";
            values.clear();
            values.add(file_id);
            values.add(request.body.captureImageRef.camera_config_id);
            getDBConnectionByPosition(db_position).insertValue(sql , values);
            responseMessage.set200Message();
            responseMessage.setBody(file_id);
        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }

}
