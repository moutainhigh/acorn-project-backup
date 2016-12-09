package com.chinadrtv.scheduler.common.integration.bean;


/**
 * 
 * @author xieen
 * @version $Id: QuartzJobStatusResponseLocal.java, v 0.1 2013-8-8 下午12:01:21 xieen Exp $
 */
public class QuartzJobStatusResponseLocal {
    
    private String QuartzJobStatusList;

    public String getQuartzJobStatusList() {
        return QuartzJobStatusList;
    }

    public void setQuartzJobStatusList(String quartzJobStatusList) {
        QuartzJobStatusList = quartzJobStatusList;
    }
    
    private String respCode;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

}
