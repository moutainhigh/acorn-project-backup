/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.facade.bean;

import java.util.HashMap;

import com.chinadrtv.scheduler.common.facade.abs.AbstractRequest;

/**
 * 
 * @author xieen
 * @version $Id: WsdlRequest.java, v 0.1 2013-8-8 上午10:24:37 xieen Exp $
 */
public class QuartzJobStatusRequest extends AbstractRequest {

    private static final long       serialVersionUID = 1375928735479L;

    private HashMap<String, String> param;

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }

    
}
