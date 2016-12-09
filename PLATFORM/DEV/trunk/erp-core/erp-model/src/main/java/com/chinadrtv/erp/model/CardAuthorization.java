package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 信用卡授权
 * User: gdj
 * Date: 13-3-12
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CARDAUTHORIZATION", schema = "IAGENT")
@IdClass(CardAuthorizationId.class)
public class CardAuthorization implements java.io.Serializable {
    private String orderid;
    private String orderdetid;
    private String prodbankid;
    private String scode;
    private String cardrightnum;
    private String prodprice;   //Double
    private Date impdt;
    private String impusr;
    private Date confirmdt;
    private String confirmusr;
    private String cardtype;
    private String cardapplynum;
    private String bankcardno;


    @Id
    @Column(name = "orderid")
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "orderdetid")
    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    @Column(name = "prodbankid")
    public String getProdbankid() {
        return prodbankid;
    }

    public void setProdbankid(String prodbankid) {
        this.prodbankid = prodbankid;
    }

    @Column(name = "scode")
    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    @Id
    @Column(name = "cardrightnum")
    public String getCardrightnum() {
       return cardrightnum;
    }

    public void setCardrightnum(String cardrightnum) {
        this.cardrightnum = cardrightnum;
    }

    @Column(name = "prodprice")
    public String getProdprice() {
        return prodprice;
    }

    public void setProdprice(String prodprice) {
        this.prodprice = prodprice;
    }

    @Column(name = "impdt")
    public Date getImpdt() {
        return impdt;
    }

    public void setImpdt(Date impdt) {
        this.impdt = impdt;
    }

    @Column(name = "impusr")
    public String getImpusr() {
        return impusr;
    }

    public void setImpusr(String impusr) {
        this.impusr = impusr;
    }

    @Column(name = "confirmdt")
    public Date getConfirmdt() {
        return confirmdt;
    }

    public void setConfirmdt(Date confirmdt) {
        this.confirmdt = confirmdt;
    }

    @Column(name = "confirmusr")
    public String getConfirmusr() {
        return confirmusr;
    }

    public void setConfirmusr(String confirmusr) {
        this.confirmusr = confirmusr;
    }

    @Column(name = "cardtype")
    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    @Column(name = "cardapplynum")
    public String getCardapplynum() {
        return cardapplynum;
    }

    public void setCardapplynum(String cardapplynum) {
        this.cardapplynum = cardapplynum;
    }

    @Column(name = "bankcardno")
    public String getBankcardno() {
        return bankcardno;
    }

    public void setBankcardno(String bankcardno) {
        this.bankcardno = bankcardno;
    }
}
