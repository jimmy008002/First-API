package dao;

import com.google.gson.Gson;
import dto.DTOResponseMessage;
import helper.AppTech;
import helper.DateTimeHelper;
import helper.FileHelper;
import model.Request;

import java.util.ArrayList;
import java.util.Map;

public class DAOAdminUser extends AppTech {
    int db_position = addDBConnection("project_db", "pig_farm");

    public String GetAdminUser(Request request) {
        ArrayList<Object> values = new ArrayList<Object>();

        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "select * from admin_user_list where 1=1 and admin_user_account_name = ? ";
        values.clear();
        values.add(request.body.adminUser.admin_user_account);

        ArrayList<Map<String, Object>> rows = getDBConnectionByPosition(db_position).getTable(sql, values);

        if (rows.size() == 0) {
            responseMessage.set406Message();
            return new Gson().toJson(responseMessage);
        }

        if (!rows.get(0).get("admin_user_password").equals(request.body.adminUser.admin_user_password)) {
            responseMessage.set405Message();
            return new Gson().toJson(responseMessage);
        }

        responseMessage.set200Message();
        responseMessage.setBody(rows);
        dbClose();
        return new Gson().toJson(responseMessage);
    }


    public String InsertAdminUser(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        ArrayList<Object> values = new ArrayList<Object>();

        String sql = "INSERT INTO admin_user_list (admin_user_id, admin_user_account_name, admin_user_password, role_id, update_admin_user_id)" +
                " VALUES (? , ?, ?, ? , ?)";



        values.clear();
        values.add(request.body.adminUser.admin_user_id);
        values.add(request.body.adminUser.admin_user_account);
        values.add(request.body.adminUser.admin_user_password);
        values.add(request.body.adminUser.role_id);
        values.add(request.body.adminUser.update_admin_user_id);

        getDBConnectionByPosition(db_position).insertValue(sql, values);
        responseMessage.set200Message();
        responseMessage.setBody(0);
        dbClose();
        return new Gson().toJson(responseMessage);
    }


    public String UpdateAdminUser(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "UPDATE admin_user_list SET  admin_user_password = ? WHERE admin_user_id = ?  " ;

        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        values.add(request.body.adminUser.admin_user_password);
        values.add(request.body.adminUser.admin_user_id);


        if (getDBConnectionByPosition(db_position).insertValue(sql, values)) {
            responseMessage.set200Message();
        } else {
            responseMessage.set400Message();
        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }

    public String DeleteAdminUser(Request request) {
        DTOResponseMessage responseMessage = new DTOResponseMessage();

        String sql = "DELETE FROM admin_user_list where (admin_user_id = ? and admin_user_password = ? and role_id != '1')";
        ArrayList<Object> values = new ArrayList<Object>();
        values.clear();
        values.add(request.body.adminUser.admin_user_id);
        values.add(request.body.adminUser.admin_user_password);

        if (getDBConnectionByPosition(db_position).insertValue(sql, values)) {
            responseMessage.set200Message();
        } else {
            responseMessage.set400Message();
        }
        dbClose();
        return new Gson().toJson(responseMessage);
    }
}
