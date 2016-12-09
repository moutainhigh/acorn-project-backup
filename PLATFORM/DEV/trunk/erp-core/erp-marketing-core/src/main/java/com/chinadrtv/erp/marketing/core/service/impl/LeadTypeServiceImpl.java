package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.LeadTypeDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.LeadTypeService;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 线索类型-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 线索类型-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("LeadTypeService")
public class LeadTypeServiceImpl implements LeadTypeService {

	private static final Logger logger = LoggerFactory
			.getLogger(LeadTypeServiceImpl.class);

	@Autowired
	private LeadTypeDao leadTypeDao;

	/**
	 * 根据id获取营销计划信息
	 */
	public LeadType getLeadTypeById(Long id) {
		return leadTypeDao.get(id);
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
	public void saveLeadType(LeadType LeadType) {
		leadTypeDao.saveOrUpdate(LeadType);
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
	public void removeLeadType(Long id) {
		leadTypeDao.remove(id);

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
		List<LeadType> list = leadTypeDao.query(campaign, dataModel);
		Integer total = leadTypeDao.queryCount(campaign);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * 查询所有营销计划列表
	 */
	public List<LeadType> queryList(String... type) {
		return leadTypeDao.queryList(type);
	}

}
