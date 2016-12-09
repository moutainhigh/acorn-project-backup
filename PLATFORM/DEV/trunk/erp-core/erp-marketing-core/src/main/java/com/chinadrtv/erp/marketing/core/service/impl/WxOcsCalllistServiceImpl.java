/*
 * @(#)WxOcsCalllistServiceImpl.java 1.0 2013-12-16下午3:01:33
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.WxOcsCalllistDao;
import com.chinadrtv.erp.marketing.core.service.WxOcsCalllistService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-16 下午3:01:33
 * 
 *        预测外呼 注释-------2014年3月24日
 */
// @Service("wxOcsCalllistService")
public class WxOcsCalllistServiceImpl implements WxOcsCalllistService {
	// @Autowired
	private WxOcsCalllistDao wxOcsCalllistDao;
	// @Autowired
	private CampaignDao campaignDao;

	private static final Logger logger = LoggerFactory
			.getLogger(WxOcsCalllistServiceImpl.class);

	public Boolean batchInsert(Campaign campaign, Date now) {
		// TODO Auto-generated method stub
		Integer begin = 0;
		Integer end = 0;
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Map<String, Object> ctiMap = new HashMap<String, Object>();
		logger.info("营销计划预测外呼 campaignid step1" + campaign.getId());
		try {
			// 查出总数 用于分页查询
			Integer phoneCounts = campaignDao.getUserCountForCampaigns(campaign
					.getGroup().getGroupCode(), campaign.getId(), now);
			logger.info("营销计划预测外呼 campaignid step2" + campaign.getId()
					+ " 总客户数" + phoneCounts);
			// 执行次数
			Integer x = phoneCounts / temps;
			// 余数
			Integer y = phoneCounts % temps;
			if (x > 1) {
				for (int i = 0; i < x; i++) {
					end = temps * (i + 1);
					if ((i + 1) == x) {
						end = end + y;
					}
					phoneList(begin, end, campaign, now, ctiMap);
					begin = end;
				}
			} else {
				end = phoneCounts;
				phoneList(begin, end, campaign, now, ctiMap);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @Description: 分页插入cti表
	 * @param begin
	 * @param end
	 * @param groupCode
	 * @return
	 * @return Boolean
	 * @throws
	 */
	private Boolean phoneList(int begin, int end, Campaign campaign, Date now,
			Map<String, Object> ctiMap) {
		Boolean temp = false;
		try {
			List<Map<String, Object>> phoneList = campaignDao
					.queryPhoneListForCampaign(campaign.getGroup()
							.getGroupCode(), begin, end, campaign.getId(), now);
			logger.info("营销计划预测外呼 campaignid step3" + campaign.getId()
					+ " 总客户电话号数" + phoneList.size());
			phoneList = iterPhoneList(phoneList, ctiMap, campaign);
			// 插入cti表中
			wxOcsCalllistDao.batchInsert(phoneList);
			temp = true;
		} catch (Exception e) {
			logger.error("营销计划预测外呼 campaignid " + campaign.getId() + " 错误信息"
					+ e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}

		return temp;

	}

	/**
	 * 
	 * @Description: 过滤短信
	 * @param phoneList
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> iterPhoneList(
			List<Map<String, Object>> phoneList, Map<String, Object> ctiMap,
			Campaign campaign) {
		// 匹配手机规则
		Iterator<Map<String, Object>> iter = phoneList.iterator();
		// 添加到备份list
		List<Map<String, Object>> phoneListBak = new ArrayList<Map<String, Object>>();
		// 备份迭代map
		Map<String, Object> temp3 = null;
		// 迭代map
		Map<String, Object> temp2 = null;
		Long contactId = 0l;
		String phn2 = "";
		String phn1 = "";
		String phn3 = "";
		String contact_info = "";
		Long chan_n = 0l;
		Long batch = campaign.getId();
		Long daily_from = 0l;
		Long daily_till = 0l;
		// 累加数字
		Long temps = 0l;
		while (iter.hasNext()) {
			temp2 = iter.next();
			phn2 = "" + temp2.get("PHN2");
			phn1 = "" + temp2.get("PHN1");
			phn3 = "" + temp2.get("PHN3");
			contactId = Long.valueOf(temp2.get("CONTACTID").toString());
			chan_n = Long.valueOf(temp2.get("CHAIN_N").toString()) - 1l;
			if (phn2.startsWith("0") && phn2.length() == 12) {
				phn2 = phn2.substring(1);
			}
			if (!"null".equals(phn1) && phn1.length() >= 2) {
				contact_info = phn1 + phn2;
				if (!"null".equals(phn3) && phn3.length() >= 2) {
					contact_info = phn1 + phn2 + ",,,,,,,,,," + phn3;
				}
			} else {
				contact_info = phn2;
			}
			daily_from = Long.valueOf("" + campaign.getStartDate().getHours())
					* 3600
					+ Long.valueOf("" + campaign.getStartDate().getMinutes())
					* 60
					+ Long.valueOf("" + campaign.getStartDate().getSeconds());

			daily_till = Long.valueOf("" + campaign.getEndDate().getHours())
					* 3600
					+ Long.valueOf("" + campaign.getEndDate().getMinutes())
					* 60
					+ Long.valueOf("" + campaign.getEndDate().getSeconds());
			temp2.put("daily_from", daily_from);
			temp2.put("daily_till", daily_till);
			temp2.put("batch", batch);
			temp2.put("chain_id", contactId);
			if (phn2.length() == 11) {
				// 处理多添加一条到数据里
				temp3 = new HashMap<String, Object>();
				temp3.putAll(temp2);
				if (chan_n == 0l) {
					temps = 0l;
					temp3.put("contact_info", "80" + contact_info);
					temp2.put("chain_n", temps);
					temps = temps + 1;
					temp3.put("chain_n", temps);
					temps++;
				} else {
					temp2.put("chain_n", temps);
					temps = temps + 1;
					temp3.put("chain_n", temps);
					temp3.put("contact_info", "80" + contact_info);
					temps++;
				}
				phoneListBak.add(temp3);
			} else {
				if (chan_n == 0l) {
					temps = 0l;
					temp2.put("chain_n", temps);
					temps++;
				} else {
					temp2.put("chain_n", temps);
					temps++;
				}
			}
			temp2.put("contact_info", "8" + contact_info);
			phoneListBak.add(temp2);
		}
		logger.info("营销计划预测外呼 campaignid step4" + campaign.getId() + " 总客户电话号数"
				+ phoneList.size());
		iter = null;
		return phoneListBak;
	}
}
