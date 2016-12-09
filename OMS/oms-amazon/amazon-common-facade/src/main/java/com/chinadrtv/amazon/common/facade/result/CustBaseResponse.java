package com.chinadrtv.amazon.common.facade.result;

import com.chinadrtv.amazon.common.facade.result.enums.CustomerResultCode;

public class CustBaseResponse {

    /** 返回的结果码*/
    private CustomerResultCode customerResultCode;

    /**返回的结果对象*/
    private Object             object;

    public CustBaseResponse(CustomerResultCode customerResultCode, Object object) {
        this.customerResultCode = customerResultCode;
        this.object = object;
    }

    public CustBaseResponse() {

    }

    /**
     * Getter method for property <tt>customerResultCode</tt>.
     * 
     * @return property value of customerResultCode
     */
    public CustomerResultCode getCustomerResultCode() {
        return customerResultCode;
    }

    /**
     * Setter method for property <tt>customerResultCode</tt>.
     * 
     * @param customerResultCode value to be assigned to property customerResultCode
     */
    public void setCustomerResultCode(CustomerResultCode customerResultCode) {
        this.customerResultCode = customerResultCode;
    }

    /**
     * Getter method for property <tt>object</tt>.
     * 
     * @return property value of object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Setter method for property <tt>object</tt>.
     * 
     * @param object value to be assigned to property object
     */
    public void setObject(Object object) {
        this.object = object;
    }
}
