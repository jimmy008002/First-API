package bis;

import dao.DAOdetect;
import model.Request;

public class BISdetect {



    public String Getdectect(Request request) {
        return new DAOdetect().Getdectect(request);
    }




}
