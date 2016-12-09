package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-12-26
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ADDRESS", schema = "IAGENT")
public class Address implements java.io.Serializable {

    private String addressid;
    private String address;

    private String state;
    private String city;
    private String area;
    private String zip;
    private String contactid;
    private String addrtypid;
    private String additionalinfo;
    private String addrconfirm;
    private String addconfirmation;
    private String flag;
    private Integer areaid;
    private String isdefault;
//    private Integer black;

    @Id
    @Column(name = "ADDRESSID", length = 16)

    public String getAddressid() {
        return this.addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    @Column(name = "ADDRESS", length = 128)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "STATE", length = 10)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "CITY", length = 30)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "AREA", length = 10)
    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "ZIP", length = 10)
    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name = "CONTACTID", unique = true, length = 16)
    public String getContactid() {
        return this.contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "ADDRTYPID", length = 16)
    public String getAddrtypid() {
        return this.addrtypid;
    }

    public void setAddrtypid(String addrtypid) {
        this.addrtypid = addrtypid;
    }

    @Column(name = "ADDITIONALINFO", length = 10)
    public String getAdditionalinfo() {
        return this.additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    @Column(name = "ADDRCONFIRM", length = 10)
    public String getAddrconfirm() {
        return this.addrconfirm;
    }

    public void setAddrconfirm(String addrconfirm) {
        this.addrconfirm = addrconfirm;
    }

    @Column(name = "ADDCONFIRMATION", length = 2)
    public String getAddconfirmation() {
        return this.addconfirmation;
    }

    public void setAddconfirmation(String addconfirmation) {
        this.addconfirmation = addconfirmation;
    }

    @Column(name = "FLAG", length = 8)
    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "AREAID", precision = 7, scale = 0)
    public Integer getAreaid() {
        return this.areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    @Column(name = "ISDEFAULT", length = 2)
    public String getIsdefault() {
        return this.isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

//    @Column(name = "IS_BLACK", length = 1)
//    public Integer getBlack() {
//        return this.black;
//    }
//
//    public void setBlack(Integer black) {
//        this.black = black;
//    }

    /**
	 * <p>Title: hashCode</p>
	 * <p>Description: 您不能随便重写该方法，该方法很重要</p>
	 * @return Integer
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addconfirmation == null) ? 0 : addconfirmation.hashCode());
		result = prime * result + ((additionalinfo == null) ? 0 : additionalinfo.hashCode());
		result = prime * result + ((addrconfirm == null) ? 0 : addrconfirm.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((addressid == null) ? 0 : addressid.hashCode());
		result = prime * result + ((addrtypid == null) ? 0 : addrtypid.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaid == null) ? 0 : areaid.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((isdefault == null) ? 0 : isdefault.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description:</p>
	 * @param obj
	 * @return Boolean
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
		Address other = (Address) obj;
		if (addconfirmation == null) {
			if (other.addconfirmation != null)
				return false;
		} else if (!addconfirmation.equals(other.addconfirmation))
			return false;
		if (additionalinfo == null) {
			if (other.additionalinfo != null)
				return false;
		} else if (!additionalinfo.equals(other.additionalinfo))
			return false;
		if (addrconfirm == null) {
			if (other.addrconfirm != null)
				return false;
		} else if (!addrconfirm.equals(other.addrconfirm))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (addressid == null) {
			if (other.addressid != null)
				return false;
		} else if (!addressid.equals(other.addressid))
			return false;
		if (addrtypid == null) {
			if (other.addrtypid != null)
				return false;
		} else if (!addrtypid.equals(other.addrtypid))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaid == null) {
			if (other.areaid != null)
				return false;
		} else if (!areaid.equals(other.areaid))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (isdefault == null) {
			if (other.isdefault != null)
				return false;
		} else if (!isdefault.equals(other.isdefault))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
}
