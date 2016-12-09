/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.facade.bean;

import com.chinadrtv.scheduler.common.facade.abs.AbstractRequest;

/**
 * 
 * @author xieen
 * @version $Id: DatabaseOperateRequest.java, v 0.1 2013-8-8 上午10:46:14 xieen Exp $
 */
public class DatabaseOperateRequest extends AbstractRequest {

    private static final long serialVersionUID = 1375928503962L;

    private String            param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    
}
