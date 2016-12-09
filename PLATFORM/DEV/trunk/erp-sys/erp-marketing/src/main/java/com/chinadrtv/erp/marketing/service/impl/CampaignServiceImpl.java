package com.chinadrtv.erp.marketing.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.ResponseListResult;
import com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeParamsDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeValueDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeParamsDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeValueDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignRequest;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.dao.CampaignCrConfigDao;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.marketing.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.model.marketing.CampaignCrConfig;
import com.chinadrtv.erp.model.marketing.LeadTypeParams;
import com.chinadrtv.erp.model.marketing.LeadTypeValue;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.user.aop.RowAuth;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划管理-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划管理-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("campaignService")
public class CampaignServiceImpl implements CampaignService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignServiceImpl.class);

	@Autowired
	private CampaignDao campaignDao;

	@Autowired
	private CampaignTypeDao campaignTypeDao;

	@Autowired
	private CampaignTypeValueDao campaignTypeValueDao;

	@Autowired
	private CampaignTypeParamsDao campaignTypeParamsDao;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private GroupSmsSendService groupSmsSendService;

	@Autowired
	private CampaignBatchDao campaignBatchDao;
	@Autowired
	private LeadTypeParamsDao leadTypeParamsDao;

	@Autowired
	private LeadTypeValueDao leadTypeValueDao;
	@Autowired
	private CampaignMonitorDao campaignMonitorDao;

	@Autowired
	private CampaignApiService campaignApiService;
	@Autowired
	private CampaignCrConfigDao campaignCrConfigDao;

	/**
	 * 根据id获取营销计划信息
	 */
	public Campaign getCampaignById(Long id) {
		return campaignDao.get(id);
	}

	/**
	 * <p>
	 * Title: saveCampaign
	 * </p>
	 * <p>
	 * Description:保存并更新营销计划,及设置和更新调度任务
	 * </p>
	 * 
	 * @param Campaign
	 * @see com.chinadrtv.erp.marketing.service.CampaignService#saveCampaign(com.chinadrtv.erp.marketing.model.Campaign)
	 */
	public void saveCampaign(Campaign campaign, String user,
			JobCronSet jobCronSet) {

		try {
			// if (Campaign.getExecuteCycel().equals("0") ||
			// Campaign.getExecuteCycel().equals("5")) {
			// Campaign.setExecuteType("1");
			// Campaign.setIsRecover("0");
			// } else {
			// // 是否当天数据回收
			// if (StringUtils.isEmpty(Campaign.getIsRecover())) {
			// Campaign.setIsRecover("0");
			// } else {
			// Campaign.setIsRecover("1");
			// }
			//
			// Campaign.setExecuteType("2");
			// }
			//
			// jobCronSet.setFrequency(Campaign.getExecuteCycel());
			// if (StringUtils.isEmpty(Campaign.getGroupCode())) {
			//
			// // String nextVal = getNextVal();
			// Campaign.setGroupCode(Campaign.getGroupCodeTmp());
			// Campaign.setCrtUser(user);
			// Campaign.setCrtDate(new Date());
			// CampaignDao.save(Campaign);
			//
			// jobCronSet.setJobName(Campaign.getGroupCodeTmp());
			// jobCronSet.setGroup(Constants.JOB_GROUP_CUSTOMER_GROUP);
			// schedulerService.scheduleAddJob(jobCronSet);
			//
			// } else {
			// JobDetail jobDetail =
			// schedulerService.getJobDetail(Campaign.getGroupCode(),
			// Constants.JOB_GROUP_CUSTOMER_GROUP);
			// if (jobDetail != null) {
			// jobCronSet.setJobName(jobDetail.getName());
			// jobCronSet.setGroup(jobDetail.getGroup());
			// schedulerService.scheduleUpdateJob(jobCronSet);
			// } else {
			// jobCronSet.setJobName(Campaign.getGroupCode());
			// jobCronSet.setGroup(Constants.JOB_GROUP_CUSTOMER_GROUP);
			// schedulerService.scheduleAddJob(jobCronSet);
			// }
			// Campaign orgiGroup = CampaignDao.get(Campaign.getGroupCode());
			// orgiGroup.setGroupName(Campaign.getGroupName());
			// orgiGroup.setAssignType(Campaign.getAssignType());
			// orgiGroup.setBusiCode(Campaign.getBusiCode());
			// orgiGroup.setDepartment(Campaign.getDepartment());
			// orgiGroup.setExecuteCycel(Campaign.getExecuteCycel());
			// orgiGroup.setExecuteType(Campaign.getExecuteType());
			// orgiGroup.setRemark(Campaign.getRemark());
			// orgiGroup.setValidEndDate(Campaign.getValidEndDate());
			// orgiGroup.setValidStartDate(Campaign.getValidStartDate());
			// orgiGroup.setStatus(Campaign.getStatus());
			// orgiGroup.setUpDate(Campaign.getUpDate());
			// orgiGroup.setUpUser(Campaign.getUpUser());
			// orgiGroup.setJobId(Campaign.getJobId());
			// orgiGroup.setQueneId(Campaign.getQueneId());
			// orgiGroup.setRejectCycle(Campaign.getRejectCycle());
			// orgiGroup.setIsRecover(Campaign.getIsRecover());
			// orgiGroup.setExt1(Campaign.getExt1());
			// // CampaignDao.saveOrUpdate(Campaign);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("保存营销计划异常,id=" + campaign.getId() + ";异常信息="
					+ e.getMessage());
		}
	}

	/**
	 * <p>
	 * Title: removeCampaign
	 * </p>
	 * <p>
	 * Description: 删除营销计划
	 * </p>
	 * 
	 * @param Campaign
	 * @see com.chinadrtv.erp.marketing.service.CampaignService#removeCampaign(com.chinadrtv.erp.marketing.model.Campaign)
	 */
	public void removeCampaign(Long id) {
		campaignDao.remove(id);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description: 查询营销计划列表
	 * </p>
	 * 
	 * @param Campaign
	 * @param dataModel
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CampaignService#query(com.chinadrtv.erp.marketing.model.Campaign,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	@RowAuth
	public Map<String, Object> query(CampaignDto campaign,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Campaign> list = campaignDao.query(campaign, dataModel);
		Integer total = campaignDao.queryCount(campaign);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * 查询所有营销计划列表
	 */
	public List<Campaign> queryList() {
		// TODO Auto-generated method stub
		return campaignDao.queryList();
	}

	/**
	 * 保存或更新营销计划
	 */
	public void saveCampaign(Campaign campaign, String user) {
		if (campaign.getId() == null) {
			campaign.setStatus(Constants.CAMPAIGN_ACTIVE);
			campaign.setCreateDate(new Date());
			campaign.setCreateUser(user);
			campaign.setUpdateDate(new Date());
			campaign.setUpdateUser(user);
			campaignDao.saveOrUpdate(campaign);
		} else {
			Campaign camp = campaignDao.get(campaign.getId());
			if (!camp.getAudience().equals(campaign.getAudience())) {
				camp.setLastExecDate(null);
			}
			camp.setAudience(campaign.getAudience());
			camp.setName(campaign.getName());
			camp.setBudgetedCost(campaign.getBudgetedCost());
			camp.setCampaignTypeId(campaign.getCampaignTypeId());
			camp.setLeadTypeId(campaign.getLeadTypeId());
			camp.setLeadTargeted(campaign.getLeadTargeted());
			camp.setDescription(campaign.getDescription());
			camp.setStartDate(campaign.getStartDate());
			camp.setEndDate(campaign.getEndDate());
			camp.setOwner(campaign.getOwner());
			camp.setUpdateDate(new Date());
			camp.setDepartment(campaign.getDepartment());
			camp.setUpdateUser(user);
			camp.setTollFreeNum(campaign.getTollFreeNum());
			camp.setDnis(campaign.getDnis());
		}

	}

	/**
	 * scm媒体保存营销计划
	 * 
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public Campaign saveCampaign(CampaignRequest campaignRequest)
			throws Exception {

		Campaign campaign = new Campaign();

		PropertyUtils.copyProperties(campaign, campaignRequest);
		campaign.setStatus(Constants.CAMPAIGN_ACTIVE);
		campaign.setCreateDate(new Date());
		campaign.setUpdateDate(new Date());
		campaign.setCreateUser(campaignRequest.getCreateUser());
		campaign.setUpdateUser(campaignRequest.getUpdateUser());
		campaign.setDepartment(campaignRequest.getExecDepartment());
		campaignDao.saveOrUpdate(campaign);
		// 修改为leadtypeparam
		List<LeadTypeParams> paramsList = leadTypeParamsDao
				.getParamsList(campaignRequest.getLeadTypeId());
		LeadTypeValue leadTypeValue = null;
		for (LeadTypeParams params : paramsList) {
			leadTypeValue = new LeadTypeValue();
			leadTypeValue.setCampaignId(campaign.getId());
			leadTypeValue.setCode(params.getCode());
			leadTypeValue.setLeadTypeParamsId(params.getId());
			leadTypeValue.setValue(campaignRequest.genParamValues(params
					.getCode()));
			leadTypeValue.setCreateDate(new Date());
			leadTypeValue.setUpdateDate(new Date());
			leadTypeValue.setCreateUser(campaignRequest.getCreateUser());
			leadTypeValue.setUpdateUser(campaignRequest.getCreateUser());
			leadTypeValueDao.saveOrUpdate(leadTypeValue);
		}

		// 分解落地号，将媒体计划信息放入缓存
		String dnis = campaign.getDnis();
		if (!StringUtils.isEmpty(dnis)) {
			String[] dnisList = dnis.split(",");
			for (String dnisNum : dnisList) {
				campaignApiService.getMediaPlan(dnisNum, campaign);
			}
		}

		return campaign;
	}

	/**
	 * scm媒体更新营销计划
	 * 
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public void updateCampaign(CampaignRequest campaignRequest) {

		Campaign campaign = campaignDao.get(campaignRequest.getId());

		campaign.setUpdateDate(new Date());

		if (!StringUtil.isNullOrBank(campaignRequest.getTollFreeNum())) {
			campaign.setTollFreeNum(campaignRequest.getTollFreeNum());
		}
		if (!StringUtil.isNullOrBank(campaignRequest.getDnis())) {
			campaign.setDnis(campaignRequest.getDnis());
		}
		if (!StringUtil.isNullOrBank(campaignRequest.getDescription())) {
			campaign.setDescription(campaignRequest.getDescription());
		}

		if (!StringUtil.isNullOrBank(campaignRequest.getStatus())) {
			campaign.setStatus(campaignRequest.getStatus());
		}

		if(campaignRequest.getStartDate()!=null){
			campaign.setStartDate(campaignRequest.getStartDate());
		}
		
		if(campaignRequest.getEndDate()!=null){
			campaign.setEndDate(campaignRequest.getEndDate());
		}
		
		if (!StringUtil.isNullOrBank(campaignRequest.getUpdateUser())) {
			campaign.setUpdateUser(campaignRequest.getUpdateUser());
		}
		campaign.setUpdateDate(new Date());

		campaignDao.saveOrUpdate(campaign);

		if (!StringUtil.isNullOrBank(campaignRequest.getProduct1())) {

			leadTypeValueDao.removeAll(campaign.getId());

			// 修改为leadtypeparam
			List<LeadTypeParams> paramsList = new ArrayList<LeadTypeParams>();
			try {
				paramsList = leadTypeParamsDao.getParamsList(campaign
						.getLeadTypeId());
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			LeadTypeValue leadTypeValue = null;
			for (LeadTypeParams params : paramsList) {
				leadTypeValue = new LeadTypeValue();
				leadTypeValue.setCampaignId(campaign.getId());
				leadTypeValue.setCode(params.getCode());
				leadTypeValue.setLeadTypeParamsId(params.getId());
				leadTypeValue.setValue(campaignRequest.genParamValues(params
						.getCode()));
				leadTypeValue.setCreateDate(new Date());
				leadTypeValue.setUpdateDate(new Date());
				leadTypeValue.setCreateUser(campaignRequest.getUpdateUser());
				leadTypeValue.setUpdateUser(campaignRequest.getUpdateUser());
				leadTypeValueDao.saveOrUpdate(leadTypeValue);
			}

		}

	}

	/**
	 * 保存营销调度任务
	 * 
	 * @Description: TODO
	 * @param campaignId
	 * @param jobCronSet
	 * @return void
	 * @throws
	 */
	public void saveSchedule(Long campaignId, JobCronSet jobCronSet) {
		try {
			JobDetail jobDetail = schedulerService.getJobDetail(
					Constants.JOB_NAME_CAMPAIGN_PREFIX + campaignId,
					Constants.JOB_GROUP_CAMPAIGN);

			if (jobDetail != null) {
				jobCronSet.setJobName(jobDetail.getKey().getName());
				jobCronSet.setGroup(jobDetail.getKey().getGroup());
				schedulerService.scheduleUpdateJob(jobCronSet, null);
			} else {
				jobCronSet.setJobName(Constants.JOB_NAME_CAMPAIGN_PREFIX
						+ campaignId);
				jobCronSet.setGroup(Constants.JOB_GROUP_CAMPAIGN);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("campaignId", campaignId);
				schedulerService.scheduleAddJob(jobCronSet, params);
			}

			Campaign campaign = campaignDao.get(campaignId);

			if (campaign.getCampaignTypeId() == 5) {
				campaign.setStatus(Constants.CAMPAIGN_COUPON);
			} else {
				if (campaign.getStatus().equals(Constants.CAMPAIGN_STOP)
						|| campaign.getStatus().equals(
								Constants.CAMPAIGN_COMPLETE)) {
					campaign.setStatus(Constants.CAMPAIGN_ACTIVE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存或更新营销计划
	 */
	public void saveOrUpdate(Campaign campaign) {
		campaignDao.saveOrUpdate(campaign);
	}

	/**
	 * 停止营销计划
	 * 
	 * @Description: TODO
	 * @param campaignId
	 * @return void
	 * @throws
	 */
	public void stopCampaign(Long campaignId) throws Exception {

		Campaign campaign = campaignDao.get(campaignId);

		boolean flag = false;
		boolean result = true;

		List<CampaignBatch> campaignBatchList = campaignBatchDao.queryList(
				campaign.getId(), Constants.CAMPAIGN_BATCH_STATUS_NORMAL);
		for (CampaignBatch batch : campaignBatchList) {
			try {
				SmsSend smsSend = groupSmsSendService.smsStop(
						batch.getBatchId(), campaign.getDepartment());
				batch.setStatus(Constants.CAMPAIGN_BATCH_STATUS_FINISH);
				campaignBatchDao.saveOrUpdate(batch);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("campaignId" + campaignId + "停止失败 batchid:"
						+ batch.getBatchId());
				throw new Exception("停止失败");
			}
		}
		campaign.setStatus(Constants.CAMPAIGN_STOP);

	}

	/**
	 * 继续执行营销计划
	 * 
	 * @Description: TODO
	 * @param campaignId
	 * @return void
	 * @throws
	 */
	public void goOnCampaign(Long campaignId) throws Exception {

		Campaign campaign = campaignDao.get(campaignId);
		campaign.setStatus(Constants.CAMPAIGN_ACTIVE);

	}

	/**
	 * 根据400 号码 呼入时间查询 营销计划
	 * 
	 * @param tollFreeNum
	 * 
	 * @param callTime
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.CampaignService#queryInboundCampaign
	 *      (java.lang.String, java.lang.String)
	 */
	public ResponseListResult queryInboundCampaign(String tollFreeNum,
			String callTime) {
		// TODO Auto-generated method stub
		List<CampaignDto> list = campaignDao.queryInboundCampaign(tollFreeNum,
				callTime);
		ResponseListResult result = new ResponseListResult();
		// Map<String, Object> map = new HashMap<String, Object>();
		if (list != null && !list.isEmpty()) {
			// Campaign campaign = list.get(0);
			// map.put("id", campaign.getId());
			// map.put("campaignType", campaign.getCampaignType());
			// map.put("leadType", campaign.getLeadType());
			// map.put("execDepartment", campaign.getExecDepartment());
			// map.put("name", campaign.getName());
			// map.put("startDate",
			// DateTimeUtil.sim3.format(campaign.getStartDate()));
			// map.put("endDate",
			// DateTimeUtil.sim3.format(campaign.getEndDate()));
			// map.put("owner", campaign.getOwner());
			// map.put("tollFreeNum", campaign.getTollFreeNum());
			// map.put("dnis", campaign.getDnis());
			// map.put("description", campaign.getDescription());
			// map.put("createUser", campaign.getCreateUser());
			result.setStatus("1");
			result.setResult(list);
		} else {
			result.setStatus("-1");
			result.setMsg("无数据");
			result.setCode("0001");
		}
		return result;
	}

	/*
	 * 查询老客户营销活动
	 * 
	 * @param execDepartment
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#queryOldCustomerCampaign
	 * (java.lang.String)
	 */
	public ResponseListResult queryOldCustomerCampaign(String execDepartment) {
		List<CampaignDto> list = campaignDao
				.queryOldCustomerCampaign(execDepartment);
		ResponseListResult result = new ResponseListResult();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtil.isNullOrBank(execDepartment)) {
			if (list != null && !list.isEmpty()) {
				// Campaign campaign = list.get(0);
				// map.put("id", campaign.getId());
				// map.put("campaignType", campaign.getCampaignType());
				// map.put("leadType", campaign.getLeadType());
				// map.put("execDepartment", campaign.getExecDepartment());
				// map.put("name", campaign.getName());
				// map.put("startDate",
				// DateTimeUtil.sim3.format(campaign.getStartDate()));
				// map.put("endDate",
				// DateTimeUtil.sim3.format(campaign.getEndDate()));
				// map.put("owner", campaign.getOwner());
				// map.put("tollFreeNum", campaign.getTollFreeNum());
				// map.put("dnis", campaign.getDnis());
				// map.put("description", campaign.getDescription());
				// map.put("createUser", campaign.getCreateUser());
				result.setStatus("1");
				result.setResult(list);
			} else {
				result.setStatus("-1");
				result.setMsg("数据库查询异常");
				result.setCode("0001");
			}
		} else {
			result.setStatus("-1");
			result.setMsg("传入参数为空");
			result.setCode("0002");
		}
		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryCampaignMonitor
	 * </p>
	 * <p>
	 * Description:campaignMonitor 分页
	 * </p>
	 * 
	 * @param Campaign
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.CampaignService#queryCampaignMonitor
	 *      (com.chinadrtv.erp.marketing.core.dto.CampaignDto,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public Map<String, Object> queryCampaignMonitor(
			CampaignMonitorDto campaignMonitor, DataGridModel dataModel) {
		// TODO Auto-generated method stub

		Map<String, Object> result = new HashMap<String, Object>();

		Integer total = campaignMonitorDao
				.getCampaignMonitorCount(campaignMonitor);
		List<CampaignMonitorDto> list = campaignMonitorDao
				.getCampaignMonitorPageList(campaignMonitor, dataModel);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryMonitorByCampaignId</p> <p>Description: </p>
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#queryMonitorByCampaignId
	 * (java.lang.Long)
	 */
	@Override
	public CampaignMonitor queryMonitorByCampaignId(Long campaignId) {
		// TODO Auto-generated method stub
		return campaignMonitorDao.getByCampaignId(campaignId);

	}

	/*
	 * (非 Javadoc) <p>Title: insertCampaignMonitor</p> <p>Description: </p>
	 * 
	 * @param CampaignMonitor
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#insertCampaignMonitor
	 * (com.chinadrtv.erp.smsapi.model.CampaignMonitor)
	 */
	@Override
	public void insertCampaignMonitor(CampaignMonitor CampaignMonitor) {
		// TODO Auto-generated method stub
		campaignMonitorDao.saveOrUpdate(CampaignMonitor);
	}

	/*
	 * (非 Javadoc) <p>Title: getCampaignBatchs</p> <p>Description: </p>
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#getCampaignBatchs
	 * (java.lang.Long)
	 */
	@Override
	public List<CampaignBatch> getCampaignBatchs(Long campaignId) {
		// TODO Auto-generated method stub
		return campaignBatchDao.queryList(campaignId, "");

	}

	/*
	 * (非 Javadoc) <p>Title: getByUserId</p> <p>Description: </p>
	 * 
	 * @param userId
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#getByUserId(java.
	 * lang.String)
	 */
	@Override
	public CampaignCrConfig getByUserId(String userId) {
		// TODO Auto-generated method stub
		return campaignCrConfigDao.getByUserId(userId);
	}
}
