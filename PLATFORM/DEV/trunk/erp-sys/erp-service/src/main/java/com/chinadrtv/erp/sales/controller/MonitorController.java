package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.sales.dao.MonitorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private MonitorDao monitorDao;

    @RequestMapping(value = "/detect")
    @ResponseBody
    public void detect(HttpServletResponse response)
    {
        Date dt=monitorDao.getSysDate();
        if(dt==null)
        {
            throw new RuntimeException("detect error");
        }
        try{
            response.getOutputStream().write("ok".getBytes("utf-8"));
        }catch (Exception exp)
        {
            logger.error("detect output error:",exp);
            throw new RuntimeException(exp.getMessage());
        }
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex, HttpServletRequest request) {
        logger.error("sales detect error:",ex);
        return ex.getMessage();
    }
}
