package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.ModifyOrderhist;
import com.chinadrtv.erp.tc.core.dao.ModifyOrderhistDao;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ModifyOrderhistDaoImpl extends GenericDaoHibernateBase<ModifyOrderhist,String> implements ModifyOrderhistDao {
    public ModifyOrderhistDaoImpl()
    {
        super(ModifyOrderhist.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /**
     * 查找是否存在修改请求
     * @param orderId
     * @return
     */
    public List<ModifyOrderhist> findModifyFromOrderId(String orderId)
    {
        //select count(1) into v_select from iagent.modify_orderhist a
        //where a.STATUS in ('1', '2') and a.orderid = P_OrderNo;

        //select count(1) into v_select from iagent.modify_orderhist a join iagent.modify_orderhist_ext b
        //on a.modorderid = b.modorderid where a.STATUS in ('1', '2') and b.orderid = P_OrderNo;
        String hql="from ModifyOrderhist where status in('1','2') and orderid=:orderid";

        List<ModifyOrderhist> modifyOrderhistList=this.findList(hql, new ParameterString("orderid", orderId));

        hql="from ModifyOrderhist a,ModifyOrderhistExt b where a.modorderid=b.modifyOderhistExtKey.modorderid and a.status in('1','2') and b.modifyOderhistExtKey.orderid=?";

        List modifyList=this.hibernateTemplate.find(hql,orderId);
        if(modifyList!=null)
        {
            for(Object obj : modifyList)
            {
                ModifyOrderhist modifyOrderhistExt=(ModifyOrderhist)(((Object[])obj)[0]);

                boolean bFind=false;
                for(ModifyOrderhist modifyOrderhist:modifyOrderhistList)
                {

                    if(modifyOrderhist.getModorderid().equals(modifyOrderhistExt.getModorderid()))
                    {
                        bFind=true;
                        break;
                    }
                }
                if(bFind==false)
                {
                    modifyOrderhistList.add(modifyOrderhistExt);
                }
            }
        }

        return modifyOrderhistList;
    }

    public Session getJustSession()
    {
        return this.getSession();
    }
}
