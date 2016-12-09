package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.model.marketing.CampaignTypeValue;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划类型参数值-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划类型参数值-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface CampaignTypeValueService {
	public CampaignTypeValue getCampaignTypeValueById(Long id);

	public void saveCampaignTypeValue(CampaignTypeValue campaignTypeValue);

	public void saveSmsParamTypeValue(Long campaignId, String user,
			SmssnedDto smsParam);

	public void removeCampaignTypeValue(Long id);

	public Map<String, Object> query(CampaignDto campaign,
			DataGridModel dataModel);

	public List<CampaignTypeValue> queryList();

	public SmssnedDto querySmsParamTypeValue(Long campaignId);

	public Map<String, String> queryParamTypeValue(Long campaignId);

}
