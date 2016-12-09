package com.chinadrtv.erp.distribution.service.impl;

import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.uc.dao.IvrcallbackDao;
import com.chinadrtv.erp.uc.dao.WilcomCallDetailDao;
import com.chinadrtv.erp.uc.dto.IvrGrpNumCti;
import com.chinadrtv.erp.uc.dto.IvrUser;
import com.chinadrtv.erp.uc.dto.Ivrdist;
import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.distribution.service.CallInitService;
import com.chinadrtv.erp.distribution.service.WilcomCallDetailService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.model.marketing.*;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.AcdGroupDao;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 通话详细记录服务类
 * User: gaudi
 * Date: 13-12-16
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
@Service("wilcomCallDetailService")
public class WilcomCallDetailServiceImpl implements WilcomCallDetailService {

    @Autowired
    private IvrcallbackDao ivrcallbackDao;

    @Autowired
    private AcdGroupDao acdGroupDao;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("WilcomWxCallDetailDao")
    private WilcomCallDetailDao wxCallDetailDao;

    @Autowired
    @Qualifier("WilcomBjCallDetailDao")
    private WilcomCallDetailDao bjCallDetailDao;

    @Autowired
    @Qualifier("WilcomShCallDetailDao")
    private WilcomCallDetailDao shCallDetailDao;

    @Autowired
    private CallBackDao callBackDao;

    @Autowired
    private CallbackLogDao callbackLogDao;

    @Autowired
    private CallInitService callInitService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Value("${oldIvrNotified}")
    private Boolean oldIvrNotified;

    public Long findIvrCount(CallbackSpecification specification){
        ivrInit(specification);
        List<String> callerNumbers = GetAllIvrNumbers(specification);
        HashMap<String, Long> allocatedNumbers = new HashMap<String, Long>();
        String ani = specification.getAni();
        try {
            //Oracle IN的不超过1000个
            for (int n = 0; n < callerNumbers.size(); n+=1000) {
                List<String> temp = callerNumbers.subList(n, Math.min(callerNumbers.size(), n + 1000));
                specification.setAni(StringUtils.join(temp, ','));
                Map<String, Long> map = callBackDao.findAllocatedNumbers(specification);
                //检查检查老系统分配合并
                Map<String, Long> map2 = ivrcallbackDao.findAllocatedNumbers(specification);
                for(String callerNumber: map2.keySet()){
                    if(map.containsKey(callerNumber)){
                        map.put(callerNumber, map.remove(callerNumber)+map2.get(callerNumber));
                    } else {
                        map.put(callerNumber, map2.get(callerNumber));
                    }
                }
                allocatedNumbers.putAll(map);
            }
        } finally {
            specification.setAni(ani);
        }
        Long result = 0L;
        for(String callerId : callerNumbers) {
            //已分配（IVR）或 8小时内Callback与通话已经有任务
            if(!allocatedNumbers.containsKey(callerId) ||
                 allocatedNumbers.get(callerId) == 0) {
                result++;
            }
        }
        return result;
    }

    public void assignIvrCount(CallbackSpecification specification, List<AssignAgentDto> agents) {
        ivrInit(specification);
        assignIvrCount2(specification, agents);
    }

    public void ivrComplete(String caseId, String userId){
        List<Callback> callbacks = callBackDao.findCallbacksByCaseId(caseId);
        if(callbacks != null && !callbacks.isEmpty()){
            for(Callback callback: callbacks){
                campaignBPMTaskService.completeTaskAndUpdateStatus(String.format("Callback[%d]任务完成", callback.getCallbackId()), String.valueOf(callback.getTaskId()),"",userId);
            }
        }
    }

    public Long findAbandonCount(CallbackSpecification specification){
        abandonInit(specification);

        List<String> callerNumbers = GetAllAbandonNumbers(specification);
        HashMap<String, Long> allocatedNumbers = new HashMap<String, Long>();
        String ani = specification.getAni();
        try {
            //Oracle IN的不超过1000个
            for (int n = 0; n < callerNumbers.size(); n+=1000) {
                List<String> temp = callerNumbers.subList(n, Math.min(callerNumbers.size(), n + 1000));
                specification.setAni(StringUtils.join(temp, ','));
                allocatedNumbers.putAll(callBackDao.findAllocatedNumbers(specification));
            }
        } finally {
            specification.setAni(ani);
        }
        Long result = 0L;
        for(String callerId : callerNumbers) {
            //已分配（IVR）或 8小时内Callback与通话已经有任务
            if(!allocatedNumbers.containsKey(callerId) ||
                    allocatedNumbers.get(callerId) == 0) {
                result++;
            }
        }
        return result;
    }

    public void assignAbandonCount(CallbackSpecification specification, List<AssignAgentDto> agents){
        abandonInit(specification);
        assignCount(specification, agents, "abandon");
    }

    public void assignIvrhist(CallbackSpecification specification, Ivrdist ivrdist) throws ServiceException {
        ivrInit(specification);
        assignIvrdist(specification, ivrdist);
    }

    private void assignIvrCount2(CallbackSpecification specification, List<AssignAgentDto> agents) {

        AgentUser user = SecurityHelper.getLoginUser();
        String batchId = String.valueOf(new Date().getTime());
        List<String> callerNumbers = GetAllIvrNumbers(specification);
        String ani = specification.getAni();
        try {
            Integer n = 0;
            for (AssignAgentDto assignAgentDto : agents) {
                Integer m = assignAgentDto.getAssignCount();
                for(Integer i = n; i < callerNumbers.size(); i++,n++) {
                    specification.setAni(callerNumbers.get(i));

                    List<Ivrdist> ivrdists = ivrcallbackDao.findAllocatedIvrdists(specification);
                    //老系统未分配，直接保存
                    if(ivrdists.isEmpty()){

                        CustomerDto customer = callInitService.createCustomer(user, callerNumbers.get(i));
                        if(customer != null) {
                            if(StringUtils.isBlank(customer.getPhone2())){
                                customer.setPhone2(callerNumbers.get(i));
                            }
                            List<WilcomCallDetail> callDetails = findAllIvrDetails(specification);
                            if(!callDetails.isEmpty()) {
                                Date startDate = specification.getStartDate();
                                Date endDate = specification.getEndDate();
                                try {
                                    //specification.setStartDate(null);
                                    //specification.setEndDate(null);
                                    //已分配（IVR）或 8小时内Callback与通话已经有任务
                                    List<Callback> callbacks = callBackDao.findAllocatedCallbacks(specification);
                                    if(callbacks.isEmpty()) {
                                        //1、创建Task: contact, lead, LeadInteraction
                                        //取第一个IVR记录创建任务
                                        WilcomCallDetail callDetail = callDetails.get(0);
                                        List<LeadInteraction> list = new ArrayList<LeadInteraction>();

                                        LeadInteraction li = callInitService.createLi(user, customer, callDetail.getAni(), callDetail.getDnis(),
                                                callDetail.getCaseid() + callDetail.getArea(), callDetail.getCallgrp(), callDetail.getCreatetime(),
                                                callDetail.getEndtime(), assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),
                                                CampaignTaskSourceType2.VM.getIndex(), -1);

                                        list.add(li);
                                        //回写老系统记录
                                        if(oldIvrNotified) {
                                            ivrcallbackDao.CreateIvrdist(user, callDetail, assignAgentDto.getUserId(), assignAgentDto.getWorkGrp());
                                        }
                                        //其他IVR记录发配相同任务
                                        for(Integer index = 1; index < callDetails.size(); index++){
                                            callDetail = callDetails.get(index);
                                            li = callInitService.createLiWithInstId(user, customer, li.getBpmInstId(), callDetail.getAni(), callDetail.getDnis(), callDetail.getCaseid() + callDetail.getArea(), callDetail.getCallgrp(), callDetail.getCreatetime(), callDetail.getEndtime(),
                                                    assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(), CampaignTaskSourceType2.VM.getIndex(), -1);
                                            list.add(li);
                                            //回写老系统记录
                                            if(oldIvrNotified) {
                                                ivrcallbackDao.CreateIvrdist(user, callDetail, assignAgentDto.getUserId(), assignAgentDto.getWorkGrp());
                                            }
                                        }
                                        //2、生成IVR,Callback、CallbackLog
                                        for(LeadInteraction item : list){
                                            createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, "ivr");
                                            item.setLead(null);
                                        }
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
                                        for(WilcomCallDetail callDetail : callDetails){
                                            String connId=callDetail.getCaseid() + callDetail.getArea();
                                            if(!connNumbers.contains(connId)){
                                                LeadInteraction li = callInitService.createLiWithInstId(user, customer, instId, callDetail.getAni(), callDetail.getDnis(), connId, callDetail.getCallgrp(), callDetail.getCreatetime(), callDetail.getEndtime(),
                                                        assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(), CampaignTaskSourceType2.VM.getIndex(), -1);
                                                list.add(li);
                                            }
                                            //回写老系统记录(一部分)
                                            /*
                                            if(oldIvrNotified) {
                                                ivrcallbackDao.CreateIvrdist(user, callDetail, assignAgentDto.getUserId(), assignAgentDto.getWorkGrp());
                                            }
                                            */
                                        }
                                        //2、生成IVR,Callback、CallbackLog
                                        for(LeadInteraction item : list){
                                            createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, "ivr");
                                            item.setLead(null);
                                        }
                                    }

                                } finally {
                                    specification.setStartDate(startDate);
                                    specification.setEndDate(endDate);
                                }
                            }
                        }
                    }
                }
            }
        } catch (ServiceException ex){

        } finally {
            specification.setAni(ani);
        }
    }

    private void assignIvrdist(CallbackSpecification specification, Ivrdist ivrdist) throws ServiceException {

        AgentUser user = SecurityHelper.getLoginUser();
        if(user == null){
            user = new AgentUser();
            user.setUserId(ivrdist.getRecpsn());
            try {
                user.setWorkGrp(userService.getUserGroup(ivrdist.getRecpsn()));
            } catch (ServiceException ex){

            }
        }
        String batchId = String.valueOf(new Date().getTime());
        String ani = specification.getAni();
        try {
            AssignAgentDto assignAgentDto = new AssignAgentDto();
            assignAgentDto.setAssignCount(1);
            assignAgentDto.setUserId(ivrdist.getUsrid());
            assignAgentDto.setWorkGrp(ivrdist.getGrpid());

            CustomerDto customer = callInitService.createCustomer(user, ivrdist.getAni());
            if(customer != null) {
                if(StringUtils.isBlank(customer.getPhone2())){
                    customer.setPhone2(ivrdist.getAni());
                }
                //1、创建Task: contact, lead, LeadInteraction
                //取第一个IVR记录创建任务
                List<LeadInteraction> list = new ArrayList<LeadInteraction>();

                LeadInteraction li = callInitService.createLi(user, customer, ivrdist.getAni(), ivrdist.getDnis(),
                        ivrdist.getCaseid(), ivrdist.getCallgrp(), ivrdist.getCreatetime(),
                        ivrdist.getCreatetime(), ivrdist.getUsrid(),
                        CampaignTaskSourceType.INCOMING.getIndex(), CampaignTaskSourceType2.VM.getIndex(), -1);

                list.add(li);
                //2、生成IVR,Callback、CallbackLog
                for(LeadInteraction item : list){
                    createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, ivrdist.getType());
                    item.setLead(null);
                }

                /*
                //已分配（IVR）或 8小时内Callback与通话已经有任务
                List<Callback> callbacks = callBackDao.findAllocatedCallbacks(specification);
                if(callbacks.isEmpty()) {
                    //1、创建Task: contact, lead, LeadInteraction
                    //取第一个IVR记录创建任务
                    List<LeadInteraction> list = new ArrayList<LeadInteraction>();

                    LeadInteraction li = callInitService.createLi(user, customer, ivrdist.getAni(), ivrdist.getDnis(),
                            ivrdist.getCaseid(), ivrdist.getCallgrp(), ivrdist.getCreatetime(),
                            ivrdist.getCreatetime(), ivrdist.getUsrid());

                    list.add(li);
                    //2、生成IVR,Callback、CallbackLog
                    for(LeadInteraction item : list){
                        createCallback(user, customer, assignAgentDto, item, batchId, true);
                    }
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
                    //2、生成IVR,Callback、CallbackLog
                    for(LeadInteraction item : list){
                        createCallback(user, customer, assignAgentDto, item, batchId, true);
                    }
                }
                */
            }
            else{
                throw new ServiceException("电话号码"+ivrdist.getAni()+"有错误，创建用户失败");
            }
        } finally {
            specification.setAni(ani);
        }
    }


    private void assignCount(CallbackSpecification specification, List<AssignAgentDto> agents, String type) {

        AgentUser user = SecurityHelper.getLoginUser();
        String batchId = String.valueOf(new Date().getTime());
        List<String> callerNumbers = "ivr".equalsIgnoreCase(type) ? GetAllIvrNumbers(specification) : GetAllAbandonNumbers(specification);
        String ani = specification.getAni();
        try {
            Integer n = 0;
            for (AssignAgentDto assignAgentDto : agents) {
                Integer m = assignAgentDto.getAssignCount();
                for(Integer i = n; i < callerNumbers.size(); i++,n++) {
                    CustomerDto customer = callInitService.createCustomer(user, callerNumbers.get(i));
                    if(customer != null) {
                        if(StringUtils.isBlank(customer.getPhone2())){
                            customer.setPhone2(callerNumbers.get(i));
                        }
                        specification.setAni(customer.getPhone2());
                        List<WilcomCallDetail> callDetails = "ivr".equalsIgnoreCase(type) ? findAllIvrDetails(specification) : findAllAbandonDetails(specification);
                        if(!callDetails.isEmpty()) {
                            Date startDate = specification.getStartDate();
                            Date endDate = specification.getEndDate();
                            try
                            {
                                //specification.setStartDate(null);
                                //specification.setEndDate(null);
                                //已分配（IVR）或 8小时内Callback与通话已经有任务
                                List<Callback> callbacks = callBackDao.findAllocatedCallbacks(specification);
                                if(callbacks.isEmpty()) {
                                    //1、创建Task: contact, lead, LeadInteraction
                                    //取第一个IVR记录创建任务
                                    WilcomCallDetail callDetail = callDetails.get(0);
                                    List<LeadInteraction> list = new ArrayList<LeadInteraction>();
                                    //+ callDetail.getArea()
                                    LeadInteraction li = callInitService.createLi(user, customer, callDetail.getAni(), callDetail.getDnis(),
                                    		callDetail.getCaseid() , callDetail.getCallgrp(), callDetail.getCreatetime(),
                                    		callDetail.getEndtime(), assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),
                                            "ivr".equalsIgnoreCase(type) ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);

                                    list.add(li);
                                    //其他IVR记录发配相同任务
                                    for(Integer index = 1; index < callDetails.size(); index++){
                                        callDetail = callDetails.get(index);
                                        //+ callDetail.getArea()
                                        li = callInitService.createLiWithInstId(user, customer, li.getBpmInstId(), callDetail.getAni(), callDetail.getDnis(), callDetail.getCaseid() , callDetail.getCallgrp(), callDetail.getCreatetime(), callDetail.getEndtime(),
                                                assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),
                                                "ivr".equalsIgnoreCase(type) ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);
                                        list.add(li);
                                    }
                                    //2、生成IVR,Callback、CallbackLog
                                    for(LeadInteraction item : list){
                                        createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, type);
                                        item.setLead(null);
                                    }
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
                                    for(WilcomCallDetail callDetail : callDetails){
                                        // + callDetail.getArea()  数据库视图已经加好
                                        if(!connNumbers.contains(callDetail.getCaseid())){
                                            //+ callDetail.getArea()
                                            LeadInteraction li = callInitService.createLiWithInstId(user, customer, instId, callDetail.getAni(), callDetail.getDnis(), callDetail.getCaseid(), callDetail.getCallgrp(), callDetail.getCreatetime(), callDetail.getEndtime(),
                                                    assignAgentDto.getUserId(), CampaignTaskSourceType.INCOMING.getIndex(),
                                                    "ivr".equalsIgnoreCase(type) ? CampaignTaskSourceType2.VM.getIndex() : CampaignTaskSourceType2.GIVEUP.getIndex(), -1);
                                            list.add(li);
                                        }
                                    }
                                    //2、生成IVR,Callback、CallbackLog
                                    for(LeadInteraction item : list){
                                        createCallback(user, customer, assignAgentDto, item.getLead(), item, batchId, type);
                                        item.setLead(null);
                                    }
                                }

                            } finally {
                                specification.setStartDate(startDate);
                                specification.setEndDate(endDate);
                            }
                        }
                    }
                }
            }
        } catch (ServiceException ex){

        } finally {
            specification.setAni(ani);
        }
    }

    private void createCallback(AgentUser user, CustomerDto customer, AssignAgentDto assignAgentDto, Lead lead, LeadInteraction li, String batchId, String type){
        Callback callback = new Callback();
        if("ivr".equalsIgnoreCase(type)){
            callback.setName("IVR_WILCOM");
            callback.setType(CallbackType.IVR_WILCOM.getStringIndex());
        }
        else if("abandon".equalsIgnoreCase(type)){
            callback.setName("ABANDON_WILCOM");
            callback.setType(CallbackType.GIVEUP_WILCOM.getStringIndex());
        }
        else if("a".equalsIgnoreCase(type)){
            callback.setName("A_CONNECTED_WILCOM");
            callback.setType(CallbackType.CONNECTED_WILCOM.getStringIndex());
        }
        else if("b".equalsIgnoreCase(type)){
            callback.setName("B_CONNECTED_WILCOM");
            callback.setType(CallbackType.CONNECTED_WILCOM.getStringIndex());
        }
        else if("c".equalsIgnoreCase(type)){
            callback.setName("C_CONNECTED_WILCOM");
            callback.setType(CallbackType.CONNECTED_WILCOM.getStringIndex());
        }
        else
        {
            callback.setName("UNKNOWN_WILCOM");
            callback.setType(type);
        }
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
//        callback.setContactId(customer.getContactId());
//        callback.setLatentcontactId(customer.getPotentialContactId());

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

    /**
     * 获取三地进线电话
     * @param specification
     * @return
     */
    private List<String> GetAllIvrNumbers(CallbackSpecification specification){
        List<String> list = new ArrayList<String>();
        List<String> callerNumbers;
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            IvrUser ivrUser = ivrcallbackDao.getIvrUser(user.getUserId());
            if(ivrUser != null && ivrUser.getDist_type() != null){
                if("2".equals(ivrUser.getDist_type())){ //查询三地
                    callerNumbers = wxCallDetailDao.findIvrNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                    callerNumbers = bjCallDetailDao.findIvrNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                    callerNumbers = shCallDetailDao.findIvrNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                }
                else if("1".equals(ivrUser.getDist_type())){ //查询本部门
                    GroupInfo groupInfo = getAgentGroupInfo(user.getWorkGrp());
                    if(groupInfo != null && StringUtils.isNotBlank(groupInfo.getAreaCode())){
                        if("021".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = shCallDetailDao.findIvrNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                        else if("010".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = bjCallDetailDao.findIvrNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                        else if("0510".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = wxCallDetailDao.findIvrNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private List<String> GetAllAbandonNumbers(CallbackSpecification specification){

        List<String> list = new ArrayList<String>();
        List<String> callerNumbers;
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            IvrUser ivrUser = ivrcallbackDao.getIvrUser(user.getUserId());
            if(ivrUser != null && ivrUser.getDist_type() != null){
                if("2".equals(ivrUser.getDist_type())){ //查询三地
                    callerNumbers = wxCallDetailDao.findAbandonNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                    callerNumbers = bjCallDetailDao.findAbandonNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                    callerNumbers = shCallDetailDao.findAbandonNumbers(specification);
                    if(!callerNumbers.isEmpty()) {
                        for(String callerNumber : callerNumbers){
                            if(!list.contains(callerNumber)) {
                                list.add(callerNumber);
                            }
                        }
                    }
                }
                else if("1".equals(ivrUser.getDist_type())){ //查询本部门
                    GroupInfo groupInfo = getAgentGroupInfo(user.getWorkGrp());
                    if(groupInfo != null && StringUtils.isNotBlank(groupInfo.getAreaCode())){
                        if("021".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = shCallDetailDao.findAbandonNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                        else if("010".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = bjCallDetailDao.findAbandonNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                        else if("0510".equals(groupInfo.getAreaCode()))
                        {
                            callerNumbers = wxCallDetailDao.findAbandonNumbers(specification);
                            if(!callerNumbers.isEmpty()) {
                                for(String callerNumber : callerNumbers){
                                    if(!list.contains(callerNumber)) {
                                        list.add(callerNumber);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private WilcomCallDetail findIvrDetail(CallbackSpecification specification, String caseId){
         String callId = specification.getCallId();
         try
         {
             specification.setCallId(caseId);
             List<WilcomCallDetail> details = findAllIvrDetails(specification);
             if(details.size() > 0){
                 return details.get(0);
             }
             else return  null;
         }
         finally {
             specification.setCallId(callId);
         }
    }

    private List<WilcomCallDetail> findAllIvrDetails(CallbackSpecification specification){
        List<WilcomCallDetail> list = new ArrayList<WilcomCallDetail>();
        List<WilcomCallDetail> callDetails;
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            IvrUser ivrUser = ivrcallbackDao.getIvrUser(user.getUserId());
            if(ivrUser != null && ivrUser.getDist_type() != null){
                if(specification.getCallId() != null){  //有CaseID
                    if(specification.getCallId().endsWith("WX")){
                        specification.setCallId(specification.getCallId().substring(0, specification.getCallId().length()-2));

                        callDetails = wxCallDetailDao.findIvrDetails(specification);
                        for(WilcomCallDetail detail : callDetails){
                            list.add(detail);
                            detail.setArea("WX");
                        }
                    }
                    else if(specification.getCallId().endsWith("BJ")){

                        specification.setCallId(specification.getCallId().substring(0, specification.getCallId().length()-2));

                        callDetails = bjCallDetailDao.findIvrDetails(specification);
                        for(WilcomCallDetail detail : callDetails){
                            list.add(detail);
                            detail.setArea("BJ");
                        }
                    }
                    else if(specification.getCallId().endsWith("SH")){
                        specification.setCallId(specification.getCallId().substring(0, specification.getCallId().length()-2));

                        callDetails = shCallDetailDao.findIvrDetails(specification);
                        for(WilcomCallDetail detail : callDetails){
                            list.add(detail);
                            detail.setArea("SH");
                        }
                    }
                }
                else if("2".equals(ivrUser.getDist_type())){ //查询三地

                    callDetails = wxCallDetailDao.findIvrDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("WX");
                    }

                    callDetails = bjCallDetailDao.findIvrDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("BJ");
                    }

                    callDetails = shCallDetailDao.findIvrDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("SH");
                    }
                }
                else if("1".equals(ivrUser.getDist_type())){ //查询本部门
                    GroupInfo groupInfo = getAgentGroupInfo(user.getWorkGrp());
                    if(groupInfo != null && StringUtils.isNotBlank(groupInfo.getAreaCode())){
                        if("021".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = shCallDetailDao.findIvrDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("WX");
                            }
                        }
                        else if("010".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = bjCallDetailDao.findIvrDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("BJ");
                            }
                        }
                        else if("0510".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = wxCallDetailDao.findIvrDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("SH");
                            }
                        }
                    }
                }
            }

        }
        return list;
    }

    private List<WilcomCallDetail> findAllAbandonDetails(CallbackSpecification specification){
        List<WilcomCallDetail> list = new ArrayList<WilcomCallDetail>();
        List<WilcomCallDetail> callDetails;
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            IvrUser ivrUser = ivrcallbackDao.getIvrUser(user.getUserId());
            if(ivrUser != null && ivrUser.getDist_type() != null){
                if("2".equals(ivrUser.getDist_type())){ //查询三地
                    callDetails = wxCallDetailDao.findAbandonDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("WX");
                    }

                    callDetails = bjCallDetailDao.findAbandonDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("BJ");
                    }

                    callDetails = shCallDetailDao.findAbandonDetails(specification);
                    for(WilcomCallDetail detail : callDetails){
                        list.add(detail);
                        detail.setArea("SH");
                    }
                }
                else if("1".equals(ivrUser.getDist_type())){ //查询本部门
                    GroupInfo groupInfo = getAgentGroupInfo(user.getWorkGrp());
                    if(groupInfo != null && StringUtils.isNotBlank(groupInfo.getAreaCode())){
                        if("021".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = shCallDetailDao.findAbandonDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("SH");
                            }
                        }
                        else if("010".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = bjCallDetailDao.findAbandonDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("BJ");
                            }
                        }
                        else if("0510".equals(groupInfo.getAreaCode()))
                        {
                            callDetails = wxCallDetailDao.findAbandonDetails(specification);
                            for(WilcomCallDetail detail : callDetails){
                                list.add(detail);
                                detail.setArea("WX");
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private void ivrInit(CallbackSpecification specification) {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            //数据库配置的IVR对应acd组
            if(StringUtils.isBlank(specification.getAcdGroup())){
                List<IvrGrpNumCti> groupNums = ivrcallbackDao.getIvrGrpNumCtis(user.getDepartment());
                String groups = "";
                for(IvrGrpNumCti groupNum : groupNums){
                    if(StringUtils.isNotBlank(groups)) groups += ",";
                    groups += groupNum.getAcdId();
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

    private void abandonInit(CallbackSpecification specification) {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            //数据库配置的IVR对应acd组
            if(StringUtils.isBlank(specification.getAcdGroup())){
                List<AcdGroup> acdGroups = acdGroupDao.getAcdGroupsByDeptNo(user.getDepartment(), false);
                String groups = "";
                for(AcdGroup acdGroup : acdGroups){
                    if(StringUtils.isNotBlank(groups)) groups += ",";
                    groups += acdGroup.getAcdId();
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

    private GroupInfo getAgentGroupInfo(String groupId){
        try
        {
            return userService.getGroupInfo(groupId);
        }
        catch (Exception ex){
            return null;
        }
    }
}
