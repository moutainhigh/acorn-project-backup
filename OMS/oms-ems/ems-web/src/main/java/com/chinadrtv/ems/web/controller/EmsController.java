package com.chinadrtv.ems.web.controller;

import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.ems.service.TransformJsonObejctService;
import com.chinadrtv.erp.orderfeedback.model.MailStatusHis;
import com.chinadrtv.erp.orderfeedback.service.MailStatusHisService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/es40-order-logistics-feedback-ems" })
public class EmsController {
    //log
    private static final Log logger =  LogFactory.getLog(EmsController.class);

    @Autowired
    private TransformJsonObejctService transformJsonObejctService;
    @Autowired
    private MailStatusHisService mailStatusHisService;


    @RequestMapping(value = "/http/update", method = RequestMethod.POST,headers ={"Content-Type=text/json"})
    @ResponseBody
    public String emsFeedback(HttpServletRequest request)
    {
        StringBuilder response = new StringBuilder();

        try{
            //获取授权信息
            String authenticate = request.getHeader("authenticate");
            logger.debug("授权信息authenticate = "+authenticate);
            //获取版本号
            String version = request.getHeader("version");
            logger.debug("版本号version = "+version);
            //验证授权信息和版本号
            String strResult = transformJsonObejctService.checkAuthenticateAndVersion(authenticate,version);
            if(!("").equals(strResult)){
                return strResult;
            }
            //获取post Json数据
            ServletInputStream stream=request.getInputStream();
            byte[] bytes= new byte[request.getContentLength()];
            stream.read(bytes);
            String data=new String(bytes,"utf-8");
            stream.close();
            //转换Json数据对象
            List<MailStatusHis> mailStatusHisList = transformJsonObejctService.transXmlToList(data);
            logger.debug("mailStatusHisList size = "+mailStatusHisList.size());
            //调用公共接口保存数据
            mailStatusHisService.saveAll(mailStatusHisList);
            logger.debug("EMS反馈成功！");
            response.append("{\"response\":{\"success\":1,\"failmailnums\":\"\",\"remark\":\"备注信息\"}}");
        }catch (Exception e)
        {
        	logger.error("com.chinadrtv.ems.web.controller.EmsController:异常信息",e);
            response.append("{\"response\":{\"success\":0,\"failmailnums\":\"\",\"remark\":\""+e.getMessage()+"\"}}");
        }
        return response.toString();
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex, HttpServletRequest request) {
        //MarshallingFailureException marshallingFailureException=null;
        return ex.getMessage();
    }
}
