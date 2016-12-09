package com.chinadrtv.erp.model.inventory;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品基础信息
 * User: Administrator
 * Date: 13-1-28
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="PLUBASINFO", schema = "IAGENT")
public class PlubasInfo implements java.io.Serializable {
    private Long ruid;              //NUMBER not null,
    private String plucode;           //VARCHAR2(20) not null,
    private String pluname;           //VARCHAR2(100) not null,
    private String catcode;           //VARCHAR2(20) not null,
    private String brandcode;         //VARCHAR2(20),
    private String spec;              //VARCHAR2(40),
    private String pkunit;            //VARCHAR2(20) not null,
    private Double pkqty;             //NUMBER default 1 not null,
    private Double plunature;         //NUMBER default 0 not null,
    private Long depgrpid;          //NUMBER not null,
    private String memo;              //VARCHAR2(255),
    private Double slprc;             //NUMBER(18,2) default 0 not null,
    private Double outtx;             //NUMBER not null,
    private Double hslprc;            //NUMBER(18,2) not null,
    private Double lslprc;            //NUMBER(18,2) not null,
    private Boolean valid;            //NUMBER not null,
    private String validpsn;          //VARCHAR2(20),
    private Date validdat;          //DATE,
    private String comcode;           //VARCHAR2(20),
    private String comcontcode;       //VARCHAR2(20),
    private Double whslprc;           //NUMBER(18,2) not null,
    private String status;            //VARCHAR2(10) default 1 not null,
    private Double avgcsprc;          //NUMBER(18,6) not null,
    private Date stamdat;           //DATE,
    private String obcd;              //VARCHAR2(40),
    private Double addtx;             //NUMBER,
    private Double present;           //NUMBER,
    private String issuiteplu;        //CHAR(1) default 0,
    private String spellcode;         //VARCHAR2(20),
    private Double csrat;             //NUMBER(18,6) not null,
    private Double viprat;            //NUMBER,
    private Double qdqty;             //NUMBER,
    private String smprep;            //VARCHAR2(4000),
    private Double viptyp;            //NUMBER,
    private Double fare;              //NUMBER not null,
    private String nccode;            //VARCHAR2(20) not null,
    private Date credat;            //DATE,
    private String crepsn;            //VARCHAR2(20),
    private String ncfree;            //VARCHAR2(4),
    private Date modidat;           //DATE default sysdate not null,
    private Double prliid;            //NUMBER,
    private Double ticket;            //NUMBER,
    private String spellname;         //VARCHAR2(40),
    private Double dptid;             //NUMBER,
    private Boolean issend;            //NUMBER(1) default 0 not null,
    private String ncfreename;        //VARCHAR2(100),
    private Boolean issndnc;       //NUMBER(1) default 0 not null,
    private Boolean istv;              //NUMBER(1) default 0 not null,
    private String modipsn;           //VARCHAR2(20),
    private Boolean stdbox;            //NUMBER(1) default 0 not null,
    private Double curcsprc;          //NUMBER(18,6) default 0 not null,
    private Double farext;            //NUMBER default 0 not null,
    private Double ncfreestatus;      //NUMBER default 1 not null,
    private Boolean hostedplu;         //NUMBER(1) default 0 not null,
    private String prodser;           //VARCHAR2(50),
    private Long lastLockSeqId;   //NUMBER,
    private Long lastUpdateSeqId; //NUMBER,
    private Date lastUpdateTime;  //DATE,
    private Double mailPrice;         //NUMBER(10,2),
    private Double sendRate;           //NUMBER(10,2)

    //private List<PlubasInfo> suites = new ArrayList<PlubasInfo>();  //产品套装

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLUBASINFO_SEQ")
    @SequenceGenerator(name = "PLUBASINFO_SEQ", sequenceName = "IAGENT.PLUBASINFO_SEQ")
    @Column(name = "RUID")
    public Long getRuid() {
        return this.ruid;
    }

    public void setRuid(Long value) {
        this.ruid = value;
    }

    @Column(name = "PLUCODE", unique = true)
    public String getPlucode() {
        return plucode;
    }

    public void setPlucode(String pluode) {
        this.plucode = pluode;
    }

    @Column(name = "PLUNAME")
    public String getPluname() {
        return pluname;
    }

    public void setPluname(String pluname) {
        this.pluname = pluname;
    }

    @Column(name = "CATCODE")
    public String getCatcode() {
        return catcode;
    }

    public void setCatcode(String catcode) {
        this.catcode = catcode;
    }

    @Column(name = "BRANDCODE")
    public String getBrandcode() {
        return brandcode;
    }

    public void setBrandcode(String brandcode) {
        this.brandcode = brandcode;
    }

    @Column(name = "SPEC")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Column(name = "PKUNIT")
    public String getPkunit() {
        return pkunit;
    }

    public void setPkunit(String pkunit) {
        this.pkunit = pkunit;
    }

    @Column(name = "PKQTY")
    public Double getPkqty() {
        return pkqty;
    }

    public void setPkqty(Double pkqty) {
        this.pkqty = pkqty;
    }

    @Column(name = "PLUNATURE")
    public Double getPlunature() {
        return plunature;
    }

    public void setPlunature(Double plunature) {
        this.plunature = plunature;
    }

    @Column(name = "DEPGRPID")
    public Long getDepgrpid() {
        return depgrpid;
    }

    public void setDepgrpid(Long depgrpid) {
        this.depgrpid = depgrpid;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "SLPRC")
    public Double getSlprc() {
        return slprc;
    }

    public void setSlprc(Double slprc) {
        this.slprc = slprc;
    }

    @Column(name = "OUTTX")
    public Double getOuttx() {
        return outtx;
    }

    public void setOuttx(Double outtx) {
        this.outtx = outtx;
    }

    @Column(name = "HSLPRC")
    public Double getHslprc() {
        return hslprc;
    }

    public void setHslprc(Double hslprc) {
        this.hslprc = hslprc;
    }

    @Column(name = "LSLPRC")
    public Double getLslprc() {
        return lslprc;
    }

    public void setLslprc(Double lslprc) {
        this.lslprc = lslprc;
    }

    @Column(name = "VALID")
    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "VALIDPSN")
    public String getValidpsn() {
        return validpsn;
    }

    public void setValidpsn(String validpsn) {
        this.validpsn = validpsn;
    }

    @Column(name = "VALIDDAT")
    public Date getValiddat() {
        return validdat;
    }

    public void setValiddat(Date validdat) {
        this.validdat = validdat;
    }

    @Column(name = "COMCODE")
    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    @Column(name = "COMCONTCODE")
    public String getComcontcode() {
        return comcontcode;
    }

    public void setComcontcode(String comcontcode) {
        this.comcontcode = comcontcode;
    }

    @Column(name = "WHSLPRC")
    public Double getWhslprc() {
        return whslprc;
    }

    public void setWhslprc(Double whslprc) {
        this.whslprc = whslprc;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "AVGCSPRC")
    public Double getAvgcsprc() {
        return avgcsprc;
    }

    public void setAvgcsprc(Double avgcsprc) {
        this.avgcsprc = avgcsprc;
    }

    @Column(name = "STAMDAT")
    public Date getStamdat() {
        return stamdat;
    }

    public void setStamdat(Date stamdat) {
        this.stamdat = stamdat;
    }

    @Column(name = "OBCD")
    public String getObcd() {
        return obcd;
    }

    public void setObcd(String obcd) {
        this.obcd = obcd;
    }

    @Column(name = "ADDTX")
    public Double getAddtx() {
        return addtx;
    }

    public void setAddtx(Double addtx) {
        this.addtx = addtx;
    }

    @Column(name = "PRESENT")
    public Double getPresent() {
        return present;
    }

    public void setPresent(Double present) {
        this.present = present;
    }

    @Column(name = "ISSUITEPLU")
    public String getIssuiteplu() {
        return issuiteplu;
    }

    public void setIssuiteplu(String issuiteplu) {
        this.issuiteplu = issuiteplu;
    }

    @Column(name = "SPELLCODE")
    public String getSpellcode() {
        return spellcode;
    }

    public void setSpellcode(String spellcode) {
        this.spellcode = spellcode;
    }

    @Column(name = "CSRAT")
    public Double getCsrat() {
        return csrat;
    }

    public void setCsrat(Double csrat) {
        this.csrat = csrat;
    }

    @Column(name = "VIPRAT")
    public Double getViprat() {
        return viprat;
    }

    public void setViprat(Double viprat) {
        this.viprat = viprat;
    }

    @Column(name = "QDQTY")
    public Double getQdqty() {
        return qdqty;
    }

    public void setQdqty(Double qdqty) {
        this.qdqty = qdqty;
    }

    @Column(name = "SMPREP")
    public String getSmprep() {
        return smprep;
    }

    public void setSmprep(String smprep) {
        this.smprep = smprep;
    }

    @Column(name = "VIPTYP")
    public Double getViptyp() {
        return viptyp;
    }

    public void setViptyp(Double viptyp) {
        this.viptyp = viptyp;
    }

    @Column(name = "FARE")
    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    @Column(name = "NCCODE")
    public String getNccode() {
        return nccode;
    }

    public void setNccode(String nccode) {
        this.nccode = nccode;
    }

    @Column(name = "CREDAT")
    public Date getCredat() {
        return credat;
    }

    public void setCredat(Date credat) {
        this.credat = credat;
    }

    @Column(name = "CREPSN")
    public String getCrepsn() {
        return crepsn;
    }

    public void setCrepsn(String crepsn) {
        this.crepsn = crepsn;
    }

    @Column(name = "NCFREE")
    public String getNcfree() {
        return ncfree;
    }

    public void setNcfree(String ncfree) {
        this.ncfree = ncfree;
    }

    @Column(name = "MODIDAT")
    public Date getModidat() {
        return modidat;
    }

    public void setModidat(Date modidat) {
        this.modidat = modidat;
    }

    @Column(name = "PRLIID")
    public Double getPrliid() {
        return prliid;
    }

    public void setPrliid(Double prliid) {
        this.prliid = prliid;
    }

    @Column(name = "TICKET")
    public Double getTicket() {
        return ticket;
    }

    public void setTicket(Double ticket) {
        this.ticket = ticket;
    }

    @Column(name = "SPELLNAME")
    public String getSpellname() {
        return spellname;
    }

    public void setSpellname(String spellname) {
        this.spellname = spellname;
    }

    @Column(name = "DPTID")
    public Double getDptid() {
        return dptid;
    }

    public void setDptid(Double dptid) {
        this.dptid = dptid;
    }

    @Column(name = "ISSEND")
    public Boolean getIssend() {
        return issend;
    }

    public void setIssend(Boolean issend) {
        this.issend = issend;
    }

    @Column(name = "NCFREENAME")
    public String getNcfreename() {
        return ncfreename;
    }

    public void setNcfreename(String ncfreename) {
        this.ncfreename = ncfreename;
    }

    @Column(name = "ISSNDNC")
    public Boolean getIssndnc() {
        return issndnc;
    }

    public void setIssndnc(Boolean issndnc) {
        this.issndnc = issndnc;
    }

    @Column(name = "ISTV")
    public Boolean getIstv() {
        return istv;
    }

    public void setIstv(Boolean istv) {
        this.istv = istv;
    }

    @Column(name = "MODIPSN")
    public String getModipsn() {
        return modipsn;
    }

    public void setModipsn(String modipsn) {
        this.modipsn = modipsn;
    }

    @Column(name = "STDBOX")
    public Boolean getStdbox() {
        return stdbox;
    }

    public void setStdbox(Boolean stdbox) {
        this.stdbox = stdbox;
    }

    @Column(name = "CURCSPRC")
    public Double getCurcsprc() {
        return curcsprc;
    }

    public void setCurcsprc(Double curcsprc) {
        this.curcsprc = curcsprc;
    }

    @Column(name = "FAREXT")
    public Double getFarext() {
        return farext;
    }

    public void setFarext(Double farext) {
        this.farext = farext;
    }

    @Column(name = "NCFREESTATUS")
    public Double getNcfreestatus() {
        return ncfreestatus;
    }

    public void setNcfreestatus(Double ncfreestatus) {
        this.ncfreestatus = ncfreestatus;
    }

    /**
     * 代管商品[0否1是]
     * @return
     */
    @Column(name = "HOSTEDPLU")
    public Boolean getHostedplu() {
        return hostedplu;
    }

    public void setHostedplu(Boolean hostedplu) {
        this.hostedplu = hostedplu;
    }

    @Column(name = "PRODSER")
    public String getProdser() {
        return prodser;
    }

    public void setProdser(String prodser) {
        this.prodser = prodser;
    }

    @Column(name = "LAST_LOCK_SEQID")
    public Long getLastLockSeqId() {
        return lastLockSeqId;
    }

    public void setLastLockSeqId(Long lastLockSeqId) {
        this.lastLockSeqId = lastLockSeqId;
    }

    @Column(name = "LAST_UPDATE_SEQID")
    public Long getLastUpdateSeqId() {
        return lastUpdateSeqId;
    }

    public void setLastUpdateSeqId(Long lastUpdateSeqId) {
        this.lastUpdateSeqId = lastUpdateSeqId;
    }

    @Column(name = "LAST_UPDATE_TIME")
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "MAILPRICE")
    public Double getMailPrice() {
        return mailPrice;
    }

    public void setMailPrice(Double mailPrice) {
        this.mailPrice = mailPrice;
    }


    @Column(name = "SENDRAT")
    public Double getSendRate() {
        return sendRate;
    }

    public void setSendRate(Double sendRate) {
        this.sendRate = sendRate;
    }



    /*
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTSUITETYPE", schema="IAGENT",
            joinColumns = @JoinColumn(name = "PRODSUITESCMID", referencedColumnName="PLUCODE"),
            inverseJoinColumns = @JoinColumn(name = "PRODSCMID", referencedColumnName="PLUCODE"),
            uniqueConstraints=@UniqueConstraint(columnNames={"PRODSUITESCMID", "PRODSCMID"})
    )

    //@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    //@JoinFormula("(select b.RUID from IAGENT.PRODUCTSUITETYPE a,IAGENT.PLUBASINFO b where a.PRODSCMID=b.PLUCODE and a.PRODSUITESCMID=PLUCODE)")
    public List<PlubasInfo> getSuites() {
        return suites;
    }

    public void setSuites(List<PlubasInfo> suites) {
        this.suites = suites;
    }
    */
}
