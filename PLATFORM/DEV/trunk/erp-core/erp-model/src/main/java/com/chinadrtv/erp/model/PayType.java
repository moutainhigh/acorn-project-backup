package com.chinadrtv.erp.model;

import javax.persistence.*;

/**  付款方式
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "PAY_TYPE", schema = "ACOAPP_OMS")
public class PayType implements java.io.Serializable{
    private Long id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAY_TYPE_SEQ")
    @SequenceGenerator(name = "PAY_TYPE_SEQ", sequenceName = "ACOAPP_OMS.PAY_TYPE_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
