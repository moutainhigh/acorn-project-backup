package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackLogSpecification;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * 话务分配历史
 * User: gaudi.gao
 * Date: 13-8-9
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public interface CallbackLogService {
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

    /**
     * 导出记录
     * @param specification
     * @return
     */
    HSSFWorkbook exportCallbackLogs(CallbackLogSpecification specification);
}
