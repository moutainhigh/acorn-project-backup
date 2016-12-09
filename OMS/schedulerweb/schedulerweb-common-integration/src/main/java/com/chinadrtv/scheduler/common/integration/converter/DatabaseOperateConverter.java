/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.integration.converter;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateRequest;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateResponse;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateResponseLocal;

/**
 * 
 * @author xieen
 * @version $Id: DatabaseOperateRequestConverter.java, v 0.1 2013-8-8 下午12:57:17 xieen Exp $
 */
public class DatabaseOperateConverter {
    public static DatabaseOperateRequest getDatabaseOperateRequest(DatabaseOperateRequestLocal databaseOperateRequestLocal) {
        if (databaseOperateRequestLocal == null)
            return null;
        DatabaseOperateRequest databaseOperateRequest = new DatabaseOperateRequest();
        BeanUtils.copyProperties(databaseOperateRequestLocal, databaseOperateRequest);
        return databaseOperateRequest;
    }

    public static DatabaseOperateRequestLocal getDatabaseOperateRequestLocal(DatabaseOperateRequest databaseOperateRequest) {
        if (databaseOperateRequest == null)
            return null;
        DatabaseOperateRequestLocal databaseOperateRequestLocal = new DatabaseOperateRequestLocal();
        BeanUtils.copyProperties(databaseOperateRequest, databaseOperateRequestLocal);
        return databaseOperateRequestLocal;
    }

    public static DatabaseOperateResponse getDatabaseOperateResponse(DatabaseOperateResponseLocal databaseOperateResponseLocal) {
        if (databaseOperateResponseLocal == null)
            return null;
        DatabaseOperateResponse databaseOperateResponse = new DatabaseOperateResponse();
        BeanUtils.copyProperties(databaseOperateResponseLocal, databaseOperateResponse);
        return databaseOperateResponse;
    }

    public static DatabaseOperateResponseLocal getDatabaseOperateResponseLocal(DatabaseOperateResponse databaseOperateResponse) {
        if (databaseOperateResponse == null)
            return null;
        DatabaseOperateResponseLocal databaseOperateResponseLocal = new DatabaseOperateResponseLocal();
        BeanUtils.copyProperties(databaseOperateResponse, databaseOperateResponseLocal);
        return databaseOperateResponseLocal;
    }
}
