package com.chinadrtv.erp.customer.services.dto;

import java.io.Serializable;

/**
 * Created by xieguoqiang on 14-5-15.
 */
public class CustomerServicePhoneDto implements Serializable {
    private String phn1;
    private String phn2;
    private String phn3;
    private String phoneType;
    private String prmphn;

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

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPrmphn() {
        return prmphn;
    }

    public void setPrmphn(String prmphn) {
        this.prmphn = prmphn;
    }
}
