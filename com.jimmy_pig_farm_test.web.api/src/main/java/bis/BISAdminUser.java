package bis;

import model.Request;
import dao.DAOAdminUser;

public class BISAdminUser {
    public String GetAdminUser(Request request) {
        return new DAOAdminUser().GetAdminUser(request);
    }

    public String InsertAdminUser(Request request) {
        return new DAOAdminUser().InsertAdminUser(request);
    }
    public String UpdateAdminUser(Request request) {
        return new DAOAdminUser().UpdateAdminUser(request);
    }
    public String DeleteAdminUser(Request request) {
        return new DAOAdminUser().DeleteAdminUser(request);
    }

}
