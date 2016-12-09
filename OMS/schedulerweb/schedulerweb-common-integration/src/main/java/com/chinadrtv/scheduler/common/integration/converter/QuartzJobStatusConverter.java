/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.integration.converter;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusRequest;
import com.chinadrtv.scheduler.common.facade.bean.QuartzJobStatusResponse;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusRequestLocal;
import com.chinadrtv.scheduler.common.integration.bean.QuartzJobStatusResponseLocal;
import com.chinadrtv.util.XmlUtil;

/**
 * 
 * @author xieen
 * @version $Id: QuartzJobStatusRequestConverter.java, v 0.1 2013-8-8 下午12:58:08 xieen Exp $
 */
public class QuartzJobStatusConverter {
    public static QuartzJobStatusRequestLocal getQuartzJobStatusRequestLocal(QuartzJobStatusRequest quartzJobStatusRequest) {
        if (quartzJobStatusRequest == null)
            return null;
        QuartzJobStatusRequestLocal quartzJobStatusRequestLocal = new QuartzJobStatusRequestLocal();
        BeanUtils.copyProperties(quartzJobStatusRequest, quartzJobStatusRequestLocal);
        return quartzJobStatusRequestLocal;
    }

    public static QuartzJobStatusRequest getQuartzJobStatusRequest(QuartzJobStatusRequestLocal quartzJobStatusRequestLocal) {
        if (quartzJobStatusRequestLocal == null)
            return null;
        QuartzJobStatusRequest quartzJobStatusRequest = new QuartzJobStatusRequest();
        BeanUtils.copyProperties(quartzJobStatusRequestLocal, quartzJobStatusRequest);
        return quartzJobStatusRequest;
    }

    public static QuartzJobStatusResponseLocal getQuartzJobStatusResponseLocal(QuartzJobStatusResponse quartzJobStatusResponse) {
        if (quartzJobStatusResponse == null)
            return null;
        QuartzJobStatusResponseLocal quartzJobStatusResponseLocal = new QuartzJobStatusResponseLocal();
        BeanUtils.copyProperties(quartzJobStatusResponse, quartzJobStatusResponseLocal);
        return quartzJobStatusResponseLocal;
    }

    public static QuartzJobStatusResponse getQuartzJobStatusResponse(QuartzJobStatusResponseLocal quartzJobStatusResponseLocal) {
        if (quartzJobStatusResponseLocal == null)
            return null;
        QuartzJobStatusResponse quartzJobStatusResponse = new QuartzJobStatusResponse();
        BeanUtils.copyProperties(quartzJobStatusResponseLocal, quartzJobStatusResponse);
        return quartzJobStatusResponse;
    }

}
