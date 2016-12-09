package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
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

import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeService;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 营销计划类型管理
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("campaignType")
public class CampaignTypeController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignTypeController.class);

	@Autowired
	private CampaignTypeService campaignTypeService;

	/**
	 * 
	 * @Description: 初始化营销计划类型列表页面
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
		ModelAndView mav = new ModelAndView("campaign/campaignType");
		logger.info("进入营销计划类型设置功能");
		return mav;
	}

	/**
	 * 获取营销计划类型列表
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

		Map<String, Object> reuslt = campaignTypeService.query(campaign,
				dataModel);
		return reuslt;
	}

	/**
	 * 获取营销计划类型列表
	 * 
	 * @param campaign
	 * @param dataModel
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "getCampaignTypes", method = RequestMethod.POST)
	@ResponseBody
	public List<CampaignType> list(HttpServletRequest request)
			throws IOException, JSONException, ParseException {

		List<CampaignType> reuslt = campaignTypeService.queryList();
		return reuslt;
	}

}