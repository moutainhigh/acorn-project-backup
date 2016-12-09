package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * 运费规则配置明细(价格)
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue("PRICE")
public class LogisticsPriceRuleDetail extends LogisticsFeeRuleDetail implements java.io.Serializable {

    private Double amountFloor;      //金额范围最小(>=)
    private Double amountCeil;
    private Double succeedFeeRatio;
    private Double succeedFeeAmount;
    private Double refusedFeeRatio;
    private Double refusedFeeAmount;
    private Double virtualFeeAmount;

    @Column(name = "AMOUNT_FLOOR")
    public Double getAmountFloor() {
        return amountFloor;
    }

    public void setAmountFloor(Double amountFloor) {
        this.amountFloor = amountFloor;
    }

    @Column(name = "AMOUNT_CEIL")
    public Double getAmountCeil() {
        return amountCeil;
    }

    public void setAmountCeil(Double amountCeil) {
        this.amountCeil = amountCeil;
    }

    @Column(name = "SUCCEED_FEE_RATIO")
    public Double getSucceedFeeRatio() {
        return succeedFeeRatio;
    }

    public void setSucceedFeeRatio(Double succeedFeeRatio) {
        this.succeedFeeRatio = succeedFeeRatio;
    }

    @Column(name = "SUCCEED_FEE_AMOUNT")
    public Double getSucceedFeeAmount() {
        return succeedFeeAmount;
    }

    public void setSucceedFeeAmount(Double succeedFeeAmount) {
        this.succeedFeeAmount = succeedFeeAmount;
    }

    @Column(name = "REFUSED_FEE_RATIO")
    public Double getRefusedFeeRatio() {
        return refusedFeeRatio;
    }

    public void setRefusedFeeRatio(Double refusedFeeRatio) {
        this.refusedFeeRatio = refusedFeeRatio;
    }

    @Column(name = "REFUSED_FEE_AMOUNT")
    public Double getRefusedFeeAmount() {
        return refusedFeeAmount;
    }

    public void setRefusedFeeAmount(Double refusedFeeAmount) {
        this.refusedFeeAmount = refusedFeeAmount;
    }

    @Column(name = "VIRTUAL_FEE_AMOUNT")
    public Double getVirtualFeeAmount() {
        return virtualFeeAmount;
    }

    public void setVirtualFeeAmount(Double virtualFeeAmount) {
        this.virtualFeeAmount = virtualFeeAmount;
    }
}
