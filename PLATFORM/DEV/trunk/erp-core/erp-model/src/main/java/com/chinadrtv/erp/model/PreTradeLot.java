package com.chinadrtv.erp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**  订单导入批号
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "PRE_TRADE_LOT", schema = "ACOAPP_OMS")
public class PreTradeLot implements java.io.Serializable{
    private Long id;

    private Set<PreTrade> preTrades = new HashSet<PreTrade>(0);


    private String lotDsc;
    private String crusr;
    private Date crdt;

    @NotNull
    private Long sourceId;
    private String dlusr;
    private Date dldt;
    private String uploader;
    private Integer validCount;
    private Integer errCount;
    private Integer totalCount;
    private Integer status;
    private String errMsg;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_LOT_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_LOT_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_LOT_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "preTradeLot")
    public Set<PreTrade> getPreTrades() {
        return preTrades;
    }

    public void setPreTrades(Set<PreTrade> preTrades) {
        this.preTrades = preTrades;
    }

    @Column(name = "LOT_DSC", length = 100)
    public String getLotDsc() {
        return lotDsc;
    }

    public void setLotDsc(String lotDsc) {
        this.lotDsc = lotDsc;
    }

    @Column(name = "CRUSR", length = 100)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDT", length = 7)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "DLUSR", length = 100)
    public String getDlusr() {
        return dlusr;
    }

    public void setDlusr(String dlusr) {
        this.dlusr = dlusr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DLDT", length = 7)
    public Date getDldt() {
        return dldt;
    }

    public void setDldt(Date dldt) {
        this.dldt = dldt;
    }

    @Column(name = "UPLOADER", length = 100)
    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    @Column(name = "VALID_COUNT")
    public Integer getValidCount() {
        return validCount;
    }

    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
    }

    @Column(name = "ERR_COUNT")
    public Integer getErrCount() {
        return errCount;
    }

    public void setErrCount(Integer errCount) {
        this.errCount = errCount;
    }

    @Column(name = "TOTAL_COUNT")
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SOURCE_ID")
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "ERR_MSG",length = 1000)
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
