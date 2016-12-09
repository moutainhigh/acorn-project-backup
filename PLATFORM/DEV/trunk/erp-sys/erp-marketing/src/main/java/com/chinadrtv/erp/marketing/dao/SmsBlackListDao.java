package com.chinadrtv.erp.marketing.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;

/***
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-21 下午3:06:09
 * 
 */
public interface SmsBlackListDao extends
		GenericDao<SmsBlackList, java.lang.String> {

	public void insertBlcakList(SmsBlackList smsBlackList);

	public List<SmsBlackList> querAll();

	public List<Map<String, Object>> queryPhoneList(String groupCode,
			String flag, String blackFlag, Integer begin, Integer end);

	public Integer getUserCount(String groupCode, String phoneFlag,
			String blackFlag);

	public List<Map<String, Object>> getContact(String contactid);

	public List<Map<String, Object>> queryPhoneListForCampaign(
			String groupCode, String flag, String blackFlag, Integer begin,
			Integer end, Long campaignId, Date now);

	public Integer getUserCountForCampaign(String groupCode, String phoneFlag,
			String blackFlag, Long campaignId, Date now);
	
	public List<Map<String, Object>> queryPhoneListForCampaignPotential(
			String groupCode, String flag, String blackFlag, Integer begin,
			Integer end, Long campaignId, Date now);
	
	public Integer getUserCountForCampaignPotential(String groupCode, String phoneFlag,
			String blackFlag, Long campaignId, Date now);

}
