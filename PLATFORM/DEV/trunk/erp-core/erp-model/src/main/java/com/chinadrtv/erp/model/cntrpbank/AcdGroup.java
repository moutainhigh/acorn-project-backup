package com.chinadrtv.erp.model.cntrpbank;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-13
 * Time: 下午1:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ACDINFO", schema = "ACOAPP_CNTRPBANK")
public class AcdGroup implements java.io.Serializable {

    private String acdId;
    private String acdName;
    private Boolean ivr;
    private String deptNo;
    private Integer sno;

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="assigned")
    @Column(name = "ACDID", length = 20)
    public String getAcdId() {
        return this.acdId;
    }

    public void setAcdId(String value) {
        this.acdId = value;
    }

    @Column(name = "ACDNAME", length = 100)
    public String getAcdName() {
        return this.acdName;
    }

    public void setAcdName(String value) {
        this.acdName = value;
    }

    @Column(name = "IVR", length = 1)
    public Boolean getIvr() {
        return this.ivr;
    }

    public void setIvr(Boolean value) {
        this.ivr = value;
    }

    @Column(name = "DEPTNO", length = 100)
    public String getDeptNo() {
        return this.deptNo;
    }

    public void setDeptNo(String value) {
        this.deptNo = value;
    }

    @Column(name = "SNO", length = 100)
    public Integer getSno() {
        return this.sno;
    }

    public void setSno(Integer value) {
        this.sno = value;
    }
}
