package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
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
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface LeadTypeService {

	/**
	 * 
	 * @Description: 获取线索类型
	 * @param id
	 * @return
	 * @return LeadType
	 * @throws
	 */
	public LeadType getLeadTypeById(Long id);

	/**
	 * 
	 * @Description: 保存线索类型
	 * @param leadType
	 * @return void
	 * @throws
	 */
	public void saveLeadType(LeadType leadType);

	/**
	 * 
	 * @Description: 移除线索类型
	 * @param id
	 * @return void
	 * @throws
	 */
	public void removeLeadType(Long id);

	/**
	 * 
	 * @Description: 分页查询线索类型
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
	 * @Description: 查询所有线索类型
	 * @return
	 * @return List<LeadType>
	 * @throws
	 */
	public List<LeadType> queryList(String... type);

}
