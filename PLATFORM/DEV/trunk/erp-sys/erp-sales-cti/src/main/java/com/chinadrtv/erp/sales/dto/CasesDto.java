package com.chinadrtv.erp.sales.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-5
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class CasesDto {
    private  String  casetypeName;
    private  String crdt;
    private  String  crusr;
    private  String  delivered;
    private  String  probdsc;
    private  String  external;
    private  String  cmpfBack;

    public String getCasetypeName() {
        return casetypeName;
    }

    public void setCasetypeName(String casetypeName) {
        this.casetypeName = casetypeName;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getProbdsc() {
        return probdsc;
    }

    public void setProbdsc(String probdsc) {
        this.probdsc = probdsc;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public String getCmpfBack() {
        return cmpfBack;
    }

    public void setCmpfBack(String cmpfBack) {
        this.cmpfBack = cmpfBack;
    }
}

