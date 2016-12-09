package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-19
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
public class ScmPromotionDto {
    private ScmPromotion scmPromotion;
    //购物车产品规格
    private List<NcPlubasInfoAttribute> productTypes;

    public   ScmPromotionDto (ScmPromotion scmPromotion){
          this.scmPromotion =scmPromotion;
    }

    public List<NcPlubasInfoAttribute> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<NcPlubasInfoAttribute> productTypes) {
        this.productTypes = productTypes;
    }

    public Long getRuid() {
        return scmPromotion.getRuid();
    }

    public void setDocno(String docno) {
        scmPromotion.setDocno(docno);
    }

    public void setProdname(String prodname) {
        scmPromotion.setProdname(prodname);
    }

    public Double getFullamount() {
        return scmPromotion.getFullamount();
    }

    public void setPluid(String pluid) {
        scmPromotion.setPluid(pluid);
    }

    public String getType() {
        return scmPromotion.getType();
    }

    public String getEtime() {
        return scmPromotion.getEtime();
    }

    public void setEtime(String etime) {
        scmPromotion.setEtime(etime);
    }

    public String getDocno() {
        return scmPromotion.getDocno();
    }

    public String getRuiddet() {
        return scmPromotion.getRuiddet();
    }

    public void setWcycle(String wcycle) {
        scmPromotion.setWcycle(wcycle);
    }

    public String getProdtype() {
        return scmPromotion.getProdtype();
    }

    public String getProdname() {
        return scmPromotion.getProdname();
    }

    public void setProddsc(String proddsc) {
        scmPromotion.setProddsc(proddsc);
    }

    public String getDocname() {
        return scmPromotion.getDocname();
    }

    public void setSlprc(Double slprc) {
        scmPromotion.setSlprc(slprc);
    }

    public void setReductionamount(Double reductionamount) {
        scmPromotion.setReductionamount(reductionamount);
    }

    public void setDsc(String dsc) {
        scmPromotion.setDsc(dsc);
    }

    public void setFullamount(Double fullamount) {
        scmPromotion.setFullamount(fullamount);
    }

    public String getEdat() {
        return scmPromotion.getEdat();
    }

    public String getWcycle() {
        return scmPromotion.getWcycle();
    }

    public void setBdat(String bdat) {
        scmPromotion.setBdat(bdat);
    }

    public String getBtime() {
        return scmPromotion.getBtime();
    }

    public Double getSlprc() {
        return scmPromotion.getSlprc();
    }

    public String getProddsc() {
        return scmPromotion.getProddsc();
    }

    public int getStatus() {
        return scmPromotion.getStatus();
    }

    public String getDsc() {
        return scmPromotion.getDsc();
    }

    public void setRuiddet(String ruiddet) {
        scmPromotion.setRuiddet(ruiddet);
    }

    public void setType(String type) {
        scmPromotion.setType(type);
    }

    public void setDocname(String docname) {
        scmPromotion.setDocname(docname);
    }

    public void setEdat(String edat) {
        scmPromotion.setEdat(edat);
    }

    public void setStatus(int status) {
        scmPromotion.setStatus(status);
    }

    public String getPluid() {
        return scmPromotion.getPluid();
    }

    public void setBtime(String btime) {
        scmPromotion.setBtime(btime);
    }

    public String getBdat() {
        return scmPromotion.getBdat();
    }

    public void setProdtype(String prodtype) {
        scmPromotion.setProdtype(prodtype);
    }

    public void setRuid(Long ruid) {
        scmPromotion.setRuid(ruid);
    }

    public Double getReductionamount() {
        return scmPromotion.getReductionamount();
    }
}
