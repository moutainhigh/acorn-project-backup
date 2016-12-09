/*
 * @(#)SmsAnswerDao.java 1.0 2013-2-22下午5:05:47
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午5:05:47
 * 
 */
public interface SmsAnswerDao {

	public void saveSmsAnswer(SmsAnswer smsAnswers);

	/**
	 * 
	 * @Description: 根据手机号码查询总量
	 * @param mobile
	 * @return
	 * @return Long
	 * @throws
	 */
	public Long getCountsByMobile(String mobile);

	/***
	 * 
	 * @Description: 根据手机号码查询分页
	 * @param contactId
	 * @param dataModel
	 * @return
	 * @return List<SmsSendDetail>
	 * @throws
	 */
	public List<SmsAnswer> getSmsAnswerByMobile(String mobile,
			DataGridModel dataModel);

}
