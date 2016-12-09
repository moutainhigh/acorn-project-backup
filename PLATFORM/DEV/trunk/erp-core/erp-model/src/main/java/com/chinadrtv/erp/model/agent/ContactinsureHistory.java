package com.chinadrtv.erp.model.agent;
import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * Date: 14-3-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "CONTACTINSURE_HISTORY", schema = "IAGENT")
@Entity
public class ContactinsureHistory {
    private Long id;
    private String contactid;
    private Date insureSeccDate;
    private String callId;
    private String userId;

    @Column(name = "ID", length = 16)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CONTACTINSUREHISTORY")
    @SequenceGenerator(name="SEQ_CONTACTINSUREHISTORY", allocationSize=1, initialValue=1,sequenceName="IAGENT.SEQ_CONTACTINSUREHISTORY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "INSURESECCDATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getInsureSeccDate() {
        return insureSeccDate;
    }

    public void setInsureSeccDate(Date insureSeccDate) {
        this.insureSeccDate = insureSeccDate;
    }
    @Column(name = "CALLID", length = 50)
    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    @Column(name = "USERID", length = 16)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
