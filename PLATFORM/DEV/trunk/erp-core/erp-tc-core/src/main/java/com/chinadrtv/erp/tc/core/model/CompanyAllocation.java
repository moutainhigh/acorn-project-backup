package com.chinadrtv.erp.tc.core.model;

import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.agent.Order;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CompanyAllocation {
    private List<Integer> companyIdList;
    private Integer matchCompanyId;
    private String errorCode;
    private String errorDesc;

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    private boolean isAssigned;

    private String swappedOrderId;

    public Order getOrderhist() {
        return orderhist;
    }

    public List<OrderAssignRule> getOrderAssignRuleList() {
        return orderAssignRuleList;
    }

    private Order orderhist;
    private List<OrderAssignRule> orderAssignRuleList;

    public CompanyAllocation(Order order,List<OrderAssignRule> orderAssignRuleList)
    {
        this.isAssigned=false;
        this.companyIdList=new ArrayList<Integer>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule: orderAssignRuleList)
            {
                if(orderAssignRule.getEntityId()!=null)
                {
                    Integer companyId=orderAssignRule.getEntityId().intValue();
                    if(!this.companyIdList.contains(companyId))
                    {
                        this.companyIdList.add(companyId);
                    }
                }
            }
        }
        this.orderhist=order;
        this.orderAssignRuleList=orderAssignRuleList;
    }

    public String getOrderId() {
        if(this.orderhist!=null)
            return this.orderhist.getOrderid();
        return null;
    }

    public Integer getCompanyId() {
        if(this.orderhist!=null)
            return Integer.parseInt(this.orderhist.getEntityid());
        return null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }


    public String getSwappedOrderId() {
        return swappedOrderId;
    }

    public void setSwappedOrderId(String swappedOrderId) {
        this.swappedOrderId = swappedOrderId;
    }


    public List<Integer> getCompanyIdList() {
        return companyIdList;
    }

    public Integer getMatchCompanyId() {
        return matchCompanyId;
    }

    public void setMatchCompanyId(Integer matchCompanyId) {
        this.matchCompanyId = matchCompanyId;
    }

    public CompanyAllocation clone()
    {
        return new CompanyAllocation(this.orderhist,this.orderAssignRuleList);
    }

    public boolean isSucc()
    {
        if(StringUtils.isEmpty(this.errorCode))
        {
            return true;
        }
        return false;
    }
}
