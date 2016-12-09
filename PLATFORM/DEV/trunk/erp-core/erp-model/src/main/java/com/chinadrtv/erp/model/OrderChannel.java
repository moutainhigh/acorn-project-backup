package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "OMS_CHANNEL", schema = "ACOAPP_OMS")
public class OrderChannel implements java.io.Serializable{
    private Long id;

    private String channelName;

    private String crUser;

    private Date crDT;

    //private Set<OrderAssignRule> orderAssignRules=new HashSet<OrderAssignRule>(0);

    private Set<OrderPayType> orderPayTypes=new HashSet<OrderPayType>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_CHANNEL_SEQ")
    @SequenceGenerator(name = "OMS_CHANNEL_SEQ", sequenceName = "ACOAPP_OMS.OMS_CHANNEL_SEQ")
    @Column(name = "CHANNEL_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name ="CHANNEL_NAME", length = 20)
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    @OneToMany( fetch = FetchType.EAGER, mappedBy="orderChannel")
    public Set<OrderPayType> getOrderPayTypes() {
        return orderPayTypes;
    }

    public void setOrderPayTypes(Set<OrderPayType> orderPayTypes) {
        this.orderPayTypes = orderPayTypes;
    }

    /*@OneToMany( fetch = FetchType.LAZY, mappedBy="orderChannel")
    public Set<OrderAssignRule> getOrderAssignRules() {
        return orderAssignRules;
    }

    public void setOrderAssignRules(Set<OrderAssignRule> orderAssignRules) {
        this.orderAssignRules = orderAssignRules;
    } */
}