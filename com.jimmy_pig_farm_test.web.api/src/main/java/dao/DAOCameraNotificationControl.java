package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import helper.DateTimeHelper;
import model.Request;

import java.util.ArrayList;
import java.util.Map;

public class DAOCameraNotificationControl extends AppTech {
    int db_position = addDBConnection("project_db", "towngas");

    public String GetCameraNotificationControl(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        String sql = "select *  from camera_notification_control ";
     /*           "admin_user on camera_notification_control.admin_user_id = admin_user.admin_user_id ";
        if (request.body.cameraNotificationControl.admin_user_id != -1) {
            sql += "and admin_user.admin_user_id = ? ";
            values.add(request.body.cameraNotificationControl.admin_user_id);
        }
        if (request.body.cameraNotificationControl.camera_config_id != -1) {
            sql += "and camera_notification_control.camera_config_id = ?";
            values.add(request.body.cameraNotificationControl.camera_config_id);
        }
*/
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String InsertCameraNotificationControl(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        String sql = "";
        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        sql = "delete from camera_notification_control where admin_user_id = ?";
        values.add(request.body.cameraNotificationControl.admin_user_id);

        for (int i = 0; i < request.body.cameraNotificationControl.camera_notification_control_methods.size(); i++) {
            sql = "INSERT INTO camera_notification_control (admin_user_id, " +
                    " camera_config_id, camera_notification_control_method, update_admin_user_id) VALUES (?, ?, ?, ?) ";
            values.clear();
            values.add(request.body.cameraNotificationControl.admin_user_id);
            values.add(request.body.cameraNotificationControl.camera_config_id);
            values.add(request.body.cameraNotificationControl.camera_notification_control_methods.get(i));
            values.add(request.body.cameraNotificationControl.update_admin_user_id);
            getDBConnectionByPosition(db_position).insertValue(sql, values);
        }
        sql = "select * from camera_notification_control where admin_user_id = ? ";
        values.clear();
        values.add(request.body.cameraNotificationControl.admin_user_id);
        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);
        responseMessage.setBody(rows);
        responseMessage.set200Message();
        dbClose();
        return new Gson().toJson(responseMessage);
    }

       public String DeleteCameraNotificationControl(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String sql = "UPDATE admin_user SET admin_user_status = ? , update_datetime = now() , update_admin_user_id = ?  WHERE admin_user_id = ";
        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        values.add("Inactive");
        values.add(request.body.adminUser.update_admin_user_id);
        values.add(request.body.adminUser.admin_user_id);
        if (getDBConnectionByPosition(db_position).insertValue(sql, values)) {
            responseMessage.set200Message();
        } else {
            responseMessage.set400Message();
        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }
}
