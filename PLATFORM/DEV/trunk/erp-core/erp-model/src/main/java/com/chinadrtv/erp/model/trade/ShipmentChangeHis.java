package com.chinadrtv.erp.model.trade;

import javax.persistence.*;
import java.util.Date;

/**
 * 发运单变更历史
 * User: 徐志凯
 * Date: 13-2-4
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "SHIPMENT_CHANGE_HIS", schema = "ACOAPP_OMS")
@Entity
public class ShipmentChangeHis implements java.io.Serializable, Comparable {
    /**
     * 内部Id
     */
    private Long id;

    /**
     *
     */
    private String processStamp;

    /**
     *
     */
    private Date dateTimeStamp;

    /**
     * 引用发运单对象
     */
    private Long shipmentRefId;

    /**
     * 发运单号
     */
    private String shipmentId;

    /**
     * 更新前邮件号
     */
    private String beforeMailId;

    /**
     * 更新后邮件号
     */
    private String afterMailId;

    /**
     * 更新前承运商
     */
    private String beforeEntityId;

    /**
     * 更新后承运商
     */
    private String afterEntityId;

    /**
     * 更新前仓库
     */
    private String beforeWarehouseId;

    /**
     * 更新后仓库
     */
    private String afterWarehouseId;

    /**
     * 更新前-结算状态Id
     */
    private String beforeAccountStatusId;

    /**
     * 更新后-结算状态Id
     */
    private String afterAccountStatusId;

    /**
     * 更新前-结算状态备注
     */
    private String beforeAccountStatusRemark;
    /**
     * 更新后-结算状态备注
     */
    private String afterAccountStatusRemark;

    /**
     * 更新前-物流状态Id
     */
    private String beforeLogisticsStatusId;

    /**
     * 更新后-物流状态Id
     */
    private String afterLogisticsStatusId;

    /**
     * 更新前-物流状态备注
     */
    private String beforeLogisticsStatusRemark;

    /**
     * 更新后-物流状态备注
     */
    private String afterLogisticsStatusRemark;

    /**
     * 处理人员名称
     */
    private String userStamp;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_CHANGE_HIS_SEQ")
    @SequenceGenerator(name = "SHIPMENT_CHANGE_HIS_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_CHANGE_HIS_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPMENT_REF_ID", length = 22)
    public Long getShipmentRefId() {
        return shipmentRefId;
    }

    public void setShipmentRefId(Long shipmentRefId) {
        this.shipmentRefId = shipmentRefId;
    }

    @Column(name = "SHIPMENT_ID", length = 20)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "BEFORE_MAIL_ID", length = 20)
    public String getBeforeMailId() {
        return beforeMailId;
    }

    public void setBeforeMailId(String beforeMailId) {
        this.beforeMailId = beforeMailId;
    }

    @Column(name = "AFTER_MAIL_ID", length = 20)
    public String getAfterMailId() {
        return afterMailId;
    }

    public void setAfterMailId(String afterMailId) {
        this.afterMailId = afterMailId;
    }

    @Column(name = "BEFORE_ENTITY_ID", length = 20)
    public String getBeforeEntityId() {
        return beforeEntityId;
    }

    public void setBeforeEntityId(String beforeEntityId) {
        this.beforeEntityId = beforeEntityId;
    }

    @Column(name = "AFTER_ENTITY_ID", length = 20)
    public String getAfterEntityId() {
        return afterEntityId;
    }

    public void setAfterEntityId(String afterEntityId) {
        this.afterEntityId = afterEntityId;
    }

    @Column(name = "BEFORE_WAREHOUSE_ID", length = 20)
    public String getBeforeWarehouseId() {
        return beforeWarehouseId;
    }

    public void setBeforeWarehouseId(String beforeWarehouseId) {
        this.beforeWarehouseId = beforeWarehouseId;
    }

    @Column(name = "AFTER_WAREHOUSE_ID", length = 20)
    public String getAfterWarehouseId() {
        return afterWarehouseId;
    }

    public void setAfterWarehouseId(String afterWarehouseId) {
        this.afterWarehouseId = afterWarehouseId;
    }

    @Column(name = "BEFORE_ACCOUNT_STATUS_ID", length = 10)
    public String getBeforeAccountStatusId() {
        return beforeAccountStatusId;
    }

    public void setBeforeAccountStatusId(String beforeAccountStatusId) {
        this.beforeAccountStatusId = beforeAccountStatusId;
    }

    @Column(name = "AFTER_ACCOUNT_STATUS_ID", length = 10)
    public String getAfterAccountStatusId() {
        return afterAccountStatusId;
    }

    public void setAfterAccountStatusId(String afterAccountStatusId) {
        this.afterAccountStatusId = afterAccountStatusId;
    }

    @Column(name = "BEFORE_ACCOUNT_STATUS_REMARK", length = 100)
    public String getBeforeAccountStatusRemark() {
        return beforeAccountStatusRemark;
    }

    public void setBeforeAccountStatusRemark(String beforeAccountStatusRemark) {
        this.beforeAccountStatusRemark = beforeAccountStatusRemark;
    }


    @Column(name = "AFTER_ACCOUNT_STATUS_REMARK", length = 100)
    public String getAfterAccountStatusRemark() {
        return afterAccountStatusRemark;
    }

    public void setAfterAccountStatusRemark(String afterAccountStatusRemark) {
        this.afterAccountStatusRemark = afterAccountStatusRemark;
    }


    @Column(name = "BEFORE_LOGISTICS_STATUS_ID", length = 10)
    public String getBeforeLogisticsStatusId() {
        return beforeLogisticsStatusId;
    }

    public void setBeforeLogisticsStatusId(String beforeLogisticsStatusId) {
        this.beforeLogisticsStatusId = beforeLogisticsStatusId;
    }


    @Column(name = "AFTER_LOGISTICS_STATUS_ID", length = 10)
    public String getAfterLogisticsStatusId() {
        return afterLogisticsStatusId;
    }

    public void setAfterLogisticsStatusId(String afterLogisticsStatusId) {
        this.afterLogisticsStatusId = afterLogisticsStatusId;
    }


    @Column(name = "BEFORE_LOGISTICS_STATUS_REMARK", length = 100)
    public String getBeforeLogisticsStatusRemark() {
        return beforeLogisticsStatusRemark;
    }

    public void setBeforeLogisticsStatusRemark(String beforeLogisticsStatusRemark) {
        this.beforeLogisticsStatusRemark = beforeLogisticsStatusRemark;
    }


    @Column(name = "AFTER_LOGISTICS_STATUS_REMARK", length = 100)
    public String getAfterLogisticsStatusRemark() {
        return afterLogisticsStatusRemark;
    }

    public void setAfterLogisticsStatusRemark(String afterLogisticsStatusRemark) {
        this.afterLogisticsStatusRemark = afterLogisticsStatusRemark;
    }


    @Column(name = "USER_STAMP", length = 20)
    public String getUserStamp() {
        return userStamp;
    }

    public void setUserStamp(String userStamp) {
        this.userStamp = userStamp;
    }


    @Column(name = "PROCESS_STAMP", length = 50)
    public String getProcessStamp() {
        return processStamp;
    }

    public void setProcessStamp(String processStamp) {
        this.processStamp = processStamp;
    }

    @Column(name = "DATE_TIME_STAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(Date dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentChangeHis that = (ShipmentChangeHis) o;

        if (id != that.id) return false;
        if (shipmentRefId != that.shipmentRefId) return false;
        if (afterAccountStatusId != null ? !afterAccountStatusId.equals(that.afterAccountStatusId) : that.afterAccountStatusId != null)
            return false;
        if (afterAccountStatusRemark != null ? !afterAccountStatusRemark.equals(that.afterAccountStatusRemark) : that.afterAccountStatusRemark != null)
            return false;
        if (afterEntityId != null ? !afterEntityId.equals(that.afterEntityId) : that.afterEntityId != null)
            return false;
        if (afterLogisticsStatusId != null ? !afterLogisticsStatusId.equals(that.afterLogisticsStatusId) : that.afterLogisticsStatusId != null)
            return false;
        if (afterLogisticsStatusRemark != null ? !afterLogisticsStatusRemark.equals(that.afterLogisticsStatusRemark) : that.afterLogisticsStatusRemark != null)
            return false;
        if (afterMailId != null ? !afterMailId.equals(that.afterMailId) : that.afterMailId != null) return false;
        if (afterWarehouseId != null ? !afterWarehouseId.equals(that.afterWarehouseId) : that.afterWarehouseId != null)
            return false;
        if (beforeAccountStatusId != null ? !beforeAccountStatusId.equals(that.beforeAccountStatusId) : that.beforeAccountStatusId != null)
            return false;
        if (beforeAccountStatusRemark != null ? !beforeAccountStatusRemark.equals(that.beforeAccountStatusRemark) : that.beforeAccountStatusRemark != null)
            return false;
        if (beforeEntityId != null ? !beforeEntityId.equals(that.beforeEntityId) : that.beforeEntityId != null)
            return false;
        if (beforeLogisticsStatusId != null ? !beforeLogisticsStatusId.equals(that.beforeLogisticsStatusId) : that.beforeLogisticsStatusId != null)
            return false;
        if (beforeLogisticsStatusRemark != null ? !beforeLogisticsStatusRemark.equals(that.beforeLogisticsStatusRemark) : that.beforeLogisticsStatusRemark != null)
            return false;
        if (beforeMailId != null ? !beforeMailId.equals(that.beforeMailId) : that.beforeMailId != null) return false;
        if (beforeWarehouseId != null ? !beforeWarehouseId.equals(that.beforeWarehouseId) : that.beforeWarehouseId != null)
            return false;
        if (dateTimeStamp != null ? !dateTimeStamp.equals(that.dateTimeStamp) : that.dateTimeStamp != null)
            return false;
        if (processStamp != null ? !processStamp.equals(that.processStamp) : that.processStamp != null) return false;
        if (shipmentId != null ? !shipmentId.equals(that.shipmentId) : that.shipmentId != null) return false;
        if (userStamp != null ? !userStamp.equals(that.userStamp) : that.userStamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (shipmentRefId != null ? shipmentRefId.hashCode() : 0);
        result = 31 * result + (shipmentId != null ? shipmentId.hashCode() : 0);
        result = 31 * result + (beforeMailId != null ? beforeMailId.hashCode() : 0);
        result = 31 * result + (afterMailId != null ? afterMailId.hashCode() : 0);
        result = 31 * result + (beforeEntityId != null ? beforeEntityId.hashCode() : 0);
        result = 31 * result + (afterEntityId != null ? afterEntityId.hashCode() : 0);
        result = 31 * result + (beforeWarehouseId != null ? beforeWarehouseId.hashCode() : 0);
        result = 31 * result + (afterWarehouseId != null ? afterWarehouseId.hashCode() : 0);
        result = 31 * result + (beforeAccountStatusId != null ? beforeAccountStatusId.hashCode() : 0);
        result = 31 * result + (afterAccountStatusId != null ? afterAccountStatusId.hashCode() : 0);
        result = 31 * result + (beforeAccountStatusRemark != null ? beforeAccountStatusRemark.hashCode() : 0);
        result = 31 * result + (afterAccountStatusRemark != null ? afterAccountStatusRemark.hashCode() : 0);
        result = 31 * result + (beforeLogisticsStatusId != null ? beforeLogisticsStatusId.hashCode() : 0);
        result = 31 * result + (afterLogisticsStatusId != null ? afterLogisticsStatusId.hashCode() : 0);
        result = 31 * result + (beforeLogisticsStatusRemark != null ? beforeLogisticsStatusRemark.hashCode() : 0);
        result = 31 * result + (afterLogisticsStatusRemark != null ? afterLogisticsStatusRemark.hashCode() : 0);
        result = 31 * result + (userStamp != null ? userStamp.hashCode() : 0);
        result = 31 * result + (processStamp != null ? processStamp.hashCode() : 0);
        result = 31 * result + (dateTimeStamp != null ? dateTimeStamp.hashCode() : 0);
        return result;
    }

    public int compareTo(Object o) {
        if(o instanceof ShipmentChangeHis)
        {
            ShipmentChangeHis other=(ShipmentChangeHis)o;
            if(this.getDateTimeStamp()!=null)
            {
                if(other.getDateTimeStamp()==null)
                    return 1;
                else
                    return this.dateTimeStamp.compareTo(other.getDateTimeStamp());
            }
        }
        return -1;
    }
}
