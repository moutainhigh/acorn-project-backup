package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * 渠道商品价格
 * User: gdj
 * Date: 13-5-13
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PRODUCTGRPLIMIT", schema = "IAGENT")
@IdClass(ProductGrpId.class)
public class ProductGrpLimit {

    private String prodId;
    private String grpId;
    private String status;
    private Date startDate;
    private Date endDate;
    private Double lpPrice;
    private String promotDocNo;
    private Date updat;

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

    @Column(name = "LPRICE")
    public Double getLpPrice() {
        return lpPrice;
    }

    public void setLpPrice(Double lpPrice) {
        this.lpPrice = lpPrice;
    }

    @Column(name = "PROMOTDOCNO")
    public String getPromotDocNo() {
        return promotDocNo;
    }

    public void setPromotDocNo(String promotDocNo) {
        this.promotDocNo = promotDocNo;
    }

    @Column(name = "UPDAT")
    public Date getUpdat() {
        return updat;
    }

    public void setUpdat(Date updat) {
        this.updat = updat;
    }
}
