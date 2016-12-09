package com.chinadrtv.erp.admin.model;

import javax.persistence.*;
import java.lang.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "FREIGHTVERSION", schema = "IAGENT")
public class FreightVersion implements java.io.Serializable {

    private Long id;
    private String name;
    private Company company;
    private Date startDate;
    private Date endDate;
    private Boolean active;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREIGHTVERSION_SEQ")
    @SequenceGenerator(name = "FREIGHTVERSION_SEQ", sequenceName = "IAGENT.FREIGHTVERSION_SEQ")
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

    @Column(name = "STARTDATE", length = 7)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date value) {
        this.startDate = value;
    }

    @Column(name = "ENDDATE", length = 7)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date value) {
        this.endDate = value;
    }

    @Column(name = "ACTIVE", length = 8)
    public Boolean getActive(){
        return this.active;
    }

    public void setActive(Boolean value) {
        this.active = value;
    }
}
