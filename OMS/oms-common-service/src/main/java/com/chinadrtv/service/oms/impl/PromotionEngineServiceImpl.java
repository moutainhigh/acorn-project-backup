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

package com.chinadrtv.service.oms.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PromotionDao;
import com.chinadrtv.dal.oms.dao.PromotionRuleDao;
import com.chinadrtv.model.iagent.PlubasInfo;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.Promotion;
import com.chinadrtv.model.oms.PromotionRule;
import com.chinadrtv.model.oms.PromotionRuleResult;
import com.chinadrtv.service.oms.PromotionEngineService;

/**
 * @author richard.mao
 */
@Service("promotionEngineService")
public class PromotionEngineServiceImpl implements PromotionEngineService {

    private static final Logger log =
            Logger.getLogger(PromotionEngineServiceImpl.class.getName());
	/**
	 * the place to cache the compiled MVEL rules
	 */
	private static Map<PromotionRule, Serializable> compiledRules = new HashMap<PromotionRule, Serializable>(0);
	/*@Autowired
	private PromotionRuleDao promotionRuleDao;

	@Autowired
	private PromotionDao promotionDao;
*/
//    @Autowired
//    private PromotionResultDao promotionResultDao;
    



//    @Autowired
//    private SystemConfigure systemConfigUtil;

   /* @Autowired
    private PromotionCacheService promotionCacheService;
*/
	public String getRuleFilePath() {
		return "";//systemConfigUtil.getPromRulePath();
	}

/*    public List<Promotion> getGlobalPromotions() {
        return promotionDao.getGlobalPromotions();
    }*/


	/*public List<Promotion> getAvailablePromotions(PreTrade preTrade,Integer clacRound) {
		// process validation rule 1.1, 1.2
		
		List<Promotion> allPromotions = promotionCacheService.getAllPromotions(preTrade.getTradeType(),preTrade.getOutCrdt(),true, false, false,clacRound);
        List<Promotion> resultPromotions = new ArrayList<Promotion>(allPromotions.size());
        for (PreTradeDetail preTradeDetail : preTrade.getPreTradeDetails()) {
            for (Promotion promotion : allPromotions) {
                // the promotion has no product restriction
                if (promotion.getPromotionProdcuts() == null) {
                    resultPromotions.add(promotion);
                } else {
                    for (PlubasInfo plubasInfo : promotion.getPromotionProdcuts()) {
                        if (plubasInfo.getPlucode().equalsIgnoreCase(preTradeDetail.getOutSkuId())){
                        resultPromotions.add(promotion);
                        }
                    }
                }
            }
            // the promotions are full, break the loop
            if (resultPromotions.size() == allPromotions.size())
                break;
        }
		return allPromotions;
	}



	@SuppressWarnings("unchecked")
	public List<PromotionRuleResult> getPromotionResult(PreTrade preTrade, @SuppressWarnings("rawtypes") Map context,Integer clacRound)
			throws PromotionException {
		// get the applicable promotions for the user
		List<Promotion> promotions = this.getAvailablePromotions(preTrade, clacRound);
		List<PromotionRuleResult> results = new ArrayList<PromotionRuleResult>();

		context.put(RESULT_PROMOTION_CONTEXT_KEY, results);

		@SuppressWarnings("rawtypes")
		Map contextMap = new HashMap();
		for (Promotion promotion : promotions) {
			// set the context
			contextMap.clear();
			context.put(PromotionConstants.PROMOTION_CONTEXT_PROMOTION_REQUIRE_COUPON, promotion.getRequiresCoupon());
            context.put(PromotionConstants.PROMOTION_CONTEXT_PROMOTION_NAME, promotion.getName());
			setContext(promotion, context, contextMap);

			// put the option <key, value> pair in the context map as well
			setPromotionOptionValues(promotion, contextMap);
            try{
                List<PromotionRuleResult> resultList = evaluate( promotion, promotion.getPromotionRule(), contextMap);
                results.addAll(resultList);
            }catch (Exception e){
                log.severe(e.getMessage());
            }
		}
        promotions = null;
        return results;
	}

    public void savePromotionResult(List<PromotionRuleResult> results) {
        for (PromotionRuleResult ruleResult: results){
        	try{
        		promotionResultDao.save(ruleResult);	
        	}catch(Exception e){
        		e.printStackTrace();
        	}
            
        }
    }

    private String getFullRuleFileName(String fileName) {
		String path = getRuleFilePath();
		if (path != null && !path.endsWith(File.separator)&&path.length()>0) {
			path = path + File.separator;
            return path + fileName;
		}  else{
            return fileName;
        }

	}

    private String getRuleFromClassPath(String ruleFileName){
        String resourceName = "com/chinadrtv/b2c/service/promotion/" + ruleFileName;
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream( resourceName)));
        String s = "";
        String temp = "";
        try {
            while((temp = reader.readLine()) != null)
            {
                s = s + temp;
            }
        } catch (IOException e) {
            log.severe("Error to load rule file: com/chinadrtv/b2c/service/promotion/" + ruleFileName);
        }
        return s;
    }
	private List<PromotionRuleResult> evaluate( Promotion promotion, PromotionRule rule,
			@SuppressWarnings("rawtypes") Map ruleContext) throws PromotionException {
		Serializable compiled = compiledRules.get(rule);
		if (compiled == null) {
			synchronized (PromotionEngineServiceImpl.class) {
                String ruleText = rule.getRuleBtext();
				//System.out.println("classPath:"+this.getClass().getResource("")+File.separator+"mvel"+File.separator);
			//	String mvelPath = Thread.currentThread().getContextClassLoader().getResource("")+File.separator+"mvel"+File.separator;
				//String ruleText = this.readRuleTextFromFile("D:\\mvel\\"+rule.getRuleFileName(), "utf-8");
				//System.out.println("ruleText:"+ruleText);
				compiled = MVEL.compileExpression(ruleText);
				//System.out.println("ruleTextssssssssss:");
				compiledRules.put(rule, compiled);
			}
		}

		Date dateAdded = new Date();

		@SuppressWarnings("unchecked")
		List<PromotionRuleResult> results = (List<PromotionRuleResult>) MVEL.executeExpression(compiled, ruleContext);
		for (PromotionRuleResult result : results) {
			result.setPromotion(promotion);
			result.setDateAdded(dateAdded);
		}

		return results;
	}

	*//**
	 * read the rule text from file
	 *
	 * @param fileName
	 * @param encoding
	 * @return
	 *//*
	private String readRuleTextFromFile(String fileName, String encoding) throws PromotionException {

		File ruleFile = null;

		try {
			ruleFile = new File(fileName);
            FileInputStream stream;
            if (ruleFile.exists()){
                stream = new FileInputStream(ruleFile);
            } else{
                stream = new FileInputStream(PromotionEngineServiceImpl.class.getResource("/").getPath()+fileName);
            }

			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				 Instead of using default, pass in a decoder. 
				return Charset.forName(encoding).decode(bb).toString();
			} finally {
				stream.close();
			}
		} catch (FileNotFoundException e) {
            log.severe(e.getMessage());
			throw new PromotionException("the rule file does not exist:" + ruleFile);
		} catch (IOException e) {
            log.severe(e.getMessage());
			throw new PromotionException("reading rule file failure:" + ruleFile);
		}

	}

	@SuppressWarnings("unchecked")
	private void setContext(Promotion promotion,  @SuppressWarnings("rawtypes") Map context,
			@SuppressWarnings("rawtypes") Map resultContext) throws PromotionException {
		PromotionRule rule = promotion.getPromotionRule();
		String ruleContext = rule.getRuleContext();
		if (ruleContext == null || ruleContext.isEmpty()) {
			return;
        }

       // resultContext.put(PromotionConstants.PROMOTION_CONTEXT_PROMOTION_PRODUCT,promotion.getPromotionProdcuts());
        resultContext.put(PromotionConstants.PROMOTION_CONTEXT_PROMOTION_REQUIRE_COUPON,promotion.getRequiresCoupon());

		String[] ruleContextNames = ruleContext.split(",");
		for (String ruleContextName : ruleContextNames) {
			 if (context.containsKey(ruleContextName)){
				resultContext.put(ruleContextName, context.get(ruleContextName));
            } else {
				throw new PromotionException("the context isn't set:" + ruleContextName + " for rule:"
						+ rule.getRuleId());
            }
		}
	}

	*//**
	 * add the promotion rule option
	 *
	 * @param promotion
	 * @param resultContext
	 *//*

	private void setPromotionOptionValues(Promotion promotion, @SuppressWarnings("rawtypes") Map resultContext)
			throws PromotionException {
        Map<String, Object> optionValues = promotionCacheService.getPromotionOptionValues(promotion.getPromotionid());
        for (String key: optionValues.keySet()){
            resultContext.put(key, optionValues.get(key));
        }
	}



	*//**
	 * clear the cached compiled rule after rule has been changed.
	 *
	 * @param ruleId
	 *            the rule Id
	 *//*
	synchronized public void ruleChanged(Long ruleId) {
		PromotionRule rule = promotionRuleDao.findByRuleId(ruleId);
		compiledRules.remove(rule);
	}

	public List<PromotionRuleResult> savePromotionResultt(List<PromotionRuleResult> results) {
		List<PromotionRuleResult> list = new ArrayList();
        for (PromotionRuleResult ruleResult: results){
        	try{
        		PromotionRuleResult ruleResult2 =	promotionResultDao.save(ruleResult);
        		list.add(ruleResult2);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
            
        }
        return list;
	}
*/

}
