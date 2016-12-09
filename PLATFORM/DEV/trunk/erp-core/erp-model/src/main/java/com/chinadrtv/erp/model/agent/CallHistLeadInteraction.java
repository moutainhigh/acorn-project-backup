package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-16
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "callhist_leadinteraction", schema = "IAGENT")
public class CallHistLeadInteraction implements Serializable {
    @EmbeddedId
    private CallHistLeadInteractionId callHistLeadInteractionId;
    @Column(name="MODELID")
    private String  modelId;
    @Column(name="CREATEDATE")
    private String createDate;
    @Column(name="ENDDATE")
    private String endDate;
    @Column(name="CREATEUSER")
    private String createUser;
    @Column(name="NOTE")
    private String note;
    @Column(name="CONTACTID")
    private  String contactId;

    public CallHistLeadInteractionId getCallHistLeadInteractionId() {
        return callHistLeadInteractionId;
    }

    public void setCallHistLeadInteractionId(CallHistLeadInteractionId callHistLeadInteractionId) {
        this.callHistLeadInteractionId = callHistLeadInteractionId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
