package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.core.model.ScmPromotion;
/**
 * API-IC-1.	SCM满赠促销
 *  
 * @author haoleitao
 * @date 2013-5-14 下午4:18:20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface SCMpromotionService {
	public List<ScmPromotion> getCmsPromotion(String prodids, String moneys, String surid,
			String orderid);
}
