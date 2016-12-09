package com.chinadrtv.erp.model.agent;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "COUNTY_ALL", schema = "ACOAPP_OMS")
@Entity
public class CountyAll implements Serializable {
    private String provid;

    @javax.persistence.Column(name = "PROVID")
    public String getProvid() {
        return provid;
    }

    public void setProvid(String provid) {
        this.provid = provid;
    }

    private Integer cityid;

    @javax.persistence.Column(name = "CITYID")
    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    private Integer countyid;

    @Id
    @javax.persistence.Column(name = "COUNTYID")
    public Integer getCountyid() {
        return countyid;
    }

    public void setCountyid(Integer countyid) {
        this.countyid = countyid;
    }

    private String countyname;

    @javax.persistence.Column(name = "COUNTYNAME")
    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    private String countykey;

    @javax.persistence.Column(name = "COUNTYKEY")
    public String getCountykey() {
        return countykey;
    }

    public void setCountykey(String countykey) {
        this.countykey = countykey;
    }

    private String countFlag;

    @javax.persistence.Column(name = "COUNT_FLAG")
    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
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

    private String areacode;

    @javax.persistence.Column(name = "AREACODE")
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
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
		CountyAll other = (CountyAll) obj;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (cityid == null) {
			if (other.cityid != null)
				return false;
		} else if (!cityid.equals(other.cityid))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (countFlag == null) {
			if (other.countFlag != null)
				return false;
		} else if (!countFlag.equals(other.countFlag))
			return false;
		if (countyid == null) {
			if (other.countyid != null)
				return false;
		} else if (!countyid.equals(other.countyid))
			return false;
		if (countykey == null) {
			if (other.countykey != null)
				return false;
		} else if (!countykey.equals(other.countykey))
			return false;
		if (countyname == null) {
			if (other.countyname != null)
				return false;
		} else if (!countyname.equals(other.countyname))
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

    /** 
	 * <p>Title: hashCode</p>
	 * <p>Description: 您不能随便重写该方法，该方法很重要</p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areacode == null) ? 0 : areacode.hashCode());
		result = prime * result + ((cityid == null) ? 0 : cityid.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((countFlag == null) ? 0 : countFlag.hashCode());
		result = prime * result + ((countyid == null) ? 0 : countyid.hashCode());
		result = prime * result + ((countykey == null) ? 0 : countykey.hashCode());
		result = prime * result + ((countyname == null) ? 0 : countyname.hashCode());
		result = prime * result + ((provid == null) ? 0 : provid.hashCode());
		result = prime * result + ((spellid == null) ? 0 : spellid.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}
}
