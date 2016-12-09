package com.chinadrtv.erp.distribution.service;

import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-15
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public interface AgentCallbackService {
    /**
     * 获取可分配电话数量
     * @param specification
     * @return
     */
    Long findCallbackCount(CallbackSpecification specification);
    /**
     * 取数分配
     * @param specification
     * @param agents
     */
    void assignCallbackCount(CallbackSpecification specification, List<AssignAgentDto> agents);
}
