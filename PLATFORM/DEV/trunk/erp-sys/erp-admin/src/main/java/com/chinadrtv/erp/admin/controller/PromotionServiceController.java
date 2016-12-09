/**
 * 
 */
package com.chinadrtv.erp.admin.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.compat.log.Level;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.admin.service.PlubasInfoService;
import com.chinadrtv.erp.admin.service.PreTradeDetailService;
import com.chinadrtv.erp.admin.service.PreTradeService;
import com.chinadrtv.erp.core.service.PromotionEngineService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.model.PromotionRuleResult;
import com.chinadrtv.erp.model.PromotionRuleResult_FreeProduct;
import com.chinadrtv.erp.model.PromotionRuleResult_MatchProduct;
import com.chinadrtv.erp.admin.util.HttpUtil;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  促销服务 
 * @author haoleitao
 * @date 2013-4-22 上午10:03:21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
public class PromotionServiceController extends BaseController {
	  private static final Logger logger = Logger.getLogger(PromotionServiceController.class.getName());
	  @Autowired
	  private PreTradeDetailService preTradeDetailService;
	  @Autowired
	  private PreTradeService preTradeService;
	  @Autowired
	  private PromotionEngineService promotionEngineService;
	  @Autowired
	  private PlubasInfoService plubasInfoService;
	  
	  private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	  
	  /**
	   * 
	   * 前置订单促销接口
	   * 
	   * @param pretreadId 
	   * @param request
	   * @param response
	   * @return
	   * @throws IOException
	   */
	    @RequestMapping(value = "/PromotionService/callPrePromotion/{pretreadId}", method = RequestMethod.GET)
	    public String callPromotion(@PathVariable String pretreadId, HttpServletRequest request, HttpServletResponse response) throws IOException {
	        //促销接口调用
	    	String message="";
	        List<Map> list = new ArrayList();
			  try {
				  //1.获取订单
				  PreTrade preTrade = preTradeService.getByTradeId(pretreadId);
				  if(preTrade == null){
					  message = "订单不存在";
				  }else{
				  Set set = new HashSet();
				  //2.执行活动规则
				  System.out.println(preTrade.getId());
				  System.out.println(preTrade.getOutCrdt());
				  //3.验证活动结果
				   Map<String, Object> context = new HashMap<String, Object>();
				 // context.clear();
				   //添加订单金额
			        context.put("SUM_AMOUNT", preTrade.getPayment());
			     //  context.put("TREADID",preTrade.getTradeId());
			        /**
			         * 获取促销规则: 
			         * context:环境变量,
			         * preTrade.getSourceId().toString():渠道号
			         * preTrade.getOutCrdt() :订购日期
			         * 
			         * 
			         */
			        List<PromotionRuleResult> promotionRuleResults = promotionEngineService.getPromotionResult(preTrade,context, 0);
			        promotionRuleResults = promotionEngineService.savePromotionResultt(promotionRuleResults);
			        JSONObject jsonData = new JSONObject();

			        
			        for (PromotionRuleResult promotionRuleResult : promotionRuleResults) {
			            if (promotionRuleResult instanceof PromotionRuleResult_FreeProduct) {
			            	for (PromotionRuleResult promotionRuleResultc:promotionRuleResult.getChildResult()){
			                PromotionRuleResult_FreeProduct freeProduct = (PromotionRuleResult_FreeProduct) promotionRuleResultc;
			                if (freeProduct.getSkuCode() != null) {
			                    String skucodes[] = freeProduct.getProductId().split(",");

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
			                        Map map = new HashMap();
			                        map.put("qty", freeProduct.getUnitNumber());
			                        map.put("tradeId",preTrade.getTradeId());
			                        map.put("promotionResultId",freeProduct.getId());
			                        map.put("isVaid", true);
			                        map.put("pgroup",freeProduct.getGroup());
			                      
			              try{
			              Long skuId = Long.valueOf(freeProduct.getSkuCode()) ;
			              preTradeDetail.setSkuId(skuId);
			              map.put("skuId", skuId);
			              }catch (Exception e) {
			                 //logger.severe("skuId error:" + freeProduct.getSkuCode());
			                  preTradeDetail.setIsVaid(false);
			                  map.put("isVaid", false);
			              }
			                        preTradeDetail.setOutSkuId(skuCode);
			                        map.put("outSkuId",skuCode);
                                 System.out.println(map);
			                  
			                        list.add(map);
			                       // preTradeDetailService.addPreTradeDetail(preTradeDetail);
			                       // preTrade.getPreTradeDetails().add(preTradeDetail);
			                    }
			                    }
			                }
			            	}
			            }else if (promotionRuleResult instanceof PromotionRuleResult_MatchProduct) {
			            	for (PromotionRuleResult promotionRuleResultc:promotionRuleResult.getChildResult()){
			            	PromotionRuleResult_MatchProduct matchProduct = (PromotionRuleResult_MatchProduct) promotionRuleResultc;
			                if (matchProduct.getSkuCode() != null) {
			                    String skucodes[] = matchProduct.getProductId().split(",");

			                    for (String skuCode : skucodes) {
				                    if(!skuCode.equals("")){
				                    
			                        PreTradeDetail preTradeDetail = new PreTradeDetail();
			                        preTradeDetail.setPreTrade(preTrade);
			                        preTradeDetail.setQty(matchProduct.getUnitNumber());
			                        preTradeDetail.setPrice(0d);
			                        preTradeDetail.setUpPrice(0d);
			                        preTradeDetail.setTradeId(preTrade.getTradeId());
			                        preTradeDetail.setPromotionResultId(matchProduct.getId());
			                        preTradeDetail.setIsVaid(true);
			                        Map map = new HashMap();
			                        map.put("qty", matchProduct.getUnitNumber());
			                        map.put("tradeId",preTrade.getTradeId());
			                        map.put("promotionResultId",matchProduct.getId());
			                        map.put("isVaid", true);
			                        map.put("isPer",matchProduct.getIsPer());
			                        map.put("discount",matchProduct.getDiscount());
			                        
			                      
			              try{
			              Long skuId = Long.valueOf(matchProduct.getSkuCode()) ;
			              preTradeDetail.setSkuId(skuId);
			              map.put("skuId", skuId);
			              }catch (Exception e) {
			                 //logger.severe("skuId error:" + freeProduct.getSkuCode());
			                  preTradeDetail.setIsVaid(false);
			                  map.put("isVaid", false);
			              }
			                        preTradeDetail.setOutSkuId(skuCode);
			                        map.put("outSkuId",skuCode);
                                 System.out.println(map);
			                  
			                        list.add(map);
			                       // preTradeDetailService.addPreTradeDetail(preTradeDetail);
			                       // preTrade.getPreTradeDetails().add(preTradeDetail);
			                    }
			                    }
			                }
			            }
			            }
			        }
			        //preTradeService.
			      //  preTradeService.updateSkuTitle(preTrade);
				  }
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.info(e.getMessage());
				message="";
		        response.setContentType("text/json;charset=UTF-8");
		        response.setHeader("Cache-Control", "no-cache");
		        response.getWriter().print(message);
			} 
//			  catch (PromotionException e) {
//					logger.info(e.getMessage());
//					message="";
//			        response.setContentType("text/json;charset=UTF-8");
//			        response.setHeader("Cache-Control", "no-cache");
//			        response.getWriter().print(message);
//			}
			  System.out.println("sss:"+list);
			  message =  jsonBinder.toJson(list);
	    
	        response.setContentType("text/json;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
	        response.getWriter().print(message);
	        return null;
	    }
	    
	   /**
	    * 	    
	    * 
	    * CMS 促销接口
	    * @param prodids ,订单的产品id 
	    * @param moneys, 产品金额
	    * @param surid, 做作员
	    * @param orderid,订单id
	    * @param request
	    * @param response
	    * @return
	    * @throws IOException
	     */
	    @RequestMapping(value = "/PromotionService/callCMSPromotion", method = RequestMethod.POST)
	    public String CMSPromotion(@RequestParam(required = false, defaultValue = "") String param,
//	    		@RequestParam(required = false, defaultValue = "") String prodids,
//				@RequestParam(required = false, defaultValue = "") String moneys,
//				@RequestParam(required = false, defaultValue = "") String surid,
//				@RequestParam(required = false, defaultValue = "") String orderid,
	    		HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	//获取proids
	    	Gson gson = new GsonBuilder().create();
	    	
	    	 //prodids,订单商品Id
	         //moneys,订单商品数量*金额
	    	 //订单的操作人Id
	    	// orderId,在编辑订单时候用
	        Result r = gson.fromJson(param, Result.class);
	    	//  List list = plubasInfoService.getCmsPromotion(r.getProdids(), r.getMoneys(), r.getSurid(), r.getOrderid());
	    	//  System.out.println("list:"+list);
	    	  // HttpUtil.ajaxReturn(response, gson.toJson(list), "application/text");
	    	  
	    	   return null;
	    }
	  
	    @RequestMapping(value = "/test", method = RequestMethod.POST)
	    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	
	    	return null;
	    }
	  
	    
	    class Result{
	    	private String prodids;
	    	private String moneys;
	    	private String surid;
	    	private String orderid;
			public String getProdids() {
				return prodids;
			}
			public void setProdids(String prodids) {
				this.prodids = prodids;
			}
			public String getMoneys() {
				return moneys;
			}
			public void setMoneys(String moneys) {
				this.moneys = moneys;
			}
			public String getSurid() {
				return surid;
			}
			public void setSurid(String surid) {
				this.surid = surid;
			}
			public String getOrderid() {
				return orderid;
			}
			public void setOrderid(String orderid) {
				this.orderid = orderid;
			}
	    	
	    }
}
