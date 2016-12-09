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

import com.chinadrtv.erp.model.agent.AreaAll;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "AREA_MAPPING", schema = "ACOAPP_OMS")
@Entity
public class AreaMapping {

    private int areaid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_AREA_MAPPING_SEQ")
    @SequenceGenerator(name = "OMS_AREA_MAPPING_SEQ", sequenceName = "ACOAPP_OMS.OMS_AREA_MAPPING_SEQ")
    @javax.persistence.Column(name = "AREAID")
    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
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

//    private int mappingAreaId;
//
//    @javax.persistence.Column(name = "MAPPING_AREAID")
//    @Basic
//    public int getMappingAreaId() {
//		return mappingAreaId;
//	}
//
//	public void setMappingAreaId(int mappingAreaId) {
//		this.mappingAreaId = mappingAreaId;
//	}

    private AreaAll areaAll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAPPING_AREAID",referencedColumnName="AREAID")
    public AreaAll getAreaAll() {
        return areaAll;
    }

    public void setAreaAll(AreaAll areaAll) {
        this.areaAll = areaAll;
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

        AreaMapping areaAll = (AreaMapping) o;

        if (areaid != areaAll.areaid) return false;
//        if (mappingAreaId != areaAll.mappingAreaId) return false;
        if (areaname != null ? !areaname.equals(areaAll.areaname) : areaAll.areaname != null) return false;
        if (remark != null ? !remark.equals(areaAll.remark) : areaAll.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = remark != null ? remark.hashCode() : 0;
        result = 31 * result + areaid;
//        result = 31 * result + mappingAreaId;
        result = 31 * result + (areaname != null ? areaname.hashCode() : 0);
        return result;
    }
}
