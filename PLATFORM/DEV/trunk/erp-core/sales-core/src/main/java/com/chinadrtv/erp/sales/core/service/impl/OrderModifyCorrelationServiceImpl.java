package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.model.OrderModifyCorrelation;
import com.chinadrtv.erp.sales.core.service.OrderModifyCorrelationService;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 订单关联修改服务
 * User: 徐志凯
 * Date: 13-9-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderModifyCorrelationServiceImpl implements OrderModifyCorrelationService {
    public OrderModifyCorrelationServiceImpl()
    {
        this.initMap();
    }

    /*private class TaskType
    {
        public TaskType(String name,boolean isSrc)
        {
            this.name=name;
            this.isSource=isSrc;
        }
        private String name;
        private boolean isSource;//是否发起者

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TaskType taskType = (TaskType) o;

            if (name != null ? !name.equals(taskType.name) : taskType.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "TaskType{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }*/

    private List<List<String>> auditCluesList;

    private void initMap()
    {
        //TODO:后期从配置文件中读取
        auditCluesList=new ArrayList<List<String>>();
        List<String> list1=new ArrayList<String>();
        list1.add(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()));
        list1.add(String.valueOf(UserBpmTaskType.ORDER_MAILPRICE_CHANGE.getIndex()));
        auditCluesList.add(list1);

        List<String> list2=new ArrayList<String>();
        list2.add(String.valueOf(UserBpmTaskType.ORDER_CART_CHANGE.getIndex()));
        list2.add(String.valueOf(UserBpmTaskType.ORDER_MAILPRICE_CHANGE.getIndex()));
        auditCluesList.add(list2);

        List<String> list3=new ArrayList<String>();
        list3.add(String.valueOf(UserBpmTaskType.ORDER_CARD_CHANGE.getIndex()));
        list3.add(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()));
        list3.add(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()));
        auditCluesList.add(list3);

        //增加收货地址与电话关联
        List<String> list4=new ArrayList<String>();
        list4.add(String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()));
        list4.add(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()));
        auditCluesList.add(list4);
    }

    private boolean isMatchClue(List<String> list,List<String> matchList)
    {
        for(String str:list)
        {
            if(!matchList.contains(str))
                return false;
        }

        return true;
    }
    public List<OrderModifyCorrelation> getCorrelationOrder(List<UserBpmTaskType> userBpmTaskTypeList, List<List<UserBpmTaskType>> dynamicList)
    {
        //目前的关联有 1.购物车 - 运费
        //2. 地址 - 运费
        //3. 信用卡 - 收货人
        //4. 收货人 - 地址
        //从配置文件中读取相关信息
        //首先从配置和订单修改中，获取需要关联的流程
        List<List<String>> orderModifyAuditList=new ArrayList<List<String>>();
        for(UserBpmTaskType userBpmTaskType:userBpmTaskTypeList)
        {
            String strType=String.valueOf(userBpmTaskType.getIndex());
            for(List<String> list:auditCluesList)
            {
                if(strType.equals(list.get(0)))
                {
                    orderModifyAuditList.add(list);
                }
            }
        }

        //合并子流程（如果一个关联流程包括了另外一个关联流程，那么去掉被关联的流程）
        List<List<String>> tempList=new ArrayList<List<String>>();
        if(dynamicList!=null&&dynamicList.size()>0)
        {
            for(List<UserBpmTaskType> list:dynamicList)
            {
                List<String> addList=new ArrayList<String>();
                for(UserBpmTaskType userBpmTaskType:list)
                {
                    addList.add(String.valueOf(userBpmTaskType.getIndex()));
                }

                tempList.add(addList);
            }
        }

        tempList.addAll(orderModifyAuditList);
        orderModifyAuditList.clear();
        for(List<String> list:tempList)
        {
            //如果没有包含，那么添加
            boolean bMatch=false;
            for(List<String> matchList:orderModifyAuditList)
            {
                if(isMatchClue(list,matchList))
                {
                    bMatch=true;
                    break;
                }
            }
            if(bMatch==false)
            {
                orderModifyAuditList.add(list);
            }
        }

        List<OrderModifyCorrelation> orderModifyCorrelationList=new ArrayList<OrderModifyCorrelation>();

        List<UserBpmTaskType> handledList=new ArrayList<UserBpmTaskType>();
        int count=userBpmTaskTypeList.size()*10;

        do{
            for(UserBpmTaskType userBpmTaskType : userBpmTaskTypeList)
            {
                boolean bFind=false;
                for(UserBpmTaskType userBpmTaskTypeHandle:handledList)
                {
                    if(userBpmTaskType.getIndex()==userBpmTaskTypeHandle.getIndex())
                    {
                        bFind=true;
                        break;
                    }
                }
                if(bFind==true)
                    continue;
                UserBpmTaskType parentUserBpmTaskType= getParentTaskType(userBpmTaskTypeList,userBpmTaskType,orderModifyAuditList);
                if(parentUserBpmTaskType==null)
                {
                    OrderModifyCorrelation orderModifyCorrelation2=new OrderModifyCorrelation();
                    orderModifyCorrelation2.setUserBpmTaskType(userBpmTaskType);
                    orderModifyCorrelationList.add(orderModifyCorrelation2);
                    handledList.add(userBpmTaskType);
                }
                else
                {
                    //首先确定父节点(父节点可能还没处理，那么忽略)
                    OrderModifyCorrelation orderModifyCorrelation=getParentNode(orderModifyCorrelationList, parentUserBpmTaskType);
                    if(orderModifyCorrelation!=null)
                    {
                        OrderModifyCorrelation orderModifyCorrelation2=new OrderModifyCorrelation();
                        orderModifyCorrelation2.setUserBpmTaskType(userBpmTaskType);
                        orderModifyCorrelation.getOrderModifyCorrelationList().add(orderModifyCorrelation2);
                        handledList.add(userBpmTaskType);
                    }
                }
            }

            count--;
            if(count<=0)
            {
                throw new RuntimeException("订单修改关联流程配置信息错误");
            }

        }while (handledList.size()<userBpmTaskTypeList.size());
        return orderModifyCorrelationList;
    }

    private OrderModifyCorrelation getParentNode(List<OrderModifyCorrelation> orderModifyCorrelationList, UserBpmTaskType userBpmTaskType)
    {
        for(OrderModifyCorrelation orderModifyCorrelation:orderModifyCorrelationList)
        {
            if(orderModifyCorrelation.getUserBpmTaskType().getIndex()==userBpmTaskType.getIndex())
            {
                return orderModifyCorrelation;
            }

            if(orderModifyCorrelation.getOrderModifyCorrelationList()!=null)
            {
                OrderModifyCorrelation orderModifyCorrelation1=this.getParentNode(orderModifyCorrelation.getOrderModifyCorrelationList(), userBpmTaskType);
                if(orderModifyCorrelation1!=null)
                {
                    return orderModifyCorrelation1;
                }
            }
        }

        return null;
    }

    private UserBpmTaskType getParentTaskType(List<UserBpmTaskType> userBpmTaskTypeList,UserBpmTaskType userBpmTaskType,List<List<String>> dynamicList)
    {
        String typeName=String.valueOf(userBpmTaskType.getIndex());
        //查找是否存在父子关系
        for(List<String> list:dynamicList)
        {
            for(int index=0;index<list.size();index++)
            {
                if(list.get(index).equals(typeName))
                {
                    //首先检查源任务是否存在
                    int sourceType=Integer.parseInt(list.get(0));
                    boolean bValid=false;
                    for(UserBpmTaskType userBpmTaskType1:userBpmTaskTypeList)
                    {
                        if(sourceType==userBpmTaskType1.getIndex())
                        {
                            bValid=true;
                            break;
                        }
                    }

                    if(bValid==true&&(index<(list.size()-1)))
                    {
                        int parentType=Integer.parseInt(list.get(index+1));
                        for(UserBpmTaskType userBpmTaskType1:userBpmTaskTypeList)
                        {
                            if(parentType==userBpmTaskType1.getIndex())
                            {
                                return userBpmTaskType1;
                            }
                        }
                        //没有找到，那么递归查找
                        return getParentTaskType(userBpmTaskTypeList,UserBpmTaskType.fromNumber(parentType),dynamicList);
                    }
                    //没有对应的，那么继续下一个关联分支
                    break;
                }
            }
        }

        return null;
    }

    @Autowired
    private OrderhistDao orderhistDao;

    public List<List<UserBpmTaskType>> getContentCorrelationFromOrder(OrderChange orderChange, List<UserBpmTaskType> userBpmTaskTypeList)
    {
        if(userBpmTaskTypeList==null||userBpmTaskTypeList.size()==0)
            return null;
        List<List<UserBpmTaskType>> listList=new ArrayList<List<UserBpmTaskType>>();
        //情况一：换地址引起收货人变化
        int matchCount=0;
        for(UserBpmTaskType userBpmTaskType:userBpmTaskTypeList)
        {
            if(userBpmTaskType.getIndex()==UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()
                    ||userBpmTaskType.getIndex()==UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex())
                matchCount++;
        }
        if(matchCount==2)
        {
            if(orderChange.getGetContactChange()!=null)
            {
                String contactId=orderChange.getGetContactChange().getContactid();
                if(StringUtils.isNotEmpty(contactId))
                {
                    Order order=orderhistDao.get(orderChange.getId());
                    if(!contactId.equals(order.getGetcontactid()))
                    {
                        List<UserBpmTaskType> dynamicList=new ArrayList<UserBpmTaskType>();
                        dynamicList.add(UserBpmTaskType.CONTACT_BASE_CHANGE);
                        dynamicList.add(UserBpmTaskType.CONTACT_ADDRESS_CHANGE);

                        listList.add(dynamicList);
                    }
                }
            }
        }

        //情况二：信用卡分期数变化，那么关联运费和信用卡
        if(StringUtils.isNotBlank(orderChange.getLaststatus()))
        {
            for(UserBpmTaskType userBpmTaskType:userBpmTaskTypeList)
            {
                if(userBpmTaskType.getIndex()==UserBpmTaskType.ORDER_MAILPRICE_CHANGE.getIndex())
                {
                    List<UserBpmTaskType> dynamicList=new ArrayList<UserBpmTaskType>();
                    dynamicList.add(UserBpmTaskType.ORDER_CARD_CHANGE);
                    dynamicList.add(UserBpmTaskType.ORDER_MAILPRICE_CHANGE);
                    listList.add(dynamicList);
                    break;
                }
            }
        }
        return listList;
    }
}
