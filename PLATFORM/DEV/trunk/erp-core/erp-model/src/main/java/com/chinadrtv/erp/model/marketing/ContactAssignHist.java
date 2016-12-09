package com.chinadrtv.erp.model.marketing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-8-27
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "CONTACT_ASSIGN_HIST", schema = "ACOAPP_MARKETING")
@Entity
public class ContactAssignHist implements Serializable {

    private Long  id; //ID
    private String customerId; //CUSTOMER_ID	VARCHAR2(50)	Y			客户id
    private Long campaignId; //CAMPAIGN_ID	NUMBER(19)	Y			营销编号
    private String batchCode; //BATCH_CODE	VARCHAR2(50)	Y			客户群批次号
    private Date createDate; //CREATE_DATE	DATE	Y			创建时间
    private String createUser; //CREATE_USER	VARCHAR2(50)	Y			创建用户
    private String groupCode; //GROUP_CODE	VARCHAR2(50)	Y			客户群编码
    private String contactInfo; //CONTACT_INFO	VARCHAR2(100)	Y
    private String statisInfo; //STATIS_INFO	VARCHAR2(50)	Y
    private String  jobid; //JOBID	VARCHAR2(100)	Y			JOBID数据级别
    private String queueid; //QUEUEID	VARCHAR2(50)	Y			QUEUEID队列
    private Long priority; //PRIORITY	NUMBER	Y			优先级
    private Date updateDate; //UPDATE_DATE	DATE	Y
    private String updateUser; //UPDATE_USER	VARCHAR2(50)	Y
    private String dataSource; //DATA_SOURCE	VARCHAR2(50)	Y			客户来源;正式客户-CONTACT;潜客-POTENTIAL
    private String assignGroup; //ASSIGN_GROUP	VARCHAR2(50)	Y			分配到的坐席组
    private String assignGroupUser; //ASSIGN_GROUP_USER	VARCHAR2(50)	Y			分配到坐席组的用户id
    private Date assignGroupTime; //ASSIGN_GROUP_TIME	DATE	Y			分配到坐席组的时间
    private String assignAgent; //ASSIGN_AGENT	VARCHAR2(50)	Y			分配到的坐席
    private String assignUser; //ASSIGN_USER	VARCHAR2(50)	Y			分配到坐席的用户id
    private Date assignTime;//ASSIGN_TIME	DATE	Y			分配时间
    private Long leadId; //LEAD_ID	NUMBER(19)	Y			生成的线索id
    private String status; // STATUS	VARCHAR2(50)	Y			0未分配；1已分配到组；2已分配到坐席；3线索任务已执行


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.Seq_Contact_Assign_Hist")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "CUSTOMER_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    @Column(name = "CAMPAIGN_ID")
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }


    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    @Column(name = "GROUP_CODE")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    @Column(name = "CONTACT_INFO")
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    @Column(name = "STATIS_INFO")
    public String getStatisInfo() {
        return statisInfo;
    }

    public void setStatisInfo(String statisInfo) {
        this.statisInfo = statisInfo;
    }

    @Column(name = "JOBID")
    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    @Column(name = "QUEUEID")
    public String getQueueid() {
        return queueid;
    }

    public void setQueueid(String queueid) {
        this.queueid = queueid;
    }

    @Column(name = "PRIORITY")
    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }


    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "DATA_SOURCE")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }



    @Column(name = "ASSIGN_GROUP")
    public String getAssignGroup() {
        return assignGroup;
    }

    public void setAssignGroup(String assignGroup) {
        this.assignGroup = assignGroup;
    }
    @Column(name = "ASSIGN_GROUP_USER")
    public String getAssignGroupUser() {
        return assignGroupUser;
    }

    public void setAssignGroupUser(String assignGroupUser) {
        this.assignGroupUser = assignGroupUser;
    }
    @Column(name = "ASSIGN_GROUP_TIME")
    public Date getAssignGroupTime() {
        return assignGroupTime;
    }

    public void setAssignGroupTime(Date assignGroupTime) {
        this.assignGroupTime = assignGroupTime;
    }
    @Column(name = "ASSIGN_AGENT")
    public String getAssignAgent() {
        return assignAgent;
    }

    public void setAssignAgent(String assignAgent) {
        this.assignAgent = assignAgent;
    }
    @Column(name = "ASSIGN_USER")
    public String getAssignUser() {
        return assignUser;
    }


    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    @Column(name = "ASSIGN_TIME")
    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    @Column(name = "LEAD_ID")
    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
