package com.chinadrtv.amazon.common.facade.bean.customer;

import com.chinadrtv.amazon.common.facade.abs.AbstractResponse;

public class CustBasePersonalRes extends AbstractResponse {
    
    /**versionId  */
    private static final long serialVersionUID = 4997778550000285641L;
    /** 会员编号*/
    private String customerId;
    /** 会员名称*/
    private String name;
    
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CustBasePersonalRes [customerId=" + customerId + ", name=" + name + "]";
    }
}
