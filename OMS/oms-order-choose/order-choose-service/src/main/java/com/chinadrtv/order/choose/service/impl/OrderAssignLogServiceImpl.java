package com.chinadrtv.order.choose.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.OrderAssignLog;
import com.chinadrtv.order.choose.dal.dao.OrderAssignLogDao;
import com.chinadrtv.order.choose.service.OrderAssignLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderAssignLogServiceImpl extends GenericServiceImpl<OrderAssignLog,Long> implements OrderAssignLogService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderAssignLogServiceImpl.class);
    @Autowired
    private OrderAssignLogDao orderAssignLogDao;

    @Override
    protected GenericDao<OrderAssignLog,Long> getGenericDao(){
        return orderAssignLogDao;
    }

    public void saveOrUpateOrderAssignLog(OrderAssignLog orderAssignLog)
    {
        //如果错误信息太长，那么截断
        if(StringUtils.isNotEmpty(orderAssignLog.getCodeDsc()))
        {
            if(orderAssignLog.getCodeDsc().length()>255)
            {
                logger.error("orderid:"+orderAssignLog.getOrderId()+" - error:"+orderAssignLog.getCodeDsc());
                String strError=orderAssignLog.getCodeDsc().substring(0,255);
                orderAssignLog.setCodeDsc(strError);
            }
        }
        //如果找到，就更新，找不到，就插入
        List<OrderAssignLog> orderAssignLogList=orderAssignLogDao.findList("from OrderAssignLog where orderId=:orderId", new ParameterString("orderId",orderAssignLog.getOrderId()));
        OrderAssignLog matchOrderAssignLog=null;
        if(orderAssignLogList!=null)
        {
            for(OrderAssignLog orderAssignLog1:orderAssignLogList)
            {
                if(orderAssignLog.getAssignType().equals(orderAssignLog1.getAssignType()))
                {
                    //检查其他值是否相等
                    if(isSameLog(orderAssignLog,orderAssignLog1))
                    {
                        matchOrderAssignLog=orderAssignLog1;
                        break;
                    }
                }
            }
        }
        if(matchOrderAssignLog!=null)
        {
            matchOrderAssignLog.setCodeDsc(orderAssignLog.getCodeDsc());
            matchOrderAssignLog.setCrUser(orderAssignLog.getCrUser());
            matchOrderAssignLog.setCodeId(orderAssignLog.getCodeId());
            matchOrderAssignLog.setMdDate(orderAssignLog.getMdDate());
            matchOrderAssignLog.setRegulationDsc(orderAssignLog.getRegulationDsc());
            matchOrderAssignLog.setRegulationId(orderAssignLog.getRegulationId());

            orderAssignLogDao.saveOrUpdate(matchOrderAssignLog);
        }
        else
        {
            orderAssignLogDao.save(orderAssignLog);
        }
    }

    private boolean isSameLog(OrderAssignLog orderAssignLog1, OrderAssignLog orderAssignLog2)
    {
        if(!isSameString(orderAssignLog1.getRegulationId(),orderAssignLog2.getRegulationId()))
        {
            return false;
        }

        if(!isSameString(orderAssignLog1.getRegulationDsc(), orderAssignLog2.getRegulationDsc()))
        {
            return false;
        }

        if(!isSameString(orderAssignLog1.getCodeId(), orderAssignLog2.getCodeId()))
        {
            return false;
        }

        if(!isSameString(orderAssignLog1.getCodeDsc(), orderAssignLog2.getCodeDsc()))
        {
            return false;
        }

        return true;
    }

    private boolean isSameString(String left,String right)
    {
        if(StringUtils.isEmpty(left))
        {
            if(StringUtils.isNotEmpty(right))
            {
                return false;
            }
        }
        else
        {
            return left.equals(right);
        }
        return true;
    }
}
