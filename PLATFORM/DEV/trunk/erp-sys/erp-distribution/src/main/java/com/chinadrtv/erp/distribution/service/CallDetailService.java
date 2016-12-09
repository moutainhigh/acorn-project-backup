package com.chinadrtv.erp.distribution.service;

import com.chinadrtv.erp.distribution.dto.CallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public interface CallDetailService {
    /**
     * 获取可分配电话数量
     * @param specification
     * @return
     */
    Long findIvrCount(CallbackSpecification specification);
    /**
     * 取数分配
     * @param specification
     * @param agents
     */
    List<String> assignIvrCount(CallbackSpecification specification, List<AssignAgentDto> agents);
    /**
     * 获取可分配放弃电话数量
     * @param specification
     * @return
     */
    Long findAbandonCount(CallbackSpecification specification);
    /**
     * "放弃"取数分配
     * @param specification
     * @param agents
     */
    List<String> assignAbandonCount(CallbackSpecification specification, List<AssignAgentDto> agents);

}
