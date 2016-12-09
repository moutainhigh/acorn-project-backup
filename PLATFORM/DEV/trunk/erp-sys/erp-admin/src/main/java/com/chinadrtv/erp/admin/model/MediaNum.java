package com.chinadrtv.erp.admin.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-11-13
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "MS_N400", schema = "IAGENT")
public class MediaNum implements java.io.Serializable {
    private Long ruid;
    private String n400;

    @Id
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "N400", length = 20)
    public String getN400() {
        return n400;
    }

    public void setN400(String n400) {
        this.n400 = n400;
    }
}

