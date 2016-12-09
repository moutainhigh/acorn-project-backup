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
@Table(name = "PROVINCE", schema = "IAGENT")
public class Province implements java.io.Serializable {

    private String provinceId;
    private String chinese;
    private String english;

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="assigned")
    @Column(name = "PROVINCEID", length = 8)
    public String getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(String value) {
        this.provinceId = value;
    }

    @Column(name = "CHINESE", length = 16)
    public String getChinese() {
        return this.chinese;
    }

    public void setChinese(String value) {
        this.chinese = value;
    }

    @Column(name = "ENGLISH", length = 32)
    public String getEnglish() {
        return this.english;
    }

    public void setEnglish(String value) {
        this.english = value;
    }
}
