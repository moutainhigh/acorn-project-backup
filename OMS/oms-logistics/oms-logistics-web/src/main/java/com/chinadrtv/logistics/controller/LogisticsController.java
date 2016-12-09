package com.chinadrtv.logistics.controller;

import com.chinadrtv.logistics.dal.model.OrderLogistics;
import com.chinadrtv.logistics.model.OrderInfo;
import com.chinadrtv.logistics.service.OrderLogisticsService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/logistics"})
public class LogisticsController
{
    private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);

    @Autowired
    private OrderLogisticsService orderLogisticsService;

    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public List<OrderLogistics> query(@RequestBody OrderInfo orderInfo) { logger.info("begin query");
        if ((orderInfo == null) || (StringUtils.isEmpty(orderInfo.getOrderId())))
        {
            logger.error("order id is null");
            return null;
        }

        logger.info("order query id:" + orderInfo.getOrderId());

        Long startStamp = Long.valueOf(System.currentTimeMillis());
        try
        {
            Long endStamp;
            return this.orderLogisticsService.queryLogistics(orderInfo.getOrderId());
        }
        finally {
            Long endStamp = Long.valueOf(System.currentTimeMillis());
            logger.info("end query costs: " + (endStamp.longValue() - startStamp.longValue()));
        }
    }

    @ExceptionHandler
    @ResponseBody
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