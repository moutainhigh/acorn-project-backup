package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.common.ResponseListResult;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignRequest;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.model.marketing.CampaignCrConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;

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
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface CampaignService {
	public Campaign getCampaignById(Long id);

	public void saveCampaign(Campaign Campaign, String user,
			JobCronSet jobCronSet);

	public void saveCampaign(Campaign Campaign, String user);

	public Campaign saveCampaign(CampaignRequest campaignRequest)
			throws Exception;

	public void updateCampaign(CampaignRequest campaignRequest);

	public void removeCampaign(Long id);

	public Map<String, Object> query(CampaignDto Campaign,
			DataGridModel dataModel);

	public List<Campaign> queryList();

	public void saveSchedule(Long campaignId, JobCronSet jobCronSet);

	public void saveOrUpdate(Campaign campaign);

	public void stopCampaign(Long campaignId) throws Exception;

	public void goOnCampaign(Long campaignId) throws Exception;

	public ResponseListResult queryInboundCampaign(String tollFreeNum,
			String callTime);

	public ResponseListResult queryOldCustomerCampaign(String execDepartment);

	public Map<String, Object> queryCampaignMonitor(
			CampaignMonitorDto Campaign, DataGridModel dataModel);

	public CampaignMonitor queryMonitorByCampaignId(Long campaignId);

	public void insertCampaignMonitor(CampaignMonitor CampaignMonitor);

	public List<CampaignBatch> getCampaignBatchs(Long campaignId);

	public CampaignCrConfig getByUserId(String userId);

}
