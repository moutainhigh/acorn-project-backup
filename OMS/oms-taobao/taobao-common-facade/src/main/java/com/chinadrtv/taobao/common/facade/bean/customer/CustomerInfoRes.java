package com.chinadrtv.taobao.common.facade.bean.customer;

import com.chinadrtv.taobao.common.facade.abs.AbstractResponse;

public class CustomerInfoRes extends AbstractResponse{

    /**version ID  */
    private static final long serialVersionUID = -5119107732927881485L;

    /** 客户标识 */
    private String            customerId;

    /** 注册帐号*/
    private String            regAccount;

    /** 真实姓名*/
    private String            realName;

    /** 证件类型*/
    private String            identityTyper;

    /** 证件号码*/
    private String            identityNumber;

    /** 用户状态 I.未激活 T.正常 U.不可用*/
    private String            status;



    /**
     * Getter method for property <tt>customerId</tt>.
     * 
     * @return property value of customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for property <tt>customerId</tt>.
     * 
     * @param customerId value to be assigned to property customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for property <tt>regAccount</tt>.
     * 
     * @return property value of regAccount
     */
    public String getRegAccount() {
        return regAccount;
    }

    /**
     * Setter method for property <tt>regAccount</tt>.
     * 
     * @param regAccount value to be assigned to property regAccount
     */
    public void setRegAccount(String regAccount) {
        this.regAccount = regAccount;
    }

    /**
     * Getter method for property <tt>realName</tt>.
     * 
     * @return property value of realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Setter method for property <tt>realName</tt>.
     * 
     * @param realName value to be assigned to property realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Getter method for property <tt>identityTyper</tt>.
     * 
     * @return property value of identityTyper
     */
    public String getIdentityTyper() {
        return identityTyper;
    }

    /**
     * Setter method for property <tt>identityTyper</tt>.
     * 
     * @param identityTyper value to be assigned to property identityTyper
     */
    public void setIdentityTyper(String identityTyper) {
        this.identityTyper = identityTyper;
    }

    /**
     * Getter method for property <tt>identityNumber</tt>.
     * 
     * @return property value of identityNumber
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * Setter method for property <tt>identityNumber</tt>.
     * 
     * @param identityNumber value to be assigned to property identityNumber
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CustomerInfoRes [customerId=" + customerId + ", regAccount=" + regAccount
               + ", realName=" + realName
               + ", status=" + status + ", customerType="
               + "]";
    }
}
