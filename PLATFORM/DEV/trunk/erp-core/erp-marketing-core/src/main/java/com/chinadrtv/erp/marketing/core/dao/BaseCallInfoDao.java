package com.chinadrtv.erp.marketing.core.dao;

import com.chinadrtv.erp.model.marketing.BaseCallInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public interface BaseCallInfoDao {
    List<BaseCallInfo> getActiveBaseCalls();
    void excludeCallNumber(String callerId);
}
