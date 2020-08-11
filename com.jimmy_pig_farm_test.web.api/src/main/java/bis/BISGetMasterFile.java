package bis;

import dao.DAOGetMasterFile;
import dao.DAOburn;
import model.Request;

public class BISGetMasterFile {
    public String GetMasterFile(Request request) {
        return new DAOGetMasterFile().GetMasterFile(request);
    }

    public String newMasterFile(Request request) {
        return new DAOGetMasterFile().newGetMasterFile(request);
    }


    public String Insertmaster(Request request) {
        return new DAOGetMasterFile().Insertmaster(request);
    }


    public String NewInsertmaster(Request request) {
         return new DAOGetMasterFile().newInsertmaster(request);
    }

    public String Deletemaster(Request request) {
        return new DAOGetMasterFile().Deletemaster(request);
    }

    public String Updatemaster(Request request) {
        return new DAOGetMasterFile().Updatemaster(request);
    }

}
