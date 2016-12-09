package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.service.ProductService;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.service.JobRelationService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.marketing.service.BaseConfigService;
import com.chinadrtv.erp.marketing.service.NamesMarketingService;
import com.chinadrtv.erp.marketing.service.ObQueuesService;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.agent.JobsRelation;
import com.chinadrtv.erp.model.agent.ObQueues;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 字典提供
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("dict")
public class DictionaryController extends BaseController {

	@Autowired
	private NamesMarketingService namesMarketingService;

	@Autowired
	private BaseConfigService baseConfigService;

	@Autowired
	private JobRelationService jobRelationService;

	@Autowired
	private ObQueuesService obQueuesService;

	@Autowired
	private NamesService namesService;

	@Autowired
	private ProductService productService;

	
	/**
	 * 查询部门
	 * 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws IOException
	 * @return List<NamesMarketing>
	 * @throws
	 */
	@RequestMapping(value = "getDepartment")
	@ResponseBody
	public List<Names> getDepartment(HttpServletRequest request)
			throws IOException {
		return namesMarketingService.queryNamesList();
	}

	/**
	 * 查询部门
	 * 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @throws IOException
	 * @return List<NamesMarketing>
	 * @throws
	 */
	@RequestMapping(value = "getDict")
	@ResponseBody
	public List<Names> getDepartment(HttpServletRequest request, String dictName)
			throws IOException {
		return namesService.getAllNames(dictName);
	}

	/**
	 * 根据code查询配置参数
	 * 
	 * @Description: TODO
	 * @param request
	 * @param code
	 * @return
	 * @throws IOException
	 * @return List<BaseConfig>
	 * @throws
	 */
	@RequestMapping(value = "getBaseConfig")
	@ResponseBody
	public List<BaseConfig> getBaseConfig(HttpServletRequest request,
			String code) throws IOException {
		return baseConfigService.query(code);
	}

	/**
	 * 查询产品列表
	 * 
	 * @Description: TODO
	 * @param request
	 * @param code
	 * @return
	 * @throws IOException
	 * @return List<BaseConfig>
	 * @throws
	 */
	@RequestMapping(value = "getProduct")
	@ResponseBody
	public Map<String, Object> getProduct(HttpServletRequest request,
			String productName, DataGridModel dataGrid) throws IOException {
		return productService.query(productName, dataGrid.getStartRow(),
				dataGrid.getRows());
	}

	/**
	 * 获取jobs，初始化业务编码下拉框
	 * 
	 * @Description: TODO
	 * @param request
	 * @param code
	 * @return
	 * @throws IOException
	 * @return List<JobsRelation>
	 * @throws
	 */
	@RequestMapping(value = "getJobs")
	@ResponseBody
	public List<AgentJobs> getJobs(HttpServletRequest request, String code)
			throws IOException {
		return jobRelationService.queryAllJob();
	}

	/**
	 * 查询队列，初始化队列下拉框
	 * 
	 * @Description: TODO
	 * @param request
	 * @param jobId
	 * @return
	 * @throws IOException
	 * @return List<ObQueues>
	 * @throws
	 */
	@RequestMapping(value = "getQueues")
	@ResponseBody
	public List<ObQueues> getQueues(HttpServletRequest request, String jobId)
			throws IOException {
		return obQueuesService.queryList(jobId);
	}

}