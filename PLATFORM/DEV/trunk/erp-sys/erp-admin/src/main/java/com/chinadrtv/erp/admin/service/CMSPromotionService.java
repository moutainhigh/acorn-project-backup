package com.chinadrtv.erp.admin.service;

import java.util.List;
/**
 * CMS促销服务
 * 
 * @author haoleitao
 *
 */
public interface CMSPromotionService {
	public List getPromotion(String prodids,String moneys,String surid,String orderid);
}
