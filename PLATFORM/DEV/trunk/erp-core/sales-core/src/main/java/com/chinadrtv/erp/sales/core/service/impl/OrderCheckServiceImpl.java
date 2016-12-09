package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.OrderCheck;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.dao.OrderCheckDao;
import com.chinadrtv.erp.sales.core.service.OrderCheckService;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderCheckServiceImpl implements OrderCheckService {

    @Autowired
    private OrderCheckDao orderCheckDao;

    private HashMap<String,OrderRuleCheckService> ruleCheckServiceHashMap;

    @Autowired
    private void initRuleService(List<OrderRuleCheckService> orderRuleCheckServiceList)
    {
        ruleCheckServiceHashMap=new HashMap<String, OrderRuleCheckService>();
        for(OrderRuleCheckService orderRuleCheckService:orderRuleCheckServiceList)
        {
            ruleCheckServiceHashMap.put(orderRuleCheckService.getRuleName(), orderRuleCheckService);
        }
    }

    @Override
    public boolean checkOrder(Order order, UserBpmTaskType userBpmTaskType,String usrId) {
        List<OrderCheck> orderCheckList=orderCheckDao.getOrderChecks(order.getOrderid());
        if(orderCheckList!=null&&orderCheckList.size()>0)
        {
            Boolean bPass=true;
            String ruleName=null;
            if(userBpmTaskType!=null)
            {
                ruleName = this.getCheckRuleName(String.valueOf(userBpmTaskType.getIndex()));
            }
            //检查每个规则，即使主管驳回了，也需要检查规则，因为订单可能后续修改过了
            for(OrderCheck orderCheck:orderCheckList)
            {
                //如果主管审批通过了，那么不需要校验规则
                if(orderCheck.getCheckRule().equals(ruleName))
                {
                    orderCheck.setManagerCheck(true);
                    orderCheck.setManagerId(usrId);
                    orderCheckDao.saveOrUpdate(orderCheck);
                }
                if(!bPass.equals(orderCheck.getManagerCheck()))
                {
                    if(ruleCheckServiceHashMap.containsKey(orderCheck.getCheckRule()))
                    {
                        OrderRuleCheckService orderRuleCheckService= ruleCheckServiceHashMap.get(orderCheck.getCheckRule());
                        if(orderRuleCheckService.checkOrder(order))
                        {
                            orderCheck.setSysCheck(true);
                            orderCheckDao.saveOrUpdate(orderCheck);
                        }
                    }
                }
            }

            //根据规则检查
            return checkRuleResult(orderCheckList);
        }
        else
        {
            return true;
        }
    }

    private boolean checkRuleResult(List<OrderCheck> orderCheckList)
    {
        Boolean bPass=true;
        for(OrderCheck orderCheck:orderCheckList)
        {
            if(bPass.equals(orderCheck.getManagerCheck())||bPass.equals(orderCheck.getSysCheck()))
            {
            }else
            {
                return false;
            }
        }
        return true;
    }

    private final static Boolean bPass=true;

    public List<OrderRuleCheckService> attachOrderRules(Order order){
        List<OrderRuleCheckService> orderRuleCheckServiceList=new ArrayList<OrderRuleCheckService>();
        List<OrderCheck> orderCheckList=new ArrayList<OrderCheck>();
        //对于每个规则，检查是否通过，如果不通过，那么就添加
        for(Map.Entry<String,OrderRuleCheckService> entry: ruleCheckServiceHashMap.entrySet())
        {
            OrderRuleCheckService orderRuleCheckService=entry.getValue();
            if(!bPass.equals(orderRuleCheckService.checkOrder(order)))
            {
                orderRuleCheckServiceList.add(orderRuleCheckService);
                OrderCheck orderCheck=new OrderCheck();
                orderCheck.setSysCheck(false);
                orderCheck.setCreateDate(new Date());
                orderCheck.setCheckRule(orderRuleCheckService.getRuleName());
                orderCheck.setOrderId(order.getOrderid());
                //orderCheck.setAgentId(order.getCrusr());
                orderCheck.setCreateUser(order.getCrusr());

                orderCheckList.add(orderCheck);
            }
        }

        if(orderCheckList.size()>0)
        {
            orderCheckDao.saveOrderChecks(orderCheckList);
        }
        return orderRuleCheckServiceList;
    }

    public String getCheckRuleName(String bpmType)
    {
        if(StringUtils.isEmpty(bpmType))
        {
            return null;
        }
        if(ruleCheckServiceHashMap!=null)
        {
            for(Map.Entry<String,OrderRuleCheckService> entry:ruleCheckServiceHashMap.entrySet())
            {
                if(bpmType.equals(entry.getValue().getBPMType()))
                {
                    return entry.getValue().getRuleName();
                }
            }
        }

        return null;
    }
}
