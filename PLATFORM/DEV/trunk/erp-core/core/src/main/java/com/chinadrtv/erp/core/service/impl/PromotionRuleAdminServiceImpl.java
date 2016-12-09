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
package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.dao.PromotionResultDao;
import com.chinadrtv.erp.core.dao.PromotionRuleDao;
import com.chinadrtv.erp.core.service.PromotionAdminService;
import com.chinadrtv.erp.core.service.PromotionRuleAdminService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 
 * @author richard.mao
 *
 */
@Service("promotionRuleAdminService")
public class PromotionRuleAdminServiceImpl implements PromotionRuleAdminService {

	@Autowired
	private PromotionRuleDao ruleDao;
	
	@Autowired
	private PromotionAdminService promotionAdminService;
	
	@Autowired
	private PromotionResultDao resultDao;
	
	public void savePromotionRule(PromotionRule rule)  {
		ruleDao.saveRule(rule);
	}

	public void deletePromotionRuleById(Long promotionRuleId)  {
		ruleDao.deleteRule(promotionRuleId);

	}


	public List<PromotionRule> getAllPromotionRules(Boolean installedOnly)  {
		return ruleDao.findAllRules(installedOnly);
	}

	public PromotionRule findById(Long promotionRuleId)  {
		return ruleDao.findByRuleId(promotionRuleId);
	}

	public void setInstalled(Long promotionRuleId, Boolean installed) throws PromotionException {
		ruleDao.setInstalled(promotionRuleId, installed);

		if(!installed){
			promotionAdminService.ruleInstalled(promotionRuleId, installed);
		}
	}

	public String generateDefaultUIFromRuleDef(PromotionRule rule) throws PromotionException{
		StringBuffer buff=new StringBuffer();
		buff.append("<!--default MVEL based UI generated from rule option specification-->\n");
		buff.append("<!--the UI could reference the rule option dictionary object. e.g. rule.options.<option Key>.name-->\n");
		buff.append("<!--Note: all the rule option dictionary object has been put in the MVEL context-->\n");
		buff.append("");
		
		//TODO
		
		return null;
	}

	public void savePromotionRuleUIFile(PromotionRule rule, String ruleUIText)
			throws PromotionException {
		if(rule==null||ruleUIText==null||ruleUIText.isEmpty()) 
			throw new PromotionException("rule and/or rule UI text is null or empty");
		
		rule.setRuleUIFileName(rule.getRuleName()+".ui.mvel");
		File ruleUIFile=new File(System.getProperty("RULE_DIR"), rule.getRuleUIFileName());
		try {
			FileUtils.writeStringToFile(ruleUIFile, ruleUIText, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new PromotionException("cannot save the rule UI text to file:"+ruleUIFile+",rule:"+rule.getRuleName());
		}
		
	}

	public void savePromotionRuleFile(PromotionRule rule, String ruleText)
			throws PromotionException {
		if(rule==null||ruleText==null||ruleText.isEmpty()) 
			throw new PromotionException("rule and/or rule text is null or empty");
		
		rule.setRuleFileName(rule.getRuleName()+".mvel");
		File ruleFile=new File(System.getProperty("RULE_DIR"), rule.getRuleFileName());
		try {
			FileUtils.writeStringToFile(ruleFile, ruleText, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new PromotionException("cannot save the rule text to file:"+ruleFile+",rule:"+rule.getRuleName());
		}
		
	}

	public String getPromotionRuleUIText(PromotionRule rule)
			throws PromotionException {
		if(rule==null) throw new PromotionException("rule is null");
		File ruleUIFile=new File(System.getProperty("RULE_DIR"), rule.getRuleUIFileName());
		try {
			return FileUtils.readFileToString(ruleUIFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new PromotionException("cannot read the rule UI text from file:"+ruleUIFile+",rule:"+rule.getRuleName());
		}
	}

	public String getPromotionRuleText(PromotionRule rule)
			throws PromotionException {
		if(rule==null) throw new PromotionException("rule is null");
		File ruleFile=new File(System.getProperty("RULE_DIR"), rule.getRuleFileName());
		try {
			return FileUtils.readFileToString(ruleFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new PromotionException("cannot read the rule text from file:"+ruleFile+",rule:"+rule.getRuleName());
		}
	}

    public int getPromotionRulesCount() {
        return ruleDao.getPromotionRulesCount();
    }

    public List<PromotionRule> searchPaginatedPromotionRulesList(int start_index, Integer num_per_page) {
        return ruleDao.getPaginatedPromotionRulesList(start_index,num_per_page);
    }

    public void deletePromotionRuleByIds(String promotionRuleIDs) {
        ruleDao.deletePromotionRuleByIds(promotionRuleIDs);
    }

	public PromotionRuleResult save(PromotionRuleResult promotion) {
		return resultDao.save(promotion);
	}

}
