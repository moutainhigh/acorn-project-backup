/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.integration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.runtime.startup.impl.RemoteConfigInfoImpl;

/**
 * 
 * @author xieen
 * @version $Id: JmsReceiverListener.java, v 0.1 2013-7-23 下午8:50:30 xieen Exp $
 */
public class JmsReceiverListener extends JmsListener<HashMap<String, String>> {

    private Logger           logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");

    private RemoteConfigInfoImpl remoteConfigInfo;

    public void setRemoteConfigInfo(RemoteConfigInfoImpl remoteConfigInfo) {
        this.remoteConfigInfo = remoteConfigInfo;
    }

    /** 
     * @see com.pinganfu.runtime.jms.receive.JmsListener#messageHandler(javax.jms.Message)
     */
    @Override
    public void messageHandler(HashMap<String, String> msg) {
        logger.info("receive jms message, reload config information...");
        try {
            remoteConfigInfo.execute();
        } catch (Exception e) {
            logger.info("Reload config information error", e);
        }
    }

}
