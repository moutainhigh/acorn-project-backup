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
@Table(name = "DOC80BF", schema="aco_scm_dev")
public class Doc80bf implements java.io.Serializable {
    private String docno;
    private String pluno;

    @Id
    @Column(name = "DOCNO")
    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    @Column(name = "PLUNO")
    public String getPluno() {
        return pluno;
    }

    public void setPluno(String pluno) {
        this.pluno = pluno;
    }
}
