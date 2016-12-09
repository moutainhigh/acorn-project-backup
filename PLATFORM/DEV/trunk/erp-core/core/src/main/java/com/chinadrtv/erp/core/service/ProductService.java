package com.chinadrtv.erp.core.service;
import com.chinadrtv.erp.model.agent.Product;

import java.util.*;
public interface ProductService{
	/**
	* @Description: TODO
	* @return
	* @return List<Product>
	* @throws
	*/ 
	List<Product> queryList();
	
	public Product query(String productId);
	
	public Map<String,Object> query(String productName, Integer startRow, Integer rows) ;
}
