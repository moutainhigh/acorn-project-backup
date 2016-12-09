package com.chinadrtv.erp.oms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 资源文件映射类
 *  
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
	
	@Value("${TAOBAOJ_SOURCE_URL}")
	private String taobaojUrl;
	@Value("${TAOBAOCJ_SOURCE_URL}")
	private String taobaocjUrl;
	@Value("${QQPP_SOURCE_URL}")
	private String qqppUrl;
	@Value("${HJXF_SOURCE_URL}")
	private String hjxfUrl;
	@Value("${HJXYX_SOURCE_URL}")
	private String hjxyxUrl;
	@Value("${HJXGM_SOURCE_URL}")
	private String hjqgmUrl;
	
	
	
	
	@Value("${JSON_SOURCE_URL}")
	private String jsonSourceUrl;
    @Value("${APPROVAL_INTERFACE}")
	private int approvalInterface;
	@Value("${CHAMA_AUTO_APPROVAL_COUNT}")
	private String chamsAutoApprovalCount; //茶马一次自动审核订单的数量
	
	
	@Value("${SETTLE}")
	private String settle;
	@Value("${TC_QUERY_WAYBILL_URL}")
	private String queryWaybill;
	@Value("${TC_RESEND_WAYBILL_URL}")
	private String resendWaybill;
    @Value("${TC_DELIVER_IDUP_URL}")
    private String deliverIdupUrl;
    
    @Value("${SALES_HOME}")
    private String salseHome;

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

	public String getSettle() {
		return settle;
	}

	public void setSettle(String settle) {
		this.settle = settle;
	}

	public String getJsonSourceUrl() {
		return jsonSourceUrl;
	}

	public void setJsonSourceUrl(String jsonSourceUrl) {
		this.jsonSourceUrl = jsonSourceUrl;
	}
	
	public String getQueryWaybill() {
		return queryWaybill;
	}

	public void setQueryWaybill(String queryWaybill) {
		this.queryWaybill = queryWaybill;
	}

	public String getResendWaybill() {
		return resendWaybill;
	}

	public void setResendWaybill(String resendWaybill) {
		this.resendWaybill = resendWaybill;
	}

    public String getDeliverIdupUrl() {
        return deliverIdupUrl;
    }

    public void setDeliverIdupUrl(String deliverIdupUrl) {
        this.deliverIdupUrl = deliverIdupUrl;
    }

	public String getChamsAutoApprovalCount() {
		return chamsAutoApprovalCount;
	}

	public void setChamsAutoApprovalCount(String chamsAutoApprovalCount) {
		this.chamsAutoApprovalCount = chamsAutoApprovalCount;
	}

	public int getApprovalInterface() {
		return approvalInterface;
	}

	public void setApprovalInterface(int approvalInterface) {
		this.approvalInterface = approvalInterface;
	}

	public String getTaobaojUrl() {
		return taobaojUrl;
	}

	public void setTaobaojUrl(String taobaojUrl) {
		this.taobaojUrl = taobaojUrl;
	}

	public String getTaobaocjUrl() {
		return taobaocjUrl;
	}

	public void setTaobaocjUrl(String taobaocjUrl) {
		this.taobaocjUrl = taobaocjUrl;
	}

	public String getQqppUrl() {
		return qqppUrl;
	}

	public void setQqppUrl(String qqppUrl) {
		this.qqppUrl = qqppUrl;
	}

	public String getHjxfUrl() {
		return hjxfUrl;
	}

	public void setHjxfUrl(String hjxfUrl) {
		this.hjxfUrl = hjxfUrl;
	}

	public String getHjxyxUrl() {
		return hjxyxUrl;
	}

	public void setHjxyxUrl(String hjxyxUrl) {
		this.hjxyxUrl = hjxyxUrl;
	}

	public String getHjqgmUrl() {
		return hjqgmUrl;
	}

	public void setHjqgmUrl(String hjqgmUrl) {
		this.hjqgmUrl = hjqgmUrl;
	}

	public String getSalseHome() {
		return salseHome;
	}

	public void setSalseHome(String salseHome) {
		this.salseHome = salseHome;
	}
	
}
