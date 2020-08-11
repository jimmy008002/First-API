package bis;

import dao.DAOCameraNotificationControl;
import model.Request;

public class BISCameraNotificationControl {
    public String GetCameraNotificationControl(Request request) {
        return new DAOCameraNotificationControl().GetCameraNotificationControl(request);
    }

    public String InsertCameraNotificationControl(Request request) {
        return new DAOCameraNotificationControl().InsertCameraNotificationControl(request);
    }

    public String DeleteCameraNotificationControl(Request request) {
        return new DAOCameraNotificationControl().DeleteCameraNotificationControl(request);
    }

}
