package com.chinadrtv.erp.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dao.NamesMarketingDao;
import com.chinadrtv.erp.marketing.dao.SmsAnswersDao;
import com.chinadrtv.erp.marketing.dao.SmsBlackListDao;
import com.chinadrtv.erp.marketing.dto.SmsAnswerDto;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.service.SmsAnswersService;
import com.chinadrtv.erp.marketing.service.SmsSendVarService;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.marketing.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Customers;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.model.SmsBatch;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.smsapi.util.BatchIdUtil;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.UUidUtil;

/****
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-18 下午2:45:09
 * 
 */
@Service("smsAnswersService")
public class SmsAnswersServiceImpl implements SmsAnswersService {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsAnswersServiceImpl.class);
	@Autowired
	private SmsAnswersDao smsAnswersDao;

	@Autowired
	private SmsBlackListDao smsBlackListDao;
	@Autowired
	private GroupSmsSendService groupSmsSendService;

	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	@Autowired
	private SmsSendDao smsSendDao;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private SmsSendVarService smsSendVarService;
	@Autowired
	private NamesMarketingDao namesMarketingDao;

	/**
	 * 逻辑删除 (非 Javadoc)
	 * <p>
	 * Title: removeSmsAnswer
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param orderhist
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswerService#removeSmsAnswer(com.chinadrtv.erp.marketing.model.SmsAnswer)
	 */

	public void removeSmsAnswer(Long id) {
		// TODO Auto-generated method stub
		SmsAnswer smsAnswer = smsAnswersDao.getSmsAnswerById(id);
		smsAnswer.setState("1");
		smsAnswersDao.save(smsAnswer);
	}

	/**
	 * 返回短信回复数据 (非 Javadoc)
	 * <p>
	 * Title: getSmsAnswer
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsAnswerDto
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswerService#getSmsAnswer(com
	 *      .chinadrtv.erp.marketing.dto.SmsAnswerDto,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */

	public Map<String, Object> getSmsAnswer(SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmsAnswerDto> list = smsAnswersDao.query(smsAnswerDto, dataModel);
		int count = smsAnswersDao.queryCount(smsAnswerDto);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

	/**
	 * 加入黑名单 (非 Javadoc)
	 * <p>
	 * Title: insertBlack
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswerService#insertBlack()
	 */
	public int insertBlack(Long id, String userid) {
		// TODO Auto-generated method stub
		int flag = 0;
		SmsAnswer smsAnswer = smsAnswersDao.getSmsAnswerById(id);
		smsAnswer.setState("2");
		SmsBlackList smsBlackList = new SmsBlackList();
		smsBlackList.setAddTime(new Date());
		try {
			smsBlackList.setPhoneNum(smsAnswer.getMobile());
			smsBlackList.setCreator(userid);
			smsAnswersDao.save(smsAnswer);
			smsBlackListDao.insertBlcakList(smsBlackList);
		} catch (Exception e) {
			// TODO: handle exception
			flag = 1;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询所有黑名单 (非 Javadoc)
	 * <p>
	 * Title: queryList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#queryList()
	 */
	public List<SmsBlackList> queryList() {
		// TODO Auto-generated method stub
		return smsBlackListDao.querAll();
	}

	/**
	 * 获得手机号码 (非 Javadoc)
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
			String flag, String blackflag, Integer begin, Integer end) {
		// TODO Auto-generated method stub
		return smsBlackListDao.queryPhoneList(groupCode, flag, blackflag,
				begin, end);
	}

	/**
	 * 插入短信发送临时表 (非 Javadoc)
	 * <p>
	 * Title: insertBatchList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#insertBatchList
	 *      (java.util.List)
	 */
	public boolean insertBatchList(List<Map<String, Object>> list,
			String batchId, String department, String creator, String template) {
		// TODO Auto-generated method stub
		boolean flag = true;
		List<SmsBatch> smsList = new ArrayList<SmsBatch>();
		List<String> varUsers = new ArrayList<String>();
		Customers customers = new Customers();
		Map contactMap = new HashMap<String, Object>();
		// 保存动态参数
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		try {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					SmsBatch smsBatch = new SmsBatch();
					smsBatch.setBatchId(batchId);
					smsBatch.setUuid(UUidUtil.getUUid());
					smsBatch.setTombile(list.get(i).get("PHN2").toString());
					smsBatch.setConnectId(list.get(i).get("CONTACTID")
							.toString());
					smsBatch.setTimestamps(new Date());
					String contactId = "" + list.get(i).get("CONTACTID");
					if (template.contains("{user.name}")
							|| template.contains("{user.gender}")) {
						try {
							paramList = smsBlackListDao.getContact(contactId);
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
	 * 查询客户群手机数 (非 Javadoc)
	 * <p>
	 * Title: mapList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param phoneFlag
	 * 
	 * @param blackFlag
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#mapList(java.lang
	 *      .String, java.lang.String)
	 */
	public Map mapList(String phoneFlag, String blackFlag, String customers) {
		// TODO Auto-generated method stub
		Date date = new Date();
		logger.info("查询数量开始时间" + DateTimeUtil.sim3.format(date));
		// 查出总数 用于分页查询
		Integer phoneCounts = smsBlackListDao.getUserCount(customers,
				phoneFlag, blackFlag);
		Date date1 = new Date();
		logger.info("查询customers总数量" + DateTimeUtil.sim3.format(date1));
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Integer begin = 0;
		Integer end = 0;
		Integer counts = 0;
		// 得到所有的用户名单
		List<Map<String, Object>> phoneList = new ArrayList<Map<String, Object>>();
		// 匹配手机规则
		Integer x = phoneCounts / temps;
		Integer y = phoneCounts % temps;
		if (x > 1) {
			for (int i = 0; i < x; i++) {
				end = temps * (i + 1);
				if ((i + 1) == x) {
					end = end + y;
				}
				phoneList = smsBlackListDao.queryPhoneList(customers,
						phoneFlag, blackFlag, begin, end);
				begin = end;
				Date date2 = new Date();
				logger.info("分页查询总数量begin：" + begin + "=end:" + end
						+ DateTimeUtil.sim3.format(date2));
				// 匹配手机规则
				Date date3 = new Date();
				phoneList = iterPhoneList(phoneList);
				logger.info("手机号码过滤begin：" + begin + "=end:" + end
						+ DateTimeUtil.sim3.format(date3));
				counts = counts + phoneList.size();
				phoneList = null;
			}
		} else {
			end = phoneCounts;
			phoneList = smsBlackListDao.queryPhoneList(customers, phoneFlag,
					blackFlag, begin, end);
			// 匹配手机规则
			phoneList = iterPhoneList(phoneList);
			counts = phoneList.size();
			phoneList = null;
		}
		Map maps = new HashMap();
		maps.put("PHONES", counts);
		return maps;
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
	 * 营销计划短信 (非 Javadoc)
	 * <p>
	 * Title: mapList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param phoneFlag
	 * 
	 * @param blackFlag
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#mapList(java.lang
	 *      .String, java.lang.String)
	 */
	public Map mapListForCampaign(String phoneFlag, String blackFlag,
			String customers, Long campaignId) {
		// TODO Auto-generated method stub
		// 查出总数 用于分页查询
		Date now = new Date();
		Integer phoneCounts = smsBlackListDao.getUserCountForCampaign(
				customers, phoneFlag, blackFlag, campaignId, now);
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Integer begin = 0;
		Integer end = 0;
		Integer counts = 0;
		// 得到所有的用户名单
		List<Map<String, Object>> phoneList = new ArrayList<Map<String, Object>>();
		// 匹配手机规则
		Integer x = phoneCounts / temps;
		Integer y = phoneCounts % temps;
		if (x > 1) {
			for (int i = 0; i < x; i++) {
				end = temps * (i + 1);
				if ((i + 1) == x) {
					end = end + y;
				}
				phoneList = smsBlackListDao.queryPhoneListForCampaign(
						customers, phoneFlag, blackFlag, begin, end,
						campaignId, now);
				begin = end;
				// 手机号验证
				phoneList = iterPhoneList(phoneList);
				counts = counts + phoneList.size();
				phoneList = null;
			}
		} else {
			end = phoneCounts;
			phoneList = smsBlackListDao.queryPhoneListForCampaign(customers,
					phoneFlag, blackFlag, begin, end, campaignId, now);
			// 手机号验证
			phoneList = iterPhoneList(phoneList);
			counts = phoneList.size();
			phoneList = null;
		}
		Map maps = new HashMap();
		maps.put("PHONES", counts);
		return maps;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsSend
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsAnswerDto
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#getSmsSend(com.
	 *      chinadrtv.erp.marketing.dto.SmssnedDto,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> getSmsSend(SmssnedDto smssendDto,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmssnedDto> list = smsAnswersDao.query(smssendDto, dataModel);
		int count = smsAnswersDao.queryCounts(smssendDto);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCounts
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param batchid
	 * 
	 * @param status
	 * 
	 * @param times
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#getCounts(java.
	 *      lang.String, java.lang.String, java.sql.Date)
	 */
	public Long getCounts(String batchid, String status) {
		// TODO Auto-generated method stub
		Long temp = null;
		try {
			temp = smsSendDetailDao.getCounts(batchid, status);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return temp;
	}

	/*
	 * (非 Javadoc) <p>Title: getTimeList</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @param status
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#getTimeList(java
	 * .lang.String, java.lang.String)
	 */
	public List<Map<String, Object>> getTimeList(String batchid, String status) {
		// TODO Auto-generated method stub
		return smsSendDetailDao.getTimeList(batchid, status);
	}

	public Long getTimeCount(String batchid, String date, String statusTime) {
		return smsSendDetailDao.getTimeCount(batchid, date, statusTime);
	}

	/*
	 * (非 Javadoc) <p>Title: getById</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#getById(java.lang
	 * .String)
	 */
	public SmsSend getById(String batchid) {
		// TODO Auto-generated method stub
		return smsSendDao.getByBatchid(batchid);
	}

	/*
	 * (非 Javadoc) <p>Title: SaveByBatchId</p> <p>Description: </p>
	 * 
	 * @param smsSend
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#SaveByBatchId(com
	 * .chinadrtv.erp.smsapi.module.SmsSend)
	 */
	public Boolean saveByBatchId(String userId, SmssnedDto smssnedDto,
			String department) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		// 批次id
		String batchId = BatchIdUtil.getBatchId();
		// 获得短信内容
		String template = "";
		// 客户群编码
		String groupCode = "";
		// 客户群编码
		String groupName = "";
		try {
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
				template = template.replace(var.get("varName"),
						var.get("varValue"));
				smsSendVarService.save(smsVar);
			}

			// 时间scope
			groupCode = smssnedDto.getCustomers().split("=")[0];
			groupName = smssnedDto.getCustomers().split("=")[1];
			SmsSend smsSend = new SmsSend();
			smsSend.setBatchId(batchId);
			smsSend.setCreatetime(new Date());
			smsSend.setCreator(userId);
			logger.info("department" + department);
			smsSend.setDepartment(department);
			smsSend.setSigniture("橡果国际");
			smsSend.setGroupName(groupName);
			smsSend.setGroupCode(groupCode);
			smsSend.setSendStatus("1");
			smsSend.setSmsContent(template);
			smsSend.setCreator(userId);
			smsSend.setAllowChannel(smssnedDto.getAllowChannel());
			smsSend.setRealtime(smssnedDto.getRealtime());
			smsSend.setIsreply(smssnedDto.getIsreply());
			smsSend.setPriority(Long.valueOf(smssnedDto.getPriority()));
			smsSend.setTemplateId(smssnedDto.getTemplate().toString()
					.split("=")[1]);
			smsSend.setSmsName(smssnedDto.getTemplate().toString().split("=")[2]);
			smsSend.setTimestamps(new Date());
			smsSend.setType("2");
			smsSend.setUuid(batchId);
			smsSend.setStarttime(DateTimeUtil.sim3.parse(smssnedDto.getStime()));
			smsSend.setEndtime(DateTimeUtil.sim3.parse(smssnedDto.getEtime()));
			smsSend.setSource("marketing");
			smsSend.setBlackListFilter(smssnedDto.getBlackListFilter());
			smsSend.setMainNum(smssnedDto.getMainNum());
			// 设置时间段 和吞吐量
			smsSend.setTimescope(smssnedDto.getStarttime() + "="
					+ smssnedDto.getEndtime() + "=" + smssnedDto.getMaxsend());
			logger.info("batchId:" + batchId + "timeScope:"
					+ smssnedDto.getStarttime() + "=" + smssnedDto.getEndtime()
					+ "=" + smssnedDto.getMaxsend() + "=" + "creator:" + userId);

			List<String> varLists = StringUtil.getVar(template);
			List<Map<String, String>> list = smsSendVarService
					.getVarByContent(template);

			// 保存smssend
			smsSendDao.saveSmsSend(smsSend);
			JobCronSet jobCronSet = new JobCronSet();
			jobCronSet.setFrequency("0");// 设定job立即执行
			jobCronSet.setGroup(Constants.JOB_GROUP_SMS_SEND);
			jobCronSet.setJobName(batchId);
			schedulerService.scheduleAddJob(jobCronSet);

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("batchId:" + batchId + "创建失败" + e);
		}
		return flag;

	}

	/**
	 * 定时任务发送短信
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsAnswersService#newSmsSend(java
	 *      .lang.String)
	 */
	public Boolean newSmsSend(String batchid) {
		logger.info("正在创建任务 batchid" + batchid);
		// TODO Auto-generated method stub
		// 获得smssend
		SmsSend smsSend = smsSendDao.getByBatchid(batchid);
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
		Integer begin = 0;
		Integer end = 0;
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Boolean flag = true;
		// 查出总数 用于分页查询
		Integer phoneCounts = smsBlackListDao.getUserCount(groupCode,
				smsSend.getMainNum(), smsSend.getBlackListFilter());
		// 执行次数
		Integer x = phoneCounts / temps;
		// 余数
		Integer y = phoneCounts % temps;
		groupSmsSendService.newFileDirs();
		// 文件名
		String filename = DateTimeUtil.sim.format(new Date()) + batchid
				+ ".csv";
		String ftpnames = PropertiesUtil.getSmsCsvPath()
				+ DateTimeUtil.sim2.format(new Date()) + "/" + filename;
		if (x > 1) {
			for (int i = 0; i < x; i++) {
				end = temps * (i + 1);
				if ((i + 1) == x) {
					end = end + y;
				}
				// 插入批次数据
				phoneListSend(smsSend, batchid, groupCode, groupName, template,
						list, begin, end, ftpnames, filename);
				begin = end;
			}
		} else {
			end = phoneCounts;
			// 插入批次数据
			phoneListSend(smsSend, batchid, groupCode, groupName, template,
					list, begin, end, ftpnames, filename);
		}
		try {
			// 调用发送
			// flag = groupSmsSendService.batchGroupSend(smsSend.getBatchId(),
			// smsSend, filename, ftpnames, list);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("batchid:" + smsSend.getBatchId() + "发送短信失败" + e);
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 匹配手机规则，插入临时表
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean phoneListSend(SmsSend smsSend, String batchid,
			String groupCode, String groupName, String template,
			List<Map<String, String>> list, int begin, int end,
			String ftpnames, String filename) {
		List<Map<String, Object>> phoneList = getPhoneList(groupCode,
				smsSend.getMainNum(), smsSend.getBlackListFilter(), begin, end);
		// 匹配手机规则
		phoneList = iterPhoneList(phoneList);
		Boolean temp = true;
		// 插入批次表
		insertBatchList(phoneList, batchid, smsSend.getDepartment(),
				smsSend.getCreator(), smsSend.getSmsContent());
		logger.info(batchid + "插入临时表begin" + begin + "=end:" + end);
		return temp;
	}

	/**
	 * 解析 timeScope
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

	public static void main(String[] args) {
		// String endpoint = "http://3tong.cn:8086/ds/services/getByKw?wsdl";
		// IKeywordServiceProxy ywsnp = new IKeywordServiceProxy();
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Integer count = 11;
		// Integer begin = 0;
		// Integer end = 0;
		// Integer temp = 3;
		// Integer x = count / temp;
		// Integer y = count % temp;
		// if (x > 1) {
		// for (int i = 0; i < x; i++) {
		// begin = temp * i;
		// end = temp * (i + 1);
		// System.out.println(begin + "==" + end);
		// if ((i + 1) == x) {
		// System.out.println(end + "==" + (end + y));
		// }
		// }
		// } else {
		// System.out.println(0 + "==" + (y));
		// }
	}

	/*
	 * (非 Javadoc) <p>Title: queryContactidByPhone</p> <p>Description: </p>
	 * 
	 * @param phone
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsAnswersService#queryContactidByPhone
	 * (java.lang.String)
	 */
	public List queryContactidByPhone(String phone) {
		// TODO Auto-generated method stub
		return namesMarketingDao.queryContactidByPhone(phone);
	}

	/**
	 * 根据营销计划查询短信批次列表
	 */
	public Map<String, Object> getSmsSendByCampaignId(Long campaignId,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmssnedDto> list = namesMarketingDao.queryByCampaign(campaignId,
				dataModel);
		int count = namesMarketingDao.queryCountsByCampaign(campaignId);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

}
