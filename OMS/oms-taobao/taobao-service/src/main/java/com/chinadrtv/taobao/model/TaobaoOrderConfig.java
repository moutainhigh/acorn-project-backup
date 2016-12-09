package com.chinadrtv.taobao.model;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-1-29
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class TaobaoOrderConfig {
    private String url;
    private String appkey;
    private String appSecret;
    private String sessionKey;
    private String customerId;
    private String tradeType;
    private String tmsCode;
    private Integer sourceId ;
    //库存同步
    private Boolean syncStock = false;
    //拆单
    private Boolean splitOrder = false;

    public TaobaoOrderConfig(){

    }

    public TaobaoOrderConfig(String url, String appKey, String appSecret, String sessionKey, 
    		String customerId, String tradeType, String tmsCode, Integer sourceId, Boolean syncStock, Boolean splitOrder){
        this.url = url;
        this.appkey = appKey ;
        this.appSecret = appSecret ;
        this.sessionKey = sessionKey;
        this.customerId = customerId;
        this.tradeType = tradeType ;
        this.tmsCode = tmsCode ;
        this.setSourceId(sourceId);
        this.syncStock = syncStock;
        this.splitOrder = splitOrder;
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

	public Boolean getSyncStock() {
		return syncStock;
	}

	public void setSyncStock(Boolean syncStock) {
		this.syncStock = syncStock;
	}

	public Boolean getSplitOrder() {
		return splitOrder;
	}

	public void setSplitOrder(Boolean splitOrder) {
		this.splitOrder = splitOrder;
	}

	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "TaobaoOrderConfig [url=" + url + ", appkey=" + appkey + ", appSecret=" + appSecret + ", sessionKey="
				+ sessionKey + ", customerId=" + customerId + ", tradeType=" + tradeType + ", tmsCode=" + tmsCode
				+ ", sourceId=" + sourceId + ", syncStock=" + syncStock + ", splitOrder=" + splitOrder + "]";
	}
}
