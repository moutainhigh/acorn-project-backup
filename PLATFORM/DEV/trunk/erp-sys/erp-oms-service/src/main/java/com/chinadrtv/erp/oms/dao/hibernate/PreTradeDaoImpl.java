package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.oms.dao.PreTradeDao;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-7-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class PreTradeDaoImpl extends GenericDaoHibernate<PreTrade,Long> implements PreTradeDao {
    public PreTradeDaoImpl()
    {
        super(PreTrade.class);
    }

    @Override
    public PreTrade findTradeByNetOrderId(String netOrderId) {
        return this.find("from PreTrade where tradeId=:netOrderId",new ParameterString("netOrderId",netOrderId));
    }
}
