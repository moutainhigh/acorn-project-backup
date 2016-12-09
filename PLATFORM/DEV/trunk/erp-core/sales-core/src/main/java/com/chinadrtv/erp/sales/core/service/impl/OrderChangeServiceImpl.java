package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.sales.core.dao.OrderChangeDao;
import com.chinadrtv.erp.sales.core.service.OrderChangeService;
import com.chinadrtv.erp.sales.core.util.FieldValueCompareUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderChangeServiceImpl extends GenericServiceImpl<OrderChange, Long> implements OrderChangeService {

    public OrderChangeServiceImpl()
    {

    }

    @Autowired
    private OrderChangeDao orderChangeDao;

    @Override
    protected GenericDao<OrderChange, Long> getGenericDao() {
        return orderChangeDao;
    }

    public void addOrderChange(OrderChange orderChange)
    {
        orderChangeDao.save(orderChange);
    }

    public void deleteOrderChange(OrderChange orderChange)
    {
        orderChangeDao.remove(orderChange.getId());
    }

    public OrderChange getOrderChangeFromProcInstId(String prodInstId)
    {
        return orderChangeDao.find("from OrderChange where procInstId=:procInstId",new ParameterString("procInstId",prodInstId));
    }

    public OrderChange getOrderChange(Long orderChangeId)
    {
        return orderChangeDao.get(orderChangeId);
    }

    public OrderChange mergeBatchOrderChanges(List<Long> orderChangeIdList)
    {
        Map<String,Parameter> mapParms=new Hashtable<String, Parameter>();
        int index=0;

        StringBuilder strBld=new StringBuilder("from OrderChange where ");
        for(Long orderChangeId:orderChangeIdList)
        {
            String parmName="Id"+index;
            if(index==0)
            {
                strBld.append("orderChangeId=:"+parmName);
            }
            else
            {
                strBld.append(" or orderChangeId=:"+parmName);
            }
            mapParms.put(parmName,new ParameterLong(parmName,orderChangeId));
            index++;
        }

        List<OrderChange> orderChangeList=orderChangeDao.findList(strBld.toString(),mapParms);

        if(orderChangeList==null||orderChangeList.size()<=0)
            return null;

        //首先找到有明细的
        OrderChange firstOrderChange=null;
        for(OrderChange orderChange:orderChangeList)
        {
            if(orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0)
            {
                firstOrderChange=new OrderChange();
                BeanUtils.copyProperties(orderChange,firstOrderChange);
                break;
            }
        }
        if(firstOrderChange==null)
        {
            for(OrderChange orderChange:orderChangeList)
            {
                firstOrderChange=new OrderChange();
                BeanUtils.copyProperties(orderChange,firstOrderChange);
                break;
            }
        }
        for(OrderChange orderChange:orderChangeList)
        {
            if(orderChange.getOrderChangeId().equals(firstOrderChange.getOrderChangeId()))
                continue;
            mergeOrderChange(firstOrderChange,orderChange);
        }
        return firstOrderChange;
    }

    @Override
    public OrderChange saveOrderChange(OrderChange orderChange) {
        //
        AddressExtChange addressExtChange=orderChange.getAddress();
        if(addressExtChange!=null)
        {
            addressExtChange.setOrderChange(orderChange);
            AddressChange addressChange=addressExtChange.getAddressChange();
            if(addressChange!=null)
                addressChange.setAddressExtChange(addressExtChange);
        }
        ContactChange contactChange=orderChange.getContactChange();
        if(contactChange!=null)
            contactChange.setOrderChange(orderChange);
        ContactChange getContactChange =orderChange.getGetContactChange();
        if(getContactChange!=null)
            getContactChange.setOrderChange(orderChange);
        ContactChange payContactChange=orderChange.getPayContactChange();
        if(payContactChange!=null)
            payContactChange.setOrderChange(orderChange);
        if(orderChange.getCardChanges()!=null&&orderChange.getCardChanges().size()>0)
        {
            for(CardChange cardChange:orderChange.getCardChanges())
            {
                cardChange.setOrderChange(orderChange);
            }
        }
        if(orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0)
        {
            for(OrderdetChange orderdetChange:orderChange.getOrderdetChanges())
            {
                orderdetChange.setOrderChange(orderChange);
            }
        }
        return orderChangeDao.save(orderChange);
    }

    private OrderChange mergeOrderChange(OrderChange orderChange1,OrderChange orderChange2)
    {
        FieldValueCompareUtil.copyNullFld(orderChange2,orderChange1);
        if(orderChange2.getCardChanges()!=null&&orderChange2.getCardChanges().size()>0)
        {
            for(CardChange cardChange:orderChange2.getCardChanges())
            {
                orderChange1.getCardChanges().add(cardChange);
            }
        }
        return orderChange1;
    }

}
