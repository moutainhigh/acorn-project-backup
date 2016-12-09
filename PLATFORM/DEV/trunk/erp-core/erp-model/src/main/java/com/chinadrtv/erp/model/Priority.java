package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PRIORITY", schema = "IAGENT")
public class Priority implements Serializable {
    private String priorityId;
    private String dsc;

    public Priority() {
    }

    @Id
    @Column(name = "PRIORITYID")
    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    @Column(name = "DSC")
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }
}