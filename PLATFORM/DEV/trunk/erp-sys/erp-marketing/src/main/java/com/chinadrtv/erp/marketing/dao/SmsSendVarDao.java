package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.smsapi.model.SmsSendVar;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * smsSendVarDAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * smsSendVarDAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface SmsSendVarDao extends GenericDao<SmsSendVar, java.lang.Long> {

	public List<SmsSendVar> query(String batchId);
}
