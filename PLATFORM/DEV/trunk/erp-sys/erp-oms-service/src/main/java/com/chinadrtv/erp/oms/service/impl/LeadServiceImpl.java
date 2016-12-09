package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.LeadQueryDto;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;
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
public class LeadServiceImpl implements LeadService {
    public LeadTask saveLead(Lead lead) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void insertLead(Lead lead) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean updateLead(Long leadId, Long status, String statusReason, String updateUser) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean updateLead(Long leadId, Long status, String statusReason, String updateUser, Date updateDate, String dnis, String ani, String userGroup) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean updateLead(Lead lead) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean reapportionLead(Long leadId, String owner, String updateUser) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Lead getLastestAliveLead(String agentId, String contactId, String campaignId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Lead getLastestAliveLead(String agentId, String contactId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Object> queryOverdueLead() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateStatus4OverdueLead(long leadId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updatePotential2Normal(String contactId, String potentialContactId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int updateContact(String instId, String contactId, boolean isPotential) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Map<String, Object>> query(LeadQueryDto leadQueryDto, DataGridModel dataModel) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Lead get(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long transferLeadAndSubsidiaries(String instId, String newAgentId, Date endDate, String comment) throws ServiceException, Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isAlive(Lead lead) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
