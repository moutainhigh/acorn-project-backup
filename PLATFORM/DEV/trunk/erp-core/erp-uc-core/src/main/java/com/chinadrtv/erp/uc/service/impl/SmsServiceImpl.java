/*
 * @(#)SmsServiceImpl.java 1.0 2013-7-2下午1:19:40
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dao.LeadDao;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;
import com.chinadrtv.erp.uc.dao.LeadInteractionDao;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.SmsService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-7-2 下午1:19:40 
 * 
 */
@Service
public class SmsServiceImpl implements SmsService {
	
	private static final String SMS_EXE_DEPARTMENT = "80000";
	
	@Autowired
	private SmsApiService smsApiService;
	@Autowired
	private LeadInteractionDao leadInteractionDao;
	@Autowired
	private LeadDao leadDao;
	@Autowired
	private LeadInteractionOrderService leadInteractionOrderService;
	@Autowired
	private LeadService leadService;

    @Autowired
    private OrderDelDetailService orderDelDetailService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private UserService userService;
	/** 
	 * <p>Title: sendSms</p>
	 * <p>Description: </p>
	 * @param smsSendDto
	 * @throws ServiceException 
	 * @see com.chinadrtv.erp.uc.service.SmsService#sendSms(com.chinadrtv.erp.marketing.core.dto.SmsSendDto, String orderId)
	 */ 
	public void sendSms(SmsSendDto smsSendDto, String orderId) throws ServiceException {
		
		AgentUser user = SecurityHelper.getLoginUser();
		smsSendDto.setDepartment(SMS_EXE_DEPARTMENT);
		smsSendDto.setSource(SMS_SOURCE);
		smsSendDto.setCreator(user.getUserId());
		
		Lead lead = leadService.getLastestAliveLead(user.getUserId(), smsSendDto.getCustomerId());
		
		String uuid = smsApiService.singleSmsSend(smsSendDto);
		
		SmsSendDto ssd = smsApiService.getSmsByUuid(uuid);
		
		if(null!=lead && null!=lead.getId()){
			LeadInteraction li = new LeadInteraction();
			li.setLead(lead);
			li.setLeadId(lead.getId());
			li.setCreateDate(new Date());
			li.setCreateUser(user.getUserId());
			li.setStatus("0");
			li.setReserveDate(null);
			li.setResultCode(null);
			li.setResultDescriptiong(null);
			li.setComments(null);
			li.setTimeLength(0L);
			li.setAppResponseCode(uuid);
			li.setInterActionType(LeadInteractionType.SMS.getIndex() + "");
			li.setAppResponseStatus(ssd.getSmsStatus());
			li.setAppContent(smsSendDto.getSmsContent());
			li.setContactId(smsSendDto.getCustomerId());
			li.setOperation(null);
			
			li = (LeadInteraction) leadInteractionDao.save(li);

			if(null!=orderId && !"".equals(orderId)){
				String[] orderIdArr = orderId.split(",");
				for(String _orderId : orderIdArr){
					LeadInteractionOrder lio = new LeadInteractionOrder();
					lio.setLeadInteraction(li);
					lio.setOrderId(_orderId);
					leadInteractionOrderService.save(lio);
				}
			}
		}
	}

    /**
     * 创建订单发送短信
     *
     * @param user
     * @param order
     * @throws ServiceException
     */
    public void sendOrderMessage(AgentUser user, Order order) throws ServiceException {
        if (order == null || order.getOrderid() == null) {
            return;
        }
        if (StringUtils.isBlank(order.getContactid()) || order.getTotalprice() == null) {
            order = orderDelDetailService.queryOrderhistbyorid(order.getOrderid());
            if (order == null) {
                return;
            }
        }
        //部分订单类型不能发送短信
        String[] notSendMessageOrderType = "98,100,103,104,106,107,108,109,110,115,121,122,123,124,127,98,99,105,111,112,114,126".split(",");
        for (String orderType : notSendMessageOrderType) {
            if (orderType.equalsIgnoreCase(order.getOrdertype())) {
                return;
            }
        }
        String mobile = getContactMobile(order.getContactid());
        if (StringUtils.isNotBlank(mobile)) {
            SmsSendDto smsSendDto = new SmsSendDto();
            smsSendDto.setMobile(mobile);
            smsSendDto.setDepartment("80000");
            smsSendDto.setCustomerId(order.getContactid());
            smsSendDto.setCreator(user.getUserId());
            String groupType = userService.getGroupType(user.getUserId());
            if ("in".equalsIgnoreCase(groupType)) {
                smsSendDto.setOrderType("inbound");
            } else {
                smsSendDto.setOrderType("outbound");
            }
            smsSendDto.setPrice(order.getTotalprice().toString());
            smsSendDto.setOrderId(order.getOrderid());
            smsSendDto.setSource("SALES");
            smsApiService.SmsSendByOrderType(smsSendDto);
        }

    }
    private String getContactMobile(String contactId) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        Map<String, Object> phoneMap = phoneService.findByContactId(contactId, dataGridModel);
        if (phoneMap == null || phoneMap.get("rows") == null) {
            return null;
        }
        List<Phone> phoneList = (List<Phone>) phoneMap.get("rows");
        if (!phoneList.isEmpty()) {
            for (Phone phone : phoneList) {
                if ("Y".equalsIgnoreCase(phone.getPrmphn()) && phone.getPhn2().length() == 11) {
                    return phone.getPhn2();
                }
            }
            for (Phone phone : phoneList) {
                if (phone.getPhn2().length() == 11) {
                    return phone.getPhn2();
                }
            }
        }
        return null;
    }
}
