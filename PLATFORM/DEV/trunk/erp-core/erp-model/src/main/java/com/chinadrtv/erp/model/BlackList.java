package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackList
 * Description: 地址或电话黑名单
 * User: xieguoqiang
 *
 * @version 1.0
 */

@javax.persistence.Table(name = "BLACK_LIST", schema = "IAGENT")
@Entity
public class BlackList implements java.io.Serializable {
    private Long id;
    private String contactId;
    private Long potentialContactId;
//    private String addressId;
    private Long phoneId;
    private String agentId;
    private Long leadInteractionId;
    private Integer isActive;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
    private Long blackPhoneId;
    private String createUserGrp;

    public BlackList() {
    }

    public BlackList(String contactId, String createUser) {
        this.contactId = contactId;
//        this.addressId = addressId;
        this.createUser = createUser;
    }

    public BlackList(Long potentialContactId, Long phoneId, Long leadInteractionId, String createUser, String createUserGrp, Long blackPhoneId) {
        this.potentialContactId = potentialContactId;
        this.phoneId = phoneId;
        this.leadInteractionId = leadInteractionId;
        this.createUser = createUser;
        this.createUserGrp = createUserGrp;
        this.blackPhoneId = blackPhoneId;
    }

    public BlackList(String contactId, Long phoneId, Long leadInteractionId, String createUser, String createUserGrp, Long blackPhoneId) {
        this.contactId = contactId;
        this.phoneId = phoneId;
        this.leadInteractionId = leadInteractionId;
        this.createUser = createUser;
        this.createUserGrp = createUserGrp;
        this.blackPhoneId = blackPhoneId;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLACK_LIST_SEQ")
    @SequenceGenerator(name = "BLACK_LIST_SEQ", sequenceName = "IAGENT.BLACK_LIST_SEQ", allocationSize=1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CONTACT_ID", length = 20)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "POTENTIAL_CONTACT_ID", length = 19)
    public Long getPotentialContactId() {
        return potentialContactId;
    }

    public void setPotentialContactId(Long potentialContactId) {
        this.potentialContactId = potentialContactId;
    }

//    @Column(name = "ADDRESSID", length = 20)
//    public String getAddressId() {
//        return addressId;
//    }

//    public void setAddressId(String addressId) {
//        this.addressId = addressId;
//    }

    @Column(name = "PHONEID", length = 20)
    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    @Column(name = "AGENT_ID", length = 20)
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Column(name = "LEDA_INTERACTION_ID", length = 19)
    public Long getLeadInteractionId() {
        return leadInteractionId;
    }

    public void setLeadInteractionId(Long leadInteractionId) {
        this.leadInteractionId = leadInteractionId;
    }

    @Column(name = "IS_ACTIVE", length = 1)
    public Integer getActive() {
        return isActive;
    }

    public void setActive(Integer active) {
        isActive = active;
    }

    @Column(name = "CREATE_USER", length = 20)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_DATE", length = 20)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_USER", length = 20)
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "UPDATE_DATE", length = 20)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "BLACK_PHONE_ID", length = 20)
    public Long getBlackPhoneId() {
        return blackPhoneId;
    }

    public void setBlackPhoneId(Long blackPhoneId) {
        this.blackPhoneId = blackPhoneId;
    }

    @Column(name = "CREATE_USER_GRP", length = 20)
    public String getCreateUserGrp() {
        return createUserGrp;
    }

    public void setCreateUserGrp(String createUserGrp) {
        this.createUserGrp = createUserGrp;
    }
}
