package com.chinadrtv.erp.model.agent;

//import org.hibernate.envers.Audited;
//import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-17
 */
//@Audited(targetAuditMode= RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name="PRODUCT", schema = "IAGENT")
public class Product implements Serializable {

    /**
     * 内部ID
     */
    //private Long id;

    private String recid;

    private String prodid;

    private String catid;

    private String brandid;

    private String prodname;

    private String sname;

    private String scode;

    private BigDecimal unitprice;

    private String status;

    private Long scriptid;

    private String priority;

    private String produnit;

    private String remark;

    private BigDecimal costpct;

    private BigDecimal inprice;

    private String codenum;

    private BigDecimal lprice;

    private BigDecimal hprice;

    private String script;

    private String discountlimit;

    private BigDecimal discount;

    private String uniname;

    private BigDecimal point;

    private String companyid;

    private Long buynumber;

    private String baleprodid;

    private BigDecimal feerate;

    private String oldprodid;

    private String membertype;

    private Date prodcrdt;

    private String prodmodel;

    private BigDecimal transexpense;

    private String present;

    private BigDecimal dealerprice;

    private String mainchannel;

    private String returnratio;

    private String paymentterms;

    private String procurement;

    private String development;

    private String infanteeterms;

    private BigDecimal fcost;

    private Long lastLockSeqid;

    private Long lastUpdateSeqid;

    //private Date lastUpdateTime;

    /*@Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "IAGENT.PRODUCT_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    @Column(name = "RECID", length = 30)
    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    @Id
    @Column(name = "PRODID", length = 30)
    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    @Column(name = "CATID", length = 20)
    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    @Column(name = "BRANDID", length = 100)
    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    @Column(name = "PRODNAME", length = 100)
    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    @Column(name = "SNAME", length = 60)
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Column(name = "SCODE", length = 10)
    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    @Column(name = "UNITPRICE",  precision = 10, scale = 2)
    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    @Column(name = "STATUS", length = 100)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "SCRIPTID")
    public Long getScriptid() {
        return scriptid;
    }

    public void setScriptid(Long scriptid) {
        this.scriptid = scriptid;
    }

    @Column(name = "PRIORITY", length = 100)
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "PRODUNIT", length = 20)
    public String getProdunit() {
        return produnit;
    }

    public void setProdunit(String produnit) {
        this.produnit = produnit;
    }

    @Column(name = "REMARK", length = 10)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "COSTPCT", precision = 10, scale = 2)
    public BigDecimal getCostpct() {
        return costpct;
    }

    public void setCostpct(BigDecimal costpct) {
        this.costpct = costpct;
    }

    @Column(name = "INPRICE", precision = 10, scale = 2)
    public BigDecimal getInprice() {
        return inprice;
    }

    public void setInprice(BigDecimal inprice) {
        this.inprice = inprice;
    }

    @Column(name = "CODENUM", length = 12)
    public String getCodenum() {
        return codenum;
    }

    public void setCodenum(String codenum) {
        this.codenum = codenum;
    }

    @Column(name = "LPRICE", precision = 10, scale = 2)
    public BigDecimal getLprice() {
        return lprice;
    }

    public void setLprice(BigDecimal lprice) {
        this.lprice = lprice;
    }

    @Column(name = "HPRICE", precision = 10, scale = 2)
    public BigDecimal getHprice() {
        return hprice;
    }

    public void setHprice(BigDecimal hprice) {
        this.hprice = hprice;
    }

    @Column(name = "SCRIPT", length = 4000)
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Column(name = "DISCOUNTLIMIT", length = 4)
    public String getDiscountlimit() {
        return discountlimit;
    }

    public void setDiscountlimit(String discountlimit) {
        this.discountlimit = discountlimit;
    }

    @Column(name = "DISCOUNT", precision = 10, scale = 2)
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Column(name = "UNINAME", length = 100)
    public String getUniname() {
        return uniname;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    @Column(name = "POINT", precision = 10, scale = 2)
    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    @Column(name = "COMPANYID", length = 8)
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Column(name = "BUYNUMBER")
    public Long getBuynumber() {
        return buynumber;
    }

    public void setBuynumber(Long buynumber) {
        this.buynumber = buynumber;
    }

    @Column(name = "BALEPRODID", length = 30)
    public String getBaleprodid() {
        return baleprodid;
    }

    public void setBaleprodid(String baleprodid) {
        this.baleprodid = baleprodid;
    }

    @Column(name = "FEERATE", precision = 10, scale = 2)
    public BigDecimal getFeerate() {
        return feerate;
    }

    public void setFeerate(BigDecimal feerate) {
        this.feerate = feerate;
    }

    @Column(name = "OLDPRODID", length = 30)
    public String getOldprodid() {
        return oldprodid;
    }

    public void setOldprodid(String oldprodid) {
        this.oldprodid = oldprodid;
    }

    @Column(name = "MEMBERTYPE", length = 20)
    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

    @Column(name = "PRODCRDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getProdcrdt() {
        return prodcrdt;
    }

    public void setProdcrdt(Date prodcrdt) {
        this.prodcrdt = prodcrdt;
    }

    @Column(name = "PRODMODEL", length = 60)
    public String getProdmodel() {
        return prodmodel;
    }

    public void setProdmodel(String prodmodel) {
        this.prodmodel = prodmodel;
    }

    @Column(name = "TRANSEXPENSE", precision = 10, scale = 2)
    public BigDecimal getTransexpense() {
        return transexpense;
    }

    public void setTransexpense(BigDecimal transexpense) {
        this.transexpense = transexpense;
    }

    @Column(name = "PRESENT", length = 2)
    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    @Column(name = "DEALERPRICE", precision = 10, scale = 2)
    public BigDecimal getDealerprice() {
        return dealerprice;
    }

    public void setDealerprice(BigDecimal dealerprice) {
        this.dealerprice = dealerprice;
    }

    @Column(name = "MAINCHANNEL", length = 60)
    public String getMainchannel() {
        return mainchannel;
    }

    public void setMainchannel(String mainchannel) {
        this.mainchannel = mainchannel;
    }

    @Column(name = "RETURNRATIO", length = 20)
    public String getReturnratio() {
        return returnratio;
    }

    public void setReturnratio(String returnratio) {
        this.returnratio = returnratio;
    }

    @Column(name = "PAYMENTTERMS", length = 20)
    public String getPaymentterms() {
        return paymentterms;
    }

    public void setPaymentterms(String paymentterms) {
        this.paymentterms = paymentterms;
    }

    @Column(name = "PROCUREMENT", length = 20)
    public String getProcurement() {
        return procurement;
    }

    public void setProcurement(String procurement) {
        this.procurement = procurement;
    }

    @Column(name = "DEVELOPMENT", length = 20)
    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    @Column(name = "INFANTEETERMS", length = 20)
    public String getInfanteeterms() {
        return infanteeterms;
    }

    public void setInfanteeterms(String infanteeterms) {
        this.infanteeterms = infanteeterms;
    }

    @Column(name = "FCOST", precision = 10, scale = 2)
    public BigDecimal getFcost() {
        return fcost;
    }

    public void setFcost(BigDecimal fcost) {
        this.fcost = fcost;
    }

    @Column(name = "LAST_LOCK_SEQID")
    public Long getLastLockSeqid() {
        return lastLockSeqid;
    }

    public void setLastLockSeqid(Long lastLockSeqid) {
        this.lastLockSeqid = lastLockSeqid;
    }

    @Column(name = "LAST_UPDATE_SEQID")
    public Long getLastUpdateSeqid() {
        return lastUpdateSeqid;
    }

    public void setLastUpdateSeqid(Long lastUpdateSeqid) {
        this.lastUpdateSeqid = lastUpdateSeqid;
    }

    /*@Column(name = "LAST_UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }*/

    @Override
    public String toString() {
        return this.prodid!=null?this.prodid:"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (buynumber != product.buynumber) return false;
        if (lastLockSeqid != product.lastLockSeqid) return false;
        if (lastUpdateSeqid != product.lastUpdateSeqid) return false;
        if (scriptid != product.scriptid) return false;
        if (baleprodid != null ? !baleprodid.equals(product.baleprodid) : product.baleprodid != null) return false;
        if (brandid != null ? !brandid.equals(product.brandid) : product.brandid != null) return false;
        if (catid != null ? !catid.equals(product.catid) : product.catid != null) return false;
        if (codenum != null ? !codenum.equals(product.codenum) : product.codenum != null) return false;
        if (companyid != null ? !companyid.equals(product.companyid) : product.companyid != null) return false;
        if (costpct != null ? !costpct.equals(product.costpct) : product.costpct != null) return false;
        if (dealerprice != null ? !dealerprice.equals(product.dealerprice) : product.dealerprice != null) return false;
        if (development != null ? !development.equals(product.development) : product.development != null) return false;
        if (discount != null ? !discount.equals(product.discount) : product.discount != null) return false;
        if (discountlimit != null ? !discountlimit.equals(product.discountlimit) : product.discountlimit != null)
            return false;
        if (fcost != null ? !fcost.equals(product.fcost) : product.fcost != null) return false;
        if (feerate != null ? !feerate.equals(product.feerate) : product.feerate != null) return false;
        if (hprice != null ? !hprice.equals(product.hprice) : product.hprice != null) return false;
        if (infanteeterms != null ? !infanteeterms.equals(product.infanteeterms) : product.infanteeterms != null)
            return false;
        if (inprice != null ? !inprice.equals(product.inprice) : product.inprice != null) return false;
        if (lprice != null ? !lprice.equals(product.lprice) : product.lprice != null) return false;
        if (mainchannel != null ? !mainchannel.equals(product.mainchannel) : product.mainchannel != null) return false;
        if (membertype != null ? !membertype.equals(product.membertype) : product.membertype != null) return false;
        if (oldprodid != null ? !oldprodid.equals(product.oldprodid) : product.oldprodid != null) return false;
        if (paymentterms != null ? !paymentterms.equals(product.paymentterms) : product.paymentterms != null)
            return false;
        if (point != null ? !point.equals(product.point) : product.point != null) return false;
        if (present != null ? !present.equals(product.present) : product.present != null) return false;
        if (priority != null ? !priority.equals(product.priority) : product.priority != null) return false;
        if (procurement != null ? !procurement.equals(product.procurement) : product.procurement != null) return false;
        if (prodcrdt != null ? !prodcrdt.equals(product.prodcrdt) : product.prodcrdt != null) return false;
        if (prodid != null ? !prodid.equals(product.prodid) : product.prodid != null) return false;
        if (prodmodel != null ? !prodmodel.equals(product.prodmodel) : product.prodmodel != null) return false;
        if (prodname != null ? !prodname.equals(product.prodname) : product.prodname != null) return false;
        if (produnit != null ? !produnit.equals(product.produnit) : product.produnit != null) return false;
        if (recid != null ? !recid.equals(product.recid) : product.recid != null) return false;
        if (remark != null ? !remark.equals(product.remark) : product.remark != null) return false;
        if (returnratio != null ? !returnratio.equals(product.returnratio) : product.returnratio != null) return false;
        if (scode != null ? !scode.equals(product.scode) : product.scode != null) return false;
        if (script != null ? !script.equals(product.script) : product.script != null) return false;
        if (sname != null ? !sname.equals(product.sname) : product.sname != null) return false;
        if (status != null ? !status.equals(product.status) : product.status != null) return false;
        if (transexpense != null ? !transexpense.equals(product.transexpense) : product.transexpense != null)
            return false;
        if (uniname != null ? !uniname.equals(product.uniname) : product.uniname != null) return false;
        if (unitprice != null ? !unitprice.equals(product.unitprice) : product.unitprice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recid != null ? recid.hashCode() : 0;
        result = 31 * result + (prodid != null ? prodid.hashCode() : 0);
        result = 31 * result + (catid != null ? catid.hashCode() : 0);
        result = 31 * result + (brandid != null ? brandid.hashCode() : 0);
        result = 31 * result + (prodname != null ? prodname.hashCode() : 0);
        result = 31 * result + (sname != null ? sname.hashCode() : 0);
        result = 31 * result + (scode != null ? scode.hashCode() : 0);
        result = 31 * result + (unitprice != null ? unitprice.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (scriptid!=null? scriptid.hashCode():0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (produnit != null ? produnit.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (costpct != null ? costpct.hashCode() : 0);
        result = 31 * result + (inprice != null ? inprice.hashCode() : 0);
        result = 31 * result + (codenum != null ? codenum.hashCode() : 0);
        result = 31 * result + (lprice != null ? lprice.hashCode() : 0);
        result = 31 * result + (hprice != null ? hprice.hashCode() : 0);
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (discountlimit != null ? discountlimit.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (uniname != null ? uniname.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + (companyid != null ? companyid.hashCode() : 0);
        result = 31 * result + (buynumber!=null? buynumber.hashCode():0);
        result = 31 * result + (baleprodid != null ? baleprodid.hashCode() : 0);
        result = 31 * result + (feerate != null ? feerate.hashCode() : 0);
        result = 31 * result + (oldprodid != null ? oldprodid.hashCode() : 0);
        result = 31 * result + (membertype != null ? membertype.hashCode() : 0);
        result = 31 * result + (prodcrdt != null ? prodcrdt.hashCode() : 0);
        result = 31 * result + (prodmodel != null ? prodmodel.hashCode() : 0);
        result = 31 * result + (transexpense != null ? transexpense.hashCode() : 0);
        result = 31 * result + (present != null ? present.hashCode() : 0);
        result = 31 * result + (dealerprice != null ? dealerprice.hashCode() : 0);
        result = 31 * result + (mainchannel != null ? mainchannel.hashCode() : 0);
        result = 31 * result + (returnratio != null ? returnratio.hashCode() : 0);
        result = 31 * result + (paymentterms != null ? paymentterms.hashCode() : 0);
        result = 31 * result + (procurement != null ? procurement.hashCode() : 0);
        result = 31 * result + (development != null ? development.hashCode() : 0);
        result = 31 * result + (infanteeterms != null ? infanteeterms.hashCode() : 0);
        result = 31 * result + (fcost != null ? fcost.hashCode() : 0);
        result = 31 * result + (lastLockSeqid !=null ?lastLockSeqid.hashCode():0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        return result;
    }
}
