package com.chinadrtv.erp.model.agent;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "CITY_ALL", schema = "ACOAPP_OMS")
@Entity
public class CityAll implements Serializable {
    private String provid;

    @javax.persistence.Column(name = "PROVID")
    public String getProvid() {
        return provid;
    }

    public void setProvid(String provid) {
        this.provid = provid;
    }

    private Integer cityid;

    @Id
    @javax.persistence.Column(name = "CITYID")
    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    private String cityname;

    @javax.persistence.Column(name = "CITYNAME")
    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    private String citykey;

    @javax.persistence.Column(name = "CITYKEY")
    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    private String cityFlag;

    @javax.persistence.Column(name = "CITY_FLAG")
    public String getCityFlag() {
        return cityFlag;
    }

    public void setCityFlag(String cityFlag) {
        this.cityFlag = cityFlag;
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
		CityAll other = (CityAll) obj;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (cityFlag == null) {
			if (other.cityFlag != null)
				return false;
		} else if (!cityFlag.equals(other.cityFlag))
			return false;
		if (cityid == null) {
			if (other.cityid != null)
				return false;
		} else if (!cityid.equals(other.cityid))
			return false;
		if (citykey == null) {
			if (other.citykey != null)
				return false;
		} else if (!citykey.equals(other.citykey))
			return false;
		if (cityname == null) {
			if (other.cityname != null)
				return false;
		} else if (!cityname.equals(other.cityname))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		result = prime * result + ((cityFlag == null) ? 0 : cityFlag.hashCode());
		result = prime * result + ((cityid == null) ? 0 : cityid.hashCode());
		result = prime * result + ((citykey == null) ? 0 : citykey.hashCode());
		result = prime * result + ((cityname == null) ? 0 : cityname.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((provid == null) ? 0 : provid.hashCode());
		result = prime * result + ((spellid == null) ? 0 : spellid.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}
}
