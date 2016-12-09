package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**   地址组明细表
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "OMS_AREAGROUP_DETAIL", schema = "ACOAPP_OMS")
public class AreaGroupDetail implements java.io.Serializable{
    private Long id;

    private AreaGroup areaGroup;

    private Integer provinceId;

    private Integer cityId;

    private Integer countyId;

    private Integer areaId;

    private String crUser;

    private Date crDT;

    private String userDef1;

    private String userDef2;

    private String userDef3;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_AREA_GROUP_DETAIL_SEQ")
    @SequenceGenerator(name = "OMS_AREA_GROUP_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.OMS_AREA_GROUP_DETAIL_SEQ")
    @Column(name = "RUID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_GROUP_ID")
    public AreaGroup getAreaGroup() {
        return areaGroup;
    }

    public void setAreaGroup(AreaGroup areaGroup) {
        this.areaGroup = areaGroup;
    }

    @Column(name = "PROVIENCE_ID")
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "CITY_ID")
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "COUNTY_ID")
    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    @Column(name = "AREA_ID")
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Column(name = "CRUSER", length = 10)
    public String getCrUser() {
        return crUser;
    }

    public void setCrUser(String crUser) {
        this.crUser = crUser;
    }

    @Column(name = "CRDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrDT() {
        return crDT;
    }

    public void setCrDT(Date crDT) {
        this.crDT = crDT;
    }

    @Column(name = "USER_DEF1", length = 10)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USER_DEF2")
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USER_DEF3")
    public String getUserDef3() {
        return userDef3;
    }


    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }


    public int hashCode()
    {
        if(areaId!=null)
            return areaId.intValue();
        return -1;
    }

    public boolean equals(Object obj) {
        if(obj instanceof AreaGroupDetail == false) return false;
        if(this==obj) return true;

        AreaGroupDetail other=(AreaGroupDetail)obj;

        if(this.cityId==null)
        {
            if(other.cityId!=null)
                return false;
        }
        else if(!this.cityId.equals(other.cityId))
        {
            return false;
        }

        if(this.countyId==null)
        {
            if(other.countyId!=null)
                return false;
        }
        else if(!this.countyId.equals(other.countyId))
        {
            return false;
        }

        if(this.areaId==null)
        {
            if(other.areaId!=null)
                return false;
        }
        else if(!this.areaId.equals(other.areaId))
        {
            return false;
        }

        if(this.provinceId==null)
        {
            if(other.provinceId!=null)
                return false;
        }
        else if(!this.provinceId.equals(other.provinceId))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return getStringFromLong(this.provinceId)+"-"+getStringFromLong(this.cityId)+"-"+getStringFromLong(this.countyId)+"-"+getStringFromLong(this.areaId);
    }

    private String getStringFromLong(Integer count)
    {
        return count!=null?count.toString():"";
    }

}
