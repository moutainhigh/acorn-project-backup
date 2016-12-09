package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.sales.core.service.ShipmentHeaderService;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentException;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentReturnCode;
import com.chinadrtv.erp.tc.core.service.RfErrorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private RfErrorService rfErrorService;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
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
}
