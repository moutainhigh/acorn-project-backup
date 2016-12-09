/*
 * @(#)CampaignReceiverService.java 1.0 2013年8月28日上午10:59:51
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年8月28日 上午10:59:51 
 * 
 */
public interface CampaignReceiverService extends GenericService<CampaignReceiver, Long> {

	
	/**
	 * <p>根据组查询待分配的任务</p>
	 * @param cr
	 * @return Model list
	 */
	List<CampaignReceiver> queryAssignmentByGroup(CampaignReceiverDto cr);
	
	/**
	 * <p>分配到坐席</p>
	 * @param cr
	 * @param agentList Map<String, Object> assignAgent, assignQty
	 */
	void assignToAgent(CampaignReceiverDto crDto, List<Map<String, Object>> agentList, String assignMode) throws Exception;



    /**
     * <p>获取分配到组的数量</p>
     *  dto ==>Long campaignId,String batchCode,String jobId
     * @param dto
     * @return
     */
    int getAssigntotle(CampaignReceiverDto dto);

    /**
     *@param alloStrategy 分配策略 cycle order
     * @param dto
     */
    void assignToGroup(CampaignReceiverDto dto ,String groupIds,String groupNums,AgentUser user, String alloStrategy);
    
    /**
     * <p>按批次回收已分配到坐席未拨打的数据</p>
     * @param batchNo
     */
    void recycleBatchData(String batchCode) throws Exception;
    
    /**
     * <p>根据bpmInstId 查询 CampaignReceiver</p>
     * @param bpmInstId 任务ID
     * @return CampaignReceiver
     */
    CampaignReceiver queryByBpmInstId(long bpmInstId);
    
    /**
     * <p>根据批次查询未执行的任务数</p>
     * @param batchCode
     * @return Map<String, Object>  totalQty 批次总数，unStartQty 已分配未拨打（可回收）数量
     */
    Map<String, Integer> queryBatchProgress(String batchCode);
    
    
    /**
     * <p>标记CampaignReceiver状态为已执行</p>
     * @param taskId
     * @throws Exception
     */
    void signStatus(Long taskId) throws Exception;

}
