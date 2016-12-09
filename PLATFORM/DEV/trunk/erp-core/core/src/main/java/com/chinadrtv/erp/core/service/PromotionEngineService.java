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
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionRuleResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * the promotion engine service. Use this service to get available promotions per user; 
 * you could run the promotion rule sequentially and get the result.
 * 
 * method implementation notes: 
 * 1) getAvailablePromotions: this method should iterate all the valid promotions and return the promotions in order of execOrder. <br/>
 * 	The validation rules:
 * 	1.1) the promotion should be set as 'active'
 *  1.2) the promotion should be within the valid time scope(start time, end time)
 *  1.3) check the products inside user basket. For each product, check if the product is included/excluded by the promotion
 *  1.4) check user's level/group & validate against the promotion's user inclusive/exclusive definition
 *  1.5) check if the promotion is cumulative(i.e. multiple promotions with the same type(i.e. rule id) cannot co-exist). 
 *  	If yes, only the first promotion of the type will be returned
 *  1.6) rare case-check if promotion has max # of usage settings. If yes, check user's order history to see the # of usage.
 *  
 *  2)getPromotionResult(): the promotion rules returned by getAvailablePromotions() are evaluated sequentially.<br/>
 *  	Each promotion is evaluated under the input context & promotion option context & result Promotion context( with name 'resultPromotions') 
 *  	Note: the original input context objects are read-only, but each promotion's evaluation result are put in the original context as well.
 *  		The design reason is the 2nd promotion might operate on the result of the 1st(e.g. free product promotion after order total discount promotion).
 */
/**
 * 
 * @author richard.mao
 *
 */
public interface PromotionEngineService {
	
	/**
	 * the context key used in MVEL context. The promotion evaluation result(s) is/are put in the MVEL context under this name.
	 */
	public static final String RESULT_PROMOTION_CONTEXT_KEY="resultPromotions";
	
	/**
	 * get the available promotions for the given user. Note: the result promotion(s) should be ordered by sortOrder 
	 * so that the promotion will be executed sequentially per the sortOrder
	 * @param preTrade
     * @param clacRound
	 * @return
	 */
	public List<Promotion> getAvailablePromotions(PreTrade preTrade, Integer clacRound) throws PromotionException;

	/**
	 * get the promotion result. this method should invoke <code>PromotionEngineService.getAvailablePromotions</code> 
	 * to get the available promotions first.
	 * 
	 * @param context the additional context except 'user' context. You could pass 'coupon' or 'product' as context
	 * @return
	 */
	public List<PromotionRuleResult> getPromotionResult(PreTrade preTrade,@SuppressWarnings("rawtypes") Map context, Integer clacRound) throws PromotionException;
	
	/**
	 * this method is used to cleanup the cached compiled rule
	 * 
	 * @param ruleId
	 */
	public void ruleChanged(Long ruleId) throws PromotionException;

    public String getRuleFilePath();

    public List<Promotion> getGlobalPromotions();

    void savePromotionResult(List<PromotionRuleResult> results);
    
    List<PromotionRuleResult> savePromotionResultt(List<PromotionRuleResult> results);
    
    

}
