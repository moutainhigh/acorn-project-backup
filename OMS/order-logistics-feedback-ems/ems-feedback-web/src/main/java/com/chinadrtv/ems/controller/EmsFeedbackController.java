package com.chinadrtv.ems.controller;

import com.chinadrtv.ems.service.LoggerService;
import com.chinadrtv.ems.service.TransformJsonObejctService;
import com.chinadrtv.model.iagent.MailStatusHis;
import com.chinadrtv.service.iagent.MailStatusHisService;

import com.chinadrtv.service.oms.OmsFeedbackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/es40-order-logistics-feedback-ems" })
public class EmsFeedbackController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EmsFeedbackController.class);

    public EmsFeedbackController()
    {
        logger.info("EmsFeedbackController is created!");
    }

    @Autowired
    private MailStatusHisService mailStatusHisService;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private TransformJsonObejctService transformJsonObejctService;

    @Autowired
    private OmsFeedbackTestService omsFeedbackTestService;

    @RequestMapping(value = "/http/update", method = {RequestMethod.GET,RequestMethod.POST},headers ={"Content-Type=text/json"})
    @ResponseBody
    public String emsFeedback(HttpServletRequest request)
    {
        StringBuilder response = new StringBuilder();
        Long startStamp = System.currentTimeMillis();

        try{
            //获取授权信息
            String authenticate = request.getHeader("authenticate");
            logger.info("授权信息authenticate = "+authenticate);
            //获取版本号
            String version = request.getHeader("version");
            logger.info("版本号version = "+version);
            //验证授权信息和版本号
            String strResult = transformJsonObejctService.checkAuthenticateAndVersion(authenticate,version);
            if(!("").equals(strResult)){
                logger.error("check auth error:"+strResult);
                return strResult;
            }
            //获取post Json数据
            ServletInputStream stream=null;
            String data=null;
            try{
                stream=request.getInputStream();
                byte[] bytes= new byte[request.getContentLength()];
                stream.read(bytes);
                data=new String(bytes,"utf-8");

                loggerService.logData(data);
            }catch (Exception exp)
            {
                logger.error("fetch data error:",exp);
                return "{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\""+exp.getMessage()+"\"}}";
            }
            finally {
                if(stream!=null)
                    stream.close();
            }

            //转换Json数据对象
            List<MailStatusHis> mailStatusHisList = transformJsonObejctService.transXmlToList(data);
            if(mailStatusHisList!=null&& mailStatusHisList.size()>0)
            {
                logger.info("mailStatusHisList size = " + mailStatusHisList.size());
                //调用公共接口保存数据
                mailStatusHisService.saveAll(mailStatusHisList);
                logger.debug("ems feed back succ！");
            }
            else
            {
                logger.error("ems no data:"+data);
            }
            response.append("{\"response\":{\"success\":1,\"failmailnums\":\"\",\"remark\":\"备注信息\"}}");
        }catch (Exception e)
        {
        	logger.error("ems feed back error:",e);
            response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\""+e.getMessage()+"\"}}");
        }
        finally {
            Long endStamp = System.currentTimeMillis();
            logger.info("ems feed back costs: " + (endStamp - startStamp));
        }
        return response.toString();
    }

    @RequestMapping(value = "/feedback/detect", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testFeedback() throws Exception{
        logger.info("begin test");
        StringBuilder response = new StringBuilder();
        try{
            int temp = omsFeedbackTestService.omsTestConnectDate();
            response.append("success connect data :"+temp);
        }catch (Exception ex){
            logger.info("test error:",ex);
            throw ex;
        }
        logger.info("end test");
        return "ok";
    }
    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex, HttpServletRequest request) {
        logger.error("system error:",ex);
        return ex.getMessage();
    }
}
