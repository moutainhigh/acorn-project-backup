package com.chinadrtv.oms.suning.dto;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2014-11-12 下午2:15:03
 * 
 */
public class OrderConfig {
	private String url;
	private String appkey;
	private String appSecret;
	private String sessionKey;
	private String customerId;
	private String tradeType;
	private String tmsCode;
	private Integer sourceId;
	private Integer pageSize = 100;
	private Integer currentPage = 1;
	private Boolean syncStock = false;
	private Boolean splitOrder = false;

	public OrderConfig() {

	}

	public OrderConfig(String url, String appKey, String appSecret, String sessionKey, String customerId,
			String tradeType, String tmsCode, Integer sourceId) {
		this.url = url;
		this.appkey = appKey;
		this.appSecret = appSecret;
		this.sessionKey = sessionKey;
		this.customerId = customerId;
		this.tradeType = tradeType;
		this.tmsCode = tmsCode;
		this.sourceId = sourceId;
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

	@Override
	public String toString() {
		return "TaobaoOrderConfig [url=" + url + ", appkey=" + appkey + ", appSecret=" + appSecret + ", sessionKey="
				+ sessionKey + ", customerId=" + customerId + ", tradeType=" + tradeType + ", tmsCode=" + tmsCode
				+ ", sourceId=" + sourceId + ", syncStock=" + syncStock + ", splitOrder=" + splitOrder + "]";
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
