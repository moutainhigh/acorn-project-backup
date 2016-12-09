package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-1-28
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PRODUCTGRPCHANNEL", schema = "IAGENT")
@IdClass(ProductGrpId.class)
public class ProductGrpChannel implements java.io.Serializable {

    private String prodId;
    private String grpId;
    private String status;
    private Date startDate;
    private Date endDate;

    @Id
    @Column(name = "PRODID")
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String value) {
        this.prodId = value;
    }

    @Id
    @Column(name = "GRPID")
    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String value) {
        this.grpId = value;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @Column(name = "STARTDT")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date value) {
        this.startDate = value;
    }

    @Column(name = "ENDDT")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date value) {
        this.endDate = value;
    }
}
