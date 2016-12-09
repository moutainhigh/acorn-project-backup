package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackLogSpecification;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CallbackLogDao extends GenericDao<CallbackLog, Long> {
    /**
     * 获取分配历史数量
     * @param specification
     * @return
     */
    Long findCallbackLogCount(CallbackLogSpecification specification);
    /**
     * 获取分配历史
     * @param specification
     * @return
     */
    List<CallbackLog> findCallbackLogs(CallbackLogSpecification specification, Integer index, Integer size);
}
