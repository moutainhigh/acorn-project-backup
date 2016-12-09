package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 运费规则配置明细(重量)
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@DiscriminatorValue("WEIGHT")
public class LogisticsWeightRuleDetail extends LogisticsFeeRuleDetail implements java.io.Serializable {

    private String destinationProvince;
    private String destinationCity;
    private String destinationCounty;
    private Double weightFloor;
    private Double weightCeil;
    private Double weightInitial;
    private Double weightInitialFee;
    private Double weightIncrease;
    private Double weightIncreaseFee;
    private Double succeedFeeRatio;
    private Double succeedFeeAmount;
    private Double refusedFeeRatio;
    private Double refusedFeeAmount;
    private Double specialFee;
    private Double sideLength;
    private Double volumeFeeAmount;
    private Integer roundType;
    private Integer refusedIncludePostFee;

    @Column(name = "DESTINATION_PROVINCE")
    public String getDestinationProvince() {
        return destinationProvince;
    }

    public void setDestinationProvince(String destinationProvince) {
        this.destinationProvince = destinationProvince;
    }

    @Column(name = "DESTINATION_CITY")
    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    @Column(name = "DESTINATION_COUNTY")
    public String getDestinationCounty() {
        return destinationCounty;
    }

    public void setDestinationCounty(String destinationCounty) {
        this.destinationCounty = destinationCounty;
    }

    @Column(name = "WEIGHT_FLOOR")
    public Double getWeightFloor() {
        return weightFloor;
    }

    public void setWeightFloor(Double weightFloor) {
        this.weightFloor = weightFloor;
    }

    @Column(name = "WEIGHT_CEIL")
    public Double getWeightCeil() {
        return weightCeil;
    }

    public void setWeightCeil(Double weightCeil) {
        this.weightCeil = weightCeil;
    }

    @Column(name = "WEIGHT_INITIAL")
    public Double getWeightInitial() {
        return weightInitial;
    }

    public void setWeightInitial(Double weightInitial) {
        this.weightInitial = weightInitial;
    }

    @Column(name = "WEIGHT_INITIAL_FEE")
    public Double getWeightInitialFee() {
        return weightInitialFee;
    }

    public void setWeightInitialFee(Double weightInitialFee) {
        this.weightInitialFee = weightInitialFee;
    }

    @Column(name = "WEIGHT_INCREASE")
    public Double getWeightIncrease() {
        return weightIncrease;
    }

    public void setWeightIncrease(Double weightIncrease) {
        this.weightIncrease = weightIncrease;
    }

    @Column(name = "WEIGHT_INCREASE_FEE")
    public Double getWeightIncreaseFee() {
        return weightIncreaseFee;
    }

    public void setWeightIncreaseFee(Double weightIncreaseFee) {
        this.weightIncreaseFee = weightIncreaseFee;
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

    @Column(name = "SPECIAL_FEE")
    public Double getSpecialFee() {
        return specialFee;
    }

    public void setSpecialFee(Double specialFee) {
        this.specialFee = specialFee;
    }

    @Column(name = "SIDE_LENGTH")
    public Double getSideLength() {
        return sideLength;
    }

    public void setSideLength(Double sideLength) {
        this.sideLength = sideLength;
    }

    @Column(name = "VOLUME_FEE_AMOUNT")
    public Double getVolumeFeeAmount() {
        return volumeFeeAmount;
    }

    public void setVolumeFeeAmount(Double volumeFeeAmount) {
        this.volumeFeeAmount = volumeFeeAmount;
    }

    @Column(name = "ROUND_TYPE")
    public Integer getRoundType() {
        return roundType;
    }

    public void setRoundType(Integer roundType) {
        this.roundType = roundType;
    }

    @Column(name = "REFUSED_INCLUDE_POSTFEE")
    public Integer getRefusedIncludePostFee() {
        return refusedIncludePostFee;
    }

    public void setRefusedIncludePostFee(Integer refusedIncludePostFee) {
        this.refusedIncludePostFee = refusedIncludePostFee;
    }

}
