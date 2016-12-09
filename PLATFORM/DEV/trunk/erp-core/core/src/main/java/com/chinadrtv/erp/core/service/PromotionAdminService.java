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
import com.chinadrtv.erp.model.Promotion;

import java.util.List;

/**
 * 
 * @author richard.mao
 *
 */
public interface PromotionAdminService {
	public Promotion getPromotionById(Long promotionId) throws PromotionException;
	public void deletePromotion(Long promotionId) throws PromotionException;
	public void setActive(Long promotionId, Boolean active) throws PromotionException;
	/**
	 * change notification from promotion rule install/un-install. Normally only un-install was handled.
	 * @param ruleId
	 * @param installed
	 * @throws PromotionException
	 */
	public void ruleInstalled(Long ruleId, Boolean installed) throws PromotionException;
	public Promotion savePromotion(Promotion promotion) throws PromotionException;


        /**
       * generate the UI HTML from the UI MVEL file
       * @param promotion
       * @return
       * @throws PromotionException
       */
	public String generateUI(Promotion promotion) throws PromotionException;

    public int getPromotionsCount();
    public List<Promotion> searchPaginatedPromotionsList(int start_index, Integer num_per_page);
    void deletePromotionByIds(String promotionIDs);
    /**
     * inc or dec the exec order
     * @param promotion
     * @param direction 1 inc the execOrder, the execOrder will be later, -1 ,the order will be earlier
     * @return
     * @throws
     */
    void updateExecOrder(Promotion promotion, int direction);
    int getPromotionsCountByNameActive(int start_index, Integer num_per_page, String promotionName, int active);
    List<Promotion> getPaginatedPromotionsListByNameActive(int start_index, Integer num_per_page, String promotionName, int active);

    void resetExecOrder();

    void usePromotion(Promotion promotion) throws PromotionException;
    
    int updatePromotinActive(Long promotionId,Boolean active );
}
