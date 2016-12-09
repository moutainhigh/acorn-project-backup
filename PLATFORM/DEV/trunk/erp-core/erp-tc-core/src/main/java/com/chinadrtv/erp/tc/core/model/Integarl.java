package com.chinadrtv.erp.tc.core.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-1-28
 * Time: 下午4:09
 * p_n_useconpoint 积分服务存储过程的参数
 */
public class Integarl implements Serializable {
    private String  stype;
    private String  sorderid;
    private String  scontactid;
    private Double  npoint;
    private String  scrusr;

    public String getScrusr() {
		return scrusr;
	}

	public void setScrusr(String scrusr) {
		this.scrusr = scrusr;
	}

	public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSorderid() {
        return sorderid;
    }

    public void setSorderid(String sorderid) {
        this.sorderid = sorderid;
    }

    public String getScontactid() {
        return scontactid;
    }

    public void setScontactid(String scontactid) {
        this.scontactid = scontactid;
    }

    public Double getNpoint() {
        return npoint;
    }

    public void setNpoint(Double npoint) {
        this.npoint = npoint;
    }

  

}
