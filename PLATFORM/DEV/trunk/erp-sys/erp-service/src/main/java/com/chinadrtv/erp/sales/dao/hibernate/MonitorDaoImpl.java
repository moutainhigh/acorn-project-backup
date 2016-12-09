package com.chinadrtv.erp.sales.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.sales.dao.MonitorDao;
import com.chinadrtv.erp.sales.dao.ProductTypeDao;
import org.apache.http.impl.cookie.DateUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class MonitorDaoImpl extends
        GenericDaoHibernate<ProductType, String> implements MonitorDao {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MonitorDaoImpl.class);

    public MonitorDaoImpl()
    {
        super(ProductType.class);
    }

    public Date getSysDate()
    {
        try{
            SQLQuery sqlQuery = this.getSession().createSQLQuery("select to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') from dual");
            String str=(String)sqlQuery.uniqueResult();
            return DateUtils.parseDate(str,new String[]{"yyyy-MM-dd hh:mm:ss"});
        }catch (Exception exp)
        {
            logger.error("get sys date error:",exp);
        }
        return null;
    }
}
