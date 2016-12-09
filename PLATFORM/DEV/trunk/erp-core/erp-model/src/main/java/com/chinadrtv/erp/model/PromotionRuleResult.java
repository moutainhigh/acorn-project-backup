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


import com.chinadrtv.erp.exception.PromotionException;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author richard.mao
 *
 */
@Entity
@Table(name = "PROMOTION_RULE_RESULT", schema = "ACOAPP_OMS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",
        discriminatorType=DiscriminatorType.STRING)
public class PromotionRuleResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8038876382809595212L;
	
	private Long id;
	private Promotion promotion;
	private Long preTradeId;

	private String resultDescription;
	
	private Date dateAdded;
	private String createdBy;

    private Long orderId;
    
    private PromotionRuleResult parentResult;
    
    private List<PromotionRuleResult> childResult;
    

    public boolean canConsume() {
    	return true;
    }
	
	public PromotionRuleResult(){
		
	}
	
	/**
	 * 
	 * @param resultDescription the result description
	 */
	public PromotionRuleResult(String resultDescription){
		this.resultDescription=resultDescription;
	}
	
	/**
	 * filter the promotion rule result list
	 * @param sourceList
	 * @param fullClassName the full class name to filter. It should be child class name of PromotionRuleResult
	 * @return
	 * @throws PromotionException
	 */
	public static List<PromotionRuleResult> filterByClass(List<PromotionRuleResult> sourceList, String fullClassName) throws PromotionException{
		if(sourceList==null) return null;
		List<PromotionRuleResult> resultList=new ArrayList<PromotionRuleResult>();
		
		Class<?> thisClass=null;
		try{
			thisClass=Class.forName(fullClassName);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new PromotionException(e.getMessage());
		}
		
		for(PromotionRuleResult source:sourceList){
			//check if source object is instance of specified class
			if(thisClass.isInstance(source)) resultList.add(source);
		}
		return resultList;
	}
	
	
	// Property accessors
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Promotion_Result_SEQ")
    @SequenceGenerator(name = "Promotion_Result_SEQ", sequenceName = "ACOAPP_OMS.Promotion_Result_SEQ")
    @Column(name = "PROMOTION_RESULT_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long promotionResultId) {
		this.id = promotionResultId;
	}
	
    @Column(name = "pre_trade_id")
    public Long getPreTradeId() {
        return this.preTradeId;
    }

    public void setPreTradeId(Long preTradeId) {
        this.preTradeId = preTradeId;
    }

    @ManyToOne()
    @JoinColumn(name = "promotion_id", referencedColumnName = "PROMOTION_ID")
    public Promotion getPromotion() {
        return this.promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion=promotion;
    }
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_ADDED", length = 7)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	@Column(name = "CREATED_BY", length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	@Column(name = "RESULT_DESCRIPTION", length = 512)
	public String getResultDescription() {
		return this.resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)  
	@JoinColumn(name = "pid")  
	public PromotionRuleResult getParentResult() {
		return parentResult;
	}

	public void setParentResult(PromotionRuleResult parentResult) {
		this.parentResult = parentResult;
	}


    @OneToMany(targetEntity = PromotionRuleResult.class, cascade = { CascadeType.ALL }, mappedBy = "parentResult")  
    @Fetch(FetchMode.SUBSELECT)  
	public List<PromotionRuleResult> getChildResult() {
		return childResult;
	}


	public void setChildResult(List<PromotionRuleResult> childResult) {
		this.childResult = childResult;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PromotionRuleResult [id=");
		builder.append(id);
		builder.append(", promotion=");
		builder.append(promotion);
		builder.append(", preTradeId=");
		builder.append(preTradeId);
		builder.append(", resultDescription=");
		builder.append(resultDescription);
		builder.append(", dateAdded=");
		builder.append(dateAdded);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append("]");
		return builder.toString();
	}


    @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (promotion != null) {
			result = prime * result + ((promotion.getPromotionid() == null) ? 0 : promotion.getPromotionid().hashCode());
		} else {
			result = prime * result + ((resultDescription == null) ? 0 : resultDescription.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromotionRuleResult other = (PromotionRuleResult) obj;
		if (promotion != null) {
			if (promotion.getPromotionid() == null) {
				if (other.promotion.getPromotionid() != null)
					return false;
			} else if (!promotion.getPromotionid().equals(other.promotion.getPromotionid()))
				return false;
		} else {
			if (resultDescription == null) {
				if (other.resultDescription != null)
					return false;
			} else if (!resultDescription.equals(other.resultDescription))
				return false;
		}
		return true;
	}

}
