package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
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
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface CampaignTypeService {
	/**
	 * 
	 * @Description: 获取营销类型
	 * @param id
	 * @return
	 * @return CampaignType
	 * @throws
	 */
	public CampaignType getCampaignTypeById(Long id);

	/**
	 * 
	 * @Description: 保存营销类型
	 * @param campaignType
	 * @return void
	 * @throws
	 */
	public void saveCampaignType(CampaignType campaignType);

	/**
	 * 
	 * @Description: 移除营销类型
	 * @param id
	 * @return void
	 * @throws
	 */
	public void removeCampaignType(Long id);

	/**
	 * 
	 * @Description: 分页查询列表
	 * @param Campaign
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> query(CampaignDto Campaign,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description: 查询列表
	 * @return
	 * @return List<CampaignType>
	 * @throws
	 */
	public List<CampaignType> queryList();

}
