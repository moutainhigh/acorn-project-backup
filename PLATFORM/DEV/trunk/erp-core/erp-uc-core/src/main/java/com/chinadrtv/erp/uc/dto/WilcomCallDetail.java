package com.chinadrtv.erp.uc.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public class WilcomCallDetail implements java.io.Serializable {
    /* 数据库唯一标识(自增长) */
    private Long id;
    private Long isselect;
    private String caseid;
    private Date createtime;
    private Date endtime;
    private String  ani;
    private String dnis;
    private String callgrp;
    private String assign;
    private String calllen;
    private String calltype;
    private String usrid;
    private String mediaproduct;
    private String area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsselect() {
        return isselect;
    }

    public void setIsselect(Long isselect) {
        this.isselect = isselect;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public String getCallgrp() {
        return callgrp;
    }

    public void setCallgrp(String callgrp) {
        this.callgrp = callgrp;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getCalllen() {
        return calllen;
    }

    public void setCalllen(String calllen) {
        this.calllen = calllen;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getMediaproduct() {
        return mediaproduct;
    }

    public void setMediaproduct(String mediaproduct) {
        this.mediaproduct = mediaproduct;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
