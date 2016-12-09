package com.chinadrtv.gonghang.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 2014-04-22
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class OrderConfig {
    private String url;
    private String appKey;
    private String appSecret;
    private String accessToken;
    private String customerId;
    private String tradeType;
    private String tmsCode;
    private Long sourceId ;
    private String tradeFrom;

    public OrderConfig(){}

    public OrderConfig(String url, String appKey, String appSecret, String accessToken, String customerId, String tradeType, String tmsCode, Long sourceId){
        this.url = url;
        this.appKey = appKey ;
        this.appSecret = appSecret ;
        this.accessToken = accessToken ;
        this.customerId = customerId ;
        this.tradeType = tradeType ;
        this.tmsCode = tmsCode ;
        this.setSourceId(sourceId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}
}
