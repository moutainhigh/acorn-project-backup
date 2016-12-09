package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划类型DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划类型DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CampaignTypeDao extends
		GenericDao<CampaignType, java.lang.Long> {

	public List<CampaignType> query(CampaignDto campaign,
			DataGridModel dataModel);

	public Integer queryCount(CampaignDto campaign);

	public List<CampaignType> queryList();
}
