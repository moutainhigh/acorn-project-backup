/*
 * @(#)LeadTaskDao.java 1.0 2013-5-10上午11:34:22
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskVO;
import com.chinadrtv.erp.model.marketing.UserBpm;

/**
 * 
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:34:22
 * 
 */
public interface UserBpmDao extends GenericDao<UserBpm, java.lang.Long> {

	List<ApprovingTaskVO> queryChangeRequest(ApprovingTaskDto aTaskDto,
			DataGridModel dataModel);
	
	UserBpm queryApprovingTaskById(String batchId);

	Integer queryCount(ApprovingTaskDto aTaskDto);

	int cancelChangeRequestByBatchID(String batchID);

	int updateChangeRequestStatus(String batchID, String status);
	
	int updateChangeRequestUser(String batchID, String approveUser, Date approveDate);
	
	String getChangeRequestStatus(String batchID);
	
	List<UserBpm> queryUnCompletedOrderChangeRequest(String orderId);

}
