package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.Cmpfback;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.dto.AddressDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-28
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public class CmpfbackEventDto {
    private String address;
    private String zip;
    private String phoneMask;
    private String contactName;
    private boolean complainManager;
    private boolean cmpfbackDepartment;
    private String  resultMessage;

    private Cmpfback cmpfback = new Cmpfback();

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneMask() {
        return phoneMask;
    }

    public void setPhoneMask(String phoneMask) {
        this.phoneMask = phoneMask;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public boolean isComplainManager() {
        return complainManager;
    }

    public void setComplainManager(boolean complainManager) {
        this.complainManager = complainManager;
    }

    public boolean isCmpfbackDepartment() {
        return cmpfbackDepartment;
    }

    public void setCmpfbackDepartment(boolean cmpfbackDepartment) {
        this.cmpfbackDepartment = cmpfbackDepartment;
    }

    public Cmpfback getCmpfback() {
        return cmpfback;
    }

    public void setCmpfback(Cmpfback cmpfback) {
        this.cmpfback = cmpfback;
    }


    public String getCaseid() {
        return cmpfback.getCaseid();
    }

    public String getChuliusr() {
        return cmpfback.getChuliusr();
    }

    public void setAreacode(String areacode) {
        cmpfback.setAreacode(areacode);
    }

    public String getProbdsc() {
        return cmpfback.getProbdsc();
    }

    public void setChuliusr(String chuliusr) {
        cmpfback.setChuliusr(chuliusr);
    }

    public void setProbdsc(String probdsc) {
        cmpfback.setProbdsc(probdsc);
    }

    public void setCrdt(Date crdt) {
        cmpfback.setCrdt(crdt);
    }

    public String getCrusr() {
        return cmpfback.getCrusr();
    }

    public String getPhone() {
        return cmpfback.getPhone();
    }

    public String getYijian2() {
        return cmpfback.getYijian2();
    }

    public void setResult(String result) {
        cmpfback.setResult(result);
    }

    public Date getChulidt() {
        return cmpfback.getChulidt();
    }

    public String getReason() {
        return cmpfback.getReason();
    }

    public void setChuliqk(String chuliqk) {
        cmpfback.setChuliqk(chuliqk);
    }

    public void setOrderid(String orderid) {
        cmpfback.setOrderid(orderid);
    }

    public void setProductdsc(String productdsc) {
        cmpfback.setProductdsc(productdsc);
    }

    public void setYijian2(String yijian2) {
        cmpfback.setYijian2(yijian2);
    }

    public String getFbusr() {
        return cmpfback.getFbusr();
    }

    public Date getOrdercrdt() {
        return cmpfback.getOrdercrdt();
    }

    public String getResult() {
        return cmpfback.getResult();
    }

    public String getFeedbackdsc() {
        return cmpfback.getFeedbackdsc();
    }

    public void setContactid(String contactid) {
        cmpfback.setContactid(contactid);
    }

    public void setChulidt(Date chulidt) {
        cmpfback.setChulidt(chulidt);
    }

    public Date getCrdt() {
        return cmpfback.getCrdt();
    }

    public String getAreacode() {
        return cmpfback.getAreacode();
    }

    public void setFbusr(String fbusr) {
        cmpfback.setFbusr(fbusr);
    }

    public void setReason(String reason) {
        cmpfback.setReason(reason);
    }

    public void setDnote(String dnote) {
        cmpfback.setDnote(dnote);
    }

    public String getOrderid() {
        return cmpfback.getOrderid();
    }

    public void setCrusr(String crusr) {
        cmpfback.setCrusr(crusr);
    }

    public String getChuliqk() {
        return cmpfback.getChuliqk();
    }

    public void setPhone(String phone) {
        cmpfback.setPhone(phone);
    }

    public String getYijian() {
        return cmpfback.getYijian();
    }

    public void setOrdercrdt(Date ordercrdt) {
        cmpfback.setOrdercrdt(ordercrdt);
    }

    public String getContactid() {
        return cmpfback.getContactid();
    }

    public Date getFbdt() {
        return cmpfback.getFbdt();
    }

    public String getDnote() {
        return cmpfback.getDnote();
    }

    public String getProductdsc() {
        return cmpfback.getProductdsc();
    }

    public void setCaseid(String caseid) {
        cmpfback.setCaseid(caseid);
    }

    public void setFbdt(Date fbdt) {
        cmpfback.setFbdt(fbdt);
    }

    public void setFeedbackdsc(String feedbackdsc) {
        cmpfback.setFeedbackdsc(feedbackdsc);
    }

    public void setYijian(String yijian) {
        cmpfback.setYijian(yijian);
    }
}
