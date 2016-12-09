package com.chinadrtv.scheduler.common.integration;

import java.util.HashMap;

/**
 * 
 * @author xieen
 * @version $Id: JmsIntegration.java, v 0.1 2013-8-5 下午7:45:08 xieen Exp $
 */
public interface JmsIntegration {

    public void sendJmsMessage(HashMap<String, Object> jsmMessage);

}
