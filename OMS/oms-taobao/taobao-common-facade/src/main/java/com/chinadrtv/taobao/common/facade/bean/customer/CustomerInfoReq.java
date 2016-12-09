package com.chinadrtv.taobao.common.facade.bean.customer;

import java.io.Serializable;

import com.chinadrtv.taobao.common.facade.abs.AbstractRequest;

public class CustomerInfoReq extends AbstractRequest implements Serializable {

    /** Version ID */
    private static final long serialVersionUID = 4535896753777395049L;

    /** 注册帐号*/
    private String            regAccount;

    /** 真实姓名*/
    private String            realName;

    /** 登录密码*/
    private String            loginPassWord;

    /** 支付密码*/
    private String            payPassWord;

    /** 证件类型*/
    private String            identityTyper;

    /** 证件号码*/
    private String            identityNumber;

    /** 客户类型 B.商户 C.个人*/
    private String            customerType;

    /** 用户图像存放路径*/
    private String            picWay;

    /** 用户属性 1.买家  2. 卖家*/
    private String            property;
    
    /**注册标识 common.普通注册，fast.快捷注册 batch.批量注册*/
    private String             regType;
    
    /**注册渠道 D.移动终端 W.网路*/
    private String         regChannel;
    
    /**注册方式 M.手机 E.邮箱*/
    private String        regWay;
    
    /**移动终端设备号*/
    private String        deviceNum;

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
     * Getter method for property <tt>loginPassWord</tt>.
     * 
     * @return property value of loginPassWord
     */
    public String getLoginPassWord() {
        return loginPassWord;
    }

    /**
     * Setter method for property <tt>loginPassWord</tt>.
     * 
     * @param loginPassWord value to be assigned to property loginPassWord
     */
    public void setLoginPassWord(String loginPassWord) {
        this.loginPassWord = loginPassWord;
    }

    /**
     * Getter method for property <tt>payPassWord</tt>.
     * 
     * @return property value of payPassWord
     */
    public String getPayPassWord() {
        return payPassWord;
    }

    /**
     * Setter method for property <tt>payPassWord</tt>.
     * 
     * @param payPassWord value to be assigned to property payPassWord
     */
    public void setPayPassWord(String payPassWord) {
        this.payPassWord = payPassWord;
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
     * Getter method for property <tt>customerType</tt>.
     * 
     * @return property value of customerType
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * Setter method for property <tt>customerType</tt>.
     * 
     * @param customerType value to be assigned to property customerType
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
     * Getter method for property <tt>picWay</tt>.
     * 
     * @return property value of picWay
     */
    public String getPicWay() {
        return picWay;
    }

    /**
     * Setter method for property <tt>picWay</tt>.
     * 
     * @param picWay value to be assigned to property picWay
     */
    public void setPicWay(String picWay) {
        this.picWay = picWay;
    }

    /**
     * Getter method for property <tt>property</tt>.
     * 
     * @return property value of property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Setter method for property <tt>property</tt>.
     * 
     * @param property value to be assigned to property property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Getter method for property <tt>regType</tt>.
     * 
     * @return property value of regType
     */
    public String getRegType() {
        return regType;
    }

    /**
     * Setter method for property <tt>regType</tt>.
     * 
     * @param regType value to be assigned to property regType
     */
    public void setRegType(String regType) {
        this.regType = regType;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CustomerInfoReq [regAccount=" + regAccount + ", realName=" + realName
               + ", customerType=" + customerType + ", property=" + property + "]";
    }

    /**
     * Getter method for property <tt>regChannel</tt>.
     * 
     * @return property value of regChannel
     */
    public String getRegChannel() {
        return regChannel;
    }

    /**
     * Setter method for property <tt>regChannel</tt>.
     * 
     * @param regChannel value to be assigned to property regChannel
     */
    public void setRegChannel(String regChannel) {
        this.regChannel = regChannel;
    }

    /**
     * Getter method for property <tt>regWay</tt>.
     * 
     * @return property value of regWay
     */
    public String getRegWay() {
        return regWay;
    }

    /**
     * Setter method for property <tt>regWay</tt>.
     * 
     * @param regWay value to be assigned to property regWay
     */
    public void setRegWay(String regWay) {
        this.regWay = regWay;
    }

    /**
     * Getter method for property <tt>deviceNum</tt>.
     * 
     * @return property value of deviceNum
     */
    public String getDeviceNum() {
        return deviceNum;
    }

    /**
     * Setter method for property <tt>deviceNum</tt>.
     * 
     * @param deviceNum value to be assigned to property deviceNum
     */
    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }
}
