/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.integration.bean;

import java.util.HashMap;

/**
 * 
 * @author xieen
 * @version $Id: QuartzJobStatusRequestLocal.java, v 0.1 2013-8-8 下午12:01:05 xieen Exp $
 */
public class QuartzJobStatusRequestLocal {

    private HashMap<String, String> param;

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }

}
