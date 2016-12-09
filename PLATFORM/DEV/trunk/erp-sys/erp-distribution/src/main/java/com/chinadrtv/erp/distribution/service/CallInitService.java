package com.chinadrtv.erp.distribution.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.user.model.AgentUser;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-16
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public interface CallInitService {
    CustomerDto createCustomer(AgentUser user, String ani);
    CustomerDto createCustomer(String contactId, String potentialContactId, String ani, AgentUser user);
    Lead createLead(AgentUser agentUser,  CustomerDto customerDto, String callerId, String calleeId, String cticonnId, Date inDate, Date outDat) throws ServiceException;
    LeadInteraction createLi(AgentUser user, CustomerDto customerDto, String callerId, String calleeId, String cticonnId, String acdGroup, Date inDate, Date outDate,  String owner, Integer sourceType1, Integer sourceType2, Integer sourceType3) throws ServiceException;
    LeadInteraction createLiWithInstId(AgentUser user, CustomerDto customerDto,String instId, String callerId, String calleeId, String cticonnId,String acdGroup, Date inDate, Date outDate,  String owner, Integer sourceType1, Integer sourceType2, Integer sourceType3) throws ServiceException;
}
