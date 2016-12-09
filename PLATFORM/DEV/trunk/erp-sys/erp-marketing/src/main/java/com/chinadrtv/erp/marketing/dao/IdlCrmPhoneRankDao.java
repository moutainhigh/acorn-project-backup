package com.chinadrtv.erp.marketing.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmPhoneRank;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmUsrFlag;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	电话排名DAO
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>电话排名DAO
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19 
 *
 */
public interface IdlCrmPhoneRankDao extends GenericDao<IdlCrmPhoneRank,java.lang.String>{
	
	public List<IdlCrmPhoneRank> queryListByJdbc(Integer startNm,Integer endNum);
	public int updateSyncFlag(String phoneid,String phoneRank,Date lastDate,String newFlag,String origFlag);
	
}
