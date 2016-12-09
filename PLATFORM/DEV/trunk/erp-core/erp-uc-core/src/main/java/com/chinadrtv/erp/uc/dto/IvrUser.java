package com.chinadrtv.erp.uc.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-14
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class IvrUser implements java.io.Serializable {

    private String userid;
    private String deptid;
    private String redist;
    private String distall_dx;
    private String showgrp_inb;
    private String dist_type;
    private String showgrp_type;
    private String showtype_type;
    private String disttogrp;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getRedist() {
        return redist;
    }

    public void setRedist(String redist) {
        this.redist = redist;
    }

    public String getDistall_dx() {
        return distall_dx;
    }

    public void setDistall_dx(String distall_dx) {
        this.distall_dx = distall_dx;
    }

    public String getShowgrp_inb() {
        return showgrp_inb;
    }

    public void setShowgrp_inb(String showgrp_inb) {
        this.showgrp_inb = showgrp_inb;
    }

    public String getDist_type() {
        return dist_type;
    }

    public void setDist_type(String dist_type) {
        this.dist_type = dist_type;
    }

    public String getShowgrp_type() {
        return showgrp_type;
    }

    public void setShowgrp_type(String showgrp_type) {
        this.showgrp_type = showgrp_type;
    }

    public String getShowtype_type() {
        return showtype_type;
    }

    public void setShowtype_type(String showtype_type) {
        this.showtype_type = showtype_type;
    }

    public String getDisttogrp() {
        return disttogrp;
    }

    public void setDisttogrp(String disttogrp) {
        this.disttogrp = disttogrp;
    }
}
