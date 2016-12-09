package com.chinadrtv.erp.model.marketing;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午1:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "BASE_ACDINFO", schema = "ACOAPP_MARKETING")
public class BaseAcdInfo implements java.io.Serializable  {

    private Long id;
    private String area;
    private String acd;
    private String acd2;
    private String acdName;
    private String deptNo;
    private String ivrVdn;
    private String mediaProdId;
    private String mediaProdName;
    private String description;
    private Boolean active;
    private Date modifiedOn;
    private String modifiedBy;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_ACDINFO", sequenceName = "ACOAPP_MARKETING.SEQ_BASE_ACDINFO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASE_ACDINFO")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AREA")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "ACD")
    public String getAcd() {
        return acd;
    }

    public void setAcd(String acd) {
        this.acd = acd;
    }

    @Column(name = "ACD2")
    public String getAcd2() {
        return acd2;
    }

    public void setAcd2(String acd2) {
        this.acd2 = acd2;
    }

    @Column(name = "ACD_NAME")
    public String getAcdName() {
        return acdName;
    }

    public void setAcdName(String acdName) {
        this.acdName = acdName;
    }

    @Column(name = "DEPT_NO")
    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    @Column(name = "IVR_VDN")
    public String getIvrVdn() {
        return ivrVdn;
    }

    public void setIvrVdn(String ivrVdn) {
        this.ivrVdn = ivrVdn;
    }

    @Column(name = "MEDIA_PRODID")
    public String getMediaProdId() {
        return mediaProdId;
    }

    public void setMediaProdId(String mediaProdId) {
        this.mediaProdId = mediaProdId;
    }

    @Column(name = "MEDIA_PRODNAME")
    public String getMediaProdName() {
        return mediaProdName;
    }

    public void setMediaProdName(String mediaProdName) {
        this.mediaProdName = mediaProdName;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "MODIFIED_ON")
    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Column(name = "MODIFIED_BY")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
