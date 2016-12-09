/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dao.UsrTaskRecommendDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.StringUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 2013-5-6 上午10:17:37
 * 
 * @version 1.0.0
 * @author yangfei
 * 
 */
@Service("bpmTaskService")
public class CampaignBPMTaskServiceImpl implements CampaignBPMTaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignBPMTaskServiceImpl.class);
	
	@Autowired
	private UsrTaskRecommendDao usrTaskRecommendDao;

	@Autowired
	private BpmProcessService bpmProcessService;

	@Autowired
	private LeadTaskDao leadTaskDao;

	@Autowired
	private CampaignDao campaignDao;

	/**
	 * 
	 */
	public CampaignTaskVO queryMarketingTask(String instId) {
		CampaignTaskVO cvo = null;
		if(StringUtils.isBlank(instId)) {
			return cvo;
		}
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		mTaskDto.setInstId(instId);
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setRows(10);
		List<Object> objs = leadTaskDao.query(mTaskDto, dataModel);
		List<CampaignTaskVO> vos = convertObj2VO(objs);
		if(vos != null && vos.size() > 0) {
			cvo = vos.get(0);
		}
		return cvo;
	}
	
    public LeadTask get(Long id) {
    	LeadTask leadTask = null;
    	try {
    		leadTask = leadTaskDao.get(id);
    	} catch(Exception e) {
    		logger.error("Failed to get the leadTask"+id,e);
    	}
        return leadTask;
    }
	
	public Map<String, Object> queryMarketingTask(CampaignTaskDto mTaskDto,
			DataGridModel dataModel) throws MarketingException{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(mTaskDto.getUserID())) {
				throw new MarketingException("座席ID不能为空", ErrorCode.UserNull);
			}
			if(StringUtils.isBlank(mTaskDto.getStartDate()) && StringUtils.isBlank(mTaskDto.getEndDate())) {
				String startDate = DateUtil.addDays(new Date(), -90);
				String endDate = DateUtil.addDays(new Date(), 0);
				mTaskDto.setStartDate(startDate);
				mTaskDto.setEndDate(endDate);
			}
			if (StringUtils.isBlank(mTaskDto.getInstId()) && DateUtil.checkDateRange(mTaskDto.getStartDate(),
					mTaskDto.getEndDate(), Constants.maxQueryTimeRange)) {
				throw new MarketingException("查询区段过大", ErrorCode.OutOfTimeRange);
			}
			if (StringUtils.isNotBlank(mTaskDto.getEndDate())) {
				String endDate = DateUtil.addDays(mTaskDto.getEndDate(), 1);
				mTaskDto.setEndDate(endDate);
			}
			List<Object> objs = leadTaskDao.query(mTaskDto, dataModel);
			List<CampaignTaskVO> vos = convertObj2VO(objs);
			Integer total = leadTaskDao.queryCount(mTaskDto);
			result.put("rows", vos);
			result.put("total", total);
		} catch (MarketingException e) {
			throw e;
		} catch (ParseException e) {
			throw new MarketingException(e, "date parse errors!",ErrorCode.System);
		} catch (Exception e) {
			throw new MarketingException(e, "System errors!",ErrorCode.Unknown);
		}
		return result;
	}
	
	public List<Map<String, Object>> queryContactTaskHistory(String contactId) {
		return leadTaskDao.queryContactTaskHistory(contactId);
	}

	public Map<String, Object> queryUnStartedCampaignTask(CampaignTaskDto mTaskDto,
			DataGridModel dataModel) throws MarketingException{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(mTaskDto.getUserID())) {
				throw new MarketingException("座席ID不能为空", ErrorCode.UserNull);
			}
			if (StringUtils.isNotBlank(mTaskDto.getEndDate())) {
				String endDate = DateUtil.addDays(mTaskDto.getEndDate(), 1);
				mTaskDto.setEndDate(endDate);
			}
			List<Object> objs = leadTaskDao.query(mTaskDto, dataModel);
			List<CampaignTaskVO> vos = convertObj2VO(objs);
			Integer total = leadTaskDao.queryUnStartedCampaignTaskCount(mTaskDto);
			result.put("rows", vos);
			result.put("total", total);
		} catch (MarketingException e) {
			throw e;
		} catch (ParseException e) {
			throw new MarketingException(e, "date parse errors!",ErrorCode.System);
		} catch (Exception e) {
			throw new MarketingException(e, "System errors!",ErrorCode.Unknown);
		}
		return result;
	}
	
	public String createMarketingTask(LeadTask leadTask) throws MarketingException {
		CampaignTaskVO campaignTaskVO = queryMarketingTask(leadTask.getBpmInstanceId());
		String bpmId = createMarketingTask(String.valueOf(leadTask.getLeadId()), campaignTaskVO.getCaID(),leadTask.getContactId(),
				leadTask.getIsPotential(), leadTask.getPdCustomerId(), leadTask.getUserid(), campaignTaskVO.getEndDate(), 
				leadTask.getAppointDate(),leadTask.getSourceType()==null?-1:leadTask.getSourceType(), 
				leadTask.getSourceType2()==null?-1:leadTask.getSourceType2(),
				leadTask.getSourceType3()==null?-1:leadTask.getSourceType3(),leadTask.getRemark());
		return bpmId;
	}
	/**
	 * 创建推荐任务或者预约任务，任务时效为endDate
	 * out-bound取数创建营销任务 以下情形校验不通过，不创建任务
	 * <ul>
	 * <li>营销任务已过期</li>
	 * <li>客户不存在</li>
	 * </ul>
	 */
	public String createMarketingTask(String leadID, String campaignID, String contactID, long isPotential, String pdCustomerId,
			String usrID, Date endDate, Date appointDate,long sourceType, 
			long sourceType2, long sourceType3, String remark) throws MarketingException {
		String instID = null;
		try {
			if(StringUtils.isNotBlank(contactID) && !StringUtil.isNumeric(contactID)) {
				throw new MarketingException("客户ID必须为数字",ErrorCode.CUSTOMERNOTALLOWED);
			}
			List<LeadTask> leadTasks = new ArrayList<LeadTask>();
			// start BPM task
			String processKey = leadTaskDao.queryBPMProcessID(campaignID);
			if ((System.currentTimeMillis() - endDate.getTime() < 0)) {
				Map<String, Object> vars = new HashMap<String, Object>();
				SimpleDateFormat sf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				vars.put("taskDuration", sf.format(endDate));
				vars.put("applyUserId", usrID);
				instID = bpmProcessService.startProcessInstance(
						processKey, "", usrID, vars);
				// String instID = "inst123";
				List<String> tasks = bpmProcessService
						.queryTaskByAssigneeAndInstance(usrID, instID);

				for (String tid : tasks) {
					LeadTask mTask = new LeadTask();
					mTask.setUserid(usrID);
					mTask.setBpmInstanceId(instID);
                    if(sourceType == 0 || sourceType==2) {
                        mTask.setStatus(String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()));
                    } else {
					    mTask.setStatus(String.valueOf(CampaignTaskStatus.INITIALIZED.getIndex()));
                    }
					mTask.setBpmTaskId(tid);
					mTask.setCreateDate(new Date());
					if(StringUtils.isNotBlank(remark)) {
						mTask.setRemark(remark);
					}
					if(appointDate != null) {
						mTask.setAppointDate(appointDate);
					}
					mTask.setContactId(contactID);
					mTask.setLeadId(Long.valueOf(leadID));
					mTask.setIsPotential(isPotential);
					mTask.setPdCustomerId(pdCustomerId);
                    mTask.setSourceType(sourceType);
                    if(sourceType == CampaignTaskSourceType.INCOMING.getIndex()) {
                    	mTask.setSourceType2(sourceType2);
                    }
                    if(sourceType2 == CampaignTaskSourceType2.CONNECTED.getIndex()) {
                    	mTask.setSourceType3(sourceType3);
                    }
                    mTask.setCareateUser(usrID);
                    mTask.setUpdateUser(usrID);
                    mTask.setUpdateDate(new Date());
					LeadTask mTaskRtn = leadTaskDao.save(mTask);
					leadTasks.add(mTaskRtn);
				}
				
			} else {
				throw new MarketingException("线索过期，不能创建任务",ErrorCode.LEADOVERDUE);
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			throw new MarketingException(e, "System errors!",ErrorCode.Unknown);
		}
		return instID;
	}
	
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.bpmTaskService",expiration=43200)
	public String queryBPMProcessID(@ParameterValueKeyProvider String leadID) {
		return leadTaskDao.queryBPMProcessID(leadID);
	}

	public void completeTaskAndUpdateStatus(String remark,
			String processInsId, String taskID, String userID) {
		if(!isTaskFinished(processInsId)) {
			Map<String, Object> v = new HashMap<String, Object>();
			try {
				bpmProcessService.completeTaskByInstID(processInsId, v, userID);
			} catch (ActivitiException ae) {
				logger.warn("Activiti errors!", ae);
			}
		}
		updateTaskStatus(processInsId, String.valueOf(CampaignTaskStatus.STOP.getIndex()), remark, taskID);
	}
	
	/**
	 * 从
	 */
	public List<String> clearInvalidPushTask() {
		List<String> clearedInsts = new ArrayList<String>();
		List<LeadTask> leadTasks = leadTaskDao.queryInvalidPushInst();
		for(LeadTask lt : leadTasks) {
			closeTaskAndUpdateStatus("重复分配的任务，系统自动关闭", lt.getBpmInstanceId(), "", lt.getUserid());
			clearedInsts.add(lt.getBpmInstanceId());
		}
		return clearedInsts;
	}
	
	public void closeTaskAndUpdateStatus(String remark,
			String processInsId, String taskID, String userID) {
		if(!isTaskFinished(processInsId)) {
			Map<String, Object> v = new HashMap<String, Object>();
			try {
				bpmProcessService.completeTaskByInstID(processInsId, v, userID);
			} catch (ActivitiException ae) {
				logger.warn("Activiti errors!", ae);
			}
		}
		updateTaskStatus(processInsId, String.valueOf(CampaignTaskStatus.SYSTEMSTOP.getIndex()), remark, taskID);
	}
	
	public boolean cancelTask(String remark, String processInsId, String taskID, String userID) {
		boolean isCanceled = false;
		updateTaskStatus(processInsId, 
				String.valueOf(CampaignTaskStatus.CANCEL.getIndex()), remark, taskID);
		isCanceled = true;
		return isCanceled;
	}

	public void saveOrUpdate(LeadTask leadTask) {
		leadTaskDao.saveOrUpdate(leadTask);
	}
	
	public boolean updateTaskStatus(String instID, String status, String remark, String taskID) {
		boolean isSuc = true;
		int number = leadTaskDao.updateInstStatus(instID, status, remark, taskID);
		if (number < 1) {
			isSuc = false;
		}
		return isSuc;
	}

	/**
	 * 修改task所有者为新所有者
	 */
	public int changeTaskOwnerByLead(long leadID, String newOwner) {
		int updatedNum = 0;
		List<Object> objs = leadTaskDao.queryTasksByLead(leadID);
		updatedNum = leadTaskDao.updateTaskOwnerByLead(leadID, newOwner);
		for (Object obj : objs) {
			String taskID = (String) obj;
			try {
				bpmProcessService.changeTaskOwner(taskID, newOwner);
			} catch (ActivitiException ae) {
				logger.warn("Activiti errors!", ae);
			}
		}
		return updatedNum;
	}
	
	public List<Object> queryInstsByLead(long leadID) {
		return leadTaskDao.queryInstsByLead(leadID);
	}
	
	public LeadTask queryInst(String instID) {
		return leadTaskDao.queryInst(instID);
	}
	
	public int updateTaskStatusByLead(long leadID, String status) {
		return leadTaskDao.updateTaskStatusByLead(leadID, status);
	}

	public Integer queryCount(CampaignTaskDto mTaskDto) {
		if(StringUtils.isBlank(mTaskDto.getStartDate()) && StringUtils.isBlank(mTaskDto.getEndDate())) {
			String startDate = DateUtil.addDays(new Date(), -3);
			String endDate = DateUtil.addDays(new Date(), 0);
			mTaskDto.setStartDate(startDate);
			mTaskDto.setEndDate(endDate);
		}
		if (StringUtils.isNotBlank(mTaskDto.getEndDate())) {
			String endDate = null;
			try {
				endDate = DateUtil.addDays(mTaskDto.getEndDate(), 1);
			} catch (ParseException e) {
				logger.warn("date parse",e);
			}
			mTaskDto.setEndDate(endDate);
		}
		return leadTaskDao.queryCount(mTaskDto);
	}
	
	@ReadThroughSingleCache(namespace="counter/queryAllocatedCount",expiration=3600)
	public Integer queryAllocatedCount(@ParameterValueKeyProvider CampaignTaskDto mTaskDto) {
		return leadTaskDao.queryCount(mTaskDto);
	}
	
	@ReadThroughSingleCache(namespace="counter/queryUndialCount",expiration=3600)
	public Integer queryUndialCount(@ParameterValueKeyProvider CampaignTaskDto mTaskDto) {
		return leadTaskDao.queryCount(mTaskDto);
	}
	
	public Integer queryUnStartedCampaignTaskCount(CampaignTaskDto mTaskDto) {
		return leadTaskDao.queryUnStartedCampaignTaskCount(mTaskDto);
	}
	
	/**
	 * initialized, active are in unfinished state, 
	 * cancel, stop , systemstop are in finished state
	 * isTaskFinished
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	public boolean isTaskFinished(String instId) {
		boolean isFinished = false;
		LeadTask lt = null;
		String status = String.valueOf(CampaignTaskStatus.INITIALIZED.getIndex());
		try {
			lt = leadTaskDao.queryInst(instId);
			status = lt.getStatus();
		} catch(Exception e) {
			logger.warn("查询任务状态异常",e);
		}
		if(!String.valueOf(CampaignTaskStatus.INITIALIZED.getIndex()).equals(status) &&
				!String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()).equals(status)) {
			isFinished = true;
		}
		return isFinished;
	}
	
	public boolean queryInstsIsDone(String instID) {
		boolean isRecommended = false;
		Object obj = leadTaskDao.queryInstsIsDone(instID);
		if(obj != null) {
			int isDone = ((BigDecimal)obj).intValue();
			isRecommended = isDone==0?false:true;
		}
		return isRecommended;
	}

	/**
	 * TODOX marketingPlan->campaign
	 */
	public List<TaskFormItem> getTaskForm(String campaignID) {
		long camID = Long.valueOf(campaignID);
		List<Object> objs = campaignDao.queryCampaignParas(camID);
		List<TaskFormItem> taskForms = convertObj2TaskForm(objs, camID);
		return taskForms;
	}

	/**
	 * 保存任务执行结果 submitTaskForm
	 * 
	 * @param instID
	 * @param taskID
	 * @param taskItems
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public boolean submitTaskForm(String instID, String leadTaskId,
			List<TaskFormItem> taskItems) {
		boolean isSuc = false;
		UsrTaskRecommend existingRecommend = usrTaskRecommendDao.getRecommendByInstId(instID);
		if(existingRecommend == null) {
			existingRecommend = new UsrTaskRecommend();
			existingRecommend.setProcess_insid(instID);
			existingRecommend.setLeadTaskid(leadTaskId);
		}
		mergeTaskItemInfo2UsrTaskRecommend(taskItems, existingRecommend);
		usrTaskRecommendDao.saveOrUpdate(existingRecommend);
		//更新
		leadTaskDao.updateInstIsDone(instID, 1L);
		isSuc = true;
		return isSuc;
	}
	
	public int updatePotential2Normal(String contactId, String potentialContactId) {
		return leadTaskDao.updatePotential2Normal(contactId, potentialContactId);
	}
	
	public int updateInstAppointDate(String instID, Date appointDate) {
		int number = 0 ;
		try {
			if(appointDate != null) {
				number = leadTaskDao.updateInstAppointDate(instID, appointDate);
			}
		} catch(Exception e) {
			logger.error("预约时间更新错误",e);
		}
		return number;
	}

	// TODO hard-code binding?
	private void mergeTaskItemInfo2UsrTaskRecommend(
			List<TaskFormItem> taskItems, UsrTaskRecommend usrTaskRecommend) {
		for (int i = 0; i < taskItems.size(); i++) {
			TaskFormItem tfi = taskItems.get(i);
			switch (i) {
			case 0: {
				if (tfi.isChecked()) {
					usrTaskRecommend.setRecommend_prod1(tfi.getValue());
				}
				break;
			}
			case 1: {
				if (tfi.isChecked()) {
					usrTaskRecommend.setRecommend_prod2(tfi.getValue());
				}
				break;
			}
			case 2: {
				if (tfi.isChecked()) {
					usrTaskRecommend.setRecommend_prod3(tfi.getValue());
				}
				break;
			}
			case 3:  {
				if (tfi.isChecked()) {
					usrTaskRecommend.setOther_recommend_prod(tfi.getValue());
				}
				break;
			}
			default: {
				break;
			}
			}
		}
	}

	private List<TaskFormItem> convertObj2TaskForm(List<Object> objs, long camID) {
		List<TaskFormItem> taskForms = new ArrayList<TaskFormItem>();
		for (Object obj : objs) {
			Object[] objArr = (Object[]) obj;
			TaskFormItem tfi = new TaskFormItem();
			tfi.setName((String) objArr[1]);
			tfi.setType((String) objArr[2]);
			long camParaID = ((BigDecimal) objArr[0]).longValue();
			tfi.setCamName((String) objArr[3]);
			List<Object> valueObjs = campaignDao.queryCampaignParaValue(camID,
					camParaID);
			if(valueObjs != null && valueObjs.size() > 0) {
				String value = (String) valueObjs.get(0);
				tfi.setValue(value);
			}
			taskForms.add(tfi);
		}
		return taskForms;
	}

	private List<CampaignTaskVO> convertObj2VO(List<Object> objs) {
		List<CampaignTaskVO> vos = new ArrayList<CampaignTaskVO>();
		for (Object obj : objs) {
			try {
				Object[] objArr = (Object[]) obj;
				CampaignTaskVO mvo = new CampaignTaskVO();
				if(objArr[0] != null) {
					mvo.setCaID("" + ((BigDecimal) objArr[0]).intValue());
				}
				mvo.setCaName((String) objArr[1]);
				mvo.setLtpName((String) objArr[2]);
				if(StringUtils.isNotBlank((String) objArr[3])) {
					mvo.setConid((String) objArr[3]);
					mvo.setConName((String) objArr[4]);
				} else if(objArr[5] != null) {
					mvo.setConid(""+(BigDecimal) objArr[5]);
					mvo.setConName((String) objArr[6]);
				}
				mvo.setLtCreateDate((Date) objArr[7]);
				mvo.setEndDate((Date) objArr[8]);
				mvo.setLtStatus((String) objArr[9]);
				mvo.setUserID((String) objArr[10]);
				mvo.setRemark((String) objArr[11]);
				mvo.setAppointDate((Date) objArr[12]);
				mvo.setTid(((BigDecimal) objArr[13]).intValue());
				mvo.setInstID((String) objArr[14]);
				if(objArr[15] != null) {
					mvo.setIsPotential(((BigDecimal) objArr[15]).intValue());
				}
				mvo.setPdCustomerId((String) objArr[16]);
				if(objArr[17] != null) {
					mvo.setLeadId(((BigDecimal) objArr[17]).intValue());
				}
				if (objArr[18] != null) {
					mvo.setExigency(((BigDecimal) objArr[18]).intValue());
				}
                if(objArr[19] != null) {
                    mvo.setSourceType(((BigDecimal) objArr[19]).intValue());
                }
                if(objArr[20] != null) {
                    mvo.setLtpId(((BigDecimal) objArr[20]).intValue());
                }
                if(objArr[21] != null) {
                    mvo.setSourceType2(((BigDecimal) objArr[21]).intValue());
                }
                if(objArr[22] != null) {
                    mvo.setSourceType3(((BigDecimal) objArr[22]).intValue());
                }
				vos.add(mvo);
			} catch(Exception e) {
				logger.error("数据异常，丢弃此数据，继续执行",e);
			}
		}
		return vos;
	}

	public BpmProcessService getBpmProcessService() {
		return bpmProcessService;
	}

	public void setBpmProcessService(BpmProcessService bpmProcessService) {
		this.bpmProcessService = bpmProcessService;
	}
}
