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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * order related promotion rule result. The user's order total will be calculated as following:
 * 1) add all basket product's order total
 * 2) get the discounted order total based on PromotionRuleResult_OrderTotal(either deduce a certain # or has a discount %
 * 
 * The order total does not including shipping fee etc.
 */

/**
 * 
 * @author richard.mao
 *
 */
@Entity
@DiscriminatorValue("OrderTotal")
public class PromotionRuleResult_OrderTotal extends PromotionRuleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5893785827869265036L;
	
	private Float discount;
	private Boolean isPercentage;
	
	public PromotionRuleResult_OrderTotal(){
		
	}
	
	public PromotionRuleResult_OrderTotal(String resultDescription, float discount, boolean percentage){
		super(resultDescription);
		this.discount=discount;
		this.isPercentage =percentage;
	}


    @Column(name = "DISCOUNT")
    public Float getDiscount(){
		return this.discount;
	}
	public void setDiscount(Float discount){
		this.discount=discount;
	}


    @Column(name = "IS_PERCENTAGE")
    public Boolean getPercentage() {
        return isPercentage;
    }

    public void setPercentage(Boolean percentage) {
        isPercentage = percentage;
    }
    
    public String toString(){
    	StringBuffer buff=new StringBuffer();
		buff.append(super.toString());
		buff.append(",discount:").append(discount);
		buff.append(",isPercentage:").append(isPercentage);
		return buff.toString();
    }


    @Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
