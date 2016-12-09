package com.chinadrtv.erp.tc.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.tc.core.constant.cache.OrderAssignErrorCode;
import com.chinadrtv.erp.tc.core.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.tc.core.service.OrderAssignRuleService;
import com.chinadrtv.erp.tc.core.utils.OrderAssignException;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderAssignRuleServiceImpl implements OrderAssignRuleService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderAssignRuleServiceImpl.class);

    @Autowired
    private OrderAssignRuleDao orderAssignRuleDao;

    public List<OrderAssignRule> findMatchRuleFromAmount(BigDecimal amount)
    {
        if(amount==null)
            return null;
        List<OrderAssignRule> orderAssignRuleList= orderAssignRuleDao.getAllValidRules();
        List<OrderAssignRule> matchOrderAssignRuleList =new ArrayList<OrderAssignRule>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:orderAssignRuleList)
            {
                if(orderAssignRule.getMaxAmount()!=null||orderAssignRule.getMinAmount()!=null)
                {
                    BigDecimal maxAmount=orderAssignRule.getMaxAmount()!=null ? orderAssignRule.getMaxAmount() : new BigDecimal("999999999");
                    BigDecimal minAmount=orderAssignRule.getMinAmount()!=null ? orderAssignRule.getMinAmount() : new BigDecimal("0");

                    if(amount.compareTo(maxAmount)<=0 && amount.compareTo(minAmount)>=0)
                    {
                        matchOrderAssignRuleList.add(orderAssignRule);
                    }
                }
            }
        }
        return matchOrderAssignRuleList;
    }

    public List<OrderAssignRule> findMatchRuleFromProds(List<String> prods)
    {
        if(prods==null)
            return null;
        List<OrderAssignRule> orderAssignRuleList= orderAssignRuleDao.getAllValidRules();
        List<OrderAssignRule> matchOrderAssignRuleList =new ArrayList<OrderAssignRule>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:orderAssignRuleList)
            {
                if(orderAssignRule.getProdCode()!=null)
                {
                    if(prods.contains(orderAssignRule.getProdCode()))
                    {
                        matchOrderAssignRuleList.add(orderAssignRule);
                    }
                }
            }
        }
        return matchOrderAssignRuleList;
    }

    public List<OrderAssignRule> findMatchRuleFromCondition(List<Long> orderChannelIdList, List<Long> areaGroupIdList)
    {
        if(orderChannelIdList==null||orderChannelIdList.size()==0)
        {
            throw new OrderAssignException(OrderAssignErrorCode.CHANNEL_NOT_FOUND,OrderAssignErrorCode.CHANNEL_NOT_FOUND_DSC);
        }
        if(areaGroupIdList==null||areaGroupIdList.size()==0)
        {
            throw new OrderAssignException(OrderAssignErrorCode.ADDRESS_NOT_FOUND,OrderAssignErrorCode.CHANNEL_NOT_FOUND_DSC);
        }

        List<OrderAssignRule> orderAssignRuleList= orderAssignRuleDao.getAllValidRules();
        List<OrderAssignRule> matchOrderAssignRuleList =new ArrayList<OrderAssignRule>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:orderAssignRuleList)
            {
                if( orderAssignRule.getMaxAmount()==null&& orderAssignRule.getMinAmount()==null&& orderAssignRule.getProdCode()==null)
                {
                    if(orderAssignRule.getOrderChannel()!=null&&orderAssignRule.getOrderChannel().getId()!=null
                            &&orderAssignRule.getAreaGroup()!=null&&orderAssignRule.getAreaGroup().getId()!=null)
                    {
                        if(orderChannelIdList.contains(orderAssignRule.getOrderChannel().getId())
                                &&areaGroupIdList.contains(orderAssignRule.getAreaGroup().getId()))
                        {
                            matchOrderAssignRuleList.add(orderAssignRule);
                        }
                    }
                }
            }
        }
        else
        {
            throw new OrderAssignException(OrderAssignErrorCode.RULE_NOT_FOUND, OrderAssignErrorCode.CHANNEL_NOT_FOUND_DSC);
        }
        return matchOrderAssignRuleList;
    }
}
