package com.chinadrtv.erp.model.marketing;

import javax.persistence.*;

/**
 * 基本电话表
 * User: guadi.gao
 * Date: 14-4-8
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "BASE_CALLINFO", schema = "ACOAPP_MARKETING")
public class BaseCallInfo implements java.io.Serializable {
    private Long id;
    private String inclusion;
    private String exclusion;
    private Boolean active;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_CALLINFO", sequenceName = "ACOAPP_MARKETING.SEQ_BASE_CALLINFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASE_CALLINFO")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "INCLUSION")
    public String getInclusion() {
        return inclusion;
    }

    public void setInclusion(String inclusion) {
        this.inclusion = inclusion;
    }

    @Column(name = "EXCLUSION")
    public String getExclusion() {
        return exclusion;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
