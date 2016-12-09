package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**  订单类型与付款方式
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "OMS_ORDER_PAY_TYPE", schema = "ACOAPP_OMS")
public class OrderPayType implements java.io.Serializable{
    private Long id;

    private OrderChannel orderChannel;

    //private String channelName;

    private Long orderTypeId;

    private Long payTypeId;

    private String crUser;

    private Date crDT;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_ORDER_PAY_TYPE_SEQ")
    @SequenceGenerator(name = "OMS_ORDER_PAY_TYPE_SEQ", sequenceName = "ACOAPP_OMS.OMS_ORDER_PAY_TYPE_SEQ")
    @Column(name = "RUID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHANNEL_ID")
    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(OrderChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    @Column(name = "ORDER_TYPE_ID")
    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    @Column(name = "PAY_TYPE_ID")
    public Long getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Long payTypeId) {
        this.payTypeId = payTypeId;
    }

    @Column(name = "CRUSER", length = 10)
    public String getCrUser() {
        return crUser;
    }

    public void setCrUser(String crUser) {
        this.crUser = crUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDT")
    public Date getCrDT() {
        return crDT;
    }

    public void setCrDT(Date crDT) {
        this.crDT = crDT;
    }
}
