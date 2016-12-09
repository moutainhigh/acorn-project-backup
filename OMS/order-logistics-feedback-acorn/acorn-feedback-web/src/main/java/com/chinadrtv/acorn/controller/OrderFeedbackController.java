package com.chinadrtv.acorn.controller;

import com.chinadrtv.acorn.bean.CheckResult;
import com.chinadrtv.acorn.bean.FeedbackInfo;
import com.chinadrtv.acorn.service.OrderLogisticsFeedbackService;
import com.chinadrtv.model.iagent.MailStatusHis;
import com.chinadrtv.service.iagent.MailStatusHisService;
import com.chinadrtv.service.oms.OmsFeedbackTestService;
import com.chinadrtv.util.HttpParamUtils;
import com.chinadrtv.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({ "/acorn" })
public class OrderFeedbackController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackController.class);

    private static final String USER_ID="UserId";
    private static final String XML="Xml";
    private static final String CHECK_DATA="CheckData";

    @Autowired
    private OrderLogisticsFeedbackService orderLogisticsFeedbackService;

    @Autowired
    private MailStatusHisService<MailStatusHis> mailStatusHisService;

    @RequestMapping(value = "/feedback", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void feedback(HttpServletRequest request,HttpServletResponse response)
    {
        logger.info("beign feedback");
        Long startStamp = System.currentTimeMillis();
        try{
            Map<String, String> map=getParamsFromRequest(request);

            if(map==null)
                map=new Hashtable<String, String>();
            FeedbackInfo feedbackInfo=new FeedbackInfo();
            if(map.containsKey(USER_ID))
            {
                feedbackInfo.setUserId(map.get(USER_ID));
            }
            if(map.containsKey(XML))
            {
                feedbackInfo.setXml(map.get(XML));
            }
            if(map.containsKey(CHECK_DATA))
            {
                feedbackInfo.setCheckData(map.get(CHECK_DATA));
            }

            CheckResult checkResult = orderLogisticsFeedbackService.checkFeedbackData(feedbackInfo);
            if(!checkResult.isSucc())
            {
                writeResult(response, checkResult.getErrorMsg());
                logger.error("feedback error:"+checkResult.getErrorMsg());
                logger.error("feedback user:"+feedbackInfo.getUserId());
                logger.error("feedback data:"+feedbackInfo.getXml());
                return;
            }
            List<MailStatusHis> mailStatusHisList = orderLogisticsFeedbackService.parseFeedbackData(feedbackInfo);

            //TODO:导入反馈
            mailStatusHisService.saveAll(mailStatusHisList);
            //return "OK";
            logger.info("feed back end");
            writeResult(response,checkResult.getErrorMsg());
        }catch (Exception exp)
        {
            logger.error("feed back error:",exp);
            writeResult(response,exp.getMessage());
        }
        finally {
            Long endStamp = System.currentTimeMillis();
            logger.info("feed back costs: " + (endStamp - startStamp));
        }
    }

    private void writeResult(HttpServletResponse response,String str)
    {
        ServletOutputStream stream=null;
        try{
            stream= response.getOutputStream();
            stream.write(MD5Util.encode2hex(str).getBytes());
        }catch (Exception exp)
        {
            logger.error("get output stream error:",exp);
        }
        finally {
            if(stream!=null)
                try{
                stream.close();
                }catch (Exception innerExp)
                {
                    logger.error("close inner exp:",innerExp);
                }
        }

    }

    private Map<String, String> getParamsFromRequest(HttpServletRequest request)
    {
        //首先从输入参数中获取
        if(request.getParameter(USER_ID)!=null||request.getParameter(XML)!=null||request.getParameter(CHECK_DATA)!=null)
        {
            Map<String, String> retMap= new HashMap<String, String>();
            if(request.getParameter(USER_ID)!=null)
                retMap.put(USER_ID,request.getParameter(USER_ID));
            if(request.getParameter(XML)!=null)
                retMap.put(XML,request.getParameter(XML));
            if(request.getParameter(CHECK_DATA)!=null)
                retMap.put(CHECK_DATA,request.getParameter(CHECK_DATA));
            return retMap;
        }
        /*Map map=request.getParameterMap();
        if(map!=null&&map.size()>0)
        {
            Map<String, String> retMap= new HashMap<String, String>();
            if(map.containsKey(USER_ID))
            {
                if(map.get(USER_ID)!=null)
                {
                    retMap.put(USER_ID,map.get(USER_ID).toString());
                }
            }
            if(map.containsKey(XML))
            {
                if(map.get(XML)!=null)
                    retMap.put(XML,map.get(XML).toString());
            }
            if(map.containsKey(CHECK_DATA))
            {
                if(map.get(CHECK_DATA)!=null)
                    retMap.put(CHECK_DATA,map.get(CHECK_DATA).toString());
            }

            return retMap;
        }*/

        ServletInputStream stream=null;
        try{
            stream=request.getInputStream();
            byte[] bytes= new byte[request.getContentLength()];
            int count=stream.read(bytes);
            System.out.println(count);
            if(count>0)
            {
                return HttpParamUtils.getUrlParameters(new String(bytes));
            }
            return null;
        }catch (Exception exp)
        {
            logger.error("get input stream error:",exp);
            return null;
        }
        finally {
            if(stream!=null)
            {
                try{
                    stream.close();
                }catch (Exception innerExp)
                {
                    logger.error("stream close error:", innerExp);
                }
            }
        }
    }


    @Autowired
    private OmsFeedbackTestService omsFeedbackTestService;

    @RequestMapping(value = "/feedback/detect", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testFeedback() throws Exception{
        logger.info("begin test");
        try{
            int temp = omsFeedbackTestService.omsTestConnectDate();
        }catch (Exception exp)
        {
            logger.error("test error:",exp);
            throw exp;
        }
        logger.info("end test");
        return "ok";
    }

    @ExceptionHandler
    public String handleException(Exception ex, HttpServletRequest request) {
        logger.error("system error:",ex);
        return MD5Util.encode2hex(ex.getMessage());
    }

}
