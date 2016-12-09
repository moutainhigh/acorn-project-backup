package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: CustomerPhoneFrontDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class CustomerPhoneFrontDto implements Serializable {
    private String customerType;
    private String customerId;
    private String customerPhoneId;
    private String phn1;
    private String phn2;
    private String phn3;
    private String prmphn;
    private String phonetypid;
    private Date lastCallDate;
    private Integer callCount;
    private String instId;
    private String status;
    private String comment;

    private String noEncryptionPhone;
    private String belongAddress;

    private Integer state;

    private String noAreaCodePhone;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerPhoneId() {
        return customerPhoneId;
    }

    public void setCustomerPhoneId(String customerPhoneId) {
        this.customerPhoneId = customerPhoneId;
    }

    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    public String getPrmphn() {
        return prmphn;
    }

    public void setPrmphn(String prmphn) {
        this.prmphn = prmphn;
    }

    public String getPhonetypid() {
        return phonetypid;
    }

    public void setPhonetypid(String phonetypid) {
        this.phonetypid = phonetypid;
    }

    public String getLastCallDateStr() {
        if (lastCallDate == null)
            return null;
        return DateUtil.dateToString(lastCallDate);
    }

    public Date getLastCallDate() {
        return lastCallDate;
    }

    public void setLastCallDate(Date lastCallDate) {
        this.lastCallDate = lastCallDate;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNoEncryptionPhone() {
        return noEncryptionPhone;
    }

    public void setNoEncryptionPhone(String noEncryptionPhone) {
        this.noEncryptionPhone = noEncryptionPhone;
    }

    public String getBelongAddress() {
        return belongAddress;
    }

    public void setBelongAddress(String belongAddress) {
        this.belongAddress = belongAddress;
    }

    public String getNoAreaCodePhone() {
        return noAreaCodePhone;
    }

    public void setNoAreaCodePhone(String noAreaCodePhone) {
        this.noAreaCodePhone = noAreaCodePhone;
    }
}
