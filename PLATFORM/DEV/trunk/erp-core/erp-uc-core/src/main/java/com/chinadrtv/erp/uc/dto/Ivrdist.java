package com.chinadrtv.erp.uc.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-2-14
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public class Ivrdist implements java.io.Serializable {
    private Long ruid;
    private String usrid;
    private String grpid;
    private String caseid;
    private String recpsn;
    private Date recdat;
    private String ani;
    private String dnis;
    private Date createtime;
    private String callgrp;
    private Integer status;
    private String type;

    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getGrpid() {
        return grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getRecpsn() {
        return recpsn;
    }

    public void setRecpsn(String recpsn) {
        this.recpsn = recpsn;
    }

    public Date getRecdat() {
        return recdat;
    }

    public void setRecdat(Date recdat) {
        this.recdat = recdat;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCallgrp() {
        return callgrp;
    }

    public void setCallgrp(String callgrp) {
        this.callgrp = callgrp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
