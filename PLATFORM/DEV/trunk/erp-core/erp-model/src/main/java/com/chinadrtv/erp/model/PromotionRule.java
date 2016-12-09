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

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @author richard.mao
 *
 */
@Entity
@Table(name = "PROMOTIONRULE", schema = "ACOAPP_OMS")
public class PromotionRule  implements java.io.Serializable{
	private Long ruleId;
	private String ruleName;
	private String ruleFileName;
	private String ruleUIFileName;
	private Boolean installed=Boolean.FALSE;
	private String ruleContext;
	private Set<PromotionRuleOption> ruleOptions;
	
	private Date dateAdded;
	private Date lastModified;
	private String updateBy;
	private String createdBy;
    //promotion rule text
    private String ruleText;
    
    private String ruleBtext;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROMOTION_RULE_SEQ")
    @SequenceGenerator(name = "PROMOTION_RULE_SEQ", sequenceName = "ACOAPP_OMS.PROMOTION_RULE_SEQ")
    @Column(name = "RULE_ID")
	public Long getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	@Column(name = "RULE_NAME", length = 512, unique=true)
	public String getRuleName() {
		return this.ruleName;
	}
	
	public void setRuleName(String ruleName) {
		this.ruleName=ruleName;
	}
	
	
	/**
	 * the rule file name. Note: the file name should be relative name(under /rules/ folder). The rule file name should end with .mvel
	 * @return
	 */
	@Column(name = "RULE_FILE_NAME", length = 512)
	public String getRuleFileName() {
		return this.ruleFileName;
	}
	
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName=ruleFileName;
	}

	/**
	 * the rule file UI file name. Note: the file name should be relative name(under /rules/ folder). The rule file name should end with .mvel
	 * @return
	 */
	@Column(name = "RULE_UI_FILE_NAME", length = 512)
	public String getRuleUIFileName() {
		return this.ruleUIFileName;
	}
	
	public void setRuleUIFileName(String ruleUIFileName) {
		this.ruleUIFileName=ruleUIFileName;
	}
	
	
	@Column(name = "RULE_INSTALLED")
	public Boolean isInstalled() {
		return this.installed;
	}
	
	public void setInstalled(Boolean installed) {
		this.installed=installed;
	}
	
	/**
	 * the rule context contain the ',' seperated context variable name(s). The context must be set before the MVEL rule is executed by caller.
	 * Currently the context variable could be 'user', 'coupon','resultPromotions'
	 * @return
	 */
	@Column(name = "RULE_CONTEXT", length=512)
	public String getRuleContext() {
		return this.ruleContext;
	}
	
	/**
	 * the rule context contain the ',' seperated context variable name(s). The context must be set before the MVEL rule is executed by caller.
	 * Currently the context variable could be 'user', 'coupon','resultPromotions'
	 * @param ruleContext
	 */
	public void setRuleContext(String ruleContext) {
		this.ruleContext=ruleContext;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "promotionRule")
    @OrderBy("sortId")
    public Set<PromotionRuleOption> getRuleOptions() {
        return this.ruleOptions;
    }

    public void setRuleOptions(Set<PromotionRuleOption> ruleOptions) {
        this.ruleOptions = ruleOptions;
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

    @Column(name = "RULE_TEXT")
    public String getRuleText() {
        return ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }
    
    @Column(name="RULE_BTEXT",columnDefinition="CLOB")
    @Lob
	public String getRuleBtext() {
		return ruleBtext;
	}

	public void setRuleBtext(String ruleBtext) {
		this.ruleBtext = ruleBtext;
	}

	@Override
	public int hashCode(){
		return this.ruleId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null ||!(obj instanceof PromotionRule)) return false;
		return this.ruleId.equals(((PromotionRule)obj).getRuleId());
	}
	
	
	
	@Override
	public String toString(){
		StringBuffer resultBuff=new StringBuffer();
		resultBuff.append("id:").append(ruleId);
		resultBuff.append(",name:").append(ruleName);
		resultBuff.append(",uiFileName:").append(ruleUIFileName);
		resultBuff.append(",ruleContext:[").append(ruleContext);
		resultBuff.append("],ruleOptions:").append(ruleOptions);
		resultBuff.append(",installed:").append(installed);
		resultBuff.append(",createdBy:").append(createdBy);
		resultBuff.append(",dateAdded:").append(dateAdded);
		resultBuff.append(",updateBy:").append(updateBy);
		resultBuff.append(",lastModified:").append(lastModified);
		
		return resultBuff.toString();
	}
}
