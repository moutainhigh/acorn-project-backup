package com.chinadrtv.scheduler.common.integration.ws.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.runtime.ws.BaseWSClient;
import com.chinadrtv.scheduler.common.facade.WebServiceUtil;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateRequest;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateResponse;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusRequest;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusResponse;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.DatabaseOperateResponseLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusResponseLocal;
import com.chinadrtv.scheduler.common.integration.converter.DatabaseOperateConverter;
import com.chinadrtv.scheduler.common.integration.converter.QuartzJobStatusConverter;
import com.chinadrtv.scheduler.common.integration.ws.WebServiceUtilClient;
import com.chinadrtv.scheduler.common.util.WebServiceAddressUtil;

/**
 * 
 * @author xieen
 * @version $Id: WebServiceUtilClientImpl.java, v 0.1 2013-8-8 下午3:31:24 xieen Exp $
 */
public class WebServiceUtilClientImpl extends BaseWSClient<WebServiceUtil> implements
                                                                          WebServiceUtilClient {
    private static final Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");

    public QuartzJobStatusResponseLocal doInvokeWs(String hostName, QuartzJobStatusRequestLocal qjsr) {
        QuartzJobStatusRequest quartzJobStatusRequest = QuartzJobStatusConverter
            .getQuartzJobStatusRequest(qjsr);
        String quartzWebService = WebServiceAddressUtil.getHostWebServiceAddress(hostName);
       
        String url = SystemContext.get("quartzWebService");
        if (StringUtils.isBlank(url)) {
            url = System.getProperty("quartzWebService");
        }
        quartzWebService = url;
        
        logger.info("quartzWebService: " + quartzWebService);
        
        WebServiceUtil webServiceUtil = this.getAppointRemoteBean(quartzWebService);
        QuartzJobStatusResponse quartzJobStatusResponse = webServiceUtil
            .doInvokeWs(quartzJobStatusRequest);
        QuartzJobStatusResponseLocal quartzJobStatusResponseLocal = QuartzJobStatusConverter
            .getQuartzJobStatusResponseLocal(quartzJobStatusResponse);
        return quartzJobStatusResponseLocal;
    }

    public DatabaseOperateResponseLocal doDataInvokeWs(DatabaseOperateRequestLocal dor) {
        DatabaseOperateRequest databaseOperateRequest = DatabaseOperateConverter
            .getDatabaseOperateRequest(dor);
        String quartzWebService = WebServiceAddressUtil.getRandomWebServiceAddress();
        //quartzWebService = "http://localhost:8080/services/webServiceUtilRemote";
        
        String url = SystemContext.get("quartzWebService");
        if (StringUtils.isBlank(url)) {
            url = System.getProperty("quartzWebService");
        }
        quartzWebService = url;
        
        logger.info("quartzWebService: " + quartzWebService);
        
        WebServiceUtil webServiceUtil = this.getAppointRemoteBean(quartzWebService);
        DatabaseOperateResponse databaseOperateResponse = webServiceUtil
            .doDataInvokeWs(databaseOperateRequest);
        DatabaseOperateResponseLocal databaseOperateResponseLocal = DatabaseOperateConverter
            .getDatabaseOperateResponseLocal(databaseOperateResponse);
        return databaseOperateResponseLocal;
    }

}
