package com.chinadrtv.logistics.controller;

import com.chinadrtv.logistics.dal.model.Menberlive;
import com.chinadrtv.logistics.model.ContactInfo;
import com.chinadrtv.logistics.service.ContactMenberliveService;
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

import java.util.List;


/**
 * Created by Net on 14-12-24.
 */
@Controller
@RequestMapping({"/Menberlive"})
public class MenberController
{
    private static final Logger logger = LoggerFactory.getLogger(MenberController.class);

    @Autowired
    private ContactMenberliveService contactMenberliveService;

    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public List<Menberlive> query(@RequestBody ContactInfo contactinfo) {
        logger.info("begin query");
        if ((contactinfo == null) || (StringUtils.isEmpty(contactinfo.getContactId())))
        {
            logger.error("contact id is null");
            return null;
        }

        logger.info("contact query id:" + contactinfo.getContactId());

        Long startStamp = Long.valueOf(System.currentTimeMillis());
        try
        {
            return this.contactMenberliveService.queryMenberlive(contactinfo.getContactId());
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