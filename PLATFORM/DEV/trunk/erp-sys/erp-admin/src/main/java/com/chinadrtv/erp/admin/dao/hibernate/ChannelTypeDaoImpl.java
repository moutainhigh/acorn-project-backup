package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.ChannelType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 *  
 * @author haoleitao
 * @date 2013-4-3 上午11:14:24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ChannelTypeDaoImpl extends GenericDaoHibernate<ChannelType, String> implements ChannelTypeDao {

    public ChannelTypeDaoImpl() {
        super(ChannelType.class);
    }

    public ChannelType getNativeById(String id) {
        Session session = getSession();
        session.clear(); //清楚一级缓存(BaseType的Id数据不是唯一)
        return (ChannelType)session.get(ChannelType.class, id);
    }

    public List<ChannelType> getAllChannelTypes()
    {
        Session session = getSession();
        Query query = session.createQuery("from ChannelType");
        return query.list();
    }
}
