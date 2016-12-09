package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:手机归属地</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-6-4 下午1:53:30 
 *
 */
@Entity
@Table(name = "MPZONE", schema = "IAGENT")
public class MpZone implements java.io.Serializable {

	private static final long serialVersionUID = -4129328320569993420L;
	
	private String zoneid;
	private String province;
	private String city;
	private String cardtype;
	private String areacode;

	// Constructors

	/** default constructor */
	public MpZone() {
	}

	/** full constructor */
	public MpZone(String province, String city, String cardtype, String areacode) {
		this.province = province;
		this.city = city;
		this.cardtype = cardtype;
		this.areacode = areacode;
	}

	@Id
	@Column(name = "ZONEID", nullable = false, length = 16)
	public String getZoneid() {
		return this.zoneid;
	}

	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "CARDTYPE", length = 40)
	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	@Column(name = "AREACODE", length = 6)
	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	/** 
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return Integer
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areacode == null) ? 0 : areacode.hashCode());
		result = prime * result + ((cardtype == null) ? 0 : cardtype.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((zoneid == null) ? 0 : zoneid.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
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
		MpZone other = (MpZone) obj;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (cardtype == null) {
			if (other.cardtype != null)
				return false;
		} else if (!cardtype.equals(other.cardtype))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (zoneid == null) {
			if (other.zoneid != null)
				return false;
		} else if (!zoneid.equals(other.zoneid))
			return false;
		return true;
	}

}