/**
 * 
 */
package com.chinadrtv.erp.admin.service;

import java.util.List;
import java.util.Set;

import com.chinadrtv.erp.model.inventory.PlubasInfo;

/**
 * 商品信息服务 
 * @author haoleitao
 * @date 2013-3-25 下午5:16:45
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PlubasInfoService {
 public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size);
 public int getAllPlubasInfosCount(String catcode,String pluname);
 public Set<PlubasInfo> getPlubasInfosByIds(String ids);
 //public List getCmsPromotion(String prodids, String moneys, String surid,String orderid);
}
