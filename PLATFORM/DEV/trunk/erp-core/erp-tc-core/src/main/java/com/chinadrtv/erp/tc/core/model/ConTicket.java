package com.chinadrtv.erp.tc.core.model;

public class ConTicket
{
    private String stype;
    private Double ncurprodprice;
    private Double curticketprice;
    private String sorderid;
    private String scontactid;
    private String scrusr;

    public String getStype()
    {
        return this.stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public Double getNcurprodprice() {
        return this.ncurprodprice;
    }

    public void setNcurprodprice(Double ncurprodprice) {
        this.ncurprodprice = ncurprodprice;
    }

    public Double getCurticketprice() {
        return this.curticketprice;
    }

    public void setCurticketprice(Double curticketprice) {
        this.curticketprice = curticketprice;
    }

    public String getSorderid() {
        return this.sorderid;
    }

    public void setSorderid(String sorderid) {
        this.sorderid = sorderid;
    }

    public String getScontactid() {
        return this.scontactid;
    }

    public void setScontactid(String scontactid) {
        this.scontactid = scontactid;
    }

    public String getScrusr() {
        return this.scrusr;
    }

    public void setScrusr(String scrusr) {
        this.scrusr = scrusr;
    }
}