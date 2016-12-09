package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;
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
public class CampaignBPMTaskServiceImpl implements CampaignBPMTaskService {
    public CampaignTaskVO queryMarketingTask(String instId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Object> queryMarketingTask(CampaignTaskDto mTaskDto, DataGridModel dataModel) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Map<String, Object>> queryContactTaskHistory(String contactId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Object> queryUnStartedCampaignTask(CampaignTaskDto mTaskDto, DataGridModel dataModel) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Integer queryCount(CampaignTaskDto mTaskDto) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer queryAllocatedCount(CampaignTaskDto campaignTaskDto) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer queryUndialCount(CampaignTaskDto campaignTaskDto) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String createMarketingTask(LeadTask leadTask) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String createMarketingTask(String s, String s1, String s2, long l, String s3, String s4, Date date, Date date1, long l1, long l2, long l3, String s5) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> clearInvalidPushTask() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeTaskAndUpdateStatus(String s, String s1, String s2, String s3) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String createMarketingTask(String leadID, String campaignID, String contactID, long isPotential, String pdCustomerId, String usrID, Date endDate, Date appointDate, long sourceType, String remark) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean cancelTask(String remark, String processInsId, String taskID, String userID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean updateTaskStatus(String instID, String status, String remark, String taskID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int changeTaskOwnerByLead(long leadID, String newOwner) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Object> queryInstsByLead(long leadID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LeadTask queryInst(String instID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateTaskStatusByLead(long leadID, String status) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String queryBPMProcessID(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void completeTaskAndUpdateStatus(String remark, String processInsId, String taskID, String userID) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<TaskFormItem> getTaskForm(String campaignID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean submitTaskForm(String instID, String leadTaskId, List<TaskFormItem> taskItems) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updatePotential2Normal(String contactId, String potentialContactId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Integer queryUnStartedCampaignTaskCount(CampaignTaskDto mTaskDto) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isTaskFinished(String instId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateInstAppointDate(String instID, Date appointDate) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public LeadTask get(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveOrUpdate(LeadTask leadTask) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean queryInstsIsDone(String instID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
