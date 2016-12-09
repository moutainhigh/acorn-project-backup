/**
 * 
 */
package com.chinadrtv.erp.admin;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.chinadrtv.erp.admin.service.PlubasInfoService;
import com.chinadrtv.erp.admin.service.PreTradeDetailService;
import com.chinadrtv.erp.admin.service.PreTradeService;
import com.chinadrtv.erp.core.service.PromotionAdminService;
import com.chinadrtv.erp.core.service.PromotionEngineService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionRuleResult;
import com.chinadrtv.erp.model.PromotionRuleResult_FreeProduct;
import com.chinadrtv.erp.model.PromotionRuleResult_MatchProduct;
import com.chinadrtv.erp.test.SpringTest;

/**
 *  
 * @author haoleitao
 * @date 2013-3-8 下午3:08:46
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@TransactionConfiguration(defaultRollback = false)
public class PromotionTest extends SpringTest {
	  @Autowired
	  private PromotionAdminService promotionAdminService;
	  
	  @Autowired
	  private PromotionEngineService promotionEngineService;
	  
	  @Autowired
	  private PreTradeService preTradeService;
	  
	  @Autowired
	  private PreTradeDetailService preTradeDetailService;
	  
	  @Autowired
	  private PlubasInfoService plubasInfoService;
	  
	  /**
	   * 测试 promotionAdminService
	   */
	
	  @Test
	  public void getPromotionById(){
		  try {
			Promotion  p = promotionAdminService.getPromotionById(Long.valueOf("8050"));
			System.out.println("PPP:"+p.getName());
			assertTrue(p.getName() != null);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PromotionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  
	  @Test
	  public void savePreTradeDetail(){
		   PreTradeDetail preTradeDetail = new PreTradeDetail();
		   preTradeDetail.setPayment(12.0);
		   preTradeDetail.setQty(12);
		   preTradeDetail.setPrice(12.00);
		   preTradeDetailService.savePreTradeDetail(preTradeDetail);
		   
		   assertTrue("123".equals("123"));
	  }
	  
	  /**
	   * 赠品
	   * @author haoleitao
	   * @date 2013-3-8 下午4:03:26
	   */
	  @Test
	  public void gifts(){
		  try {
			  //1.获取订单
			  PreTrade preTrade = preTradeService.getById(Long.valueOf("246503"));
			  Set set = new HashSet();
			  //2.执行活动规则
			  System.out.println(preTrade.getId());
			  System.out.println(preTrade.getOutCrdt());
			  //3.验证活动结果
			   Map<String, Object> context = new HashMap<String, Object>();
			 // context.clear();
			   //添加订单金额
		        context.put("SUM_AMOUNT", preTrade.getPayment());
		        /**
		         * 获取促销规则: 
		         * context:环境变量,
		         * preTrade.getSourceId().toString():渠道号
		         * preTrade.getOutCrdt() :订购日期
		         * 
		         * 
		         */
		        List<PromotionRuleResult> promotionRuleResults = promotionEngineService.getPromotionResult(preTrade,context, 0);
		        System.out.println("size:"+promotionRuleResults);
		        promotionRuleResults = promotionEngineService.savePromotionResultt(promotionRuleResults);
		        for (PromotionRuleResult promotionRuleResult : promotionRuleResults) {
		            if (promotionRuleResult instanceof PromotionRuleResult_FreeProduct) {
		                PromotionRuleResult_FreeProduct freeProduct = (PromotionRuleResult_FreeProduct) promotionRuleResult;
		                if (freeProduct.getSkuCode() != null) {
		                    String skucodes[] = freeProduct.getSkuCode().split(",");

		                    for (String skuCode : skucodes) {
			                    if(!skuCode.equals("")){
		                        PreTradeDetail preTradeDetail = new PreTradeDetail();
		                        preTradeDetail.setPreTrade(preTrade);
		                        preTradeDetail.setQty(freeProduct.getUnitNumber());
		                        preTradeDetail.setPrice(0d);
		                        preTradeDetail.setUpPrice(0d);
		                        preTradeDetail.setTradeId(preTrade.getTradeId());
		                        preTradeDetail.setPromotionResultId(freeProduct.getId());
		                        preTradeDetail.setIsVaid(true);
		                  
//		              try{
//		              Long skuId = Long.valueOf(freeProduct.getSkuCode()) ;
//		              preTradeDetail.setSkuId(skuId);
//		              }catch (Exception e) {
//		                 //logger.severe("skuId error:" + freeProduct.getSkuCode());
//		                  preTradeDetail.setIsVaid(false);
//		              }
		                        preTradeDetail.setOutSkuId(skuCode);
		                        preTradeDetailService.addPreTradeDetail(preTradeDetail);
		                       // preTrade.getPreTradeDetails().add(preTradeDetail);
		                    }
		                    }
		                }
		            }

		        }
		        //preTradeService.
		      //  preTradeService.updateSkuTitle(preTrade);
			
				  assertTrue(preTrade.getId() == 246503 );
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  catch (PromotionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }


}
