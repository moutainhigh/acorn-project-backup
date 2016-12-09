package com.chinadrtv.erp.uc.dto;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 14-3-3
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class IvrDeptRate {
    private String deptid;
    private String ivrtype;
    private Double rate;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getIvrtype() {
        return ivrtype;
    }

    public void setIvrtype(String ivrtype) {
        this.ivrtype = ivrtype;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
