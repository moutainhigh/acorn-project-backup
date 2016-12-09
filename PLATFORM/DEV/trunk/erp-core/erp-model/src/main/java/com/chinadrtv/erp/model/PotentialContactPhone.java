package com.chinadrtv.erp.model;

import javax.persistence.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "POTENTIAL_CONTACT_PHONE", schema = "ACOAPP_MARKETING")
@Entity
public class PotentialContactPhone implements java.io.Serializable {
   
	private static final long serialVersionUID = -3954485612255786795L;
	private Long id;
    private String phone1;
    private String phone2;
    private String phone3;
    private String phoneNum;
    private String phoneTypeId;
    private String prmphn;
    private String potentialContactId;
    private PotentialContact potentialContact;

    private Date lastCallDate;
    private Integer callCount;
    private Integer black;
    
    private String phone_mask;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POTENTIAL_CONTACT_PHONE")
    @SequenceGenerator(name = "SEQ_POTENTIAL_CONTACT_PHONE", sequenceName = "ACOAPP_MARKETING.SEQ_POTENTIAL_CONTACT_PHONE", allocationSize=1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PHONE1", length = 10)
    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Column(name = "PHONE2", length = 20)
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Column(name = "PHONE3", length = 10)
    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    @Column(name = "PHONE_NUM", length = 40)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name = "PHONE_TYPE_ID", length = 10)
    public String getPhoneTypeId() {
        return phoneTypeId;
    }

    public void setPhoneTypeId(String phoneTypeId) {
        this.phoneTypeId = phoneTypeId;
    }

    @Column(name = "IS_DEFAULT", length = 1)
    public String getPrmphn() {
        return prmphn;
    }

    public void setPrmphn(String prmphn) {
        this.prmphn = prmphn;
    }

    @Column(name = "POTENTIAL_CONTACT_ID", length = 16)
    public String getPotentialContactId() {
        return potentialContactId;
    }

    public void setPotentialContactId(String potentialContactId) {
        this.potentialContactId = potentialContactId;
    }

    @ManyToOne
    @JoinColumn(name="POTENTIAL_CONTACT_ID", insertable = false, updatable = false)
	public PotentialContact getPotentialContact() {
		return potentialContact;
	}

	public void setPotentialContact(PotentialContact potentialContact) {
		this.potentialContact = potentialContact;
	}

    @Column(name = "LAST_CALL_DATE", length = 20)
    public Date getLastCallDate() {
        return lastCallDate;
    }

    public void setLastCallDate(Date lastCallDate) {
        this.lastCallDate = lastCallDate;
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
    
    
	/**
	 * @return the phone_mask
	 */
    @Transient
	public String getPhone_mask() {
		return phone2;
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

	/** 
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phone1 == null) ? 0 : phone1.hashCode());
		result = prime * result + ((phone2 == null) ? 0 : phone2.hashCode());
		result = prime * result + ((phone3 == null) ? 0 : phone3.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
		result = prime * result + ((phoneTypeId == null) ? 0 : phoneTypeId.hashCode());
		result = prime * result + ((potentialContact == null) ? 0 : potentialContact.hashCode());
		result = prime * result + ((potentialContactId == null) ? 0 : potentialContactId.hashCode());
		result = prime * result + ((prmphn == null) ? 0 : prmphn.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PotentialContactPhone other = (PotentialContactPhone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phone1 == null) {
			if (other.phone1 != null)
				return false;
		} else if (!phone1.equals(other.phone1))
			return false;
		if (phone2 == null) {
			if (other.phone2 != null)
				return false;
		} else if (!phone2.equals(other.phone2))
			return false;
		if (phone3 == null) {
			if (other.phone3 != null)
				return false;
		} else if (!phone3.equals(other.phone3))
			return false;
		if (phoneNum == null) {
			if (other.phoneNum != null)
				return false;
		} else if (!phoneNum.equals(other.phoneNum))
			return false;
		if (phoneTypeId == null) {
			if (other.phoneTypeId != null)
				return false;
		} else if (!phoneTypeId.equals(other.phoneTypeId))
			return false;
		if (potentialContact == null) {
			if (other.potentialContact != null)
				return false;
		} else if (!potentialContact.equals(other.potentialContact))
			return false;
		if (potentialContactId == null) {
			if (other.potentialContactId != null)
				return false;
		} else if (!potentialContactId.equals(other.potentialContactId))
			return false;
		if (prmphn == null) {
			if (other.prmphn != null)
				return false;
		} else if (!prmphn.equals(other.prmphn))
			return false;
		return true;
	}
}
