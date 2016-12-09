package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-17
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public interface WilcomCallDetailDao {
    /**
     * 获取指定时段电话数(按电话去重)
     * @param specification
     * @return
     */
    Long findIvrCount(CallbackSpecification specification);
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
    List<WilcomCallDetail> findIvrDetails(CallbackSpecification specification);
    /**
     * 获取指定时段电话数(按电话去重)
     * @param specification
     * @return
     */
    Long findAbandonCount(CallbackSpecification specification);
    /**
     * 获取放弃电话号码
     */
    List<String> findAbandonNumbers(CallbackSpecification specification);

    /**
     * 获取放弃电话明细
     * @param specification
     * @return
     */
    List<WilcomCallDetail> findAbandonDetails(CallbackSpecification specification);
}
