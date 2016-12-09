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
package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleResult;

import java.util.List;

/**
 * 
 * @author richard.mao
 *
 */
public interface PromotionRuleAdminService {
	
	public void savePromotionRule(PromotionRule rule);
	public void deletePromotionRuleById(Long promotionRuleId);
	/**
	 * get all promotion rules
	 * @param installedOnly flag if return only installed rules
	 * @return
	 */
	public List<PromotionRule> getAllPromotionRules(Boolean installedOnly);
	public PromotionRule findById(Long promotionRuleId);
	/**
	 * persistent promotion
	 * @param promition
	 * @return
	 */
	public PromotionRuleResult save(PromotionRuleResult promotion);
	
	/**
	 * install/uninstall the rule. If uninstall. the dependent promotions will set to inactive automatically
	 * @param promotionRuleId
	 * @param installed
	 * @throws PromotionException
	 */
	public void setInstalled(Long promotionRuleId, Boolean installed) throws PromotionException;
	/**
	 * generate default UI from rule/rule options
	 * @param rule
	 * @return the MVEL based HTML template
	 */
	public String generateDefaultUIFromRuleDef(PromotionRule rule) throws PromotionException;
	
	/**
	 * save promotion UI text to file. The UI text should be MVEL template format. The file will be saved in file:
	 * $RULE_DIR/$ruleName.ui.mvel
	 * @param rule
	 * @param ruleUIText
	 */
	public void savePromotionRuleUIFile(PromotionRule rule, String ruleUIText) throws PromotionException;
	
	/**
	 * save promotion rule text to file. The UI text should be MVEL format. The file will be saved in file:
	 * $RULE_DIR/$ruleName.mvel
	 * @param rule
	 * @param ruleText
	 */
	public void savePromotionRuleFile(PromotionRule rule, String ruleText) throws PromotionException; 
	
	
	/**
	 * read the promotion UI definition file. it should be in MVEL template format.
	 * @param rule
	 * @return
	 * @throws PromotionException
	 */
	public String getPromotionRuleUIText(PromotionRule rule) throws PromotionException;
	
	/**
	 * read the promotion rule definition file. it should be in MVEL format
	 * @param rule
	 * @return
	 * @throws PromotionException
	 */
	public String getPromotionRuleText(PromotionRule rule) throws PromotionException;

    public int getPromotionRulesCount();;
    public List<PromotionRule> searchPaginatedPromotionRulesList(int start_index, Integer num_per_page);

    public void deletePromotionRuleByIds(String promotionRuleIDs);
}
