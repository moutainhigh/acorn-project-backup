package com.chinadrtv.erp.model.marketing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Callback 日志表
 * User: 徐志凯
 * Date: 13-8-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "CALLBACK_LOG", schema = "ACOAPP_MARKETING")
@Entity
public class CallbackLog implements Serializable {
    private Long id;

    private Long callbackId;

    private String contactId;

    private String usrId;

    @Transient
    private String usrName;

    private String usrGrp;

    @Transient
    private String usrGrpName;

    private String orderId;

    private String status;

    private Date requiredt;

    private String priority;

    private String flag;

    private String phn1;

    private String phn2;

    private String phn3;

    private String remark;

    private String name;

    private String mediaplanId;

    private String mediaprodId;//媒体产品编号

    private Date crdt;

    private String dnis;

    private String areacode;

    private String type;

    private String groupAssigner;

    private String userAssigner;

    private String mdusr;

    private Date mddt;

    private String ani;

    @Transient
    private Date callDate;  //呼入时间

    @Transient
    private Long callDuration;  //通话时长

    private String latentContactId;

    private String caseId;

    private String opusr;//	第一次分配人

    @Transient
    private String opusrName;

    private String firstusrId; //第一次分配座席

    @Transient
    private String firstusrName;  //第一次分配座席名称

    @Transient
    private String firstusrGrpName;  //第一次分配座席工作组

    private Date firstdt;   //第一次分配时间

    private String dbusrId;  //	最后分配人

    @Transient
    private String dbusrName;  //	最后分配人名称

    private String rdbusrId;

    @Transient
    private String rdbusrName;  //	最后分配坐席名称

    @Transient
    private String rdbusrGrpName;  //	最后分配坐席组名称

    private Date dbdt;

    private Long leadInteractionId;

    private String batchId;

    private Long allocateCount;
    private Boolean allocatedManual;
    private Long taskId;
    private String acdGroup;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CALLBACK_LOG")
    @SequenceGenerator(name = "SEQ_CALLBACK_LOG", sequenceName = "ACOAPP_MARKETING.SEQ_CALLBACK_LOG", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "CALLBACK_ID")
    public Long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    @Column(name = "CONTACT_ID", length = 16)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "USRID", length = 10)
    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrid) {
        this.usrId = usrid;
    }

    @Transient
    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    @Column(name = "USRGRP", length = 50)
    public String getUsrGrp() {
        return usrGrp;
    }

    public void setUsrGrp(String usrgrp) {
        this.usrGrp = usrgrp;
    }

    @Column(name = "ORDER_ID", length = 16)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "STATUS", length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "REQUIREDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRequiredt() {
        return requiredt;
    }

    public void setRequiredt(Date requiredt) {
        this.requiredt = requiredt;
    }

    @Column(name = "PRIORITY", length = 10)
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "FLAG", length = 10)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "PHN1", length = 10)
    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    @Column(name = "PHN2", length = 20)
    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    @Column(name = "PHN3", length = 20)
    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    @Column(name = "REMARK", length = 128)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "NAME", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MEDIAPLAN_ID", length = 16)
    public String getMediaplanId() {
        return mediaplanId;
    }

    public void setMediaplanId(String mediaplanId) {
        this.mediaplanId = mediaplanId;
    }

    @Column(name = "MEDIAPROD_ID")
    public String getMediaprodId() {
        return mediaprodId;
    }

    public void setMediaprodId(String mediaprodId) {
        this.mediaprodId = mediaprodId;
    }

    @Column(name = "CRDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "DNIS", length = 40)
    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    @Column(name = "AREACODE", length = 6)
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "GROUP_ASSIGNER", length = 20)
    public String getGroupAssigner() {
        return groupAssigner;
    }

    public void setGroupAssigner(String groupAssigner) {
        this.groupAssigner = groupAssigner;
    }

    @Column(name = "USER_ASSIGNER", length = 20)
    public String getUserAssigner() {
        return userAssigner;
    }

    public void setUserAssigner(String userAssigner) {
        this.userAssigner = userAssigner;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "MDDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "ANI", length = 32)
    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    @Transient
    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    @Transient
    public Long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Long callDuration) {
        this.callDuration = callDuration;
    }

    @Column(name = "LATENT_CONTACT_ID", length = 16)
    public String getLatentContactId() {
        return latentContactId;
    }

    public void setLatentContactId(String latentContactId) {
        this.latentContactId = latentContactId;
    }

    @Column(name = "CASE_ID", length = 64)
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @Column(name = "OPUSR")
    public String getOpusr() {
        return opusr;
    }

    public void setOpusr(String opusr) {
        this.opusr = opusr;
    }

    @Column(name = "FIRSTUSRID")
    public String getFirstusrId() {
        return firstusrId;
    }

    public void setFirstusrId(String firstusrId) {
        this.firstusrId = firstusrId;
    }
    @Column(name = "FIRSTDT")
    public Date getFirstdt() {
        return firstdt;
    }

    public void setFirstdt(Date firstdt) {
        this.firstdt = firstdt;
    }

    @Column(name = "DBUSRID", length = 10)
    public String getDbusrId() {
        return dbusrId;
    }

    public void setDbusrId(String dbusrid) {
        this.dbusrId = dbusrid;
    }

    @Column(name = "RDBUSRID", length = 10)
    public String getRdbusrId() {
        return rdbusrId;
    }

    public void setRdbusrId(String rdbusrid) {
        this.rdbusrId = rdbusrid;
    }

    @Column(name = "DBDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDbdt() {
        return dbdt;
    }

    public void setDbdt(Date dbdt) {
        this.dbdt = dbdt;
    }

    @Column(name = "LEAD_INTERACTION_ID")
    public Long getLeadInteractionId() {
        return leadInteractionId;
    }

    public void setLeadInteractionId(Long leadInteractionId) {
        this.leadInteractionId = leadInteractionId;
    }

    @Column(name = "BATCH_ID", length = 50)
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Transient
    public String getUsrGrpName() {
        return usrGrpName;
    }

    public void setUsrGrpName(String usrGrpName) {
        this.usrGrpName = usrGrpName;
    }

    @Transient
    public String getOpusrName() {
        return opusrName;
    }

    public void setOpusrName(String opusrName) {
        this.opusrName = opusrName;
    }

    @Transient
    public String getFirstusrName() {
        return firstusrName;
    }

    public void setFirstusrName(String firstusrName) {
        this.firstusrName = firstusrName;
    }

    @Transient
    public String getFirstusrGrpName() {
        return firstusrGrpName;
    }

    public void setFirstusrGrpName(String firstusrGrpName) {
        this.firstusrGrpName = firstusrGrpName;
    }

    @Transient
    public String getDbusrName() {
        return dbusrName;
    }

    public void setDbusrName(String dbusrName) {
        this.dbusrName = dbusrName;
    }

    @Transient
    public String getRdbusrName() {
        return rdbusrName;
    }

    public void setRdbusrName(String rdbusrName) {
        this.rdbusrName = rdbusrName;
    }

    @Transient
    public String getRdbusrGrpName() {
        return rdbusrGrpName;
    }

    public void setRdbusrGrpName(String rdbusrGrpName) {
        this.rdbusrGrpName = rdbusrGrpName;
    }

    @Column(name = "TASK_ID")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Column(name = "ACDGROUP")
    public String getAcdGroup() {
        return acdGroup;
    }

    public void setAcdGroup(String acdGroup) {
        this.acdGroup = acdGroup;
    }

    @Column(name = "ALLOCATED_NUMBER")
    public Long getAllocateCount() {
        return allocateCount;
    }

    public void setAllocateCount(Long allocateCount) {
        this.allocateCount = allocateCount;
    }

    @Column(name = "ALLOCATED_MANUAL")
    public Boolean getAllocatedManual() {
        return allocatedManual;
    }

    public void setAllocatedManual(Boolean allocatedManual) {
        this.allocatedManual = allocatedManual;
    }


}

