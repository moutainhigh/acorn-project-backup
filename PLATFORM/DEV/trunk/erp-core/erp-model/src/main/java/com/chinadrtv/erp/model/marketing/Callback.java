package com.chinadrtv.erp.model.marketing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-19
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "CALLBACK", schema = "ACOAPP_MARKETING")
public class Callback implements Serializable {
    private  Long callbackId;//			回呼单ID
    private String contactId;//			客户编号
    private String usrId;   //				座席编号
    private String usrGrp;   //				座席组编号	
    private String orderId;  //			订单编号
    private String status; //		状态1：客户不在、2：联系不上、3：不订购、4：订购5：咨询、6：投诉、7：骚扰电话、8：取消订单9：追加订单
    private Date requiredt; //需要回呼的日期
    private String priority;//	优先级100：普通、150：紧急
    private String flag;//	处理标志：0：未处理、1：正在处理、2：处理完毕
    private String phn1;//区号
    private String phn2;//	电话号码
    private String phn3; //分机号码
    private String remark; //备注
    private String name;//客户姓名
    private String mediaplanId;//媒体调查编号
    private String mediaprodId;//媒体产品编号
    private Date crdt;//		CALLBACK创建日期
    private String dnis; //被叫号码
    private String areacode; //所属电话中心
    private String type;//	进线类型
    private String mdusr;//回复坐席编号
    private Date mddt;//	回复日期
    private String ani; //主叫号码
    private String groupAssigner;
    private String userAssigner;
    private String latentcontactId; //潜在客户编号
    private String caseId;
    private String dbusrId; //分配工号
    private String rdbusrId;//被分配工号
    private Date  dbdt;//分配日期
    private String opusr;//	处理人工号
    private Date firstdt;//第一次分配时间
    private String firstusrId; //第一次分配座席
    private Long leadInteractionId;
	private Long allocateCount;
    private Boolean allocatedManual;
	private Long taskId;
    private String batchId;
    private String acdGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_callback")
    @SequenceGenerator(name = "seq_callback", sequenceName = "ACOAPP_MARKETING.seq_callback", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 8)
    public Long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    @Column(name = "BATCH_ID", length = 50)
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Column(name = "CONTACT_ID")
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    @Column(name = "USRID")
    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "REQUIREDT")
    public Date getRequiredt() {
        return requiredt;
    }

    public void setRequiredt(Date requiredt) {
        this.requiredt = requiredt;
    }

    @Column(name = "PRIORITY")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "PHN1")
    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }
    @Column(name = "PHN2")
    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }
    @Column(name = "PHN3")
    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "MEDIAPLAN_ID")
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

    @Column(name = "CRDT")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }
    @Column(name = "DNIS")
    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }
    @Column(name = "AREACODE")
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "MDUSR")
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }
    @Column(name = "MDDT")
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }
    @Column(name = "ANI")
    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }
    @Column(name = "LATENT_CONTACT_ID")
    public String getLatentcontactId() {
        return latentcontactId;
    }

    public void setLatentcontactId(String latentcontactId) {
        this.latentcontactId = latentcontactId;
    }
    @Column(name = "CASE_ID")
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    @Column(name = "DBUSRID")
    public String getDbusrId() {
        return dbusrId;
    }

    public void setDbusrId(String dbusrId) {
        this.dbusrId = dbusrId;
    }
    @Column(name = "RDBUSRID")
    public String getRdbusrId() {
        return rdbusrId;
    }

    public void setRdbusrId(String rdbusrId) {
        this.rdbusrId = rdbusrId;
    }
    @Column(name = "DBDT")
    public Date getDbdt() {
        return dbdt;
    }

    public void setDbdt(Date dbdt) {
        this.dbdt = dbdt;
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
	
	@Column(name="USRGRP", length=50)
	public String getUsrGrp() {
		return usrGrp;
	}

	public void setUsrGrp(String usrGrp) {
		this.usrGrp = usrGrp;
	}

	@Column(name = "LEAD_INTERACTION_ID")
	public Long getLeadInteractionId() {
		return leadInteractionId;
	}

	public void setLeadInteractionId(Long leadInteractionId) {
		this.leadInteractionId = leadInteractionId;
	}

	@Column(name = "ALLOCATED_NUMBER")
	public Long getAllocateCount() {
		return allocateCount;
	}

	public void setAllocateCount(Long allocateCount) {
		this.allocateCount = allocateCount;
	}

	@Column(name = "TASK_ID")
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

    @Column(name = "ALLOCATED_MANUAL")
    public Boolean getAllocatedManual() {
        return allocatedManual;
    }

    public void setAllocatedManual(Boolean allocatedManual) {
        this.allocatedManual = allocatedManual;
    }

    @Column(name = "ACDGROUP")
    public String getAcdGroup() {
        return acdGroup;
    }

    public void setAcdGroup(String acdGroup) {
        this.acdGroup = acdGroup;
    }
}
