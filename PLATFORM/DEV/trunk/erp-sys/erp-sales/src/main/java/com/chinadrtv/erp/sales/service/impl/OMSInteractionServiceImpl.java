/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.sales.core.service.OrderEmsService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.ShipmentHeaderService;
import com.chinadrtv.erp.sales.dto.OrderInfo4AssistantReview;
import com.chinadrtv.erp.sales.service.OMSInteractionService;
import com.chinadrtv.erp.tc.core.dao.WarehouseDao;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 2013-6-25 上午9:31:23
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("omsInteractionService")
public class OMSInteractionServiceImpl implements OMSInteractionService{
	private static final Logger logger = LoggerFactory.getLogger(OMSInteractionServiceImpl.class);
	@Autowired
	private OrderhistService orderhistService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ShipmentHeaderService shipmentHeaderService;
	
	@Autowired
	private WarehouseDao WarehouseDao;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserBpmTaskService userBpmTaskService;
	
	@Autowired
	private ChangeRequestService changeRequestService;
	
	@Autowired
	private OrderEmsService orderEmsService;

	@Override
	public List<OrderInfo4AssistantReview> viewDistributionChangeQuery(
			String startDate, String endDate, boolean isEms, int start, int end) {
		List<OrderInfo4AssistantReview> orderInfo4AssistantReviews = new ArrayList<OrderInfo4AssistantReview>();
		List<UserBpmTask> userBpmTasks = userBpmTaskService.queryManagerApprovedEmsChangeProcess(startDate, endDate, isEms, start, end);
		for(UserBpmTask userBpmTask : userBpmTasks) {
			try {
				String batchId = userBpmTask.getBatchID();
				UserBpm userBpm = changeRequestService.queryApprovingTaskById(batchId);
				String orderId = userBpm.getOrderID();
				Order order = orderhistService.getOrderHistByOrderid(orderId);
				//获取地址信息
				AddressExt address = order.getAddress();
				Address addr = addressService.get(address.getAddressId());
				//获取送货公司
				String companyId = order.getCompanyid();
				Company company = companyService.findCompany(companyId);
				
				//获取仓库名
				ShipmentHeader shipmentHeader = shipmentHeaderService.getShipmentHeaderFromOrderId(orderId);
				Warehouse wareHouse = null;
				if(shipmentHeader != null) {
					wareHouse = WarehouseDao.get(Long.valueOf(shipmentHeader.getWarehouseId()));
				}
				
				//初始化OrderInfo4AssistantReview
				OrderInfo4AssistantReview orderInfo4AssistantReview = new OrderInfo4AssistantReview();
				orderInfo4AssistantReview.setInstId(userBpmTask.getBpmInstID());
				orderInfo4AssistantReview.setAppliedUser(userBpm.getCreateUser());
				orderInfo4AssistantReview.setOrderId(orderId);
				orderInfo4AssistantReview.setOrderCrDate(DateUtil.dateToString(order.getCrdt()));
				if(wareHouse != null) {
					orderInfo4AssistantReview.setDefaultWarehouse(wareHouse.getWarehouseName());
					orderInfo4AssistantReview.setDesignedWarehouse(wareHouse.getWarehouseName());
					orderInfo4AssistantReview.setDesignedWarehouseId(String.valueOf(wareHouse.getWarehouseId()));
				}
				if(address != null) {
					orderInfo4AssistantReview.setProvince(address.getProvince().getChinese());
					orderInfo4AssistantReview.setCity(address.getCity().getCityname());
					orderInfo4AssistantReview.setCountry(address.getCounty().getCountyname());
					orderInfo4AssistantReview.setAddressDsc(address.getAddressDesc());
				}
				if(addr != null) {
					if(StringUtils.isNotBlank(addr.getAddress())) {
						orderInfo4AssistantReview.setAllAddress(addr.getAddress());
					} else {
						StringBuilder addSB = new StringBuilder();
						addSB.append(orderInfo4AssistantReview.getProvince()).append(orderInfo4AssistantReview.getCity())
							 .append(orderInfo4AssistantReview.getCountry()).append(orderInfo4AssistantReview.getAddressDsc());
						orderInfo4AssistantReview.setAllAddress(addSB.toString());
					}
				}
				if(company != null) {
					orderInfo4AssistantReview.setDefaultEntityName(company.getName());
					orderInfo4AssistantReview.setDesignedEntityName(company.getName());
				}
				if(address != null) {
					String designedEntityId = orderEmsService.getEmsCompanyId(address);
					if(StringUtils.isNotBlank(designedEntityId)) {
						orderInfo4AssistantReview.setDesignedEntityId(designedEntityId);
						Company designedCompany = companyService.findCompany(designedEntityId);
						if(designedCompany != null) {
							orderInfo4AssistantReview.setDesignedEntityName(designedCompany.getName());
						}
					}
				}
				
				orderInfo4AssistantReview.setComment(userBpmTask.getRemark());
				orderInfo4AssistantReviews.add(orderInfo4AssistantReview);
			} catch(Exception e) {
				logger.error("查询流程出错:"+userBpmTask.getBpmInstID(),e);
			}
		}
		return orderInfo4AssistantReviews;
	}

	/**
	 * actionType > 0,同意;actionType <= 0,拒绝
	 */
	@Override
	public boolean action(int actionType, String instId, String orderId, String company) throws Exception {
		boolean result = false;
		try {
			UserBpmTask userBpmTask = userBpmTaskService.queryTaskByInstId(instId);
			//call tc service
			if(actionType > 0) {
				orderhistService.auditOrderEms(instId, company, AuditTaskType.ORDERCHANGE, orderId, "", company);
			} else {
				//call process service
				String batchId = userBpmTask.getBatchID();
				UserBpm userBpm = changeRequestService.queryApprovingTaskById(batchId);
				userBpmTaskService.updateStatus(userBpm.getCreateUser(), instId, "", String.valueOf(AuditTaskStatus.ASSITANTPROCESSED.getIndex()), true);
			}
	
			result = true;
		} catch (ErpException e) {
			logger.error("",e);
			throw e;
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
		return result;
	}
    
}
