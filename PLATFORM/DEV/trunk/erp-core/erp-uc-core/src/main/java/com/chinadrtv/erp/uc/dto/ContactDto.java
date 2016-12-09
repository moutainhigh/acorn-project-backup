/*
 * @(#)ContactDto.java 1.0 2013-5-23下午4:41:47
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.Contact;

import java.util.Date;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-23 下午4:41:47 
 * 
 */
public class ContactDto extends Contact {

	private static final long serialVersionUID = 7631785839792466303L;
	private String contactid;
	private String name;
	private String sex;
	private String title;
	private String dept;
	private String contacttype;
	private String email;
	private String webaddr;
	private java.util.Date crdt;
	private java.util.Date crtm;
	private String crusr;
	private java.util.Date mddt;
	private java.util.Date mdtm;
	private String mdusr;
	private String entityid;
	private java.util.Date birthday;
	private String ages;
	private String education;
	private String income;
	private String marriage;
	private String occupation;
	private String consumer;
	private String pin;
	private Long total;
	private String purpose;
	private String netcontactid;
	private String jifen;
	private String areacode;
	private Long ticketvalue;
	private String fancy;
	private String idcardFlag;
	private String attitude;
	private String membertype;
	private String memberlevel;
	private java.util.Date lastdate;
	private Integer totalfrequency;
	private Long totalmonetary;
	private String sundept;
	private Integer credit;
	private String customersource;
	private String datatype;
	private String car;
	private String creditcard;
	private String children;
	private java.util.Date childrenage;
	private java.lang.Integer carmoney1;
	private java.lang.Integer carmoney2;
	private String dnis;
	private String caseid;
    private String isHasElder;
    private java.util.Date elderBirthday;
    private String occupationStatus;
	
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getContacttype() {
		return contacttype;
	}
	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebaddr() {
		return webaddr;
	}
	public void setWebaddr(String webaddr) {
		this.webaddr = webaddr;
	}
	public java.util.Date getCrdt() {
		return crdt;
	}
	public void setCrdt(java.util.Date crdt) {
		this.crdt = crdt;
	}
	public java.util.Date getCrtm() {
		return crtm;
	}
	public void setCrtm(java.util.Date crtm) {
		this.crtm = crtm;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public java.util.Date getMddt() {
		return mddt;
	}
	public void setMddt(java.util.Date mddt) {
		this.mddt = mddt;
	}
	public java.util.Date getMdtm() {
		return mdtm;
	}
	public void setMdtm(java.util.Date mdtm) {
		this.mdtm = mdtm;
	}
	public String getMdusr() {
		return mdusr;
	}
	public void setMdusr(String mdusr) {
		this.mdusr = mdusr;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getAges() {
		return ages;
	}
	public void setAges(String ages) {
		this.ages = ages;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getNetcontactid() {
		return netcontactid;
	}
	public void setNetcontactid(String netcontactid) {
		this.netcontactid = netcontactid;
	}
	public String getJifen() {
		return jifen;
	}
	public void setJifen(String jifen) {
		this.jifen = jifen;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public Long getTicketvalue() {
		return ticketvalue;
	}
	public void setTicketvalue(Long ticketvalue) {
		this.ticketvalue = ticketvalue;
	}
	public String getFancy() {
		return fancy;
	}
	public void setFancy(String fancy) {
		this.fancy = fancy;
	}
	public String getIdcardFlag() {
		return idcardFlag;
	}
	public void setIdcardFlag(String idcardFlag) {
		this.idcardFlag = idcardFlag;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getMembertype() {
		return membertype;
	}
	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	public String getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}
	public java.util.Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(java.util.Date lastdate) {
		this.lastdate = lastdate;
	}
	public Integer getTotalfrequency() {
		return totalfrequency;
	}
	public void setTotalfrequency(Integer totalfrequency) {
		this.totalfrequency = totalfrequency;
	}
	public Long getTotalmonetary() {
		return totalmonetary;
	}
	public void setTotalmonetary(Long totalmonetary) {
		this.totalmonetary = totalmonetary;
	}
	public String getSundept() {
		return sundept;
	}
	public void setSundept(String sundept) {
		this.sundept = sundept;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public String getCustomersource() {
		return customersource;
	}
	public void setCustomersource(String customersource) {
		this.customersource = customersource;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public java.util.Date getChildrenage() {
		return childrenage;
	}
	public void setChildrenage(java.util.Date childrenage) {
		this.childrenage = childrenage;
	}
	public java.lang.Integer getCarmoney1() {
		return carmoney1;
	}
	public void setCarmoney1(java.lang.Integer carmoney1) {
		this.carmoney1 = carmoney1;
	}
	public java.lang.Integer getCarmoney2() {
		return carmoney2;
	}
	public void setCarmoney2(java.lang.Integer carmoney2) {
		this.carmoney2 = carmoney2;
	}
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

    public Date getElderBirthday() {
        return elderBirthday;
    }

    public void setElderBirthday(Date elderBirthday) {
        this.elderBirthday = elderBirthday;
    }

    public String getHasElder() {
        return isHasElder;
    }

    public void setHasElder(String hasElder) {
        isHasElder = hasElder;
    }

    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

    /**
	 * <p>Title: hashCode</p>
	 * <p>Description: 请慎重修改此方法</p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ages == null) ? 0 : ages.hashCode());
		result = prime * result + ((areacode == null) ? 0 : areacode.hashCode());
		result = prime * result + ((attitude == null) ? 0 : attitude.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((carmoney1 == null) ? 0 : carmoney1.hashCode());
		result = prime * result + ((carmoney2 == null) ? 0 : carmoney2.hashCode());
		result = prime * result + ((caseid == null) ? 0 : caseid.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((childrenage == null) ? 0 : childrenage.hashCode());
		result = prime * result + ((consumer == null) ? 0 : consumer.hashCode());
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((contacttype == null) ? 0 : contacttype.hashCode());
		result = prime * result + ((crdt == null) ? 0 : crdt.hashCode());
		result = prime * result + ((credit == null) ? 0 : credit.hashCode());
		result = prime * result + ((creditcard == null) ? 0 : creditcard.hashCode());
		result = prime * result + ((crtm == null) ? 0 : crtm.hashCode());
		result = prime * result + ((crusr == null) ? 0 : crusr.hashCode());
		result = prime * result + ((customersource == null) ? 0 : customersource.hashCode());
		result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((dnis == null) ? 0 : dnis.hashCode());
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((entityid == null) ? 0 : entityid.hashCode());
		result = prime * result + ((fancy == null) ? 0 : fancy.hashCode());
		result = prime * result + ((idcardFlag == null) ? 0 : idcardFlag.hashCode());
		result = prime * result + ((income == null) ? 0 : income.hashCode());
		result = prime * result + ((jifen == null) ? 0 : jifen.hashCode());
		result = prime * result + ((lastdate == null) ? 0 : lastdate.hashCode());
		result = prime * result + ((marriage == null) ? 0 : marriage.hashCode());
		result = prime * result + ((mddt == null) ? 0 : mddt.hashCode());
		result = prime * result + ((mdtm == null) ? 0 : mdtm.hashCode());
		result = prime * result + ((mdusr == null) ? 0 : mdusr.hashCode());
		result = prime * result + ((memberlevel == null) ? 0 : memberlevel.hashCode());
		result = prime * result + ((membertype == null) ? 0 : membertype.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((netcontactid == null) ? 0 : netcontactid.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		result = prime * result + ((purpose == null) ? 0 : purpose.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((sundept == null) ? 0 : sundept.hashCode());
		result = prime * result + ((ticketvalue == null) ? 0 : ticketvalue.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalfrequency == null) ? 0 : totalfrequency.hashCode());
		result = prime * result + ((totalmonetary == null) ? 0 : totalmonetary.hashCode());
		result = prime * result + ((webaddr == null) ? 0 : webaddr.hashCode());
		result = prime * result + ((elderBirthday == null) ? 0 : elderBirthday.hashCode());
		result = prime * result + ((isHasElder == null) ? 0 : isHasElder.hashCode());
		result = prime * result + ((occupationStatus == null) ? 0 : occupationStatus.hashCode());
		return result;
	}
	/** 
	 * <p>Title: equals</p>
	 * <p>Description: 请慎重修改此方法</p>
	 * @param obj
	 * @return Boolean 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactDto other = (ContactDto) obj;
		if (ages == null) {
			if (other.ages != null)
				return false;
		} else if (!ages.equals(other.ages))
			return false;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (attitude == null) {
			if (other.attitude != null)
				return false;
		} else if (!attitude.equals(other.attitude))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (carmoney1 == null) {
			if (other.carmoney1 != null)
				return false;
		} else if (!carmoney1.equals(other.carmoney1))
			return false;
		if (carmoney2 == null) {
			if (other.carmoney2 != null)
				return false;
		} else if (!carmoney2.equals(other.carmoney2))
			return false;
		if (caseid == null) {
			if (other.caseid != null)
				return false;
		} else if (!caseid.equals(other.caseid))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (childrenage == null) {
			if (other.childrenage != null)
				return false;
		} else if (!childrenage.equals(other.childrenage))
			return false;
		if (consumer == null) {
			if (other.consumer != null)
				return false;
		} else if (!consumer.equals(other.consumer))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (contacttype == null) {
			if (other.contacttype != null)
				return false;
		} else if (!contacttype.equals(other.contacttype))
			return false;
		if (crdt == null) {
			if (other.crdt != null)
				return false;
		} else if (!crdt.equals(other.crdt))
			return false;
		if (credit == null) {
			if (other.credit != null)
				return false;
		} else if (!credit.equals(other.credit))
			return false;
		if (creditcard == null) {
			if (other.creditcard != null)
				return false;
		} else if (!creditcard.equals(other.creditcard))
			return false;
		if (crtm == null) {
			if (other.crtm != null)
				return false;
		} else if (!crtm.equals(other.crtm))
			return false;
		if (crusr == null) {
			if (other.crusr != null)
				return false;
		} else if (!crusr.equals(other.crusr))
			return false;
		if (customersource == null) {
			if (other.customersource != null)
				return false;
		} else if (!customersource.equals(other.customersource))
			return false;
		if (datatype == null) {
			if (other.datatype != null)
				return false;
		} else if (!datatype.equals(other.datatype))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (dnis == null) {
			if (other.dnis != null)
				return false;
		} else if (!dnis.equals(other.dnis))
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (entityid == null) {
			if (other.entityid != null)
				return false;
		} else if (!entityid.equals(other.entityid))
			return false;
		if (fancy == null) {
			if (other.fancy != null)
				return false;
		} else if (!fancy.equals(other.fancy))
			return false;
		if (idcardFlag == null) {
			if (other.idcardFlag != null)
				return false;
		} else if (!idcardFlag.equals(other.idcardFlag))
			return false;
		if (income == null) {
			if (other.income != null)
				return false;
		} else if (!income.equals(other.income))
			return false;
		if (jifen == null) {
			if (other.jifen != null)
				return false;
		} else if (!jifen.equals(other.jifen))
			return false;
		if (lastdate == null) {
			if (other.lastdate != null)
				return false;
		} else if (!lastdate.equals(other.lastdate))
			return false;
		if (marriage == null) {
			if (other.marriage != null)
				return false;
		} else if (!marriage.equals(other.marriage))
			return false;
		if (mddt == null) {
			if (other.mddt != null)
				return false;
		} else if (!mddt.equals(other.mddt))
			return false;
		if (mdtm == null) {
			if (other.mdtm != null)
				return false;
		} else if (!mdtm.equals(other.mdtm))
			return false;
		if (mdusr == null) {
			if (other.mdusr != null)
				return false;
		} else if (!mdusr.equals(other.mdusr))
			return false;
		if (memberlevel == null) {
			if (other.memberlevel != null)
				return false;
		} else if (!memberlevel.equals(other.memberlevel))
			return false;
		if (membertype == null) {
			if (other.membertype != null)
				return false;
		} else if (!membertype.equals(other.membertype))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (netcontactid == null) {
			if (other.netcontactid != null)
				return false;
		} else if (!netcontactid.equals(other.netcontactid))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin))
			return false;
		if (purpose == null) {
			if (other.purpose != null)
				return false;
		} else if (!purpose.equals(other.purpose))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (sundept == null) {
			if (other.sundept != null)
				return false;
		} else if (!sundept.equals(other.sundept))
			return false;
		if (ticketvalue == null) {
			if (other.ticketvalue != null)
				return false;
		} else if (!ticketvalue.equals(other.ticketvalue))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (totalfrequency == null) {
			if (other.totalfrequency != null)
				return false;
		} else if (!totalfrequency.equals(other.totalfrequency))
			return false;
		if (totalmonetary == null) {
			if (other.totalmonetary != null)
				return false;
		} else if (!totalmonetary.equals(other.totalmonetary))
			return false;
		if (webaddr == null) {
			if (other.webaddr != null)
				return false;
		} else if (!webaddr.equals(other.webaddr))
			return false;
        if (elderBirthday == null) {
            if (other.elderBirthday != null)
                return false;
        } else if (!elderBirthday.equals(other.elderBirthday))
            return false;
        if (isHasElder == null) {
            if (other.isHasElder != null)
                return false;
        } else if (!isHasElder.equals(other.isHasElder))
            return false;
        if (occupationStatus == null) {
            if (other.occupationStatus != null)
                return false;
        } else if (!occupationStatus.equals(other.occupationStatus))
            return false;
		return true;
	}
}
