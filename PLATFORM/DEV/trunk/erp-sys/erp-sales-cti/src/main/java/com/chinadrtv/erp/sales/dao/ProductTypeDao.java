package com.chinadrtv.erp.sales.dao;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.ProductType;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author dengqianyong
 *
 */
public interface ProductTypeDao {

	/**
	 * 
	 * @param type
	 * @return
	 */
	ProductType getProductType(String productId, String type);

    List<ProductType> getProductTypes(List<OrderDetail> orderDetailList);
}
