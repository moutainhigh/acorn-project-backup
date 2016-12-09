package com.chinadrtv.erp.distribution.service.impl;

import com.chinadrtv.erp.distribution.dao.AgentCallbackDao;
import com.chinadrtv.erp.distribution.service.AgentCallbackService;
import com.chinadrtv.erp.distribution.service.CallInitService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.model.agent.CallbackEx;
import com.chinadrtv.erp.model.marketing.*;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-15
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
@Service("agentCallbackService")
public class AgentCallbackServiceImpl implements AgentCallbackService {

    private static final Logger logger = LoggerFactory.getLogger(AgentCallbackServiceImpl.class);

    @Autowired
    private AgentCallbackDao callDetailDao;

    @Autowired
    private CallBackDao callBackDao;

    @Autowired
    private CallbackLogDao callbackLogDao;

    @Autowired
    private CallInitService callInitService;

    @Override
    public Long findCallbackCount(CallbackSpecification specification) {
        AgentUser user = SecurityHelper.getLoginUser();
        specification.setAgentUser(user.getUserId());
        return callDetailDao.findCallbackCount(specification);
    }

    @Override
    public void assignCallbackCount(CallbackSpecification specification, List<AssignAgentDto> agents) {


        AgentUser user = SecurityHelper.getLoginUser();
        specification.setAgentUser(user.getUserId());
        String batchId = String.valueOf(new Date().getTime());
        List<CallbackEx> callbackExs = callDetailDao.findCallbackDetails(specification);
        Integer n = 0;
        try
        {
            for (AssignAgentDto assignAgentDto : agents) {
                Integer m = assignAgentDto.getAssignCount();
                for(Integer i = n; i < callbackExs.size(); i++,n++) {
                    CallbackEx callbackEx = callbackExs.get(i);
                    CustomerDto customer = callInitService.createCustomer(callbackEx.getContactId(), callbackEx.getLatentContactId(), callbackEx.getPhn2(), user);
                    if(customer != null) {
                        if(StringUtils.isBlank(customer.getPhone2())){
                            customer.setPhone2(callbackEx.getPhn2());
                        }
                        specification.setAni(customer.getPhone2());
                        //已分配（IVR）或 8小时内Callback与通话已经有任务
                        List<Callback> callbacks = callBackDao.findAllocatedCallbacks(specification);
                        if(callbacks.isEmpty()) {
                            //创建任务
                            CallbackEx callDetail = callbackExs.get(i);
                            List<LeadInteraction> list = new ArrayList<LeadInteraction>();
                            /* TODO 无ACD组 */
                            LeadInteraction li = callInitService.createLi(user, customer, customer.getPhone2(), callDetail.getDnis(), callDetail.getCaseId(), "", callDetail.getCrdt(), callDetail.getDbdt(),
                                    assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),  CampaignTaskSourceType2.SNATCHIN.getIndex(), -1);
                            list.add(li);
                            //生成Callback、CallbackLog
                            for(LeadInteraction item : list){
                                createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId);
                                li.setLead(null);
                            }
                            //回写用户
                            callDetail.setUsrId(assignAgentDto.getUserId());
                            callDetail.setMddt(new Date());
                            callDetail.setMdusr(user.getUserId());
                            if(--m == 0) break;
                        } else {
                            //1、查询待分配IVR（包含已经分配过，需要比较）
                            String instId = "";
                            List<LeadInteraction> list = new ArrayList<LeadInteraction>();
                            List<String> connNumbers = new ArrayList<String>();
                            for(Callback callback: callbacks) {
                                connNumbers.add(callback.getCaseId());
                                if(StringUtils.isBlank(instId)){
                                    instId = String.valueOf(callback.getTaskId());
                                }
                            }
                            CallbackEx callDetail = callbackExs.get(i);
                            LeadInteraction li = callInitService.createLiWithInstId(user, customer, instId, customer.getPhone2(), callDetail.getDnis(), callDetail.getCaseId(), "", callDetail.getCrdt(), callDetail.getDbdt(),
                                    assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),  CampaignTaskSourceType2.SNATCHIN.getIndex(), -1);
                            list.add(li);
                            //生成Callback、CallbackLog
                            for(LeadInteraction item : list){
                                createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId);
                                li.setLead(null);
                            }
                            //回写用户
                            callDetail.setUsrId(assignAgentDto.getUserId());
                            callDetail.setMddt(new Date());
                            callDetail.setMdusr(user.getUserId());
                            if(--m == 0) break;
                        }
                    }
                }
            }
        }
        catch (ServiceException ex){
            logger.error("assignCallbackCount", ex);
        }
    }

    private void createCallback(AgentUser user, CustomerDto customer, AssignAgentDto assignAgentDto,Lead lead, LeadInteraction li, String batchId){
        Callback callback = new Callback();
        callback.setName("CALLBACK_WILCOM");
        callback.setType(CallbackType.CALLBACK_WILCOM.getStringIndex());
        callback.setBatchId(batchId);
        callback.setAllocatedManual(false);
        callback.setCaseId(li.getCallId());
        callback.setAcdGroup(li.getAcdGroup());
        callback.setAni(li.getAni());
        callback.setDnis(li.getDnis());
        callback.setPhn1(customer.getPhone1());
        callback.setPhn2(customer.getPhone2());
        callback.setPhn3(customer.getPhone3());
        callback.setLeadInteractionId(li.getId());
        callback.setAllocateCount(1L);
        callback.setUserAssigner(user.getUserId());
        callback.setGroupAssigner(user.getUserId());
        callback.setFirstdt(new Date());
        callback.setFirstusrId(assignAgentDto.getUserId());
        callback.setDbdt(new Date());
        callback.setDbusrId(user.getUserId());
        callback.setRdbusrId(assignAgentDto.getUserId());
        callback.setUsrId(user.getUserId());
        callback.setUsrGrp(user.getWorkGrp());
        callback.setOpusr(assignAgentDto.getUserId());
        callback.setTaskId(Long.parseLong(li.getBpmInstId()));
        callback.setCrdt(new Date());
        callback.setPriority("1");
        //callback.setContactId(customer.getContactId());
        //callback.setLatentcontactId(customer.getPotentialContactId());
        if("2".equals(customer.getContactType())){   //customer.getCustomerType() ? xieguoqian
            callback.setLatentcontactId(customer.getContactId());
        } else {
            callback.setContactId(customer.getContactId());
        }


        if(lead != null && lead.getCampaignId() != null){
            callback.setMediaplanId(String.valueOf(lead.getCampaignId()));
        }

        callback = callBackDao.save(callback);
        createCallbackLog(user, callback, batchId);
    }

    private void createCallbackLog(AgentUser user, Callback callback, String batchId){
        //记录登记分配历史
        CallbackLog callbackLog = new CallbackLog();
        callbackLog.setName("CALLBACK_WILCOM");
        callbackLog.setType(CallbackType.CALLBACK_WILCOM.getStringIndex());
        callbackLog.setCallbackId(callback.getCallbackId());
        callbackLog.setBatchId(batchId);
        callbackLog.setFirstusrId(callback.getFirstusrId());
        callbackLog.setFirstdt(callback.getFirstdt());
        callbackLog.setAcdGroup(callback.getAcdGroup());
        BeanUtils.copyProperties(callback, callbackLog);
        callbackLogDao.save(callbackLog);
    }
}
