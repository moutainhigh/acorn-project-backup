package com.chinadrtv.oms.internet.dal.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 12-12-26
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class TradeFeedback implements java.io.Serializable {
    private String opsTradeId;
    private String tmsCode;
    private String tmsLogisiticsIds;
    private Double weight;
    private Date createDat;

    public TradeFeedback(){

    }

    public TradeFeedback(String opsTradeId, String tmsCode, String tmsLogisiticsIds, Double weight, Date createDat){
        this.opsTradeId = opsTradeId;
        this.tmsCode = tmsCode;
        this.tmsLogisiticsIds = tmsLogisiticsIds;
        this.weight = weight;
        this.createDat = createDat;
    }

    public String getOpsTradeId() {
        return opsTradeId;
    }

    public void setOpsTradeId(String opsTradeId) {
        this.opsTradeId = opsTradeId;
    }

    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    public String getTmsLogisiticsIds() {
        return tmsLogisiticsIds;
    }

    public void setTmsLogisiticsIds(String tmsLogisiticsIds) {
        this.tmsLogisiticsIds = tmsLogisiticsIds;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getCreateDat() {
        return createDat;
    }

    public void setCreateDat(Date createDat) {
        this.createDat = createDat;
    }
}
