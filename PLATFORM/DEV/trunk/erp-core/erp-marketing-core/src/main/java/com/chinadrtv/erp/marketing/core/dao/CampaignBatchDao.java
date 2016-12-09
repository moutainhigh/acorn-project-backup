package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划批次DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划批次DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CampaignBatchDao extends
		GenericDao<CampaignBatch, java.lang.Long> {

	public List<CampaignBatch> query(CampaignDto campaign,
			DataGridModel dataModel);

	public Integer queryCount(CampaignDto campaign);

	public List<CampaignBatch> queryList(Long campaignId, String status);

	public CampaignBatch getCampaignBatchByCampaignId(Long campaignId);

	public SmsSendDto getSmsDetailByCampaingId(Long campaignId, String mobile);
}
