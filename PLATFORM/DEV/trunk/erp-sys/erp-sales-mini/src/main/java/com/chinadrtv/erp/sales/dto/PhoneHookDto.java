package com.chinadrtv.erp.sales.dto;

import java.util.Date;

/**
 * 坐席挂机参数
 * 
 * @author haoleitao
 *
 */
public class PhoneHookDto {
	private Boolean isContact; //是否在联系
	private String contactTime; //联系时间
	private String remark; //备注
	private Long h_leadId; //线索ID
	private String h_instId;//任务ID
	private String h_pdCustomerId; //计数
	private String h_contactId;//联系人ID
	private Long h_campaignId; //营销计划
	private Long h_ispotential;//客户类型
    private String ani; //主叫号
    private String dani; //被叫号
	private String usrId;
	private Boolean isOutbound;
    private Long timerLength ;
    private Integer boundType; //电话呼出类型
    private String connId; //cti流水号
    private Date ctiedt;
    private Date ctisdt;
    private Integer reson;//挂机原因
    private String callResult;
    private String callType;
	public Boolean getIsContact() {
		return isContact;
	}
	public void setIsContact(Boolean isContact) {
		this.isContact = isContact;
	}
	public String getContactTime() {
		return contactTime;
	}
	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getH_leadId() {
		return h_leadId;
	}
	public void setH_leadId(Long h_leadId) {
		this.h_leadId = h_leadId;
	}
	public String getH_instId() {
		return h_instId;
	}
	public void setH_instId(String h_instId) {
		this.h_instId = h_instId;
	}
	public String getH_pdCustomerId() {
		return h_pdCustomerId;
	}
	public void setH_pdCustomerId(String h_pdCustomerId) {
		this.h_pdCustomerId = h_pdCustomerId;
	}
	public String getH_contactId() {
		return h_contactId;
	}
	public void setH_contactId(String h_contactId) {
		this.h_contactId = h_contactId;
	}
	public Long getH_campaignId() {
		return h_campaignId;
	}
	public void setH_campaignId(Long h_campaignId) {
		this.h_campaignId = h_campaignId;
	}
	public Long getH_ispotential() {
		return h_ispotential;
	}
	public void setH_ispotential(Long h_ispotential) {
		this.h_ispotential = h_ispotential;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public Boolean getIsOutbound() {
		return isOutbound;
	}
	public void setIsOutbound(Boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getDani() {
        return dani;
    }

    public void setDani(String dani) {
        this.dani = dani;
    }

    public Long getTimerLength() {
        return timerLength;
    }

    public void setTimerLength(Long timerLength) {
        this.timerLength = timerLength;
    }

    public Integer getBoundType() {
        return boundType;
    }

    public void setBoundType(Integer boundType) {
        this.boundType = boundType;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public Date getCtiedt() {
        return ctiedt;
    }

    public void setCtiedt(Date ctiedt) {
        this.ctiedt = ctiedt;
    }

    public Date getCtisdt() {
        return ctisdt;
    }

    public void setCtisdt(Date ctisdt) {
        this.ctisdt = ctisdt;
    }

    public Integer getReson() {
        return reson;
    }

    public void setReson(Integer reson) {
        this.reson = reson;
    }

    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
