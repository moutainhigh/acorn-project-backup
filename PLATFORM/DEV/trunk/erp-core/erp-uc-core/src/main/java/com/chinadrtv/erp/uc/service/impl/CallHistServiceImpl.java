package com.chinadrtv.erp.uc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.agent.CallHistLeadInteraction;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dao.BlackListDao;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallHistDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dao.LeadInteractionDao;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dao.PotentialContactPhoneDao;
import com.chinadrtv.erp.uc.service.CallHistService;

@Service("callHistService")
public class CallHistServiceImpl implements CallHistService {
    private static final Logger logger = LoggerFactory.getLogger(CallHistServiceImpl.class);

    @Autowired
    private CallHistDao callHistDao;

    @Autowired
    private LeadInteractionDao leadInteractionDao;

    @Autowired
    private BlackListDao blackListDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PotentialContactPhoneDao potentialContactPhoneDao;
    @Autowired
    private CallBackDao callBackDao;
    @Autowired
    private CallbackLogDao callbackLogDao;
    @Autowired
    private LeadService leadService;

    public void addCallHist(Lead lead, String contactId, Date stTm, Date endTm,
                            String userId, String batchId, String queueId, String callType) {

        LeadInteraction leadInteraction = createLeadInteraction(userId, lead, "1", "1");
        leadInteractionDao.save(leadInteraction);

        CallHist callHist = createCallHist(contactId, stTm, endTm, userId,
                callType);
        callHistDao.save(callHist);
    }

    private CallHist createCallHist(String contactId, Date stTm, Date endTm,
                                    String userId, String callType) {
        CallHist entity = new CallHist();
        entity.setContactId(contactId);
        entity.setStTm(stTm);
        entity.setEndTm(endTm);
        entity.setUserId(userId);
        entity.setCallType(callType);
        return entity;
    }

    public List<CallHist> getCallHistByContactId(String contactId, Date sdt,
                                                 Date edt, int index, int size) throws Exception {
        return callHistDao.getCallHistByContactId(contactId, sdt, edt, index, size);
    }

    public int getCallHistByContactIdCount(String contactId, Date sdt, Date edt) throws Exception {
        return callHistDao.getCallHistByContactIdCount(contactId, sdt, edt);
    }

    public void saveCallHist(CallHist callHist) {
        callHistDao.save(callHist);
    }

    public void updateCallHistContact(String contactId, String potentialContactId) {
        callHistDao.updateCallHistContact(contactId,potentialContactId);
    }


    public Map<String, Object> getCallHistLeadInteraction(String contactId, DataGridModel dataGridModel) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long count = callHistDao.getCallHistLeadInteraction(contactId);
        List<CallHistLeadInteraction> list = callHistDao.getCallHistLeadInteraction(contactId, dataGridModel.getStartRow(), dataGridModel.getRows());
        appendBlackListNote(list);
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    private void appendBlackListNote(List<CallHistLeadInteraction> list) {
        for (CallHistLeadInteraction callHistLeadInteraction : list) {
            if (StringUtils.equals(callHistLeadInteraction.getCallHistLeadInteractionId().getTableType(), "leadInteraction")) {
                try {
                    BlackList blackList = blackListDao.findByLeadInteractionId(callHistLeadInteraction.getModelId());
                    if (blackList != null) {
                        String phoneNum = "";
                        if (StringUtils.isNotBlank(blackList.getContactId())) phoneNum = phoneDao.get(blackList.getPhoneId()).getPhn2();
                        else phoneNum = potentialContactPhoneDao.get(blackList.getPhoneId()).getPhone2();
                        phoneNum = phoneNum.replaceAll("([0-9]{3})([-\\d]{0,4})([0-9]{4}$)", "$1$2****");
                        String note = StringUtils.isBlank(callHistLeadInteraction.getNote()) ? ("加黑:" + phoneNum) : (callHistLeadInteraction.getNote() + " 加黑:" + phoneNum);
                        callHistLeadInteraction.setNote(note);
                    }
                } catch (Exception e) {
                    logger.error("查找联系历史的加黑记录出错,创建人:"+callHistLeadInteraction.getContactId()+",创建时间:"+callHistLeadInteraction.getCreateDate(), e);
                    continue;
                }
            }
        }
    }

    private LeadInteraction createLeadInteraction(String userId, Lead lead,
                                                  String operation, String status) {
        LeadInteraction entity = new LeadInteraction();
        entity.setCreateDate(new Date());
        entity.setCreateUser(userId);
//		entity.setLead(lead);
        entity.setOperation(operation);
        entity.setStatus(status);
        return entity;
    }
}
