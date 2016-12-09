/*
 * @(#)CampaignApiServiceImpl.java 1.0 2013-5-10上午10:56:46
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.JobRelationexDao;
import com.chinadrtv.erp.marketing.core.dao.ObAssignHistDao;
import com.chinadrtv.erp.marketing.core.dao.ObContactDao;
import com.chinadrtv.erp.marketing.core.dao.ObContactHistDao;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.dto.JobsRelationex;
import com.chinadrtv.erp.marketing.core.jms.QueueConsumer;
import com.chinadrtv.erp.marketing.core.jms.QueueMessageProducer;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.agent.ObAssignHist;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.model.agent.ObContactHist;
import com.chinadrtv.erp.util.DateUtil;

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
@Service("jobRelationexService")
public class JobRelationexServiceImpl implements JobRelationexService {

	private static final Logger logger = LoggerFactory.getLogger(JobRelationexServiceImpl.class);

	@Autowired
	private JobRelationexDao jobRelationexDao;
	
	@Autowired
	private ObAssignHistDao obAssignHistDao;
	
	@Autowired
	private ObContactDao obContactDao;
	
	@Autowired
	private ObContactHistDao obContactHistDao;
	
	@Autowired
	private QueueConsumer queueConsumer;
	
	@Autowired
	private QueueMessageProducer messageProducer;

	/**
	* <p>Title: queryJobRelationex</p>
	* <p>Description:查询坐席取数规则列表 </p>
	* @param userId
	* @param userType
	* @param jobId
	* @return
	*/ 
	public List<JobsRelationex> queryJobRelationex(String userId, String userType, String jobId) {

		List<JobsRelationex> result = jobRelationexDao.queryJobRelationex(userId, userType, jobId);
		
		Integer assignHist = 0;
		Integer currentHist = 0;
		for(JobsRelationex jobsRelateionex:result){
			if(jobsRelateionex.getTop()!=null){
				assignHist = jobRelationexDao.queryAssignHist(userId, jobsRelateionex.getJobId(), jobsRelateionex.getDays());
			}else{
				assignHist = jobRelationexDao.queryAssignHist(userId, jobsRelateionex.getJobId(), 1);
			}
			jobsRelateionex.setUsedNum(assignHist);
			currentHist = jobRelationexDao.queryCurrentAssignHist(userId, jobsRelateionex.getJobId());
			jobsRelateionex.setCurrentNum(currentHist);
		}
		return result;
	}

	
	/**
	 *
	* <p>Title: submitAssignHist</p>
	* <p>Description: 提交取数</p>
	* @param assignHist
	 * @throws ServiceException 
	 */
	public void submitAssignHist(ObAssignHist assignHist) throws ServiceException {
		ObContact obContact = obContactDao.get(assignHist.getPdCustomerId());
		
		if(obContact == null){
			throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+assignHist.getPdCustomerId()+"不存在");
		}else if(obContact.getStatus()!=null &&
				obContact.getStatus().equals(Constants.OBCONTACT_STATUS_ASSIGNED)){
			throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+assignHist.getPdCustomerId()+"已经被领用");
		}
		
		obContact.setStatus(Constants.OBCONTACT_STATUS_ASSIGNED);
		obContactDao.saveOrUpdate(obContact);
		obAssignHistDao.save(assignHist);
	}


	/**
	* <p>Title: getSeqNextValue</p>
	* <p>Description: </p>
	* @return
	*/ 
	public Long getSeqNextValue() {
		return obAssignHistDao.getSeqNextValue();
	}


	/* (非 Javadoc)
	* <p>Title: assignHist</p>
	* <p>Description: </p>
	* @param jobId
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#assignHist(java.lang.String)
	*/ 
	@Override
	public AssignMessage assignHist(ObAssignHist assignHist) throws ServiceException {

		AssignMessage message = null;
		long start =System.currentTimeMillis();
		try {
			message = queueConsumer.receive(assignHist.getJobId());
			
			long end = System.currentTimeMillis();
			
			logger.info("取数用时="+(end-start));
			if(message == null){
				message = new AssignMessage();
				message.setJobId(assignHist.getJobId());
				message = messageProducer.send(message);
				
				if(message!=null && message.getStatus()==Constants.OBCONTACT_GET_SUCCESS){
					message = queueConsumer.receive(assignHist.getJobId());
				}
			}
			
			if(!StringUtils.isEmpty(message.getPdCustomerId())){
				
				ObContact obContact = obContactDao.get(message.getPdCustomerId());
				
				if(obContact == null){
					throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+message.getPdCustomerId()+"不存在");
				}else if(obContact.getStatus()!=null &&
						obContact.getStatus().equals(Constants.OBCONTACT_STATUS_ASSIGNED)){
					throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+message.getPdCustomerId()+"已经被领用");
				}
				obContact.setAssignagent(assignHist.getAgent());
				obContact.setAssigntime(new Date());
				obContact.setUpdatetime(new Date());
				obContact.setStatus(Constants.OBCONTACT_STATUS_ASSIGNED);
				obContactDao.saveOrUpdate(obContact);
				
				assignHist.setAssignTime(new Date());
				assignHist.setContactId(message.getBatchId());
				assignHist.setQueueId(message.getQueueId());
				assignHist.setPdCustomerId(message.getPdCustomerId());
				assignHist.setContactId(message.getContactId());
				obAssignHistDao.save(assignHist);
				
			}
		} catch (JMSException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
			throw new ServiceException("取数失败");
		}
		return message;
	}


	/* (非 Javadoc)
	* <p>Title: queryUnprocessed</p>
	* <p>Description: </p>
	* @param userId
	* @param jobId
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryUnprocessed(java.lang.String, java.lang.String)
	*/ 
	@Override
	public List<AssignMessage> queryUnprocessed(String userId, String jobId) {
		return jobRelationexDao.queryUnprocessed(userId, jobId);
	}


	/* (非 Javadoc)
	* <p>Title: dialContact</p>
	* <p>Description: </p>
	* @param pdCustomerId
	* @param agent
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#dialContact(java.lang.String, java.lang.String)
	*/ 
	@Override
	public void dialContact(String pdCustomerId, String agent) throws ServiceException {

		ObContact obContact = obContactDao.get(pdCustomerId);
		
		if(obContact == null){
			throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+pdCustomerId+"不存在");
		}
		
		obContact.setUpdatetime(new Date());
		obContact.setPd_dialcnt(obContact.getPd_dialcnt()!=null?obContact.getPd_dialcnt()+1:1);
		obContact.setCurqueuedialcnt(obContact.getCurqueuedialcnt()!=null?obContact.getCurqueuedialcnt()+1:1);
		obContactDao.saveOrUpdate(obContact);
		
		ObContactHist obContactHist = obContactHistDao.findById(pdCustomerId);
		if(obContactHist!=null){
			obContactHist.setUpdatetime(new Date());
			obContactHist.setPd_dialcnt(obContact.getPd_dialcnt()!=null?obContact.getPd_dialcnt()+1:1);
			obContactHist.setCurqueuedialcnt(obContact.getCurqueuedialcnt()!=null?obContact.getCurqueuedialcnt()+1:1);
		}else{
			obContactHist = new ObContactHist();
			try {
				BeanUtils.copyProperties(obContactHist, obContact);
			} catch (Exception e) {
				throw new ServiceException(ExceptionConstant.SERVICE_USER_UPDATE_EXCEPTION,"客户id="+pdCustomerId+"拨打保存失败");
			} 
		}
		obContactHistDao.saveOrUpdate(obContactHist);
	}


	/* (非 Javadoc)
	* <p>Title: queryJob</p>
	* <p>Description: </p>
	* @param deptNum
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryJob(java.lang.String)
	*/ 
	@Override
	public List<AgentJobs> queryJob(String agentId) {
		return jobRelationexDao.queryJob(agentId);
	}


	/* (非 Javadoc)
	* <p>Title: queryJobGroup</p>
	* <p>Description: </p>
	* @param jobId
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryJobGroup(java.lang.String)
	*/ 
	@Override
	public List<Grp> queryJobGroup(String dept,String jobId) {
		return jobRelationexDao.queryJobGroup(dept,jobId);
	}
	
//	/* (非 Javadoc)
//	* <p>Title: queryJob</p>
//	* <p>Description: </p>
//	* @param deptNum
//	* @return
//	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryJob(java.lang.String)
//	*/ 
//	@Override
//	public List<AgentJobs> queryJob(String deptNum) {
//		return jobRelationexDao.queryJob(deptNum);
//	}
//
//
//	/* (非 Javadoc)
//	* <p>Title: queryJobGroup</p>
//	* <p>Description: </p>
//	* @param jobId
//	* @return
//	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryJobGroup(java.lang.String)
//	*/ 
//	@Override
//	public List<Grp> queryJobGroup(String jobId) {
//		return jobRelationexDao.queryJobGroup(jobId);
//	}

	public static void main(String arg[]){
		long l = 1380270240000l;
		Date t = new Date(l);
		try {
			System.out.println(DateUtil.date2FormattedString(t, "yyyy-MM-dd HH:mm:ss"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
	}


	/* (非 Javadoc)
	* <p>Title: queryAllJob</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.JobRelationexService#queryAllJob()
	*/ 
	@Override
	public List<AgentJobs> queryAllJob() {
		return jobRelationexDao.queryAllJob();
	}
	
}
