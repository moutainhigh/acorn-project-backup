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
 * @version $Id: DatabaseOperateResponse.java, v 0.1 2013-8-8 上午10:46:33 xieen Exp $
 */
public class DatabaseOperateResponse extends AbstractResponse {

    private static final long serialVersionUID = 1375928503962L;

    private String            databaseOperateRsp;

    public String getDatabaseOperateRsp() {
        return databaseOperateRsp;
    }

    public void setDatabaseOperateRsp(String databaseOperateRsp) {
        this.databaseOperateRsp = databaseOperateRsp;
    }

    
}
