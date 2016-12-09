package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-26
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "OMS_REGULATION_SELECTION_LOG", schema = "ACOAPP_OMS")
public class OrderAssignLog implements java.io.Serializable {
    private Long id;

    private String orderId;

    private String regulationId;

    private String regulationDsc;

    private String codeId;

    private String codeDsc;

    private String crUser;

    private Date mdDate;

    private Integer assignType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OMS_REGULATION_SEL_LOG_SEQ")
    @SequenceGenerator(name = "OMS_REGULATION_SEL_LOG_SEQ", sequenceName = "ACOAPP_OMS.OMS_REGULATION_SEL_LOG_SEQ")
    @Column(name = "RUID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDERID", length = 16)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "REGULATION_ID", length = 10)
    public String getRegulationId() {
        return regulationId;
    }

    public void setRegulationId(String regulationId) {
        this.regulationId = regulationId;
    }

    @Column(name = "REGULATION_DSC", length = 256)
    public String getRegulationDsc() {
        return regulationDsc;
    }

    public void setRegulationDsc(String regulationDsc) {
        this.regulationDsc = regulationDsc;
    }

    @Column(name = "CODE_ID", length = 10)
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Column(name = "CODE_DSC", length = 256)
    public String getCodeDsc() {
        return codeDsc;
    }

    public void setCodeDsc(String codeDsc) {
        this.codeDsc = codeDsc;
    }

    @Column(name = "CRUSER", length = 10)
    public String getCrUser() {
        return crUser;
    }

    public void setCrUser(String crUser) {
        this.crUser = crUser;
    }

    @Column(name = "MDDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMdDate() {
        return mdDate;
    }

    public void setMdDate(Date mrDate) {
        this.mdDate = mrDate;
    }

    @Column(name = "REGULATION_TYPE")
    public Integer getAssignType() {
        return assignType;
    }

    public void setAssignType(Integer assignType) {
        this.assignType = assignType;
    }

}
