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
package com.chinadrtv.erp.core.dao;

import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleOption;

import java.util.List;

/**
 * 
 * @author richard.mao
 *
 */
public interface PromotionRuleDao extends GenericDao<PromotionRule, Long> {
	public PromotionRule findByRuleId(Long ruleId);
	public PromotionRule saveRule(PromotionRule promotionRule);
	public void setInstalled(Long ruleId, Boolean installed);
	public List<PromotionRule> findAllRules(Boolean installedOnly);
	public void deleteRule(Long ruleId);
	public PromotionRuleOption findPromotionRuleOption(Long ruleId, String ruleOptionKey);
    public int getPromotionRulesCount();
    public List<PromotionRule> getPaginatedPromotionRulesList(int start_index, Integer num_per_page);

    void deletePromotionRuleByIds(String promotionRuleIDs);
}
