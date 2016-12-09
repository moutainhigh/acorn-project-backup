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

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.LeadTypeService;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 线索类型管理
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("leadType")
public class LeadTypeController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(LeadTypeController.class);

	@Autowired
	private LeadTypeService leadTypeService;

	/**
	 * 
	 * @Description: 初始化线索类型列表页面
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
		ModelAndView mav = new ModelAndView("campaign/leadType");
		logger.info("进入线索类型设置功能");
		return mav;
	}

	/**
	 * 获取线索类型列表
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

		Map<String, Object> reuslt = leadTypeService.query(campaign, dataModel);
		return reuslt;
	}

	/**
	 * 获取线索类型列表
	 * 
	 * @param campaign
	 * @param dataModel
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "getLeadTypes", method = RequestMethod.POST)
	@ResponseBody
	public List<LeadType> list(HttpServletRequest request) throws IOException,
			JSONException, ParseException {

		List<LeadType> reuslt = leadTypeService.queryList(Constants.LEAD_TYPE_CAMPAIGN);
		return reuslt;
	}

}