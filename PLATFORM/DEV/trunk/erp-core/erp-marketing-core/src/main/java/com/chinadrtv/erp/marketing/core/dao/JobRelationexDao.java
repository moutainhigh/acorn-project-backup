/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.dto.JobsRelationex;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.Grp;
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
 * @since 2013-1-22 下午2:21:44 
 * 
 */
public interface JobRelationexDao extends GenericDao<JobsRelation, java.lang.String>{

	/**
	 * 
	* @Description: 查询取数规则
	* @param userId
	* @param userType
	* @param jobId
	* @return
	* @return List<JobsRelationex>
	* @throws
	 */
	public List<JobsRelationex> queryJobRelationex(String userId,String userType, String jobId) ;
	
	
	/**
	 * 
	* @Description: 查询已分配数
	* @param userId
	* @param jobId
	* @param days
	* @return
	* @return Integer
	* @throws
	 */
	public Integer queryAssignHist(String userId, String jobId,Integer days);
	
	/**
	 * 
	* @Description: 查询当前数量
	* @param userId
	* @param jobId
	* @return
	* @return Integer
	* @throws
	 */
	public Integer queryCurrentAssignHist(String userId, String jobId);
	
	/**
	 * 
	* @Description: 查看座席是否还有未处理资料
	* @param userId
	* @param jobId
	* @return
	* @return Integer
	* @throws
	 */
	public List<AssignMessage> queryUnprocessed(String userId, String jobId);
	
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
	public List<AgentJobs> queryJob(String agengId);
	
	/**
	 * 
	* @Description: 查询业务编号下的部门列表
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
