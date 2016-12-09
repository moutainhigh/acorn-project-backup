package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.model.marketing.CampaignTypeValue;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划类型参数值DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划类型参数值DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CampaignTypeValueDao extends
		GenericDao<CampaignTypeValue, java.lang.Long> {

	/**
	 * 
	 * @Description: 分页查询campaignTypeValue
	 * @param CampaignTypeValue
	 * @param dataModel
	 * @return
	 * @return List<CampaignTypeValue>
	 * @throws
	 */
	public List<CampaignTypeValue> query(CampaignDto CampaignTypeValue,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description: 查询数量
	 * @param campaign
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer queryCount(CampaignDto campaign);

	/**
	 * 
	 * @Description: 移除某营销计划所有Typevalue
	 * @param campaignId
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer removeAll(Long campaignId);

	/**
	 * 
	 * @Description: 查询s所有列表
	 * @return
	 * @return List<CampaignTypeValue>
	 * @throws
	 */
	public List<CampaignTypeValue> queryList();

	/**
	 * 
	 * @Description: 查询某campaign的所有列表
	 * @param campaignId
	 * @return
	 * @return List<CampaignTypeValue>
	 * @throws
	 */
	public List<CampaignTypeValue> queryList(Long campaignId);

}
