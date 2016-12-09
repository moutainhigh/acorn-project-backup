/*
 * @(#)CampaignApiService.java 1.0 2013-5-10上午10:55:49
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.dto.JobsRelationex;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.agent.ObAssignHist;

/**
 * 
 * <dl>
 *    <dt><b>Title:查询和提交取数接口</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:查询和提交取数接口</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-15 下午5:45:47 
 *
 */
public interface JobRelationexService {
	
	/**
	* <p>Title: queryJobRelationex</p>
	* <p>Description:查询坐席取数规则列表 </p>
	* @param userId
	* @param userType
	* @param jobId
	* @return
	*/ 
	public List<JobsRelationex> queryJobRelationex(String userId,String userType, String jobId);
	
	/**
	 *
	* <p>Title: submitAssignHist</p>
	* <p>Description: 提交取数</p>
	* @param assignHist
	 */
	public void submitAssignHist(ObAssignHist assignHist) throws ServiceException ;
	
	
	/**
	 * 
	* @Description: 取数接口    其中jobId/agent/defGrp/dept传值
	* @param jobId
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public AssignMessage assignHist(ObAssignHist assignHist) throws ServiceException ;
	
	/**
	 * 
	* @Description: 返回下一个序列值
	* @return
	* @return Long
	* @throws
	 */
	public Long getSeqNextValue();
	
	/**
	 * 
	* @Description: 查看座席是否还有未处理资料,如果不为空，返回的List中的AssignMessage对象中的contactid为未处理客户
	* 
	* @param userId
	* @param jobId
	* @return
	* @return List<AssignMessage>
	* @throws
	 */
	public List<AssignMessage> queryUnprocessed(String userId, String jobId);
	
	
	/**
	 * 
	* @Description: 拨打取数，对取数进行拨打后，才能取下一条
	* @param pdCustomerId
	* @param agent
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public void dialContact(String pdCustomerId,String agent) throws ServiceException;
	
	/**
	 * 
	* @Description: 查询所有jobId
	* @return
	* @return List<AgentJobs>
	* @throws
	 */
	public List<AgentJobs> queryAllJob();
	
	/**
	 * 
	* @Description:查询部门可以看到的业务编号列表
	* @param deptNum
	* @return
	* @return List<AgentJobs>
	* @throws
	 */
	public List<AgentJobs> queryJob(String agentId);
	
	/**
	 * 
	* @Description: 查询业务编号下的组列表
	* @param jobId
	* @return
	* @return List<Grp>
	* @throws
	 */
	public List<Grp> queryJobGroup(String dept,String jobId);
	
//	/**
//	 * 
//	* @Description:查询部门可以看到的业务编号列表
//	* @param deptNum
//	* @return
//	* @return List<AgentJobs>
//	* @throws
//	 */
//	public List<AgentJobs> queryJob(String deptNum);
//	
//	/**
//	 * 
//	* @Description: 查询业务编号下的部门列表
//	* @param jobId
//	* @return
//	* @return List<Grp>
//	* @throws
//	 */
//	public List<Grp> queryJobGroup(String jobId);
}
