package com.chinadrtv.erp.model;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "CARD_CHANGE", schema = "IAGENT")
@Entity
public class CardChange {
    private static final long serialVersionUID = -6004552692745006282L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_CHANGE_SEQ")
    @SequenceGenerator(name = "CARD_CHANGE_SEQ", sequenceName = "IAGENT.CARD_CHANGE_SEQ")
    @Column(name = "CARD_CHANGE_ID")
    public Long getCardChangeId() {
        return cardChangeId;
    }

    /**
     * 卡主键
     */
    private Long cardId;

    /**
     * 客户编号
     */
    private String contactId;

    /**
     * 客户所属公司编号
     */
    private String entityId;

    /**
     * 客户证件类型
     */
    private String type;

    /**
     * 信用卡卡号
     */
    private String cardNumber;

    /**
     * 有效期
     */
    private String validDate;

    /**
     * 是否已校验
     */
    private String checked;

    /**
     * 附加码
     */
    private String extraCode;

    /**
     * 信用额度
     */
    private String limit;


    private Long lastLockSeqid;


    private Long lastUpdateSeqid;


    private java.util.Date lastUpdateTime;
    //columns END

    @Column(name = "CARDID")
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    @Column(name = "CONTACTID", length = 16)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "ENTITYID", length = 16)
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    @Column(name = "TYPE", length = 128)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "DSC", length = 250)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    @Column(name = "VALIDDATE", length = 20)
    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
    @Column(name = "CHECKED", length = 10)
    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
    @Column(name = "EXTRACODE", length = 10)
    public String getExtraCode() {
        return extraCode;
    }

    public void setExtraCode(String extraCode) {
        this.extraCode = extraCode;
    }

    @Column(name = "LIMIT", length = 10)
    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    @Column(name = "LAST_LOCK_SEQID", length = 22)
    public Long getLastLockSeqid() {
        return lastLockSeqid;
    }

    public void setLastLockSeqid(Long lastLockSeqid) {
        this.lastLockSeqid = lastLockSeqid;
    }

    @Column(name = "LAST_UPDATE_SEQID", length = 22)
    public Long getLastUpdateSeqid() {
        return lastUpdateSeqid;
    }

    public void setLastUpdateSeqid(Long lastUpdateSeqid) {
        this.lastUpdateSeqid = lastUpdateSeqid;
    }

    @Column(name = "LAST_UPDATE_TIME", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(java.util.Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    private Long contactChangeId;

    @ManyToOne
    @JoinColumn(name = "ORDER_CHANGE_ID")
    public OrderChange getOrderChange() {
        return orderChange;
    }

    public void setOrderChange(OrderChange orderChange) {
        this.orderChange = orderChange;
    }

    private OrderChange orderChange;

    @Column(name = "CONTACT_CHANGE_ID")
    public Long getContactChangeId() {
        return contactChangeId;
    }

    public void setContactChangeId(Long contactChangeId) {
        this.contactChangeId = contactChangeId;
    }

    private Long cardChangeId;

    public void setCardChangeId(Long cardChangeId) {
        this.cardChangeId = cardChangeId;
    }

    private String procInstId;

    @Column(name = "PROC_INST_ID",  length = 18)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    private String createUser;

    @Column(name = "CREATE_USER",  length = 20)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    private Date createDate;

    @Column(name = "CREATE_DATE",  length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardChange that = (CardChange) o;

        if (cardChangeId != null ? !cardChangeId.equals(that.cardChangeId) : that.cardChangeId != null) return false;
        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (checked != null ? !checked.equals(that.checked) : that.checked != null) return false;
        if (contactChangeId != null ? !contactChangeId.equals(that.contactChangeId) : that.contactChangeId != null)
            return false;
        if (contactId != null ? !contactId.equals(that.contactId) : that.contactId != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (cardNumber != null ? !cardNumber.equals(that.cardNumber) : that.cardNumber != null) return false;
        if (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) return false;
        if (extraCode != null ? !extraCode.equals(that.extraCode) : that.extraCode != null) return false;
        if (lastLockSeqid != null ? !lastLockSeqid.equals(that.lastLockSeqid) : that.lastLockSeqid != null)
            return false;
        if (lastUpdateSeqid != null ? !lastUpdateSeqid.equals(that.lastUpdateSeqid) : that.lastUpdateSeqid != null)
            return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (limit != null ? !limit.equals(that.limit) : that.limit != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (validDate != null ? !validDate.equals(that.validDate) : that.validDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contactId != null ? contactId.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validDate != null ? validDate.hashCode() : 0);
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        result = 31 * result + (extraCode != null ? extraCode.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (lastLockSeqid != null ? lastLockSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (contactChangeId != null ? contactChangeId.hashCode() : 0);
        result = 31 * result + (cardChangeId != null ? cardChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
