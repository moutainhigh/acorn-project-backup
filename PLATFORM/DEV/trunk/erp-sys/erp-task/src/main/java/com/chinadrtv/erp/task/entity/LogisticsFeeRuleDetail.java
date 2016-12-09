package com.chinadrtv.erp.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinadrtv.erp.task.core.orm.entity.IdEntity;

@Entity
@Table(name = "LOGISTICS_FEE_RULE_DETAIL", schema = "ACOAPP_OMS")
public class LogisticsFeeRuleDetail extends IdEntity{

	private static final long serialVersionUID = -4012712192510055500L;
	
	@Column(name = "RULE_TYPE")
	private String ruleType;	//计费类型 按单，按重量
	
	@Column(name = "REFUSED_INCLUDE_POSTFEE")
	private String refusedIncludePostfee;	//拒收包含邮费（o或null不包含，1包含）
	
	@Column(name = "ROUND_TYPE")
	private String roundType;	//取整方式（0或null正常，1向上取整）
	
	//////按金额计算所用到的字段/////
	@Column(name = "AMOUNT_FLOOR")
    private Double amountFloor;      		//金额范围最小(>=)
	@Column(name = "AMOUNT_CEIL")			
    private Double amountCeil;				//金额范围最大<
	@Column(name = "VIRTUAL_FEE_AMOUNT")
    private Double virtualFeeAmount;
    //////按金额计算所用到的字段/////
    
    //////按重量计算所用到的字段/////
	@Column(name = "DESTINATION_PROVINCE")
    private String destinationProvince;
	@Column(name = "DESTINATION_CITY")
    private String destinationCity;
	@Column(name = "DESTINATION_COUNTY")
    private String destinationCounty;
	@Column(name = "WEIGHT_FLOOR")
    private Double weightFloor;
	@Column(name = "WEIGHT_CEIL")
    private Double weightCeil;

	@Column(name = "WEIGHT_INITIAL")
	private Double weightInitial;
	@Column(name = "WEIGHT_INITIAL_FEE")
    private Double weightInitialFee;
	@Column(name = "WEIGHT_INCREASE")
    private Double weightIncrease;
	@Column(name = "WEIGHT_INCREASE_FEE")
    private Double weightIncreaseFee;
	@Column(name = "SPECIAL_FEE")
    private Double specialFee;
	@Column(name = "SIDE_LENGTH")
    private Double sideLength;
	@Column(name = "VOLUME_FEE_AMOUNT")
    private Double volumeFeeAmount;
    //////按重量计算所用到的字段/////
    
	//////金额、重量都用到的字段/////
    @Column(name = "SUCCEED_FEE_RATIO")
    private Double succeedFeeRatio = 0D;		//成功费率 
    @Column(name = "SUCCEED_FEE_AMOUNT")
    private Double succeedFeeAmount = 0D;		//成功费用 
    @Column(name = "REFUSED_FEE_RATIO")
    private Double refusedFeeRatio = 0D;		//拒收费率 
    @Column(name = "REFUSED_FEE_AMOUNT")
    private Double refusedFeeAmount = 0D;		//拒收费用 
    //////金额、重量都用到的字段/////
    
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public Double getAmountFloor() {
		return amountFloor;
	}
	public void setAmountFloor(Double amountFloor) {
		this.amountFloor = amountFloor;
	}
	public Double getAmountCeil() {
		return amountCeil;
	}
	public void setAmountCeil(Double amountCeil) {
		this.amountCeil = amountCeil;
	}
	public Double getVirtualFeeAmount() {
		return virtualFeeAmount;
	}
	public void setVirtualFeeAmount(Double virtualFeeAmount) {
		this.virtualFeeAmount = virtualFeeAmount;
	}
	public String getDestinationProvince() {
		return destinationProvince;
	}
	public void setDestinationProvince(String destinationProvince) {
		this.destinationProvince = destinationProvince;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getDestinationCounty() {
		return destinationCounty;
	}
	public void setDestinationCounty(String destinationCounty) {
		this.destinationCounty = destinationCounty;
	}
	public Double getWeightFloor() {
		return weightFloor;
	}
	public void setWeightFloor(Double weightFloor) {
		this.weightFloor = weightFloor;
	}
	public Double getWeightCeil() {
		return weightCeil;
	}
	public void setWeightCeil(Double weightCeil) {
		this.weightCeil = weightCeil;
	}
	public Double getWeightInitialFee() {
		return weightInitialFee;
	}
	public void setWeightInitialFee(Double weightInitialFee) {
		this.weightInitialFee = weightInitialFee;
	}
	public Double getWeightIncrease() {
		return weightIncrease;
	}
	public void setWeightIncrease(Double weightIncrease) {
		this.weightIncrease = weightIncrease;
	}
	public Double getWeightIncreaseFee() {
		return weightIncreaseFee;
	}
	public void setWeightIncreaseFee(Double weightIncreaseFee) {
		this.weightIncreaseFee = weightIncreaseFee;
	}
	public Double getSpecialFee() {
		return specialFee;
	}
	public void setSpecialFee(Double specialFee) {
		this.specialFee = specialFee;
	}
	public Double getSideLength() {
		return sideLength;
	}
	public void setSideLength(Double sideLength) {
		this.sideLength = sideLength;
	}
	public Double getVolumeFeeAmount() {
		return volumeFeeAmount;
	}
	public void setVolumeFeeAmount(Double volumeFeeAmount) {
		this.volumeFeeAmount = volumeFeeAmount;
	}
	public Double getSucceedFeeRatio() {
		return succeedFeeRatio;
	}
	public void setSucceedFeeRatio(Double succeedFeeRatio) {
		this.succeedFeeRatio = succeedFeeRatio;
	}
	public Double getSucceedFeeAmount() {
		return succeedFeeAmount;
	}
	public void setSucceedFeeAmount(Double succeedFeeAmount) {
		this.succeedFeeAmount = succeedFeeAmount;
	}
	public Double getRefusedFeeRatio() {
		return refusedFeeRatio;
	}
	public void setRefusedFeeRatio(Double refusedFeeRatio) {
		this.refusedFeeRatio = refusedFeeRatio;
	}
	public Double getRefusedFeeAmount() {
		return refusedFeeAmount;
	}
	public void setRefusedFeeAmount(Double refusedFeeAmount) {
		this.refusedFeeAmount = refusedFeeAmount;
	}
	public String getRefusedIncludePostfee() {
		return refusedIncludePostfee;
	}
	public void setRefusedIncludePostfee(String refusedIncludePostfee) {
		this.refusedIncludePostfee = refusedIncludePostfee;
	}
	public String getRoundType() {
		return roundType;
	}
	public void setRoundType(String roundType) {
		this.roundType = roundType;
	}
	public Double getWeightInitial() {
		return weightInitial;
	}
	public void setWeightInitial(Double weightInitial) {
		this.weightInitial = weightInitial;
	}

}
