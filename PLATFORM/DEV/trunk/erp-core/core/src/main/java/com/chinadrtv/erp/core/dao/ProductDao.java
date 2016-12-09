/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao;

import java.util.List;

import com.chinadrtv.erp.model.agent.Product;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:21:44 
 * 
 */
public interface ProductDao extends GenericDao<Product, java.lang.String>{

	/**
	* @Description: TODO
	* @return
	* @return List<Product>
	* @throws
	*/ 
	List<Product> queryList();
	
	public List<Product> query(String productName, Integer startRow, Integer rows) ;
	
	public Integer queryCount(String productName) ;
	
	public Product query(String productId) ;

}
