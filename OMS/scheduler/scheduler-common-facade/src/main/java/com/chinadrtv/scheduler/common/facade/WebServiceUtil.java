/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.facade;

import javax.jws.WebService;

import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateRequest;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateResponse;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusRequest;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusResponse;

/**
 * 
 * @author zhaobo
 * @version $Id: BaseWebService.java, v 0.1 2013-7-25 下午2:36:19 DELL Exp $
 */
@WebService
public interface WebServiceUtil {

    public QuartzJobStatusResponse doInvokeWs(QuartzJobStatusRequest qjsr);

    public DatabaseOperateResponse doDataInvokeWs(DatabaseOperateRequest dor);
}
