package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.agent.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MyOrderFrontDto extends Order implements Serializable {
    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    private String contactname;

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String right;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    
    public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String bankName;
	
	//是否可申请催送货
	private Boolean couldReApply;

	public Boolean getCouldReApply() {
		return couldReApply;
	}

	public void setCouldReApply(Boolean couldReApply) {
		this.couldReApply = couldReApply;
	}

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Date trackTime) {
        this.trackTime = trackTime;
    }

    /**
     * 跟单分配人
     */
    private String assignUser;
    /**
     * 跟单分配时间
     */
    private Date assignTime;
    /**
     * 跟单时间
     */
    private Date trackTime;
}
