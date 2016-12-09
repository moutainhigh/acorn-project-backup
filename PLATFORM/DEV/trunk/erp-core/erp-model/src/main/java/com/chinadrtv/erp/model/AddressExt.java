package com.chinadrtv.erp.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "ADDRESS_EXT", schema = "IAGENT")
@Entity
public class AddressExt  implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String   addressId;
	private Date   	 uptime;
	private String addressDesc;
	private String contactId;
	private String addressType;
	private String areaName;

	private Province province;
	private CityAll city;
	private CountyAll county;
	private AreaAll area;
	private Integer auditState;
	
	
	@Id
    @Column(name = "ADDRESSID")
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	@javax.persistence.Column(name = "UPTIME")
	@Basic
	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	@javax.persistence.Column(name = "ADDRDESC")
	@Basic
	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	@javax.persistence.Column(name = "CONTACTID")
	@Basic
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@javax.persistence.Column(name = "ADDRESS_TYPE")
	@Basic
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	@javax.persistence.Column(name = "AREANAME")
	@Basic
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "PROVINCEID",referencedColumnName="PROVINCEID")
	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "CITYID",referencedColumnName="CITYID")
	public CityAll getCity() {
		return city;
	}

	public void setCity(CityAll city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "COUNTYID",referencedColumnName="COUNTYID")
	public CountyAll getCounty() {
		return county;
	}

	public void setCounty(CountyAll county) {
		this.county = county;
	}


	@ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "AREAID",referencedColumnName="AREAID")
	public AreaAll getArea() {
		return area;
	}

	public void setArea(AreaAll area) {
		this.area = area;
	}

	@Column(name = "AUDIT_STATE", length = 2)
	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressDesc == null) ? 0 : addressDesc.hashCode());
		result = prime * result + ((addressId == null) ? 0 : addressId.hashCode());
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((auditState == null) ? 0 : auditState.hashCode());
		result = prime * result + ((uptime == null) ? 0 : uptime.hashCode());
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
		AddressExt other = (AddressExt) obj;
		if (addressDesc == null) {
			if (other.addressDesc != null)
				return false;
		} else if (!addressDesc.equals(other.addressDesc))
			return false;
		if (addressId == null) {
			if (other.addressId != null)
				return false;
		} else if (!addressId.equals(other.addressId))
			return false;
		if (addressType == null) {
			if (other.addressType != null)
				return false;
		} else if (!addressType.equals(other.addressType))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (auditState == null) {
			if (other.auditState != null)
				return false;
		} else if (!auditState.equals(other.auditState))
			return false;
		if (uptime == null) {
			if (other.uptime != null)
				return false;
		} else if (!uptime.equals(other.uptime))
			return false;
		return true;
	}

}
