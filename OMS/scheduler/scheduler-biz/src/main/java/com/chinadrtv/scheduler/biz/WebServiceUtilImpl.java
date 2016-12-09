/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.scheduler.common.facade.WebServiceUtil;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateRequest;
import com.chinadrtv.scheduler.common.facade.bean.DatabaseOperateResponse;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusRequest;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusResponse;
import com.chinadrtv.scheduler.common.facade.code.RspCode;
import com.chinadrtv.scheduler.service.dboperate.DatabaseOperateService;
import com.chinadrtv.scheduler.service.quartz.QuartzSchedulerService;
import com.chinadrtv.util.XmlUtil;

/**
 * 
 * @author zhaobo
 * @version $Id: BaseWebServiceImpl.java, v 0.1 2013-7-25 下午2:34:15 DELL Exp $
 */
@WebService
public class WebServiceUtilImpl implements WebServiceUtil {
    private Logger                 logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");
    private QuartzSchedulerService quartzSchedulerService;

    private DatabaseOperateService databaseOperateService;

    public void setQuartzSchedulerService(QuartzSchedulerService quartzSchedulerService) {
        this.quartzSchedulerService = quartzSchedulerService;
    }

    public void setDatabaseOperateService(DatabaseOperateService databaseOperateService) {
        this.databaseOperateService = databaseOperateService;
    }

    @Override
    public QuartzJobStatusResponse doInvokeWs(QuartzJobStatusRequest qjsr) {
        QuartzJobStatusResponse quartzJobStatusResponse = new QuartzJobStatusResponse();
        try {
            List<HashMap<String, String>> list = this.quartzSchedulerService.checkQuartzJob();
            quartzJobStatusResponse.setQuartzJobStatusList(XmlUtil.toXml(list));
            quartzJobStatusResponse.setRespCode(RspCode.SUCCESS.getCode());
        } catch (SchedulerException se) {
            logger.warn("获取Quartz JOB状态列表失败!");
            logger.warn("请求参数:"+ReflectionToStringBuilder.toString(qjsr));
            quartzJobStatusResponse.setRespCode(RspCode.ERROR.getCode());
            return quartzJobStatusResponse;
        }
        return quartzJobStatusResponse;
    }

    @Override
    public DatabaseOperateResponse doDataInvokeWs(DatabaseOperateRequest dor) {
        DatabaseOperateResponse databaseOperateResponse = new DatabaseOperateResponse();
        HashMap<String, Object> param = XmlUtil.toObjectByAnnotation(dor.getParam(), HashMap.class);
        try {
            String result = this.databaseOperateService.databaseOperate(param);
            databaseOperateResponse.setRespCode(RspCode.SUCCESS.getCode());
            databaseOperateResponse.setDatabaseOperateRsp(result);
        } catch (Exception e) {
            logger.warn("数据库操作失败!");
            logger.warn("请求参数:"+ReflectionToStringBuilder.toString(dor));
            databaseOperateResponse.setRespCode(RspCode.ERROR.getCode());
            return databaseOperateResponse;
        }

        return databaseOperateResponse;
    }
}
