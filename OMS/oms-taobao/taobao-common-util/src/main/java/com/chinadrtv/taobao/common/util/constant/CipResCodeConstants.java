package com.chinadrtv.taobao.common.util.constant;

public enum CipResCodeConstants {

    //002000~002030 为系统公共异常
    SYS_SUCCESS("000000", "成功"),
    SYS_PARAM_NOT_NULL("002001", "必要参数不能为空"), 
    SYS_PARAM_NOT_RIGHT("002002", "入参数值不合法"), 
    SYS_QUERY_DATA_EXCEPTION("002003","查询数据异常"),
    SYS_UPDATE_DATA_FAIL("002004", "更新数据失败"),

    RUNTIME_EXCEPTION("002095", "运行时异常"),
    SYSTEM_TIMEOUT("002096", "系统超时"),
    DB_EXCEPTION("002097", "数据库异常"),
    SERVICE_EXCEPTION("002098", "调用服务异常"),
    SYSTEM_ERROR("002099", "系统错误");

    private final String code;
    private final String msg;

    private CipResCodeConstants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    private CipResCodeConstants(String code, String msg, String detailedDesc) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return Returns the key.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the msg.
     */    
    public String getMsg(){
        return msg;
    }
    
    /**
     * 
     * 
     * @param detailedDesc
     * @return
     */
    public String getMsg(String detailedDesc) {
        return msg + ":" + detailedDesc;
    }

    public static CipResCodeConstants getByKey(String code) {
        if (code == null)
            return null;
        code = code.trim();
        for (CipResCodeConstants value : values()) {
            if (value.getCode().equals(code))
                return value;
        }
        return null;
    }

}
