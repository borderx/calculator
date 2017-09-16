package com.borderx.calculate;

/**
 * Created by hh on 2017/3/7.
 */
public enum CalculateType {
    Arithmetic (0, "Arithmetic", ""),
    SimpleConfig(1, "SimpleConfig", "")
    ;


    private int type;
    private String code;
    private String msg;

    CalculateType(int type, String code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
