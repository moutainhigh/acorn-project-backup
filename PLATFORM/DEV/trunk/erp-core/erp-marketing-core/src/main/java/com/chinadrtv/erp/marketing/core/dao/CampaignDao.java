package com.chinadrtv.erp.marketing.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划管理DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划管理DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CampaignDao extends GenericDao<Campaign, java.lang.Long> {

	/**
	 * 
	 * @Description: 分页查询营销计划
	 * @param campaign
	 * @param dataModel
	 * @return
	 * @return List<Campaign>
	 * @throws
	 */
	public List<Campaign> query(CampaignDto campaign, DataGridModel dataModel);

	/**
	 * 
	 * @Description: 查询营销计划总条数
	 * @param campaign
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer queryCount(CampaignDto campaign);

	/**
	 * 
	 * @Description: 查询所有营销计划列表
	 * @return
	 * @return List<Campaign>
	 * @throws
	 */
	public List<Campaign> queryList();

	/**
	 * 
	 * @Description: 查询inbound进线营销计划
	 * @param tollFreeNum
	 * @param callTime
	 * @return
	 * @return List<CampaignDto>
	 * @throws
	 */
	public List<CampaignDto> queryInboundCampaign(String tollFreeNum,
			String callTime);

	/**
	 * 
	 * @Description: 查询老客户营销计划
	 * @param execDepartment
	 * @return
	 * @return List<CampaignDto>
	 * @throws
	 */
	public List<CampaignDto> queryOldCustomerCampaign(String execDepartment);

	/**
	 * 
	 * @Description: 查询营销计划参数值
	 * @param camID
	 * @return
	 * @return List<Object>
	 * @throws
	 */
	public List<Object> queryCampaignParas(long camID);

	/**
	 * 
	 * @Description: 查询营销计划参数值
	 * @param camID
	 * @param camParaID
	 * @return
	 * @return List<Object>
	 * @throws
	 */
	public List<Object> queryCampaignParaValue(long camID, long camParaID);

	/**
	 * 
	 * @Description: 根据jobId查询营销计划列表
	 * @param jobId
	 * @param campaignType
	 * @param date
	 * @return
	 * @return List<Campaign>
	 * @throws
	 */
	public List<Campaign> queryList(String jobId, Integer campaignType,
			Date date);

	/**
	 * 
	 * @Description: 根据营销计划查出cti总数
	 * @param groupCode
	 * @param campaignId
	 * @param now
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer getUserCountForCampaigns(String groupCode, Long campaignId,
			Date now);

	/**
	 * 根据联系人id 获得手机号码
	 * 
	 * @Description: TODO
	 * @param groupCode
	 * @param begin
	 * @param end
	 * @param campaignId
	 * @param now
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryPhoneListForCampaign(
			String groupCode, Integer begin, Integer end, Long campaignId,
			Date now);

}
