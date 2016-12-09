package com.chinadrtv.taobao.common.util.exception;

public class CustBizException extends RuntimeException {

    /** VersionId  */
    private static final long serialVersionUID = -623479705840419636L;
    
    /**返回码*/
    private String errorCode;
    /**信息*/
    private String errorMessage;
    
    /**
     * 构造函数
     */
    public CustBizException() {
        super();
    }

    /**
     * 构造函数
     * @param errorCode
     */
    public CustBizException(String errorCode) {
        super(errorCode);
    }

    /**
     * 构造函数
     * @param cause
     */
    public CustBizException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errorCode
     * @param cause
     */
    public CustBizException(String errorCode, Throwable cause) {
        super(errorCode);
    }

    /**
     * 构造函数
     * @param errorCode
     * @param message
     */
    public CustBizException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 构造函数
     * @param errorCode
     * @param message
     * @param cause
     */
    public CustBizException(String errorCode, String message, Throwable cause) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     * 
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     * 
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorMessage</tt>.
     * 
     * @return property value of errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter method for property <tt>errorMessage</tt>.
     * 
     * @param errorMessage value to be assigned to property errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

