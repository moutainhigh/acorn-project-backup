package com.chinadrtv.erp.core.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.ProductDao;
import com.chinadrtv.erp.core.service.ProductService;
import com.chinadrtv.erp.model.agent.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

	/**
	 * 获取产品列表
	 */
	public List<Product> queryList() {
		return productDao.queryList();
	}

	/**
	 * 分页查询产品列表
	 */
	public Map<String, Object> query(String productName, Integer startRow,
			Integer rows) {
		List<Product> list = productDao.query(productName, startRow, rows);
		Integer count = productDao.queryCount(productName);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", count);
		return result;
	}

	/**
	* <p>Title: query</p>
	* <p>Description: </p>
	* @param productId
	* @return
	* @see com.chinadrtv.erp.core.service.ProductService#query(java.lang.String)
	*/ 
	public Product query(String productId) {
		return productDao.query(productId);
	}

}
