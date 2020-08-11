package model;

public class MMSBody {
    public String user;
    public String pwd;
    public String subject;
    public String msg;
    public String phone;
    public String accountno;
    public String imgfile;

    public void setMMSBody() {
        msg = "msg";
        subject = "subject";
        imgfile = "";
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}


