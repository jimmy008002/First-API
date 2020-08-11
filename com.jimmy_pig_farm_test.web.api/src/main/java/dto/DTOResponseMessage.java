package dto;

public class DTOResponseMessage {
    private String status;
    private String code;
    private String desc;
    private Object body;

    public void set200Message() {
        status = "Success";
        code = "200";
        desc = "OK";
    }


    public void set204Message() {
        status = "Fail";
        code = "204";
        desc = "No Content";
    }

    public void set201Message() {
        status = "Success";
        code = "201";
        desc = "Created";
    }

    public void set304Message() {
        status = "Fail";
        code = "304";
        desc = "Not Modified";
    }

    public void set400Message() {
        status = "Fail";
        code = "400";
        desc = "Bad Request";
    }

    public void set401Message() {
        status = "Fail";
        code = "401";
        desc = "Unauthoried";
    }

    public void set403Message() {
        status = "Fail";
        code = "403";
        desc = "Forbidden";
    }

    public void set404Message() {
        status = "Fail";
        code = "404";
        desc = "Not Found";
    }

    public void set405Message() {
        status = "Fail";
        code = "405";
        desc = "Password Not Correct";
    }

    public void set406Message() {
        status = "Fail";
        code = "406";
        desc = "User Not Found";
    }

    public void set409Message() {
        status = "Fail";
        code = "409";
        desc = "Conflict";
    }

    public void set410Message() {
        status = "Fail";
        code = "410";
        desc = "Please input conditions.";
    }

    public void set411Message() {
        status = "Fail";
        code = "411";
        desc = "Please input key.";
    }

    public void set412Message() {
        status = "Fail";
        code = "412";
        desc = "Duplicate project name.";
    }

    public void set413Message(String desc_info) {
        status = "Duplicate Key";
        code = "413";
        desc = desc_info;

    }


    public void set422Message() {
        status = "Fail";
        code = "422";
        desc = "Invalid Entry";
    }

    public void set500Message() {
        status = "Fail";
        code = "500";
        desc = "Internal Server Error";
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void addBody(Object body) {
        this.body = body;
    }

}
