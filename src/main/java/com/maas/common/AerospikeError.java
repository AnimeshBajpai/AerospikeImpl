package com.maas.common;

public enum AerospikeError {

    INVALID_CONFIG(6001, "Invalid Configurations");

    private String  msg;
    private Integer code;

    private AerospikeError(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }
}
