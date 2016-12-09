/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:21:44
 * 
 */
public interface NamesMarketingDao extends GenericDao<Names, java.lang.String> {

	/**
	 * @Description: TODO
	 * @return
	 * @return List<Names>
	 * @throws
	 */
	List<Names> queryNamesList();

	public List queryContactidByPhone(String phone);

	public List<SmssnedDto> queryByCampaign(Long campaignId,
			DataGridModel dataModel);

	public Integer queryCountsByCampaign(Long campaignId);

}
