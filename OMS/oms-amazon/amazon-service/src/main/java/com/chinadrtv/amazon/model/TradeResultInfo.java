package com.chinadrtv.amazon.model;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 12-12-27
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class TradeResultInfo implements java.io.Serializable{
    private String messageId;
    private String resultStatus ;
    private String errorCode ;
    private String errorDescription;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
