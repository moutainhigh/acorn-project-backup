package com.chinadrtv.erp.admin.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.PlubasInfo;

/**
 * PlubasInfoDao
 *  
 * @author haoleitao
 * @date 2013-3-25 下午6:27:29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PlubasInfoDao extends GenericDao<PlubasInfo, Long> {
	public PlubasInfo getPlubasInfo(String plucode);
	public PlubasInfo getPlubasInfoByplid(Long ruid);
	public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size);
	public int getAllPlubasInfosCount(String catcode,String pluname);
	public List getCmsPromotion(String prodids, String moneys, String surid,
			String orderid);
}
