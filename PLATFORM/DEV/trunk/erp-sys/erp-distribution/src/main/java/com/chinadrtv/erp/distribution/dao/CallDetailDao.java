package com.chinadrtv.erp.distribution.dao;

import com.chinadrtv.erp.distribution.dto.CallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-17
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public interface CallDetailDao {
    /**
     * 获取指定时段电话
     * @param specification
     * @return
     */
    List<String> findIvrNumbers(CallbackSpecification specification);
    /**
     * 获取指定时段通话记录
     * @param specification
     * @return
     */
    List<CallDetail> findIvrDetails(CallbackSpecification specification);
    /**
     * 获取放弃电话号码
     */
    List<String> findAbandonNumbers(CallbackSpecification specification);

    /**
     * 获取放弃电话明细
     * @param specification
     * @return
     */
    List<CallDetail> findAbandonDetails(CallbackSpecification specification);
}
