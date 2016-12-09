package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.sales.dto.CtiLoginDto;


/**
 * 
 * <dl>
 *    <dt><b>Title:查询分机号</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-9-3 下午1:33:47 
 *
 */
public interface TelephoneService  {
	
	public CtiLoginDto queryCtiLoginInfo(String agentId,String ip);
}
