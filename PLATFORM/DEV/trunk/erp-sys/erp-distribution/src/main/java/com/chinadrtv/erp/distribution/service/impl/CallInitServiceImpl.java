package com.chinadrtv.erp.distribution.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.distribution.service.CallInitService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-16
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
@Service("callInitService")
public class CallInitServiceImpl implements CallInitService {

    private static transient final Logger logger = LoggerFactory.getLogger(CallInitServiceImpl.class);

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

    public CustomerDto createCustomer(String contactId, String potentialContactId, String ani, AgentUser user) {

        if(StringUtils.isNotBlank(contactId)) {

        } else  if(StringUtils.isNotBlank(potentialContactId)) {

        } else{

        }
        return createCustomer(user, ani);
    }

    public CustomerDto createCustomer(AgentUser user, String ani) {

        try
        {
            PhoneAddressDto dto = getValidPhoneAddress(ani);
            if(dto != null)
            {
                CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
                customerBaseSearchDto.setPhone(dto.getAni());
                DataGridModel dgm = new DataGridModel();
                dgm.setPage(1);
                dgm.setRows(2);
                Map<String, Object> map = contactService.findByBaseConditionForDistribution(customerBaseSearchDto, dgm);
                Integer count = (Integer)map.get("total");
                if(count != null && count > 0){
                    List<CustomerDto> customers = (List<CustomerDto>)map.get("rows");
                    if(customers != null && customers.size() > 0){
                        CustomerDto customer = customers.get(0);
                        for(CustomerDto customerDto : customers){
                            if(customer.getCrdtDate() != null &&
                                    customerDto.getCrdtDate() != null &&
                                    customer.getCrdtDate().getTime() > customerDto.getCrdtDate().getTime()){
                                customer = customerDto;
                            }
                        }
                        initCustomerDto(customer, dto);
                        return customer;
                    }
                } else {
                    return createPotentialContact(user, dto);
                }
            }
            return null;
        }
        catch (Exception ex){
            logger.error("获取电话"+ani+"对应的用户失败", ex);
            throw new RuntimeException("获取电话"+ani+"对应的用户失败", ex);
        }
    }

    private PhoneAddressDto getValidPhoneAddress(String ani){

        PhoneAddressDto dto = phoneService.splicePhone(ani);
        if(!"6".equals(dto.getPhonetypid())){
            dto.setAni(ani);
            return dto;
        }
        if(ani.length() > 12){
            ani = ani.substring(ani.length() -12, ani.length());
            dto = phoneService.splicePhone(ani);
            if(!"6".equals(dto.getPhonetypid())){
                dto.setAni(ani);
                return dto;
            }
            ani = ani.substring(ani.length() -11, ani.length());
            dto = phoneService.splicePhone(ani);
            if(!"6".equals(dto.getPhonetypid())){
                dto.setAni(ani);
                return dto;
            }
        }
        return null;
    }

    private void initCustomerDto(CustomerDto customerDto,PhoneAddressDto phone) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        customerDto.setCrdt(sdf.format(now));

        sdf = new SimpleDateFormat("HH:mm:ss");
        customerDto.setCrtm(sdf.format(now));

        if(phone.getPhonetypid() != null && phone.getPhonetypid().equals("4")){
            customerDto.setPhone1(phone.getPhn1());
            customerDto.setPhone2(phone.getPhn2());
            customerDto.setPhone3(phone.getPhn3());
            customerDto.setPhoneNum("Y");
            customerDto.setPhoneType("4");
        }else{
            customerDto.setPhone1(phone.getPhn1());
            customerDto.setPhone2(phone.getPhn2());
            customerDto.setPhone3(phone.getPhn3());
            customerDto.setPhoneNum("Y");
            customerDto.setPhoneType("1");

        }
    }

    private CustomerDto createPotentialContact(AgentUser user, PhoneAddressDto phone){

        if(StringUtils.isNotBlank(phone.getPhn2())){
            try {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setCrusr(user.getUserId());
                initCustomerDto(customerDto, phone);
                Map<String, Object> resultMap = contactService.saveCustomer(customerDto);
                Object potentialContactId = resultMap.get("potentialContactId");
                if(potentialContactId != null){
                    customerDto.setCustomerType("2");
                    customerDto.setContactType("2");
                    customerDto.setContactId(String.valueOf(potentialContactId));
                }
                Object contactId = resultMap.get("contactId");
                if(contactId != null){
                    customerDto.setContactId(String.valueOf(contactId));
                }
                return customerDto;
            } catch (Exception ex){
                logger.error("保存客户失败："+phone.getPhn2(), ex);
                return null;
            }
        }
        return  null;
    }

    public Lead createLead(AgentUser agentUser,  CustomerDto customerDto, String callerId, String calleeId, String cticonnId, Date inDate, Date outDat) throws ServiceException {
        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CampaignDto dto = campaignApiService.queryInboundCampaign(calleeId, dateformat1.format(inDate));
        Lead lead = new Lead();
        lead.setOutbound(false);
        lead.setCampaignId(dto.getId());
        lead.setOwner(agentUser.getUserId());
        lead.setAni(callerId);
        lead.setDnis(calleeId);
        lead.setTaskSourcType(CampaignTaskSourceType.INCOMING.getIndex());
        //0:呼入;1:呼出
        lead.setCallDirection(0L);
        //0：开始，1：正常结束，2：系统关闭，3：客户拒绝，4：其他原因结束
        lead.setStatus(0L);
        if("2".equals(customerDto.getContactType() )){
            lead.setPotentialContactId(customerDto.getContactId());
        } else {
            lead.setContactId(customerDto.getContactId());
        }
        lead.setUserGroup(agentUser.getWorkGrp());
        lead.setBeginDate(new Date());
        lead.setCreateUser(agentUser.getUserId());
        lead.setCreateDate(new Date());
        lead.setUpdateUser(agentUser.getUserId());
        lead.setUpdateDate(new Date());
        return lead;
    }

    public LeadInteraction createLeadInteraction(AgentUser agentUser, String callerId, String calleeId, String cticonnId, String acdGroup, Date inDate, Date outDate) throws ServiceException {
        //添加交互
        LeadInteraction leadInteraction = new LeadInteraction();
        leadInteraction.setCreateUser(agentUser.getUserId());
        leadInteraction.setCreateDate(new Date());
        leadInteraction.setBeginDate(inDate);
        leadInteraction.setEndDate(outDate);
        leadInteraction.setGroupCode(agentUser.getWorkGrp());
        leadInteraction.setCtiStartDate(inDate);
        leadInteraction.setCtiEndDate(outDate);
        leadInteraction.setCallId(cticonnId);
        leadInteraction.setAcdGroup(acdGroup);
        leadInteraction.setInterActionType(LeadInteractionType.INBOUND_IN.getIndexString());
        leadInteraction.setAni(callerId);
        leadInteraction.setDnis(calleeId);
        leadInteraction.setIsValid(1l);
        leadInteraction.setAllocateCount(0l);
        leadInteraction.setStatus("0");
        leadInteraction.setResultCode("-1"); //新建时设置为-1
        leadInteraction.setOperation("呼入");
        leadInteraction.setComments("话务分配创建任务");
        leadInteraction.setCallResult("0");
        leadInteraction.setCallType("Call Back");
        return leadInteraction;
    }

    public LeadInteraction createLiWithInstId(AgentUser user, CustomerDto customerDto,String instId, String callerId, String calleeId, String cticonnId,String acdGroup, Date inDate, Date outDate, String owner, Integer sourceType1, Integer sourceType2, Integer sourceType3) throws ServiceException {
        Lead lead = createLead(user, customerDto, callerId, calleeId, cticonnId, inDate, outDate);
        lead.setOwner(owner);
        lead.setTaskSourcType(sourceType1);
        lead.setTaskSourcType2(sourceType2);
        lead.setTaskSourcType3(sourceType3);
        LeadInteraction li = createLeadInteraction(user, callerId, calleeId, cticonnId, acdGroup, inDate, outDate);
        leadService.insertLead(lead);
        if(StringUtils.isNotBlank(lead.getContactId())){
            li.setContactId(lead.getContactId());
        } else {
            li.setContactId(lead.getPotentialContactId());
        }
        li.setLeadId(lead.getId());
        li.setIsValid(1L);
        li.setBpmInstId(instId);
        li.setAcdGroup(acdGroup);

        if(null == li.getLead()) {
            lead = leadService.get(li.getLeadId());
            li.setLead(lead);
        }

        leadInterActionService.insertLeadInterAction(li);
        return li;
    }

    public LeadInteraction createLi(AgentUser user, CustomerDto customerDto, String callerId, String calleeId, String cticonnId, String acdGroup, Date inDate, Date outDate, String owner, Integer sourceType1, Integer sourceType2, Integer sourceType3) throws ServiceException {
        //创建任务
        Lead lead = createLead(user, customerDto, callerId, calleeId, cticonnId, inDate, outDate);
        lead.setOwner(owner);
        lead.setTaskSourcType(sourceType1);
        lead.setTaskSourcType2(sourceType2);
        lead.setTaskSourcType3(sourceType3);
        LeadTask task = leadService.saveLead(lead);
        //创建交互
        //campaignBPMTaskService.updateTaskStatus(task.getBpmInstanceId(), String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()), null, null);

        LeadInteraction li = createLeadInteraction(user, callerId, calleeId, cticonnId, acdGroup, inDate, outDate);
        if(StringUtils.isNotBlank(lead.getContactId())){
            li.setContactId(lead.getContactId());
        } else {
            li.setContactId(lead.getPotentialContactId());
        }
        li.setLeadId(task.getLeadId());
        li.setIsValid(1L);
        li.setAcdGroup(acdGroup);
        li.setBpmInstId(task.getBpmInstanceId());

        if(null == li.getLead()) {
            lead = leadService.get(li.getLeadId());
            li.setLead(lead);
        }

        leadInterActionService.insertLeadInterAction(li);
        return li;
    }

}
