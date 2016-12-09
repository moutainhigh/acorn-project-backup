package com.chinadrtv.amazon.common.facade.bean.contact;

import java.io.Serializable;
import com.chinadrtv.amazon.common.facade.abs.AbstractResponse;

public class CustomerContactRes extends AbstractResponse implements Serializable {

    /** Version ID */
    private static final long serialVersionUID = -8149789373787392356L;

    /**联系人编号*/
    private Long           id;

    /**客户标识*/
    private String            customerId;
    
    /**联系人标识*/
    private String            friendCustomerId;
    
    /**联系人姓名*/
    private String            friendName;
    
    /**联系人别名*/
    private String            friendAlias;
    
    /**联系人帐号*/
    private String            frinedAccount;
    
    /**状态 T-可用 U-不可用 C-已注销*/
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
     * Getter method for property <tt>friendCustomerId</tt>.
     * 
     * @return property value of friendCustomerId
     */
    public String getFriendCustomerId() {
        return friendCustomerId;
    }

    /**
     * Setter method for property <tt>friendCustomerId</tt>.
     * 
     * @param friendCustomerId value to be assigned to property friendCustomerId
     */
    public void setFriendCustomerId(String friendCustomerId) {
        this.friendCustomerId = friendCustomerId;
    }

    /**
     * Getter method for property <tt>friendName</tt>.
     * 
     * @return property value of friendName
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     * Setter method for property <tt>friendName</tt>.
     * 
     * @param friendName value to be assigned to property friendName
     */
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    /**
     * Getter method for property <tt>friendAlias</tt>.
     * 
     * @return property value of friendAlias
     */
    public String getFriendAlias() {
        return friendAlias;
    }

    /**
     * Setter method for property <tt>friendAlias</tt>.
     * 
     * @param friendAlias value to be assigned to property friendAlias
     */
    public void setFriendAlias(String friendAlias) {
        this.friendAlias = friendAlias;
    }

    /**
     * Getter method for property <tt>frinedAccount</tt>.
     * 
     * @return property value of frinedAccount
     */
    public String getFrinedAccount() {
        return frinedAccount;
    }

    /**
     * Setter method for property <tt>frinedAccount</tt>.
     * 
     * @param frinedAccount value to be assigned to property frinedAccount
     */
    public void setFrinedAccount(String frinedAccount) {
        this.frinedAccount = frinedAccount;
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
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CustomerContactRes [id=" + id + ", customerId=" + customerId
               + ", friendCustomerId=" + friendCustomerId + ", friendName=" + friendName
               + ", friendAlias=" + friendAlias + ", frinedAccount=" + frinedAccount + ", status="
               + status + "]";
    }
}
