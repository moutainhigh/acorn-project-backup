package com.chinadrtv.erp.model;

import javax.persistence.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-26
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "PHONE", schema = "IAGENT")
public class Phone implements java.io.Serializable {
    // Fields
    private Long phoneid;
    private String contactid;
    private String phn1;
    private String phn2;
    private String phn3;
    private String entityid;
    private String phonetypid;
    private String prmphn;
    private String remark;
    private String contactRowid;
    private String phoneNum;

    private Date lastCallDate;
    private Date lastBIDate;
    private Boolean isAvailable;
    private Integer priority;
    private Integer callCount;
    private Integer black;
    private Integer state;
    private Integer phoneRank;
    private String phone_mask;

    @Id
    @Column(name = "PHONEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONE_SEQ")
    @SequenceGenerator(name = "PHONE_SEQ", sequenceName = "IAGENT.SEQPHONE",allocationSize=1)
    public Long getPhoneid() {
        return this.phoneid;
    }

    public void setPhoneid(Long phoneid) {
        this.phoneid = phoneid;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return this.contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PHN1", length = 10)
    public String getPhn1() {
        return this.phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    @Column(name = "PHN2", length = 20)
    public String getPhn2() {
        return this.phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    @Column(name = "PHN3", length = 20)
    public String getPhn3() {
        return this.phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    @Column(name = "ENTITYID", length = 16)
    public String getEntityid() {
        return this.entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @Column(name = "PHONETYPID", length = 10)
    public String getPhonetypid() {
        return this.phonetypid;
    }

    public void setPhonetypid(String phonetypid) {
        this.phonetypid = phonetypid;
    }

    @Column(name = "PRMPHN", length = 1)
    public String getPrmphn() {
        return this.prmphn;
    }

    public void setPrmphn(String prmphn) {
        this.prmphn = prmphn;
    }

    @Column(name = "REMARK", length = 50)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CONTACT_ROWID", length = 40)
    public String getContactRowid() {
        return this.contactRowid;
    }

    public void setContactRowid(String contactRowid) {
        this.contactRowid = contactRowid;
    }
    
    @Column(name = "PHONE_NUM", length = 20)
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

    @Column(name = "LAST_CALL_DATE", length = 20)
    public Date getLastCallDate() {
        return lastCallDate;
    }

    public void setLastCallDate(Date lastCallDate) {
        this.lastCallDate = lastCallDate;
    }

    @Column(name = "LAST_BI_UPDATE", length = 20)
    public Date getLastBIDate() {
        return lastBIDate;
    }

    public void setLastBIDate(Date lastBIDate) {
        this.lastBIDate = lastBIDate;
    }

    @Column(name = "IS_AVAILABLE", length = 1)
    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Column(name = "PRIORITY", length = 3)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "CALL_COUNT", length = 5)
    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    @Column(name = "IS_BLACK", length = 1)
    public Integer getBlack() {
        return black;
    }

    public void setBlack(Integer black) {
       this.black = black;
    }

    @Column(name = "STATE", length = 2)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    /**
	 * @return the phoneRank
	 */
	@Column(name = "phone_Rank")
	public Integer getPhoneRank() {
		return phoneRank;
	}

	/**
	 * @param phoneRank the phoneRank to set
	 */
	public void setPhoneRank(Integer phoneRank) {
		this.phoneRank = phoneRank;
	}

	
	/**
	 * @return the phone_mask
	 */
	@Transient
	public String getPhone_mask() {
		return phn2;
	}
	
	@Transient
	public String getPhoneMask() {
		return phone_mask;
	}

	/**
	 * @param phone_mask the phone_mask to set
	 */
	public void setPhone_mask(String phone_mask) {
		this.phone_mask = phone_mask;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((black == null) ? 0 : black.hashCode());
		result = prime * result + ((callCount == null) ? 0 : callCount.hashCode());
		result = prime * result + ((contactRowid == null) ? 0 : contactRowid.hashCode());
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((entityid == null) ? 0 : entityid.hashCode());
		result = prime * result + ((isAvailable == null) ? 0 : isAvailable.hashCode());
		result = prime * result + ((lastBIDate == null) ? 0 : lastBIDate.hashCode());
		result = prime * result + ((lastCallDate == null) ? 0 : lastCallDate.hashCode());
		result = prime * result + ((phn1 == null) ? 0 : phn1.hashCode());
		result = prime * result + ((phn2 == null) ? 0 : phn2.hashCode());
		result = prime * result + ((phn3 == null) ? 0 : phn3.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
		result = prime * result + ((phoneRank == null) ? 0 : phoneRank.hashCode());
		result = prime * result + ((phone_mask == null) ? 0 : phone_mask.hashCode());
		result = prime * result + ((phoneid == null) ? 0 : phoneid.hashCode());
		result = prime * result + ((phonetypid == null) ? 0 : phonetypid.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((prmphn == null) ? 0 : prmphn.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (black == null) {
			if (other.black != null)
				return false;
		} else if (!black.equals(other.black))
			return false;
		if (callCount == null) {
			if (other.callCount != null)
				return false;
		} else if (!callCount.equals(other.callCount))
			return false;
		if (contactRowid == null) {
			if (other.contactRowid != null)
				return false;
		} else if (!contactRowid.equals(other.contactRowid))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (isAvailable == null) {
			if (other.isAvailable != null)
				return false;
		} else if (!isAvailable.equals(other.isAvailable))
			return false;
		if (lastBIDate == null) {
			if (other.lastBIDate != null)
				return false;
		} else if (!lastBIDate.equals(other.lastBIDate))
			return false;
		if (lastCallDate == null) {
			if (other.lastCallDate != null)
				return false;
		} else if (!lastCallDate.equals(other.lastCallDate))
			return false;
		if (phn1 == null) {
			if (other.phn1 != null)
				return false;
		} else if (!phn1.equals(other.phn1))
			return false;
		if (phn2 == null) {
			if (other.phn2 != null)
				return false;
		} else if (!phn2.equals(other.phn2))
			return false;
		if (phn3 == null) {
			if (other.phn3 != null)
				return false;
		} else if (!phn3.equals(other.phn3))
			return false;
		if (phoneNum == null) {
			if (other.phoneNum != null)
				return false;
		} else if (!phoneNum.equals(other.phoneNum))
			return false;
		if (phoneRank == null) {
			if (other.phoneRank != null)
				return false;
		} else if (!phoneRank.equals(other.phoneRank))
			return false;
		if (phone_mask == null) {
			if (other.phone_mask != null)
				return false;
		} else if (!phone_mask.equals(other.phone_mask))
			return false;
		if (phoneid == null) {
			if (other.phoneid != null)
				return false;
		} else if (!phoneid.equals(other.phoneid))
			return false;
		if (phonetypid == null) {
			if (other.phonetypid != null)
				return false;
		} else if (!phonetypid.equals(other.phonetypid))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (prmphn == null) {
			if (other.prmphn != null)
				return false;
		} else if (!prmphn.equals(other.prmphn))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}
