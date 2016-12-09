package com.chinadrtv.scheduler.common.integration.ws;

import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateResponseLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusResponseLocal;

/**
 * 
 * @author xieen
 * @version $Id: WebServiceUtilClient.java, v 0.1 2013-8-8 下午3:28:16 xieen Exp $
 */
public interface WebServiceUtilClient {

    public QuartzJobStatusResponseLocal doInvokeWs(String hostName, QuartzJobStatusRequestLocal qjsr);

    public DatabaseOperateResponseLocal doDataInvokeWs(DatabaseOperateRequestLocal dor);

}
