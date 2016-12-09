/**
 * 
 *	橡果国际ERP技术支持中心
 * Copyright (c) 2013-2013 Acorn International,Inc. All Rights Reserved.
 */
package com.chinadrtv.losepickmail.biz.jms;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.losepickmail.biz.LosePickMailHandler;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * 
 * @author andrew
 * @version $Id: MailSendTopicListener.java, v 0.1 2013年12月10日 下午3:57:53 andrew Exp $
 */
@SuppressWarnings("rawtypes")
public class LosePickMailTopicListener extends JmsListener{

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LosePickMailTopicListener.class);
    
    @Autowired
    private LosePickMailHandler losePickMailHandler;
    
    /** 
     * @see com.chinadrtv.runtime.jms.receive.JmsListener#messageHandler(java.lang.Object)
     */
    @Override
    public void messageHandler(Object msg) throws Exception {
        
        try {
            losePickMailHandler.doSendCommand();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单漏分拣邮件提醒发送失败", e);
        }
        
    }

}
