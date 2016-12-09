/*
 * @(#)ShipmentController.java 1.0 2013-2-16上午11:08:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.oms.dto.OrderPaymentDto;
import com.chinadrtv.erp.oms.dto.ShipmentSenderDto;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.OrderPaymentConfirmService;
import com.chinadrtv.erp.oms.service.SourceConfigure;
import com.chinadrtv.erp.oms.util.Page;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-2-16 上午11:08:20 
 * 
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentController.class);
	
	private final static String PAYTYPE_MAILORDER = "12";
	
	private final static String PAYTYPE_REMITTANCE = "13";
	
	@Autowired
	private SourceConfigure sourceConfigure;
	@Autowired
	private CompanyService companyService;
	@Autowired
    private RestTemplate template;
	@Autowired
	private AgentUserDao agentUserDao;
	
	@Autowired
	private OrderPaymentConfirmService orderPaymentConfirmService;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
	
	/**
	 * SR3.6_1.8运单重发
	* @Description: 
	* @return
	* @return ModelAndView
	* @throws
	 */
    @SuppressWarnings({"unchecked", "static-access"})
	@RequestMapping(value = "/index")
	public ModelAndView resendWayBill(String orderid){
        ModelAndView mav = new ModelAndView("shipment/resendWaybill");
        if ((null != orderid) && (!"".equals(orderid)))
            try {
                Map params = new HashMap();
                params.put("orderid", orderid);

                String url = this.sourceConfigure.getQueryWaybill();

                Map resultMap = (Map)this.template.postForObject(url, params, Map.class, new Object[0]);

                boolean success = Boolean.parseBoolean(resultMap.get("success").toString());
                if (success) {
                    JSONObject jsonObject = JSONObject.fromObject(resultMap.get("shipmentSenderDto"));

                    ShipmentSenderDto shDto = (ShipmentSenderDto)JSONObject.toBean(jsonObject, ShipmentSenderDto.class);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Long crdt =  Long.parseLong(((Map)resultMap.get("shipmentSenderDto")).get("crdt").toString());
                    Long senddt =  Long.parseLong(((Map)resultMap.get("shipmentSenderDto")).get("senddt").toString());
                    Long rfoutdat =  Long.parseLong(((Map)resultMap.get("shipmentSenderDto")).get("rfoutdat").toString());
                    shDto.setOrderDate(new Date(crdt));
                    shDto.setOutDate(new Date(rfoutdat));
                    shDto.setSendDate(new Date(senddt));
                    if ((null != shDto.getWorkerId()) && (!"".equals(shDto.getWorkerId()))) {
                        try {
                            AgentUser agentUser = (AgentUser)this.agentUserDao.get(shDto.getWorkerId());
                            shDto.setWorkerName(agentUser.getName());
                        } catch (Exception e) {
                            logger.error("查询运单加载AgentUser失败", e);
                            e.printStackTrace();
                            throw new Exception("查询运单加载AgentUser失败");
                        }
                    }
                    mav.addObject("shipmentSenderDto", shDto);
                } else {
                    mav.addObject("message", "订单[" + orderid + "] 加载发运单信息失败 : " + resultMap.get("message").toString());
                    mav.addObject("shipmentSenderDto", new ShipmentSenderDto());
                    logger.error("加载运单发送信息失败");
                }
            }
            catch (Exception e) {
                mav.addObject("shipmentSenderDto", new ShipmentSenderDto());
                mav.addObject("message", "订单[" + orderid + "] 加载发运单信息失败 : " + e.getMessage());
                logger.error("加载运单发送信息失败", e);
                e.printStackTrace();
            }
        else {
            mav.addObject("shipmentSenderDto", new ShipmentSenderDto());
        }

        List companyList = this.companyService.getAllCompanies();
        mav.addObject("companyList", companyList);
        return mav;
	}
	
	/**
	 * 重发运单
	* @Description: 
	* @param shipmentSenderDto
	* @return ModelAndView
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/resendWaybill")
	@ResponseBody
	public Map<String, Object> resendWaybill(ShipmentSenderDto shipmentSenderDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			AgentUser agentUser = SecurityHelper.getLoginUser();
			shipmentSenderDto.setMdusr(agentUser.getUserId());
			
			String url = sourceConfigure.getResendWaybill();
			resultMap = template.postForObject(url, shipmentSenderDto, Map.class);
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
			logger.error("重发运单失败", e);
			e.printStackTrace();
		}
		return resultMap;
	}

	@RequestMapping(value = "/orderPaymentConfirm")
	public ModelAndView orderPaymentConfirm(){
		ModelAndView mav = new ModelAndView("shipment/orderPaymentConfirm");
		Calendar calendar = Calendar.getInstance();
		mav.addObject("startDate", sdf.format(calendar.getTime()));
		mav.addObject("endDate", sdf.format(calendar.getTime()));
		return mav;
	}
	
	@RequestMapping(value = "/orderPaymentConfirm/payTypes", method = RequestMethod.POST)
	public void payTypes(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String jsonData = "[ {\"payTypeId\":\"\",\"name\":\"--请选择--\"}, {\"payTypeId\":" + PAYTYPE_MAILORDER + ",\"name\":\"邮购订单\"}, {\"payTypeId\":" + PAYTYPE_REMITTANCE + ",\"name\":\"银行转账\"} ]";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try {
			response.getWriter().print(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/orderPaymentConfirm/listOrder", method = RequestMethod.POST)
	public void listOrder(
			@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String orderId,
            @RequestParam(required = false, defaultValue="") String payUserName,
            @RequestParam(required = false, defaultValue="") String payUserTel,
            @RequestParam(required = false, defaultValue="") String payType,
            @RequestParam(required = false, defaultValue="") String orderDateS,
            @RequestParam(required = false, defaultValue="") String orderDateE,
			HttpServletRequest request, 
			HttpServletResponse response) throws IOException{

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		Page<OrderPaymentDto> opdPage = orderPaymentConfirmService.queryOrderPayment(orderId, payUserName, payUserTel, payType, orderDateS, orderDateE, (page - 1) * rows, rows);
        if(opdPage != null && opdPage.getContent()!=null){
            String jsonData = "{\"rows\":" +gson.toJson(opdPage.getContent()) + ",\"total\":" + opdPage.getTotalElements() + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
		
	}
	
	@RequestMapping(value = "/orderPaymentConfirm/confirm", method = RequestMethod.POST)
	public void confirm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String orderId = request.getParameter("orderIdStr");
		String payNo = request.getParameter("payNo");
		String payUser = request.getParameter("payUser");
		String payDateStr = request.getParameter("payDate");
		Date payDate = null;
		if(payDateStr!=null && !"".equals(payDateStr)){
			try {
				payDate = dateFormat.parse(payDateStr);
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
		}
		try {
			orderPaymentConfirmService.confirm(orderId, payNo, payUser, payDate);
		} catch (BizException e) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ e.getMessage() +"\"}");
		}

	}
	
	//////请求EMS发货//////
	@RequestMapping(value = "/omsRequestCarrier")
	public ModelAndView omsRequestCarrier(){
		ModelAndView mav = new ModelAndView("shipment/omsRequestCarrier");
		Calendar calendar = Calendar.getInstance();
		mav.addObject("timeS", sdf.format(calendar.getTime()));
		mav.addObject("timeE", sdf.format(calendar.getTime()));
		return mav;
	}
	
	/**
	 * 请求EMS发货，列表
	 * @param page
	 * @param rows
	 * @param timeS
	 * @param timeE
	 * @param isEms
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/omsRequestCarrier/list")
	public void omsRequestCarrierList(
			@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String timeS,
            @RequestParam(required = false, defaultValue="") String timeE,
            @RequestParam(required = false, defaultValue="") String isEms,
			HttpServletRequest request, 
			HttpServletResponse response){
		
		String json = null;
		try {
			json = orderPaymentConfirmService.omsRequestCarrierList(timeS, timeE, isEms, page, rows);
		} catch (IOException e) {
			json = "{\"status\":-1,\"msg\":\"" + e.getMessage() + "\"}";
			e.printStackTrace();
		}
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * 请求ems发货，审批（同意op：1/拒绝：op：0）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/omsRequestCarrier/approval", method = RequestMethod.POST)
	public void omsRequestCarrierApproval(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String orderIds = request.getParameter("orderIds");
		String designedEntityIds = request.getParameter("designedEntityIds");
		String designedWarehouseIds = request.getParameter("designedWarehouseIds");
		String op = request.getParameter("op");
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = "";
		if(user!=null){
			userId = user.getUserId();
		}
		
		String msg = null;
		try {
			msg = orderPaymentConfirmService.omsRequestCarrierApproval(ids, orderIds, designedEntityIds, designedWarehouseIds, op, userId);
		} catch (Exception e) {
			msg = "{\"status\":-1,\"msg\":\"" + e.getMessage() + "\"}";
			e.printStackTrace();
		}
        if(msg!=null){
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            try {
				response.getWriter().print(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
}
