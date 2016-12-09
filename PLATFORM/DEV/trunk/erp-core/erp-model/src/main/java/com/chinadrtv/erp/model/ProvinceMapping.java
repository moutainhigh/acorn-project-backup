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

import com.chinadrtv.erp.model.agent.Province;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "PROVINCE_MAPPING", schema = "ACOAPP_OMS")
@Entity
public class ProvinceMapping {
    private int provinceid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_PROVINCE_MAPPING_SEQ")
    @SequenceGenerator(name = "OMS_PROVINCE_MAPPING_SEQ", sequenceName = "ACOAPP_OMS.OMS_PROVINCE_MAPPING_SEQ")
    @javax.persistence.Column(name = "PROVINCEID")
    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    private String provinceName;

    @javax.persistence.Column(name = "PROVINCENAME")
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

//	private int mappingProvinceId;
//	
//	@javax.persistence.Column(name = "MAPPING_PROVINCEID")
//	@Basic
//    public int getMappingProvinceId() {
//		return mappingProvinceId;
//	}
//
//	public void setMappingProvinceId(int mappingProvinceId) {
//		this.mappingProvinceId = mappingProvinceId;
//	}

    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAPPING_PROVINCEID",referencedColumnName="PROVINCEID")
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
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

        ProvinceMapping province = (ProvinceMapping) o;

//        if (mappingProvinceId != province.mappingProvinceId) return false;
        if (provinceid != province.provinceid) return false;
        if (provinceName != null ? !provinceName.equals(province.provinceName) : province.provinceName != null) return false;
        if (remark != null ? !remark.equals(province.remark) : province.remark != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = provinceName != null ? provinceName.hashCode() : 0;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
