package com.chinadrtv.erp.model;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ADDRESS_EXT_CHANGE", schema = "IAGENT")
@Entity
public class AddressExtChange implements java.io.Serializable{
    private static final long serialVersionUID = -1913516578741003646L;

    private Long addressExtChangeId;

    private Province province;

    private CityAll city;

    private CountyAll county;

    private AreaAll area;

    private String addressId;

    private Date uptime;

    private String addressDesc;

    private String addressType;

    private String contactId;

    private String areaName;

    private String procInstId;

    private Date createDate;

    private String createUser;

    private AddressChange addressChange;

    private OrderChange orderChange;

    @Id
    @Column(name = "ADDRESS_EXT_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_EXT_CHANGE_SEQ")
    @SequenceGenerator(name = "ADDRESS_EXT_CHANGE_SEQ", sequenceName = "IAGENT.ADDRESS_EXT_CHANGE_SEQ")
    public Long getAddressExtChangeId() {
        return addressExtChangeId;
    }

    public void setAddressExtChangeId(Long addressExtChangeId) {
        this.addressExtChangeId = addressExtChangeId;
    }

    @OneToOne( cascade=CascadeType.ALL)
    @JoinColumn(name="ADDRESS_CHANGE_ID")
    public AddressChange getAddressChange() {
        return addressChange;
    }

    public void setAddressChange(AddressChange addressChange) {
        this.addressChange = addressChange;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "PROVINCEID",referencedColumnName="PROVINCEID")
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "CITYID",referencedColumnName="CITYID")
    public CityAll getCity() {
        return city;
    }

    public void setCity(CityAll city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "COUNTYID",referencedColumnName="COUNTYID")
    public CountyAll getCounty() {
        return county;
    }

    public void setCounty(CountyAll county) {
        this.county = county;
    }


    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name = "AREAID",referencedColumnName="AREAID")
    public AreaAll getArea() {
        return area;
    }

    public void setArea(AreaAll area) {
        this.area = area;
    }

    @Column(name = "ADDRESSID", length = 18)
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @Column(name = "UPTIME", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    @Column(name = "ADDRDESC", length = 100)
    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    @Column(name = "ADDRESS_TYPE", length = 10)
    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "AREANAME", length = 60)
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @OneToOne(mappedBy = "address")
    public OrderChange getOrderChange() {
        return orderChange;
    }

    public void setOrderChange(OrderChange orderChange) {
        this.orderChange = orderChange;
    }

    @Column(name = "CREATE_DATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER", length = 20)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
