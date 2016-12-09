package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.ScmSetDto;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeService;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeValueService;
import com.chinadrtv.erp.marketing.core.service.LeadTypeValueService;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignCrConfig;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.service.CouponCrService;
import com.chinadrtv.erp.util.CodecUtils;

/**
 * 营销计划管理
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("campaign")
public class CampaignController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignController.class);

	@Autowired
	private CampaignService campaignService;

	// @Autowired
	// private CustomerSqlSourceService customerSqlSourceService;
	//
	// @Autowired
	// private CustomerBatchService customerBatchService;

	@Autowired
	private CampaignTypeService campaignTypeService;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private CampaignTypeValueService campaignTypeValueService;

	@Autowired
	private LeadTypeValueService leadTypeValueService;
	@Autowired
	private CouponCrService couponCrService;

	// /**
	// *
	// * @Description: 优惠券查看分页
	// * @param request
	// * @param couponCr
	// * @param dataModel
	// * @return
	// * @return Map<String,Object>
	// * @throws
	// */
	// @RequestMapping(value = "couponCrList", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> couponCrList(HttpServletRequest request,
	// Long campaingId, DataGridModel dataModel) {
	// Map<String, Object> result = new HashMap<String, Object>();
	// CouponCr coupon = new CouponCr();
	//
	// coupon.setCampaignId(campaingId);
	// result = couponCrService.getListForPage(coupon, dataModel);
	//
	// return result;
	//
	// }

	/**
	 * 
	 * @Description: TODO
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinCouponView", method = RequestMethod.GET)
	public ModelAndView openWinCouponView(Long campaignId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("campaign/couponListForCampaign");
		mav.addObject("campaignId", campaignId);
		return mav;
	}

	/**
	 * 
	 * @Description: 初始化营销计划列表页面
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "init", method = RequestMethod.GET)
	public ModelAndView main(
			@RequestParam(required = false, defaultValue = "") String from,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("campaign/index");
		logger.info("进入营销计划设置功能");
		return mav;
	}

	/**
	 * 获取营销计划列表
	 * 
	 * @param campaign
	 * @param dataModel
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			CampaignDto campaign, DataGridModel dataModel) throws IOException,
			JSONException, ParseException {

		Map<String, Object> reuslt = campaignService.query(campaign, dataModel);
		return reuslt;
	}

	/**
	 * @throws Exception
	 * 
	 * @Description: 打开编辑营销计划设置窗口并初始化
	 * @param request
	 * @param response
	 * @param groupCode
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditCampaign", method = RequestMethod.GET)
	public ModelAndView editGroup(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws Exception {
		ModelAndView mav = new ModelAndView("campaign/editCampaign");

		Campaign campaign = null;
		String userId = getUserId(request);
		/*
		 * 如果营销计划不为空，则查询供页面初始化编辑信息
		 */
		if (id != null) {
			campaign = campaignService.getCampaignById(id);
			// JobDetail jobDetail = schedulerService.getJobDetail(id,
			// Constants.JOB_GROUP_CUSTOMER_GROUP);
			// if (jobDetail != null) {
			// JobCronSet jobCronSet = (JobCronSet)
			// jobDetail.getJobDataMap().get("JOB_CRON_SET");
			// mav.addObject("jobCronSet", jobCronSet);
			// }
		} else {
			campaign = new Campaign();
			CampaignCrConfig campaignCrConfig = campaignService
					.getByUserId(userId);
			if (campaignCrConfig != null) {
				campaign.setCampaignTypeId(campaignCrConfig.getMarketingType());
				campaign.setDepartment(campaignCrConfig
						.getMarketingDepartment());
			}
		}
		mav.addObject("campaign", campaign);
		mav.addObject("returnUrl", getReturnUrlEncode(request));
		return mav;
	}

	/**
	 * 
	 * @Description: 保存或更新营销计划
	 * @param request
	 * @param response
	 * @param group
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCampaign(HttpServletRequest request,
			HttpServletResponse response, Campaign campaign, String returnUrl)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		campaign.setCreateDepartment(this.getDepartmentId(request));
		if (campaign.getCampaignTypeId() == 5) {
			campaign.setStatus(Constants.CAMPAIGN_COUPON);
		}
		campaignService.saveCampaign(campaign, getUserId(request));
		CampaignType campaignType = campaignTypeService
				.getCampaignTypeById(campaign.getCampaignTypeId());
		result.put("result", "success");
		result.put("campaignId", campaign.getId());
		result.put("campaign", campaign);
		result.put("next", campaignType.getPageUrl());
		result.put("returnUrl", returnUrl);
		return result;
	}

	// /**
	// *
	// * @Description: 保存或更新营销计划
	// * @param request
	// * @param response
	// * @param group
	// * @param model
	// * @return
	// * @throws IOException
	// * @return Map<String,Object>
	// * @throws
	// */
	// @RequestMapping(value = "saveCampaign", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> saveGroup(HttpServletRequest request,
	// HttpServletResponse response,
	// Campaign group, JobCronSet jobCronSet) throws IOException {
	// Map<String, Object> result = new HashMap<String, Object>();
	// campaignService.saveCampaign(group, getUserId(request), jobCronSet);
	// result.put("result", "success");
	// return result;
	// }

	/**
	 * 
	 * @Description: 打开短信参数编辑窗口
	 * @param request
	 * @param response
	 * @param groupCode
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditSmsSet", method = RequestMethod.GET)
	public ModelAndView openWinEditSmsSet(HttpServletRequest request,
			HttpServletResponse response, Long id, String returnUrl)
			throws IOException {
		ModelAndView mav = new ModelAndView("campaign/smsSet");
		Campaign campaign = campaignService.getCampaignById(id);
		Map<String, String> smsSet = campaignTypeValueService
				.queryParamTypeValue(id);
		mav.addObject("startDate", campaign.getStartDate());
		mav.addObject("endDate", campaign.getEndDate());
		mav.addObject("campaignId", id);
		mav.addObject("campaignId", id);
		mav.addObject("smsSet", smsSet);
		mav.addObject("campaign", campaign);
		mav.addObject("sourceUrl", CodecUtils.defuzzify(returnUrl));
		mav.addObject("returnUrl", getReturnUrlEncode(request));
		return mav;
	}

	/**
	 * 保存短信参数设置
	 * 
	 * @Description: TODO
	 * @param request
	 * @param campaignId
	 * @param response
	 * @param smssnedDto
	 * @param returnUrl
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveSmsSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSmsSet(HttpServletRequest request,
			Long campaignId, HttpServletResponse response,
			SmssnedDto smssnedDto, String returnUrl) {
		// 发送量集合
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			campaignTypeValueService.saveSmsParamTypeValue(campaignId,
					getUserId(request), smssnedDto);
			result.put("result", "1");
			result.put("campaignId", campaignId);
			result.put("returnUrl", returnUrl);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "创建失败");
		}
		return nextSchedule(result);
	}

	/**
	 * 
	 * @Description: 打开短信参数编辑窗口
	 * @param request
	 * @param response
	 * @param groupCode
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditRecommendSet", method = RequestMethod.GET)
	public ModelAndView openWinEditRecommendSet(HttpServletRequest request,
			HttpServletResponse response, Long id, String returnUrl)
			throws IOException {
		ModelAndView mav = new ModelAndView("campaign/editRecommend");
		Campaign campaign = campaignService.getCampaignById(id);
		Map<String, String> leadSet = leadTypeValueService
				.queryParamTypeValue(id);
		mav.addObject("campaignId", id);
		mav.addObject("leadSet", leadSet);
		mav.addObject("sourceUrl", CodecUtils.defuzzify(returnUrl));
		mav.addObject("returnUrl", getReturnUrlEncode(request));
		return mav;
	}

	/**
	 * 保存Lead参数设置
	 * 
	 * @Description: TODO
	 * @param request
	 * @param campaignId
	 * @param response
	 * @param smssnedDto
	 * @param returnUrl
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveLeadSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveLeadSet(HttpServletRequest request,
			Long campaignId, HttpServletResponse response, ScmSetDto scmSetDto,
			String returnUrl) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			leadTypeValueService.saveLeadParamTypeValue(campaignId,
					getUserId(request), scmSetDto);

			Campaign campaign = campaignService.getCampaignById(campaignId);
			CampaignType campaignType = campaignTypeService
					.getCampaignTypeById(campaign.getCampaignTypeId());

			result.put("isSchedule", campaignType.getIsSchedule());
			result.put("result", "1");
			result.put("campaignId", campaignId);
			result.put("returnUrl", returnUrl);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "创建失败");
		}
		return nextSchedule(result);
	}

	/**
	 * 下一步设置调度任务
	 * 
	 * @Description: TODO
	 * @param result
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> nextSchedule(Map<String, Object> result) {
		result.put("next", "/campaign/openWinEditScheduleSet");
		return result;
	}

	/**
	 * @throws Exception
	 * 
	 * @Description: 打开编辑营销计划调度任务设置
	 * @param request
	 * @param response
	 * @param groupCode
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditScheduleSet", method = RequestMethod.GET)
	public ModelAndView openWinEditScheduleSet(HttpServletRequest request,
			HttpServletResponse response, Long id, String returnUrl)
			throws Exception {
		ModelAndView mav = new ModelAndView("campaign/schedule");

		Campaign campaign = campaignService.getCampaignById(id);
		/*
		 * 如果客户群编码不为空，则查询供页面初始化编辑信息
		 */
		if (id != null) {
			JobDetail jobDetail = schedulerService.getJobDetail(
					Constants.JOB_NAME_CAMPAIGN_PREFIX + id,
					Constants.JOB_GROUP_CAMPAIGN);
			if (jobDetail != null) {
				JobCronSet jobCronSet = (JobCronSet) jobDetail.getJobDataMap()
						.get("JOB_CRON_SET");
				mav.addObject("jobCronSet", jobCronSet);
			}
		}
		mav.addObject("sourceUrl", CodecUtils.defuzzify(returnUrl));
		mav.addObject("campaignId", id);
		mav.addObject("groupCode", campaign.getAudience());
		return mav;
	}

	/**
	 * 
	 * @Description: 保存或更新营销调度
	 * @param request
	 * @param response
	 * @param group
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveSchedule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSchedule(HttpServletRequest request,
			HttpServletResponse response, Long campaignId, JobCronSet jobCronSet)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		campaignService.saveSchedule(campaignId, jobCronSet);
		result.put("result", "success");
		return result;
	}

	/**
	 * 停止营销计划
	 * 
	 * @Description: TODO
	 * @param request
	 * @param campaignId
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "stopCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopCampaign(HttpServletRequest request,
			Long campaignId, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			campaignService.stopCampaign(campaignId);
			result.put("result", "营销计划停止成功");
		} catch (Exception e) {
			logger.error("营销计划停止失败，信息：" + e.getMessage());
			e.printStackTrace();

			result.put("result", "营销计划停止失败，信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 继续执行营销计划
	 * 
	 * @Description: TODO
	 * @param request
	 * @param campaignId
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "goOnCampaign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goOnCampaign(HttpServletRequest request,
			Long campaignId, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			campaignService.goOnCampaign(campaignId);
			result.put("result", "营销计划重新启动成功");
		} catch (Exception e) {
			logger.error("营销计划重新启动，信息：" + e.getMessage());
			e.printStackTrace();

			result.put("result", "营销计划重新启动，信息：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @Description: TODO
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinCampaignView", method = RequestMethod.GET)
	public ModelAndView openWinCampaignView(Long campaignId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("sms/sendListForCampaign");
		mav.addObject("campaignId", campaignId);
		return mav;
	}

}