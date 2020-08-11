package bis;

import dao.DAOMMSRecord;
import model.Request;

import java.io.IOException;

public class BISMMSRecord {
    public String GetMMSRecord(Request request) {
        return new DAOMMSRecord().GetMMSRecord(request);
    }

    public String InsertMMSRecord(Request request) {
        return new DAOMMSRecord().InsertMMSRecord(request);
    }

    public String MMSFileUpload(Request request)  {
        return new DAOMMSRecord().MMSFileUpload(request);
    }
    public String SendEmail(Request request)  {
        return new DAOMMSRecord().SendEmail(request);
    }
}
