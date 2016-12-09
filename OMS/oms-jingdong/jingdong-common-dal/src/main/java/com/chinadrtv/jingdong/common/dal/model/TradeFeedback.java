package com.chinadrtv.jingdong.common.dal.model;

public class TradeFeedback implements java.io.Serializable {
    private String tradeId;
    private String companyCode;
    private String mailId ;
    private String orderId;

    public TradeFeedback(){

    }

    public TradeFeedback(String tradeId, String mapCode, String mailId, String orderId){
        this.tradeId = tradeId ;
        this.mailId = mailId ;
        this.companyCode = mapCode ;
        this.orderId = orderId ;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
