package com.chinadrtv.yuantong.controller;

import com.chinadrtv.yuantong.common.dal.dao.WmsShipmentHeaderDao;
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
 * Date: 14-2-14
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/yt" })
public class YuantongController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(YuantongController.class);

    @Autowired
    private WmsShipmentHeaderDao wmsShipmentHeaderDao;
    @RequestMapping(value = "/DB/detect", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testFeedback() throws Exception{
        logger.info("begin test connect.......");
        try{
            int temp = wmsShipmentHeaderDao.findShipmentHeader().size();
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = df.format(lastHandleTime);
        logger.info("lastHandleTime:"+lastHandleTime);
        return temp;
    }
}
