/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressChange;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PhoneChange;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.service.OrderChangeService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.dto.AuditInfoDto;
import com.chinadrtv.erp.sales.service.AuditTaskProcessService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.AddressChangeService;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneChangeService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
/**
 * 2013-6-25 上午9:31:23
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("auditTaskProcessService")
public class AuditTaskProcessServiceImpl implements AuditTaskProcessService{
	private static final Logger logger = LoggerFactory.getLogger(AuditTaskProcessServiceImpl.class);
	@Autowired
	private OrderhistService orderhistService;
	@Autowired
	private PhoneService phoneService;

	@Autowired
	private AddressChangeService addressChangeService;

	@Autowired
	private PhoneChangeService phoneChangeService;
	
	@Autowired
	private OrderChangeService oderChangeService;
	
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private AddressService addressService;

    public boolean updateAuditTaskBatch(AuditInfoDto[] auditInfos) throws Exception {
		boolean isSuc = false;
		try
        {
            //优先处理购物车流程
			/*
            List<AuditInfoDto> list = Arrays.asList(auditInfos);
            Collections.sort(list, new Comparator<AuditInfoDto>() {
                public int compare(AuditInfoDto arg0, AuditInfoDto arg1) {
                    return !"3".equals(arg0.getBusiType()) && "3".equals(arg1.getBusiType()) ? 1 : 0;
                }
            });
			*/
            Map<AuditInfoDto, UserBpmTask> map1 = new HashMap<AuditInfoDto, UserBpmTask>();
            Map<AuditInfoDto, UserBpmTask> map2 = new HashMap<AuditInfoDto, UserBpmTask>();
            Map<AuditInfoDto, UserBpmTask> map3 = new HashMap<AuditInfoDto, UserBpmTask>();
            for (AuditInfoDto auditInfo : auditInfos) {
				UserBpmTask task = userBpmTaskService.queryTaskByInstId(auditInfo.getInstId());

                if (StringUtils.isNotBlank(task.getParentInsId())) {
                    //不处理 gaudi.gao
                    continue;
                } else if("3".equals(task.getBusiType())){
                	map3.put(auditInfo, task);
                } else if("7".equals(task.getBusiType())) { //购物车只能 关联 运费
                    map1.put(auditInfo, task);
                } else{
                    map2.put(auditInfo, task);
                }
			}
            for(AuditInfoDto auditInfo : map3.keySet()){
                updateAuditTask(auditInfo, map3.get(auditInfo));
            }
            for(AuditInfoDto auditInfo : map1.keySet()){
                updateAuditTask(auditInfo, map1.get(auditInfo));
            }
            for(AuditInfoDto auditInfo : map2.keySet()){
                updateAuditTask(auditInfo, map2.get(auditInfo));
            }
			//新增订单,更新订单状态
			AuditInfoDto ainfo = auditInfos[0];
			if(String.valueOf(AuditTaskType.ORDERADD.getName()).equals(ainfo.getBusiType())) {
				if(String.valueOf(AuditTaskStatus.APPROEED.getIndex()).equals(ainfo.getStatus())) {
					orderhistService.auditResultOnOrderCreate(auditInfos[0].getId(), true, SecurityHelper.getLoginUser().getUserId());
				} else {
					orderhistService.auditResultOnOrderCreate(auditInfos[0].getId(), false, SecurityHelper.getLoginUser().getUserId());
				}
			}
			
			//有一个修改订单批准流程，在更新订单时，需要调用订单规则校验
			if(String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(ainfo.getBusiType())) {
				for(AuditInfoDto auditInfo : auditInfos) {
					if(String.valueOf(AuditTaskStatus.APPROEED.getIndex()).equals(auditInfo.getStatus())) {
						orderhistService.checkAuditedOrder(orderhistService.getOrderHistByOrderid(auditInfo.getId()));
						break;
					}
				}
			}
			
			isSuc = true;
		} catch (Exception e) {
			logger.error("更新任务失败", e);
			throw e;
		}
		return isSuc;
    }

    private boolean updateAuditTask(AuditInfoDto auditInfo, UserBpmTask task)  throws Exception
    {
        boolean result = true;
        //优先处理购物车流程
        List<UserBpmTask> list = task.getSubTasks();
        if(list.size() > 0){
            Collections.sort(list, new Comparator<UserBpmTask>() {
                public int compare(UserBpmTask arg0, UserBpmTask arg1) {
                    return !"3".equals(arg0.getBusiType()) && "3".equals(arg1.getBusiType()) ? 1 : 0;
                }
            });
            //主流程相关子流程，递归提交(需要先提交子流程，否则订单处理可能出错)
            for(UserBpmTask subtask : list){
                result = result && updateAuditTask(auditInfo, subtask);
            }
        }
        return result && updateAuditTask(
                auditInfo.getComment(), task.getBpmInstID(),
                auditInfo.getStatus(), auditInfo.getBusiType(),
                auditInfo.getId(), auditInfo.getAppliedUser());
    }

	private boolean updateAuditTask(String comment, String instId, String status, String busiType, String id, String appliedUser) throws Exception{
		boolean isSuc = false;
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		UserBpmTask userBpmTask = userBpmTaskService.queryTaskByInstId(instId);
		if (String.valueOf(AuditTaskStatus.APPROEED.getIndex()).equals(status)) {
			//批准
			if (String.valueOf(AuditTaskType.ORDERCANCEL.getName()).equals(busiType)) {
				if(appliedUser != null && userId != null && userId.equals(appliedUser)) {
					logger.error("Can't approve the change request submitted by someone self!");
					throw new MarketingException("不能审批自己的修改请求", ErrorCode.NotProperApprover);
				}
				orderhistService.updateOrderCancel(instId, id,comment, userId);
			} else if (String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(busiType)) {
				//非ems流程，直接调用tc接口
				if(!String.valueOf(UserBpmTaskType.ORDER_EMS_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
					orderhistService.updateOrderFromChangeRequest(instId,AuditTaskType.ORDERCHANGE,
							UserBpmTaskType.fromNumber(Integer.valueOf(userBpmTask.getBusiType())), id, userId, comment);
				} else {

					String changeObjId = userBpmTask.getChangeObjID();
					OrderChange oChange = oderChangeService.getOrderChange(Long.valueOf(changeObjId));
					if(!"Y".equals(oChange.getIsreqems())) {
						//ems流程,但是改为非ems, 直接调用tc接口
						orderhistService.updateOrderFromChangeRequest(instId,AuditTaskType.ORDERCHANGE,
								UserBpmTaskType.fromNumber(Integer.valueOf(userBpmTask.getBusiType())), id, userId, comment);
					} else {
						//ems流程,改为ems, 绕过tc接口
						userBpmTaskService.approveChangeRequest(appliedUser, userId, instId,comment);
					}
				}
				
			} else if (String.valueOf(AuditTaskType.CONTACTCHANGE.getName()).equals(busiType)) {
				if(String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
					PhoneChange pc = phoneChangeService.queryByBpmInstID(instId);
					phoneService.finishAddRequest(pc.getPhoneid(), comment, appliedUser, userId, instId);
				} else if(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
					contactService.finishUpdateRequest(id, comment, appliedUser, userId, instId);
				} else if(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
					AddressChange ac = addressChangeService.queryByBpmInstID(instId);
					Address address = null;
					try {
						address = addressService.get(ac.getAddressid());
					} catch (Exception e) {
						logger.info("Fail to fetch address"+ac.getAddressid());
					}
					if(address != null) {
						addressService.finishUpdateRequest(ac.getAddressid(), comment, appliedUser, userId, instId);
					} else {
						addressService.finishAddRequest(ac.getAddressid(), comment, appliedUser, userId, instId);
					}
				}
			} else if (String.valueOf(AuditTaskType.CONTACTADD.getName()).equals(busiType)) {
				//批准通过，更新联系人相关标识
				contactService.finishAddRequest(id, comment, appliedUser, userId, instId);
				contactService.finishAddRequestState(id, CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
				//如果联系人有关联订单，通知订单联系人批准通过更新
				orderhistService.checkOrderByContactAudit(id, userId);
			} else if (String.valueOf(AuditTaskType.ORDERADD.getName()).equals(busiType)) {
				orderhistService.updateOrderFromChangeRequest(instId,AuditTaskType.ORDERADD,
						UserBpmTaskType.fromNumber(Integer.valueOf(userBpmTask.getBusiType())),id, userId, comment);
			}
		} else {
			//驳回
			boolean isAdded =false;
			if (String.valueOf(AuditTaskType.CONTACTCHANGE.getName()).equals(busiType)) {
				if(String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
					PhoneChange pc = phoneChangeService.queryByBpmInstID(instId);
					try {
						Phone phone = null;
						try {
							phone = phoneService.get(pc.getPhoneid());
						} catch(Exception e) {
							logger.info("Fail to fetch phone"+pc.getPhoneid());
						}
						phoneService.deletePhone(phone);
					} catch(Exception e) {
						logger.info("Fail to delete phone"+pc.getPhoneid());
					}
				}
			} else if (String.valueOf(AuditTaskType.CONTACTADD.getName()).equals(busiType)) {
				contactService.finishAddRequestState(id, CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
				isAdded = true;
			} else if (String.valueOf(AuditTaskType.ORDERADD.getName()).equals(busiType)) {
				orderhistService.rejectOrderModify(id, null, userId, AuditTaskType.ORDERADD, 
						UserBpmTaskType.fromNumber(Integer.valueOf(userBpmTask.getBusiType())));
				isAdded = true;
			} else if (String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(busiType)) {
				orderhistService.rejectOrderModify(id, Long.valueOf(userBpmTask.getChangeObjID()), userId, AuditTaskType.ORDERCHANGE, 
						UserBpmTaskType.fromNumber(Integer.valueOf(userBpmTask.getBusiType())));
			}
			userBpmTaskService.rejectChangeRequest(appliedUser, userId,instId, comment, isAdded);
		}
		isSuc = true;
		return isSuc;
	}
}
