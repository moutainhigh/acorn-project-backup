/*
 * @(#)AddressDto.java 1.0 2013-5-3下午2:49:48
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.Address;

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
 * @since 2013-5-3 下午2:49:48 
 * 
 */
public class AddressDto {
	
	private String contactName;
	
    private String addressid;
    private String address; //完整地址包含四级地址
    private String addressDesc;//详细地址
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
	private Integer countyId;
	private Integer cityId;

    private String provinceName;
    private String cityName;
    private String countyName;
    private String areaName;
    private String receiveAddress;

    private Integer countySpellid;
    private Integer areaSpellid;

    private String instId;
    private String status;
    private String comment;

    private  String addressContactId; //address表中的contactiId

    private Integer auditState;

//    private Integer black;// 1代表是黑名单地址 空或者其他代表非黑名单地址

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public String getAddrtypid() {
		return addrtypid;
	}
	public void setAddrtypid(String addrtypid) {
		this.addrtypid = addrtypid;
	}
	public String getAdditionalinfo() {
		return additionalinfo;
	}
	public void setAdditionalinfo(String additionalinfo) {
		this.additionalinfo = additionalinfo;
	}
	public String getAddrconfirm() {
		return addrconfirm;
	}
	public void setAddrconfirm(String addrconfirm) {
		this.addrconfirm = addrconfirm;
	}
	public String getAddconfirmation() {
		return addconfirmation;
	}
	public void setAddconfirmation(String addconfirmation) {
		this.addconfirmation = addconfirmation;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	
	public String getAddressDesc() {
		return addressDesc;
	}
	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

    public Integer getCountySpellid() {
        return countySpellid;
    }

    public void setCountySpellid(Integer countySpellid) {
        this.countySpellid = countySpellid;
    }

    public Integer getAreaSpellid() {
        return areaSpellid;
    }

    public void setAreaSpellid(Integer areaSpellid) {
        this.areaSpellid = areaSpellid;
    }

//    public Integer getBlack() {
//        return black;
//    }
//
//    public void setBlack(Integer black) {
//        this.black = black;
//    }

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
		result = prime * result + ((addconfirmation == null) ? 0 : addconfirmation.hashCode());
		result = prime * result + ((additionalinfo == null) ? 0 : additionalinfo.hashCode());
		result = prime * result + ((addrconfirm == null) ? 0 : addrconfirm.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((addressid == null) ? 0 : addressid.hashCode());
		result = prime * result + ((addrtypid == null) ? 0 : addrtypid.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaid == null) ? 0 : areaid.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((countyId == null) ? 0 : countyId.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((isdefault == null) ? 0 : isdefault.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		AddressDto other = (AddressDto) obj;
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
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

    public String getAddressContactId() {
        return addressContactId;
    }

    public void setAddressContactId(String addressContactId) {
        this.addressContactId = addressContactId;
    }
}
