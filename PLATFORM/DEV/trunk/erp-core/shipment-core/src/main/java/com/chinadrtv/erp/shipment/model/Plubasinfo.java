package com.chinadrtv.erp.shipment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-30
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 * 组合商品表
 */
@Entity
@Table(name = "PLUBASINFO", schema="aco_scm_dev")
public class Plubasinfo implements java.io.Serializable{
    private Integer ruid;
    private String plucode;
    private String nccode;
    private String issuiteplu;
    private Double avgcsprc;
    private String pluname;



    @Id
    @Column(name = "RUID")
    public Integer getRuid() {
        return ruid;
    }

    public void setRuid(Integer ruid) {
        this.ruid = ruid;
    }

    @Column(name = "PLUCODE")
    public String getPlucode() {
        return plucode;
    }

    public void setPlucode(String plucode) {
        this.plucode = plucode;
    }

    @Column(name = "NCCODE")
    public String getNccode() {
        return nccode;
    }

    public void setNccode(String nccode) {
        this.nccode = nccode;
    }

    @Column(name = "ISSUITEPLU")
    public String getIssuiteplu() {
        return issuiteplu;
    }

    public void setIssuiteplu(String issuiteplu) {
        this.issuiteplu = issuiteplu;
    }

    @Column(name = "PLUNAME")
    public String getPluname() {
        return pluname;
    }

    public void setPluname(String pluname) {
        this.pluname = pluname;
    }

    @Column(name = "AVGCSPRC")
    public Double getAvgcsprc() {
        return avgcsprc;
    }

    public void setAvgcsprc(Double avgcsprc) {
        this.avgcsprc = avgcsprc;
    }
}
