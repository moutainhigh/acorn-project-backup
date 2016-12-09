package com.chinadrtv.erp.oms.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-14
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 * 地址组明细
 */
public class AddressDto {

    private String areagroupId;    //地址组id
    private String areagroupName;    //地址组名称
    private String channelId;     //渠道id
    private String commonCarrier;  //承运商
    private String warehouseName;   //出库仓库
    private String priority;        //优先级别
    private String crdt;    //创建时间
    private String cruser;      //创建人
    private String status;  //状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreagroupId() {
        return areagroupId;
    }

    public void setAreagroupId(String areagroupId) {
        this.areagroupId = areagroupId;
    }

    public String getAreagroupName() {
        return areagroupName;
    }

    public void setAreagroupName(String areagroupName) {
        this.areagroupName = areagroupName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCommonCarrier() {
        return commonCarrier;
    }

    public void setCommonCarrier(String commonCarrier) {
        this.commonCarrier = commonCarrier;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getCruser() {
        return cruser;
    }

    public void setCruser(String cruser) {
        this.cruser = cruser;
    }
}
