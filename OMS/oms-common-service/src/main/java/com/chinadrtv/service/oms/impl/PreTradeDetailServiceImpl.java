/*
 * @(#)PreTradeDetailServiceImpl.java 1.0 2013年11月1日下午2:16:41
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PreTradeDetailDao;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.service.oms.PreTradeDetailService;

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
 * @author andrew
 * @version 1.0
 * @since 2013年11月1日 下午2:16:41 
 * 
 */
@Service("preTradeDetailService")
public class PreTradeDetailServiceImpl implements PreTradeDetailService {

	@Autowired
	private PreTradeDetailDao preTradeDetailDao;

	/** 
	 * <p>Title: queryDetailByTradeId</p>
	 * <p>Description: </p>
	 * @param tradeId
	 * @return
	 * @see com.chinadrtv.service.oms.PreTradeDetailService#queryDetailByTradeId(java.lang.String)
	 */ 
	@Override
	public List<PreTradeDetail> queryDetailByTradeId(String tradeId) {
		return preTradeDetailDao.queryDetailByTradeId(tradeId);
	}

    @Override
    public void insert(PreTradeDetail preTradeDetail) {
        List<PreTradeDetail> preTradeDetailList = new ArrayList<PreTradeDetail>();
        preTradeDetailList.add(preTradeDetail);
        preTradeDetailDao.insertBatch(preTradeDetailList);
    }


}
