package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.Acorderlist;
import com.chinadrtv.erp.tc.core.dao.AcorderlistDao;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class AcorderlistDaoImpl extends GenericDaoHibernateBase<Acorderlist, Long> implements AcorderlistDao {
    public AcorderlistDaoImpl()
    {
        super(Acorderlist.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<Acorderlist> findAcorderListFromOrderId(final String orderId)
    {
        //select count(1) into v_select from iagent.acorderlist a
        //where a.type in ('2', '3', '4', '5') and a.orderid = P_OrderNo
        //and a.status = 1 and a.crdt > sysdate - 1;


        //***目前为了节省一次数据库读取，暂时获取服务器时间，如果时间差不大，不会影响后续的处理（因为时间不会要求精确处理）
        //Date sysdate=this.getCurrentDate();
        Date sysdate=new Date();

        final Date date=new Date(sysdate.getTime()-24*3600*1000L);

        return this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<List<Acorderlist>>(){
            public List<Acorderlist> doInHibernate(Session session) throws HibernateException {

                String hql="from Acorderlist where type in('2', '3', '4', '5') and status='1' and orderid=:orderid and crdt > :crdt";
                Query query=session.createQuery(hql);
                query.setParameter("orderid",orderId);
                query.setParameter("crdt",date);

                return query.list();
            }
        } ) ;
    }

    private Date getCurrentDate()
    {
        return this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<Date>() {
            public Date doInHibernate(Session session) throws HibernateException {
                Query query=session.createQuery("select  CURRENT_TIMESTAMP() from Acorderlist");
                query.setMaxResults(1);
                List list=query.list();
                if(list!=null)
                {
                    if(list.size()==0)
                    {
                        return new Date();
                    }
                    if(list.get(0) instanceof Date)
                    {
                        return (Date)list.get(0);
                    }
                }
                return null;
            }
        });
    }

}
