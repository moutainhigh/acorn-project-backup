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

import javax.persistence.*;

/**
 * the reward point granted
 * 
 * @author richard.mao
 * 
 */
@Entity
@DiscriminatorValue("rewardpoint")
public class PromotionRuleResult_RewardPoint extends PromotionRuleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3149538327913958237L;

	private Integer rewardPoint;


    private Boolean isExtraPoint = false;

    @Transient
    public Boolean getIsExtraPoint() {
        return isExtraPoint;
    }

    public void setIsExtraPoint(Boolean extraPoint) {
        isExtraPoint = extraPoint;
    }

	public PromotionRuleResult_RewardPoint(String resultDescription, int rewardPoint) {
		super(resultDescription);
		this.rewardPoint = rewardPoint;
	}

	public PromotionRuleResult_RewardPoint() {

	}

	@Column(name = "REWARD_POINT")
	public Integer getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(Integer rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(super.toString());
		buff.append(",rewardPoint:").append(rewardPoint);
		return buff.toString();
	}

}
