package com.chinadrtv.erp.sales.core.model;

import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.constant.MonthType;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MessageQueryDto extends SysMessage {
    public MessageQueryDto()
    {
    }
    /*public Integer getMonthType() {
        return monthType;
    }

    public void setMonthType(Integer monthType) {
        this.monthType = monthType;
    }*/

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    private MonthType monthType;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    private MessageType messageType;

    public List<String> getGrpIdList() {
        return grpIdList;
    }

    public void setGrpIdList(List<String> grpIdList) {
        this.grpIdList = grpIdList;
    }

    private List<String> grpIdList;//查询的组列表（用于部门主管查询）
    //private Integer monthType;//0-本月 1-上个月 2-更早
    private Integer startPos;
    private Integer pageSize;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private Date beginDate;
    private Date endDate;

    public Integer getCountRows() {
        return countRows;
    }

    public void setCountRows(Integer countRows) {
        this.countRows = countRows;
    }

    private Integer countRows;

    public Boolean getHaveReceiver() {
        return isHaveReceiver;
    }

    public void setHaveReceiver(Boolean haveReceiver) {
        isHaveReceiver = haveReceiver;
    }

    private Boolean isHaveReceiver;

    public List<Integer> getCheckStatusList() {
        return checkStatusList;
    }

    public void setCheckStatusList(List<Integer> checkStatusList) {
        this.checkStatusList = checkStatusList;
    }

    private List<Integer> checkStatusList;
}
