package com.chinadrtv.scheduler.common.facade.code;

public enum RspCode {
    
    SUCCESS("成功", "000"), 
    REPEAT("重复交易", "001"), 
    OFFLINE("系统离线状态", "002"),
    ERROR("异常","003"),
    PARAMBUG("参数不完整","004"),
    FAILD("失败","005");

    private String value;
    
    private String chName;

    private RspCode(String chName, String value) {
        this.value = value;
        this.chName = chName;
    }

    public String getCode() {
        return value;
    }
    
    public String getName(){
        return chName;
    }

    
}
