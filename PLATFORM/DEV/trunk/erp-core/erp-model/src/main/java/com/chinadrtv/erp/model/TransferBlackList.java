package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackList
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@javax.persistence.Table(name = "TRANSFER_PHNBLACKLIST", schema = "IAGENT")
@Entity
public class TransferBlackList implements Serializable {
    private String id;
    private String contactId;
    private Integer orderNum;
    private Integer completeNum;
    private Double completeRate;
    private Date upData;
    private String valid;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CONTACTID", length = 20)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "ORDERNUM", length = 5)
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "COMPLETENUM", length = 5)
    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    @Column(name = "COMPLETERATE", length = 5)
    public Double getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    @Column(name = "UPDATA", length = 20)
    public Date getUpData() {
        return upData;
    }

    public void setUpData(Date upData) {
        this.upData = upData;
    }

    @Column(name = "VALID", length = 2)
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
