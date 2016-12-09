package com.chinadrtv.erp.knowledge.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.service.ProductService;
import com.chinadrtv.erp.model.Names;

/**
 * 字典提供
 * <p/>
 * Created with IntelliJ IDEA. User: zhaizy Date: 12-12-20 Time: 上午11:34
 */

@Controller
@RequestMapping("dict")
public class DictionaryController extends BaseController {

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
	@RequestMapping(value = "getDict")
	@ResponseBody
	public List<Names> getDepartment(HttpServletRequest request, String dictName)
			throws IOException {
		return namesService.getAllNames(dictName);
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

}