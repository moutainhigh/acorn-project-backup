package com.chinadrtv.erp.sales.core.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class UsrMessage implements Serializable {
    public UsrMessage(String usrId,String grpManager,String departManager)
    {
        this.usrId=usrId;
        this.grpManager=grpManager;
        this.departManager=departManager;
    }

    public UsrMessage()
    {
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }


    private String usrId;

    public String getGrpManager() {
        return grpManager;
    }

    public void setGrpManager(String grpManager) {
        this.grpManager = grpManager;
    }

    public String getDepartManager() {
        return departManager;
    }

    public void setDepartManager(String departManager) {
        this.departManager = departManager;
    }

    private String grpManager;
    private String departManager;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    private Date beginDate;

    @Override
    public String toString() {
        return StringUtils.isNotEmpty(usrId) ? usrId : "";
    }
}
