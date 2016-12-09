package com.chinadrtv.erp.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**  地址组
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "OMS_AREA_GROUP_HEADER", schema = "ACOAPP_OMS")
public class AreaGroup implements java.io.Serializable{
    private Long id;

    private String name;

    private String crUser;

    private Date crDT;

    private Boolean isActive;

    private OrderChannel orderChannel;

    private Set<AreaGroupDetail> areaGroupDetails = new HashSet<AreaGroupDetail>(0);

    private Set<OrderAssignRule> orderAssignRules=new HashSet<OrderAssignRule>(0);

    private String userDef1;

    private String userDef2;

    private String note;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_AREA_GROUP_HEADER_SEQ")
    @SequenceGenerator(name = "OMS_AREA_GROUP_HEADER_SEQ", sequenceName = "ACOAPP_OMS.OMS_AREA_GROUP_HEADER_SEQ")
    @Column(name = "AREAGROUP_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "areaGroup")
    public Set<AreaGroupDetail> getAreaGroupDetails() {
        return areaGroupDetails;
    }

    public void setAreaGroupDetails(Set<AreaGroupDetail> areaGroupDetails) {
        this.areaGroupDetails = areaGroupDetails;
    }

    @Column(name = "AREAGROUP_NAME", length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Column(name = "ISACTIVE")
    @Type(type = "yes_no")
    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID")
    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(OrderChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    @OneToMany( fetch = FetchType.EAGER, mappedBy="areaGroup")
    public Set<OrderAssignRule> getOrderAssignRules() {
        return orderAssignRules;
    }

    public void setOrderAssignRules(Set<OrderAssignRule> orderAssignRules) {
        this.orderAssignRules = orderAssignRules;
    }

    @Column(name = "USER_DEF1", length = 20)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USER_DEF2", length = 20)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "NOTE", length = 256)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString()
    {
        if(this.id!=null)
            return this.id.toString();
        else
            return "0";
    }
}
