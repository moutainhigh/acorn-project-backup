package com.chinadrtv.erp.admin.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "FREIGHTLINE", schema = "IAGENT")
public class FreightLine implements java.io.Serializable {

    private Long id;                //标识
    private Double priority;         //优先级
    private Company company;         //快递公司
    private FreightLeg leg;           //费用行程
    private FreightQuota quota;      //费用限额
    private FreightVersion version;  //费用版本
    private Double basePrice;       //基本费用(投递费/首重费)
    private Double baseRate;        //基本费率(手续费)
    private Double surcharge;       //附加费用(提货费/续重费)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREIGHTLINE_SEQ")
    @SequenceGenerator(name = "FREIGHTLINE_SEQ", sequenceName = "IAGENT.FREIGHTLINE_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Column(name = "PRIORITY")
    public Double getPriority() {
        return this.priority;
    }

    public void setPriority(Double value) {
        this.priority = value;
    }

    @ManyToOne
    @JoinColumn(name = "COMPANYID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company value) {
        this.company = value;
    }

    @ManyToOne
    @JoinColumn(name = "VERSIONID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public FreightVersion getVersion() {
        return version;
    }

    public void setVersion(FreightVersion value) {
        this.version = value;
    }

    @ManyToOne
    @JoinColumn(name = "QUOTAID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public FreightQuota getQuota() {
        return this.quota;
    }

    public void setQuota(FreightQuota value) {
        this.quota = value;
    }

    @ManyToOne
    @JoinColumn(name = "LEGID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public FreightLeg getLeg() {
        return this.leg;
    }

    public void setLeg(FreightLeg value) {
        this.leg = value;
    }

    @Column(name = "BASEPRICE", precision = 2)
    public Double getBasePrice() {
        return this.basePrice;
    }

    public void setBasePrice(Double value) {
        this.basePrice = value;
    }

    //基本费率(手续费率)
    @Column(name = "BASERATE", precision = 2)
    public Double getBaseRate() {
        return this.baseRate;
    }

    public void setBaseRate(Double value) {
        this.baseRate = value;
    }

    //额外费用(提货费/续重费)
    @Column(name = "SURCHARGE", length = 100)
    public Double getSurcharge() {
        return this.surcharge;
    }

    public void setSurcharge(Double value) {
        this.surcharge = value;
    }
}
