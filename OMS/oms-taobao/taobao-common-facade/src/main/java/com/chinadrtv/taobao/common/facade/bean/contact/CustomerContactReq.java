package com.chinadrtv.taobao.common.facade.bean.contact;

import java.io.Serializable;
import com.chinadrtv.taobao.common.facade.abs.AbstractRequest;

public class CustomerContactReq extends AbstractRequest implements Serializable {

    /** Version ID */
    private static final long serialVersionUID = -1997329722089616031L;

    /**客户标识*/
    private String            customerId;
    
    /**联系人标识*/
    private String            friendCustomerId;
    
    /**联系人姓名*/
    private String            friendName;
    
    /**联系人帐号*/
    private String            frinedAccount;
    
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CustomerContactReq [customerId=" + customerId + ", friendCustomerId="
               + friendCustomerId + ", friendName=" + friendName + ", frinedAccount="
               + frinedAccount + "]";
    }
}
