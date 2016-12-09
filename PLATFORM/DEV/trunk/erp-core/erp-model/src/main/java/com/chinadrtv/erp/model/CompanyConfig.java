package com.chinadrtv.erp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "COMPANY_CONFIG", schema = "ACOAPP_OMS")
@Entity
public class CompanyConfig  implements java.io.Serializable {

    private String companyId;
    private String manualActing;//是否手动挑单，Y手动，N自动
    private String optionCompanyId;//备选承运商
    private Integer optionWareHouseId;//备选承运商仓库id
    private String optionWareHouseName;//备选承运商仓库id



    @Id
    @javax.persistence.Column(name = "COMPANYID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @javax.persistence.Column(name = "MANUAL_ACTING")
    @Basic
    public String getManualActing() {
        return manualActing;
    }

    public void setManualActing(String manualActing) {
        this.manualActing = manualActing;
    }

    @javax.persistence.Column(name = "OPTION_COMPANYID")
    @Basic
    public String getOptionCompanyId() {
        return optionCompanyId;
    }

    public void setOptionCompanyId(String optionCompanyId) {
        this.optionCompanyId = optionCompanyId;
    }

    @javax.persistence.Column(name = "OPTION_WAREHOUSEID")
    @Basic
    public Integer getOptionWareHouseId() {
        return optionWareHouseId;
    }

    public void setOptionWareHouseId(Integer optionWareHouseId) {
        this.optionWareHouseId = optionWareHouseId;
    }



    @javax.persistence.Column(name = "OPTION_WAREHOUSENAME")
    @Basic
    public String getOptionWareHouseName() {
        return optionWareHouseName;
    }

    public void setOptionWareHouseName(String optionWareHouseName) {
        this.optionWareHouseName = optionWareHouseName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result
                + ((manualActing == null) ? 0 : manualActing.hashCode());
        result = prime * result
                + ((optionCompanyId == null) ? 0 : optionCompanyId.hashCode());
        result = prime
                * result
                + ((optionWareHouseId == null) ? 0 : optionWareHouseId
                .hashCode());
        result = prime
                * result
                + ((optionWareHouseName == null) ? 0 : optionWareHouseName
                .hashCode());
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
        CompanyConfig other = (CompanyConfig) obj;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (manualActing == null) {
            if (other.manualActing != null)
                return false;
        } else if (!manualActing.equals(other.manualActing))
            return false;
        if (optionCompanyId == null) {
            if (other.optionCompanyId != null)
                return false;
        } else if (!optionCompanyId.equals(other.optionCompanyId))
            return false;
        if (optionWareHouseId == null) {
            if (other.optionWareHouseId != null)
                return false;
        } else if (!optionWareHouseId.equals(other.optionWareHouseId))
            return false;
        if (optionWareHouseName == null) {
            if (other.optionWareHouseName != null)
                return false;
        } else if (!optionWareHouseName.equals(other.optionWareHouseName))
            return false;
        return true;
    }

}
