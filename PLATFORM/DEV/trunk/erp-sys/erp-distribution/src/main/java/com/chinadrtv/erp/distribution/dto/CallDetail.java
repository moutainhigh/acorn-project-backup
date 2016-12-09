package com.chinadrtv.erp.distribution.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public class CallDetail implements java.io.Serializable {
    /* 数据库唯一标识(自增长) */
    private Long id;
    /* CTI ConnId电话唯一标识 */
    private String connId;
    /* 进线VDN名称 */
    private String vdn;
    /* 进VDN时间 */
    private Date vdnInTime;
    /* 出VDN时间 */
    private Date vdnOutTime;
    /* 插入时间 */
    private Date insertTime;
    /* 来电号码 */
    private String bk1;
    /* 落地号码 */
    private String bk2;
    /* ACD组 */
    private String skillGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public String getVdn() {
        return vdn;
    }

    public void setVdn(String vdn) {
        this.vdn = vdn;
    }

    public Date getVdnInTime() {
        return vdnInTime;
    }

    public void setVdnInTime(Date vdnInTime) {
        this.vdnInTime = vdnInTime;
    }

    public Date getVdnOutTime() {
        return vdnOutTime;
    }

    public void setVdnOutTime(Date vdnOutTime) {
        this.vdnOutTime = vdnOutTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getBk1() {
        return bk1;
    }

    public void setBk1(String bk1) {
        this.bk1 = bk1;
    }

    public String getBk2() {
        return bk2;
    }

    public void setBk2(String bk2) {
        this.bk2 = bk2;
    }

    public String getSkillGroup() {
        return skillGroup;
    }

    public void setSkillGroup(String skillGroup) {
        this.skillGroup = skillGroup;
    }
}
