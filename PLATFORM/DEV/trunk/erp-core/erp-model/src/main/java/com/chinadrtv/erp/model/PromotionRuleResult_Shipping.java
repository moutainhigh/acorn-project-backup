/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.io.Serializable;

/**
 * Shipping at free/discount
 * 
 * @author richard.mao
 *
 */

@Entity
@DiscriminatorValue("shipping")
public class PromotionRuleResult_Shipping extends PromotionRuleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1519461678990196951L;
	
	private Float discount;
	private Boolean isPercentage;
	
	public PromotionRuleResult_Shipping(){
		
	}
	
	/**
	 * 
	 * @param resultDescription
	 * @param discount
	 * @param percented if the discount is percentage discount
	 */
	public PromotionRuleResult_Shipping(String resultDescription, float discount, boolean percented){
		super(resultDescription);
		this.discount=discount;
		this.isPercentage=percented;
	}
	
	@Column(name = "DISCOUNT")
	public Float getDiscount() {
		return discount;
	}


	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	/**
	 * if the discount is percentage discount
	 * @return
	 */
	@Column(name = "IS_PERCENTAGE")
	public Boolean getIsPercentage() {
		return isPercentage;
	}


	/**
	 *  if the discount is percentage discount
	 * @param isPercentage
	 */
	public void setPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	
	public String toString(){
		StringBuffer buff=new StringBuffer();
		buff.append(super.toString());
		buff.append(",productname:").append(discount);
		buff.append(",unitNumber:").append(isPercentage);
		return buff.toString();
	}

    public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	
}
