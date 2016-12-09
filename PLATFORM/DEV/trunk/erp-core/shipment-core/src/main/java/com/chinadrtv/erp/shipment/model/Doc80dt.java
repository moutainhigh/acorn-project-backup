package com.chinadrtv.erp.shipment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-1
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "DOC80DT", schema="aco_scm_dev")
public class Doc80dt implements java.io.Serializable {
    private Integer pluid;
    private String docno;
    private Integer qty;
    private Integer rat;

    @Id
    @Column(name = "RUID")
    public Integer getPluid() {
        return pluid;
    }

    public void setPluid(Integer pluid) {
        this.pluid = pluid;
    }

    @Column(name = "DOCNO")
    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "RAT")
    public Integer getRat() {
        return rat;
    }

    public void setRat(Integer rat) {
        this.rat = rat;
    }
}
