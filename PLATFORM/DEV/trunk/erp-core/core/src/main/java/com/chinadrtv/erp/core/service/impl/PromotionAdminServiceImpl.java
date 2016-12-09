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

import com.chinadrtv.erp.core.dao.PromotionDao;
import com.chinadrtv.erp.core.dao.PromotionOptionDao;
import com.chinadrtv.erp.core.service.PromotionAdminService;
import com.chinadrtv.erp.core.service.PromotionCacheService;
import com.chinadrtv.erp.core.service.PromotionRuleAdminService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionRuleOption;
import org.mvel2.templates.TemplateRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author richard.mao
 */
@Service("promotionAdminService")
public class PromotionAdminServiceImpl implements PromotionAdminService {

    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private PromotionOptionDao promotionOptionDao;

    @Autowired
    private PromotionRuleAdminService promotionRuleAdminService;

    @Autowired
    private PromotionCacheService promotionCacheService;


    public Promotion getPromotionById(Long promotionId) throws PromotionException {
        Promotion promotion = promotionDao.findById(promotionId);
        promotion.getPromotionOptions().size();
        //promotion.getPromotionProdcuts().size();
        return promotion;

    }

    public void deletePromotion(Long promotionId) throws PromotionException {
        promotionDao.deletePromotion(promotionId);
        invalidCacheOfPromotion();
    }


    public void setActive(Long promotionId, Boolean active)
            throws PromotionException {
        promotionDao.setActive(promotionId, active);
        invalidCacheOfPromotion();
    }

    public Promotion savePromotion(Promotion promotion)
            throws PromotionException {

        if(promotion.getPromotionid() != null){
           // Promotion oldPromotion = promotionDao.get(promotion.getPromotionid());
           // invalidProductPromotionByTag(oldPromotion);
            promotionOptionDao.deleteByPromotion(promotion.getPromotionid());
        } else{
            if (promotionDao.getMaxExecOrder() != null){
                Integer execOrder= promotionDao.getMaxExecOrder() +1;
                promotion.setExecOrder(execOrder);
            }
        }
        if (promotion.getUsedTimes() == null){
            promotion.setUsedTimes(0);
        }
        invalidCacheOfPromotion();
        promotionCacheService.invalidCacheOfGlobalPromotions();


        promotionCacheService.invalidPromotionOptionValues(promotion.getPromotionid());
        try {
            return promotionDao.savePromotion(promotion);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public String generateUI(Promotion promotion) throws PromotionException {
        if (promotion == null) return null;
        String mvelUIText = promotionRuleAdminService.getPromotionRuleUIText(promotion.getPromotionRule());

        //add the rule option in the context just in case the mvel UI references the rule option definitions
        Set<PromotionRuleOption> ruleOptions = promotion.getPromotionRule().getRuleOptions();
        Map<String, PromotionRuleOption> ruleOptionMap = new HashMap<String, PromotionRuleOption>(ruleOptions.size());
        for (PromotionRuleOption ruleOption : ruleOptions)
            ruleOptionMap.put("rule.options." + ruleOption.getKey(), ruleOption);

        //we didn't pre-compile the template. In the future we may compile the MVEL template
        return (String) TemplateRuntime.eval(mvelUIText, ruleOptionMap);
    }

    public int getPromotionsCount() {
        return promotionDao.getPromotionsCount();
    }

    public List<Promotion> searchPaginatedPromotionsList(int start_index, Integer num_per_page) {
        return promotionDao.getPaginatedPromotionsList(start_index, num_per_page);
    }

    public void deletePromotionByIds(String promotionIDs) {
        promotionDao.deletePromotionByIds(promotionIDs);
        invalidCacheOfPromotion();
    }

    public void updateExecOrder(Promotion promotion, int direction) {
        Integer execOrder = promotion.getExecOrder();
        if (execOrder == null){
            execOrder= promotionDao.getMaxExecOrder() +1;
            promotion.setExecOrder(execOrder);
        }
        if (direction == 1) {//go later
            Promotion latePromotion = promotionDao.getLatePromotion(promotion.getExecOrder());
            if (latePromotion != null) {
                promotion.setExecOrder(latePromotion.getExecOrder());
                latePromotion.setExecOrder(execOrder);
            }
        }else{
            Promotion earlyPromotion = promotionDao.getEarlyPromotion(promotion.getExecOrder());
            if (earlyPromotion != null) {
                promotion.setExecOrder(earlyPromotion.getExecOrder());
                earlyPromotion.setExecOrder(execOrder);
            }
        }
        invalidCacheOfPromotion();
    }

    public int getPromotionsCountByNameActive(int start_index, Integer num_per_page, String promotionName, int active) {
        return promotionDao.getPromotionsCountByNameActive(start_index,num_per_page,promotionName,active);
    }

    public List<Promotion> getPaginatedPromotionsListByNameActive(int start_index, Integer num_per_page, String promotionName, int active) {
        return promotionDao.getPaginatedPromotionsListByNameActive(start_index,num_per_page,promotionName,active);
    }

    public void resetExecOrder() {
        promotionDao.clearExecOrder();
        List<Promotion> pList = promotionDao.getPaginatedPromotionsListByNameActive("",1);
        for(int i = 0;i<pList.size();i++){
            Promotion promotion = pList.get(i);
            promotion.setExecOrder(i+1);
        }
    }

    public void usePromotion(Promotion promotion) throws PromotionException {
        int updated = promotionDao.usePromotion(promotion);
        if (updated < 1){
            invalidCacheOfPromotion();
            throw new PromotionException("promotion is not valid:" + promotion.getPromotionid());
        }
    }

    private void invalidCacheOfPromotion() {
        promotionCacheService.invalidCacheOfAllPromotions(true, false, false,1);
        promotionCacheService.invalidCacheOfAllPromotions(true, false, false,2);
    }

    public void ruleInstalled(Long ruleId, Boolean installed)
            throws PromotionException {
        if (!installed) promotionDao.setActiveForRule(ruleId, installed);

    }

	public int updatePromotinActive(Long promotionId, Boolean active) {
		// TODO Auto-generated method stub
		return promotionDao.updatePromotinActive(promotionId,  active);
	}


}
