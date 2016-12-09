package com.chinadrtv.erp.task.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 资源文件映射类
 * @author haoleitao
 * @date 2013-1-30 上午11:20:07
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Component
public class SourceConfigure {

	@Value("${TAOBAO_SOURCE_URL}")
	private String taobaoUrl;
	@Value("${TAOBAOC_SOURCE_URL}")
	private String taobaocUrl;
	@Value("${TAOBAOZ_SOURCE_URL}")
	private String taobaozUrl;
	@Value("${BUY360_SOURCE_URL}")
	private String buy360Url;
	@Value("${BUY360FBP_SOURCE_URL}")
	private String buy360fbpUrl;
	@Value("${AMAZON_SOURCE_URL}")
	private String amazonUrl;
	@Value("${AMAZONFBP_SOURCE_URL}")
	private String amazonfbpUrl;
	@Value("${CSV_SOURCE_URL}")
	private String csvUrl;
	@Value("${CHAMA_SOURCE_URL}")
	private String chamaurl;
	
	@Value("${JSON_SOURCE_URL}")
	private String jsonSourceUrl;
    @Value("${APPROVAL_INTERFACE}")
	private int approvalInterface;
	@Value("${CHAMA_AUTO_APPROVAL_COUNT}")
	private String chamsAutoApprovalCount; //茶马一次自动审核订单的数量
	public String getTaobaoUrl() {
		return taobaoUrl;
	}
	public void setTaobaoUrl(String taobaoUrl) {
		this.taobaoUrl = taobaoUrl;
	}
	public String getTaobaocUrl() {
		return taobaocUrl;
	}
	public void setTaobaocUrl(String taobaocUrl) {
		this.taobaocUrl = taobaocUrl;
	}
	public String getTaobaozUrl() {
		return taobaozUrl;
	}
	public void setTaobaozUrl(String taobaozUrl) {
		this.taobaozUrl = taobaozUrl;
	}
	public String getBuy360Url() {
		return buy360Url;
	}
	public void setBuy360Url(String buy360Url) {
		this.buy360Url = buy360Url;
	}
	public String getBuy360fbpUrl() {
		return buy360fbpUrl;
	}
	public void setBuy360fbpUrl(String buy360fbpUrl) {
		this.buy360fbpUrl = buy360fbpUrl;
	}
	public String getAmazonUrl() {
		return amazonUrl;
	}
	public void setAmazonUrl(String amazonUrl) {
		this.amazonUrl = amazonUrl;
	}
	public String getAmazonfbpUrl() {
		return amazonfbpUrl;
	}
	public void setAmazonfbpUrl(String amazonfbpUrl) {
		this.amazonfbpUrl = amazonfbpUrl;
	}
	public String getCsvUrl() {
		return csvUrl;
	}
	public void setCsvUrl(String csvUrl) {
		this.csvUrl = csvUrl;
	}
	public String getChamaurl() {
		return chamaurl;
	}
	public void setChamaurl(String chamaurl) {
		this.chamaurl = chamaurl;
	}
	public String getJsonSourceUrl() {
		return jsonSourceUrl;
	}
	public void setJsonSourceUrl(String jsonSourceUrl) {
		this.jsonSourceUrl = jsonSourceUrl;
	}
	public int getApprovalInterface() {
		return approvalInterface;
	}
	public void setApprovalInterface(int approvalInterface) {
		this.approvalInterface = approvalInterface;
	}
	public String getChamsAutoApprovalCount() {
		return chamsAutoApprovalCount;
	}
	public void setChamsAutoApprovalCount(String chamsAutoApprovalCount) {
		this.chamsAutoApprovalCount = chamsAutoApprovalCount;
	}
	
}
