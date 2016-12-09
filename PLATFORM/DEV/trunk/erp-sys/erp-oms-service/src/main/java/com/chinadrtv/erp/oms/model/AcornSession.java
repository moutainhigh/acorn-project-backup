package com.chinadrtv.erp.oms.model;

import com.google.code.ssm.api.CacheKeyMethod;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class AcornSession implements Serializable {
    public AcornSession()
    {
        this.loginIp="";
        this.orderCount=0;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    @CacheKeyMethod
    public String getSessionId() {
        return StringUtils.isBlank(sessionId)?" ":sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }


    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录时间
     */
    private Date loginDate;
    /**
     * 获取的订单号数量
     */
    private Integer orderCount;

    /**
     * 会话标识
     */
    private String sessionId;

    /**
     * 登录IP
     */
    private String loginIp;

}
