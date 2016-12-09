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
@Table(name = "FREIGHTQUOTA", schema = "IAGENT")
public class FreightQuota implements java.io.Serializable {

    private Long id;
    private String name;
    private Company company;
    private Double minAmount;            //最小金额
    private Double maxAmount;            //最大金额
    private Boolean active;               //是否有效

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREIGHTQUOTA_SEQ")
    @SequenceGenerator(name = "FREIGHTQUOTA_SEQ", sequenceName = "IAGENT.FREIGHTQUOTA_SEQ")
    @Column(name = "ID", length = 50)
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
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


    @Column(name = "MINAMOUNT", length = 100)
    public Double getMinAmount() {
        return this.minAmount;
    }

    public void setMinAmount(Double value) {
        this.minAmount = value;
    }

    @Column(name = "MAXAMOUNT", length = 100)
    public Double getMaxAmount() {
        return this.maxAmount;
    }

    public void setMaxAmount(Double value) {
        this.maxAmount = value;
    }

    @Column(name = "ACTIVE", length = 8)
    public Boolean getActive(){
        return this.active;
    }

    public void setActive(Boolean value) {
        this.active = value;
    }
}
