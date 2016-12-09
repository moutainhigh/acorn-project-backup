package com.chinadrtv.erp.oms.controller;

import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_CODE;
import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_DESC;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.oms.dao.PreTradeDao;
import com.chinadrtv.erp.oms.model.NetOrderInfo;
import com.chinadrtv.erp.oms.model.NetOrderResult;
import com.chinadrtv.erp.oms.service.SourceConfigure;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
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

        logger.debug("start add order:"+preTrade!=null?preTrade.getTradeId():"null");
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

             logger.error("add order error:"+orderExp.getMessage(),orderExp);
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


	@RequestMapping(value = "/test", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public String testOrderhist(@RequestBody String str) {
		logger.debug("just test ");
		System.out.println("just test ");

		return str;
	}


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

    private static final String sysUsrId="0000";

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private PreTradeDao preTradeDao;

    @Autowired
    private SourceConfigure sourceConfigure;


    @RequestMapping(value = "/process",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public NetOrderResult cancelOrderByNetOrderId(@RequestBody NetOrderInfo netOrderInfo)
    {
        NetOrderResult netOrderResult=new NetOrderResult();
        if(netOrderInfo==null||StringUtils.isBlank(netOrderInfo.getOps_trade_id())
                ||StringUtils.isBlank(netOrderInfo.getProcess()))
        {
            logger.error("no net orderid");
            netOrderResult.setMessage_code("C001");
            netOrderResult.setMessage("处理参数错误。");
            return netOrderResult;
        }

        logger.debug("handle net order:"+netOrderInfo.getOps_trade_id()+"-process:"+netOrderInfo.getProcess()+"-time:"+netOrderInfo.getCreated());
        if(!"1".equals(netOrderInfo.getProcess()))
        {
            logger.error("unknown process flag");
            netOrderResult.setMessage_code("C001");
            netOrderResult.setMessage("处理参数错误。");
            return netOrderResult;
        }
        //首先找到对应订单
        Order order=null;
        try
        {
            order=orderhistService.getOrderHistByNetOrderId(netOrderInfo.getOps_trade_id());
            if(order==null)
            {
                logger.error("not find order by net orderid:"+netOrderInfo.getOps_trade_id());
                //检查前置订单是否存在
                PreTrade preTrade=preTradeDao.findTradeByNetOrderId(netOrderInfo.getOps_trade_id());
                if(preTrade!=null)
                {
                    netOrderResult.setMessage_code("C002");
                    netOrderResult.setMessage("订单正在处理中，暂无法取消，请稍后再试。");
                }
                else
                {
                    netOrderResult.setMessage_code("C003");
                    netOrderResult.setMessage("无此订单，无法取消。");
                }

                return netOrderResult;
            }

            if(sourceConfigure.getNetOrderTypeList()!=null&&sourceConfigure.getNetOrderTypeList().size()>0)
            {
                if(!sourceConfigure.getNetOrderTypeList().contains(order.getOrdertype()))
                {
                    netOrderResult.setMessage_code("C004");
                    netOrderResult.setMessage("此订单类型不能取消");
                    return netOrderResult;
                }
            }

            orderhistService.updateOrderCancel(null, order.getOrderid(), "", sysUsrId);

            netOrderResult.setResult(true);
            netOrderResult.setMessage_code("C000");
            netOrderResult.setMessage("同意取消");
        }catch (ServiceException serviceExp)
        {
            logger.error("cancel order id:"+order.getOrderid()+" error:"+serviceExp.getMessage(), serviceExp);
            //检查订单是否已经取消了，如果是，那么返回成功
            Order orderOrg=orderhistService.getOrderHistByOrderid(order.getOrderid());
            if(AccountStatusConstants.ACCOUNT_CANCEL.equals(orderOrg.getStatus()))
            {
                netOrderResult.setResult(true);
                netOrderResult.setMessage_code("C000");
                netOrderResult.setMessage("同意取消");
                order = null;
            }
            else
            {
                netOrderResult.setMessage_code("C005");
                netOrderResult.setMessage(serviceExp.getMessage());
            }
        }catch (Exception exp)
        {
            logger.error("cancel order id:"+order.getOrderid()+" unknown error:", exp);
            netOrderResult.setMessage_code("C006");
            netOrderResult.setMessage(exp.getMessage());
        }

        if(netOrderResult.isResult()==true&&order!=null)
        {
            //处理消息
            try{
                sysMessageService.cancelOrderMessage(order.getOrderid());
            }catch (Exception exp)
            {
                logger.error("handle order message error:", exp);
            }
        }
        return netOrderResult;
    }

}
