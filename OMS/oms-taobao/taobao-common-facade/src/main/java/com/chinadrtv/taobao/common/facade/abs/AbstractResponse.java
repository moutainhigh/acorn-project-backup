package com.chinadrtv.taobao.common.facade.abs;

import java.io.Serializable;

public abstract class AbstractResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 8813371229893625759L;

    /** 系统返回码 */
    private String            respCode;
    
    /**结果码描述*/
    private String         memo;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    /**
     * Getter method for property <tt>memo</tt>.
     * 
     * @return property value of memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Setter method for property <tt>memo</tt>.
     * 
     * @param memo value to be assigned to property memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

}
