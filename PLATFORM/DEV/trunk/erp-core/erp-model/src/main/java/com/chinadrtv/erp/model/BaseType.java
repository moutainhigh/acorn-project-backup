package com.chinadrtv.erp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-19
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TID", discriminatorType = DiscriminatorType.STRING)
@Table(name = "NAMES", schema = "IAGENT")
public class BaseType implements java.io.Serializable {

    private String id;
    private String tid;
    private String name;
    private Boolean valid;
    /*
    private String tdsc;
    */

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="assigned")
    @Column(name = "ID", length = 100)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /*
    @Column(name = "TID", length = 100, insertable=false, updatable=false)
    public String getTid() {
        return this.tid;
    }

    public void setTid(String value) {
        this.tid = value;
    }
    */
    @Column(name = "DSC", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "VALID", length = 100)
    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean value) {
        this.valid = value;
    }
    /*

    @Column(name = "TDSC", length = 100)
    public String getTdsc() {
        return this.tdsc;
    }

    public void setTdsc(String value) {
        this.tdsc = value;
    }
    */
}
