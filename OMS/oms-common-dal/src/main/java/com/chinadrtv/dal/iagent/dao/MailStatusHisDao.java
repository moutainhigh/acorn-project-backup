package com.chinadrtv.dal.iagent.dao;

import java.util.List;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.iagent.MailStatusHis;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-18
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public interface MailStatusHisDao extends BaseDao<MailStatusHis> {

	/**
	 * 批量插入
	 * @param modelList
	 */
    void insertBatch(List<MailStatusHis> modelList) throws Exception;
    
    Boolean queryExceptDate(MailStatusHis mailStatusHis);
}
