package com.chinadrtv.erp.uc.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: UpdateLastCallDateDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class UpdateLastCallDateDto implements java.io.Serializable {
    private String customerId;
    private Long customerPhoneId;
    private String phn2;
    private Date lastCallDate;

    public UpdateLastCallDateDto(String customerId, Long customerPhoneId, Date lastCallDate) {
        this.customerId = customerId;
        this.customerPhoneId = customerPhoneId;
        this.lastCallDate = lastCallDate;
    }

    public UpdateLastCallDateDto(String customerId, String phn2, Date lastCallDate) {
        this.customerId = customerId;
        this.phn2 = phn2;
        this.lastCallDate = lastCallDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerPhoneId() {
        return customerPhoneId;
    }

    public void setCustomerPhoneId(Long customerPhoneId) {
        this.customerPhoneId = customerPhoneId;
    }

    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    public Date getLastCallDate() {
        return lastCallDate;
    }

    public void setLastCallDate(Date lastCallDate) {
        this.lastCallDate = lastCallDate;
    }
}
