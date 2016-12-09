package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeParamsDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeService;
import com.chinadrtv.erp.model.marketing.CampaignType;
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
@Service("campaignTypeService")
public class CampaignTypeServiceImpl implements CampaignTypeService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignTypeServiceImpl.class);

	@Autowired
	private CampaignTypeDao campaignTypeDao;

	@Autowired
	private CampaignTypeParamsDao campaignTypeParamsDao;

	@Autowired
	private CampaignDao campaignDao;

	/**
	 * 根据id获取营销计划信息
	 */
	public CampaignType getCampaignTypeById(Long id) {
		return campaignTypeDao.get(id);
	}

	/**
	 * <p>
	 * Title: campaignType
	 * </p>
	 * <p>
	 * Description:保存并更新营销计划,及设置和更新调度任务
	 * </p>
	 * 
	 * @param CampaignRequest
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignService#saveCampaign(com.chinadrtv.erp.marketing.core.model.Campaign)
	 */
	public void saveCampaignType(CampaignType campaignType) {
		campaignTypeDao.saveOrUpdate(campaignType);
	}

	/**
	 * *
	 * <p>
	 * Title: removeCampaign
	 * </p>
	 * <p>
	 * Description: 删除营销计划
	 * </p>
	 */
	public void removeCampaignType(Long id) {
		campaignTypeDao.remove(id);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description: 查询营销计划列表
	 * </p>
	 * 
	 * @param CampaignDto
	 * @param dataModel
	 * @return
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignService#query(com.chinadrtv.erp.marketing.core.model.Campaign,
	 *      com.chinadrtv.erp.marketing.core.common.DataGridModel)
	 */
	public Map<String, Object> query(CampaignDto campaign,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<CampaignType> list = campaignTypeDao.query(campaign, dataModel);
		Integer total = campaignTypeDao.queryCount(campaign);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * 查询所有营销计划列表
	 */
	public List<CampaignType> queryList() {
		return campaignTypeDao.queryList();
	}

}
