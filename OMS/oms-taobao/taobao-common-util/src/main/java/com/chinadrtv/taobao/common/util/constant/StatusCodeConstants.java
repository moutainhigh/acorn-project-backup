package com.chinadrtv.taobao.common.util.constant;

public enum StatusCodeConstants {
    
    C("C", "已注销"),

    T("T", "正常"),

    U("U", "已锁定"),

    I("I", "未激活");

    private final String code;
    private final String msg;

    private StatusCodeConstants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     * 
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }
}
