/*
 * @(#)SmsController.java 1.0 2013-1-
18上午9:54:17
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.marketing.dto.SmsAnswerDto;
import com.chinadrtv.erp.marketing.dto.SmsSendDetailDto;
import com.chinadrtv.erp.marketing.dto.SmsTemplateDto;
import com.chinadrtv.erp.marketing.service.BaseConfigService;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.marketing.service.CustomerGroupService;
import com.chinadrtv.erp.marketing.service.NamesMarketingService;
import com.chinadrtv.erp.marketing.service.SmsAnswersService;
import com.chinadrtv.erp.marketing.service.SmsSendDetailService;
import com.chinadrtv.erp.marketing.service.SmsSendVarService;
import com.chinadrtv.erp.marketing.service.SmsTemplateService;
import com.chinadrtv.erp.marketing.util.JfreeChartUtils;
import com.chinadrtv.erp.marketing.util.StringUtil;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/****
 * 短信回复列表
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-18 上午10:04:05
 * 
 */
@Controller
@RequestMapping("sms")
public class SmsController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsController.class);

	@Autowired
	private SmsAnswersService smsAnswerService;

	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private GroupSmsSendService groupSmsSendService;
	@Autowired
	private CustomerGroupService customerGroupService;

	@Autowired
	private NamesMarketingService namesMarketingService;

	@Autowired
	private SmsSendVarService smsSendVarService;
	@Autowired
	private SmsSendDetailService smsSendDetailService;

	@Autowired
	private BaseConfigService baseConfigService;

	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	@Autowired
	private SmsSendStatusService smsSendStatusService;

	/**
	 * 短信监控
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param batchid
	 * @return
	 * @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("openWinReport")
	public ModelAndView getAllReportRule(HttpServletRequest request,
			HttpServletResponse response, String batchid) throws Exception {

		ModelAndView modelAndView = new ModelAndView("sms/getAllReportRule");
		// 创建LineDraw对象，调用makeLine方法得到折线图
		JfreeChartUtils lineDraw = new JfreeChartUtils();
		List<String[]> list1 = new ArrayList<String[]>();
		List<String[]> list2 = new ArrayList<String[]>();
		List<Map<String, Object>> feedBackList = smsAnswerService.getTimeList(
				batchid, "feedback_status_time");
		List<Map<String, Object>> receiveList = smsAnswerService.getTimeList(
				batchid, "receive_status_time");
		String fileName = "";
		// 已发送总量
		Integer receiveCount = 0;
		// 已回执总量
		Integer feedbackCount = 0;
		if (feedBackList != null && !feedBackList.isEmpty()
				&& feedBackList.get(0) != null) {
			for (int i = 0; i < feedBackList.size(); i++) {
				if (feedBackList.get(i) != null) {
					String[] z1 = {
							"" + feedBackList.get(i).get("count").toString(),
							"回执到达量", feedBackList.get(i).get("time").toString() };
					list1.add(z1);
				}
			}
			for (int i = 0; i < list1.size(); i++) {
				// 计算已回执数量
				feedbackCount = feedbackCount
						+ Integer.valueOf(list1.get(i)[0].toString());
			}
		}
		if (receiveList != null && !receiveList.isEmpty()
				&& receiveList.get(0) != null) {
			for (int i = 0; i < receiveList.size(); i++) {
				if (receiveList.get(i) != null) {
					String[] z2 = {
							"" + receiveList.get(i).get("count").toString(),
							"发送到达量", receiveList.get(i).get("time").toString() };
					list2.add(z2);
				}
			}
			for (int i = 0; i < list2.size(); i++) {
				// 计算已回执数量
				receiveCount = receiveCount
						+ Integer.valueOf(list2.get(i)[0].toString());
			}
			fileName = lineDraw.makeLine("到达量", "时间", "数量", 1500, 1000,
					request, list2, list1);
		}
		SmsSend smsSend = smsAnswerService.getById(batchid);
		// 把得到的图片放到request范围里面
		request.setAttribute("fileName", fileName);
		request.setAttribute("batchid", batchid);
		request.setCharacterEncoding("utf-8");
		request.setAttribute("receiveCount", receiveCount);
		request.setAttribute("feedbackCount", feedbackCount);
		request.setAttribute("count",
				smsSendDetailService.queryCountByBatchid(batchid));
		String campaignId = smsSend.getCampaignId();
		if (campaignId != null) {
			Campaign campaign = campaignService.getCampaignById(Long
					.valueOf(campaignId));
			if (campaign.getCampaignTypeId() == 5) {
				request.setAttribute("stopFlag", "1");
			} else {
				request.setAttribute("stopFlag", "0");
			}
		}
		modelAndView.addObject("smssend", smsSend);
		return modelAndView;
	}

	/**
	 * 查看sla信息
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param batchid
	 * @return
	 * @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("openWinSmsSla")
	public ModelAndView smsSla(HttpServletRequest request,
			HttpServletResponse response, String batchid) throws Exception {
		SmsSend smsSend = smsAnswerService.getById(batchid);
		ModelAndView modelAndView = new ModelAndView("sms/smsSla");
		modelAndView.addObject("smsSend", smsSend);
		modelAndView.addObject("startTime",
				DateTimeUtil.sim3.format(smsSend.getStarttime()));
		modelAndView.addObject("endTime",
				DateTimeUtil.sim3.format(smsSend.getEndtime()));
		List<SmsSendVar> varList = smsSendVarService.query(batchid);
		modelAndView.addObject("varList", varList);
		List<Map<String, String>> list = null;
		// 获得短信模板
		SmsTemplate smsTemplate = smsTemplateService.getById(Long
				.valueOf(smsSend.getTemplateId()));

		// 获得时间段发送
		if (smsSend.getTimescope() != null
				&& !("==").equals(smsSend.getTimescope())) {
			String times[] = smsSend.getTimescope().split("=");
			list = smsAnswerService.getTimeScopeList(times[0], times[1],
					times[2]);
		}
		modelAndView.addObject("timeScopeList", list);
		modelAndView.addObject("smsTemplate", smsTemplate);
		return modelAndView;
	}

	/**
	 * 客户群分组
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "grouplist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGroupList(HttpServletRequest request,
			CustomerGroupDto group, DataGridModel dataModel) {
		group.setStatus("Y");
		Map<String, Object> reuslt = customerGroupService.query(group,
				dataModel);
		return reuslt;
	}

	/**
	 * 短信模板分组
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "smslist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSmsList(HttpServletRequest request,
			SmsTemplateDto smsTemplateDto, DataGridModel dataModel) {
		smsTemplateDto.setStatus("1");
		if (!StringUtil.isNullOrBank(smsTemplateDto.getThemeTemplate())) {
			if (smsTemplateDto.getThemeTemplate().equals("123")) {
				smsTemplateDto.setThemeTemplate("单条优惠券");
			}
			if (smsTemplateDto.getThemeTemplate().equals("456")) {
				smsTemplateDto.setThemeTemplate("批量优惠券");
			}
			if (smsTemplateDto.getThemeTemplate().equals("789")) {
				smsTemplateDto.setThemeTemplate("");
			}
		}
		Map<String, Object> reuslt = smsTemplateService.findPageList(
				smsTemplateDto, dataModel);
		return reuslt;
	}

	@RequestMapping(value = "stopSms", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopSms(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String batchId = request.getParameter("batchId");
		SmsSend smsSends = smsAnswerService.getById(batchId);
		Long counts = 0l;
		try {
			SmsSend smsSend = groupSmsSendService.smsStop(batchId,
					smsSends.getDepartment());
			if (smsSend.getSendStatus().equals("4")) {
				if (smsSend.getErrorCode() != null
						&& smsSend.getErrorCode().equals("r:999")) {
					if (smsSend.getSendStatus().equals("7")) {
						result.put("result", "短信已发送结束无法停止！");
					}
					if (smsSend.getSendStatus().equals("8")) {
						result.put("result", "短信还未发送到大汉平台，无法停止！");
					}
				} else {
					result.put("result", "停止失败");
				}
				logger.info("batchId:" + batchId + "==任务停止失败");
			} else {
				logger.info("batchId:" + batchId + "==任务停止成功");
				result.put("result", "停止成功");
			}
		} catch (Exception e) {
			result.put("result", "停止失败");
			logger.error("batchId:" + batchId + "==任务停止失败" + e);
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 短信回复列表显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView msgList(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sms/index");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("startTimes", sim.format(new Date()) + " 00:00:00");
		request.setAttribute("endTimes", sim.format(new Date()) + " 23:59:59");
		return mav;
	}

	/***
	 * 短信回复列表json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "smsList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response, SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = smsAnswerService.getSmsAnswer(smsAnswerDto, dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 短信发送详情列表显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "detailsIndex", method = RequestMethod.GET)
	public ModelAndView msgDetailList(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sms/detailsIndex");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("startTimes", sim.format(new Date()) + " 00:00:00");
		request.setAttribute("endTimes", sim.format(new Date()) + " 23:59:59");
		return mav;
	}

	/***
	 * 短信发送详情json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "detailesList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailesList(HttpServletRequest request,
			HttpServletResponse response, SmsSendDetailDto smsSendDetail,
			DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = smsSendDetailService.getSmsSendDetailList(smsSendDetail,
					dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 短信发送列表json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "smsSendList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> snedList(HttpServletRequest request,
			HttpServletResponse response, SmssnedDto smssnedDto,
			DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = smsAnswerService.getSmsSend(smssnedDto, dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/****
	 * 删除回复短信 （逻辑删除）
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param smsAnswerDto
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "deleteObjects", method = RequestMethod.POST)
	@ResponseBody
	public void deleteObjects(HttpServletRequest request,
			HttpServletResponse response, SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel) {
		String ids = request.getParameter("ids");
		String[] id = ids.split(",");
		// SmsAnswer smsAnswer = new SmsAnswer();
		for (int i = 0; i < id.length; i++) {
			smsAnswerService.removeSmsAnswer(Long.valueOf(id[i]));
		}
	}

	/*****
	 * 添加黑名单
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param smsAnswerDto
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "insertBlack", method = RequestMethod.POST)
	@ResponseBody
	public void insertBlack(HttpServletRequest request,
			HttpServletResponse response, SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel) {

		String ids = request.getParameter("ids");
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			smsAnswerService.insertBlack(Long.valueOf(id[i]),
					this.getUserId(request).toString());
		}
	}

	/***
	 * 短息发送列表
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "sendList", method = RequestMethod.GET)
	public ModelAndView sendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("startTimes", sim.format(new Date()) + " 00:00:00");
		request.setAttribute("endTimes", sim.format(new Date()) + " 23:59:59");
		// 查出所有用户组
		ModelAndView mav = new ModelAndView("sms/sendList");
		mav.addObject("templatelist", smsTemplateService.queryList());
		return mav;
	}

	/**
	 * 短信发送保存
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param smssnedDto
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saves(HttpServletRequest request,
			HttpServletResponse response, SmssnedDto smssnedDto) {
		// 发送量集合
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 保存smssend
			if (smsAnswerService.saveByBatchId(this.getUserId(request),
					smssnedDto, this.getDepartmentId(request))) {
				result.put("result", "正在创建中");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "创建失败");
		}
		return result;
	}

	/***
	 * 查询数量
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param smssnedDto
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "counts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCounts(HttpServletRequest request,
			HttpServletResponse response, SmssnedDto smssnedDto) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps = smsAnswerService.mapList(smssnedDto.getMainNum(),
				smssnedDto.getBlackListFilter(), smssnedDto.getCustomers());
		return maps;
	}

	/**
	 * 获得短信模板内容
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param smssnedDto
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "templates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTemplates(HttpServletRequest request,
			HttpServletResponse response, SmssnedDto smssnedDto) {
		Map<String, Object> maps = new HashMap<String, Object>();
		// 获得短信内容
		String template = smsTemplateService
				.getById(
						Long.valueOf(smssnedDto.getTemplate().toString()
								.split("=")[0])).getContent();
		maps.put("content", template);
		maps.put("size", template.length());
		return maps;
	}

	/**
	 * 
	 * @Description: 导航
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "templateIndex", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sms/templateList");
		List<Names> namesList = namesMarketingService.queryNamesList();
		List<BaseConfig> baseList = baseConfigService.query("THEME_TEMPLATE");
		mav.addObject("namesList", namesList);
		mav.addObject("baseList", baseList);
		return mav;
	}

	/**
	 * 
	 * @Description: 短信模板分页查询
	 * @param request
	 * @param response
	 * @param smsTemplateDto
	 * @param dataModel
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "templateList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response, SmsTemplateDto smsTemplateDto,
			DataGridModel dataModel) throws IOException {

		Map<String, Object> resutlMap = smsTemplateService.findPageList(
				smsTemplateDto, dataModel);

		return resutlMap;
	}

	/**
	 * 查看敏感词
	 * 
	 * @Description: TODO
	 * @param smsTemplate
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "viewStopWords", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewStopWords(SmsTemplate smsTemplate,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> list = smsTemplateService.templateFilter(smsTemplate);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list != null && !list.isEmpty()) {
			StringBuilder wordfilter = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				wordfilter.append(list.get(i) + ",");
			}
			resultMap.put(
					"wordfilter",
					wordfilter.toString().substring(0,
							wordfilter.toString().lastIndexOf(",")));
		}
		return resultMap;
	}

	/**
	 * 
	 * @Description: 短信模板保存
	 * @param smsTemplate
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "templateSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(SmsTemplate smsTemplate,
			HttpServletRequest request, HttpServletResponse response) {
		List<BaseConfig> baseConfigs = new ArrayList<BaseConfig>();
		if (smsTemplate.getThemeTemplate() != null
				&& smsTemplate.getThemeTemplate().equals("0")) {
			smsTemplate.setThemeTemplate("未分类");
		} else {
			baseConfigs = baseConfigService.queryByValue(smsTemplate
					.getThemeTemplate());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// List<String> list = smsTemplateService.templateFilter(smsTemplate);
		// Boolean flag = false;
		// // 判断短信主题是否需要过滤敏感词
		// if (baseConfigs != null && !baseConfigs.isEmpty()) {
		// for (int i = 0; i < baseConfigs.size(); i++) {
		// if (baseConfigs.get(i).getParaName().equals("营销")) {
		// flag = true;
		// }
		// }
		// }
		// if (list != null && !list.isEmpty() && flag == true) {
		// StringBuilder wordfilter = new StringBuilder();
		// for (int i = 0; i < list.size(); i++) {
		// wordfilter.append(list.get(i) + ",");
		// }
		// resultMap.put(
		// "wordfilter",
		// wordfilter.toString().substring(0,
		// wordfilter.toString().lastIndexOf(",")));
		// } else {
		if (null == smsTemplate || smsTemplate.getId() == null) {
			smsTemplate.setCode(smsTemplateService.getNextVal());
			smsTemplate.setCrtuid(this.getUserId(request));
			smsTemplate.setCrttime(new Date());
		} else {
			smsTemplate.setUpduid(this.getUserId(request));
			smsTemplate.setUpdtime(new Date());
		}
		// 判断是否为动态模板
		if (smsTemplate.getContent().contains("{user.name}")
				|| smsTemplate.getContent().contains("{user.gender}")) {
			smsTemplate.setDynamicTemplate("1");
		} else {
			smsTemplate.setDynamicTemplate("0");
		}
		smsTemplate.setDeptid(getDepartmentId(request));
		try {
			smsTemplateService.saveSmsTemplate(smsTemplate);
			resultMap.put("success", true);
		} catch (Exception e) {
			resultMap.put("success", false);
			e.printStackTrace();
		}
		// }
		return resultMap;
	}

	/**
	 * 
	 * @Description: 短信模板删除
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "templateDelete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam(required = true) String ids,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			smsTemplateService.deleteSmsTemplate(ids);
			resultMap.put("success", true);
		} catch (Exception e) {
			resultMap.put("success", false);
			e.printStackTrace();
		}

		return resultMap;
	}

	/**
	 * 
	 * @Description: 动态解析短信模板静态变量集合
	 * @param content
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	@RequestMapping(value = "getStaticVar", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getStaticVar(String content) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		if (!StringUtil.isNullOrBank(content)) {
			resultList = smsSendVarService.getVarByContent(content);
		}
		return resultList;
	}

	/***
	 * 短信发送详情列表显示_营销计划
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinDetailsIndex", method = RequestMethod.GET)
	public ModelAndView detailsIndexForCampaign(HttpServletRequest request,
			HttpServletResponse response, Long campaignId) {
		ModelAndView mav = new ModelAndView("sms/detailsIndexForCampaign");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("startTimes", sim.format(new Date()) + " 00:00:00");
		request.setAttribute("endTimes", sim.format(new Date()) + " 23:59:59");

		mav.addObject("campaignId", campaignId);
		return mav;
	}

	/***
	 * 短信回复列表json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "detailesListForCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailesListForCampaign(
			HttpServletRequest request, HttpServletResponse response,
			SmsSendDetailDto smsSendDetail, DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = smsSendDetailService.getSmsDetailListForcampaign(
					smsSendDetail, dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/***
	 * 短信发送列表json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "smsSendListByCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> smsSendListByCampaign(
			HttpServletRequest request, HttpServletResponse response,
			Long campaignId, DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = smsAnswerService.getSmsSendByCampaignId(campaignId,
					dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "countsForCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countsForCampaign(HttpServletRequest request,
			HttpServletResponse response, SmssnedDto smssnedDto, Long campaignId) {

		Campaign campaign = campaignService.getCampaignById(campaignId);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps = smsAnswerService.mapListForCampaign(smssnedDto.getMainNum(),
				smssnedDto.getBlackListFilter(), campaign.getAudience(),
				campaignId);
		return maps;
	}

	/**
	 * 
	 * @Description: campaign 短信发送速度 分页
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "campaignMonitorList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CampaignMonitorList(HttpServletRequest request,
			HttpServletResponse response, CampaignMonitorDto campaignMonitor,
			DataGridModel dataModel) {
		Map<String, Object> maps = new HashMap<String, Object>();
		campaignMonitor.setCreateUser(this.getUserId(request));
		maps = campaignService.queryCampaignMonitor(campaignMonitor, dataModel);
		return maps;
	}

	/**
	 * 
	 * @Description:初始化
	 * @return
	 * @return ModelAndView
	 * @throws
	 */

	@RequestMapping(value = "campaignMonitorInit", method = RequestMethod.GET)
	public ModelAndView campaignMonitorInit(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("sms/campaignMonitor");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		modelAndView.addObject("startTimes", sim.format(new Date())
				+ " 00:00:00");
		modelAndView
				.addObject("endTimes", sim.format(new Date()) + " 23:59:59");
		modelAndView.addObject("createUser", getUserId(request));

		return modelAndView;
	}

	/**
	 * 
	 * @Description: 改变短信发送速度
	 * @param request
	 * @param response
	 * @param campaignMonitor
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "campaignChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> campaignChange(HttpServletRequest request,
			HttpServletResponse response, CampaignMonitorDto campaignMonitor) {
		Map<String, Object> maps = new HashMap<String, Object>();
		Boolean flag = true;
		try {
			CampaignMonitor campaignMonitorsCampaignMonitor = campaignService
					.queryMonitorByCampaignId(campaignMonitor.getCampaignId());
			campaignMonitor.setUpdateTime(new Date());
			campaignMonitor.setUpdateUser(this.getUserId(request));
			campaignMonitor.setDeparment(campaignMonitorsCampaignMonitor
					.getDeparment());
			campaignMonitor.setCreateTime(campaignMonitorsCampaignMonitor
					.getCreateTime());
			if (campaignMonitor.getType().equals("0")) {
				campaignMonitor.setMinuCount("");
			}
			flag = groupSmsSendService.changeSendSpeed(campaignMonitor);
			maps.put("result", flag);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			maps.put("result", flag);
		}
		return maps;
	}

	/**
	 * 
	 * @Description: 改变短信发送速度
	 * @param request
	 * @param response
	 * @param campaignMonitor
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "openWincampaignChangeInit")
	public ModelAndView campaignChangeInit(HttpServletRequest request,
			HttpServletResponse response, CampaignMonitorDto campaignMonitor) {
		ModelAndView modelAndView = new ModelAndView(
				"sms/openWincampaignChangeInit");
		List<CampaignBatch> list = campaignService
				.getCampaignBatchs(campaignMonitor.getCampaignId());
		CampaignMonitor campaignMonitorsCampaignMonitor = campaignService
				.queryMonitorByCampaignId(campaignMonitor.getCampaignId());
		Boolean flag = false;
		for (CampaignBatch campaignBatch : list) {
			SmsSend smsSend = smsAnswerService.getById(campaignBatch
					.getBatchId());
			// 判断动态模板
			if (smsSend.getDynamicTemplate().equals("1")) {
				flag = true;
			}
			modelAndView.addObject("flag", flag);
		}
		modelAndView.addObject("campaignMonitor",
				campaignMonitorsCampaignMonitor);
		return modelAndView;
	}

	public SmsTemplateService getSmsTemplateService() {
		return smsTemplateService;
	}

	public void setSmsTemplateService(SmsTemplateService smsTemplateService) {
		this.smsTemplateService = smsTemplateService;
	}

	public NamesMarketingService getNamesService() {
		return namesMarketingService;
	}

	public void setNamesService(NamesMarketingService namesMarketingService) {
		this.namesMarketingService = namesMarketingService;
	}
}
