package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.marketing.service.RecommendConfService;
import com.chinadrtv.erp.model.marketing.RecommendConf;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 手工挑单处理
 * <p/>
 * Created with IntelliJ IDEA. User: haoleitao Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("/recommend")
public class RecommendRuleController extends BaseController {

	@Autowired
	private RecommendConfService recommendConfService;

	/**
	 * 初始化产品推荐列表页面
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
	@RequestMapping(value = "init", method = RequestMethod.GET)
	public ModelAndView main(
			@RequestParam(required = false, defaultValue = "") String from,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {

		ModelAndView mav = new ModelAndView("recommend/index");
		return mav;
	}

	/**
	 * 获取产品推荐规则列表
	 * 
	 * @Description: TODO
	 * @param request
	 * @param group
	 * @param dataModel
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			DataGridModel dataModel) throws IOException, JSONException,
			ParseException {

		Map<String, Object> reuslt = recommendConfService.queryList(dataModel);
		return reuslt;
	}

	/**
	 * @throws Exception
	 * 
	 * @Description: 打开编辑客户群组窗口并初始化
	 * @param request
	 * @param response
	 * @param groupCode
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditRecommend", method = RequestMethod.GET)
	public ModelAndView editGroup(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model)
			throws Exception {
		ModelAndView mav = new ModelAndView("recommend/editRecommend");

		RecommendConf recommend = null;

		if (id != null) {
			recommend = recommendConfService.getRecommendConf(id);
		}
		mav.addObject("recommend", recommend);
		return mav;
	}

	/**
	 * 保存规则配置
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param group
	 * @param jobCronSet
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveRecommend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRecommend(HttpServletRequest request,
			HttpServletResponse response, RecommendConf recommend)
			throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (recommend.getId() != null) {
			recommend.setUp_user(this.getUserId(request));
		} else {
			recommend.setCrt_user(this.getUserId(request));
		}
		recommendConfService.saveRecommendConf(recommend);
		result.put("result", "success");
		return result;
	}

	@RequestMapping(value = "queryGroupCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupCode(HttpServletRequest request,
			HttpServletResponse response, String groupCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		RecommendConfDto recommend = new RecommendConfDto();
		recommend.setGroupId(groupCode);
		RecommendConf recomeConf = recommendConfService
				.getRecommendConf(recommend);
		if (recomeConf != null) {
			result.put("result", "该客户群已推荐产品");
		}
		return result;
	}

}