/*
 * @(#)ShipmentController.java 1.0 2013-2-16上午11:08:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.controller;

import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_CODE;
import static com.chinadrtv.erp.tc.core.constant.OrderCode.OPERATE_DESC;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentException;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentReturnCode;
import com.chinadrtv.erp.tc.core.dto.ShipmentSenderDto;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.tc.core.service.RfErrorService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;

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
public class ShipmentController extends TCBaseController{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentController.class);
	
	@Autowired
	private ShipmentHeaderService shipmentHeaderService;
	@Autowired
	private CompanyService companyService;

    @Autowired
    private RfErrorService rfErrorService;
    
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
    
	/**
	 * 同步取消运单
	* @Description: 同步取消运单
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/cancelWaybill", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> cancelWaybill(@RequestBody Map<String, Object> params){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			int result = shipmentHeaderService.cancelWayBill(params);
			if(result == 0){
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("同步取消运单", e);
			e.printStackTrace();
		}
		
		resultMap.put(OrderCode.OPERATE_CODE, code);
		resultMap.put(OrderCode.OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * SR3.6_1.1产生运单 
	* @Description: 产生运单后需要将设置运单的OrderHistory对象并保存
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/generateWaybill", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> generateWaybill(@RequestBody Order orderhist){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = "";
		String message = "";
		try {
			shipmentHeaderService.generateWaybill(orderhist, false);
			//shipmentHeaderService.generateShipmentHeader(orderhist, false);
			
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "系统错误, " + e.getMessage();
			logger.error("同步取消运单", e);
			e.printStackTrace();
		}
		
		resultMap.put(OrderCode.OPERATE_CODE, code);
		resultMap.put(OrderCode.OPERATE_DESC, message);
		
		return resultMap;
	}
	
	/**
	 * 加载运单发送信息
	* @Description:
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/queryWaybill")
	@ResponseBody
	public Map<String, Object> queryWaybill(@RequestBody Map<String, Object> params){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ShipmentSenderDto shDto = shipmentHeaderService.queryWaybill(params.get("orderid").toString());
			shDto.setShipmentDetails(null);
			resultMap.put("shipmentSenderDto", shDto);
			resultMap.put("success", true);
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			resultMap.put("success", false);
			logger.error("加载运单发送信息失败", e);
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 重发运单
	* @Description: 
	* @param shipmentSenderDto
	* @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping(value = "/resendWaybill")
	@ResponseBody
	public Map<String, Object> resendWaybill(@RequestBody ShipmentSenderDto shipmentSenderDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			shipmentHeaderService.resendWaybill(shipmentSenderDto);
			resultMap.put("success", true);
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
			logger.error("重发运单失败", e);
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * @Description: 更改运单的送货公司
	 * @param params
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/deliverIdup", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public Map<String, Object> deliverIdup(@RequestBody Map<String, Object> params) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = "";
		String message = "";

		try {
			int result = shipmentHeaderService.updateShipmentEntity(params);
			if (result == 0) {
				code = OrderCode.SUCC;
				message = "更新成功";
			}
		} catch (Exception e) {
			code = OrderCode.SYSTEM_ERROR;
			message = "更改运单的送货公司失败, " + e.getMessage();

			e.printStackTrace();
			logger.error("更改运单的送货公司错误: ", e);
		}

		resultMap.put(OPERATE_CODE, code);
		resultMap.put(OPERATE_DESC, message);

		return resultMap;
	}

    @RequestMapping(value = "/scanOutShipment", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public ShipmentReturnCode scanOutShipment(@RequestBody RequestScanOutInfo requestScanOutInfo)
    {
        try{
            logger.info("begin scan out orderhist");

            this.shipmentHeaderService.scanOutShipment(requestScanOutInfo);

            logger.info("end scan out orderhist");

            return new ShipmentReturnCode(OrderCode.SUCC,null);
        }
        catch (ShipmentException shipmentExp)
        {
            logger.error("scan out orderhist have shipment exception",  shipmentExp);

            rfErrorService.saveScanOutErrorInfo(shipmentExp.getShipmentReturnCode(), requestScanOutInfo);

            return  shipmentExp.getShipmentReturnCode();
        }
        catch (RuntimeException runtimeExp)
        {
            logger.error("scan out orderhist have unkown exception");

            String errorMsg=runtimeExp.getMessage();
            if(StringUtils.isEmpty(errorMsg))
            {
                errorMsg="订单不存在";
            }
            if(runtimeExp instanceof ValidationException)
            {
                errorMsg="订单相关数据有错误，请联系相关人员";
            }
            ShipmentReturnCode shipmentReturnCode= new ShipmentReturnCode(OrderCode.SYSTEM_ERROR, errorMsg);

            rfErrorService.saveScanOutErrorInfo(shipmentReturnCode,requestScanOutInfo);

            logger.error("");
            logger.error("runtime error show order Id:" + requestScanOutInfo.getOrderId());
            logger.error("error show end", runtimeExp);
            logger.error("");
            return shipmentReturnCode;
        }
        catch (Exception exp)
        {
            logger.error("scan out orderhist have unkown exception", exp);

            String errorMsg=exp.getMessage();
            if(StringUtils.isEmpty(errorMsg))
            {
                errorMsg="订单不存在";
            }
            ShipmentReturnCode shipmentReturnCode= new ShipmentReturnCode(OrderCode.SYSTEM_ERROR, errorMsg);

            rfErrorService.saveScanOutErrorInfo(shipmentReturnCode,requestScanOutInfo);
            logger.error("");
            logger.error("error show order Id:"+requestScanOutInfo.getOrderId(),exp);
            logger.error("error show end");
            logger.error("");
            return shipmentReturnCode;
        }
    }

    /**
     * 结算导入确认
     * @Description: 结算导入确认
     * @return
     * @return Map<String,Object>
     * @throws
     */
    @RequestMapping(value = "/accountShipment", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public Map<String, Object> accountShipment(@RequestBody Map<String, Object> params){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String code = "";
        String message = "";
        try {
            boolean result = shipmentHeaderService.settleAccountsShipment(params);
            if(result){
                code = OrderCode.SUCC;
                message = "更新成功";
            }
        } catch (Exception e) {
            code = OrderCode.SYSTEM_ERROR;
            message = "系统错误, " + e.getMessage();
            logger.error("导入结算运单确认异常", e);
        }

        resultMap.put(OrderCode.OPERATE_CODE, code);
        resultMap.put(OrderCode.OPERATE_DESC, message);
        resultMap.put("clearid", params.get("clearid"));
        resultMap.put("orderid", params.get("orderid"));

        return resultMap;
    }
}
