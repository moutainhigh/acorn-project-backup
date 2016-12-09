package com.chinadrtv.erp.marketing.service.impl;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.dao.IdlCrmUsrFlagDao;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmUsrFlag;
import com.chinadrtv.erp.marketing.service.IdlCrmUsrFlagService;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	客户群管理--服务类
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>客户群管理-客户标签-服务类
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50 
 *
 */
@Service("idlCrmUsrFlagService")
public class IdlCrmUsrFlagServiceImpl implements Serializable ,IdlCrmUsrFlagService{

	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IdlCrmUsrFlagDao idlCrmUsrFlagDao;
	

	/**
	 * 更新同步的数据
	 */
	public void saveOrUpdate(IdlCrmUsrFlag idlCrmUsrFlag) {
		idlCrmUsrFlagDao.saveOrUpdate(idlCrmUsrFlag);
	}


	/**
	 * 查询未同步的BI数据
	 */
	public List<IdlCrmUsrFlag> queryList(String scope,String scopeEnd,Integer pageCount) {
		return idlCrmUsrFlagDao.queryList(scope, scopeEnd, pageCount);
	}


	/**
	 * 更新同步状态 
	 */
	 public int updateSyncFlag(String scopeStart,String newFlag,String origFlag) {
		return idlCrmUsrFlagDao.updateSyncFlag(scopeStart, newFlag, origFlag);
	}


	/* (非 Javadoc)
	* <p>Title: queryListByJdbc</p>
	* <p>Description: </p>
	* @param scope
	* @param startNm
	* @param endNum
	* @return
	* @see com.chinadrtv.erp.marketing.service.IdlCrmUsrFlagService#queryListByJdbc(java.lang.String, java.lang.Integer, java.lang.Integer)
	*/ 
	public List<IdlCrmUsrFlag> queryListByJdbc(String scope, Integer startNm,
			Integer endNum) {
		return idlCrmUsrFlagDao.queryListByJdbc(scope, startNm, endNum);
	}


	/* (非 Javadoc)
	* <p>Title: queryCount</p>
	* <p>Description: </p>
	* @param scope
	* @return
	* @see com.chinadrtv.erp.marketing.service.IdlCrmUsrFlagService#queryCount(java.lang.String)
	*/ 
	public int queryCount(String scope) {
		return idlCrmUsrFlagDao.queryCount(scope);
	}

}
