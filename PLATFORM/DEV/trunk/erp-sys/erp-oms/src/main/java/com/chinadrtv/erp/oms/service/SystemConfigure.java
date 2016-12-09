package com.chinadrtv.erp.oms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User: liuhaidong
 * Date: 12-12-25
 */
@Component
public class SystemConfigure {


    @Value("${esb.csvPath}")
    private  String realPathDir;

    @Value("${esb.taobaoPath}")
    private  String taobaoPathDir;

    @Value("${esb.jingdongPath}")
    private  String jingdongPathDir;

    @Value("${esb.chamaPath}")
    private  String chamaPathDir;

    @Value("${esb.amazonPath}")
    private  String amazonPathDir;
    
    @Value("${esb.freebackfilePath}")
    private String freebackfilePathDir;

    @Value("${esb.backorderPath}")
    private  String backorderPathDir;

    @Value("${esb.ccPath}")
    private  String ccPathDir;
    
    @Value("${erp.report.server.url}")
    private  String reportServerUrl;

    @Value("${esb.LogisticsStatusPath}")
    private String LogisticsStatusPathDir;



    public String getRealPathDir() {
        return realPathDir;
    }

    public void setRealPathDir(String realPathDir) {
        this.realPathDir = realPathDir;
    }

    public String getTaobaoPathDir() {
        return taobaoPathDir;
    }

    public void setTaobaoPathDir(String taobaoPathDir) {
        this.taobaoPathDir = taobaoPathDir;
    }

    public String getJingdongPathDir() {
        return jingdongPathDir;
    }

    public void setJingdongPathDir(String jingdongPathDir) {
        this.jingdongPathDir = jingdongPathDir;
    }

    public String getChamaPathDir() {
        return chamaPathDir;
    }

    public void setChamaPathDir(String chamaPathDir) {
        this.chamaPathDir = chamaPathDir;
    }

    public String getAmazonPathDir() {
        return amazonPathDir;
    }

    public void setAmazonPathDir(String amazonPathDir) {
        this.amazonPathDir = amazonPathDir;
    }

	public String getFreebackfilePathDir() {
		return freebackfilePathDir;
	}

	public void setFreebackfilePathDir(String freebackfilePathDir) {
		this.freebackfilePathDir = freebackfilePathDir;
	}

    public String getBackorderPathDir() {
        return backorderPathDir;
    }

    public void setBackorderPathDir(String backorderPathDir) {
        this.backorderPathDir = backorderPathDir;
    }

    public String getCcPathDir() {
        return ccPathDir;
    }

    public void setCcPathDir(String ccPathDir) {
        this.ccPathDir = ccPathDir;
    }

	public String getReportServerUrl() {
		return reportServerUrl;
	}

	public void setReportServerUrl(String reportServerUrl) {
		this.reportServerUrl = reportServerUrl;
	}

    public String getLogisticsStatusPathDir() {
        return LogisticsStatusPathDir;
    }

    public void setLogisticsStatusPathDir(String logisticsStatusPathDir) {
        this.LogisticsStatusPathDir = logisticsStatusPathDir;
    }
    
}
