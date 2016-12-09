/*
 * @(#)NamesService.java 1.0 2013-1-22下午2:20:10
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;

import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.model.agent.JobsRelation;

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
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:20:10 
 * 
 */
public interface JobRelationService {

	/**
	* @Description: TODO
	* @param uid
	* @return
	* @return List<JobsRelation>
	* @throws
	*/ 
	List<JobsRelation> queryList();

	/**
	 * 
	* @Description: 查询所有jobId
	* @return
	* @return List<AgentJobs>
	* @throws
	 */
	public List<AgentJobs> queryAllJob();
}
