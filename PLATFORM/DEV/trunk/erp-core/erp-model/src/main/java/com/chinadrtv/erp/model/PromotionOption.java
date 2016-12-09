package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * PromotionOption entity.
 */
@Entity
@Table(name = "PROMOTION_OPTION", schema = "ACOAPP_OMS")
public class PromotionOption implements java.io.Serializable {

	// Fields

	private Long optionId;
	private Promotion promotion;
	private String optionKey;
	private String optionValue;

	// Constructors

	/** default constructor */
	public PromotionOption() {
	}
	
	public PromotionOption(Long optionId, String optionKey, String optionValue){
		this.optionId=optionId;
		this.optionKey=optionKey;
		this.optionValue=optionValue;
	}

	/** minimal constructor */
	public PromotionOption(Long ruleOptionId) {
		this.optionId = ruleOptionId;
	}

	// Property accessors
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PromotionOption_SEQ")
    @SequenceGenerator(name = "PromotionOption_SEQ", sequenceName = "ACOAPP_OMS.PromotionOption_SEQ")
    @Column(name = "OPTION_ID")
	public Long getOptionId() {
		return this.optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROMOTION_ID")
	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	@Column(name = "OPTION_KEY", length = 100)
	public String getOptionKey() {
		return this.optionKey;
	}

	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	@Column(name = "OPTION_VALUE", length = 255)
	public String getOptionValue() {
		return this.optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	public String toString(){
		StringBuffer buff=new StringBuffer();
		buff.append("optionId:").append(optionId);
		buff.append(",optionKey:").append(optionKey);
		buff.append(",optionKey:").append(optionValue);
		return buff.toString();
	}

}