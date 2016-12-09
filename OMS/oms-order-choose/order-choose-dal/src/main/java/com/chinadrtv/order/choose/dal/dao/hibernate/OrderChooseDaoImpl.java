package com.chinadrtv.order.choose.dal.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.order.choose.dal.dao.OrderChooseDao;
import com.chinadrtv.order.choose.dal.model.OrderChooseQueryParm;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class OrderChooseDaoImpl extends GenericDaoHibernateBase<Order, Long> implements OrderChooseDao {
    public OrderChooseDaoImpl()
    {
        super(Order.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<Order> findOrderAutoChoose(OrderChooseQueryParm queryParm)
    {
        String hql = "FROM Order o,ShipmentHeader sh " +
                "WHERE o.status='1' AND o.orderid=sh.orderId AND o.revision=sh.orderRefRevisionId AND (o.isassign='0' or o.isassign='1' or o.isassign='2' or o.isassign is null) " +
                "AND sh.crdt > SYSDATE-:days AND o.crdt > SYSDATE-90 AND o.crdt< (SYSDATE-1/48) AND sh.accountType='1'" +
                "AND exists (" +
                "SELECT c.companyId " +
                "FROM CompanyConfig c " +
                "WHERE c.manualActing='N' and c.companyId = o.entityid " +
                ")";

        //String hql="from Order where crdt > SYSDATE-:days and status=:status and entityId is not null";

        Parameter page = new ParameterInteger("page", 0);
        page.setForPageQuery(true);

        Parameter rows = new ParameterInteger("rows", queryParm.getLimit());
        rows.setForPageQuery(true);

        Map<String, Parameter> param = new HashMap<String, Parameter>();
        param.put("days", new ParameterInteger("days",queryParm.getnDays()));
        //param.put("isAssign", new ParameterString("isAssign",queryParm.getAssignFlag()));
        param.put("page", page);
        param.put("rows", rows);

        List list=this.findPageList(hql,param);
        List<Order> orderList=new ArrayList<Order>();
        if(list!=null)
        {
            for(Object obj : list)
            {
                Object[] objs=(Object[])obj;
                orderList.add((Order)objs[0]);
            }
        }
        return orderList;
        //return this.findPageList(hql, param);
    }

    /**
     * 直接更新订单 isassign 标记
     * 目前不进行版本控制，即使其他程序同时修改订单，使标记重置了，也不会影响后期的逻辑处理
     * @param orderIdList
     */
    public void updateOrderAssignFlag(List<Long> orderIdList, String oldAssignFlag, String newAssignFlag)
    {
        StringBuilder stringBuilder = new StringBuilder("update Order set isassign=:newAssign,version=version+1 where (");
        Map<String,Object> parms = new HashMap<String, Object>();
        parms.put("newAssign", newAssignFlag);
        parms.put("oldAssign", oldAssignFlag);
        for(int i=0;i<orderIdList.size();i++)
        {
            if(i>0)
            {
                stringBuilder.append(" or ");
            }
            String name="id"+i;
            stringBuilder.append("id=:"+name);
            parms.put(name, orderIdList.get(i));
        }

        stringBuilder.append(") and isassign=:oldAssign");

        Query query = this.getSession().createQuery(stringBuilder.toString());
        for(Map.Entry<String, Object> entry : parms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }
        query.executeUpdate();
    }
}
