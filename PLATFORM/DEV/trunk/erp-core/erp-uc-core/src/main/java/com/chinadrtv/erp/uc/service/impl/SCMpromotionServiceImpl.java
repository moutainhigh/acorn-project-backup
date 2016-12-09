package com.chinadrtv.erp.uc.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.uc.dao.SqlDao;
import com.chinadrtv.erp.uc.service.SCMpromotionService;
/**
 * API-IC-1.	SCM满赠促销
 *  
 * @author haoleitao
 * @date 2013-5-14 下午4:18:43
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("sCMpromotionService")
public class SCMpromotionServiceImpl implements SCMpromotionService {
    @Autowired
    private SqlDao sqlDao;
	
	public List<ScmPromotion> getCmsPromotion(String prodids, String moneys, String surid,
		String orderid) {
		List inParams = new ArrayList();
		inParams.add(prodids);
		inParams.add(moneys);
		inParams.add(surid);
		inParams.add(orderid);
		List<ScmPromotion> list =null; 
		try {
			list  = sqlDao.exeStoredProcedure("iagent.p_getsalespromotioninfo", inParams, 1,OracleTypes.CURSOR);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//dao.getCmsPromotion(prodids, moneys, surid, orderid);
		return list;
	}
	
}
