package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-1-28
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "PRE_TRADE_COMPANY", schema = "ACOAPP_OMS")
public class PreTradeCompany implements java.io.Serializable{
    private long id ;
    private String tradeType ;
    private long companyId ;
    private String companyName ;
    private String companyAlias ;
    private long sourceId ;
    private String remark ;
    private int isTranslated;
    private String payType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_COMPANY_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_COMPANY_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_COMPANY_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "TRADE_TYPE", length = 50)
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Column(name = "COMPANY_ID")
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Column(name = "COMPANY_NAME", length = 50)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "COMPANY_ALIAS", length = 50)
    public String getCompanyAlias() {
        return companyAlias;
    }

    public void setCompanyAlias(String companyAlias) {
        this.companyAlias = companyAlias;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "ISTRANSLATED")
    public int getIsTranslated() {
        return isTranslated;
    }

    public void setIsTranslated(int translated) {
        isTranslated = translated;
    }

    @Column(name = "PAYTYPE", length = 10)
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name = "SOURCE_ID")
    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }
}
