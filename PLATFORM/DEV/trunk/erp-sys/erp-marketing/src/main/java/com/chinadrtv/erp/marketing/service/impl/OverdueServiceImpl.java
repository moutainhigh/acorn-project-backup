/*
 * @(#)Over.java 1.0 2013-3-25下午4:11:22
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.UsrTaskRecommendDao;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-25 下午4:11:22
 * 
 */
@Service("delegateExpressionBean")
public class OverdueServiceImpl implements JavaDelegate {

	@Autowired
	private UsrTaskRecommendDao usrTaskRecommendDao;

	/**
	 * <p>
	 * Title: execute
	 * </p>
	 * <p>
	 * Description: 过期任务自动执行服务
	 * </p>
	 * 
	 * @param execution
	 * @throws Exception
	 * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		String businessKey = execution.getProcessBusinessKey();
		execution.getEngineServices().getTaskService();

		UsrTaskRecommend usrTaskRecommend = usrTaskRecommendDao.get(Long
				.valueOf(businessKey));

		if (usrTaskRecommend != null) {
			usrTaskRecommend.setUp_date(new Date());
			usrTaskRecommend.setUp_user("root");
			usrTaskRecommend.setIs_finished("Y");
		}
		System.out.println(businessKey + "自动执行服务");

	}

}
