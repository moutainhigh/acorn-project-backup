package com.chinadrtv.erp.admin.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Usr", schema = "IAGENT")
//用户表--来源Names
public class User implements java.io.Serializable
{
    private String usrid;
    private String name;
    private String title;
    private String defgrp;
    private String acdgroup;
    private String valid;
    private Date lastdt;
    private String workgrp;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_LOG_SEQ")
    @SequenceGenerator(name = "AUDIT_LOG_SEQ", sequenceName = "IAGENT.AUDIT_LOG_SEQ")
    @Column(name = "USRID", length = 80)
    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    @Column(name = "NAME", length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "TITLE", length = 80)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "VALID", length = 80)
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
    @Column(name = "DEFGRP", length = 80)
    public String getDefgrp() {
        return defgrp;
    }

    public void setDefgrp(String defgrp) {
        this.defgrp = defgrp;
    }
    @Column(name = "ACDGROUP", length = 80)
    public String getAcdgroup() {
        return acdgroup;
    }

    public void setAcdgroup(String acdgroup) {
        this.acdgroup = acdgroup;
    }

    @Column(name = "LASTDT", length = 80)
    public Date getLastdt() {
        return lastdt;
    }

    public void setLastdt(Date lastdt) {
        this.lastdt = lastdt;
    }
    @Column(name = "WORKGRP", length = 80)
    public String getWorkgrp() {
        return workgrp;
    }

    public void setWorkgrp(String workgrp) {
        this.workgrp = workgrp;
    }

}
