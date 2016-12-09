package com.chinadrtv.erp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.chinadrtv.erp.model.agent.CityAll;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "CITY_MAPPING", schema = "ACOAPP_OMS")
@Entity
public class CityMapping {

    private int cityid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_CITY_MAPPING_SEQ")
    @SequenceGenerator(name = "OMS_CITY_MAPPING_SEQ", sequenceName = "ACOAPP_OMS.OMS_CITY_MAPPING_SEQ")
    @javax.persistence.Column(name = "CITYID")
    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
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

//    private int mappingCityId;

//    @javax.persistence.Column(name = "MAPPING_CITYID")
//    @Basic
//    public int getMappingCityId() {
//		return mappingCityId;
//	}
//
//	public void setMappingCityId(int mappingCityId) {
//		this.mappingCityId = mappingCityId;
//	}

    private CityAll cityAll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAPPING_CITYID",referencedColumnName="CITYID")
    public CityAll getCityAll() {
        return cityAll;
    }

    public void setCityAll(CityAll cityAll) {
        this.cityAll = cityAll;
    }

    private String remark;

    @javax.persistence.Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityMapping cityAll = (CityMapping) o;

        if (cityid != cityAll.cityid) return false;
//        if (mappingCityId != cityAll.mappingCityId) return false;
        if (cityname != null ? !cityname.equals(cityAll.cityname) : cityAll.cityname != null) return false;
        if (remark != null ? !remark.equals(cityAll.remark) : cityAll.remark != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = remark != null ? remark.hashCode() : 0;
        result = 31 * result + cityid;
//        result = 31 * result + mappingCityId;
        result = 31 * result + (cityname != null ? cityname.hashCode() : 0);
        return result;
    }
}
