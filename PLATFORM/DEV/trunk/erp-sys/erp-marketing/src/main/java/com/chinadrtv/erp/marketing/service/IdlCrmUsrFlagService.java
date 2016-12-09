package com.chinadrtv.erp.marketing.service;


import java.util.List;

import com.chinadrtv.erp.marketing.model.bi.IdlCrmUsrFlag;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-客户标签-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-客户标签-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface IdlCrmUsrFlagService {
	public void saveOrUpdate(IdlCrmUsrFlag crmUsrFlag);
	public List<IdlCrmUsrFlag> queryList(String scope,String scopeEnd,Integer pageCount);
	
	 public int updateSyncFlag(String scopeStart,String newFlag,String origFlag);
	 
	 public List<IdlCrmUsrFlag> queryListByJdbc(String scope,Integer startNm,Integer endNum);
	 public int queryCount(String scope);
}
