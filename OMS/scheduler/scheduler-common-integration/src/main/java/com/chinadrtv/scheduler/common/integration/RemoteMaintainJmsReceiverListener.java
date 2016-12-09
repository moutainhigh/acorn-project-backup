package com.chinadrtv.scheduler.common.integration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.runtime.startup.impl.RemoteConfigInfoImpl;


public class RemoteMaintainJmsReceiverListener extends JmsListener<HashMap<String, String>> {

    private Logger           logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");

    private RemoteConfigInfoImpl remoteMaintainInfo;

    public void setRemoteMaintainInfo(RemoteConfigInfoImpl remoteMaintainInfo) {
        this.remoteMaintainInfo = remoteMaintainInfo;
    }

    @Override
    public void messageHandler(HashMap<String, String> msg) {
        logger.info("receive jms message, reload config information...");
        try {
            this.remoteMaintainInfo.execute();
        } catch (Exception e) {
            logger.info("Reload config information error", e);
        }
    }

}
