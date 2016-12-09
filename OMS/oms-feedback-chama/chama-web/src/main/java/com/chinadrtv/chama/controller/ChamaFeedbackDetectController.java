package com.chinadrtv.chama.controller;

import com.chinadrtv.service.iagent.DetectService;
import com.chinadrtv.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-27
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/feedback" })
public class ChamaFeedbackDetectController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChamaFeedbackDetectController.class);
    @Autowired
    private DetectService detectService;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/detect", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testFeedback() throws Exception{
        logger.info("begin test connect.......");
        try{
            int temp = detectService.omsTestConnectDate();
            logger.info("search result:"+temp);
        }catch (Exception exp)
        {
            logger.error("test error:",exp);
            throw exp;
        }
        logger.info("end test connect....");
        return "ok";
    }

    @Autowired
    private Date lastHandleTime;

    @RequestMapping(value = "/lastHandleTime", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getLastHandleTime(){
        String temp = df.format(lastHandleTime);
        logger.info("lastHandleTime:"+lastHandleTime);
        return temp;
    }

}
