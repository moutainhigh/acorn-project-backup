package com.chinadrtv.jingdong.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-8
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class JingdongOrderConfig {
    private String url;
    private String appkey;
    private String appSecret;
    private String sessionKey;
    private String customerId;
    private String tradeType;
    private String tmsCode;
    private String tmsName;
    private String orderState;
    private Integer page;
    private Integer pageSize ;

    public JingdongOrderConfig(){

    }

    public JingdongOrderConfig(String url, String appKey, String appSecret, String sessionKey, String customerId, String tradeType, String tmsCode,String tmsName,String orderState,Integer page,Integer pageSize){
        this.url = url;
        this.appkey = appKey ;
        this.appSecret = appSecret ;
        this.sessionKey = sessionKey ;
        this.customerId = customerId ;
        this.tradeType = tradeType ;
        this.tmsCode = tmsCode ;
        this.tmsName = tmsName;
        this.orderState = orderState;
        this.setPage(page);
        this.setPageSize(pageSize);
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTmsName() {
        return tmsName;
    }

    public void setTmsName(String tmsName) {
        this.tmsName = tmsName;
    }

	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "JingdongOrderConfig [url=" + url + ", appkey=" + appkey + ", appSecret=" + appSecret + ", sessionKey="
				+ sessionKey + ", customerId=" + customerId + ", tradeType=" + tradeType + ", tmsCode=" + tmsCode
				+ ", tmsName=" + tmsName + ", orderState=" + orderState + ", page=" + page + ", pageSize=" + pageSize
				+ "]";
	}
}
