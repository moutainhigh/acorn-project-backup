package com.chinadrtv.erp.sales.service;

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

	//@Value("${QUERY_ALL_CUSTOMER}")
	private String queryAllCustomerUrl;

    @Value("${SALES_VERSION}")
    private String version;

    @Value("${CTI_SERVER_SHANGHAI}")
    private String ctiServerShanghai;
    @Value("${CTI_SERVER_SHANGHAI_BACK}")
    private String ctiServerShanghaiBack;
    @Value("${CTI_SERVER_BEIJING}")
    private String ctiServerBeijing;
    @Value("${CTI_SERVER_BEIJING_BACK}")
    private String ctiServerBeijingBack;
    @Value("${CTI_SERVER_WUXI}")
    private String ctiServerWuxi;
    @Value("${CTI_SERVER_WUXI_BACK}")
    private String ctiServerWuxiBack;

    @Value("${CTI_SERVER_WEBAPP}")
    private String ctiServerWebapp;

    @Value("${CTI_JRE_VERSION}")
    private String ctiJreVersion;

    @Value("${T_SERVICES}")
    private String tservices;


    @Value("${CTI_ANALOGLINES}")
    private String analoglines;

    @Value("${CUSTOMER_SERVICE_PHONE}")
    private String customerServicePhone;

    public Integer getInvoiceQueryDays() {
        return invoiceQueryDays;
    }

    public void setInvoiceQueryDays(Integer invoiceQueryDays) {
        this.invoiceQueryDays = invoiceQueryDays;
    }

    @Value("${ORDER_INVOICE_QUERY_DAYS}")
    private Integer invoiceQueryDays;



    public String getQueryAllCustomerUrl() {
		return queryAllCustomerUrl;
	}
	public void setQueryAllCustomerUrl(String queryAllCustomerUrl) {
		this.queryAllCustomerUrl = queryAllCustomerUrl;
	}

    public String getNCODPaytypes() {
        return NCODPaytypes;
    }

    public void setNCODPaytypes(String NCODPaytypes) {
        this.NCODPaytypes = NCODPaytypes;
    }

    @Value("${com.chinadrtv.erp.sales.NCODPaytypes}")
    private String NCODPaytypes;

    public Long getSearchDayDiff() {
        return searchDayDiff;
    }

    public void setSearchDayDiff(Long searchDayDiff) {
        this.searchDayDiff = searchDayDiff;
    }

    @Value("${com.chinadrtv.erp.sales.SearchDayDiff}")
    private Long searchDayDiff;




    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTestPayType() {
        return testPayType;
    }

    public void setTestPayType(String testPayType) {
        this.testPayType = testPayType;
    }

    @Value("${com.chinadrtv.erp.sales.core.merchants.paytype}")
    private String testPayType;


    public String getCtiServerShanghai() {
        return ctiServerShanghai;
    }

    public void setCtiServerShanghai(String ctiServerShanghai) {
        this.ctiServerShanghai = ctiServerShanghai;
    }

    public String getCtiServerBeijing() {
        return ctiServerBeijing;
    }

    public void setCtiServerBeijing(String ctiServerBeijing) {
        this.ctiServerBeijing = ctiServerBeijing;
    }

    public String getCtiServerWuxi() {
        return ctiServerWuxi;
    }

    public void setCtiServerWuxi(String ctiServerWuxi) {
        this.ctiServerWuxi = ctiServerWuxi;
    }

    public String getCtiServerWebapp() {
        return ctiServerWebapp;
    }

    public void setCtiServerWebapp(String ctiServerWebapp) {
        this.ctiServerWebapp = ctiServerWebapp;
    }

    public String getCtiJreVersion() {
        return ctiJreVersion;
    }

    public void setCtiJreVersion(String ctiJreVersion) {
        this.ctiJreVersion = ctiJreVersion;
    }

    public String getCtiServerShanghaiBack() {
        return ctiServerShanghaiBack;
    }

    public void setCtiServerShanghaiBack(String ctiServerShanghaiBack) {
        this.ctiServerShanghaiBack = ctiServerShanghaiBack;
    }

    public String getCtiServerBeijingBack() {
        return ctiServerBeijingBack;
    }

    public void setCtiServerBeijingBack(String ctiServerBeijingBack) {
        this.ctiServerBeijingBack = ctiServerBeijingBack;
    }

    public String getCtiServerWuxiBack() {
        return ctiServerWuxiBack;
    }

    public void setCtiServerWuxiBack(String ctiServerWuxiBack) {
        this.ctiServerWuxiBack = ctiServerWuxiBack;
    }

    public String getTservices() {
        return tservices;
    }

    public void setTservices(String tservices) {
        this.tservices = tservices;
    }

    public String getAnaloglines() {
        return analoglines;
    }

    public void setAnaloglines(String analoglines) {
        this.analoglines = analoglines;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone;
    }
}
