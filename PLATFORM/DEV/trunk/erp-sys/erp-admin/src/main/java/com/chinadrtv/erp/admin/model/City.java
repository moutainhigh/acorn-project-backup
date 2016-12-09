package com.chinadrtv.erp.admin.model;

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
@Table(name = "CITY_ALL", schema = "IAGENT")
public class City implements java.io.Serializable {

    private Long cityId;
    private Province province;
    private String cityName;
    private String cityKey;
    private String code;
    private String zipCode;
    private String areaCode;

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="assigned")
    @Column(name = "CITYID", length = 8)
    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long value) {
        this.cityId = value;
    }

    @ManyToOne
    @JoinColumn(name = "PROVID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province value) {
        this.province = value;
    }

    @Column(name = "CITYNAME", length = 16)
    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String value) {
        this.cityName = value;
    }

    @Column(name = "CITYKEY", length = 32)
    public String getCityKey() {
        return this.cityKey;
    }

    public void setCityKey(String value) {
        this.cityKey = value;
    }

    @Column(name = "CODE", length = 32)
    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @Column(name = "ZIPCODE", length = 6)
    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String value) {
        this.zipCode = value;
    }

    @Column(name = "AREACODE", length = 10)
    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String value) {
        this.areaCode = value;
    }
}
