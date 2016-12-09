/**
 * 
 *	橡果国际ERP技术支持中心
 * Copyright (c) 2013-2013 Acorn International,Inc. All Rights Reserved.
 */
package com.chinadrtv.losepickmail.biz.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.losepickmail.biz.LosePickMailHandler;
import com.chinadrtv.service.oms.LosePickMailService;

/**
 * 
 * @author andrew
 * @version $Id: MailSendHandlerImpl.java, v 0.1 2013年12月10日 下午4:13:03 andrew Exp $
 */
public class LosePickMailHandlerImpl implements LosePickMailHandler{
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LosePickMailHandlerImpl.class);
    
    private AtomicBoolean isRun = new AtomicBoolean(false);
    
    @Autowired
    private LosePickMailService losePickMailService;

    @Override
    public void doSendCommand() {
        
        if(isRun.compareAndSet(false, true)){
            try {
                losePickMailService.sendMailNotice();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("订单漏分拣邮件提醒发送失败", e);
            }finally {
                isRun.set(false);
            }
        }else{
            logger.error("订单漏分拣邮件提醒已在执行中");
        }
    }

    
}
