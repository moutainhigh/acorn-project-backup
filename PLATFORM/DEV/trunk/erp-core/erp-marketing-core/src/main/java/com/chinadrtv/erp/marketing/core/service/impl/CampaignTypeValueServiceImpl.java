package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeParamsDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeValueDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeValueService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignTypeParams;
import com.chinadrtv.erp.model.marketing.CampaignTypeValue;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划类型-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划类型-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("campaignTypeValueService")
public class CampaignTypeValueServiceImpl implements CampaignTypeValueService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignTypeValueServiceImpl.class);

	@Autowired
	private CampaignTypeValueDao campaignTypeValueDao;

	@Autowired
	private CampaignTypeParamsDao campaignTypeParamsDao;

	@Autowired
	private CampaignDao campaignDao;

	/**
	 * 根据id获取营销计划信息
	 */
	public CampaignTypeValue getCampaignTypeValueById(Long id) {
		return campaignTypeValueDao.get(id);
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
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignService#saveCampaign(com.chinadrtv.erp.marketing.core.model.Campaign)
	 */
	public void saveCampaignTypeValue(CampaignTypeValue campaignTypeValue) {
		campaignTypeValueDao.saveOrUpdate(campaignTypeValue);
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
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignService#removeCampaign(com.chinadrtv.erp.marketing.core.model.Campaign)
	 */
	public void removeCampaignTypeValue(Long id) {
		campaignTypeValueDao.remove(id);

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
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignService#query(com.chinadrtv.erp.marketing.core.model.Campaign,
	 *      com.chinadrtv.erp.marketing.core.common.DataGridModel)
	 */
	public Map<String, Object> query(CampaignDto campaign,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<CampaignTypeValue> list = campaignTypeValueDao.query(campaign,
				dataModel);
		Integer total = campaignTypeValueDao.queryCount(campaign);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * 查询所有营销计划列表
	 */
	public List<CampaignTypeValue> queryList() {
		return campaignTypeValueDao.queryList();
	}

	public void saveSmsParamTypeValue(Long campaignId, String user,
			SmssnedDto smsParam) {

		campaignTypeValueDao.removeAll(campaignId);

		Campaign campaign = campaignDao.get(campaignId);
		List<CampaignTypeParams> paramsList = campaignTypeParamsDao
				.queryList(campaign.getCampaignTypeId());
		CampaignTypeValue typeValue = null;
		for (CampaignTypeParams params : paramsList) {
			typeValue = new CampaignTypeValue();
			typeValue.setCampaignId(campaignId);
			typeValue.setCreateDate(new Date());
			typeValue.setUpdateDate(new Date());
			typeValue.setCreateUser(user);
			typeValue.setUpdateUser(user);
			typeValue.setCampaignTypeParamsId(params.getId());
			typeValue.setCode(params.getCode());
			typeValue.setValue(smsParam.genParamValues(params.getCode()));
			campaignTypeValueDao.saveOrUpdate(typeValue);
		}
	}

	public SmssnedDto querySmsParamTypeValue(Long campaignId) {

		SmssnedDto smsSendDto = new SmssnedDto();

		List<CampaignTypeValue> paramsList = campaignTypeValueDao
				.queryList(campaignId);
		for (CampaignTypeValue typeValue : paramsList) {
			smsSendDto.setParamValues(smsSendDto, typeValue.getCode(),
					typeValue.getValue());
		}

		return smsSendDto;
	}

	public Map<String, String> queryParamTypeValue(Long campaignId) {

		List<CampaignTypeValue> paramsList = campaignTypeValueDao
				.queryList(campaignId);
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (CampaignTypeValue typeValue : paramsList) {
			paramsMap.put(typeValue.getCode(), typeValue.getValue());
		}

		return paramsMap;
	}

	public List<Map<String, String>> queryVarForDataGrid(Long campaignId) {

		List<CampaignTypeValue> paramsList = campaignTypeValueDao
				.queryList(campaignId);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (CampaignTypeValue typeValue : paramsList) {
			paramsMap.put(typeValue.getCode(), typeValue.getValue());
		}

		return result;
	}

}
