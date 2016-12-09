package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-6
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 * 产品套件类型表
 */
@Table(name = "PRODUCTSUITETYPE", schema = "IAGENT")
@Entity
public class ProductSuiteType implements Serializable {
    private String prodId;           //单品编码
    private Integer prodNum;           //单品数量
    private Integer rat;              //金额占比
    //private String prodSuiteScmId;   //套装商品12SCM编码
    //private String prodScmId;       //  单品12SCM编码
    private String prodSuiteId;     //套装产品编码
    private String prodSuiteName;   //套装产品名称
    private String prodName;        //单品名称
    private Double prodCost;    //单品成本

    private ProductSuiteTypeId productSuiteTypeId;

    @EmbeddedId
    public ProductSuiteTypeId getProductSuiteTypeId() {
        return productSuiteTypeId;
    }

    public void setProductSuiteTypeId(ProductSuiteTypeId productSuiteTypeId) {
        this.productSuiteTypeId = productSuiteTypeId;
    }

    @Column(name = "PRODID")
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    @Column(name = "PRODNUM")
    public Integer getProdNum() {
        return prodNum;
    }

    public void setProdNum(Integer prodNum) {
        this.prodNum = prodNum;
    }

    @Column(name = "RAT")
    public Integer getRat() {
        return rat;
    }

    public void setRat(Integer rat) {
        this.rat = rat;
    }

    //@Column(name = "PRODSUITESCMID")
    public String getProdSuiteScmId() {
        if(this.productSuiteTypeId!=null)
        {
            return this.productSuiteTypeId.getProdSuiteScmId();
        }
        return null;
    }

    public void setProdSuiteScmId(String prodSuiteScmId) {
        if(this.productSuiteTypeId!=null)
        {
             this.productSuiteTypeId.setProdSuiteScmId(prodSuiteScmId);
        }
        //this.prodSuiteScmId = prodSuiteScmId;
    }

    //@Column(name = "PRODSCMID")
    public String getProdScmId() {
        if(this.productSuiteTypeId!=null)
        {
            return this.productSuiteTypeId.getProdScmId();
        }
        return null;
    }

    public void setProdScmId(String prodScmId) {
        if(this.productSuiteTypeId!=null)
        {
            this.productSuiteTypeId.setProdScmId(prodScmId);
        }
        //this.prodScmId = prodScmId;
    }

    @Column(name = "PRODSUITEID")
    public String getProdSuiteId() {
        return prodSuiteId;
    }

    public void setProdSuiteId(String prodSuiteId) {
        this.prodSuiteId = prodSuiteId;
    }

    @Column(name = "PRODSUITENAME")
    public String getProdSuiteName() {
        return prodSuiteName;
    }

    public void setProdSuiteName(String prodSuiteName) {
        this.prodSuiteName = prodSuiteName;
    }

    @Column(name = "PRODNAME")
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    @Column(name = "PRODCOST")
    public Double getProdCost() {
        return prodCost;
    }

    public void setProdCost(Double prodCost) {
        this.prodCost = prodCost;
    }
}
