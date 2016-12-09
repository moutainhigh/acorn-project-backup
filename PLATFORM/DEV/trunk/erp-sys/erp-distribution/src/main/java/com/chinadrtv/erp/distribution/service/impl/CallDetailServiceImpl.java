package com.chinadrtv.erp.distribution.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.distribution.dao.CallDetailDao;
import com.chinadrtv.erp.distribution.dto.CallDetail;
import com.chinadrtv.erp.distribution.service.CallDetailService;
import com.chinadrtv.erp.distribution.service.CallInitService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.model.marketing.*;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.AcdGroupDao;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * 通话详细记录服务类
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
@Service("callDetailService")
public class CallDetailServiceImpl implements CallDetailService {

    private static final Logger logger = LoggerFactory.getLogger(CallDetailServiceImpl.class);

    @Autowired
    private CallDetailDao callDetailDao;

    @Autowired
    private CallBackDao callBackDao;

    @Autowired
    private CallbackLogDao callbackLogDao;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private LeadService leadService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private CallInitService callInitService;

    @Autowired
    private BaseAcdInfoService baseAcdInfoService;

    @Autowired
    private BaseCallInfoService baseCallInfoService;


    private void eraseNumbers(List<String> callerNumbers, CallbackSpecification specification) {
        HashMap<String, Long> allocatedNumbers = new HashMap<String, Long>();
        String ani = specification.getAni();
        try {
            //Oracle IN的不超过1000个
            for (int n = 0; n < callerNumbers.size(); n+=1000) {
                List<String> temp = callerNumbers.subList(n, Math.min(callerNumbers.size(), n + 1000));
                specification.setAni(StringUtils.join(temp, ','));
                Map<String, Long> map = callBackDao.findAllocatedNumbers(specification);
                if(!map.isEmpty()) {
                    allocatedNumbers.putAll(map);
                }
            }
        } catch (Exception ex){
            logger.error("去重查询异常："+ex.getMessage(), ex);
        } finally {
            specification.setAni(ani);
        }

        List<BaseCallInfo> calls = baseCallInfoService.getActiveBaseCalls(0);

        for(String callerId : new ArrayList<String>(callerNumbers)) {
            //已分配（IVR）或 8小时内Callback与通话已经有任务
            if(allocatedNumbers.containsKey(callerId) && allocatedNumbers.get(callerId) > 0){
                callerNumbers.remove(callerId);
            }
            else if(callerId.startsWith("0")){
                String callerId2 = callerId.substring(1, callerId.length());
                if(allocatedNumbers.containsKey(callerId2) && allocatedNumbers.get(callerId2) > 0){
                    callerNumbers.remove(callerId);
                }
            }

            if(callerNumbers.contains(callerId)){
                //去除无效号码
                for(BaseCallInfo call : calls) {
                    if(StringUtils.isNotBlank(call.getInclusion()) &&
                       StringUtils.isNotBlank(call.getExclusion())){
                        Pattern pattern = Pattern.compile(call.getInclusion());
                        if(pattern.matcher(callerId).matches() && callerId.startsWith(call.getExclusion())) {
                            callerNumbers.remove(callerId);
                            break;
                        }
                    }
                }
            }
        }
    }

    public Long findIvrCount(CallbackSpecification specification){
        init(specification, true);
        List<String> callerNumbers = callDetailDao.findIvrNumbers(specification);

        eraseNumbers(callerNumbers, specification);

        return Long.valueOf(callerNumbers.size());
    }



    public List<String> assignIvrCount(CallbackSpecification specification, List<AssignAgentDto> agents) {
        init(specification, true);
        return assignCount(specification, agents, true);
    }

    public Long findAbandonCount(CallbackSpecification specification){
        init(specification, false);
        List<String> callerNumbers = callDetailDao.findAbandonNumbers(specification);
        eraseNumbers(callerNumbers, specification);
        return Long.valueOf(callerNumbers.size());
    }

    public List<String> assignAbandonCount(CallbackSpecification specification, List<AssignAgentDto> agents){
        init(specification, false);
        return assignCount(specification, agents, false);
    }

    private List<String> assignCount(CallbackSpecification specification, List<AssignAgentDto> agents, Boolean ivrOrAbandon) {
        AgentUser user = SecurityHelper.getLoginUser();
        String batchId = String.valueOf(new Date().getTime());

        List<String> callerNumbers = ivrOrAbandon ? callDetailDao.findIvrNumbers(specification) : callDetailDao.findAbandonNumbers(specification);

        eraseNumbers(callerNumbers, specification);

        List<String> wrongNumbers = new ArrayList<String>();

        //创建任务
        Integer n = 0;
        for (AssignAgentDto assignAgentDto : agents) {
            Integer m = assignAgentDto.getAssignCount();
            for(Integer i = n; i < callerNumbers.size(); i++) {
                try
                {
                    CustomerDto customer = callInitService.createCustomer(user, callerNumbers.get(i));
                    if(customer == null) {
                        wrongNumbers.add(callerNumbers.get(i));
                        n++;
                        continue;
                        //throw new RuntimeException("客户电话号码非法("+callerNumbers.get(i)+")");
                    }
                    if(StringUtils.isBlank(customer.getPhone2())){
                        customer.setPhone2(callerNumbers.get(i));
                    }
                    specification.setAni(callerNumbers.get(i));

                    List<CallDetail> callDetails = ivrOrAbandon ? callDetailDao.findIvrDetails(specification) : callDetailDao.findAbandonDetails(specification);
                    if(!callDetails.isEmpty()) {
                        //已分配（IVR）或 8小时内Callback与通话已经有任务
                        createCallbackIfEmpty(user, batchId, assignAgentDto, customer, callDetails, ivrOrAbandon);
                        n++;
                        if(--m == 0) break;
                        /*
                        List<Callback> callbacks = callBackDao.findAllocatedCallbacks(specification);
                        if(callbacks.isEmpty()) {
                            createCallbackIfEmpty(user, batchId, assignAgentDto, customer, callDetails, ivrOrAbandon);
                            if(--m == 0) break;
                        } else {
                            createCallbackIfNotEmpty(user, batchId, assignAgentDto, customer, callDetails, callbacks, ivrOrAbandon);
                        }
                        */
                    } else {
                       n++;
                    }
                } catch (Exception ex) {
                    logger.error("号码异常:"+callerNumbers.get(i), ex);
                    wrongNumbers.add(callerNumbers.get(i));
                }
            }
        }
        //处理非法号码
        for(String callerId : wrongNumbers) {
            baseCallInfoService.excludeCallNumber(0, callerId);
        }
        return wrongNumbers;
    }

    private void init(CallbackSpecification specification, Boolean ivr) {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            //数据库配置的IVR对应acd组

            if(StringUtils.isBlank(specification.getAcdGroup())){
                List<BaseAcdInfo> acdGroups = baseAcdInfoService.getBaseAcdsByDeptNo(user.getDepartment());
                String groups = "";
                for(BaseAcdInfo acdGroup : acdGroups){
                    if(StringUtils.isNotBlank(groups)) groups += ",";
                    groups += acdGroup.getAcd();
                }
                specification.setAcdGroup(groups);
            }
            if(specification.getStartDate() == null){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH,-3);
                specification.setStartDate(c.getTime());
            }

            if(specification.getEndDate() == null){
                specification.setEndDate(new Date());
            }
        }
    }

    private void createCallbackIfNotEmpty(AgentUser user, String batchId, AssignAgentDto assignAgentDto, CustomerDto customer, List<CallDetail> callDetails, List<Callback> callbacks, Boolean ivrOrAbandon) throws ServiceException {
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
        for(CallDetail callDetail : callDetails){
            if(!connNumbers.contains(callDetail.getConnId())){
                LeadInteraction li = callInitService.createLiWithInstId(user, customer, instId, callDetail.getBk1(), callDetail.getBk2(), callDetail.getConnId(), callDetail.getSkillGroup(), callDetail.getVdnInTime(), callDetail.getVdnOutTime(),
                        assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(), ivrOrAbandon ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);
                list.add(li);
            }
        }
        //2、生成IVR,Callback、CallbackLog
        for(LeadInteraction item : list){
            createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, ivrOrAbandon);
            item.setLead(null); //保存有问题
        }
    }

    private void createCallbackIfEmpty(AgentUser user, String batchId, AssignAgentDto assignAgentDto, CustomerDto customer, List<CallDetail> callDetails, Boolean ivrOrAbandon) throws ServiceException {
        //1、创建Task: contact, lead, LeadInteraction
        //取第一个IVR记录创建任务
        CallDetail callDetail = callDetails.get(0);
        List<LeadInteraction> list = new ArrayList<LeadInteraction>();
        LeadInteraction li = callInitService.createLi(
                user, customer, callDetail.getBk1(), callDetail.getBk2(), callDetail.getConnId(), callDetail.getSkillGroup(), callDetail.getVdnInTime(), callDetail.getVdnOutTime(),
                assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(), ivrOrAbandon ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);
        list.add(li);
        //其他IVR记录发配相同任务
        for(Integer index = 1; index < callDetails.size(); index++){
            callDetail = callDetails.get(index);
            li = callInitService.createLiWithInstId(user, customer, li.getBpmInstId(), callDetail.getBk1(), callDetail.getBk2(), callDetail.getConnId(), callDetail.getSkillGroup(), callDetail.getVdnInTime(), callDetail.getVdnOutTime(),
                    assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(), ivrOrAbandon ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);
            list.add(li);
        }
        //2、生成IVR,Callback、CallbackLog
        for(LeadInteraction item : list){
            createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, ivrOrAbandon);
            item.setLead(null); //保存有问题
        }
    }

    private void createCallback(AgentUser user, CustomerDto customer, AssignAgentDto assignAgentDto, Lead lead, LeadInteraction li, String batchId, Boolean ivrOrAbandon){
        Callback callback = new Callback();
        if(ivrOrAbandon){
            callback.setName("VM");
            callback.setType(CallbackType.IVR.getStringIndex());
        }
        else
        {
            callback.setName("ABANDON");
            callback.setType(CallbackType.GIVEUP.getStringIndex());
        }
        callback.setUsrId(user.getUserId());
        callback.setUsrGrp(user.getWorkGrp());
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
        callbackLog.setName(callback.getName());
        callbackLog.setType(callback.getType());
        callbackLog.setCallbackId(callback.getCallbackId());
        callbackLog.setBatchId(batchId);
        callbackLog.setFirstusrId(callback.getFirstusrId());
        callbackLog.setFirstdt(callback.getFirstdt());
        BeanUtils.copyProperties(callback, callbackLog);
        callbackLogDao.save(callbackLog);
    }


}
