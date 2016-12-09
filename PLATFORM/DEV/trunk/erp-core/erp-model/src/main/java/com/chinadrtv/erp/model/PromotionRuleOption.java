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



import com.chinadrtv.erp.constant.PromotionConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author richard.mao
 *
 */
@Entity
@Table(name = "PROMOTION_RULE_OPTION", schema = "ACOAPP_OMS")
public class PromotionRuleOption implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9216040868589374748L;
	private Long specId;
	private PromotionRule promotionRule;
	private String key;
	private String name;
	private String description;
	private String type;
	private Integer typeSize;
	/**
	 * the default value
	 */
	private String defaultValue;
	
	/**if this key is required
	 * 
	 */
	private Boolean required=Boolean.FALSE;
	/**
	 * for option type only. all the options are set here
	 */
	private String options;
	
	private Date dateAdded;
    private Date lastModified;
    private String updateBy;
    private String createdBy;
    private String i18nKey;
    private Double maxValue;
    private Double minValue;
    private Long sortId;
    
    public PromotionRuleOption(){
    	
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_rule_option_seq")
    @SequenceGenerator(name = "promotion_rule_option_seq", sequenceName = "ACOAPP_OMS.promotion_rule_option_seq")
    @Column(name = "RULE_OPTION_ID")
    public Long getSpecId() {
        return this.specId;
    }

    public void setSpecId(Long specId) {
        this.specId=specId;
    }
    
    @ManyToOne()
    @JoinColumn(name = "RULE_ID", referencedColumnName = "RULE_ID", nullable = false)
    public PromotionRule getPromotionRule() {
        return this.promotionRule;
    }

    public void setPromotionRule(PromotionRule promotionRule) {
        this.promotionRule = promotionRule;
    }
    
    @Column(name = "RULE_OPTION_KEY", length = 255)
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    @Column(name = "RULE_OPTION_NAME", length = 128)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "RULE_OPTION_DESCRIPTION", length = 512)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name = "RULE_OPTION_TYPE", length = 255)
    public String getType() {
        return this.type;
    }
    
    @Column(name = "RULE_OPTION_TYPE_SIZE",columnDefinition="NUMBER(10) default 30 ")
    public Integer getTypeSize() {
		return typeSize;
	}

	public void setTypeSize(Integer typeSize) {
		this.typeSize = typeSize;
	}

	public void setType(String type) {
    	if(type==null) throw new IllegalArgumentException("rule option type should be set");
    	
    	
    	
    	if(!type.equalsIgnoreCase("string") &&
    			!type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_DATE) &&
                !type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_DATE_TIME) &&
    			!type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_INT) &&
    			!type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_FLOAT) &&
                !type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_BOOLEAN) &&
                !type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_SELECT) &&
                !type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_COMBOGRID)&&
                !type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_TEXTAREA)
    			)
    		throw new IllegalArgumentException("the rule option type is not supported");
        this.type=type;
    }
    
    @Column(name = "RULE_OPTION_DEFAULT", length = 255)
    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue=defaultValue;
    }
    
    /**
     * the options should be encoded as JSON Array
     * @return
     */
    @Column(name = "RULE_OPTION_OPTIONS", length = 255)
    public String getOptions() {
        return this.options;
    }
    
    /**
     * the options should be encoded as JSON array
     * @param options
     */
    public void setOptions(String options) {
        this.options=options;
    }
    
    @Column(name = "UPDATE_BY", length = 100)
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "CREATED_BY", length = 100)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_ADDED", length = 7)
    public Date getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED", length = 7)
    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    
    @Column(name = "IS_REQUIRED")
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required=required;
	}
	
	@Override
	public String toString(){
		StringBuffer buff=new StringBuffer();
		buff.append("specId:").append(specId);
		buff.append(",key:").append(key);
		buff.append(",name:").append(name);
		buff.append(",defaultValue:").append(defaultValue);
		buff.append(",type:").append(type);
		buff.append(",required:").append(required);
		buff.append(",ruleId:").append(promotionRule.getRuleId());
		buff.append(",createdBy:").append(createdBy);
		buff.append(",dateAdded:").append(dateAdded);
		buff.append(",updateBy:").append(updateBy);
		buff.append(",lastModified:").append(lastModified);
		
		return buff.toString();
	}

    @Column(name = "I18N_KEY", length = 100)
    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    @Column(name = "MAX_VALUE", precision = 15, scale = 4)
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Column(name = "MIN_VALUE", precision = 15, scale = 4)
    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @Column(name = "SORT_ID")
    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
