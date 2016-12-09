package com.chinadrtv.erp.model.agent;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "AREA_ALL", schema = "ACOAPP_OMS")
@Entity
public class AreaAll implements Serializable {
    private String provid;

    @javax.persistence.Column(name = "PROVID")
    public String getProvid() {
        return provid;
    }

    public void setProvid(String provid) {
        this.provid = provid;
    }

    private int cityid;

    @javax.persistence.Column(name = "CITYID")
    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    private Integer countyid;

    @javax.persistence.Column(name = "COUNTYID")
    public Integer getCountyid() {
        return countyid;
    }

    public void setCountyid(Integer countyid) {
        this.countyid = countyid;
    }

    private Integer areaid;

    @Id
    @javax.persistence.Column(name = "AREAID")
    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    private String areaname;

    @javax.persistence.Column(name = "AREANAME")
    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    private String areakey;

    @javax.persistence.Column(name = "AREAKEY")
    public String getAreakey() {
        return areakey;
    }

    public void setAreakey(String areakey) {
        this.areakey = areakey;
    }

    private String flag;

    @javax.persistence.Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private String zipcode;

    @javax.persistence.Column(name = "ZIPCODE")
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    private String code;

    @javax.persistence.Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Integer spellid;

    @javax.persistence.Column(name = "SPELLID")
    public Integer getSpellid() {
        return spellid;
    }

    public void setSpellid(Integer spellid) {
        this.spellid = spellid;
    }

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
		result = prime * result + ((areaid == null) ? 0 : areaid.hashCode());
		result = prime * result + ((areakey == null) ? 0 : areakey.hashCode());
		result = prime * result + ((areaname == null) ? 0 : areaname.hashCode());
		result = prime * result + cityid;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((countyid == null) ? 0 : countyid.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((provid == null) ? 0 : provid.hashCode());
		result = prime * result + ((spellid == null) ? 0 : spellid.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
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
		AreaAll other = (AreaAll) obj;
		if (areaid == null) {
			if (other.areaid != null)
				return false;
		} else if (!areaid.equals(other.areaid))
			return false;
		if (areakey == null) {
			if (other.areakey != null)
				return false;
		} else if (!areakey.equals(other.areakey))
			return false;
		if (areaname == null) {
			if (other.areaname != null)
				return false;
		} else if (!areaname.equals(other.areaname))
			return false;
		if (cityid != other.cityid)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (countyid == null) {
			if (other.countyid != null)
				return false;
		} else if (!countyid.equals(other.countyid))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (provid == null) {
			if (other.provid != null)
				return false;
		} else if (!provid.equals(other.provid))
			return false;
		if (spellid == null) {
			if (other.spellid != null)
				return false;
		} else if (!spellid.equals(other.spellid))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null)
				return false;
		} else if (!zipcode.equals(other.zipcode))
			return false;
		return true;
	}


}
