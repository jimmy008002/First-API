package bis;

import dao.DAOGetMasterFile;
import dao.DAOburn;
import model.Request;

public class BISburn {
    public String Getburn(Request request) {
        return new DAOburn().Getburn(request);
    }


    public String Insertburn(Request request) {
        return new DAOburn().Insertburn(request);
    }


    public String   Deleteburn(Request request) {
        return new DAOburn().Deleteburn(request);
    }




}
