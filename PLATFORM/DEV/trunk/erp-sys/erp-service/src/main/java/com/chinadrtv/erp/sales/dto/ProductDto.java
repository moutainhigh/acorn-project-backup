package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.model.agent.Product;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-11-12
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
public class ProductDto {
    private Product product;
    //购物车产品规格
    private List<NcPlubasInfoAttribute> productTypes;

    public ProductDto(Product product){
        this.product = product;
    }

    public List<NcPlubasInfoAttribute> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<NcPlubasInfoAttribute> productTypes) {
        this.productTypes = productTypes;
    }

    public String getRecid() {
        return product.getRecid();
    }

    public void setPoint(BigDecimal point) {
        product.setPoint(point);
    }

    public void setBuynumber(Long buynumber) {
        product.setBuynumber(buynumber);
    }

    public void setLastUpdateSeqid(Long lastUpdateSeqid) {
        product.setLastUpdateSeqid(lastUpdateSeqid);
    }

    public void setRecid(String recid) {
        product.setRecid(recid);
    }

    public String getProdname() {
        return product.getProdname();
    }

    public void setReturnratio(String returnratio) {
        product.setReturnratio(returnratio);
    }

    public void setDealerprice(BigDecimal dealerprice) {
        product.setDealerprice(dealerprice);
    }

    public String getBaleprodid() {
        return product.getBaleprodid();
    }

    public Long getScriptid() {
        return product.getScriptid();
    }

    public void setBrandid(String brandid) {
        product.setBrandid(brandid);
    }

    public void setBaleprodid(String baleprodid) {
        product.setBaleprodid(baleprodid);
    }

    public void setLprice(BigDecimal lprice) {
        product.setLprice(lprice);
    }


    public String getProcurement() {
        return product.getProcurement();
    }

    public void setFcost(BigDecimal fcost) {
        product.setFcost(fcost);
    }


    public BigDecimal getLprice() {
        return product.getLprice();
    }

    public void setProdunit(String produnit) {
        product.setProdunit(produnit);
    }

    public BigDecimal getPoint() {
        return product.getPoint();
    }

    public BigDecimal getFeerate() {
        return product.getFeerate();
    }

    public BigDecimal getUnitprice() {
        return product.getUnitprice();
    }

    public void setDiscount(BigDecimal discount) {
        product.setDiscount(discount);
    }

    public void setCompanyid(String companyid) {
        product.setCompanyid(companyid);
    }

    public Date getProdcrdt() {
        return product.getProdcrdt();
    }

    public String getDiscountlimit() {
        return product.getDiscountlimit();
    }

    public void setProdcrdt(Date prodcrdt) {
        product.setProdcrdt(prodcrdt);
    }

    public void setHprice(BigDecimal hprice) {
        product.setHprice(hprice);
    }

    public void setInfanteeterms(String infanteeterms) {
        product.setInfanteeterms(infanteeterms);
    }

    public String getRemark() {
        return product.getRemark();
    }

    public void setDiscountlimit(String discountlimit) {
        product.setDiscountlimit(discountlimit);
    }

    public BigDecimal getHprice() {
        return product.getHprice();
    }

    public void setSname(String sname) {
        product.setSname(sname);
    }

    public BigDecimal getDiscount() {
        return product.getDiscount();
    }

    public String getMainchannel() {
        return product.getMainchannel();
    }

    public String getPaymentterms() {
        return product.getPaymentterms();
    }

    public String getOldprodid() {
        return product.getOldprodid();
    }

    public void setCodenum(String codenum) {
        product.setCodenum(codenum);
    }

    public String getPresent() {
        return product.getPresent();
    }

    public void setUnitprice(BigDecimal unitprice) {
        product.setUnitprice(unitprice);
    }

    public String getProdmodel() {
        return product.getProdmodel();
    }

    public void setFeerate(BigDecimal feerate) {
        product.setFeerate(feerate);
    }

    public void setMainchannel(String mainchannel) {
        product.setMainchannel(mainchannel);
    }

    public String getSname() {
        return product.getSname();
    }

    public void setPaymentterms(String paymentterms) {
        product.setPaymentterms(paymentterms);
    }

    public Long getLastLockSeqid() {
        return product.getLastLockSeqid();
    }

    public void setProdname(String prodname) {
        product.setProdname(prodname);
    }

    public String getDevelopment() {
        return product.getDevelopment();
    }

    public String getStatus() {
        return product.getStatus();
    }

    public void setInprice(BigDecimal inprice) {
        product.setInprice(inprice);
    }

    public void setMembertype(String membertype) {
        product.setMembertype(membertype);
    }

    public void setRemark(String remark) {
        product.setRemark(remark);
    }

    public String getCompanyid() {
        return product.getCompanyid();
    }

    public void setPresent(String present) {
        product.setPresent(present);
    }

    public BigDecimal getFcost() {
        return product.getFcost();
    }

    public String getUniname() {
        return product.getUniname();
    }

    public String getReturnratio() {
        return product.getReturnratio();
    }

    public String getProdunit() {
        return product.getProdunit();
    }

    public void setOldprodid(String oldprodid) {
        product.setOldprodid(oldprodid);
    }

    public String getPriority() {
        return product.getPriority();
    }

    public BigDecimal getInprice() {
        return product.getInprice();
    }

    public void setUniname(String uniname) {
        product.setUniname(uniname);
    }

    public BigDecimal getTransexpense() {
        return product.getTransexpense();
    }

    public void setCatid(String catid) {
        product.setCatid(catid);
    }

    public void setScript(String script) {
        product.setScript(script);
    }

    public void setProdid(String prodid) {
        product.setProdid(prodid);
    }

    public String getProdid() {
        return product.getProdid();
    }

    public void setStatus(String status) {
        product.setStatus(status);
    }

    public void setLastLockSeqid(Long lastLockSeqid) {
        product.setLastLockSeqid(lastLockSeqid);
    }

    public BigDecimal getCostpct() {
        return product.getCostpct();
    }

    public String getScript() {
        return product.getScript();
    }

    public Long getLastUpdateSeqid() {
        return product.getLastUpdateSeqid();
    }

    public void setPriority(String priority) {
        product.setPriority(priority);
    }

    public String getCodenum() {
        return product.getCodenum();
    }

    public BigDecimal getDealerprice() {
        return product.getDealerprice();
    }

    public void setScriptid(Long scriptid) {
        product.setScriptid(scriptid);
    }

    public String getMembertype() {
        return product.getMembertype();
    }

    public void setProdmodel(String prodmodel) {
        product.setProdmodel(prodmodel);
    }

    public String getScode() {
        return product.getScode();
    }

    public Long getBuynumber() {
        return product.getBuynumber();
    }

    public String getInfanteeterms() {
        return product.getInfanteeterms();
    }

    public void setDevelopment(String development) {
        product.setDevelopment(development);
    }

    public void setProcurement(String procurement) {
        product.setProcurement(procurement);
    }

    public void setCostpct(BigDecimal costpct) {
        product.setCostpct(costpct);
    }

    public String getBrandid() {
        return product.getBrandid();
    }

    public void setScode(String scode) {
        product.setScode(scode);
    }

    public String getCatid() {
        return product.getCatid();
    }

    public void setTransexpense(BigDecimal transexpense) {
        product.setTransexpense(transexpense);
    }
}
