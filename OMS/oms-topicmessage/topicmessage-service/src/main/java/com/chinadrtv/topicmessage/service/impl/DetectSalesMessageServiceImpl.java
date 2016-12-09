package com.chinadrtv.topicmessage.service.impl;

import com.chinadrtv.topicmessage.service.DetectSalesMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-13
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 * sales消息监听服务
 */
@Service("detectSalseMessageService")
public class DetectSalesMessageServiceImpl implements DetectSalesMessageService {
    private static Logger logger  = LoggerFactory.getLogger(DetectSalesMessageServiceImpl.class);

    @Value("${topsale_url}")
    private String topsaleUrl;
    @Value("${syncLeadWithTask_url}")
    private String syncLeadWithTaskUrl;
    @Value("${delayLeadAndTask_url}")
    private String delayLeadAndTaskUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void detectSalesTopicMessage() {
        logger.info("DetectSalesMessageService begin....");
        try{
            Object topsale = restTemplate.postForObject(topsaleUrl, null, Object.class);
            logger.info("topsale response result= "+topsale);
            Object syncLeadWithTask  =   restTemplate.getForObject(syncLeadWithTaskUrl,Object.class);
            logger.info("syncLeadWithTask response result= "+syncLeadWithTask);
        }catch (Exception e){
            logger.error("DetectSalesMessageService Exception:",e);
        }
       logger.info("DetectSalesMessageService end........");
    }

    @Override
    public void delaySalesTasks() {
        logger.info("delay sales task begin....");
        try{
            Object delayLeadWithTask  =   restTemplate.getForObject(delayLeadAndTaskUrl,Object.class);
            logger.info("delay sales task result= "+delayLeadWithTask);
        }catch (Exception e){
            logger.error("delay sales task Exception:",e);
        }
        logger.info("delay sales task end........");
    }
}
