package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ORDER_CHECK", schema = "IAGENT")
@Entity
public class OrderCheck implements Serializable {
    private Long id;
    private String orderId;
    private String checkRule;
    private Boolean managerCheck;
    private Boolean sysCheck;
    private String managerId;
    private String agentId;
    private Date createDate;
    private String createUser;
    private Date updateDate;
    private String updateUser;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_CHECK_SEQ")
    @SequenceGenerator(name = "ORDER_CHECK_SEQ", sequenceName = "IAGENT.SEQ_ORDER_CHECK", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_ID", length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "CHECK_RULE", length = 100)
    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    @Column(name = "MANAGER_CHECK")
    public Boolean getManagerCheck() {
        return managerCheck;
    }

    public void setManagerCheck(Boolean managerCheck) {
        this.managerCheck = managerCheck;
    }

    @Column(name = "SYS_CHECK")
    public Boolean getSysCheck() {
        return sysCheck;
    }

    public void setSysCheck(Boolean sysCheck) {
        this.sysCheck = sysCheck;
    }

    @Column(name = "MANAGER_ID", length = 50)
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @Column(name = "AGENT_ID", length = 50)
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Column(name = "CREATE_DATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER", length = 50)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "UPDATE_DATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "UPDATE_USER", length = 50)
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderCheck that = (OrderCheck) o;

        if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;
        if (checkRule != null ? !checkRule.equals(that.checkRule) : that.checkRule != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (managerCheck != null ? !managerCheck.equals(that.managerCheck) : that.managerCheck != null) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (sysCheck != null ? !sysCheck.equals(that.sysCheck) : that.sysCheck != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (checkRule != null ? checkRule.hashCode() : 0);
        result = 31 * result + (managerCheck != null ? managerCheck.hashCode() : 0);
        result = 31 * result + (sysCheck != null ? sysCheck.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        return result;
    }
}
