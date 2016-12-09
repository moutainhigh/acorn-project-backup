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
@Table(name = "FREIGHTLEG", schema = "IAGENT")
public class FreightLeg implements java.io.Serializable {

    private Long id;
    private String name;
    private Company company;
    private Province departProvince;
    private City departCity;
    private String arrivalType;
    private Province arrivalProvince;
    private City arrivalCity;
    private Double firstWeight;      //首重
    private Double additionalWeight; //续重
    private Boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREIGHTLEG_SEQ")
    @SequenceGenerator(name = "FREIGHTLEG_SEQ", sequenceName = "IAGENT.FREIGHTLEG_SEQ")
    @Column(name = "ID")
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

    @ManyToOne
    @JoinColumn(name = "DEPARTPROVID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public Province getDepartProvince() {
        return this.departProvince;
    }

    public void setDepartProvince(Province value) {
        this.departProvince = value;
    }

    @ManyToOne
    @JoinColumn(name = "DEPARTCITYID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public City getDepartCity() {
        return this.departCity;
    }

    public void setDepartCity(City value) {
        this.departCity = value;
    }

    @ManyToOne
    @JoinColumn(name = "ARRIVALPROVID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public Province getArrivalProvince() {
        return this.arrivalProvince;
    }

    public void setArrivalProvince(Province value) {
        this.arrivalProvince = value;
    }

    @ManyToOne
    @JoinColumn(name = "ARRIVALCITYID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public City getArrivalCity() {
        return this.arrivalCity;
    }

    public void setArrivalCity(City value) {
        this.arrivalCity = value;
    }

    @Column(name = "ARRIVALTYPE", length = 2)
    public String getArrivalType() {
        return this.arrivalType;
    }

    public void setArrivalType(String value) {
        this.arrivalType = value;
    }

    @Column(name = "FIRSTWEIGHT", length = 100)
    public Double getFirstWeight() {
        return this.firstWeight;
    }

    public void setFirstWeight(Double value) {
        this.firstWeight = value;
    }

    @Column(name = "ADDITIONALWEIGHT", length = 100)
    public Double getAdditionalWeight() {
        return this.additionalWeight;
    }

    public void setAdditionalWeight(Double value) {
        this.additionalWeight = value;
    }

    @Column(name = "ACTIVE", length = 8)
    public Boolean getActive(){
        return this.active;
    }

    public void setActive(Boolean value) {
        this.active = value;
    }
}
