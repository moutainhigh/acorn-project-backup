package com.chinadrtv.erp.marketing.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.marketing.dto.UnfinishedRecommend;
import com.chinadrtv.erp.marketing.service.UsrTaskRecommendService;

/**
 * AgentTask调用控制
 * <p/>
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 12-12-20
 * Time: 上午11:34
 */

@Controller
@RequestMapping("/agent")
public class AgentTaskController {


	@Autowired
	private UsrTaskRecommendService usrTaskRecommendService;
	
	/***
	 * 获取推荐产品
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
	@RequestMapping(value = "/getRecommend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRecommend(@RequestBody RecommendConfDto recommend, HttpServletResponse response)
			throws IOException {
			Map<String,Object> result = usrTaskRecommendService.qryRecommend(recommend);
			return result;
	}
	
	/***
	 * 获取未完成的推荐产品任务
	 * 
	 * @Description: TODO
	 * @param userid
	 * @param response
	 * @return
	 * @throws IOException
	 * @return List
	 * @throws
	 */
	@RequestMapping(value = "/unfinishedRecommend/userid/{userid}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> unfinishedRecommend(@PathVariable String userid, HttpServletResponse response)
			throws IOException {
			Map<String,Object> result = usrTaskRecommendService.getUnfinishedRecommend(userid);
			return result;
	}
	
	
	/***
	 * 获取未完成的推荐产品任务
	 * 
	 * @Description: TODO
	 * @param userid
	 * @param response
	 * @return
	 * @throws IOException
	 * @return List
	 * @throws
	 */
	@RequestMapping(value = "/recommend/{businessKey}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRecommend(@PathVariable String businessKey, HttpServletResponse response)
			throws IOException {
		
			Map<String, Object> result = usrTaskRecommendService.qryRecommend(businessKey);
			return result;
	}
	
	/**
	 * 
	* @Description: 提交推荐结果
	* @param recommend
	* @param response
	* @return
	* @throws IOException
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/postRecommendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> postRecommendResult(@RequestBody RecommendConfDto recommend, HttpServletResponse response)
			throws IOException {
			Map<String,Object> result = usrTaskRecommendService.postRecommendResult(recommend);
			return result;
	}
    
}