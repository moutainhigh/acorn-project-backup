package com.chinadrtv.yhd.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-03-20
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class YhdOrderConfig {
    private String url;
    private String appkey;
    private String appSecret;
    private String sessionKey;
    private String customerId;
    private String tradeType;
    private String tmsCode;
    private Integer sourceId ;
    private String orderState;

    public YhdOrderConfig(){

    }

    public YhdOrderConfig(String url, String appKey, String appSecret, String sessionKey, String customerId, String tradeType, String tmsCode, Integer sourceId,String orderState){
        this.url = url;
        this.appkey = appKey ;
        this.appSecret = appSecret ;
        this.sessionKey = sessionKey ;
        this.customerId = customerId ;
        this.tradeType = tradeType ;
        this.tmsCode = tmsCode ;
        this.orderState = orderState;
        this.setSourceId(sourceId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
