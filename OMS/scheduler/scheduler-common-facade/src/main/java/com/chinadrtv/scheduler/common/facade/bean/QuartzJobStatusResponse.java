/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.facade.bean;

import com.chinadrtv.scheduler.common.facade.abs.AbstractResponse;

/**
 * 
 * @author xieen
 * @version $Id: WsdlResponse.java, v 0.1 2013-8-8 上午10:05:53 xieen Exp $
 */
public class QuartzJobStatusResponse extends AbstractResponse {

    private static final long serialVersionUID = 1375928503962L;

    private String            quartzJobStatusList;

    public String getQuartzJobStatusList() {
        return quartzJobStatusList;
    }

    public void setQuartzJobStatusList(String quartzJobStatusList) {
        this.quartzJobStatusList = quartzJobStatusList;
    }

    
}
