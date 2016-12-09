package com.chinadrtv.erp.marketing.core.service.impl;

import com.chinadrtv.erp.marketing.core.dao.BaseCallInfoDao;
import com.chinadrtv.erp.marketing.core.service.BaseCallInfoService;
import com.chinadrtv.erp.model.marketing.BaseCallInfo;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Service("baseCallInfoService")
public class BaseCallInfoServiceImpl implements BaseCallInfoService {

    @Autowired
    private BaseCallInfoDao baseCallInfoDao;

    @ReadThroughSingleCache(namespace=NS_ACTIVE_BASE_CALLS,expiration=3600)
    public List<BaseCallInfo> getActiveBaseCalls(@ParameterValueKeyProvider long startup) {
        return baseCallInfoDao.getActiveBaseCalls();
    }

    @InvalidateSingleCache(namespace = NS_ACTIVE_BASE_CALLS)
    public void excludeCallNumber(@ParameterValueKeyProvider long startup, String callerId) {
        baseCallInfoDao.excludeCallNumber(callerId);
    }

    private static final String NS_ACTIVE_BASE_CALLS=  "com.chinadrtv.erp.marketing.core.service.baseCallInfoService.activeBaseCalls";
}
