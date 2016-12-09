/**
 * 
 */
package com.chinadrtv.erp.admin.service.impl;

import java.awt.SystemTray;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chinadrtv.erp.admin.dao.PlubasInfoDao;
import com.chinadrtv.erp.admin.model.PayType;
import com.chinadrtv.erp.admin.model.ScmPromotion;
import com.chinadrtv.erp.admin.service.PlubasInfoService;
import com.chinadrtv.erp.model.inventory.PlubasInfo;


/**
 *  
 * @author haoleitao
 * @date 2013-3-25 下午5:17:12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("plubasInfoService")
public class PlubasInfoServiceImpl implements PlubasInfoService {
    @Autowired
    private PlubasInfoDao dao;
    
    public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size) {
        return dao.getAllPlubasInfos(catcode,pluname,index ,size);  //To change body of implemented methods use File | Settings | File Templates.
    }

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.admin.service.PlubasInfoService#getAllPlubasInfosCount()
	 */
	public int getAllPlubasInfosCount(String catcode,String pluname) {
		// TODO Auto-generated method stub
		return dao.getAllPlubasInfosCount(catcode,pluname);
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.admin.service.PlubasInfoService#getPlubasInfosByIds(java.lang.String)
	 */
	public Set<PlubasInfo> getPlubasInfosByIds(String ids) {
		// TODO Auto-generated method stub
		Set<PlubasInfo> set = new HashSet();
		for(String id : ids.split(",")){
			set.add(dao.get(Long.valueOf(id)));	
		}
		
		return set;
	}

//	public List getCmsPromotion(String prodids, String moneys, String surid,
//			String orderid) {
//		// TODO Auto-generated method stub
//		List inParams = new ArrayList();
//		inParams.add(prodids);
//		inParams.add(moneys);
//		inParams.add(surid);
//		inParams.add(orderid);
//		List<Object> list =null; 
//		try {
//			list  = sqldao.exeStoredProcedure("p_getsalespromotioninfo", inParams, 1,OracleTypes.CURSOR);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//dao.getCmsPromotion(prodids, moneys, surid, orderid);
//		return list;
//	}
    
}
