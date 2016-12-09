package com.chinadrtv.logistics.controller;

import com.chinadrtv.logistics.dal.model.OrderLogistics;
import com.chinadrtv.logistics.dal.model.OrderTrackRemark;
import com.chinadrtv.logistics.model.OrderInfo;
import com.chinadrtv.logistics.service.OrderTrackRemarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/track"})
public class TrackRemarkController {
    private static final Logger logger = LoggerFactory.getLogger(TrackRemarkController.class);

    @Autowired
    private OrderTrackRemarkService orderTrackRemarkService;

    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public List<OrderTrackRemark> query(@RequestBody OrderInfo orderInfo) { logger.info("begin query");
        if ((orderInfo == null) || (StringUtils.isEmpty(orderInfo.getOrderId())))
        {
            logger.error("order id is null");
            return null;
        }

        logger.info("order query id:" + orderInfo.getOrderId());

        Long startStamp = Long.valueOf(System.currentTimeMillis());
        try
        {
            List<OrderTrackRemark> orderTrackRemarkList = this.orderTrackRemarkService.queryTrackRemarks(orderInfo.getOrderId());

            if(orderTrackRemarkList!=null)
            {
                Collections.sort(orderTrackRemarkList,new Comparator<OrderTrackRemark>(){
                    public int compare(OrderTrackRemark arg0, OrderTrackRemark arg1) {
                        if(arg0.getCreateDate()==null||arg1.getCreateDate()==null)
                            return 0;
                        return arg1.getCreateDate().compareTo(arg0.getCreateDate());
                    }
                });
                return orderTrackRemarkList;
            }
        }
        finally {
            Long endStamp = Long.valueOf(System.currentTimeMillis());
            logger.info("end query costs: " + (endStamp.longValue() - startStamp.longValue()));
        }

        return new ArrayList<OrderTrackRemark>();
    }

    @ExceptionHandler
    public String handleException(Exception ex, HttpServletRequest request)
    {
        logger.error("system error:", ex);
        logger.error("request info url:", request.getRequestURL());
        logger.error("request ip:", getIp(request));
        return ex.getMessage();
    }

    private String getIp(HttpServletRequest request)
    {
        String strIp = "";
        String forwarded = request.getHeader("x-forwarded-for");
        if (!StringUtils.isEmpty(forwarded))
        {
            strIp = strIp + "forward ip:";
            strIp = strIp + forwarded;
            strIp = strIp + ";";
        }

        String real = request.getHeader("x-real-ip");
        if (!StringUtils.isEmpty(real))
        {
            strIp = strIp + "real ip:";
            strIp = strIp + real;
            strIp = strIp + ";";
        }
        strIp = strIp + request.getRemoteAddr();
        return strIp;
    }
}
