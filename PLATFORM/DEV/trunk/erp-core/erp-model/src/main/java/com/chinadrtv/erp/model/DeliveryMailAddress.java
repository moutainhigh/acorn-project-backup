package com.chinadrtv.erp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-1-16
 * Time: 下午4:40
 * 邮件发送地址信息Model
 */
@Entity
@Table(name = "DELIVERY_MAIL_ADDRESS", schema = "ACOAPP_OMS")
public class DeliveryMailAddress {
    private String companyid;
    private String companyname;
    private String tomail;
    private String ccmail;
    private String ccmail1;
    private String bccmail;
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getTomail() {
        return tomail;
    }

    public void setTomail(String tomail) {
        this.tomail = tomail;
    }

    public String getCcmail() {
        return ccmail;
    }

    public void setCcmail(String ccmail) {
        this.ccmail = ccmail;
    }

    public String getCcmail1() {
        return ccmail1;
    }

    public void setCcmail1(String ccmail1) {
        this.ccmail1 = ccmail1;
    }

    public String getBccmail() {
        return bccmail;
    }

    public void setBccmail(String bccmail) {
        this.bccmail = bccmail;
    }





}
