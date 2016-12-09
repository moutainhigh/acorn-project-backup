package com.chinadrtv.erp.marketing.core.service;

import com.chinadrtv.erp.model.marketing.BaseCallInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public interface BaseCallInfoService {
    List<BaseCallInfo> getActiveBaseCalls(long startup);
    void excludeCallNumber(long startup, String callerId);
}
