package com.chinadrtv.erp.model;

import javax.persistence.*;

/** 订单类型
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "ORDER_TYPE", schema = "ACOAPP_OMS")
public class OrderType implements java.io.Serializable{
    private Long id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_TYPE_SEQ")
    @SequenceGenerator(name = "ORDER_TYPE_SEQ", sequenceName = "ACOAPP_OMS.ORDER_TYPE_SEQ")
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
