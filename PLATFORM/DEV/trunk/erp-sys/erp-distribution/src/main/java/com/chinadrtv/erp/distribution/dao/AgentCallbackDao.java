package com.chinadrtv.erp.distribution.dao;

import com.chinadrtv.erp.model.agent.CallbackEx;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-17
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public interface AgentCallbackDao {
    /**
     * 获取指定时段电话数(按电话去重)
     * @param specification
     * @return
     */
    Long findCallbackCount(CallbackSpecification specification);
    /**
     * 获取指定时段电话
     * @param specification
     * @return
     */
    List<String> findCallbackNumbers(CallbackSpecification specification);
    /**
     * 获取指定时段通话记录
     * @param specification
     * @return
     */
    List<CallbackEx> findCallbackDetails(CallbackSpecification specification);

}
