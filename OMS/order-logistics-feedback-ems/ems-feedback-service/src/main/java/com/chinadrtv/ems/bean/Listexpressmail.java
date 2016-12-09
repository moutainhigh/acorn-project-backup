package com.chinadrtv.ems.bean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-8-30
 * Time: 下午6:08
 * To change this template use File | Settings | File Templates.
 */
public class Listexpressmail {
    private String proctime;
    private String procdate;
    private String effect;
    private String description;
    private String mailnum;
    private String orgfullname;
    private String action;
    private String serialnumber;
    private String properdelivery;
    private String notproperdelivery;

    public String getProperdelivery() {
        return properdelivery;
    }

    public void setProperdelivery(String properdelivery) {
        this.properdelivery = properdelivery;
    }

    public String getNotproperdelivery() {
        return notproperdelivery;
    }

    public void setNotproperdelivery(String notproperdelivery) {
        this.notproperdelivery = notproperdelivery;
    }

    public String getProctime() {
        return proctime;
    }

    public void setProctime(String proctime) {
        this.proctime = proctime;
    }

    public String getProcdate() {
        return procdate;
    }

    public void setProcdate(String procdate) {
        this.procdate = procdate;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMailnum() {
        return mailnum;
    }

    public void setMailnum(String mailnum) {
        this.mailnum = mailnum;
    }

    public String getOrgfullname() {
        return orgfullname;
    }

    public void setOrgfullname(String orgfullname) {
        this.orgfullname = orgfullname;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }
}
