package com.chinadrtv.erp.distribution.service;

import java.util.List;

import com.chinadrtv.erp.uc.dto.Ivrdist;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public interface WilcomCallDetailService {
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
    void assignIvrCount(CallbackSpecification specification, List<AssignAgentDto> agents);

    /**
     * 强制IVR任务完成
     * @param caseId
     */
    void ivrComplete(String caseId, String userId);
    /**
     * 取数分配
     * @param specification
     * @param ivrdist
     */
    void assignIvrhist(CallbackSpecification specification, Ivrdist ivrdist) throws ServiceException;
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
    void assignAbandonCount(CallbackSpecification specification, List<AssignAgentDto> agents);

 }

