package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.model.marketing.UserBpm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ChangeRequestServiceImpl implements ChangeRequestService {
    public Map<String, Object> queryChangeReqeust(ApprovingTaskDto aTaskDto, DataGridModel dataModel) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Object> queryOrderCancelReqeust(ApprovingTaskDto aTaskDto, DataGridModel dataModel) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UserBpm queryApprovingTaskById(String batchId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String createChangeRequest(UserBpm userBpm) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean cancelChangeRequestByBatchID(String batchId, String remark) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateChangeRequestStatus(String batchId, String status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void closeChangeRequestStatus(String batchId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void syncBatchStatus(String batchId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateChangeRequestUser(String batchID, String approveUser, Date approveDate) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getChangeRequestStatus(String batchId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<UserBpm> queryUnCompletedOrderChangeRequest(String orderId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<UserBpm> cancelUnCompletedOrderChangeRequest(String orderId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Boolean> isRequestProcessed(List<String> batchIds) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isRequestProcessed(String batchId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
