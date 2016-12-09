package com.chinadrtv.erp.tc.controller;

import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_CODE;
import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_DESC;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;
import com.chinadrtv.erp.tc.core.model.PretradeReturnCode;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.tc.service.OrderhistService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-24
 */
@Controller
@RequestMapping("/order/1")
public class OrderhistController extends TCBaseController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderhistController.class);
	
    @Autowired
    private OrderhistService orderhistService;

    private String getIp(HttpServletRequest request)
    {
        return request.getRemoteAddr();
    }

    public OrderhistController()
    {
        order2tradeCodeMap.put(OrderCode.PRETRADE_CONTACT_NAME_INVALID,"001");
        order2tradeCodeMap.put(OrderCode.PRETRADE_HAVE_CREATED,"002");
        order2tradeCodeMap.put(OrderCode.PRETRADE_ADDRESS_DSC_INVALID,"003");
        order2tradeCodeMap.put(OrderCode.ORDER_PRICE_ZERO,"004");
        order2tradeCodeMap.put(OrderCode.PRETRADE_PROD_INVALID,"008");
        order2tradeCodeMap.put(OrderCode.PRETRADE_ADDRESS_ERROR,"009");
        order2tradeCodeMap.put(OrderCode.PRETRADE_PHONE_NOT_FOUND,"117");

        order2tradeCodeMap.put(OrderCode.PRETRADE_IDCARD_INVALID,"120");
        order2tradeCodeMap.put(OrderCode.PRETRADE_CREDITCARDTYPE_INVALID,"121");
        order2tradeCodeMap.put(OrderCode.PRETRADE_CREDITCARDEXPIRE_INVALID,"122");
        order2tradeCodeMap.put(OrderCode.PRETRADE_CREDITCARD_IMPORT_INVALID,"123");
        order2tradeCodeMap.put(OrderCode.PRETRADE_CREDITCARDCYCLES_INVALID,"124");
    }

    @RequestMapping(value = "/add",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public PretradeReturnCode addOrderhist(@RequestBody PreTradeRest preTrade){

        logger.debug("start add order");
        //new code start
        Long startStamp = System.currentTimeMillis();
        //new code end

        try
        {

            String orderId=orderhistService.addOrderhist(preTrade);

            //new code start
            Long endStamp = System.currentTimeMillis();
            logger.info("pre order import costs: " + (endStamp - startStamp));
            //new code end

            logger.debug("add order succ");

            PretradeReturnCode pretradeReturnCode=new PretradeReturnCode();
            pretradeReturnCode.setSuccess(true);
            pretradeReturnCode.setOrderId(orderId);
            pretradeReturnCode.setCode(OrderCode.SUCC);
            pretradeReturnCode.setImpState("013");
            pretradeReturnCode.setDesc("订单导入成功");

            return pretradeReturnCode;
        }
        catch (ConstraintViolationException validExp)
        {
            //new code start
            Long endStamp = System.currentTimeMillis();
            logger.info("pre order import-1 costs: " + (endStamp - startStamp));
            //new code end

            logger.error("add order valid error",validExp);
            PretradeReturnCode pretradeReturnCode=new PretradeReturnCode();
            pretradeReturnCode.setImpState("100");
            pretradeReturnCode.setCode(OrderCode.FIELD_INVALID);
            pretradeReturnCode.setDesc(this.getConstraintErrorDesc(validExp));
            return pretradeReturnCode;
        }
        catch (OrderException orderExp)
        {
            //new code start
            Long endStamp = System.currentTimeMillis();
            logger.info("pre order import-2 costs: " + (endStamp - startStamp));
            //new code end

             logger.error("add order error",orderExp);
             PretradeReturnCode pretradeReturnCode=new PretradeReturnCode();
            String tradeCode=this.getPreTradErrorCodeFromOrder(orderExp.getOrderReturnCode());
            pretradeReturnCode.setCode(orderExp.getOrderReturnCode().getCode());
            pretradeReturnCode.setDesc(orderExp.getOrderReturnCode().getDesc());
            if("002".equals(tradeCode))
            {
                pretradeReturnCode.setOrderId(orderExp.getOrderReturnCode().getDesc());
                pretradeReturnCode.setDesc("相同网络订单号已经创建过了");
            }
            pretradeReturnCode.setImpState(tradeCode);
             return pretradeReturnCode;
        }
        catch (Exception exp)
        {
            //new code start
            Long endStamp = System.currentTimeMillis();
            logger.info("pre order import-3 costs: " + (endStamp - startStamp));
            //new code end

            logger.error("add order unkown error---",exp);
            PretradeReturnCode pretradeReturnCode=new PretradeReturnCode();
            pretradeReturnCode.setCode(OrderCode.SYSTEM_ERROR);
            pretradeReturnCode.setDesc(exp.getMessage());
            pretradeReturnCode.setImpState("100");

            if(exp instanceof HibernateException)
            {
                HibernateException hibernateException=(HibernateException)exp;
                if(hibernateException.getMessage().contains("订单明细里面的个数或者价格无效"))
                {
                    pretradeReturnCode.setImpState("005");
                }
                else if(hibernateException.getMessage().contains("订单里面的价格与明细中的不符合"))
                {
                    pretradeReturnCode.setImpState("007");
                }
            }


            logger.error("add order unkown error***");

            return pretradeReturnCode;
        }
    }

    @RequestMapping(value = "/save",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public OrderReturnCode saveOrderhist(@RequestBody Order orderhist)
    {
    	Long startStamp = System.currentTimeMillis();
    	
        logger.debug("start update order");

        try
        {
            orderhistService.updateOrderhist(orderhist);

            logger.debug("update order succ");
            
            Long endStamp = System.currentTimeMillis();
            
            logger.warn("controller costs: " + (endStamp - startStamp));

            return new OrderReturnCode(OrderCode.SUCC,null);
        }
        catch (ConstraintViolationException validExp)
        {
            logger.error("add order valid error",validExp);
            OrderReturnCode orderReturnCode=new OrderReturnCode();
            orderReturnCode.setCode(OrderCode.FIELD_INVALID);
            orderReturnCode.setDesc(this.getConstraintErrorDesc(validExp));
            return orderReturnCode;
        }
        catch (OrderException orderExp)
        {
            logger.error("update order error",orderExp);
            return orderExp.getOrderReturnCode();
        }
        catch (Exception exp)
        {
            logger.error("update order unkown error",exp);
            exp.printStackTrace();
            return new OrderReturnCode(OrderCode.SYSTEM_ERROR,exp.getMessage());
        }
    }

    /**
     * 逻辑删除订单 
    * @Description: 更新订单状态，并插入快照表
    * @param orderhist
    * @return
    * @return Map<String,Object>
    * @throws
     */
    @RequestMapping(value = "/deleteLogic", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public Map<String, Object> deleteOrderLogic(@RequestBody Order orderhist){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try {
			orderhistService.deleteOrderLogic(orderhist);
			resultMap.put("code", OrderCode.SUCC);
			resultMap.put("desc", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", OrderCode.SYSTEM_ERROR);
			resultMap.put("desc", "操作失败  "+e.getMessage());
			logger.error("TC 删除订单失败", e);
		}
    	return resultMap;
    }
    
    /**
     * 物理删除订单
    * @Description: 直接删除数据，并插入快照表
    * @param orderhist
    * @return
    * @return Map<String,Object>
    * @throws
     */
    @RequestMapping(value = "/deletePhysical", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public Map<String, Object> deleteOrderPhysical(@RequestBody Order orderhist){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
			orderhistService.deleteOrderPhysical(orderhist);
			resultMap.put("code", OrderCode.SUCC);
			resultMap.put("desc", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", OrderCode.SYSTEM_ERROR);
			resultMap.put("desc", "操作失败  "+e.getMessage());
			logger.error("TC 删除订单失败", e);
		}
    	return resultMap;
    }
    
    /**
     * 更新订单索权号
    * @Description: 更新订单索权号
    * @param orderhist	订单号
    * @return
    * @return Map<String,Object>
    * @throws
     */
	@RequestMapping(value = "/updateOrderRightNum", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderRightNum(@RequestBody Order orderhist) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int result = orderhistService.updateOrderRightNum(orderhist);
			String code = "";
			String message = "";
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，该订单不是信用卡支付";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，该订单已入库";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
			resultMap.put(OrderCode.OPERATE_CODE, code);
			resultMap.put(OrderCode.OPERATE_DESC, message);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put(OrderCode.OPERATE_CODE, OrderCode.SYSTEM_ERROR);
			resultMap.put(OrderCode.OPERATE_DESC, "更新失败 " + e.getMessage());
			logger.error("更新订单索权号", e);
		}

		return resultMap;
	}
	
	/**
	 * 更新订单子订单号 
	* @Description: 
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderChild", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderChild(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		
		try {
			int result = orderhistService.updateOrderChild(orderhist);

			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单未出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = OrderCode.SYSTEM_ERROR;
			message = "更新失败， " + e.getMessage();
			logger.error("更新订单子订单号 ", e);
		}
		
		resultMap.put(OrderCode.OPERATE_CODE, code);
		resultMap.put(OrderCode.OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 订单取消分拣状态
	* @Description: 订单取消分拣状态
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/cancelSortStatus", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public OrderReturnCode cancelSortStatus(@Valid @RequestBody Map<String, Object> params){
		
		String code = "";
		String message = "";
		try {
			if(null==params.get("orderid") || "".equals(params.get("orderid"))){
				code = OrderCode.FIELD_INVALID;
				message = "订单号[orderid]不能为空";
				return new OrderReturnCode(code, message);
			}
			if(null==params.get("mdusr") || "".equals(params.get("mdusr"))){
				code = OrderCode.FIELD_INVALID;
				message = "修改人[mdusr]不能为空";
				return new OrderReturnCode(code, message);
			}
			if(null==params.get("companyid") || "".equals(params.get("companyid"))){
				code = OrderCode.FIELD_INVALID;
				message = "送货公司[companyid]不能为空";
				return new OrderReturnCode(code, message);
			}
			
			int result = orderhistService.cancelSortStatus(params);
			
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单已出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "更新失败，" + e.getMessage();
			logger.error("订单取消分拣状态", e);
			e.printStackTrace();
		}
		
		return new OrderReturnCode(code, message);
	}
	
	/**
	 * SR3.5_1.11 修改订单订购类型
	* @Description:  修改订单订购类型
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderType", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderType(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.updateOrderType(orderhist);
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单已出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("修改订单订购类型", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 	SR3.5_1.13 更新订购方式和送货公司
	* @Description:  更新订购方式和送货公司
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderTypeAndDelivery", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderTypeAndDelivery(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.updateOrderTypeAndDelivery(orderhist);
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单已出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("修改订单订购类型", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}

	
	/**
	 * 修改订单的收货人
	* @Description: 
	* @param params
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderConsignee", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderConsignee(@RequestBody Map<String, Object> params){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.updateOrderConsignee(params);
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单已出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("修改订单的收货人/付款人", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}
	
	
	/**
	 * 修改订单的付款人
	* @Description: 
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderPayer", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderPayer(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.updateOrderPayer(orderhist);
			if(result == -1){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单已出库";
			}else if(result == -2){
				code = OrderCode.FIELD_INVALID;
				message = "更新失败，订单不存在";
			}else if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("修改订单的收货人/付款人", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 订单回访取消订单
	* @Description: 订单回访取消订单
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/callbackCancelOrder",  method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> callbackCancelOrder(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.callbackCancelOrder(orderhist);
			if(result==0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "更新失败, " + e.getMessage();
			logger.error("订单回访取消订单", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 订单回访保存时更新订单信息
	* @Description: 
	* @param orderhist
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/callbackUpdateOrder", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> callbackUpdateOrder(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.callbackUpdateOrder(orderhist);
			if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "更新失败，" + e.getMessage();
			logger.error("订单回访保存时更新订单信息", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 	SR3.5_1.5更新订单明细 
	* @Description: 更新订单明细
	* @param orderdet
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/updateOrderDetail", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> updateOrderDetail(@RequestBody OrderDetail orderdet){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = orderhistService.updateOrderDetail(orderdet);
			if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "更新失败，" + e.getMessage();
			logger.error("订单回访保存时更新订单信息", e);
			e.printStackTrace();
		}
		
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
		
		return resultMap;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public String testOrderhist(@RequestBody String str) {
		logger.debug("just test ");
		System.out.println("just test ");

		return str;
	}
    
    /**
     * update
     * 
     * @param json
     * @author haoleitao
     * @date 2013-2-16 下午1:09:30
     */

    @RequestMapping(value = "/updateOHByJSON",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public Map<String, Object> updateOrderhistByJSON( @RequestBody String json){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	json= json.replace("{\"", "");
    	json= json.replace("\"}", "'");
    	json= json.replace("\":", ":");
    	json= json.replace(":\"", ":'");
    	json= json.replace("\",", "',");
    	json= json.replace(",\"", ",");
    	json= json.replace(":'", "='");
    	
    	String attrs[] = json.split(",");
    	String getTreadId[] = new String[2];
    	String setStr = "";
	    String message = "failed";	
	    String code = OrderCode.SYSTEM_ERROR; 
    	for(String attr : attrs){
    		//获取treadid
    		 if(attr.indexOf("orderid") != -1){
    			 getTreadId = attr.split("=");
    		 }else if(attr.indexOf("crdt") != -1) {
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("senddt") != -1) {
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("fbdt") != -1) {
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("outdt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("accdt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("starttm") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("endtm") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("parcdt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("netcrdt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("addt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("rfoutdat") != -1) {
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("confirmexpdt") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("transdate") != -1) { 
    			 setStr+=str2datea(attr);
    		 }else if(attr.indexOf("lastUpdateTime") != -1) {
    			 setStr+=str2datea(attr);
    		 } else{
    			 setStr+= attr+",";
    		 }

    		 
    	}
    	if(setStr.length()>1 && getTreadId[1].length()>1){
    	setStr= "set "+ setStr.substring(0,setStr.length()-1);
	    	try {
				int result = orderhistService.updateOrderhist(setStr,getTreadId[1].trim());
				if(result==1){
					message="success";
					code = OrderCode.SUCC;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message="failed";
				code = OrderCode.SYSTEM_ERROR;
			}
    	}
		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);
    	 return resultMap;
    	//Order orderhist = 
    	//HttpUtil.ajaxReturn(response, message, "application/text");
    }
    
    
    private String str2datea(String attr){
    	//crdt='2004-05-07 13:23:44'
    	//= to_date('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
    	attr = attr.replace("='","= to_date('");
    	attr += ",'yyyy-mm-dd hh24:mi:ss'),";
    	return attr;
    }

    /*@ExceptionHandler
    @ResponseBody
    public OrderReturnCode handleException(Exception ex, HttpServletRequest request) {
        //response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
        return new OrderReturnCode(OrderCode.SYSTEM_ERROR,ex.getMessage());
    } */


    @RequestMapping(value = "/autoChoose",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public OrderReturnCode autoChooseOrder(@RequestBody OrderAutoChooseInfo orderAutoChooseInfo)
    {
        try
        {
            orderhistService.autoChooseOrder(orderAutoChooseInfo);
            return new OrderReturnCode(OrderCode.SUCC,"");
        }
        catch (OrderException orderExp)
        {
            return orderExp.getOrderReturnCode();
        }
        catch (Exception exp)
        {
            logger.error("autoChooseOrder unkown error---",exp);
            OrderReturnCode returnCode=new OrderReturnCode();
            returnCode.setCode(OrderCode.SYSTEM_ERROR);
            returnCode.setDesc(exp.getMessage());
            logger.error("autoChooseOrder unkown error***");
            return returnCode;
        }
    }

    private static Map<String,String> order2tradeCodeMap=new HashMap<String, String>();

    private String getPreTradErrorCodeFromOrder(OrderReturnCode orderCode)
    {
        if(StringUtils.isNotEmpty(orderCode.getCode()))
        {
            if(order2tradeCodeMap.containsKey(orderCode.getCode()))
                return order2tradeCodeMap.get(orderCode.getCode());
        }
        return "100";
    }

}
