package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.oms.constant.OrderRefuseMessage;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDao;
import com.chinadrtv.erp.oms.model.OrderRefuseInfo;
import com.chinadrtv.erp.oms.model.OrderRefuseResult;
import com.chinadrtv.erp.oms.model.RefuseCode;
import com.chinadrtv.erp.oms.service.OrderRefuseCheckService;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.dao.CompanyContractDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderRefuseCheckServiceImpl implements OrderRefuseCheckService {

    @Autowired
    private ShipmentRefundDao shipmentRefundDao;

    @Autowired
    private OrderhistDao orderhistDao;

    @Autowired
    private CompanyContractDao companyContractDao;

    @Override
    public OrderReturnCode checkOrderRefuse(OrderRefuseInfo orderRefuseInfo) {
        OrderReturnCode orderReturnCode=new OrderReturnCode();
        orderReturnCode.setCode("000");
        orderReturnCode.setDesc("");

        if(orderRefuseInfo==null)
        {
            orderReturnCode.setCode(String.valueOf(RefuseCode.UNFORMAT.getCode()));
            orderReturnCode.setDesc(OrderRefuseMessage.PARAM_NO);
            return orderReturnCode;
        }
        if(StringUtils.isBlank(orderRefuseInfo.getOrderId()))
        {
            orderReturnCode.setCode(String.valueOf(RefuseCode.UNFORMAT.getCode()));
            orderReturnCode.setDesc(OrderRefuseMessage.PARAM_NO_ORDERID);
            return orderReturnCode;
        }
        if(StringUtils.isBlank(orderRefuseInfo.getMailId()))
        {
            orderReturnCode.setCode(String.valueOf(RefuseCode.UNFORMAT.getCode()));
            orderReturnCode.setDesc(OrderRefuseMessage.PARMA_NO_MAILID);
            return orderReturnCode;
        }

        List<ShipmentRefund> shipmentRefundList = shipmentRefundDao.getShipmentRefundFromOrderId(orderRefuseInfo.getOrderId());
        if(shipmentRefundList!=null&&shipmentRefundList.size()>0)
        {
            orderReturnCode.setCode(String.valueOf(RefuseCode.REFUND.getCode()));
            orderReturnCode.setDesc(RefuseCode.REFUND.getDsc());
            return orderReturnCode;
        }

        Order order=orderhistDao.getOrderHistByOrderid(orderRefuseInfo.getOrderId());
        if(order==null)
        {
            orderReturnCode.setCode(String.valueOf(RefuseCode.NOEXIST.getCode()));
            orderReturnCode.setDesc(RefuseCode.NOEXIST.getDsc());
            return orderReturnCode;
        }

        if(AccountStatusConstants.ACCOUNT_FINI.equals(order.getStatus()))
        {
            //排除一些送货公司
            if(!ignoreCompany(order.getEntityid()))
            {
                orderReturnCode.setCode(String.valueOf(RefuseCode.FINI.getCode()));
                orderReturnCode.setDesc(RefuseCode.FINI.getDsc());
                return orderReturnCode;
            }
        }

        return orderReturnCode;
    }

    private boolean ignoreCompany(String entityId)
    {
        if(StringUtils.isNotBlank(entityId))
        {
            CompanyContract companyContract=companyContractDao.find("from CompanyContract where id=:id and isManual=true",new ParameterInteger("id",Integer.parseInt(entityId)));
            if(companyContract!=null)
            {
                return true;
            }
        }
        return false;
    }
}
