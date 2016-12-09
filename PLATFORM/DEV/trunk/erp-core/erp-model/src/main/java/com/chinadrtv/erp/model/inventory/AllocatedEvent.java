package com.chinadrtv.erp.model.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓库预分配(非持久化对象)
 * User: gaodejian
 * Date: 13-1-31
 * Time: 下午3:34
 * To change this template use File | Settings | File Templates.
 * 输入参数：渠道，仓库，子渠道，业务类型，数据来源，商品列表（商品编码（12码）、销售数量）,操作人
 */
public class AllocatedEvent implements java.io.Serializable {


    private String channel;
    private String subChannel;
    private String warehouse;
    private String businessType;
    private String businessNo;
    private Date businessDate;
    private Long sourceId;
    private String user;
    private List<AllocatedEventItem> eventItems;
    private List<String> errors;

    public AllocatedEvent(){
        super();
        errors = new ArrayList<String>();
        eventItems = new ArrayList<AllocatedEventItem>();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<AllocatedEventItem> getEventItems() {
        return eventItems;
    }

    public void setEventItems(List<AllocatedEventItem> eventItems) {
        this.eventItems = eventItems;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public Boolean isSuccess(){
        return errors.isEmpty();
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }
}
