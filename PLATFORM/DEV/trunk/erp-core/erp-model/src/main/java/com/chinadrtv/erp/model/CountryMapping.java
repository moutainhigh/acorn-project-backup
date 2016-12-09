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

import com.chinadrtv.erp.model.agent.CountyAll;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "COUNTY_MAPPING", schema = "ACOAPP_OMS")
@Entity
public class CountryMapping {

    private int countyid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_COUNTY_MAPPING_SEQ")
    @SequenceGenerator(name = "OMS_COUNTY_MAPPING_SEQ", sequenceName = "ACOAPP_OMS.OMS_COUNTY_MAPPING_SEQ")
    @javax.persistence.Column(name = "COUNTYID")
    public int getCountyid() {
        return countyid;
    }

    public void setCountyid(int countyid) {
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

//    private int mappingCountyId;
//    
//    @javax.persistence.Column(name = "MAPPING_COUNTYID")
//    @Basic
//    public int getMappingCountyId() {
//		return mappingCountyId;
//	}
//
//	public void setMappingCountyId(int mappingCountyId) {
//		this.mappingCountyId = mappingCountyId;
//	}

    private CountyAll countyAll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAPPING_COUNTYID",referencedColumnName="COUNTYID")
    public CountyAll getCountyAll() {
        return countyAll;
    }

    public void setCountyAll(CountyAll countyAll) {
        this.countyAll = countyAll;
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

        CountryMapping countyAll = (CountryMapping) o;

//        if (mappingCountyId != countyAll.mappingCountyId) return false;
        if (countyid != countyAll.countyid) return false;
        if (remark != null ? !remark.equals(countyAll.remark) : countyAll.remark != null) return false;
        if (countyname != null ? !countyname.equals(countyAll.countyname) : countyAll.countyname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = remark != null ? remark.hashCode() : 0;
        result = 31 * result + countyid;
//        result = 31 * result + mappingCountyId;
        result = 31 * result + (countyname != null ? countyname.hashCode() : 0);
        return result;
    }
}
