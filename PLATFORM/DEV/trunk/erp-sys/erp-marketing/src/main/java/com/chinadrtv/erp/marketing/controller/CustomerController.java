package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.marketing.core.service.JobRelationService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.marketing.service.CustomerBatchService;
import com.chinadrtv.erp.marketing.service.CustomerGroupService;
import com.chinadrtv.erp.marketing.service.CustomerSqlSourceService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 客户群管理
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerController.class);

	@Autowired
	private CustomerGroupService customerGroupService;

	@Autowired
	private CustomerSqlSourceService customerSqlSourceService;

	@Autowired
	private CustomerBatchService customerBatchService;

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private JobRelationService jobRelationService;
	
	@Autowired
	private CampaignReceiverService campaignReceiverService;

	/**
	 * 
	 * @Description: 初始化客户群列表页面
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
		ModelAndView mav = new ModelAndView("customer/index");
		mav.addObject("list", jobRelationService.queryAllJob());
		logger.info("进入客户群设置功能");
		return mav;
	}

	/**
	 * 获取订单列表
	 * 
	 * @param orderhistDto
	 * @param dataModel
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			CustomerGroupDto group, DataGridModel dataModel)
			throws IOException, JSONException, ParseException {

		Map<String, Object> reuslt = customerGroupService.query(group,
				dataModel);
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
	@RequestMapping(value = "openWinEditGroup", method = RequestMethod.GET)
	public ModelAndView editGroup(HttpServletRequest request,
			HttpServletResponse response, String groupCode, Model model)
			throws Exception {
		ModelAndView mav = new ModelAndView("customer/editGroup");

		CustomerGroup group = null;

		/*
		 * 如果客户群编码不为空，则查询供页面初始化编辑信息
		 */
		if (!StringUtils.isEmpty(groupCode)) {
			group = customerGroupService.getCustomerGroupById(groupCode);
			JobDetail jobDetail = schedulerService.getJobDetail(groupCode,
					Constants.JOB_GROUP_CUSTOMER_GROUP);
			if (jobDetail != null) {
				JobCronSet jobCronSet = (JobCronSet) jobDetail.getJobDataMap()
						.get("JOB_CRON_SET");
				mav.addObject("jobCronSet", jobCronSet);
			}
			group.setGroupCodeTmp(group.getGroupCode());
			mav.addObject("flag", "1");
		} else {
			group = new CustomerGroup();
			group.setGroupCodeTmp(customerGroupService.getNextVal());
			mav.addObject("flag", "0");
		}

		mav.addObject("group", group);
		return mav;
	}

	@RequestMapping(value = "getGroup")
	@ResponseBody
	public Map<String,Object> getGroup(HttpServletRequest request,
			HttpServletResponse response, String groupCode)
			throws Exception {

		CustomerGroup group = null;
		Map<String,Object> result = new HashMap<String, Object>();
		/*
		 * 如果客户群编码不为空，则查询供页面初始化编辑信息
		 */
		group = customerGroupService.getCustomerGroupById(groupCode);
		result.put("group", group);
		JobDetail jobDetail = schedulerService.getJobDetail(groupCode,
				Constants.JOB_GROUP_CUSTOMER_GROUP);
		if (jobDetail != null) {
			JobCronSet jobCronSet = (JobCronSet) jobDetail.getJobDataMap()
					.get("JOB_CRON_SET");
			result.put("jobSet", jobCronSet);
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: 保存或更新客户群组
	 * @param request
	 * @param response
	 * @param group
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveGroup(HttpServletRequest request,
			HttpServletResponse response, CustomerGroup group,
			JobCronSet jobCronSet) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String sqlSource = group.getExt2();
		group.setExt2(null);
		// 新建客户群时保存sql
		CustomerSqlSource customerSql = new CustomerSqlSource();
		
		group.setCreateDepartment(getDepartmentId(request));
		if (sqlSource != null) {
			customerSql.setCrtDate(new Date());
			customerSql.setCrtUser(getUserId(request));
			customerSql.setSqlContent(sqlSource);
			customerSql.setGroup(group);
		}
		customerGroupService.saveCustomerGroup(group, getUserId(request),
				jobCronSet, customerSql);
		result.put("result", "success");
		return result;
	}

	/**
	 * 
	 * @Description: 打开客户群数据源sql编辑窗口
	 * @param request
	 * @param response
	 * @param groupCode
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditDataSource", method = RequestMethod.GET)
	public ModelAndView editDataSource(HttpServletRequest request,
			HttpServletResponse response, String groupCode, String flag)
			throws IOException {
		ModelAndView mav = new ModelAndView("customer/dataSource");
		CustomerGroup group = null;
		if (flag != null && !flag.equals("1")) {
			group = customerGroupService.getCustomerGroupById(groupCode);
		} else {
			group = new CustomerGroup();
			group.setGroupCode(groupCode);
		}
		mav.addObject("group", group);
		mav.addObject("flag", flag);
		return mav;
	}

	/**
	 * 
	 * @Description: 保存sql数据源
	 * @param request
	 * @param response
	 * @param sqlSource
	 * @param groupCode
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveSQLDataSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSQLDataSource(HttpServletRequest request,
			HttpServletResponse response, CustomerSqlSource sqlSource,
			String groupCode) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Long temp = customerSqlSourceService.saveCustomerSqlSource(
					sqlSource, groupCode, getUserId(request));
			result.put("result", temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @Description: 查看历史sql数据源
	 * @param request
	 * @param response
	 * @param groupCode
	 * @param dataModel
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "viewSQLDataSourceHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewSQLDataSourceHis(HttpServletRequest request,
			HttpServletResponse response, String groupCode,
			DataGridModel dataModel) throws IOException {
		Map<String, Object> result = customerSqlSourceService.querySqlHis(
				groupCode, dataModel);
		return result;
	}

	/**
	 * 
	 * @Description: 预览sql数据源数据量
	 * @param request
	 * @param response
	 * @param sqlContent
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "viewSQLCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewSQLCount(HttpServletRequest request,
			HttpServletResponse response, String sqlContent) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Long result = -1l;
		try {
			result = customerSqlSourceService.querySQLCount(sqlContent);
		} catch (Exception e) {
			e.printStackTrace();
			result = -1l;
		}

		map.put("result", result);

		return map;
	}

	/**
	 * 
	 * @Description: 预览批次历史
	 * @param request
	 * @param response
	 * @param groupCode
	 * @param dataModel
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "viewBatchHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewBatchHis(HttpServletRequest request,
			HttpServletResponse response, String groupCode,
			DataGridModel dataModel) throws IOException {
		CustomerGroup group = customerGroupService
				.getCustomerGroupById(groupCode);
		Map<String, Object> batchMap = customerBatchService.queryBatch(
				groupCode, dataModel);
		batchMap.put("groupDesc", group.getRemark());
		return batchMap;
	}

	/**
	 * 
	 * @Description: 打开数据回收窗口
	 * @param request
	 * @param response
	 * @param groupCode
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinRecoverDataSource", method = RequestMethod.GET)
	public ModelAndView recoverDataSource(HttpServletRequest request,
			HttpServletResponse response, String groupCode) throws IOException {
		ModelAndView mav = new ModelAndView("customer/recoverData");
		// CustomerGroup group =
		// customerGroupService.getCustomerGroupById(groupCode);
		// mav.addObject("group", group);
		return mav;
	}

	@RequestMapping(value = "queryCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCount(HttpServletRequest request,
			HttpServletResponse response, String ids) {
		String[] id = ids.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", customerGroupService.queryCount(id));
		return result;
	}
	
	
	@RequestMapping(value = "viewBatchStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewBatchStatus(HttpServletRequest request,
			HttpServletResponse response, String groupCode, String batchCode) throws IOException {
		Map<String, Object> batchMap = new HashMap<String, Object>();
		
		Map<String, Integer> result = campaignReceiverService.queryBatchProgress(batchCode);
		
		batchMap.put("count", result.get("totalQty"));
		batchMap.put("usable", result.get("unStartQty"));
		return batchMap;
	}
	
	@RequestMapping(value = "batchRecycle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchRecycle(HttpServletRequest request,
			HttpServletResponse response, String groupCode, String batchCode) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			campaignReceiverService.recycleBatchData(batchCode);
			result.put("result", 1);
		} catch (Exception e) {
			logger.error("Batch Data Recycle is failure:"+e);
			e.printStackTrace();
			result.put("result", -1);
			result.put("msg", e.getMessage());
		}
		
		return result;
	}
	
	
}