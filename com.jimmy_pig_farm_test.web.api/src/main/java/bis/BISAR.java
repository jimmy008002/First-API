package bis;

import dao.DAOAR_TAG;

import model.Request;

public class BISAR {
    public String GetAR(Request request) {
        return new DAOAR_TAG().GetAR(request);
    }


    public String InsertAR(Request request) {
        return new DAOAR_TAG().InsertAR(request);
    }






}
