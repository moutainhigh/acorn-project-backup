package com.chinadrtv.taobao.common.util.exception;

public class CustDaoException extends RuntimeException{

    /** versionId */
    private static final long serialVersionUID = 5757706975766148221L;

    /**返回码*/
    private String errorCode;
    /**信息*/
    private String errorMessage;
    
    /**
     * 构造函数
     */
    public CustDaoException() {
        super();
    }

    /**
     * 构造函数
     * @param errorCode
     */
    public CustDaoException(String errorCode) {
        super(errorCode);
    }

    /**
     * 构造函数
     * @param cause
     */
    public CustDaoException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errorCode
     * @param cause
     */
    public CustDaoException(String errorCode, Throwable cause) {
        super(errorCode);
    }

    /**
     * 构造函数
     * @param errorCode
     * @param message
     */
    public CustDaoException(String errorCode, String message) {
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
    public CustDaoException(String errorCode, String message, Throwable cause) {
        super(cause);
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
