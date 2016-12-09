/*
 * @(#)CampaignComponentImpl.java 1.0 2013-4-26下午2:15:25
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.component.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinadrtv.erp.marketing.component.CampaignComponent;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeValueDao;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.dao.SmsBlackListDao;
import com.chinadrtv.erp.marketing.service.SmsSendVarService;
import com.chinadrtv.erp.marketing.util.StringUtil;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignTypeValue;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.model.marketing.CouponCr;
import com.chinadrtv.erp.model.marketing.CouponUse;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.Customers;
import com.chinadrtv.erp.smsapi.dao.CouponConfigDao;
import com.chinadrtv.erp.smsapi.dao.CouponCrDao;
import com.chinadrtv.erp.smsapi.dao.CouponUseDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.model.SmsBatch;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.smsapi.util.BatchIdUtil;
import com.chinadrtv.erp.smsapi.util.CouponIdUtil;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.UUidUtil;
import com.chinadrtv.erp.uc.dao.PotentialContactDao;

/**
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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-26 下午2:15:25
 * 
 */
@Component("campaignComponent")
public class CampaignComponentImpl implements CampaignComponent {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignComponentImpl.class);

	@Autowired
	private SmsSendDao smsSendDao;

	@Autowired
	private SmsBlackListDao smsBlackListDao;

	@Autowired
	private SmsSendVarService smsSendVarService;

	@Autowired
	private GroupSmsSendService groupSmsSendService;

	@Autowired
	private CustomerGroupDao customerGroupDao;

	@Autowired
	private PotentialContactDao potentialContactDao;
	@Autowired
	private CampaignTypeValueDao campaignTypeValueDao;
	@Autowired
	private CouponCrDao couponCrDao;
	@Autowired
	private CouponUseDao couponUseDao;

	@Autowired
	private CouponConfigDao couponConfigDao;

	/**
	 * <p>
	 * Title: SaveByBatchId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsSend
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#SaveByBatchId(com.chinadrtv.erp.smsapi.model.SmsSend)
	 */
	public SmsSend saveByBatchId(String userId, SmssnedDto smssnedDto,
			String department) throws Exception {
		// 批次id
		String batchId = BatchIdUtil.getBatchId();
		// 获得短信内容
		String template = "";
		// 客户群编码
		String groupCode = "";
		// 客户群编码
		String groupName = "";
		template = smssnedDto.getTemplate().toString().split("=")[0];

		/*
		 * 静态变量入库
		 */
		List<Map<String, String>> varList = getVarList(
				smssnedDto.getVarNames(), smssnedDto.getVarValues());
		SmsSendVar smsVar = null;
		for (Map<String, String> var : varList) {
			smsVar = new SmsSendVar();
			smsVar.setBatch_id(batchId);
			smsVar.setCreate_date(new Date());
			smsVar.setCreate_user(userId);
			smsVar.setVar_name(var.get("varName"));
			smsVar.setVar_value(var.get("varValue"));
			template = template
					.replace(var.get("varName"), var.get("varValue"));
			smsSendVarService.save(smsVar);
		}

		// 时间scope
		// groupCode = smssnedDto.getCustomers().split("=")[0];
		// groupName = smssnedDto.getCustomers().split("=")[1];
		SmsSend smsSend = new SmsSend();
		smsSend.setBatchId(batchId);
		smsSend.setCreatetime(new Date());
		smsSend.setCreator(userId);
		logger.info("department" + department);
		smsSend.setDepartment(department);
		// 短信标签 去掉2014 0325
		smsSend.setSigniture("橡果国际");
		smsSend.setGroupName(smssnedDto.getGroupName());
		smsSend.setGroupCode(smssnedDto.getGroupCode());
		smsSend.setSendStatus("1");
		smsSend.setSmsContent(template);
		smsSend.setCreator(userId);
		smsSend.setAllowChannel(smssnedDto.getAllowChannel());
		smsSend.setRealtime(smssnedDto.getRealtime());
		smsSend.setIsreply(smssnedDto.getIsreply());
		smsSend.setPriority(Long.valueOf(smssnedDto.getPriority()));
		smsSend.setTemplateId(smssnedDto.getTemplateId());
		smsSend.setSmsName(smssnedDto.getTemplateTitle());
		smsSend.setTimestamps(new Date());
		smsSend.setType("2");
		smsSend.setUuid(batchId);
		smsSend.setStarttime(DateTimeUtil.sim3.parse(smssnedDto.getStime()));
		smsSend.setEndtime(DateTimeUtil.sim3.parse(smssnedDto.getEtime()));
		smsSend.setSource("marketing");
		smsSend.setBlackListFilter(smssnedDto.getBlackListFilter());
		smsSend.setMainNum(smssnedDto.getMainNum());
		smsSend.setDynamicTemplate(smssnedDto.getDynamicTemplate());
		// 设置时间段 和吞吐量
		smsSend.setTimescope((smssnedDto.getStarttime() == null ? ""
				: smssnedDto.getStarttime())
				+ "="
				+ (smssnedDto.getEndtime() == null ? "" : smssnedDto
						.getEndtime())
				+ "="
				+ (smssnedDto.getMaxsend() == null ? "" : smssnedDto
						.getMaxsend()));
		logger.info("batchId:" + batchId + "timeScope:"
				+ smssnedDto.getStarttime() + "=" + smssnedDto.getEndtime()
				+ "=" + smssnedDto.getMaxsend() + "=" + "creator:" + userId);

		List<String> varLists = StringUtil.getVar(template);
		List<Map<String, String>> list = smsSendVarService
				.getVarByContent(template);

		// 保存smssend
		smsSendDao.saveSmsSend(smsSend);

		return smsSend;

	}

	/**
	 * 
	 * @Description: 解析提交的静态变量值，以便入库
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getVarList(String varNames,
			String varValues) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (!StringUtil.isNullOrBank(varValues)) {
			String[] varNameArray = varNames.split(",");
			String[] varValueArray = varValues.split(",");
			Map<String, String> map = null;
			for (int i = 0; i < varNameArray.length; i++) {
				map = new HashMap<String, String>();
				map.put("varName", varNameArray[i]);
				map.put("varValue", varValueArray[i]);
				list.add(map);
				map = null;
			}
		}
		return list;
	}

	/*
	 * 定时任务发送短信
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#newSmsSend(java
	 * .lang.String)
	 */
	public Map<String, Object> newSmsSend(SmsSend smsSend, Campaign campaign,
			Date now) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("正在创建任务 batchid" + smsSend.getBatchId());
		// TODO Auto-generated method stub
		// 获得短信内容
		String template = smsSend.getSmsContent();
		// 客户群编码
		String groupCode = smsSend.getGroupCode();
		// 客户群编码
		String groupName = smsSend.getGroupName();
		// 时间scope
		List<Map<String, String>> list = null;
		// 获得时间段发送
		if (smsSend.getTimescope() != null
				&& !("==").equals(smsSend.getTimescope())) {
			String times[] = smsSend.getTimescope().split("=");
			list = getTimeScopeList(times[0], times[1], times[2]);
		}
		smsSend.setCampaignId("" + campaign.getId());
		smsSend.setReceiveCount(0l);
		Integer begin = 0;
		Integer end = 0;
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Boolean flag = true;
		// 查出总数 用于分页查询
		Integer phoneCounts = 0;
		CustomerGroup group = customerGroupDao.get(groupCode);

		boolean isLatent = false;
		// 如果是潜客
		if (group.getType() != null
				&& group.getType()
						.equals(Constants.CUSTOMER_TYPE_LATENTCONTACT)) {
			phoneCounts = smsBlackListDao.getUserCountForCampaignPotential(
					groupCode, smsSend.getMainNum(),
					smsSend.getBlackListFilter(), campaign.getId(), now);

			isLatent = true;
		} else {
			phoneCounts = smsBlackListDao.getUserCountForCampaign(groupCode,
					smsSend.getMainNum(), smsSend.getBlackListFilter(),
					campaign.getId(), now);
		}

		if (phoneCounts < 1) {
			smsSend.setSendStatus("2");
			smsSendDao.saveSmsSend(smsSend);
			return null;
		}
		if (StringUtil.isNullOrBank(smsSend.getTemplateId())) {
			smsSend.setSendStatus("0");
			smsSendDao.saveSmsSend(smsSend);
			return null;
		}
		// 执行次数
		Integer x = phoneCounts / temps;
		// 余数
		Integer y = phoneCounts % temps;
		// 文件名
		String filename = DateTimeUtil.sim.format(new Date())
				+ smsSend.getBatchId() + ".csv";
		// 文件保存地址
		String ftpnames = PropertiesUtil.getSmsCsvPath()
				+ DateTimeUtil.sim2.format(new Date()) + "/" + filename;
		if (x > 1) {
			for (int i = 0; i < x; i++) {
				end = temps * (i + 1);
				if ((i + 1) == x) {
					end = end + y;
				}
				phoneListSend(smsSend, smsSend.getBatchId(), groupCode,
						groupName, template, list, begin, end, campaign, now,
						isLatent);
				begin = end;
			}
		} else {
			end = phoneCounts;
			phoneListSend(smsSend, smsSend.getBatchId(), groupCode, groupName,
					template, list, begin, end, campaign, now, isLatent);
		}
		// 调用发送
		try {
			result = groupSmsSendService.batchGroupSend(smsSend.getBatchId(),
					smsSend, filename, ftpnames, list);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("campaign:" + campaign.getId() + "batchid:"
					+ smsSend.getBatchId() + "发送短信失败" + e);
		}
		return result;
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
			List<Map<String, Object>> phoneList) {
		// 匹配手机规则
		Iterator<Map<String, Object>> iter = phoneList.iterator();
		Map<String, Object> temp2 = null;
		while (iter.hasNext()) {
			temp2 = iter.next();
			String s = "" + temp2.get("PHN2");
			if (s.startsWith("0") && s.length() == 12) {
				s = s.substring(1);
				temp2.put("PHN2", s);
			}
			if (StringUtil.isMobileNO(s) != true) {
				iter.remove();
			}
		}
		iter = null;
		return phoneList;
	}

	/**
	 * 
	 * @Description: 优惠券短信过滤
	 * @param phoneList
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> iterPhoneListCoupon(
			List<Map<String, Object>> phoneList, Campaign campaign,
			Map<String, Object> couponMap) {
		// 匹配手机规则
		Iterator<Map<String, Object>> iter = phoneList.iterator();
		Map<String, Object> temp2 = null;
		String phn2 = "";
		String contactId = "";
		String couponId = "";
		String money = "";
		String startTime = "";
		String endTime = "";
		String useMoney = "";
		CouponConfig couponConfig = couponConfigDao.get(campaign
				.getCouponConfigId());
		if (couponConfig != null) {
			money = couponConfig.getCouponValue();
			startTime = DateTimeUtil.sim6.format(couponConfig.getStardt());
			endTime = DateTimeUtil.sim6.format(couponConfig.getEnddt());
			useMoney = couponConfig.getUseMoney();
		}
		while (iter.hasNext()) {
			temp2 = iter.next();
			phn2 = "" + temp2.get("PHN2");
			contactId = "" + temp2.get("CONTACTID");

			if (phn2.startsWith("0") && phn2.length() == 12) {
				phn2 = phn2.substring(1);
				temp2.put("PHN2", phn2);
			}
			if (StringUtil.isMobileNO(phn2) != true) {
				iter.remove();
			} else {
				temp2.put("money", money);
				temp2.put("startTime", startTime);
				temp2.put("endTime", endTime);
				temp2.put("useMoney", useMoney);
				if (couponMap.isEmpty()) {
					couponId = CouponIdUtil.getCouponId();
					couponMap.put(contactId, couponId);
					temp2.put("couponId", couponId);
				} else {
					if (couponMap.get(contactId) != null
							&& !("").equals(contactId)) {
						temp2.put("couponId", couponMap.get(contactId)
								.toString());
					} else {
						couponId = CouponIdUtil.getCouponId();
						temp2.put("couponId", couponId);
						couponMap.put(contactId, couponId);
					}
				}

			}
		}
		iter = null;
		return phoneList;
	}

	/**
	 * 
	 * @Description: 分页发短信
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean phoneListSend(SmsSend smsSend, String batchid,
			String groupCode, String groupName, String template,
			List<Map<String, String>> list, int begin, int end,
			Campaign campaign, Date now, boolean isLatent) {
		List<Map<String, Object>> phoneList = getPhoneList(groupCode,
				smsSend.getMainNum(), smsSend.getBlackListFilter(), begin, end,
				campaign.getId(), now, isLatent);
		// 匹配手机规则
		phoneList = iterPhoneList(phoneList);
		Boolean temp = true;
		CustomerGroup customerGroup = customerGroupDao.get(groupCode);

		// 插入批次表
		insertBatchList(phoneList, batchid, smsSend.getDepartment(),
				smsSend.getCreator(), smsSend.getSmsContent(),
				customerGroup.getType());
		logger.info("campaign:" + campaign.getId() + "=batchid" + batchid
				+ "插入临时表begin" + begin + "=end:" + end);
		return temp;
	}

	/*
	 * (非 Javadoc) <p>Title: newCouponCrs</p> <p>Description: </p>
	 * 
	 * @param campaign
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignService#newCouponCrs(com.
	 * chinadrtv.erp.model.marketing.Campaign)
	 */
	public Map<String, Object> newCouponCrs(Campaign campaign) {
		// TODO Auto-generated method stub
		List<CampaignTypeValue> paramsList = new ArrayList<CampaignTypeValue>();
		SmsSend smsSend = null;
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			paramsList = campaignTypeValueDao.queryList(campaign.getId());
			Map<String, String> paramsMap = new HashMap<String, String>();
			for (CampaignTypeValue typeValue : paramsList) {
				paramsMap.put(typeValue.getCode(), typeValue.getValue());
			}
			SmssnedDto smsSendDto = getSmsSendDto(paramsMap);
			smsSendDto.setGroupCode(campaign.getGroup().getGroupCode());
			smsSendDto.setGroupName(campaign.getGroup().getGroupName());
			// 生成短信批次
			smsSend = saveByBatchId(campaign.getCreateUser(), smsSendDto,
					campaign.getDepartment());
			// 客户群编码
			String groupCode = campaign.getGroup().getGroupCode();
			Integer begin = 0;
			Integer end = 0;
			Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
			// 查出总数 用于分页查询
			Integer phoneCounts = 0;
			CustomerGroup group = customerGroupDao.get(groupCode);
			boolean isLatent = false;
			Date now = new Date();
			List<Map<String, Object>> phoneList = new ArrayList<Map<String, Object>>();

			// 如果是潜客
			if (group.getType() != null
					&& group.getType().equals(
							Constants.CUSTOMER_TYPE_LATENTCONTACT)) {
				phoneCounts = smsBlackListDao.getUserCountForCampaignPotential(
						groupCode, smsSendDto.getMainNum(),
						smsSendDto.getBlackListFilter(), campaign.getId(), now);
				isLatent = true;
			} else {
				phoneCounts = smsBlackListDao.getUserCountForCampaign(
						groupCode, smsSendDto.getMainNum(),
						smsSendDto.getBlackListFilter(), campaign.getId(), now);
			}
			// 执行次数
			Integer x = phoneCounts / temps;
			// 余数
			Integer y = phoneCounts % temps;

			Map<String, Object> couponMap = new HashMap<String, Object>();
			if (x > 1) {
				for (int i = 0; i < x; i++) {
					end = temps * (i + 1);
					if ((i + 1) == x) {
						end = end + y;
					}
					phoneList = getPhoneList(groupCode,
							smsSendDto.getMainNum(),
							smsSendDto.getBlackListFilter(), begin, end,
							campaign.getId(), now, isLatent);
					// 匹配手机规则
					phoneList = iterPhoneListCoupon(phoneList, campaign,
							couponMap);
					insertCoupons(campaign, phoneList, smsSend);
					phoneList = null;
					begin = end;
				}
			} else {
				end = phoneCounts;
				phoneList = getPhoneList(groupCode, smsSendDto.getMainNum(),
						smsSendDto.getBlackListFilter(), begin, end,
						campaign.getId(), now, isLatent);
				// 匹配手机规则
				phoneList = iterPhoneListCoupon(phoneList, campaign, couponMap);
				insertCoupons(campaign, phoneList, smsSend);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("生成优惠券错误" + e.getMessage() + e.getStackTrace());
		}
		results.put("smsSend", smsSend);
		results.put("campaign", campaign);
		return results;
	}

	public Boolean insertCoupons(Campaign campaign,
			List<Map<String, Object>> phoneList, SmsSend smsSend) {
		String uuid = "";
		List<CouponCr> couponCrList = new ArrayList<CouponCr>();
		List<CouponUse> couponUsesList = new ArrayList<CouponUse>();
		CouponCr couponCr = null;
		CouponUse couponUse = null;
		String couponId = "";
		String cid = "";
		String tp = "";
		Boolean flag = false;
		Boolean batchFlag = false;
		Long couponCount = 0l;
		CouponConfig couponConfig = couponConfigDao.get(campaign
				.getCouponConfigId());

		try {
			for (int j = 0; j < phoneList.size(); j++) {
				cid = phoneList.get(j).get("CONTACTID").toString();
				tp = phoneList.get(j).get("PHN2").toString();
				uuid = UUidUtil.getUUid();
				couponId = phoneList.get(j).get("couponId").toString();
				couponCr = new CouponCr();
				couponCr.setCampaignId(campaign.getId());
				couponCr.setContactId(cid);
				couponCr.setCouponId(couponId);
				couponCr.setCouponValue(couponConfig.getCouponValue());
				couponCr.setCrdt(new Date());
				couponCr.setCrusr(campaign.getCreateUser());
				couponCr.setDeliverStatus("0");
				couponCr.setSmsBatchid(smsSend.getBatchId());
				couponCr.setSmsUuid(uuid);
				couponCr.setStartdt(couponConfig.getStardt());
				couponCr.setEnddt(couponConfig.getEnddt());
				couponCr.setStatus("0");
				couponCr.setType(couponConfig.getCouponType());
				couponCr.setPhone(tp);
				couponCr.setUsedDepartment(couponConfig.getUseDeparment());
				couponCr.setMoneyUse(couponConfig.getUseMoney());
				couponCrList.add(couponCr);
				// 添加到use表
				couponUse = new CouponUse();
				couponUse.setCampaignId(campaign.getId());
				couponUse.setContactId(cid);
				couponUse.setCouponType(couponConfig.getCouponType());
				couponUse.setCouponValue(couponConfig.getCouponValue());
				couponUse.setCrdt(new Date());
				couponUse.setCrusr(campaign.getCreateUser());
				couponUse.setPhone(tp);
				couponUse.setSmsBatchid(smsSend.getBatchId());
				couponUse.setSmsUuid(uuid);
				couponUse.setCouponId(couponId);
				couponUsesList.add(couponUse);
			}
			// 批量插入 couponcr
			couponCrDao.saveCouponList(couponCrList);
			logger.info("批量保存couponcr成功");
			couponUseDao.saveCouponUseList(couponUsesList);
			logger.info("批量保存couponuse成功");
			// 插入短信临时表
			batchFlag = insertBatchList(phoneList, smsSend.getBatchId(),
					smsSend.getDepartment(), smsSend.getCreator(),
					smsSend.getSmsContent(), campaign.getGroup().getType());

			// if (batchFlag = true) {
			// if (campaign.getCouponCount() == null) {
			// couponCount = 0l;
			// couponCount = couponCount + phoneList.size();
			// } else {
			// couponCount = campaign.getCouponCount() + phoneList.size();
			// }
			// logger.info("插入短信临时表成功");
			// campaign.setCouponCount(couponCount);
			// campaignDao.saveOrUpdate(campaign);
			// logger.info("更新campaign成功");
			// }
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("插入coupon 失败" + e.getMessage() + e.getStackTrace());
		}
		flag = true;

		return flag;

	}

	public SmssnedDto getSmsSendDto(Map<String, String> smsSet) {
		SmssnedDto smsSendDto = new SmssnedDto();

		Iterator<String> it = smsSet.keySet().iterator();
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			smsSendDto.setParamValues(smsSendDto, key, smsSet.get(key));
		}
		return smsSendDto;
	}

	/*
	 * (非 Javadoc) <p>Title: insertBatchList</p> <p>Description: </p>
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#insertBatchList
	 * (java.util.List)
	 */
	public boolean insertBatchList(List<Map<String, Object>> list,
			String batchId, String department, String creator, String template,
			String type) {
		boolean flag = true;
		List<SmsBatch> smsList = new ArrayList<SmsBatch>();
		List<String> varUsers = new ArrayList<String>();
		Customers customers = new Customers();
		Map contactMap = new HashMap<String, Object>();
		// 保存动态参数
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		String contactId = "";
		try {
			if (list != null) {
				SmsBatch smsBatch = null;
				Map<String, Object> mapsMap = new HashMap<String, Object>();
				for (int i = 0; i < list.size(); i++) {
					smsBatch = new SmsBatch();
					smsBatch.setBatchId(batchId);
					smsBatch.setUuid(UUidUtil.getUUid());
					smsBatch.setTombile(list.get(i).get("PHN2").toString());
					smsBatch.setConnectId(list.get(i).get("CONTACTID")
							.toString());
					smsBatch.setTimestamps(new Date());
					contactId = "" + list.get(i).get("CONTACTID");
					if (template.contains("{user.name}")
							|| template.contains("{user.gender}")
							|| template.contains("{user.money}")
							|| template.contains("{user.couponId}")
							|| template.contains("{user.startTime}")
							|| template.contains("{user.endTime}")
							|| template.contains("{user.useMoney}")) {
						try {
							// 判断优惠券
							if (list.get(i).get("couponId") != null
									&& !("").equals(list.get(i).get("couponId"))) {
								customers.setCouponId(list.get(i)
										.get("couponId").toString());
							}
							if (list.get(i).get("money") != null
									&& !("").equals(list.get(i).get("money"))) {
								customers.setMoney(list.get(i).get("money")
										.toString());
							}
							if (list.get(i).get("startTime") != null
									&& !("").equals(list.get(i)
											.get("startTime"))) {
								customers.setStartTime(list.get(i)
										.get("startTime").toString());
							}
							if (list.get(i).get("endTime") != null
									&& !("").equals(list.get(i).get("endTime"))) {
								customers.setEndTime(list.get(i).get("endTime")
										.toString());
							}
							if (list.get(i).get("useMoney") != null
									&& !("").equals(list.get(i).get("useMoney"))) {
								customers.setUseMoney(list.get(i)
										.get("useMoney").toString());
							}

							// 判断是否为潜客
							if (type.equals("1")) {
								paramList = smsBlackListDao
										.getContact(contactId);
							} else {
								PotentialContact potentialContact = potentialContactDao
										.getByContactId(Long.valueOf(contactId));
								mapsMap.put("name", potentialContact.getName());
								mapsMap.put("sex", potentialContact.getGender());
								paramList.add(mapsMap);
								mapsMap.clear();
							}
							if (paramList != null && !paramList.isEmpty()) {
								contactMap = smsBlackListDao.getContact(
										contactId).get(0);
								customers.setName("" + contactMap.get("name"));
								if (contactMap.get("sex") != null
										&& !("").equals(""
												+ contactMap.get("sex"))) {
									if (("" + contactMap.get("sex"))
											.equals("1")) {
										customers.setGender("先生");
									} else if (("" + contactMap.get("sex"))
											.equals("2")) {
										customers.setGender("女士");
									} else {
										customers.setGender("先生/女士");
									}
								} else {
									customers.setGender("先生/女士");
								}
								varUsers = smsSendVarService.getVarByObject(
										template, customers);
								if (varUsers != null && !varUsers.isEmpty()) {
									// 动态设置参数
									for (int j = 0; j < varUsers.size(); j++) {
										if (j == 0) {
											smsBatch.setParam1(varUsers.get(j));
										}
										if (j == 1) {
											smsBatch.setParam2(varUsers.get(j));
										}
										if (j == 2) {
											smsBatch.setParam3(varUsers.get(j));
										}
										if (j == 3) {
											smsBatch.setParam4(varUsers.get(j));
										}
										if (j == 4) {
											smsBatch.setParam5(varUsers.get(j));
										}
										if (j == 5) {
											smsBatch.setParam6(varUsers.get(j));
										}
										if (j == 6) {
											smsBatch.setParam7(varUsers.get(j));
										}
										if (j == 7) {
											smsBatch.setParam8(varUsers.get(j));
										}
										if (j == 8) {
											smsBatch.setParam9(varUsers.get(j));
										}
										if (j == 9) {
											smsBatch.setParam10(varUsers.get(j));
										}
									}
								}

							}
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					}
					smsBatch.setCreatetime(new Date());
					smsBatch.setDepartment(department);
					smsBatch.setCreator(creator);
					smsList.add(smsBatch);
					smsBatch = null;
				}
				// 插入批量表
				flag = groupSmsSendService.insertBatch(smsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Description: TODO
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getTimeScopeList(String starttime,
			String endTime, String maxSend) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (starttime != null && endTime != null && maxSend != null
				&& !("").equals(starttime) && !("").equals(endTime)
				&& !("").equals(maxSend)) {
			String[] max = maxSend.split(",");
			// 分段发送开始时间集合
			String[] stime = starttime.split(",");
			// 分段发送结束时间集合
			String[] etime = endTime.split(",");
			for (int i = 0; i < max.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("time", stime[i] + "-" + etime[i]);
				map.put("iops", max[i]);
				list.add(map);
				map = null;
			}
		}
		return list;
	}

	/**
	 * <p>
	 * Title: getPhoneList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#getPhoneList(java
	 *      .util.List)
	 */
	public List<Map<String, Object>> getPhoneList(String groupCode,
			String flag, String blackflag, Integer begin, Integer end,
			Long campaignId, Date now, boolean isLatent) {
		if (isLatent) {
			return smsBlackListDao.queryPhoneListForCampaignPotential(
					groupCode, flag, blackflag, begin, end, campaignId, now);
		} else {
			return smsBlackListDao.queryPhoneListForCampaign(groupCode, flag,
					blackflag, begin, end, campaignId, now);
		}
	}

	/*
	 * coupon定时任务发送短信
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#newSmsSend(java
	 * .lang.String)
	 */
	public Map<String, Object> newSmsSendByCoupon(SmsSend smsSend,
			Campaign campaign, Date now) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("正在创建任务 batchid" + smsSend.getBatchId());
		// TODO Auto-generated method stub
		// 时间scope
		List<Map<String, String>> list = null;
		// 获得时间段发送
		if (smsSend.getTimescope() != null
				&& !("==").equals(smsSend.getTimescope())) {
			String times[] = smsSend.getTimescope().split("=");
			list = getTimeScopeList(times[0], times[1], times[2]);
		}
		smsSend.setCampaignId("" + campaign.getId());
		smsSend.setReceiveCount(0l);
		// 文件名
		String filename = DateTimeUtil.sim.format(new Date())
				+ smsSend.getBatchId() + ".csv";
		// 文件保存地址
		String ftpnames = PropertiesUtil.getSmsCsvPath()
				+ DateTimeUtil.sim2.format(new Date()) + "/" + filename;
		// 调用发送
		try {
			result = groupSmsSendService.batchGroupSend(smsSend.getBatchId(),
					smsSend, filename, ftpnames, list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("campaign:" + campaign.getId() + "batchid:"
					+ smsSend.getBatchId() + "发送短信失败" + e);
		}
		return result;
	}
}
