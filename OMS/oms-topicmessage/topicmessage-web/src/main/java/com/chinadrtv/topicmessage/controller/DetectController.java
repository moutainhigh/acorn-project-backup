package com.chinadrtv.topicmessage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping({ "/topicmessage" })
public class DetectController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DetectController.class);

    @Autowired
    private Date lastHandleTime;


    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @RequestMapping(value = "/lastHandleTime", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getLastHandleTime(){
        String temp = df.format(lastHandleTime);
        logger.info("lastHandleTime:"+lastHandleTime);
        return temp;
    }

}
