package com.bawei.shaopenglai.bean;


/**
 *
 * @author Peng
 * @time 2018/12/29 15:10
 */


public class RegisterBean {


    private String message;
    private String status;
    private final String SUCCESS_CODE="0000";
    public boolean isSuceess(){
        return status.equals(SUCCESS_CODE);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
