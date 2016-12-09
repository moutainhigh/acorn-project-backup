package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CardChange;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.sales.core.dao.OrderChangeDao;
import com.chinadrtv.erp.sales.core.util.FieldValueCompareUtil;
import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//@Transactional
public class OrderChangeTestServiceImpl extends GenericServiceImpl<OrderChange, Long> implements OrderChangeTestService {

    public OrderChangeTestServiceImpl()
    {

    }

    @Autowired
    private OrderChangeDao orderChangeDao;

    @Override
    protected GenericDao<OrderChange, Long> getGenericDao() {
        return orderChangeDao;
    }

    private void updatePostFee(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        BigDecimal postFee = orderChange.getPostFee();
        if (postFee == null){
            postFee = new BigDecimal(10);
        } else {
            postFee = postFee.add(new BigDecimal(7));
        }
        orderChange.setPostFee(postFee);
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

    @Override
    public OrderChange getOrderChange(Long orderChangeId) {
        return orderChangeDao.get(orderChangeId);
    }

    public OrderChange requiredOrderChange(Long orderChangeId)
    {
        return orderChangeDao.get(orderChangeId);
    }

    @Override
    public void getOrderChangeEmbed(Long orderChangeId) {

       for (int i = 0;i<5;i++){
         getOrderChange(orderChangeId);
       }
    }

    @Override
    public void requiredOrderChangeEmbed(Long orderChangeId) {
        for (int i = 0;i<5;i++){
            requiredOrderChange(orderChangeId);
        }
    }

    @Override
    public void getOrderChangeException(Long orderChangeId) {
        getOrderChange(orderChangeId);
        throw new RuntimeException("error");
    }

    @Override
    public void requiredOrderChangeException(Long orderChangeId) {
        requiredOrderChange(orderChangeId);
        throw new RuntimeException("error");
    }

    @Override
    public void getForUpdate(Long orderChangeId) {
       OrderChange orderChange = getOrderChange(orderChangeId);
       orderChange.setCreateDate(new Date());
      // throw new RuntimeException("error");
    }

    public void requiredForUpdate(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        orderChange.setCreateDate(new Date());
         throw new RuntimeException("error");
    }

    @Override
    public void update(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        orderChange.setCreateDate(new Date());
        System.out.println(orderChange.getCreateDate());
        //throw new RuntimeException("error");
    }


    @Override
    public void updateException(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        orderChange.setCreateDate(new Date());
        throw new RuntimeException("error");
    }

    @Override
    public void requiredUpdatePostFee(Long orderChangeId) {
        updatePostFee(orderChangeId);
    }



    @Override
    public void requiredUpdatePostFeeException(Long orderChangeId) {
        updatePostFee(orderChangeId);
        throw new RuntimeException("error");
    }

    @Override
    public void supportsUpdatePostFee(Long orderChangeId) {
        updatePostFee(orderChangeId);
    }

    @Override
    public void supportsUpdatePostFeeException(Long orderChangeId) {
        updatePostFee(orderChangeId);
        throw new RuntimeException("error");
    }

    @Override
    public void requiresNewPostFee(Long orderChangeId) {
        updatePostFee(orderChangeId);
       // throw new RuntimeException("error");
    }

    //@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
    public void mandatoryUpdatePostFee(Long orderChangeId) {
        updatePostFee(orderChangeId);
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
    public void nestedInvoiceTitle(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        orderChange.setInvoicetitle(sdf.format(new Date()));
    }

    @Override
    public void nestedInvoiceTitleException(Long orderChangeId) {
        OrderChange orderChange = getOrderChange(orderChangeId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        orderChange.setInvoicetitle(sdf.format(new Date()));
        throw new RuntimeException("error");     }

    @Override
    public void nestedPostFee(Long orderChangeId) {
        updatePostFee(orderChangeId);
    }

    @Override
    public void updateNew() {
        OrderChange orderChange = new OrderChange();
        orderChange.setOrderid("936259317");
        orderChange.setLaststatus("abc");
        orderChange.setOrderChangeId(99999999l);
        orderChangeDao.save(orderChange);
    }

    @Override
    public void updateRemove() {
        orderChangeDao.remove(99999999l);
    }

    @Override
    public List<OrderChange> getOrderChangeList() {
        List<OrderChange> list =  orderChangeDao.getOrderChangeList();
        System.out.println(list.size());

        list =  orderChangeDao.getOrderChangeList();
        System.out.println(list.size());
        return list;
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
